package byow.Core;

import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Map;

public class MovementHandler {
    private World world;

    /**
     * Constructor exists so that movement handler can reference the world's wallSet and TileSet
     * for managing collision checks.
     *
     * @param w
     */
    public MovementHandler(World w) {
        this.world = w;
    }

    public static void acceptDispatch(State previousState, String s) throws CloneNotSupportedException {
        createSnapshots(previousState, s);
    }

    //TODO break apart the logic for makig snap shots into a separate method, otherwise the AG will hella complain
    // also, this is something I'd do later anyways.
    private static void createSnapshots(State previousState, String s) throws CloneNotSupportedException {
        Board previousBoard = previousState.getLastShot()[0];
        Board cp = (Board) previousBoard.clone();

        int oldX = previousBoard.getAvatarPosition()[0];
        int oldY = previousBoard.getAvatarPosition()[1];
        int newX = 0, newY = 0;

        /*
        * Mapper will look like this, given the following example String:
        * "WWASD"
        *
        * {{"W", "W"}, {"W", "WW"}, {"A", "WWA"}, {"S", "WWAS"}, {"D", "WWASD"} }
        * The mapper is then iterated and the single-element(first item in the 'tuple') strings will be read and used for updating the
        * avatar position. The second element in the 'tuple' will be used to create a key for our snapshot hashmap,
        * where the value for each key is the current state of the board.
        *
        * */
        String[][] mapper = new String[s.length()][2];
        String thing = "";

        for (int i = 0; i < s.length(); i++) {
            thing += s.charAt(i);
            mapper[i][0] = Character.toString(s.charAt(i)).toLowerCase();
            mapper[i][1] = thing;
        }

        for (int i = 0; i < thing.length(); i ++) {

            switch(mapper[i][0]) {
                case "w":
                    if (!collision(previousState, oldX, oldY + 1)) {
                        newY = oldY + 1;
                        newX = oldX;
                        cp.setTile(oldX, oldY, Tileset.WATER);
                        cp.setTile(newX, newY, Tileset.AVATAR);
                        cp.setAvatarPosition(newX, newY);
                    }
                    break;
                case "s":
                    if (!collision(previousState, oldX, oldY -  1)) {
                        newY = oldY - 1;
                        newX = oldX;
                        cp.setTile(oldX, oldY, Tileset.WATER);
                        cp.setTile(newX, newY, Tileset.AVATAR);
                        cp.setAvatarPosition(newX, newY);
                    }
                    break;
                case "a":
                    if (!collision(previousState, oldX - 1, oldY)) {
                        newX = oldX - 1;
                        newY = oldY;
                        cp.setTile(oldX, oldY, Tileset.WATER);
                        cp.setTile(newX, newY, Tileset.AVATAR);
                        cp.setAvatarPosition(newX, newY);
                    }
                    break;
                case "d":
                    if (!collision(previousState, oldX + 1, oldY)) {
                        newX = oldX + 1;
                        newY = oldY;
                        cp.setTile(oldX, oldY, Tileset.WATER);
                        cp.setTile(newX, newY, Tileset.AVATAR);
                        cp.setAvatarPosition(newX, newY);
                    }
                    break;
            }

            Map<Integer, Integer> coord = new HashMap<>();
            coord.put(newX, newY);

            Board shadowBoard = ((Board) cp.clone()).shadow();

            Map<String, Board> shot = new HashMap<>();
            shot.put(thing, cp);

            Map<String, Board> shadowShot = new HashMap<>();
            shadowShot.put("*" + thing, shadowBoard);

            previousState.snap(shot);
            previousState.snap(shadowShot);

            Board[] shots = new Board[2];
            shots[0] = cp;
            shots[1] = shadowBoard;

            previousState.setLastShot(shots);

            previousBoard = previousState.getLastShot()[0];
            oldY = previousBoard.getAvatarPosition()[1];
            oldX = previousBoard.getAvatarPosition()[0];
        }
    }

    private static boolean collision(State previousState, int x, int y) {
        Map<Integer, Integer> coord = new HashMap<>();
        coord.put(x, y);

        return previousState.getWallSet().contains(coord);
    }

}
