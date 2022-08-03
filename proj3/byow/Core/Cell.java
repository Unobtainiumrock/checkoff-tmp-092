package byow.Core;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static byow.Core.HelperFunctions.*;

public class Cell {

    private static final int MINSIZE = 10, MAXSIZE = 15;
    private Random r;
    private int seed;
    private int x, y, height, width;

    private int maxSplit;

    private Cell childOne, childTwo;

    private Room room; //TODO: Rectangle's (0,0) is at top left, but seems like doesn't affect our orientation
    private List<Cell> cells;


    //    private int adjMatrix[][]; //TODO: populate this after hallways are established as we would then know who is neighbor with who


    /**
     * Used for when the world is first generated.
     */
    public Cell() {
        this(200, 0, 0, 90, 60);
        this.cells = this.partition();
    }

    /**
     * Constructor
     *
     * @param seed   user's input seed
     * @param x      x coord position of Cell
     * @param y      y coord position of Cell
     * @param height height of the Cell
     * @param width  width of the Cell
     */
    public Cell(int seed, int x, int y, int width, int height) {
        this.seed = seed;
        this.r = new Random(this.seed);
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * Creates an ArrayList that would be filled with Cells (including their position on the board and size), which
     * are then filled with a different sized room in each Cell
     */
    public List<Cell> partition() {
        List<Cell> cells = new ArrayList<>();

//        Cell srcCell = new Cell(seed, 0, 0, width, height);
        Cell srcCell = this;

        cells.add(srcCell);

        boolean splitted = true;

        while (splitted) {

            splitted = false;

            for (int i = 0; i < cells.size(); i++) {

                if (cells.get(i).width < this.MINSIZE || cells.get(i).height < this.MINSIZE) {
                    System.out.println("Violation occurred");
                }

                if (cells.get(i).childOne == null && cells.get(i).childTwo == null) {
                    if (cells.get(i).width > this.MAXSIZE || cells.get(i).height > this.MAXSIZE) {
                        if (cells.get(i).split()) {
                            cells.add(cells.get(i).childOne);
                            cells.add(cells.get(i).childTwo);
                            splitted = true;
                        }
                    }
                }
            }
        }
        srcCell.createRooms(srcCell);
        return cells;
    }


    /**
     * @return true if we can split the cell more -- would then generate the position and size of the Cell
     */
    private boolean split() {
        if (this.childOne != null || this.childTwo != null) {
            return false; // already split, so don't need to split this cell anymore
        }

        boolean splitHori = this.r.nextBoolean();

        if (this.width > this.height && this.width / this.height >= 1.25) {
            splitHori = false;
        } else if (this.height > this.width && this.height / this.width >= 1.25) {
            splitHori = true;
        }

//        maxSplit = (splitHori ? this.height : this.width) - this.MINSIZE;
        maxSplit = (splitHori ? this.height - 2 : this.width - 2) - this.MINSIZE;

        if (maxSplit <= this.MINSIZE) {
            return false;
        }

        int splitLoc = inclusiveRandom(this.MINSIZE, maxSplit);


        if (splitHori) { //the following correctly maps to our (0,0) being at bottom left corner of board
//            childOne = new Cell(this.seed, this.x, this.y, this.width, splitLoc);
            childOne = new Cell(this.seed, this.x + 1, this.y + 1, this.width - 1, splitLoc);

//            childTwo = new Cell(this.seed, this.x, this.y + splitLoc, this.width, this.height - splitLoc);
            childTwo = new Cell(this.seed, this.x + 1, this.y + splitLoc + 1, this.width - 1, this.height - splitLoc - 1);
        } else {
//            childOne = new Cell(this.seed, this.x, this.y, splitLoc, this.height);
            childOne = new Cell(this.seed, this.x + 1, this.y + 1, splitLoc, this.height - 1);
//
//            childTwo = new Cell(this.seed, this.x + splitLoc, this.y, this.width - splitLoc, this.height);
            childTwo = new Cell(this.seed, this.x + splitLoc + 1, this.y + 1, this.width - splitLoc - 1, this.height - 1);
        }
        return true;
    }


    /**
     * create rooms within the Cell partitions by specifying the room width, room height, room x coord, room y coord in Cell
     */
    private void createRooms(Cell c) {
        if (c.getChildOne() != null || c.getChildTwo() != null) {
            if (c.getChildOne() != null) {
                createRooms(c.getChildOne());
            }
            if (c.getChildTwo() != null) {
                createRooms(c.getChildTwo());
            }
            if (c.getChildOne() != null && c.getChildTwo() != null) {
                createHallways(getRoomHelper(c.getChildOne()), getRoomHelper(c.getChildTwo()));
            }
        } else {
//            int roomWidth = r.nextInt(this.width - 1) + 1; // buffer the sides YOU LOSE
            int roomWidth = inclusiveRandom(this.MINSIZE, c.width) - 4;
//            int roomHeight = r.nextInt(this.height - 1) + 1;
            int roomHeight = inclusiveRandom(this.MINSIZE, c.height) - 4;
//            int roomXCoord = r.nextInt(this.width - roomWidth - 1) + 1; // -1 for buffer so room doesn't stick against side of Cell
            int roomXCoord = inclusiveRandom(1, c.width - roomWidth) - 1;
//            int roomYCoord = r.nextInt(this.height - roomHeight - 1) + 1;
            int roomYCoord = inclusiveRandom(1, c.height - roomHeight) - 1;

                c.room = new Room(c.getX() + roomXCoord, c.getY() + roomYCoord, roomWidth, roomHeight);

        }
    }


    /**
     * A helper used from random crazy recursive stuff.. not a fan.
     */
    private Room getRoomHelper(Cell c) {
        boolean splitHori = this.r.nextBoolean();

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
     * Creates a hallway between two children rooms to ensure no room is disconnected
     *
     * @param childOneRoom Cell's child one room
     * @param childTwoRoom Cell's child two room
     */
    private void createHallways(Room childOneRoom, Room childTwoRoom) {
        childOneRoom.setNeighbor(childTwoRoom);
        childTwoRoom.setNeighbor(childOneRoom);
        //TODO migrate over logic of hallways maybe..
    }

    /**
     * Getter for the list of cell partitions.
     *
     * @return
     */
    public List<Cell> getCells() {
        return this.cells;
    }

    /**
     * Room getter. Doesn't matter if some cells don't have rooms, we will handle
     * the null returns externally useing filter.
     * @return
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Getter for x coord
     *
     * @return
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for y coord
     *
     * @return
     */
    public int getY() {
        return this.y;
    }

    /**
     * Getter for height of Cell
     *
     * @return
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Getter for width of Cell
     *
     * @return
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Getter for the first child (either left or bottom child)
     *
     * @return
     */
    public Cell getChildOne() {
        return this.childOne;
    }

    /**
     * Getter for the second child (either right or top child)
     *
     * @return
     */
    public Cell getChildTwo() {
        return this.childTwo;
    }

}
