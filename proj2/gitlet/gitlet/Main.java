package gitlet;

// Stretch goal. See if any pipelines of functions are performed in the
// project and play with streams. update: Maybe not, it appears java.nio and java.io
// might not play well together. nio is a buffer oriented pkg and io is a stream oriented package.
// I think I'll prefer working with java.nio b/c apparently its non-blocking!
// https://www.geeksforgeeks.org/difference-between-java-io-and-java-nio/

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No command provided, please provide a command,");
            System.exit(0);
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                // If not already initialized, then git add should fail. Check for git init.
                Repository.exists(); // Error out the application, if repo D.N.E.
                Repository.add(args[1]);
                break;
            case "commit":
                Repository.exists(); // ditto for DNE edge case
                Repository.commit(args[1]);
            case "rm" :
                Repository.exists(); // ditto. Maybe refactor this later? Look into class-level decorators for Repository.java's methods.s
                break;
            case "log":
                Repository.exists();
                Repository.log();
                // This "test" case will be used for development purposes. I'm going to see if not closing the program
                // and interacting with it as a true CLI app using a while loop will let us handle reading/writing to
                // files, prior to closing. Similar to what I've done in previous Java courses.
            case "global-log":
                Repository.exists();
                Repository.globalLog();
            case "find":
                Repository.exists();
                Repository.find();
                break;
            case "status":
                Repository.exists();
                Repository.status();
            case "checkout":
                Repository.exists(); // Soo repetitive. Look for decorators..
                Repository.checkout();
            case "branch":
                Repository.exists();
                Repository.branch();
            case "rm-branch":
                Repository.exists();
                Repository.rmBranch();
            case "reset":
                Repository.exists();
                Repository.reset();
            case "merge":
                Repository.exists();
                Repository.merge();
            case "test":
                break;
            // TODO: FILL THE REST IN
        }
    }
}
