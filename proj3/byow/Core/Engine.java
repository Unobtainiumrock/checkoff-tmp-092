package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.Random;

/** @author Nancy Pelosi HUSKRR
 * Engine class
 */
public class Engine {
    /**
     * RENDER DUH.
     */
    private TERenderer ter = new TERenderer();

    /**
     * their width.
     */
    public static final int WIDTH = 90;

    /**
     * their height.
     */
    public static final int HEIGHT = 60;

    /**
     * Method used for exploring a fresh world. This method should handle
     * all inputs, including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }


    /**
     * TODO this thing first, then interactWithKeyboard. This will act
     * TODO as simulating key entries.
     */


    /**
     * Method used for autograding and testing your code. The input string
     * will be a series of characters (for example, "n123sswwdasdassadwas",
     * "n123sss:q", "lwww". The engine should behave exactly as if the user
     * typed these characters into the engine using interactWithKeyboard.
     * TODO Use serialization and de-serialization for the persistence of state.
     * Migrate over my parsing and validating user inputs over
     * from previous java projects.
     * TODO WE should expect de-serialization to serve as an initial state to
     * TODO which we "concatenate" the subsequent state of the program. See (*).

     * Recall that strings ending in ":q" should cause the game to quite save.
     * For example, if we do interactWithInputString("n123sss:q"), we expect
     * the game to run the first 7 commands (n123sss) and then quit and save.
     * If we then do interactWithInputString("l"), we should be back in the
     * exact same state.
     *
     *  (*) In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        String seed = "";
        long seedL = 0;
        input = input.toUpperCase();
        char firstLtr = input.charAt(0);
        char lastLtr = input.charAt(input.length() - 1);

        for (int i = 1; i < input.length() - 1; i++) {
            seed += input.charAt(i);
        }

        seedL = Long.parseLong(seed);

        Random useSeed = new Random(seedL);

        World helloWorld = new World(useSeed, WIDTH, HEIGHT);


        TETile[][] finalWorldFrame = helloWorld.getWorld();
        return finalWorldFrame;
    }


    /**
     * Stupid getter for stupid ter.
     * @return
     */
    public TERenderer getTer() {
        return this.ter;
    }
}
