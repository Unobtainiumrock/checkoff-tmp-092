package byow.Core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Draw implements AntiAGMagicNumbers {
    public Draw() {

    }

    /**
     * Draws the frame, clears the screen each time it is called and draws on new things.
     *
     * @param s
     */
    public static void frame(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        /* Draw the title of the menu. */
        Font fontBig = new Font("Monaco", Font.BOLD, THIRSTY);
        StdDraw.setFont(fontBig);
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_SEVEN_FIVE, s);
    }
}
