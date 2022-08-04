package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.junit.Test;

import java.util.*;

import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class RandomGraphTest {
    public static void main(String[] args) {
        //69696969
        Random seedR = new Random(123);
        World w = new World(seedR, 90, 60);
        w.render();
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
