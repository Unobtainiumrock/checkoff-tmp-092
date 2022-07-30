package MemoryGame;

import byowTools.RandomUtils;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MemoryGame {
    /**
     * The characters we generate random Strings from.
     */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /**
     * Encouraging phrases. Used in the last section of the spec, 'Helpful UI'.
     */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};
    /**
     * The width of the window of this game.
     */
    private int width;
    /**
     * The height of the window of this game.
     */
    private int height;
    /**
     * The current round the user is on.
     */
    private int round;
    /**
     * The Random object used to randomly generate Strings.
     */
    private Random rand;
    /**
     * Whether or not the game is over.
     */
    private boolean gameOver;
    /**
     * Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'.
     */
    private boolean playerTurn;

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        this.rand = new Random(seed);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public String generateRandomString(int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            int idx = RandomUtils.uniform(new Random(i + 69), CHARACTERS.length);
            result += CHARACTERS[idx];
        }
        return result;
    }

    public void drawFrame(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.width / 2, this.height / 2, s);

        /* If the game is not over, display encouragement, and let the user know if they
         * should be typing their answer or watching for the next round. */
        if (!gameOver) {
            Font fontSmall = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(fontSmall);
            StdDraw.line(0, this.height - 2, this.width, this.height - 2);
            StdDraw.textLeft(0, this.height - 1, "Round: " + this.round);
            if (this.playerTurn) {
                StdDraw.text(this.width / 2, this.height - 1, "Type!");
            } else {
                StdDraw.text(this.width / 2, this.height - 1, "Watch!");
            }
            int randomEncouragement = RandomUtils.uniform(this.rand, ENCOURAGEMENT.length);
            StdDraw.textRight(this.width, this.height - 1, ENCOURAGEMENT[randomEncouragement]);
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        char[] characters = letters.toCharArray();
        for (char c : characters) {
            this.drawFrame(String.valueOf(c));
            StdDraw.pause(1000);
            this.drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        String res = "";

        while(!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(1000);
        }

        while (res.length() != n) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                res += c;
                this.drawFrame(res);
            }
        }
        return res;
    }

    public void startGame() {
        this.gameOver = false;
        this.round = 1;

        drawFrame("Welcome to my game! MUAHAHAHHAA");
        StdDraw.pause(1000);

        while (!gameOver) {
            this.drawFrame("Round: " + this.round);
            StdDraw.pause(1000);
            String s = this.generateRandomString(this.round);
            this.flashSequence(s);
            String t = this.solicitNCharsInput(s.length());

            if (t.equals(s)) {
                this.round++;
            } else {
                StdDraw.pause(1500);
                this.drawFrame("You lose.");
                this.gameOver = true;
            }
            StdDraw.pause(1000);
        }
    }

}
