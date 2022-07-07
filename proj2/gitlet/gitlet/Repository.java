package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");

    public static void init() {
        // Initializes repo.
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
     * @param file A string representing the file we with to commit. If "." is provided, then it will iterate over all untracked files and add them.
     *             to the staging area.
     */
    public static void add(String file) {
        // `git add`
        // "Nothing specified, nothing added.\n"
        // "Maybe you wanted to say 'git add .'?"

        // `git add .`
        // nothing happens

        // `git add <incorrect path or file>`
        // "fatal: pathspec '<incorrect path here>' did not match any files"

        // 'git add <correct path>'
        // No feedback given, perform logic to stage stuff.
    }

    public static void commit(String commitMsg) {
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
        // Exit and give an error saying that a commit msg needs to be provided
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
    public static void exists() {
        // Checks if a repository is initialized already.
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

