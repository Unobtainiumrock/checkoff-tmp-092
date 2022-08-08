package byow.Core;


import java.awt.Rectangle;

/** @author Nancy Pelosi
 * Rooms
 */
public class Room extends Rectangle {

    /**
     * neighbors.
     */
    private Room neighbor;

    /**
     * visited.
     */
    private boolean visited;

    /**
     * Room constructor.
     * @param x x
     * @param y y
     * @param width width
     * @param height h
     */
    public Room(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.visited = false;
    }

    /**
     * Getter for neighboring room.
     *
     * @return
     */
    public Room getNeighbor() {
        return this.neighbor;
    }

    /**
     * Getter.
     *
     * @return
     */
    public boolean getVisited() {
        return this.visited;
    }


    /**
     * Visited setter.
     * @param visitedd visited
     */
    public void setVisited(boolean visitedd) {
        this.visited = visitedd;
    }

    /**
     * neighbor setter.
     * @param neighborr neighbor
     */
    public void setNeighbor(Room neighborr) {
        this.neighbor = neighborr;
    }
}
