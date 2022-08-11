package byow.Core;

import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

/**
 * Only displayed after menu screen(s) have been shown.
 */
public class HUD implements AntiAGMagicNumbers {
    public static String avatarName = "";
    private World world;

    public HUD(World w) {
        this.world = w;
    }

        private void display() {
        /* always need these two lines or else renders a black screen for some reason */
        StdDraw.setXscale(ZERO, MENU_WIDTH);
        StdDraw.setYscale(ZERO, MENU_HEIGHT);

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontSmall = new Font("Monaco", Font.BOLD, TWENTY);
        StdDraw.setFont(fontSmall);

        StdDraw.line(ZERO, MENU_HEIGHT - TWO, MENU_WIDTH, MENU_HEIGHT - TWO);

        /* Displays the tile type when mouse hovers over it at top left of screen. */
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();

        TETile tileType = this.world.getBoard().getTile(mouseX, mouseY).getCurrent();

        StdDraw.textLeft(ZERO, MENU_HEIGHT - ONE, tileType.description());

        /* Displays player's chosen avatar name at the top center of the screen. */
        if (avatarName.length() >= 1) {
            StdDraw.text(MENU_WIDTH / TWO, ZERO, this.avatarName);
        }

        StdDraw.show();
    }

    public static void setAvatarName() {
        avatarName = KeyboardInputSource.solicitInput(FIVE);
    }

    public static boolean hasAvatarName() {
        return avatarName.length() >= 1;
    }
}
