package gitlet;
import java.io.IOException;
import static gitlet.Repository.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Repository repo = new Repository();

        if (args.length == 0) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }

        String firstArg = args[0];

        switch(firstArg) {
            case "init":
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
                repo.commit(args[1]);
                break;
//            case "rm" :
//                Repository.rm(args[1]);
//                break;
            case "log":
                repo.createRuntimeObjects();
                repo.log();
                break;
//            case "global-log":
//                Repository.globalLog();
//            case "find":
//                Repository.find();
//                break;
            case "status":
                repo.createRuntimeObjects();
                repo.status();
                break;
            case "checkout":
                repo.createRuntimeObjects();
                if (args.length == 3) {
                    repo.checkout(args[1], args[2]);
                } else if (args.length == 4) {
                    repo.checkout(args[1], args[2], args[3]);
                }
            case "branch":
                repo.createRuntimeObjects();
                repo.branch(args[1]);
                break;
            case "rm-branch":
                repo.createRuntimeObjects();
                repo.rmBranch(args[1]);
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
