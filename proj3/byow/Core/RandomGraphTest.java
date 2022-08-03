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
<<<<<<< HEAD
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
=======
                        world[x + i][y] = Tileset.WALL;
                        world[x+i][y-1] = Tileset.GRASS;

                        world[x + i][y + (int) room.getHeight() - 1] = Tileset.WALL;
                        world[x+i][y + (int) room.getHeight()] = Tileset.GRASS;

>>>>>>> eac61853902e1e4cf695d7050f1f3c676cb6dcf5
                    }

                    //TODO left/right
                    for (int i = 0; i < room.getHeight(); i++) {
<<<<<<< HEAD
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
                                world[x + i][y + j] = Tileset.FLOOR;
                                Map<Integer, Integer> floorCoord = new HashMap<>();
                                floorCoord.put(x + i, y + j);
                                floorSet.add(floorCoord);

                            } catch (ArrayIndexOutOfBoundsException e) {
                                world[x - i][y + j] = Tileset.FLOOR;
                                Map<Integer, Integer> floorCoord = new HashMap<>();
                                floorCoord.put(x - i, y + j);
                                floorSet.add(floorCoord);
                            }

                        }
=======
                        world[x][y + i] = Tileset.WALL;
                        world[x-1][y+i] = Tileset.GRASS;

                        world[x + (int) room.getWidth() - 1][y + i] =  Tileset.WALL;
                        world[x + (int) room.getWidth()][y + i] =  Tileset.GRASS;
>>>>>>> eac61853902e1e4cf695d7050f1f3c676cb6dcf5
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
                    Room neighbor = room.getNeighbor();

                    int u = (int) room.getX();
                    int v = (int) room.getY();
                    int[] p1 = {u, v};

//                    int randomRoomXCoord = inclusiveRandom(u + 1, (int) (u + room.getWidth() - 1));
//                    int randomRoomYCoord = inclusiveRandom(v + 1, (int) (v + room.getHeight() - 2));

                    /**
                     * Neighbor
                     */
                    int g = (int) neighbor.getX();
                    int h = (int) neighbor.getY();
                    int[] p2 = {g, h};

//                    int randomNeighborXCoord = inclusiveRandom(g + 1, (int) (g + room.getWidth() - 1));
//                    int randomNeighborYCoord = inclusiveRandom(h + 1, (int) (h + room.getHeight() - 2));

                    /**
                     * Corner (Potential hallway intersection)
                     */
                    int[] p3 = {u, h};

<<<<<<< HEAD
                    // Draw p1 -> p3 (up/down)

                    /**
                     * Used to capture the integer the loop terminates at. Too much to think through rn.
                     */
                    int tmp = 0;

=======
//                    for (int i = 0; i < Math.abs(randomRoomYCoord - randomNeighborYCoord); i++) {
//                        if (randomNeighborYCoord < randomRoomYCoord) { //neighbor is above room
//                            world[randomRoomXCoord][randomNeighborYCoord + i] = Tileset.WALL;
//
//                        } else {
//                            world[randomRoomXCoord][randomNeighborYCoord - i] = Tileset.WALL;//neighbor is beneath room
//                        }
//                    }
//
//                    for (int i = 0; i < Math.abs(randomRoomXCoord - randomNeighborXCoord); i++) {
//                        if (randomNeighborXCoord < randomRoomXCoord) { //neighbor is to the left of room
//                            world[randomRoomXCoord - i][randomRoomYCoord] = Tileset.WALL;
//                        } else {
//                            world[randomRoomXCoord + i][randomRoomYCoord] = Tileset.WALL; //neighbor is to right of room
//                        }
//                    }

//                     Draw p1 -> p3
>>>>>>> eac61853902e1e4cf695d7050f1f3c676cb6dcf5
                    for (int i = 0; i < Math.abs(v - h); i++) {
                        Map<Integer, Integer> coord = new HashMap<>();

                        int x = u + 2;
                        int y;

                        if (v < h) {
<<<<<<< HEAD
                            y = v + i + 2;

                        } else {
                            y = v - i + 2;

=======
                            if (world[u][v+i] == Tileset.WALL) {
                                continue;
                            } else {
                                world[u][v + i] = Tileset.WALL;//draws vertical hallways
                                world[u-1][v+i] = Tileset.GRASS; //to add padding around hallways
                                world[u+1][v+i] = Tileset.GRASS; //to add padding around hallways

                            }

                        }
                        else {
                            if (world[u][v-i] == Tileset.WALL) {
                                continue;
                            } else {
                                world[u][v - i] = Tileset.WALL; //draws vertical hallways
                                world[u-1][v-i] = Tileset.GRASS; //padding around hallway
                                world[u+1][v-i] = Tileset.GRASS; //padding around hallway
                            }
>>>>>>> eac61853902e1e4cf695d7050f1f3c676cb6dcf5
                        }

                        coord.put(x, y);

                        if (!(floorSet.contains(coord) || wallSet.contains(coord))) {
                            world[x][y] = Tileset.FLOOR;
                            Map<Integer, Integer> floorCoord = new HashMap<>();
                            floorCoord.put(x, y);
                            floorSet.add(floorCoord);

                            int k = x - 1;

                            Map<Integer, Integer> checkLeft = new HashMap<>();
                            checkLeft.put(k, y);

                            if (!(floorSet.contains(checkLeft) || wallSet.contains(coord))) {
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
                        tmp = i;
                    }
                    /**
                     *  Beginning point and ending point of all (top/down) hallways.
                     *  note: This will not be reflected in the floor and wall sets.
                     *  //TODO Make these reflected in the Sets later after verifying that it doesn't affect the hall generation.
                     */
                    try {
                        world[u + 2][v + 2] = Tileset.FLOOR;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        world[u + 2][v - tmp + 3] = Tileset.FLOOR;
                    }

                    // Draw p3 -> p2 (left/right)
                    for (int i = 0; i < Math.abs(g - u); i++) {
                        Map<Integer, Integer> coord = new HashMap<>();

                        int x;
                        int y = h + 2;
                        if (g > u) {
<<<<<<< HEAD
                            x = u + i + 2;

                            coord.put(x, y);

                        } else {
                            x = u - i + 2;
                            coord.put(x, y);

=======
                            if (world[u+i][h] == Tileset.WALL) {
                                continue;
                            } else {
                                world[u + i][h] = Tileset.WALL; //draws horizontal hallways
                                world[u+i][h-1] = Tileset.GRASS; //padding around hallway
                                world[u+i][h+1] = Tileset.GRASS; //padding around hallway
                            }

                        } else {
                            if (world[u-i][h] == Tileset.WALL) {
                                continue;
                            } else {
                                world[u - i][h] = Tileset.WALL; //draws horizontal hallways
                                world[u-i][h-1] = Tileset.GRASS; //padding around hallway
                                world[u-i][h+1] = Tileset.GRASS; //padding around hallway
                            }
>>>>>>> eac61853902e1e4cf695d7050f1f3c676cb6dcf5
                        }

                        if (!(floorSet.contains(coord) || wallSet.contains(coord))) {
                            world[x][y] = Tileset.FLOOR;
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
