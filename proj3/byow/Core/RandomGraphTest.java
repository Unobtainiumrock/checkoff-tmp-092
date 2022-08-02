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
                .stream().map((cell) -> cell.getRoom())
                .collect(Collectors.toList());

        for (Rectangle r : rs) {
            System.out.println(r);
            int x = (int) r.getX();
            int y = (int) r.getY();

            world[x][y] = Tileset.FLOWER;
        }

        ter.renderFrame(world);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        TETile[][] world = new TETile[90][60];

//         Fill the world with empty spaces
        for (int x = 0; x < 60; x += 1) {
            for (int y = 0; y < 90; y += 1) {
                world[y][x] = Tileset.FLOWER;
            }
        }

//        Cell wo = new Cell();
//
//
//        ArrayList<Rectangle> rs = (ArrayList<Rectangle>) wo.getCells()
//                .stream().map((cell) -> cell.getRoom())
//                .collect(Collectors.toList());
//
//        for (Rectangle r : rs) {
//            System.out.println(r);
//            int x = (int) r.getX();
//            int y = (int) r.getY();
//
//            world[x][y] = Tileset.FLOOR;
//        }

        world[0][0] = Tileset.FLOWER;

        ter.renderFrame(world);
    }

}
