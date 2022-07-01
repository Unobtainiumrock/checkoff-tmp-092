package deque;

<<<<<<< HEAD
import jh61b.junit.In;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;



/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. Please do not import java.util.Deque here!*/

    public static Deque<Integer> lld = new LinkedListDeque<>();

    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

		assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
		lld.addFirst(0);

        assertFalse("lld1 should now contain 1 item", lld.isEmpty());

        update(); //Assigns lld equal to a new, clean LinkedListDeque!

    }

    /** Adds an item, removes an item, and ensures that lld is empty afterwards. */
    @Test
    public void addRemoveTest() {
        lld.add(2);
        lld.remove(2);
        assertTrue("LLDeque should be empty now", lld.isEmpty());

        update();
        lld = new LinkedListDeque<Integer>(); //do we need this?

    }

    /** Make sure that removing from an empty LinkedListDeque does nothing */
    @Test
    public void removeEmptyTest() {
        assertNull("removing from an empty LinkedListDeque should do nothing", lld.removeFirst());
        update();
    }

    /** Make sure your LinkedListDeque also works on non-Integer types */
    @Test
    public void multipleParamsTest() {
        Deque<Object> test1 = new LinkedListDeque<>();
        Object o = new Object();
        test1.add(o);
        assertTrue("test1 should now contain object o", test1.contains(o));

        test1.remove(o);
        assertTrue("test1 should now be empty", test1.isEmpty());

        Deque<Object> test2 = new LinkedListDeque<>();
        test2.add("hi");
        assertTrue("test2 should now contain string hi", test1.contains("hi"));

        Deque<Object> test3 = new LinkedListDeque<>();
        List <Integer> l = new ArrayList<>();
        test3.add(l);
        assertTrue("test3 should now contain list l", test1.contains(l));


    }

    }
    /** Make sure that removing from an empty LinkedListDeque returns null */
    @Test
    public void emptyNullReturn() {
<<<<<<< HEAD
        lld.remove();

        update();
=======

>>>>>>> 759b4c4e2d4cd1397a7d85cefc5a88b9f440e6bf
    }
    /** TODO: Write tests to ensure that your implementation works for really large
     * numbers of elements, and test any other methods you haven't yet tested!
     */

<<<<<<< HEAD

    public void update() {
        lld = new LinkedListDeque<Integer>();
    }

=======
>>>>>>> 759b4c4e2d4cd1397a7d85cefc5a88b9f440e6bf
}
