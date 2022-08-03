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
        ter.initialize(15, 10);
        TETile[][] world = new TETile[15][10];

//         Fill the world with empty spaces
        for (int y = 0; y < 10; y += 1) {
            for (int x = 0; x < 15; x += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Cell wo = new Cell();

//        ArrayList<Cell> cells = (ArrayList<Cell>) wo.getCells()
//
        wo.getCells()
                .stream()
                .map((cell) -> cell.getRoom())
                .filter((room) -> room != null)
                .forEach((room) -> {
                    // top/bottom
                    int x = (int) room.getX();
                    int y = (int) room.getY();
                    // left/right
                    world[x - 2][y] = Tileset.FLOOR;
                });

        ter.renderFrame(world);

//        for (Rectangle r : rs) {
//            int x = (int) r.getX(); // Top left corner
//            int y = (int) r.getY(); // top left corner
//            int width = (int) r.getWidth();
//            int height = (int) r.getHeight();
//            // x + width
//            // y + height
//
//            // Top/Bottom
//            for (int i = 0; i < width; i++) {
//                world[x + i][y] = Tileset.FLOOR;
//                world[x + i][y + height - 1] = Tileset.FLOOR;
//            }


//             Left

//             Right

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
        ter.initialize(15, 10);
        TETile[][] world = new TETile[15][10];

        // Fill the world with empty spaces
        for (int y = 0; y < 10; y++) {
            for (int x = 0; y < 15; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        Cell wo = new Cell();
    }

    @Test
    public void cellRoomTest() {
    }

}
