package deque;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> ad = new ArrayDeque<>();

    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        assertTrue("A newly initialized LLDeque should be empty", ad.isEmpty());
        ad.addFirst(0);

        assertFalse("lld1 should now contain 1 item", ad.isEmpty());

        update(); //Assigns ad equal to a new, clean ArrayDeque!


    }

    /** Adds an item, removes an item, and ensures that lld is empty afterwards. */
    @Test
    public void addRemoveTest() {


        update();
    }

    /** Make sure that removing from an empty LinkedListDeque does nothing */
    @Test
    public void removeEmptyTest() {


        update();
    }

    /** Make sure your LinkedListDeque also works on non-Integer types */
    @Test
    public void multipleParamsTest() {


        update();
    }

    /** Make sure that removing from an empty LinkedListDeque returns null */
    @Test
    public void emptyNullReturn() {


        update();
    }


    /** TODO: Write tests to ensure that your implementation works for really large
     * numbers of elements, and test any other methods you haven't yet tested!
     */


    @Test
    public void getTest() {


        update();
    }


    @Test
    public void printDequeTest() {


        update();
    }

    @Test
    public void equalsTest() {


        update();
    }

    @Test
    public void sizeTest() {


        update();
    }

    public void update() {
        ad = new ArrayDeque<Integer>();
    }

}

