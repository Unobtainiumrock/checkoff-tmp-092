package byow.Core;

import byow.TileEngine.TERenderer;
import edu.princeton.cs.algs4.StdDraw;

public class Render {
    /**
     * Interact with inputString
     * @param b
     */
    public static void render(Board b) {
        TERenderer ter = new TERenderer();
        ter.initialize(b.getWidth() + 5, b.getHeight() + 5);
        ter.renderFrame(b.getBoardTiles());
        HUD displayHUD = new HUD(b);
        displayHUD.display();
    }

}
