package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.junit.Test;

import java.util.*;

import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class RandomGraphTest {
    public static void main(String[] args) {

        TERenderer ter = new TERenderer();
        ter.initialize(90, 60);
        TETile[][] world = new TETile[90][60];

//         Fill the world with empty spaces
        for (int y = 0; y < 60; y += 1) {
            for (int x = 0; x < 90; x += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Set<Map<Integer, Integer>> wallSet = new HashSet<>();
        Set<Map<Integer, Integer>> floorSet = new HashSet<>();
//        Set<Map<Integer, Integer>> hallSet = new HashSet<>();

        /**
         * Creates our default board with size x = 90, y = 60
         */
        Cell wo = new Cell();

        /**
         * Dynamically fills our board by visiting each cell, grabbing their rooms,
         * and then filtering off null values for rooms that don't contain cells.S
         */
        ArrayList<Room> filteredRooms = (ArrayList<Room>) wo.getCells()
                .stream()
                .map((cell) -> cell.getRoom())
                .filter((room) -> room != null)
                .collect(Collectors.toList());

        /**
         * Draws the walls for each room
         */
        filteredRooms
                .forEach((room) -> {
                    int x = (int) room.getX();
                    int y = (int) room.getY();

                    //TODO top/bottom
                    for (int i = 0; i < room.getWidth(); i++) {
                        int x1 = x + i;
                        int y2 = y + (int) room.getHeight() - 1;

                        Map<Integer, Integer> w1 = new HashMap<>();
                        Map<Integer, Integer> w2 = new HashMap<>();

                        world[x1][y] = Tileset.WALL;
                        world[x1][y2] = Tileset.WALL;

                        w1.put(x1, y);
                        w2.put(x1, y2);

                        wallSet.add(w1);
                        wallSet.add(w2);
                    }

                    //TODO left/right
                    for (int i = 0; i < room.getHeight(); i++) {
                        int y1 = y + i;
                        int x2 = x + (int) room.getWidth() - 1;

                        Map<Integer, Integer> w1 = new HashMap<>();
                        Map<Integer, Integer> w2 = new HashMap<>();

                        world[x][y1] = Tileset.WALL;
                        world[x2][y1] = Tileset.WALL;

                        w1.put(x, y1);
                        w2.put(x2, y1);

                        wallSet.add(w1);
                        wallSet.add(w2);
                    }
                });

        /**
         * Fills in each room with floors
         */
        filteredRooms
                .forEach((room) -> {
                    int x = (int) room.getX() + 1;
                    int y = (int) room.getY() + 1;

                    for (int i = 0; i < room.getWidth() - 2; i++) {
                        for (int j = 0; j < room.getHeight() - 2; j++) {
                            try {

                                world[x + i][y + j] = Tileset.WATER;
                                Map<Integer, Integer> floorCoord = new HashMap<>();
                                floorCoord.put(x + i, y + j);
                                floorSet.add(floorCoord);

                            } catch (ArrayIndexOutOfBoundsException e) {
                                world[x - i][y + j] = Tileset.WATER;
                                Map<Integer, Integer> floorCoord = new HashMap<>();
                                floorCoord.put(x - i, y + j);
                                floorSet.add(floorCoord);
                            }

                        }
                    }
                });


        /**
         * Connects each room with hallways
         */
        filteredRooms
                .forEach((room) -> {
                    /**
                     * Room
                     */
                    int u = (int) room.getX();
                    int v = (int) room.getY();

                    /**
                     * Neighbor
                     */
                    int g = (int) room.getNeighbor().getX();
                    int h = (int) room.getNeighbor().getY();

                    /**
                     * Corner (Potential hallway intersection)
                     */

                    // Draw p1 -> p3 (up/down)
                    /**
                     * Used to capture the integer the loop terminates at. Too much to think through rn.
                     */
//                    int tmp = 0;

                    int numBroken = 0;

                    for (int i = 0; i < Math.abs(v - h); i++) {
                        Map<Integer, Integer> coord = new HashMap<>();

                        int x = u + 2;
                        int y;

                        if (v < h) {
                            y = v + i + 2;

                        } else {
                            y = v - i + 2;

                        }

                        coord.put(x, y);



//                        if (numBroken < 2 && wallSet.contains(coord)) {
//                            world[x][y] = Tileset.WATER;
//                            numBroken++;
//                        }

                        if (!(floorSet.contains(coord))) {
                            world[x][y] = Tileset.WATER;
                            Map<Integer, Integer> floorCoord = new HashMap<>();
                            floorCoord.put(x, y);
                            floorSet.add(floorCoord);

                            int k = x - 1;

                            Map<Integer, Integer> checkLeft = new HashMap<>();
                            checkLeft.put(k, y);

                            if (!(floorSet.contains(checkLeft) || wallSet.contains(checkLeft))) {
                                world[k][y] = Tileset.WALL;
                                wallSet.add(checkLeft);
                            }

                            int o = x + 1;

                            Map<Integer, Integer> checkRight = new HashMap<>();
                            checkRight.put(o, y);

                            if (!(floorSet.contains(checkRight) || wallSet.contains(checkRight))) {
                                world[o][y] = Tileset.WALL;
                                wallSet.add(checkRight);
                            }

                        }
//                        tmp = i;
                    }
                    /**
                     *  Beginning point and ending point of all (top/down) hallways.
                     *  note: This will not be reflected in the floor and wall sets.
                     *  //TODO Make these reflected in the Sets later after verifying that it doesn't affect the hall generation.
                     */
                    // Should break through 2 wall cells max per path
                    //

                    int numBroken2 = 0;
                    // Draw p3 -> p2 (left/right)
                    for (int i = 0; i < Math.abs(g - u); i++) {
                        Map<Integer, Integer> coord = new HashMap<>();

                        int x;
                        int y = h + 2;
                        if (g > u) {
                            x = u + i + 2;

                            coord.put(x, y);

                        } else {
                            x = u - i + 2;
                            coord.put(x, y);

                        }



//                        if (numBroken2 < 2 && wallSet.contains(coord)) {
//                            world[x][y] = Tileset.WATER;
//                            numBroken2++;
//                        }

                        if (!(floorSet.contains(coord))) {
                            world[x][y] = Tileset.WATER;
                            Map<Integer, Integer> floorCoord = new HashMap<>();
                            floorCoord.put(x, y);
                            floorSet.add(floorCoord);


                            int k = y - 1;

                            Map<Integer, Integer> checkTop = new HashMap<>();
                            checkTop.put(x, k);

                            if (!(floorSet.contains(checkTop) || wallSet.contains(checkTop))) {
                                world[x][k] = Tileset.WALL;
                                wallSet.add(checkTop);
                            }

                            int o = y + 1;

                            Map<Integer, Integer> checkBottom = new HashMap<>();
                            checkBottom.put(x, o);

                            if (!(floorSet.contains(checkBottom) || wallSet.contains(checkBottom))) {
                                world[x][o] = Tileset.WALL;
                                wallSet.add(checkBottom);
                            }

                        }

                    }
                });

        /**
         * Renders our world after populatinig it with the rooms.
         */
        ter.renderFrame(world);

    }

    @Test
    public void constructorTests() {
        /**
         * Should handle if no seed is provided via constructor overloading
         */
        RandomGraph rg = new RandomGraph();

        assertTrue(rg.getSize() != 0);

    }

    @Test
    public void randomPlacementTests() {
    }

    @Test
    public void vertexTests() {

    }

    @Test
    public void edgeTests() {

    }

    @Test
    public void worldSandbox() {

    }

    @Test
    public void cellRoomTest() {
    }

}
