package byow.Core;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/** @author Nancy Pelosi HUSKRR
 * Main class where everything happens. Splits up board, create rooms
 */
public class BSPartition {

    /**
     * min and max size a partition can be.
     */
    private static final int MINSIZE = 10, MAXSIZE = 15;

    /**
     * x and y coord, width and height of cell partitions and
     * rooms for CONSTRUCTOR.
     */
    private static final int INITX = 0, INITY = 0,
            INITWIDTH = 90, INITHEIGHT = 60;

    /**
     * x and y coord, width and height of cell partitions and rooms.
     */
    private int x, y, width, height;

    /**
     * the seed.
     */
    private Random r;

    /**
     * help me HELP YOUUUUUU~~~.
     */
    private HelperFunctions f;

    /**
     * maximum size a partition can be.
     */
    private int maxSplit;

    /**
     * the two splits of a partition.
     */
    private BSPartition childOne, childTwo;

    /**
     * the room that goes in each partition.
     */
    private Room room;

    /**
     * the partitions.
     */
    private List<BSPartition> cells;


    /**
     *
     * @param ree the random seed
     */
    public BSPartition(Random ree) {
        this(INITX, INITY, INITWIDTH, INITHEIGHT, ree);
        this.cells = this.partition();
    }

    /**
     * Constructor.
     *
     * @param xx      x coord position of Cell
     * @param yy     y coord position of Cell
     * @param heightt height of the Cell
     * @param widthh  width of the Cell
     * @param ree random seed
     */
    public BSPartition(int xx, int yy, int widthh, int heightt, Random ree) {
        this.r = ree;
        this.x = xx;
        this.y = yy;
        this.height = heightt;
        this.width = widthh;
        this.f = new HelperFunctions(ree);
    }

    /**
     * Creates an ArrayList that would be filled with Cells
     * (including their position on the board and size), which
     * are then filled with a different sized room in each Cell.
     * @return
     */
    public List<BSPartition> partition() {
        List<BSPartition> cellss = new ArrayList<>();

        BSPartition srcCell = this;

        cellss.add(srcCell);

        boolean splitted = true;

        while (splitted) {

            splitted = false;

            for (int i = 0; i < cellss.size(); i++) {

                if (cellss.get(i).width < this.MINSIZE
                        || cellss.get(i).height < this.MINSIZE) {
                    System.out.println("Violation occurred");
                }

                if (cellss.get(i).childOne == null
                        && cellss.get(i).childTwo == null) {
                    if (cellss.get(i).width > this.MAXSIZE
                            || cellss.get(i).height > this.MAXSIZE) {
                        if (cellss.get(i).split()) {
                            cellss.add(cellss.get(i).childOne);
                            cellss.add(cellss.get(i).childTwo);
                            splitted = true;
                        }
                    }
                }
            }
        }
        srcCell.createRooms(srcCell);
        return cellss;
    }


    /**
     * true if we can split the cell more -- would then
     * generate the position and size of the Cell.
     * @return
     */
    private boolean split() {
        if (this.childOne != null || this.childTwo != null) {
            return false;
        }

        boolean splitHori = this.r.nextBoolean();

        final double bound25 = 1.25;

        if (this.width > this.height && this.width / this.height >= bound25) {
            splitHori = false;
        } else if (this.height > this.width
                && this.height / this.width >= bound25) {
            splitHori = true;
        }

        maxSplit = (splitHori ? this.height : this.width) - this.MINSIZE;

        if (maxSplit <= this.MINSIZE) {
            return false;
        }

        int splitLoc = f.inclusiveRandom(this.MINSIZE, maxSplit);


        if (splitHori) {
            childOne = new BSPartition(this.x, this.y,
                    this.width, splitLoc, this.r);
            childTwo = new BSPartition(this.x, this.y + splitLoc,
                    this.width, this.height - splitLoc, this.r);
        } else {
            childOne = new BSPartition(this.x, this.y,
                    splitLoc, this.height, this.r);
            childTwo = new BSPartition(this.x + splitLoc, this.y,
                    this.width - splitLoc, this.height, this.r);

        }
        return true;
    }


    /**
     * create rooms within the Cell partitions by specifying the room width,
     * room height, room x coord, room y coord in Cell.
     * @param c each cell partition
     */
    private void createRooms(BSPartition c) {
        if (c.getChildOne() != null || c.getChildTwo() != null) {
            if (c.getChildOne() != null) {
                createRooms(c.getChildOne());
            }
            if (c.getChildTwo() != null) {
                createRooms(c.getChildTwo());
            }
            if (c.getChildOne() != null && c.getChildTwo() != null) {
                createHallways(getRoomHelper(c.getChildOne()),
                        getRoomHelper(c.getChildTwo()));
            }
        } else {
            int roomWidth = f.inclusiveRandom(this.MINSIZE, c.width) - 4;
            int roomHeight = f.inclusiveRandom(this.MINSIZE, c.height) - 4;
            int roomXCoord = f.inclusiveRandom(1, c.width - roomWidth) - 1;
            int roomYCoord = f.inclusiveRandom(1, c.height - roomHeight) - 1;

            c.room = new Room(c.getX() + roomXCoord, c.getY() + roomYCoord,
                    roomWidth, roomHeight);

        }
    }


    /**
     * A helper used from random crazy recursive stuff.. not a fan.
     * @param c a cell
     * @return
     */
    private Room getRoomHelper(BSPartition c) {

        if (c.room != null) {
            return c.room;
        }
        Room childOneRoom = null;
        Room childTwoRoom = null;

        if (c.childOne != null) {
            childOneRoom = getRoomHelper(c.getChildOne());
        }
        if (c.childTwo != null) {
            childTwoRoom = getRoomHelper(c.getChildTwo());
        }
        if (childOneRoom == null && childTwoRoom == null) {
            return null;
        }
        return childOneRoom == null ? childTwoRoom : childOneRoom;
    }


    /**
     * Creates a hallway between two children rooms
     * to ensure no room is disconnected.
     *
     * @param childOneRoom Cell's child one room
     * @param childTwoRoom Cell's child two room
     */
    private void createHallways(Room childOneRoom, Room childTwoRoom) {
        childOneRoom.setNeighbor(childTwoRoom);
        childTwoRoom.setNeighbor(childOneRoom);
    }

    /**
     * Getter for the list of cell partitions.
     *
     * @return
     */
    public List<BSPartition> getCells() {
        return this.cells;
    }

    /**
     * Room getter. Doesn't matter if some cells don't have rooms,
     * we will handle the null returns externally using filter.
     * @return
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Getter for x coord.
     *
     * @return
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for y coord.
     *
     * @return
     */
    public int getY() {
        return this.y;
    }

    /**
     * Getter for height of Cell.
     *
     * @return
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Getter for width of Cell.
     *
     * @return
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Getter for the first child (either left or bottom child).
     *
     * @return
     */
    public BSPartition getChildOne() {
        return this.childOne;
    }

    /**
     * Getter for the second child (either right or top child).
     *
     * @return
     */
    public BSPartition getChildTwo() {
        return this.childTwo;
    }

}
