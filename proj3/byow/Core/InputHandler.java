package byow.Core;

public class InputHandler {
    private Game game;

    public InputHandler(Game g) {
        this.game = g;
    }

    private void dispatchKeyPress(char c) throws CloneNotSupportedException {
        String keyPress = Character.toString(c).toLowerCase();

        game.getMovementHandler().acceptDispatch(keyPress);
//        game.getState().getSnapshots().acceptDispatch(keyPress); // change this tmrw
    }

}
