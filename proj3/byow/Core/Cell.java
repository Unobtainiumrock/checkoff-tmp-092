package byow.Core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cell {

    private Random r;

    private int seed;

    private static final int minCellSize = 5;

    private int x, y, height, width;

    private int maxSplit;

    private Cell childOne;

    private Cell childTwo;

    private Rectangle room; //TODO: Rectangle's (0,0) is at top left, need to consider orientation when implementing rooms

    private ArrayList<Rectangle> hallways;

    private int adjMatrix[][]; //TODO: populate this after hallways are established


    /**
     * Constructor
     * @param seed user's input seed
     * @param x x coord position of Cell
     * @param y y coord position of Cell
     * @param height height of the Cell
     * @param width width of the Cell
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
     *
     * @return true if we can split the cell more, and also does the size and coord position generation for the Cell
     */
    public boolean split() {
        if (this.childOne != null || this.childTwo != null) {
            return false; // already split, so don't need to split this cell anymore
        }

        boolean splitHori = r.nextBoolean();

        if (this.width > this.height && this.width / this.height >= 1.25) {
            splitHori = false;
        } else if (this.height > this.width && this.height / this.width >= 1.25) {
            splitHori = true;
        }

        maxSplit = (splitHori ? this.height : this.width) - this.minCellSize;

        if (maxSplit <= this.minCellSize) {
            return false;
        }

        int splitLoc = r.nextInt(maxSplit);

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
     * create rooms within Cell by specifying the room width, room height, room x coord, room y coord in Cell
     */
    public void createRooms() {
        if (this.childOne != null) { //make room for childOne until all children in childOne have rooms
            this.childOne.createRooms();
        } else if (this.childTwo != null) { //make room for childTwo until all children in childTwo have rooms
            this.childTwo.createRooms();
        } else if (this.childOne != null && this.childTwo != null) { //only hit here after above recursions have created rooms for all and returning back
            createHallways(childOne.getRoom(), childTwo.getRoom());

        } else {
            //TODO: find if there's any way to set the lower bound of r.nextInt to be at least 1 so don't generate wasted rooms with height or width = 0
            int roomWidth = r.nextInt(this.width - 2); //-2 for a space 1 buffer on each side
            int roomHeight = r.nextInt(this.height - 2);
            int roomXCoord = r.nextInt(this.width - roomWidth - 1); //-1 for buffer so room doesn't stick against side of Cell
            int roomYCoord = r.nextInt(this.height - roomHeight - 1);
            this.room = new Rectangle(x + roomXCoord, y + roomYCoord, roomHeight, roomWidth);
            //TODO: fill grid in each room with a certain type of tile
            //TODO: surround each room generated with wall tiles
        }
    }


    /**
     * Creates an ArrayList that would be filled with Cells (including their position on the board and size), which
     * are then filled with a different sized room in each Cell
     */
    public void createCells() {
        int maxCellSize = 10;

        List<Cell> cells = new ArrayList<>();

        Cell srcCell = new Cell(seed, 0, 0, width, height);

        cells.add(srcCell);

        boolean splitted = true;

        while (splitted) {
            splitted = false;

            for (int i = 0; i < cells.size(); i++) {
                if (cells.get(i).childOne == null && cells.get(i).childTwo == null) {
                    if (cells.get(i).width > maxCellSize || cells.get(i).height > maxCellSize) {
                        if (cells.get(i).split()) {
                            cells.add(cells.get(i).childOne);
                            cells.add(cells.get(i).childTwo);
                            splitted = true;
                        }
                    }
                }
            }
        }
        srcCell.createRooms(); //create rooms starting from srcCell, recursively going into each of its children cells to make rooms
    }


    /**
     * Get each room so can later know where to generate hallways for connection
     */
    public Rectangle getRoom() {

        if (this.room != null) {
            return this.room;
        }
        Rectangle childOneRoom = null;
        Rectangle childTwoRoom = null;

        if (this.childOne != null) {
            childOneRoom = this.childOne.getRoom();
        }
        if (this.childTwo != null) {
            childTwoRoom = this.childTwo.getRoom();
        }

        return ((childOneRoom != null) ? childOneRoom : childTwoRoom);
    }


    /**
     * Creates a hallway between two children rooms to ensure no room is disconnected
     * @param childOneRoom Cell's child one room
     * @param childTwoRoom Cell's child two room
     */
    public void createHallways(Rectangle childOneRoom, Rectangle childTwoRoom) {
        this.hallways = new ArrayList<>();

        //TODO: find a random point in childOneRoom, a random point in childTwoRoom
        //TODO: connect the two rooms via these two points, making either straight or bendy hallway
    }

    /**
     * Getter for x coord
     * @return
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for y coord
     * @return
     */
    public int getY() {
        return this.y;
    }

    /**
     * Getter for height of Cell
     * @return
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Getter for width of Cell
     * @return
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Getter for the first child (either left or bottom child)
     * @return
     */
    public Cell getChildOne() {
        return this.childOne;
    }

    /**
     * Getter for the second child (either right or top child)
     * @return
     */
    public Cell getChildTwo() {
        return this.childTwo;
    }

}
