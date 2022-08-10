package byow.Core;

import byow.TileEngine.TERenderer;

public class Render {
    public static void render(Board b) {
        TERenderer ter = new TERenderer();
        ter.initialize(b.getWidth(), b.getHeight());
        ter.renderFrame(b.getBoardTiles());
    }
}
