package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import static byow.Core.HelperFunctions.*;
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

        /**
         * Creates our default board with size x = 90, y = 60
         */
//        Cell wo = new Cell(69, 0, 0, width, height);
        Cell wo = new Cell();

        //TODO move out whatever pieces of code are being killed when the stream is closed.
        /**
         * Dynamically fills our board by visiting each cell, grabbing their rooms,
         * and then filtering off null values for rooms that don't contain cells.S
         */
        ArrayList<Room> filteredRooms = (ArrayList<Room>) wo.getCells()
                .stream()
                .map((cell) -> cell.getRoom())
                .filter((room) -> room != null)
                .collect(Collectors.toList());


        filteredRooms
                .forEach((room) -> {
                    // top/bottom
                    int x = (int) room.getX();
                    int y = (int) room.getY();

                    //TODO top/bottom
                    for (int i = 0; i < room.getWidth(); i++) {
                        world[x + i][y] = Tileset.FLOWER;
                        world[x + i][y + (int) room.getHeight() - 1] = Tileset.FLOWER;
                    }
                    // it

//                    TODO left/right
                    for (int i = 0; i < room.getHeight(); i++) {
                        world[x][y + i] = Tileset.FLOWER;
                        world[x + (int) room.getWidth() - 1][y + i] =  Tileset.FLOWER;
                    }
                });

        filteredRooms
                .forEach((room) -> {
                    /**
                     * Room
                     */
                    int u = (int) room.getX();
                    int v = (int) room.getY();
                    int[] p1 = {u, v};

                    /**
                     * Neighbor
                     */
                    int g = (int) room.getNeighbor().getX();
                    int h = (int) room.getNeighbor().getY();
                    int[] p2 = {g, h};

                    /**
                     * Corner (Potential hallway intersection)
                     */
                    int[] p3 = {u, h};

                    // Draw p1 -> p3
                    for (int i = 0; i < Math.abs(v - h); i++) {
                        if (v < h) {
                            world[u][v + i] = Tileset.FLOWER;
                        } else {
                            world[u][v - i] = Tileset.FLOWER;
                        }
                    }

//                    System.out.println(room.getX());
                    // Draw p3 -> p2
                    for (int i = 0; i < Math.abs(g - u); i++) {
                        if (g > u) {
                            world[u + i][h] = Tileset.FLOWER;
                        } else {
                            world[u - i][h] = Tileset.FLOWER;
                        }

                    }

                    room.setVisited(true);
                    room.getNeighbor().setVisited(true);

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
