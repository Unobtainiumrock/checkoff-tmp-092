package byow.Core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import byow.InputDemo.*;

public class Menu implements AntiAGMagicNumbers {

    public static void displayLandingMenu() {
        StdDraw.setCanvasSize(MENU_WIDTH * 10, MENU_HEIGHT * 10);
        StdDraw.setXscale(ZERO, MENU_WIDTH);
        StdDraw.setYscale(ZERO, MENU_HEIGHT);

        Draw.frame("THE DROWNING GAME");

        /* Draw the menu options. */
        Font fontSmall = new Font("Monaco", Font.ITALIC, TWENTY);
        StdDraw.setFont(fontSmall);
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * HALF, "New Game (N)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_FOUR, "Load Game (L)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_THREE, "Quit (Q)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_TWO, "Give Avatar Name (M)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * 0.1, "Change Outfit (V)");
        // StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_TWO, "Set Avatar Appearance (Z)");
    }

    public static void displayB() {
        StdDraw.setXscale(ZERO, MENU_WIDTH);
        StdDraw.setYscale(ZERO, MENU_HEIGHT);

        Draw.frame("THE DROWNING GAME");

        Font fontSmall = new Font("Monaco", Font.ITALIC, TWENTY);
        StdDraw.setFont(fontSmall);
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * HALF, "New Game (N)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_FOUR, "Load Game (L)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_THREE, "Quit (Q)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * 0.1, "Change Outfit (V)");
    }

    public static void displayC() {
        StdDraw.setXscale(ZERO, MENU_WIDTH);
        StdDraw.setYscale(ZERO, MENU_HEIGHT);

        Draw.frame("THE DROWNING GAME");

        Font fontSmall = new Font("Monaco", Font.ITALIC, TWENTY);
        StdDraw.setFont(fontSmall);
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * HALF, "New Game (N)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_FOUR, "Load Game (L)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_TWO, "Give Avatar Name (M)");
        StdDraw.text(MENU_WIDTH * HALF, MENU_HEIGHT * POINT_THREE, "Quit (Q)");
    }
}
