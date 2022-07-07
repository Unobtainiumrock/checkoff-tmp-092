package gitlet;

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
                // This "test" case will be used for development purposes. I'm going to see if not closing the program
                // and iteracting with it as a true CLI app using a while loop will let us handle reading/writing to
                // files prior to closing. Similar to what I've done in previous Java courses.
            case "test":
                break;
            // TODO: FILL THE REST IN
        }
    }
}
