package gitlet;
import static gitlet.Repository.*;
import static gitlet.Utils.*;
import static gitlet.StyleCheckHelper.*;

/**
 * @author Grandpa Weekend
 */
public class Main {
    /**
     *
     * @param args a
     */
    public static void main(String[] args) {
        Repository repo = new Repository();
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        if (!join(CWD, ".gitlet").exists() && !firstArg.equals("init")) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
        if (firstArg.equals("init")) {
            repo.init();
        } else {
            help(args, firstArg, repo);
        }
        repo.saveRuntimeObjects();
    }
}
