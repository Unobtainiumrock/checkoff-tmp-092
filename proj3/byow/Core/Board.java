package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Board implements Cloneable, Save, AntiAGMagicNumbers {
    private TileWrapper[][] boardTiles;
    private int[] avatarPosition = new int[2];
    private int width, height;
    private boolean shadowing = false;

    public Board(int w, int h) {
        this.width = w;
        this.height = h;
        this.boardTiles = new TileWrapper[w][h];
    }

    public void shadow(boolean shadow) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (shadow) {
                    boardTiles[x][y].turnOff();
                    this.shadowing = true;
                } else {
                    boardTiles[x][y].turnOn();
                    this.shadowing = false;
                }
            }
        }
    }
    public boolean isShadowing() {
        return this.shadowing;
    }

    public void setShadowing(boolean s) {
        this.shadowing = s;
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

    public TileWrapper getTile(int x, int y) {
        return this.boardTiles[x][y];
    }

    public void setTile(int x, int y, TETile t) {
        this.boardTiles[x][y] = new TileWrapper(t);
    }

    public TETile[][] getBoardTiles() {
        TETile[][] out = new TETile[MENU_WIDTH][MENU_HEIGHT];

        for (int y = 0; y < MENU_HEIGHT; y++) {
            for (int x = 0; x < MENU_WIDTH; x++) {
                out[x][y] = this.boardTiles[x][y].getCurrent();
            }
        }
        return out;
    }

    public void setBoardTiles(TileWrapper[][] b) {
        this.boardTiles = b;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board cp = (Board) super.clone();

        cp.setBoardTiles(new TileWrapper[this.width][this.height]);

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                cp.setTile(x, y, this.boardTiles[x][y].getCurrent());
            }
        }

        return cp;
    }
}
