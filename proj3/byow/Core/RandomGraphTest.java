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
        Cell wo = new Cell();


        //TODO move out whatever pieces of code are being killed when the stream is closed.
        /**
         * Dynamically fills our board by visiting each cell, grabbing their rooms,
         * and then filtering off null values for rooms that don't contain cells.S
         */
        ArrayList<Rectangle> filteredRooms = (ArrayList<Rectangle>) wo.getCells()
                .stream()
                .map((cell) -> cell.getRoom())
                .filter((room) -> room != null)
                .collect(Collectors.toList());


        filteredRooms
                .forEach((room) -> {
                    System.out.println(room.getX());
                    // top/bottom
                    int x = (int) room.getX();
                    int y = (int) room.getY();
//                    world[x][y] = Tileset.FLOOR;

                    //TODO top/bottom
                    for (int i = 0; i < room.getWidth(); i++) {
                        world[x + i][y] = Tileset.FLOOR;
//                        world[x][y + (int) room.getHeight() - 1] = Tileset.FLOOR;
                    }
                });

        /**
         * Renders our world after populatinig it with the rooms.
         */
        ter.renderFrame(world);

    }

    @Test
    public void cellRoomTest() {
    }

}
