package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;

import static byow.Core.Utils.*;

/**
 * @author Nancy Pelosi HUSKRR
 * Engine class
 */
public class Engine implements Save, AntiAGMagicNumbers {
    private World world;
    private MovementHandler movementHandler;
    private long seed = 69L;

    /**
     * Method used for exploring a fresh world. This method should handle
     * all inputs, including inputs from the main menu.
     */
    public void interactWithKeyboard() throws CloneNotSupportedException {
//        Menu.displayLandingMenu();
//        String menuChoice = KeyboardInputSource.solicitInput(1);

        this.menuNav(false);
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

        if (input.charAt(0) == 'l') {
            this.loadWorld();

            Board[] bs = this.loadWorld();
            boardTiles = bs[0].getBoardTiles();
        } else {

            seedL = Long.parseLong(seed);
            Random useSeed = new Random(seedL);

            this.world = new World(useSeed, MENU_WIDTH, MENU_HEIGHT, movements);
            this.movementHandler = new MovementHandler(this.world);

            //TODO Add in logic for whether or not the shadow board is loaded.
            boardTiles = this.world.getBoard().getBoardTiles();
//            World.render(this.world.getState().getLastShot()[0]);
            Render.render(this.world.getState().getLastShot()[0]);
        }

        /*
         * Save the world
         * */
        if (input.charAt(input.length() - 1) == 'q') {
            this.saveWorld();
        }

        return boardTiles;
    }

    private long buildSeed() {
        String res = "";

        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(THOUSAND);
        }

        char c = 0;

        if (StdDraw.hasNextKeyTyped()) {
            c = StdDraw.nextKeyTyped();
            res += c;
        }

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();

                if (c == 's') {
                    break;
                }

                res += c;
                Draw.frame(res);
            }
        }
        return Long.parseLong(res);
    }

    private Board[] loadWorld() {
        World loaded = new World(readObject(STATE_DIR, State.class));
        this.world = loaded;
        Board[] bs = this.world.getState().getLastShot();
        //TODO Add in logic for whether or not the shadow board is loaded.
//        World.render(bs[0]);
        Render.render(bs[0]);

        return bs;
    }

    private void saveWorld() {
        writeObject(STATE_DIR, this.world.getState());
    }

    private void menuNav(boolean second) throws CloneNotSupportedException {
        if (!second) {
            Menu.displayLandingMenu();
        } else {
            Menu.displaySecondMenu();
        }

        String menuChoice = KeyboardInputSource.solicitInput(1);

        switch (menuChoice) {
            case "n":
                Draw.frame("Please enter a numerical seed, then 's'");
                StdDraw.pause(3000);
                this.seed = this.buildSeed();
                this.world = new World(new Random(this.seed), MENU_WIDTH, MENU_HEIGHT, "");
                this.movementHandler = new MovementHandler(this.world);
//                this.world.render(this.world.getBoard());
                Render.render(this.world.getBoard());
                while (otherKeyHandler() != ":") {
                }
                break;
            case "q":
                this.saveWorld();
                System.exit(0);
                break;
            case "l":
                this.loadWorld();
                break;
            case "m":
                if (!second) {
                    Draw.frame("Give your avatar a 5-letter name!");
                    if (!HUD.hasAvatarName()) {
                        HUD.setAvatarName();
                        StdDraw.pause(2000);
                        Draw.frame("Your avatar's name is: \n" + HUD.avatarName);
                        StdDraw.pause(3000);
                        Draw.frame("Navigating to main menu... " + HUD.avatarName);
                        StdDraw.pause(3000);
                        menuNav(true);
                    }
                } else {
                    Draw.frame("Already named your avatar!");
                    break;
                }
        }
    }

    // clear, show, pause
    private String otherKeyHandler() throws CloneNotSupportedException {
        String res = "";
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(THOUSAND);
        }

        while (res.length() != 1) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();

                if (Character.toString(c).toLowerCase().equals(':')) {
                    this.saveWorld();
                    System.exit(0);
                }

                res += c;
                Dispatcher.dispatch(this.world.getState(), res);s
            }
        }
        return res;
    }
}
