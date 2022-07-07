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

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");

    public static void init() {
        // Initializes repo.
    }

    public static void exists() {
        // Checks if a repository is initialized already.
    }

    public static void add(String file) {
        // Performs git add logic.
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

