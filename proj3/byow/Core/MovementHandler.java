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

    //TODO break apart the logic for making snap shots into a separate method, otherwise the AG will hella complain
    // also, this is something I'd do later anyways.
    private static void createSnapshots(State previousState, String s) throws CloneNotSupportedException {
        Board previousBoard = previousState.getLastShot();

        int oldX1 = previousBoard.getAvatarPosition()[0];
        int oldY1 = previousBoard.getAvatarPosition()[1];
        int newX1 = 0, newY1 = 0;


        for (int i = 0; i < s.length(); i++) {

            switch (s.charAt(i)) {
                case 'w':
                    if (!collision(previousState, oldX1, oldY1 + 1)) {
                        newY1 = oldY1 + 1;
                        newX1 = oldX1;
                        previousBoard.setTile(oldX1, oldY1, Tileset.WATER);
                        previousBoard.setTile(newX1, newY1, Tileset.AVATAR);
                        doStuff(previousBoard, newX1, newY1);
                        previousBoard.setAvatarPosition(newX1, newY1);
                    }

                    break;
                case 's':
                    if (!collision(previousState, oldX1, oldY1 - 1)) {
                        newY1 = oldY1 - 1;
                        newX1 = oldX1;
                        previousBoard.setTile(oldX1, oldY1, Tileset.WATER);
                        previousBoard.setTile(newX1, newY1, Tileset.AVATAR);
                        doStuff(previousBoard, newX1, newY1);
                        previousBoard.setAvatarPosition(newX1, newY1);
                    }

                    break;
                case 'a':
                    if (!collision(previousState, oldX1 - 1, oldY1)) {
                        newX1 = oldX1 - 1;
                        newY1 = oldY1;
                        previousBoard.setTile(oldX1, oldY1, Tileset.WATER);
                        previousBoard.setTile(newX1, newY1, Tileset.AVATAR);
                        doStuff(previousBoard, newX1, newY1);
                        previousBoard.setAvatarPosition(newX1, newY1);
                    }
                    break;
                case 'd':
                    if (!collision(previousState, oldX1 + 1, oldY1)) {
                        newX1 = oldX1 + 1;
                        newY1 = oldY1;
                        previousBoard.setTile(oldX1, oldY1, Tileset.WATER);
                        previousBoard.setTile(newX1, newY1, Tileset.AVATAR);
                        doStuff(previousBoard, newX1, newY1);
                        previousBoard.setAvatarPosition(newX1, newY1);
                    }

                    break;
                case 'g':
                   if (previousBoard.isShadowing()) {
                       shadowizeBoard(previousBoard, oldX1, oldY1, false);
                   } else {
                       shadowizeBoard(previousBoard, oldX1, oldY1, true);
                   }
//                   if (!previousBoard.isShadowing()) {
//                       shadowizeBoard(previousBoard, oldX1, oldY1, true);
//                   }
                    break;
            }

            Map<String, Board> shot = new HashMap<>();
            shot.put(s, previousBoard);

            previousState.snap(shot);

            previousState.setLastShot(previousBoard);

            /* Non-shadow */
            oldY1 = previousState.getLastShot().getAvatarPosition()[1];
            oldX1 = previousState.getLastShot().getAvatarPosition()[0];

        }

        Render.render(previousBoard);

    }

    private static boolean collision(State previousState, int x, int y) {
        Map<Integer, Integer> coord = new HashMap<>();
        coord.put(x, y);

        return previousState.getWallSet().contains(coord);
    }

    private static void doStuff(Board b, int x, int y) {
        if (b.isShadowing()) {
            flipper(b, x, y);
            shadowizeBoard(b, x, y, true);
        } else {
            shadowizeBoard(b, x, y, false);
        }
    }

    /**
     * Used to "flip-on" tiles to be lit within a 1-radius around the avatar's new position
     * @param b previous board state
     * @param x new x position
     * @param y new y postition
     */
    private static void flipper(Board b, int x, int y) {
        int[][] borders = {
                {0, 1}, {0, -1},/* up, down */
                {1, 0}, {-1, 0}, /* left, right*/
                {-1, 1}, {1, 1}, /* Top left, top right */
                {-1, -1}, {-1, 1}, /* Bottom left, bottom right */
                {0, 0}
        };

        /* flip on everything in a 1-radius around (x, y) */
        for (int i = 0; i < borders.length; i++) {
            TileWrapper t = b.getTile(x + borders[i][0], y + borders[i][1]);
            t.turnOn();
        }
    }


    /**
     * We stan Shadow the hedgehog.
     * @param b previousState
     * @param x curent avatar position.
     * @param y ditto.
     */
    private static void shadowizeBoard(Board b, int x, int y, boolean shadow) {
        b.shadow(shadow);
        flipper(b, x, y);
    }

}
