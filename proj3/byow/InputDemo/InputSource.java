package byow.InputDemo;

import byow.Core.Dispatcher;
import byow.Core.AntiAGMagicNumbers;
import byow.Core.State;
import edu.princeton.cs.algs4.StdDraw;


public interface InputSource extends AntiAGMagicNumbers {
    final boolean PRINT_TYPED_KEYS = false;
    public static char getNextKey(State s) throws CloneNotSupportedException {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    Dispatcher.dispatch(s, Character.toString(c));
                }
                return c;
            }
        }
    }
}
