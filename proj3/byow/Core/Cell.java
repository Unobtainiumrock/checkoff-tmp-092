package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static byow.Core.HelperFunctions.*;

public class Cell {

    private static final int MINSIZE = 5, MAXSIZE = 10;
    private Random r;
    private int seed;
    private int x, y, height, width;

    private int maxSplit;

    private Cell childOne, childTwo;

    private Rectangle room; //TODO: Rectangle's (0,0) is at top left, but seems like doesn't affect our orientation

    private ArrayList<Rectangle> hallways;
    private List<Cell> cells;

    public int timesRan;

//    private int adjMatrix[][]; //TODO: populate this after hallways are established as we would then know who is neighbor with who


    /**
     * Used for when the world is first generated.
     */
    public Cell() {
        this(69, 0, 0, 20, 10);
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

        maxSplit = (splitHori ? this.height : this.width) - this.MINSIZE;

        if (maxSplit <= this.MINSIZE) {
            return false;
        }

        int splitLoc = inclusiveRandom(this.MINSIZE, maxSplit);


        if (splitHori) { //the following correctly maps to our (0,0) being at bottom left corner of board
            childOne = new Cell(this.seed, this.x, this.y, this.width, splitLoc);
            childTwo = new Cell(this.seed, this.x, this.y + splitLoc, this.width, this.height - splitLoc);
        } else {
            childOne = new Cell(this.seed, this.x, this.y, splitLoc, this.height);
            childTwo = new Cell(this.seed, this.x + splitLoc, this.y, this.width - splitLoc, this.height);
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
                createHallways(getRoom(c.getChildOne()), getRoom(c.getChildTwo()));
            }
        } else {
//            int roomWidth = r.nextInt(this.width - 1) + 1; // buffer the sides YOU LOSE
            int roomWidth = inclusiveRandom(this.MINSIZE, c.width) - 2;
//            int roomHeight = r.nextInt(this.height - 1) + 1;
            int roomHeight = inclusiveRandom(this.MINSIZE, c.height) - 2 ;
//            int roomXCoord = r.nextInt(this.width - roomWidth - 1) + 1; // -1 for buffer so room doesn't stick against side of Cell
            int roomXCoord = inclusiveRandom(1, c.width - roomWidth) - 1;
//            int roomYCoord = r.nextInt(this.height - roomHeight - 1) + 1;
            int roomYCoord = inclusiveRandom(1, c.height - roomHeight) - 1;
            c.room = new Rectangle(c.getX() + roomXCoord, c.getY() + roomYCoord, roomWidth, roomHeight);

            //TODO: fill grid in each room with a certain type of tile
            // Tile
            // tileset
            // render

            //TODO: surround each room generated with wall tiles
        }
    }


    /**
     * Get each room so can later know where to generate hallways for connection
     */
    public Rectangle getRoom(Cell c) {
        boolean splitHori = this.r.nextBoolean();

        if (c.room != null) {
            return c.room;
        }
        Rectangle childOneRoom = null;
        Rectangle childTwoRoom = null;

        if (c.childOne != null) {
            childOneRoom = getRoom(c.getChildOne());
        }
        if (c.childTwo != null) {
            childTwoRoom = getRoom(c.getChildTwo());
        }
        if (childOneRoom == null && childTwoRoom == null) {
            return null;
        }
//        else if (childOneRoom == null) {
//            return childTwoRoom;
//        } else if (childTwoRoom == null) {
//            return childOneRoom;
//        } else if (splitHori) {
//            return childOneRoom;
//        } else {
//            return childTwoRoom;
//        }
        return ((childOneRoom == null) ? childTwoRoom : childOneRoom);
    }


    /**
     * Creates a hallway between two children rooms to ensure no room is disconnected
     *
     * @param childOneRoom Cell's child one room
     * @param childTwoRoom Cell's child two room
     */
    public void createHallways(Rectangle childOneRoom, Rectangle childTwoRoom) {
        this.hallways = new ArrayList<>();

        //TODO: find a random point in childOneRoom, a random point in childTwoRoom
        //TODO: connect the two rooms via these two points, making either straight or bendy hallway
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
