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
        // No collisions
        Random r = new Random();
        int roomSize = 10;

        for (int i = 0; i < 100; i++) {
            System.out.println(r.nextInt(roomSize - 1) + 1);
        }

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
        TETile[][] world = new TETile[90][60];

        // Fill the world with empty spaces
        for (int x = 0; x < 60; x += 1) {
            for (int y = 0; y < 90; y += 1) {
                world[y][x] = Tileset.NOTHING;
            }
        }

        Cell wo = new Cell();

//        wo.getCells()
//                .stream().map((cell) -> cell.getRoom())
//                .forEach((room) -> {
//                    int x = (int) room.getX();
//                    int y = (int) room.getY();
//
////                    System.out.println(x);
//
//                    world[x][y] = Tileset.FLOWER;
//                });

        ArrayList<Rectangle> rs = (ArrayList<Rectangle>) wo.getCells()
                .stream().map((cell) -> cell.getRoom(cell))
                .collect(Collectors.toList());

        for (Rectangle r : rs) {
            System.out.println(r);
            int x = (int) r.getX();
            int y = (int) r.getY();

            world[x][y] = Tileset.FLOWER;
        }

        ter.renderFrame(world);
    }

    @Test
    public void cellRoomTest() {
        Cell wo = new Cell();
        wo.getCells()
                .stream().map((cell) -> cell.getRoom(cell))
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(15, 10);
        TETile[][] world = new TETile[15][10];

//         Fill the world with empty spaces
        for (int y = 0; y < 10; y += 1) {
            for (int x = 0; x < 15; x += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Cell wo = new Cell();
//
//        wo.getCells()
//                .stream().map((cell) -> cell.getRoom(cell))
//                .forEach(System.out::println);


        ArrayList<Rectangle> rs = (ArrayList<Rectangle>) wo.getCells()
                .stream().map((cell) -> cell.getRoom(cell))
                .collect(Collectors.toList());

        for (Rectangle r : rs) {
            int x = (int) r.getX(); // Top left corner
            int y = (int) r.getY(); // top left corner
            int width = (int) r.getWidth();
            int height = (int) r.getHeight();
            // x + width
            // y + height

            // Top/Bottom
            for (int i = 0; i < width; i++) {
                world[x + i][y] = Tileset.FLOOR;
                world[x + i][y + height - 1] = Tileset.FLOOR;
            }


//             Left

//             Right

        }
        ter.renderFrame(world);
    }
}
