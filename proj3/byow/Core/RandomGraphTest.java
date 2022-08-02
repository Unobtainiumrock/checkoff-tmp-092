package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.junit.Test;

import java.util.Random;

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
        TETile[][] world = new TETile[60][90];

        for (int x = 0; x < 60; x += 1) {
            for (int y = 0; y < 90; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Cell wo = new Cell();

        System.out.println(wo.getCells().size());
        System.out.println(wo.timesRan);

        wo.getCells()
                .stream().map((cell) -> cell.getRoom())
                .forEach(System.out::println);

//
//        for (int i = 0; i < 100; i++) {
//            System.out.println(inclusiveRandom(5, 7));
//        }


    }

}
