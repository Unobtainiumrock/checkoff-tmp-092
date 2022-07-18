package gitlet;

import java.io.*;
import java.util.*;


import static gitlet.Utils.*;

public class Repository implements Save {
    private CommitStore commitStore;
    private StageStore stageStore;
    private BlobStore blobStore;
    private BranchStore branchStore;
    private boolean initialized = false;
    private String currentBranch = "main";

// TODO remove the playground after testing persistence of data upon deserialization.

//    public static void playGround() {
//        // Create a blobMap and commitMap
//        BlobStore blobMap = new BlobStore();
//        CommitStore commitMap = new CommitStore();
//
//        // Create our initial commit
//        Commit initialCommit = new Commit();
//
//        // Set up some mock files
//        List<File> files = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            files.add(new File("" + i));
//        }
//
//        // Verify
//        files.forEach(System.out::println);
//        // hash the files
//        List<Map<String, byte[]>> hashedFiles = files.stream().map((file) -> {
//            Map<String, byte[]> m = new HashMap<>();
//            byte[] v = serialize(file);
//            String k = sha1(v);
//            m.put(k, v);
//            return m;
//
//        }).collect(Collectors.toList());
//        //
//        String initialCommitHash = initialCommit.getHashID(); // Also can access first commit from commit store.
//
//        Commit secondCommit = new Commit("I am a second commit", initialCommitHash, hashedFiles);
//
//        System.out.println("Test Commit Metadata");
//        System.out.println(initialCommit);
//
//
//        System.out.println("Second Commit Metadata");
//        System.out.println(secondCommit);
//
//        List<Map<String, byte[]>> fileHashes = secondCommit.getFileHashes();
//        fileHashes.forEach((file) -> {
//            String k = file.keySet().iterator().next();
//            byte[] v = file.values().iterator().next();
//            try {
//                byte[] = new ByteArrayInputStream(v);
//                ObjectInputStream = new ObjectInputStream(fis);
//                System.out.println(Utils);
//            } catch (FileNotFoundException e) {
//            }
//
//        });
//
//        Commit thirdCommit = new Commit("I am a third commit", "72", secondCommit.getHashID(), )
//
//    }

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
        this.blobStore = new BlobStore(this);
        this.branchStore = new BranchStore(this);
        this.initialized = true;
        this.branchStore.put(this.currentBranch, this.commitStore);
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

        this.stageStore.stage(tobeAdded, this.blobStore);
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

        if (this.stageStore.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        Commit parent = this.branchStore.get(this.currentBranch).getHead();
        String parentHash = parent.getHashID();
        Set<Map<String, String>> parentFileHashes = parent.getFileHashes();

        parentFileHashes.iterator().forEachRemaining((fileHash) -> this.stageStore.add(fileHash));

        Commit commit = new Commit(commitMsg, parentHash, (Set<Map<String, String>>) this.stageStore.clone());

        this.branchStore.get(this.currentBranch).add(commit.getHashID(), commit);
        this.stageStore.clear();
    }

    public void checkout(String branchName) {
        this.currentBranch = branchName;
    }

    public void checkout(String separator, String fileName) {
        checkoutHelper(this.branchStore.get(this.currentBranch).getHead().getHashID(), fileName);
    }

    public void checkout(String commitID, String separator, String fileName) {
        checkoutHelper(commitID, fileName);
    }

    private void checkoutHelper(String commitID, String fileName) {
        if (!this.branchStore.get(this.currentBranch).containsKey(commitID)) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        Commit commit = this.branchStore.get(this.currentBranch).get(commitID);
        Set<Map<String, String>> fileHashes = commit.getFileHashes();
        Iterator<Map<String, String>> iter = fileHashes.iterator();

        while (iter.hasNext()) {
            Map<String, String> curr = iter.next();
            String currFileName = curr.keySet().iterator().next();

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
        Commit currentCommit = this.branchStore.get(this.currentBranch).getHead();
        while (currentCommit != null) {
            System.out.println("===");
            System.out.println("commit " + currentCommit.getHashID());
            System.out.println("Date: " + currentCommit.getTimestamp());
            System.out.println(currentCommit.getMessage());
            System.out.println();
            currentCommit = this.branchStore.get(this.currentBranch).get(currentCommit.getParentID());
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
    public static void rm(String file) {

    }


    public static void globalLog() {

    }

    public static void find() {

    }

    public void status() {
        System.out.println("=== Branches ===");
        System.out.println("*" + this.currentBranch);
        Iterator<String> iter = this.branchStore.keySet().iterator();

        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println();
        System.out.println("=== Staged Files===");

        Iterator<Map<String, String>> iterTwo = this.stageStore.iterator();

        while (iterTwo.hasNext()) {
            Map<String, String> curr = iterTwo.next();
            String currFileName = curr.keySet().iterator().next(); // Grabs first key on a single element HashMap. --The filename
            System.out.println(currFileName);
        }

        System.out.println();
        System.out.println("=== Removes Files ===");

        Iterator<Map<String, String>> iterThree = this.stageStore.getRemoveStage().iterator();

        while (iterThree.hasNext()) {
            Map<String, String> curr = iterThree.next();
            String currFileName = curr.keySet().iterator().next(); // Grabs first key on a single element HashMap. TODO Refactor later.
            System.out.println(currFileName);
        }

        System.out.println("=== Modifications Not Staged For Commit ===");
        
    }

    public void branch(String branchName) {
        if (this.branchStore.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        Commit previousBranchesCommit = this.branchStore.get(this.currentBranch).getHead();
        this.branchStore.put(branchName, new CommitStore(previousBranchesCommit));
    }

    public static void rmBranch() {

    }

    public static void reset() {

    }

    public static void merge() {

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
        this.blobStore = readObject(BLOB_DIR, BlobStore.class);
        this.branchStore = readObject(BRANCH_DIR, BranchStore.class);
    }

    public void saveRuntimeObjects() {
        writeObject(COMMIT_DIR, this.commitStore);
        writeObject(STAGE_DIR, this.stageStore);
        writeObject(BLOB_DIR, this.blobStore);
        writeObject(BRANCH_DIR, this.branchStore);
    }

    @Override
    public void save(File f) {
        return;
    }

}

// Git status
// git status message
// "On branch <branch name>\n"
// "No commits yet"
// "Untracked files:\n"
// "  (use \"git add <file>...\" to include in what will be committed)\n"
// "        file1\n"
// "        file2\n"
// "        ...<additional files>\n"

