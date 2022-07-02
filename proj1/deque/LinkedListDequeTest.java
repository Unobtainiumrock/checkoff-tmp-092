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
        lld.addFirst(2);
        assertTrue(Integer.compare(2, lld.removeFirst()) == 0);


        lld.addLast(10); //lld = 2, 10
        assertTrue(Integer.compare(10, lld.removeLast()) == 0);


        lld.addLast(20); //lld = 2, 10, 20
        lld.addFirst(7); //lld = 7, 2, 10, 20
        lld.addFirst(3); //lld = 3, 7, 2, 10, 20
        lld.removeLast(); //lld = 3, 7, 2, 10
        assertTrue(Integer.compare(10, lld.removeLast()) == 0);

        lld.removeFirst(); //lld = 7, 2, 10
        assertTrue(Integer.compare(7, lld.removeFirst()) == 0);

        lld.removeFirst(); //lld = 2, 10
        assertTrue(Integer.compare(2, lld.removeLast()) == 0);

        lld.removeLast(); // lld = 2
        assertTrue(Integer.compare(2, lld.removeLast()) == 0);
        assertTrue(Integer.compare(2, lld.removeLast()) == 0);

        lld.removeFirst(); // lld = 2
        assertNull(lld.removeFirst());
        assertNull(lld.removeLast());

        lld.addFirst(17);
        assertTrue(Integer.compare(17, lld.removeFirst()) == 0);
        assertTrue(Integer.compare(17, lld.removeLast()) == 0);

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
        assertEquals(null, lld.get(5));

        //test for getting an index that is out of bounds of the list returns null
        lld.addFirst(5);
        assertEquals(null, lld.get(10));

        //test for getting the first index gets the first item, not sentinel
        assertTrue(Integer.compare(5, lld.get(0)) == 0);
        //also test that deque is not altered after using get()
        assertTrue(Integer.compare(5, lld.removeFirst()) == 0);


        //test for getting the last index gets the tail item, not sentinel
        //also test that deque is not altered after using get()
        lld.addFirst(13); //lld: 13, 5
        lld.addLast(20); // lld: 13, 5, 20
        assertTrue(Integer.compare(20, lld.get(2)) == 0);
        assertTrue(Integer.compare(20, lld.removeLast()) == 0);

        //test getting an index somewhere within the list
        //also test that deque is not altered after using get()
        lld.addLast(3); // lld: 13, 5, 20, 3
        lld.addFirst(9); // lld: 9, 13, 5, 20, 3
        assertTrue(Integer.compare(13, lld.get(1)) == 0);

        lld.removeLast(); // lld: 9, 13, 5, 20
        assertTrue(Integer.compare(13, lld.get(1)) == 0);
        assertEquals(null, lld.get(4));

        update();

    }


    @Test
    public void printDequeTest() {

    }

    @Test
    public void equalsTest() {
        // same size, same element at same position for two LLDs
        Deque<Integer> testComparison = new LinkedListDeque<>();
        assertTrue(Integer.compare(lld.size(), testComparison.size()) == 0);
        assertEquals(null, lld.get(0));
        assertEquals(null, testComparison.get(0));
        assertTrue(lld.equals(testComparison) == true);

        lld.addFirst(14);
        assertTrue(Integer.compare(lld.size(), testComparison.size()) > 0);
        assertTrue(lld.equals(testComparison) == false);

        testComparison.addLast(14);
        assertTrue(Integer.compare(lld.size(), testComparison.size()) == 0);
        assertTrue(lld.equals(testComparison) == true);

        lld.addLast(15); //lld = 14, 15
        testComparison.addLast(16); //TC = 14, 16
        assertTrue(Integer.compare(lld.size(), testComparison.size()) == 0);
        assertTrue(lld.equals(testComparison) == false);

        lld.removeLast(); //lld = 14
        lld.addLast(16); //lld = 14, 16
        lld.addLast(17); //lld = 14, 16, 17
        testComparison.addLast(17); //TC = 14, 16, 17
        assertTrue(lld.equals(testComparison) == true);

        update();
    }

    @Test
    public void sizeTest() {
        assertEquals(0, lld.size());
        lld.addLast(5);
        assertEquals(1, lld.size());

        lld.addFirst(7);
        assertEquals(2, lld.size());

        lld.removeLast();
        lld.removeLast();
        assertEquals(0, lld.size());

        lld.addLast(14);
        lld.addLast(15);
        lld.addFirst(1);
        assertEquals(3, lld.size());

    }

    public void update() {
        lld = new LinkedListDeque<Integer>();
    }

}
