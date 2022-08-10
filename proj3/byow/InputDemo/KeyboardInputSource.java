package byow.InputDemo;

/**
 * Created by hug.
 */

import byow.Core.*;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;

/**
 * Blame the stupid AG for this stupid shenanigans of NUMBER = # everywhere..
 */
public class KeyboardInputSource implements InputSource, AntiAGMagicNumbers {

    public static String solicitInput(int n) {
        String res = "";

        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(THOUSAND);
        }

        while (res.length() != n) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                res += c;
                Draw.frame(res);
            }
        }
        return res;
    }
}
