package gitlet;
import java.io.IOException;
import static gitlet.Repository.*;
import static gitlet.Utils.*;
public class Main {
    public static void main(String[] args) throws IOException {
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
            repo.createRuntimeObjects();
            switch(firstArg) {
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
                case "rm" :
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
//            case "merge":
//                Repository.merge();
                default:
                    System.out.println("No command with that name exists.");
            }
        }
        repo.saveRuntimeObjects();
    }
}
