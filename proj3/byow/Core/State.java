package byow.Core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class State implements Save {
    private Map<String, Board> snapshots = new HashMap<>();
    private String keyPresses = "";
    private Set<Map<Integer, Integer>> wallSet;
    private Set<Map<Integer, Integer>> floorSet;
    private Board[] lastShot = new Board[2];

    /**
     * Used when creating a new state from interractWithInputString.
     * @param b A new empty board
     * @param movements Sequence of key presses
     * @throws CloneNotSupportedException
     */
    public State(Board b, Set<Map<Integer, Integer>> w,
                 Set<Map<Integer, Integer>> f,
                 String movements) throws CloneNotSupportedException {

        Board shadow = ((Board) b.clone()).shadow();
        this.wallSet = w;
        this.floorSet = f;
        this.keyPresses = movements;

        this.lastShot[0] = b;
        this.lastShot[1] = shadow;

        Dispatcher.dispatch(this, movements
        );
    }

    public String getKeyPresses() {
        return this.keyPresses;
    }

    public void setPresses(String s) {
        this.keyPresses += s;
    }

    public Set<Map<Integer, Integer>> getWallSet() {
        return this.wallSet;
    }

    public Set<Map<Integer, Integer>> getFloorSet() {
        return this.floorSet;
    }

    public Map<String, Board> getSnapshots() {
        return this.snapshots;
    }

    public void snap(Map<String, Board> shot) {
        String k = shot.keySet().iterator().next();
        Board v = shot.values().iterator().next();
        this.snapshots.put(k, v);
    }

    public Board[] getLastShot() {
        return this.lastShot;
    }

    public void setLastShot(Board[] l) {
        this.lastShot = l;
    }
}
