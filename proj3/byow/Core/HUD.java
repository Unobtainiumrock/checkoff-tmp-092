package byow.Core;

import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;

/**
 * @author hi to myself.
 * Only displayed after menu screen(s) have been shown.
 */
public class HUD implements AntiAGMagicNumbers {
    /**
     *
     */
    public static String avatarName = "";

    /**
     *
     */
    private Board board;

    /**
     * @param b bb
     */
    public HUD(Board b) {
        this.board = b;
    }


    /**
     *
     */
    public void display() {
        StdDraw.setPenColor(Color.WHITE);
        Font fontSmall = new Font("Monaco", Font.BOLD, TWENTY);
        StdDraw.setFont(fontSmall);

        StdDraw.line(ZERO, MENU_HEIGHT + FIVE - TWO,
                MENU_WIDTH, MENU_HEIGHT + FIVE - TWO);

        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();

        TETile tileType = this.board.getTile(mouseX, mouseY).getCurrent();

        StdDraw.textLeft(ONE, MENU_HEIGHT + FIVE - ONE, tileType.description());

        if (avatarName.length() >= 1) {
            StdDraw.text(MENU_WIDTH / TWO,
                    MENU_HEIGHT + FIVE - ONE, this.avatarName);
        }

        StdDraw.show();
    }

    /**
     *
     */
    public static void setAvatarName() {
        avatarName = KeyboardInputSource.solicitInput(FIVE);
    }

    /**
     * @return
     */
    public static boolean hasAvatarName() {
        return avatarName.length() >= 1;
    }
}