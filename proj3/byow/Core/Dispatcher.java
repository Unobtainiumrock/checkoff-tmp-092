package byow.Core;

public class Dispatcher {
    public static void dispatch(State previousState, String keyPresses) throws CloneNotSupportedException {
            MovementHandler.acceptDispatch(previousState, keyPresses);
    }
}