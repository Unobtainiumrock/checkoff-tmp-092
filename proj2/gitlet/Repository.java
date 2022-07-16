package gitlet;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import static gitlet.Utils.*;

public class Repository implements Save {
    // TODO Move the description of the runtime objects out the a javadoc on the Repository class.
    // Holds the runtime Objects.
    // each time we run a git command, we will deserialize the serialized
    // runtime objects from the previous time the program ran.
    // The runtime objects will then be re-serialized using save() right before the program ends.
    public static CommitStore commitStore;
    public static StageStore stageStore;
    public static BlobStore blobStore;
    public static boolean initialized = false;

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
    public static void init() throws IOException {
        if (COMMIT_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }

        GITLET_DIR.mkdir();
        commitStore = new CommitStore();
        stageStore = new StageStore();
        blobStore = new BlobStore();

        Commit initialCommit = new Commit();
        String initSha1 = initialCommit.getHashID();
        commitStore.put(initSha1, initialCommit);
        initialized = true;
        //Verify that data persisted properly

//        CommitStore tmp = readObject(COMMIT_DIR, CommitStore.class);
//        Commit first = tmp.getFirstCommit();
//        System.out.println(first);


//        If not: make an init, give the init a SHA1 ID, join the init to the main branch and the current branch, write the init
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
    public static void add(String file) {
        File tobeAdded = join(CWD, file);

        if (!tobeAdded.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        String k = stageStore.stage(tobeAdded);
        writeObject(BLOB_DIR, blobStore);

        StageStore tmp = readObject(STAGE_DIR, StageStore.class);
        BlobStore b = readObject(BLOB_DIR, BlobStore.class);
        System.out.println(tmp.contains(k));
        Iterator iter = tmp.iterator();

        System.out.println(k);
        while (iter.hasNext())  {
            System.out.println(iter.next());
        }

        byte[] serializedFile = b.get(k);
//        File f = b.get(k);
        writeContents(join(CWD, "out.txt"), serializedFile);


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
    public static void commit(String commitMsg) {
//        StageStore stage = new StageStore(); //TODO: we're making a new stage in every method, this seems wrong?
//        //TODO: Think about how to fix the above
//        if (stage.stageEmpty()) {
//            System.out.println("No changes added to the commit.");
//            System.exit(0);
//        }
//        String parentHash = ""; //TODO: get the SHA1 ID of the commit that the head "pointer" is pointing to
//        stage.allPrevMap.putAll(stage.addStage); //since we are committing, put all the stuff on stage into allPrevMap
//        Map<String, String> fileHashes = stage.allPrevMap;
//        Commit commit = new Commit(commitMsg, parentHash, fileHashes);
//        commit.save();

        //Make a new commit with the commit constructor
        //Copy over the contents in parent commit
        // Update any necessary

        // Fresh repo, nothing to commit
        // "On branch <branch name>\n"
        // "Initial commit\n"
        // "nothing to commit"
        // exit

        // Non-fresh repo, things to commit, but not staged
        // "On branch <branch name>\n"
        // "Untracked files:\n"
        // "        file1\n"
        // "        file2\n"
        // "        ...\n"
        // "nothing added to commit but untracked files present"

        // Files staged
        // Commit message not provided
        // System.out.println("Please enter a commit message."), exit(0)

        // Commit message provided
        // First commit in a fresh repo
        // "[<branch name> (root-commit) <hash>] <commit message>\n"
        // "<number of files changed> changed, <number of insertions> insertions(+), <number of deletions> deletions (-)\n"
        // "create mode <100644 (*)> <filename here>"

        // Commits beyond the first commit
        // "[branch name <hash>] <commit message> "
        // "<number of files changed> changed, <number of insertions> insertions(+), <number of deletions> deletions (-)\n"
        // "create mode <100644 (*)> <filename here>"

        // (*) Something specific to file systems. This code appears to be associated with text files and creation. Try to Google file codes later.
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


    public static void log() {

    }

    public static void globalLog() {

    }

    public static void find() {

    }

    public static void status() {
    }

    public static void checkout() {

    }

    public static void branch() {

    }

    public static void rmBranch() {

    }

    public static void reset() {

    }

    public static void merge() {

    }

    public static void createRuntimeObjects() {
        commitStore = readObject(COMMIT_DIR, CommitStore.class);
        stageStore = readObject(STAGE_DIR, StageStore.class);
        blobStore = readObject(BLOB_DIR, BlobStore.class);
    }

    public static void saveRuntimeObjects() {
        writeObject(COMMIT_DIR, commitStore);
        writeObject(STAGE_DIR, stageStore);
        writeObject(BLOB_DIR, blobStore);
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

