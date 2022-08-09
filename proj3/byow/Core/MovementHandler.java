package byow.Core;

import byow.TileEngine.Tileset;

public class MovementHandler {
    private State state;

    public MovementHandler(State s) {
        this.state = s;
    }

    public void acceptDispatch(String s) throws CloneNotSupportedException {
        this.createSnapshots(this.state.getSnapshots().get(s), s);
    }

    private void createSnapshots(Board previousState, String s) throws CloneNotSupportedException {
        Board cp = (Board) previousState.clone();
        int oldX = previousState.getAvatarPosition()[0];
        int oldY = previousState.getAvatarPosition()[1];
        int newX = 0, newY = 0;

        cp.setTile(oldX, oldY, Tileset.WATER);

        switch(s) {
            case "w":
                newY = oldY - 1;
                break;
            case "s":
                newY = oldY + 1;
                break;
            case "a":
                newX = oldX - 1;
                break;
            case "d":
                newX = oldX + 1;
                break;
        }

        if (!cp.getTile(newX, newY).equals(Tileset.WALL)) {
            cp.setTile(newX, newY, Tileset.AVATAR);
        }

        Board shadowBoard = ((Board) cp.clone()).shadow();

        Board[] shots = new Board[2];
        shots[0] = cp;
        shots[1] = shadowBoard;

        this.state.setLastShot(shots);
        this.state.getSnapshots().put(s, cp);
        this.state.getSnapshots().put("*" + s, shadowBoard);
    }

}
