package gitlet;
import java.io.IOException;
import static gitlet.Repository.*;

public class Main {
    public static void main(String[] args) throws IOException {
//        Repository.playGround();

        if (args.length == 0) {
           throw new GitletException("Incorrect operands.");
        }

        if (COMMIT_DIR.exists()) {
            createRuntimeObjects();
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                if (args.length > 1) {
                    throw new GitletException("Incorrect operands.");
                }
                Repository.init();
                break;
            case "add":
                // If not already initialized, then git add should fail. Check for git init.
                Repository.add(args[1]);
                break;
//            case "commit":
//                Repository.commit(args[1]);
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
//            case "checkout":
//                Repository.checkout();
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
        saveRuntimeObjects();
    }
}
