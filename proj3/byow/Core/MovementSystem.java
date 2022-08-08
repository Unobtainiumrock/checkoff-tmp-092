package byow.Core;
import java.util.List;
import java.util.Stack;

public class MovementSystem {
    private List<Character> upDown = new Stack<>();
    private List<Character> leftRight = new Stack<>();

    public MovementSystem() {

    }

//    private

    // Simulate key presses for now


//    up -> push or pop, depending on which is presses first
//    down -> complement of whatever role "up takes"
//
//    pop until no more to pop, then swap roles of each
//
//    left -> push or pop, depending on which is presses first
//    right -> complement of whatever role "up takes"
//    pop until no more to pop, then swap roles each?

    public void executeMovement(char c) {
        Character.toString(c).toLowerCase();
    }

    private void swapRoles() {}
}
