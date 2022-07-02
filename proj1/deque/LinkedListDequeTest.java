package deque;


import jh61b.junit.In;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;



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
        assertTrue(new LinkedListDeque<>(2) == lld.addFirst(2));

        lld.addLast(10);
        assertEquals(2, lld.size());

        assertEquals(2, lld.removeFirst());
        assertEquals(10, lld.removeLast());

        assertTrue(new LinkedListDeque<>(14) == lld.addLast(14););

        lld.addFirst(7);
        lld.addFirst(3);
        lld.addFirst(15); //lld = 15, 3, 7, 14
        assertEquals(14, lld.removeLast()); //lld = 15, 3, 7
        assertEquals(15, lld.removeFirst()); //lld = 3, 7
        assertEquals(7, lld.removeLast());

        update();
//        lld = new LinkedListDeque<Integer>(); //do we need this?

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
        test1.addFirst(o);
        assertTrue("test1 should now contain object o", test1.contains(o));

        test1.removeFirst();
        assertTrue("test1 should now be empty", test1.isEmpty());

        Deque<Object> test2 = new LinkedListDeque<>();
        test2.addLast("hi");
        assertTrue("test2 should now contain string hi", test2.contains("hi"));

        test2.removeLast();
        assertTrue("test2 should now be empty", test2.isEmpty());


        Deque<Object> test3 = new LinkedListDeque<>();
        List <Integer> l = new ArrayList<>();
        test3.addFirst(l);
        assertTrue("test3 should now contain list l", test3.contains(l));


    }

    /** Make sure that removing from an empty LinkedListDeque returns null */
    @Test
    public void emptyNullReturn() {
        assertNull("removing from an empty LinkedListDeque should do nothing", lld.remove());
        update();
    }


    /** TODO: Write tests to ensure that your implementation works for really large
     * numbers of elements, and test any other methods you haven't yet tested!
     */



    @Test
    public void getTest() {
        //test for getting an index from an empty list should return null


        //test for getting an index that is out of bounds of the list returns null


        //test for getting the first index gets the first item, not sentinel
        //also test that deque is not altered after using get()


        //test for getting the last index gets the tail item, not sentinel
        //also test that deque is not altered after using get()


        //test getting an index somewhere within the list
        //also test that deque is not altered after using get()


    }

    @Test
    public void removeFirstTest() {
        //test that removing first from an empty list does nothing

        //test that removes first actually removes first, deque is altered

        //test that

    }

    @Test
    public void removeLastTest() {

    }

    @Test
    public void printDequeTest() {

    }

    @Test
    public void equalsTest() {


    }

    @Test
    public void sizeTest() {

    }

    public void update() {
        lld = new LinkedListDeque<Integer>();
    }

}
