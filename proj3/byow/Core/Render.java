package byow.Core;

import byow.TileEngine.TERenderer;
import edu.princeton.cs.algs4.StdDraw;

public class Render {
//    TERenderer ter = new TERenderer();
//    public Render(Board b) {
//        this.ter.initialize(b.getWidth(), b.getHeight());
//    }

    /**
     * Interact with inputString
     * @param b
     */
    public static void render(Board b) {
        TERenderer ter = new TERenderer();
        ter.initialize(b.getWidth(), b.getHeight());
        ter.renderFrame(b.getBoardTiles());
    }

}
