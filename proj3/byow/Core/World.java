package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;
import java.util.stream.Collectors;

public class World {
    private Random r;
    private Board board;
    private State state;
    private Set<Map<Integer, Integer>> wallSet = new HashSet<>();
    private Set<Map<Integer, Integer>> floorSet = new HashSet<>();
    private BSPartition wo;
    private TETile avatar;

    /**
     * Used for when we are interacting with the game via the keyboard.
     * KeyboardInputSource class expects to have
     */
    public World() {

    }

    /**
     * Used for when we are loading a world from the file system.
     *
     */
    public World(State s) {
        this.setState(s);
    }

    public World(Random r, int width, int height, String movements, TETile avatar) throws CloneNotSupportedException {
        Board b = new Board(width, height);

        this.avatar = avatar;
        this.r = r;
        this.initBoard(b);
        this.populateWorld();
        this.partitionWorld(this.r);
        this.initRooms();
        this.placeAvatar(avatar, width, height);
        this.state = new State(b, wallSet, floorSet, movements, this.avatar);
    }

    private void initBoard(Board b) {
        if (this.board == null) {
            this.board = b;
        } else {
            this.board = this.state.getLastShot();
        }
    }

    /**
     * Used externally to render the map.
     */
    public static void render(Board b) {
        TERenderer ter = new TERenderer();
        ter.initialize(b.getWidth(), b.getHeight());
        ter.renderFrame(b.getBoardTiles());
    }

    /**
     * Fills the world with blank tiles.
     */
    private void populateWorld() {
        for (int y = 0; y < 60; y += 1) {
            for (int x = 0; x < 90; x += 1) {
                this.board.setTile(x, y, Tileset.NOTHING);
            }
        }
    }

    /**
     * Uses a BSP Tree to partition the world into cells for rooms to be placed.
     * @param r nice.
     */
        private void partitionWorld(Random r) {
        this.wo = new BSPartition(r);
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
     */
    private ArrayList<Room> getRooms() {
        return (ArrayList<Room>) wo.getCells()
                .stream()
                .map((cell) -> cell.getRoom())
                .filter((room) -> room != null)
                .collect(Collectors.toList());
    }

    /**
     * Draws the walls for each room
     */
    private void draWalls(ArrayList<Room> rooms) {
        rooms
                .forEach((room) -> {
                    int x = (int) room.getX();
                    int y = (int) room.getY();

                    /*
                     * Draws the top and bottom borders on a particular room.
                     */
                    for (int i = 0; i < room.getWidth(); i++) {
                        int x1 = x + i;
                        int y2 = y + (int) room.getHeight() - 1;

                        Map<Integer, Integer> w1 = new HashMap<>();
                        Map<Integer, Integer> w2 = new HashMap<>();

                        this.board.setTile(x1, y, Tileset.WALL);

                        this.board.setTile(x1, y2, Tileset.WALL);

                        w1.put(x1, y);
                        w2.put(x1, y2);

                        this.wallSet.add(w1);
                        this.wallSet.add(w2);
                    }

                    /*
                     * Draws the left and right borders on a particular room
                     */
                    for (int i = 0; i < room.getHeight(); i++) {
                        int y1 = y + i;
                        int x2 = x + (int) room.getWidth() - 1;

                        Map<Integer, Integer> w1 = new HashMap<>();
                        Map<Integer, Integer> w2 = new HashMap<>();

                        this.board.setTile(x, y1, Tileset.WALL);
                        this.board.setTile(x2, y1, Tileset.WALL);

                        w1.put(x, y1);
                        w2.put(x2, y1);

                        wallSet.add(w1);
                        wallSet.add(w2);;
                    }
                });
    }

    /**
     * Fills in each room with water.
     */
    private void fillRooms(ArrayList<Room> rooms) {
        rooms
                .forEach((room) -> {
                    int x = (int) room.getX() + 1;
                    int y = (int) room.getY() + 1;

                    for (int i = 0; i < room.getWidth() - 2; i++) {
                        for (int j = 0; j < room.getHeight() - 2; j++) {
                            try {
                                this.board.setTile(x + i, y + j, Tileset.WATER);
                                Map<Integer, Integer> floorCoord = new HashMap<>();
                                floorCoord.put(x + i, y + j);
                                floorSet.add(floorCoord);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                this.board.setTile(x - i, y + j, Tileset.WATER);
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
     * @param rooms A list of all the rooms that cells contain, where cells are created during the partitioning process.
     */
    private void drawHalls(List<Room> rooms) {
        /*
         * Connects each room with hallways
         */
        rooms
                .forEach((room) -> {
                    /*
                     * Room A
                     */
                    int u = (int) room.getX();
                    int v = (int) room.getY();

                    /*
                     * Room B
                     */
                    int g = (int) room.getNeighbor().getX();
                    int h = (int) room.getNeighbor().getY();


                    /*
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

                        /*.
                         * Fill the hall with water.
                         */
                        if (!(this.floorSet.contains(coord))) {

                            this.board.setTile(x, y, Tileset.WATER);
                            Map<Integer, Integer> floorCoord = new HashMap<>();
                            floorCoord.put(x, y);
                            this.floorSet.add(floorCoord);

                            int k = x - 1;

                            Map<Integer, Integer> checkLeft = new HashMap<>();
                            checkLeft.put(k, y);

                            /*
                             * Adds a border to the hallway's left side.
                             */
                            if (!(this.floorSet.contains(checkLeft))) {
                                this.board.setTile(k, y, Tileset.WALL);
                                this.wallSet.add(checkLeft);
                            }

                            int o = x + 1;

                            Map<Integer, Integer> checkRight = new HashMap<>();
                            checkRight.put(o, y);

                            /*
                             * Adds a border to the hallway's right side.
                             */
                            if (!(this.floorSet.contains(checkRight))) { // floor set contains, wall doesn't
                                this.board.setTile(o, y, Tileset.WALL);
                                this.wallSet.add(checkRight);
                            }

                            /*
                             * Makes sure to remove tiles from the wallSet so that when a hall connects into a room,
                             * the avatar will be able to enter/exit rooms.
                             * */
                            this.wallSet.remove(coord);

                        }

                    }

                    /*
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

                        /*
                         * FIl
                         */
                        if (!(this.floorSet.contains(coord))) {
                            this.board.setTile(x, y, Tileset.WATER);
                            Map<Integer, Integer> floorCoord = new HashMap<>();
                            floorCoord.put(x, y);
                            this.wallSet.add(floorCoord);


                            int k = y - 1;

                            Map<Integer, Integer> checkTop = new HashMap<>();
                            checkTop.put(x, k);

                            if (!(this.floorSet.contains(checkTop))) {
                                this.board.setTile(x, k, Tileset.WALL);
                                this.wallSet.add(checkTop);
                            }

                            int o = y + 1;

                            Map<Integer, Integer> checkBottom = new HashMap<>();
                            checkBottom.put(x, o);

                            if (!(this.floorSet.contains(checkBottom))) {
                                this.board.setTile(x, o, Tileset.WALL);
                                this.wallSet.add(checkBottom);
                            }

                            /*
                             * Makes sure to remove tiles from the wallSet so that when a hall connects into a room,
                             * the avatar will be able to enter/exit rooms.
                             * */
                            this.wallSet.remove(coord);
                        }
                    }
                });
    }

    private void placeAvatar(TETile avatar, int widthBounds, int heightBounds) {
        int randX = this.r.nextInt(widthBounds);
        int randY = this.r.nextInt(heightBounds);

        TETile loc = this.board.getTile(randX, randY).getCurrent();

        while (!(loc.description() == "water")) {
            randX = this.r.nextInt(widthBounds);
            randY = this.r.nextInt(heightBounds);
            loc = this.getBoard().getTile(randX, randY).getCurrent();
        }

        this.board.setTile(randX, randY, avatar);
        this.board.setAvatarPosition(randX, randY);

    }

    public State getState() {
        return this.state;
    }
    public void setState(State s) {
        this.state = s;
    }
    public Board getBoard() {
        return this.board;
    }

}
