package gitlet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Stack;

import static gitlet.Utils.*;

public class Repository implements Save {
    private CommitStore commitStore;
    private StageStore stageStore;
    private RemoveStageStore removeStageStore;
    private BlobStore blobStore;
    private BranchStore branchStore;
    private boolean initialized = false;
    private String currentBranch;


    //Displays information about all commits ever made.
    public void globalLog() {
        for (CommitStore cs : this.branchStore.values()) {
            for (Commit c : cs.values()) {
                System.out.println("===");
                System.out.println("commit " + c.getHashID());
                System.out.println("Date: " + c.getTimestamp());
                System.out.println(c.getMessage());
                System.out.println();
            }
        }
        //TODO: remember to come back and fix the case when there are merge commits i.e. two parents
    }

    public void find(String commitMsg) {
        boolean emptyChecker = true;
        for (CommitStore cs : this.branchStore.values()) {
            for (Commit c : cs.values()) {
                if (c.getMessage().equals(commitMsg)) {
                    System.out.println(c.getHashID());
                    emptyChecker = false;
                }
            }
        }
        if (emptyChecker) {
            System.out.println("Found no commit with that message.");
        }
    }

    public void rmBranch(String branchName) {
        if (!this.branchStore.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        } else if (this.branchStore.getBranchName().equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        }

        String origBranch = this.branchStore.getBranchName();
        this.branchStore.setBranchName(branchName);
        Commit headCommit = this.branchStore.get(this.branchStore.getBranchName()).getHead();
        Set<String> hFileHashes = headCommit.getFileHashes().stream()
                .map((dualKey) -> dualKey.keySet().iterator().next())
                .collect(Collectors.toSet());

        File[] files = CWD.listFiles();

        for (File f : files) {
            if (hFileHashes.contains(f.getName())) {
                try {
                    Files.delete(Path.of(f.getName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        this.branchStore.setBranchName(origBranch);
        this.branchStore.remove(branchName);

    }

    //Checks out all the files tracked by the given commit. Removes tracked files that are not present in that commit.
    // Also moves the current branch’s head to that commit node. See the intro for an example of what happens to the head pointer after using reset.
    // The [commit id] may be abbreviated as for checkout. The staging area is cleared.
    // The command is essentially checkout of an arbitrary commit that also changes the current branch head.
    public void reset(String commitID) {
        for (CommitStore cs : this.branchStore.values()) {
            for (Commit c : cs.values()) {
                if (c.getHashID().equals(commitID)) {
//                    checkout(c.getBranchMom());

//                    checkout(c.getBranchMom(), "--", );
                    File[] files = CWD.listFiles();

                    for (File f : Arrays.asList(files)) {
                        if (!isTracked(f) && !f.isDirectory()) {
                            System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                            System.exit(0);
                        }
                    }
                    Set<Map<String, String>> fileHashes = c.getFileHashes();
                    for (File f : CWD.listFiles()) {
                        if (!fileHashes.contains(f) && !f.isDirectory()) {
                            try {
                                Files.delete(Path.of(f.getName()));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    this.stageStore.clear();
                    this.removeStageStore.clear();
                    this.branchStore.get(this.branchStore.getBranchName()).setHead(c);
                    return;
                }
            }
        }
        System.out.println("No commit with that id exists.");
        System.exit(0);

    }

    public static void merge() {

    }

    /**
     * Usage: java gitlet.Main init
     * <p>
     * Description: Creates a new Gitlet version-control system in the current directory.
     * This system will automatically start with one commit: a commit that contains no files
     * and has the commit message initial commit (just like that, with no punctuation). It will have a
     * single branch: main, which initially points to this initial commit, and main will be the current branch.
     * The timestamp for this initial commit will be 00:00:00 UTC, Thursday, 1 January 1970 in whatever format
     * you choose for dates (this is called “The (Unix) Epoch”, represented internally by the time 0.)
     * Since the initial commit in all repositories created by Gitlet will have exactly the same content,
     * it follows that all repositories will automatically share this commit (they will all have the same UID)
     * and all commits in all repositories will trace back to it.
     * <p>
     * Runtime: O(1)
     */
    public void init() {
        if (COMMIT_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }

        GITLET_DIR.mkdir();
        this.commitStore = new CommitStore(this);
        this.stageStore = new StageStore(this);
        this.removeStageStore = new RemoveStageStore(this);
        this.blobStore = new BlobStore(this);
        this.branchStore = new BranchStore(this, "main");
        this.initialized = true;
        this.branchStore.put(this.branchStore.getBranchName(), this.commitStore);
        this.saveRuntimeObjects();
    }

    /**
     * Usage: java gitlet.Main add [filename]
     * <p>
     * Description: Adds a copy of the file as it currently exists to the staging area (see the description of the commit command).
     * For this reason, adding a file is also called staging the file for addition. Staging an already-staged file overwrites the
     * previous entry in the staging area with the new contents. The staging area should be somewhere in .gitlet. If the current
     * working version of the file is identical to the version in the current commit, do not stage it to be added, and remove it
     * from the staging area if it is already there (as can happen when a file is changed, added, and then changed back to it’s
     * original version). The file will no longer be staged for removal (see gitlet rm), if it was at the time of the command.
     * <p>
     * Runtime: In the worst case, should run in linear time relative to the size of the file being added and O(log(N)), for N
     * the number of files in the commit.
     * <p>
     * Failure Cases: If the file does not exist, print the error message File does not exist. and exit without changing anything
     *
     * @param file A string representing the file we wish to commit.
     */
    public void add(String file) {
        File tobeAdded = join(CWD, file);

        if (!tobeAdded.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        String fileName = tobeAdded.getName();
        String version = sha1(fileName, readContents(tobeAdded));
        Map<String, String> dualKey = new HashMap<>();
        dualKey.put(fileName, version);

        boolean success = false;
        if (!this.removeStageStore.contains(dualKey)) {
            success = this.stageStore.add(tobeAdded, this.blobStore); // Stage needs access to blobStore
        } else {
            if (!success) {
                this.removeStageStore.clear();
            }
        }

    }

    /**
     * Usage: java gitlet.Main rm [file name]
     * <p>
     * Description: Unstage the file if it is currently staged for addition. If the file is tracked in the current commit,
     * stage it for removal and remove the file from the working directory if the user has not already done so
     * (do not remove it unless it is tracked in the current commit).
     * <p>
     * Runtime: O(1)
     *
     * @param file
     */
    public void rm(String file) {
        File tobeRemoved = join(CWD, file);

        boolean A = !tobeRemoved.exists(); //checking if file is in CWD. Ugh true = it isn't

        Commit headCommit = this.branchStore.get(this.branchStore.getBranchName()).getHead();
        Set<String> hFileHashes = headCommit.getFileHashes().stream()
                .map((dualKey) -> dualKey.keySet().iterator().next())
                .collect(Collectors.toSet());

        boolean B = !hFileHashes.contains(file);

        if (A && B) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        if (A && !B) {
            String version = sha1("dummy.txt");
            Map<String, String> realDualKey = new HashMap<>();
            realDualKey.put(file, version);
            this.removeStageStore.add(realDualKey);
            return;
        }

        String fileName = tobeRemoved.getName();
        String version = sha1(fileName, readContents(tobeRemoved));
        Map<String, String> dualKey = new HashMap<>();
        dualKey.put(fileName, version);

        boolean C = this.stageStore.canRemove(dualKey);
        boolean D = headCommit.getFileHashes().contains(dualKey);

        if (!(A || B)) {
            if (D) {
                this.removeStageStore.add(dualKey);
                tobeRemoved.delete();
            }
        }

        if (!A && B) {
            if (C) {
                this.stageStore.removeFromAddStage(tobeRemoved, this.blobStore);
            }
        }
        if (!(C || D)) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }
    }


    /**
     * Usage: java gitlet.Main commit [message]
     * <p>
     * Description: Saves a snapshot of tracked files in the current commit and staging area so they can be restored at
     * a later time, creating a new commit. The commit is said to be tracking the saved files. By default,
     * each commit’s snapshot of files will be exactly the same as its parent commit’s snapshot of files;
     * it will keep versions of files exactly as they are, and not update them. A commit will only update
     * the contents of files it is tracking that have been staged for addition at the time of commit, in
     * which case the commit will now include the version of the file that was staged instead of the version
     * it got from its parent. A commit will save and start tracking any files that were staged for addition
     * but were not tracked by its parent. Finally, files tracked in the current commit may be untracked in the
     * new commit as a result being staged for removal by the rm command (below).
     *
     * @param commitMsg A string representing the commit message.
     */
    public void commit(String commitMsg) {

        if (this.stageStore.isEmpty() && this.removeStageStore.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        if (commitMsg.equals("")) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }

        Commit parent = this.branchStore.get(this.branchStore.getBranchName()).getHead();
        String parentHash = parent.getHashID();
        Set<Map<String, String>> parentFileHashes = parent.getFileHashes();

        // parentFileHashes.iterator().forEachRemaining((fileHash) -> this.stageStore.add(fileHash));
        parentFileHashes.stream().filter((fileHash) -> !this.removeStageStore.contains(fileHash))
                .forEach((fileHash) -> this.stageStore.add(fileHash));


        Commit commit = new Commit(commitMsg, parentHash, (Set<Map<String, String>>) this.stageStore.clone(),
                this.branchStore.getBranchName(), parent);

        this.branchStore.get(this.branchStore.getBranchName()).add(commit.getHashID(), commit);
        this.stageStore.clear();
        this.removeStageStore.clear();
    }

    public boolean isTracked(File file) {
        Commit headCommit = this.branchStore.get(this.branchStore.getBranchName()).getHead();
        Set<String> hFileHashes = headCommit.getFileHashes().stream()
                .map((dualKey) -> dualKey.keySet().iterator().next())
                .collect(Collectors.toSet());

        return hFileHashes.contains(file.getName());
    }

    public void checkout(String branchName) {
        if (!this.branchStore.containsKey(branchName)) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        if (branchName.equals(this.branchStore.getBranchName())) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }
        File[] files = CWD.listFiles();
        Commit headCommit = this.branchStore.get(this.branchStore.getBranchName()).getHead();
        if (headCommit.getBranchMom().equals(branchName)) {
            for (File f : Arrays.asList(files)) {
                if (!isTracked(f)) {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    System.exit(0);
                }
            }
        }

        this.branchStore.setBranchName(branchName);
    }

    public void checkout(String separator, String fileName) {
        if (!separator.equals("--")) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        checkoutHelper(this.branchStore.get(this.branchStore.getBranchName()).getHead().getHashID(), fileName);
    }

    public void checkout(String commitID, String separator, String fileName) {
        if (!separator.equals("--")) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        checkoutHelper(commitID, fileName);
    }

    private void checkoutHelper(String commitID, String fileName) {
        if (!this.branchStore.get(this.branchStore.getBranchName()).containsKey(commitID)) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        Commit commit = this.branchStore.get(this.branchStore.getBranchName()).get(commitID);
        Set<Map<String, String>> fileHashes = commit.getFileHashes();
        Iterator<Map<String, String>> iter = fileHashes.iterator();

        while (iter.hasNext()) {
            Map<String, String> curr = iter.next(); // Dual key
            String currFileName = curr.keySet().iterator().next(); // Grab file name from first part of key

            if (currFileName.equals(fileName)) {
                byte[] serializedFile = this.blobStore.get(curr);
                File f = join(CWD, currFileName);
                writeContents(f, serializedFile);
                return;
            }
        }
        System.out.println("File does not exist in that commit.");
        System.exit(0);
    }

    public void log() {

        Commit currentCommit = this.branchStore.get(this.branchStore.getBranchName()).getHead();

        while (currentCommit != null) {
            System.out.println("===");
            System.out.println("commit " + currentCommit.getHashID());
            System.out.println("Date: " + currentCommit.getTimestamp());
            System.out.println(currentCommit.getMessage());
            System.out.println();
            currentCommit = currentCommit.getParent();
        }

//        Commit rest = this.branchStore.get(this.branchStore.getBranchName()).getHead();
//        System.out.println("===");
//        System.out.println("commit " + rest.getHashID());
//        System.out.println("Date: " + rest.getTimestamp());
//        System.out.println(rest.getMessage());
//        System.out.println();
//
//        while (rest != null) {
//            System.out.println("===");
//            System.out.println("commit " + currentCommit.getHashID());
//            System.out.println("Date: " + currentCommit.getTimestamp());
//            System.out.println(currentCommit.getMessage());
//            System.out.println();
//            rest = this.branchStore.get(this.branchStore.getBranchName()).get(rest.getParentID());
//        }

//        List<Commit> stack = new Stack<>();
//        HashMap<String, Commit> root = this.branchStore.get("main");
//        HashMap<String, Commit> branch = this.branchStore.get(this.branchStore.getBranchName());

//        for (Commit c : root.values()) {
//            ((Stack<Commit>) stack).push(c);
//        }
//
//        for (Commit c : branch.values()) {
//            ((Stack<Commit>) stack).push(c);
//        }

//        for (CommitStore cs : this.branchStore.values()) {
//            for (Commit c : cs.values()) {
//                ((Stack<Commit>) stack).push(c);
//                if (c.equals(this.branchStore.get(this.branchStore.getBranchName()).getHead())) {
//                    break;
//                }
//            }
//            ((Stack<Commit>) stack).pop();
//        }
//        recurser((Stack<Commit>) stack);
//
//        while (!stack.isEmpty()) {
//            Commit c = ((Stack<Commit>) stack).pop();
//            System.out.println("===");
//            System.out.println("commit " + c.getHashID());
//            System.out.println("Date: " + c.getTimestamp());
//            System.out.println(c.getMessage());
//            System.out.println();
//        }

    }

//    private void recurser(Stack<Commit> stack) {
//        for (CommitStore cs : this.branchStore.values()) {
//            for (Commit c : cs.values()) {
//                stack.push(c);
//                if (c.equals(this.branchStore.get(this.branchStore.getBranchName()).getHead())) {
//                    break;
//                }
//            }
//            recurser(stack);
//            stack.pop();
//        }
//    }

    public void status() {
        System.out.println("=== Branches ===");
        System.out.println("*" + this.branchStore.getBranchName());
        Iterator<String> iter = this.branchStore.keySet().iterator();

        while (iter.hasNext()) {
            String next = iter.next();
            if (!(next.equals(this.branchStore.getBranchName()))) {
                System.out.println(next);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");

        Iterator<Map<String, String>> iterTwo = this.stageStore.iterator();

        while (iterTwo.hasNext()) {
            Map<String, String> curr = iterTwo.next();
            String currFileName = curr.keySet().iterator().next(); // Grabs first key on a single element HashMap. --The filename
            System.out.println(currFileName);
        }

        System.out.println();
        System.out.println("=== Removed Files ===");

        Iterator<Map<String, String>> iterThree = this.removeStageStore.iterator();

        while (iterThree.hasNext()) {
            Map<String, String> curr = iterThree.next();
            String currFileName = curr.keySet().iterator().next(); // Grabs first key on a single element HashMap. TODO Refactor later.
            System.out.println(currFileName);
        }

        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
//       A: Tracked in the current commit, changed in the working directory, but not staged; or
//       B: Staged for addition, but with different contents than in the working directory; or
//       C: Staged for addition, but deleted in the working directory; or
//       D: Not staged for removal, but tracked in the current commit and deleted from the working directory.

//        A || B || C || D

//        A: Staged, commit, cwd
//        B: Staged, cwd
//        C: Staged, cwd
//        D: Removal stage, commit, cwd

//        File[] directoryFiles = CWD.listFiles();

        Commit currentCommit = this.branchStore.get(this.branchStore.getBranchName()).getHead();
        StageStore stage = this.stageStore;

        boolean inCurrentCommit = false;
        boolean changedInWorkingDirectory = false;
        boolean staged = false;
        boolean removalStaged = false;
        boolean differentContents = false;
        boolean inCWD = false; // clarify if D.N.E in cwd is the same as deleted in cwd.


        boolean A = inCurrentCommit && changedInWorkingDirectory && !staged;
        boolean B = staged && differentContents;
        boolean C = staged && !inCWD;
        boolean D = !removalStaged && inCurrentCommit && !inCWD;

        Arrays.stream(CWD.listFiles()).forEach((file) -> {
            String fileName = file.getName();
            if (currentCommit.getFileHashes().contains(fileName)) {

            }
//            System.out.println(file.getName());
        });

        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();

    }

    public void branch(String branchName) {
        if (this.branchStore.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        Commit previousBranchesCommit = this.branchStore.get(this.branchStore.getBranchName()).getHead();
        this.branchStore.put(branchName, new CommitStore(previousBranchesCommit));
    }

    public boolean getInitialized() {
        return this.initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public CommitStore getCommitStore() {
        return this.commitStore;
    }

    public StageStore getStageStore() {
        return this.stageStore;
    }

    public BlobStore getBlobStore() {
        return this.blobStore;
    }

    public void createRuntimeObjects() {
        this.commitStore = readObject(COMMIT_DIR, CommitStore.class);
        this.stageStore = readObject(STAGE_DIR, StageStore.class);
        this.removeStageStore = readObject(RMSTAGE_DIR, RemoveStageStore.class);
        this.blobStore = readObject(BLOB_DIR, BlobStore.class);
        this.branchStore = readObject(BRANCH_DIR, BranchStore.class);
    }

    public void saveRuntimeObjects() {
        writeObject(COMMIT_DIR, this.commitStore);
        writeObject(STAGE_DIR, this.stageStore);
        writeObject(RMSTAGE_DIR, this.removeStageStore);
        writeObject(BLOB_DIR, this.blobStore);
        writeObject(BRANCH_DIR, this.branchStore);
    }

    @Override
    public void save(File f) {
        return;
    }

}
