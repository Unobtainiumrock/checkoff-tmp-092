package gitlet;
import java.io.IOException;
import static gitlet.Repository.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Repository repo = new Repository();

        if (args.length == 0) {
            System.out.println("Incorrect operands.");
//           throw new GitletException("Incorrect operands.");
            System.exit(0);
        }

        String firstArg = args[0];

        switch(firstArg) {
            case "init":
//                SatanizeInputs.cleanse(args, 0, 0, (restArgs) -> true);
                repo.init();
                break;
            case "add":
                repo.createRuntimeObjects();
                if (COMMIT_DIR.exists()) {
                    repo.add(args[1]);
                } else {
                    System.exit(0);
                }
                break;
            case "commit":
                repo.createRuntimeObjects();
//                SatanizeInputs.cleanse(args, 1, 1, (restArgs) -> true);
                repo.commit(args[1]);
                break;
//            case "rm" :
//                Repository.rm(args[1]);
//                break;
//            case "log":
//                Repository.log();
//            case "global-log":
//                Repository.globalLog();
//            case "find":
//                Repository.find();
//                break;
//            case "status":
//                Repository.status();
            case "checkout":
                repo.createRuntimeObjects();
                SatanizeInputs.cleanse(args, 1, 3, SatanizeInputs::checkoutCleanse);
                if (args.length == 3) {
                    repo.checkout(args[1], args[2]);
                } else if (args.length == 4) {
                    repo.checkout(args[1], args[2], args[3]);
                }
//            case "branch":
//                Repository.branch();
//            case "rm-branch":
//                Repository.rmBranch();
//            case "reset":
//                Repository.reset();
//            case "merge":
//                Repository.merge();

            // This "test" case will be used for development purposes. I'm going to see if not closing the program
            // and interacting with it as a true CLI app using a while loop will let us handle reading/writing to
            // files, prior to closing. Similar to what I've done in previous Java courses.
//            case "test":
//                break;
        }
        repo.saveRuntimeObjects();
    }
}
