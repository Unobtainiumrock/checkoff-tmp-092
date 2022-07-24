package gitlet;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static gitlet.Utils.*;

public class Repository implements Save {
    private CommitStore commitStore;
    private StageStore stageStore;
    private RemoveStageStore removeStageStore;
    private BlobStore blobStore;
    private BranchStore branchStore;
    private boolean initialized = false;
    private String currentBranch;

    public static void merge() {

    }

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
        Set<String> hFileHashes = headCommit.getFileHashes().stream().map((dualKey) -> dualKey.keySet().iterator().next()).collect(Collectors.toSet());

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
        String branchName = this.branchStore.getBranchName();
        Commit head = this.branchStore.get(branchName).getHead();
        for (CommitStore cs : this.branchStore.values()) {
            for (Commit c : cs.values()) {
                if (c.getHashID().equals(commitID)) {
                    File[] files = CWD.listFiles();
                    checkForUntrackedFiles(head, files, branchName);
                    transfer(head.getFileHashes(), c.getFileHashes(), branchName);

                    for (Map<String, String> m : this.stageStore) {
                        String fname = m.keySet().iterator().next();
                        try {
                            Files.delete(Path.of(join(CWD, fname).getName()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    this.stageStore.clear();
                    this.removeStageStore.clear();
                    this.branchStore.get(branchName).setHead(c);
                    return;
                }
            }
        }
        System.out.println("No commit with that id exists.");
        System.exit(0);

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
//        rmTwo(file);
        File tobeRemoved = join(CWD, file);
//
        boolean A = !tobeRemoved.exists(); //checking if file is in CWD. Ugh true = it isn't
        String branchName = this.branchStore.getBranchName();
        Commit headCommit = this.branchStore.get(branchName).getHead();

        Set<String> hFileHashes = headCommit.getFileHashes().stream().map((dualKey) -> dualKey.keySet().iterator().next()).collect(Collectors.toSet());
//
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
//
        String fileName = tobeRemoved.getName();
        String version = sha1(fileName, readContents(tobeRemoved));
        Map<String, String> dualKey = new HashMap<>();
        dualKey.put(fileName, version);

        boolean C = this.stageStore.canRemove(dualKey);
        boolean D = headCommit.getFileHashes().contains(dualKey);

        if (!(A || B)) {
            if (D) {
                this.removeStageStore.add(dualKey);
//                tobeRemoved.delete();
                try {
                    Files.delete(Path.of(tobeRemoved.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

//        try {
//            Files.delete(Path.of(tobeRemoved.getName()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }


    public void rmTwo(String file) {
        File tobeRemoved = join(CWD, file);
        String fileName = tobeRemoved.getName();
        String version = sha1(fileName, readContents(tobeRemoved));
        String branchName = this.branchStore.getBranchName();
        Commit headCommit = this.branchStore.get(branchName).getHead();

        Map<String, String> dk = new HashMap<>();
        dk.put(fileName, version);

        boolean A = !tobeRemoved.exists(); //checking if file is in CWD. Ugh true = it isn't
        boolean B = !headCommit.getFileHashes().contains(dk);

//        Set<String> hFileHashes = headCommit.getFileHashes().stream()
//                .map((dualKey) -> dualKey.keySet().iterator().next())
//                .collect(Collectors.toSet());
//
//        boolean B = !hFileHashes.contains(file);

        if (A && B) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        if (A && !B) {
            String v = sha1("dummy.txt");
            Map<String, String> realDualKey = new HashMap<>();
            realDualKey.put(file, v);
            this.removeStageStore.add(realDualKey);
            return;
        }

        fileName = tobeRemoved.getName();
        version = sha1(fileName, readContents(tobeRemoved));
        dk = new HashMap<>();
        dk.put(fileName, version);

        boolean C = this.stageStore.canRemove(dk);
        boolean D = headCommit.getFileHashes().contains(dk);

        if (!(A || B)) {
            if (D) {
                this.removeStageStore.add(dk);
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

        parentFileHashes.stream().filter((fileHash) -> !this.removeStageStore.contains(fileHash)).forEach((fileHash) -> this.stageStore.add(fileHash));

        Commit commit = new Commit(commitMsg, parentHash, (Set<Map<String, String>>) this.stageStore.clone(), this.branchStore.getBranchName(), parent);

        this.branchStore.get(this.branchStore.getBranchName()).add(commit.getShortenedHashID(), commit);
        this.stageStore.clear();
        this.removeStageStore.clear();
    }

    public boolean isTracked(Commit c, File file) {
        Set<Map<String, String>> fileHashes = c.getFileHashes();
        String fileName = file.getName();
        String version = sha1(fileName, readContents(file));
        Map<String, String> dualKey = new HashMap<>();
        dualKey.put(fileName, version);

        return fileHashes.contains(dualKey);

    }

    // Branch Name
    public void checkout(String branchName) {
        if (!this.branchStore.containsKey(branchName)) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        if (branchName.equals(this.branchStore.getBranchName())) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }

        Commit thisOne = this.branchStore.get(this.branchStore.getBranchName()).getHead();
        Commit other = this.branchStore.get(branchName).getHead();
        Set<Map<String, String>> thisOnesHashes = thisOne.getFileHashes();
        Set<Map<String, String>> otherFileHashes = other.getFileHashes();

        for (Map<String, String> m : this.stageStore) {
            String fname = m.keySet().iterator().next();
            try {
                Files.delete(Path.of(join(CWD, fname).getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        checkForUntrackedFiles(thisOne, CWD.listFiles(), branchName);
        transfer(thisOnesHashes, otherFileHashes, branchName);

        this.branchStore.setBranchName(branchName);
        this.stageStore.clear();
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
        String branchName = this.branchStore.getBranchName();
        CommitStore cs = this.branchStore.get(branchName);

        if (commitID.length() > 8) {
            commitID = commitID.substring(0, 8);
        }

        if (!cs.containsKey(commitID)) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }

        Commit commit = cs.get(commitID);
        Set<Map<String, String>> fileHashes = commit.getFileHashes();

        for (Map<String, String> m : fileHashes) {
            String fname = m.keySet().iterator().next();
            if (fname.equals(fileName)) {
                byte[] serializedFile = this.blobStore.get(m);
                File f = join(CWD, fname);
                writeContents(f, serializedFile);
                return;
            }
        }

        System.out.println("File does not exist in that commit.");
        System.exit(0);
    }

    private void checkForUntrackedFiles(Commit c, File[] files, String branchName) {
//        if (branchName.equals("other")) {
//
//        }

        for (File f : Arrays.asList(files)) {

            if (!f.isDirectory()) {
                String fileName = f.getName();
                String version = sha1(fileName, readContents(f));
                Map<String, String> dualKey = new HashMap<>();
                dualKey.put(fileName, version);

                Set<String> fnames = this.stageStore.stream().map((dk) -> dk.keySet().iterator().next()).collect(Collectors.toSet());

                if (!isTracked(c, f) && !fnames.contains(f.getName())) {
//                    for (File fing : CWD.listFiles()) {
//                        System.out.println("I'm in CWD: " + fing.getName());
//                    }
//                    System.out.println("File it thinks is untracked:" + f.getName());
//
//                    System.out.println("Commit Object's File Hashes: ");
//                    for (Map<String, String> ch : c.getFileHashes()) {
//                        String f_name = ch.keySet().iterator().next();
//                        System.out.println(f_name);
//                    }

                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    System.exit(0);
                }

            }
        }
//        }
    }

    // Every file in a given commit, specified by branch name.
    private void transfer(Set<Map<String, String>> src, Set<Map<String, String>> dest, String branchName) {
        File[] files = CWD.listFiles();
        List<File> filesList = Arrays.asList(files);
        List<String> fileNames = filesList.stream().map((file) -> file.getName()).collect(Collectors.toList());

        // Compare src filehashes against dest file hashes
        // delete any

        for (Map<String, String> srcHash : src) {
            String fileName = srcHash.keySet().iterator().next();
            File thizF = join(CWD, fileName);

            if (fileNames.contains(fileName)) {
                try {
                    Files.delete(Path.of(thizF.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            byte[] serializedFile = this.blobStore.get(srcHash);
            File file = join(CWD, fileName);
            writeContents(file, serializedFile);


//
//            CommitStore cs = this.branchStore.get(branchName);
//            Commit head = cs.getHead();

//            Set<Map<String, String>> dk = head.getFileHashes();

            // Write
            if (!dest.isEmpty()) {
                for (Map<String, String> f : dest) {
                    String fname = f.keySet().iterator().next();
                    byte[] sf = this.blobStore.get(f);
                    File fi = join(CWD, fname);
                    writeContents(fi, sf);
                }
            }

            // Deletion
            if (!dest.contains(srcHash)) {
                try {
                    Files.delete(Path.of(thizF.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

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
    }

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
