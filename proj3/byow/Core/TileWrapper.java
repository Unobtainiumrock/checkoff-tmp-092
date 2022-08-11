package byow.Core;

import byow.TileEngine.*;

public class TileWrapper implements Save {
    private TETile current;
    private TETile tile;
    private boolean lit = true;

    public TileWrapper(TETile t) {
        this.tile = t;
        this.current = t;
    }

    private void flip() {
        this.lit = !this.lit;
        this.current = this.lit ? tile : Tileset.NOTHING;
    }

    public void turnOn() {
        this.flip();
        this.current = tile;
    }

    public void turnOff() {
        this.flip();
        this.current = Tileset.NOTHING;
    }

    public boolean isOff() {
        return this.lit != true;
    }

    public boolean isOn() {
        return this.lit == true;
    }

    public TETile getCurrent() {
        return this.current;
    }
}
