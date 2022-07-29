package gitlet;

import static gitlet.Save.COMMIT_DIR;

/**
 * @author Grandpa Weekend
 */
public class StyleCheckHelper {
    /**
     * @param args     a
     * @param firstArg f
     * @param repo     r
     */
    public static void help(String[] args, String firstArg, Repository repo) {
        repo.createRuntimeObjects();
        switch (firstArg) {
            case "add":
                if (COMMIT_DIR.exists()) {
                    repo.add(args[1]);
                } else {
                    System.exit(0);
                }
                break;
            case "commit":
                repo.commit(args[1]);
                break;
            case "rm":
                repo.rm(args[1]);
                break;
            case "log":
                repo.log();
                break;
            case "global-log":
                repo.globalLog();
                break;
            case "find":
                repo.find(args[1]);
                break;
            case "status":
                repo.status();
                break;
            case "checkout":
                if (args.length == 3) {
                    repo.checkout(args[1], args[2]);
                } else if (args.length == 4) {
                    repo.checkout(args[1], args[2], args[3]);
                } else if (args.length == 2) {
                    repo.checkout(args[1]);
                }
                break;
            case "branch":
                repo.branch(args[1]);
                break;
            case "rm-branch":
                repo.rmBranch(args[1]);
                break;
            case "reset":
                repo.reset(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
        }
    }
}
