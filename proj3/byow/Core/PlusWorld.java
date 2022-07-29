package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.Random;

/**
 * Draws a world that contains RANDOM tiles.
 */
public class PlusWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void fillFlower(TETile[][] tiles, int n) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if ((n <= i && i < 2 * n && j < 3 * n) || (n <= j && j < 2 * n && i < 3 * n)) {
                    tiles[i][j] = randomTile();
                } else {
                    tiles[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    /**
     * Picks a RANDOM tile with a 33% change of being
     * a wall, 33% chance of being a flower, and 33%
     * chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.GRASS;
            default:
                return Tileset.AVATAR;
        }
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(15, 15);

        TETile[][] flower = new TETile[15][15];
        fillFlower(flower, 3);

        ter.renderFrame(flower);
    }


}
