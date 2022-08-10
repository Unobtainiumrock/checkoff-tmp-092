package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TETile;
import java.util.Random;

import static byow.Core.Utils.*;

/**
 * @author Nancy Pelosi HUSKRR
 * Engine class
 */
public class Engine implements Save, AntiAGMagicNumbers {
    private  World world;
    private MovementHandler movementHandler;
    private KeyboardInputSource inputSource;

    /**
     * Method used for exploring a fresh world. This method should handle
     * all inputs, including inputs from the main menu.
     */
    public void interactWithKeyboard() throws CloneNotSupportedException {
        Menu.displayLandingMenu();

        while (true) {
            char c = InputSource.getNextKey(this.world.getState());
            this.interactWithInputString(Character.toString(c));
            writeObject(STATE_DIR, this.world.getState());
            break;
        }
    }

    /**
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) throws CloneNotSupportedException {
        String movements = "";
        String seed = "";

        long seedL = 0;
        int indexOfEndSeed = input.indexOf('s');
        int indexOfEndOfMovement = input.indexOf(':');

        input = input.toLowerCase();

        /*
         * Build seed String;
         * */
        for (int i = 1; i < indexOfEndSeed; i++) {
            seed += input.charAt(i);
        }

        /*
         * Building the movement String.
         * */
        for (int i = indexOfEndSeed + 1; i < indexOfEndOfMovement; i++) {
            movements += input.charAt(i);
        }

        TETile[][] boardTiles = null;

        if (input.charAt(0) == 'm') {
            Draw.frame("Give your avatar a 5-letter name!");
            HUD.setAvatarName();
        }

        if (input.charAt(0) == 'l') {
            /*
             * deserialize state and get board
             */
            World loaded = new World(readObject(STATE_DIR, State.class));
            this.world = loaded;
            Board[] bs = this.world.getState().getLastShot();

            //TODO Add in logic for whether or not the shadow board is loaded.
            boardTiles = bs[0].getBoardTiles();
            World.render(bs[0]);
        } else {

            seedL = Long.parseLong(seed);
            Random useSeed = new Random(seedL);

            this.world = new World(useSeed, MENU_WIDTH, MENU_HEIGHT, movements);
            this.movementHandler = new MovementHandler(this.world);

            //TODO Add in logic for whether or not the shadow board is loaded.
            boardTiles = this.world.getBoard().getBoardTiles();
            World.render(this.world.getState().getLastShot()[0]);
        }

        /*
         * Save the world
         * */
        if (input.charAt(input.length() - 1) == 'q') {

            /*
            Serialize State.
            * */
            writeObject(STATE_DIR, this.world.getState());
        }

        return boardTiles;
    }

}
