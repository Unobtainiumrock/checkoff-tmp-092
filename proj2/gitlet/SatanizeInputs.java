package gitlet;
import java.util.function.Function;

public class SatanizeInputs {

// Sarcasm is the lowest form of humor.
    public static void cleanse(String[] args, int lowerBound, int upperBound, Function<String[], Boolean> fn) {
        if (args.length > upperBound || args.length < lowerBound || !fn.apply(restArgs(args))) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }

    private static String[] restArgs(String[] args) {
        String[] restArgs = new String[args.length - 1];

        for (int i = 1; i < args.length - 1; i++) {
            restArgs[i] = args[i];
        }
        return restArgs;
    }

    public static boolean checkoutCleanse(String[] restArgs) {
        if (restArgs.length == 3) {
            return restArgs[2].equals("--");
        }

        if (restArgs.length == 2) {
            return restArgs[1].equals("--");
        }

        if (restArgs.length == 1) {
            return true;
        }

        return false;
    }
}
