package byow.Core;

public class Game {
    private MovementHandler movementHandler;
    private Board board;
    private State state;

    public Game(MovementHandler m, Board b, State s) {
     this.movementHandler = m;
     this.board = b;
     this.state = s;
    }

    public MovementHandler getMovementHandler() {
        return this.movementHandler;
    }

    public Board getBoard() {
        return this.board;
    }

    public State getState() {
        return this.state;
    }
}
