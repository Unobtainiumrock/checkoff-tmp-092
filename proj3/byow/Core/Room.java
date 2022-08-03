package byow.Core;


import java.awt.*;

public class Room extends Rectangle {
    private Room neighbor;
    private boolean visited;
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
     * Getter
     *
     * @return
     */
    public boolean getVisited() {
        return this.visited;
    }

    /**
     * Setter
     *
     * @param visited
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Setter
     *
     */
    public void setNeighbor(Room neighbor) {
        this.neighbor = neighbor;
    }
}
