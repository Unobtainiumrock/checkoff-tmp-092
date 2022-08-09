package byow.Core;

import java.util.HashMap;
import java.util.Map;


public class State implements Save {
    private Map<String, Board> snapshots = new HashMap<>();
    private String keyPresses = "";
    private Board[] lastShot = new Board[2];

    public State(Board b, String keyPresses) throws CloneNotSupportedException {
        Board shadow = ((Board) b.clone()).shadow();

        this.snapshots.put(keyPresses, b);
        this.snapshots.put("*" + keyPresses, shadow);
        this.lastShot[0] = b;
        this.lastShot[1] = shadow;
    }

    public Map<String, Board> getSnapshots() {
        return this.snapshots;
    }

    public String getKeyPresses() {
        return this.keyPresses;
    }

    public void setPresses(String s) {
        this.keyPresses += s;
    }

    public Board[] getLastShot() {
        return this.lastShot;
    }

    public void setLastShot(Board[] l) {
        this.lastShot = l;
    }
}
