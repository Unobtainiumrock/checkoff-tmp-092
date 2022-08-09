package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

// User input handler -> movementhandler -> board -> snapshot

public class Board implements Cloneable, Save {
    private TETile[][] boardTiles;
    private int[] avatarPosition = new int[2];
    private int width, height;

    public Board(int w, int h) {
        this.width = w;
        this.height = h;
        this.boardTiles = new TETile[w][h];
    }

    public Board shadow() {
        // Only want top, left, right, bottom, and diagonals
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {

                boolean notAtAvatar = x != this.avatarPosition[0] && y != this.avatarPosition[1];
                boolean A = x != this.avatarPosition[0] && y != this.avatarPosition[1] - 1; /*Up*/
                boolean B = x != this.avatarPosition[0] && y != this.avatarPosition[1] + 1; /*Down*/
                boolean C = x != this.avatarPosition[0] - 1 && y != this.avatarPosition[1]; /*Left*/
                boolean D = x != this.avatarPosition[0] + 1 && y != this.avatarPosition[1]; /*Right*/
                boolean E = x != this.avatarPosition[0] - 1 && y != this.avatarPosition[1] - 1; /*Top-Left*/
                boolean F = x != this.avatarPosition[0] + 1 && y != this.avatarPosition[1] - 1; /*Top-Right*/
                boolean G = x != this.avatarPosition[0] - 1 && y != this.avatarPosition[1] + 1; /*Bottom-Left*/
                boolean H = x != this.avatarPosition[0] + 1 && y != this.avatarPosition[1] + 1; /*Bottom-Right*/

                if (A && B && C && D && E && F && G && H && notAtAvatar) {
                    try {
                        this.setTile(x, y, Tileset.NOTHING);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        /*haha*/
                    }
                }
            }
        }

        return this;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int[] getAvatarPosition() {
        return this.avatarPosition;
    }

    public void setAvatarPosition(int x, int y) {
        this.avatarPosition[0] = x;
        this.avatarPosition[1] = y;
    }

    public TETile getTile(int x, int y) {
        return this.boardTiles[x][y];
    }

    public void setTile(int x, int y, TETile t) {
        this.boardTiles[x][y] = t;
    }

    public TETile[][] getBoardTiles() {
        return this.boardTiles;
    }

    public void setBoardTiles(TETile[][] b) {
        this.boardTiles = b;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board cp = (Board) super.clone();

        cp.setBoardTiles(new TETile[this.width][this.height]);

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                cp.setTile(x, y, this.boardTiles[x][y]);
            }
        }

        return cp;
    }
}
