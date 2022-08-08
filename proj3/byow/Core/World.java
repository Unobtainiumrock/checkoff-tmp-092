package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.stream.Collectors;

/**
 * @author Nancy Pelosi
 * World.
 */
public class World {

    /**
     * ugh.
     */
    private static final int INITSEED = 69, INITWIDTH = 90, INITHEIGHT = 60;
    /**
     * r.
     */
    private Random r;
    /**
     * world.
     */
    private TETile[][] world;
    /**
     * w.
     */
    private Set<Map<Integer, Integer>> wallSet;
    /**
     * fs.
     */
    private Set<Map<Integer, Integer>> floorSet;
    /**
     * screw you.
     */
    private Set<Map<Integer, Integer>> hallSet;
    /**
     * ugh.
     */
    private BSPartition wo;

    /**
     * world UGH.
     */
    public World() {
        this(new Random(INITSEED), INITWIDTH, INITHEIGHT);
    }

    /**
     * ugh2.
     *
     * @param ree    r
     * @param width  w
     * @param height h
     */
    public World(Random ree, int width, int height) {
        this.r = ree;
        this.world = new TETile[width][height];

        this.wallSet = new HashSet<>();
        this.floorSet = new HashSet<>();
        this.hallSet = new HashSet<>();

        this.populateWorld();
        this.partitionWorld(this.r);
        this.initRooms();
    }

    /**
     * Used externally to render the map.
     */
    public void render() {
    }

    /**
     * Fills the world with blank tiles.
     */
    private void populateWorld() {
        final int height = 60;
        final int width = 90;
        for (int y = 0; y < height; y += 1) {
            for (int x = 0; x < width; x += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Uses a BSP Tree to partition the world into cells for rooms to be placed.
     *
     * @param ree nice.
     */
    private void partitionWorld(Random ree) {
        this.wo = new BSPartition(ree);
    }

    /**
     * Invokes methods to place rooms within the game map.
     */
    private void initRooms() {
        ArrayList<Room> filteredRooms = this.getRooms();
        this.draWalls(filteredRooms);
        this.fillRooms(filteredRooms);
        this.drawHalls(filteredRooms);
    }

    /**
     * Grabs rooms from all cells containing rooms, filtering off the null ones.
     *
     * @return
     */
    private ArrayList<Room> getRooms() {
        return (ArrayList<Room>) wo.getCells()
                .stream()
                .map((cell) -> cell.getRoom())
                .filter((room) -> room != null)
                .collect(Collectors.toList());
    }


    /**
     * Draws the walls for each room.
     *
     * @param rooms rooms
     */
    private void draWalls(ArrayList<Room> rooms) {
        rooms
                .forEach((room) -> {
                    int x = (int) room.getX();
                    int y = (int) room.getY();

                    for (int i = 0; i < room.getWidth(); i++) {
                        int x1 = x + i;
                        int y2 = y + (int) room.getHeight() - 1;

                        Map<Integer, Integer> w1 = new HashMap<>();
                        Map<Integer, Integer> w2 = new HashMap<>();

                        world[x1][y] = Tileset.WALL;
                        world[x1][y2] = Tileset.WALL;

                        w1.put(x1, y);
                        w2.put(x1, y2);

                        this.wallSet.add(w1);
                        this.wallSet.add(w2);
                    }

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
    }

    /**
     * Fills in each room with water.
     *
     * @param rooms rooms
     */
    private void fillRooms(
            ArrayList<Room> rooms) {
        rooms
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
    }

    /**
     * Places the hallway tiles on the map
     *
     * @param rooms A list of all the rooms that cells contain, where cells are created during the partitioning process.
     */
    private void drawHalls(List<Room> rooms) {
        /**
         * Connects each room with hallways
         */
        rooms
                .forEach((room) -> {
                    /**
                     * Room A
                     */
                    int u = (int) room.getX();
                    int v = (int) room.getY();

                    /**
                     * Room B
                     */
                    int g = (int) room.getNeighbor().getX();
                    int h = (int) room.getNeighbor().getY();


                    /**
                     * Up/down.
                     */
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

                        /**
                         * Fill the hall with water.
                         */
                        if (!(floorSet.contains(coord))) {
                            world[x][y] = Tileset.WATER;
                            Map<Integer, Integer> floorCoord = new HashMap<>();
                            floorCoord.put(x, y);
                            floorSet.add(floorCoord);

                            int k = x - 1;

                            Map<Integer, Integer> checkLeft = new HashMap<>();
                            checkLeft.put(k, y);

                            /**
                             * Adds a border to the hallway's left side.
                             */
                            if (!(floorSet.contains(checkLeft) || wallSet.contains(checkLeft))) {
                                world[k][y] = Tileset.WALL;
                                wallSet.add(checkLeft);
                            }

                            int o = x + 1;

                            Map<Integer, Integer> checkRight = new HashMap<>();
                            checkRight.put(o, y);

                            /**
                             * Adds a border to the hallway's right side.
                             */
                            if (!(floorSet.contains(checkRight) || wallSet.contains(checkRight))) {
                                world[o][y] = Tileset.WALL;
                                wallSet.add(checkRight);
                            }

                        }
                    }

                    /**
                     * Left/right
                     */
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

                        /**
                         * FIl
                         */
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
    }

    /**
     * getter.
     * @return
     */
    public TETile[][] getWorld () {
        return this.world;
    }
}