package deque;
import jh61b.junit.In;
import org.junit.Test;
import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

//  public static void main(String[] args) {
//        List<Integer> test = new ArrayList<>();
//
//        test.add(1);
//        test.add(2);
//        test.add(3);
//
//        Deque<Integer> dal = new ArrayDeque<>(test);
//
//        Arrays.asList(((ArrayDeque<Integer>) dal)._items)
//                .forEach((e) -> {
//                    System.out.println(e);
//                });
//
//        System.out.printf("My size is: %s\n", dal.size());
//        System.out.printf("Capacity is: %s\n", ((ArrayDeque<Integer>) dal)._capacity);
//
//        dal.printDeque();
//
////        ArrayDeque<Integer> testTwo = new ArrayDeque<>(17);
////        System.out.println(testTwo.size());
////        System.out.println(testTwo.getCapacity());
//
//  }

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> ad = new ArrayDeque<>();

    /** Adds an item, removes an item, and ensures that lld is empty afterwards. */
    @Test
    public void addRemoveTest() {
        ad.addFirst(2);
//        System.out.printf("%s\n", ad.removeFirst());
        assertTrue(Integer.compare(2, ad.removeFirst()) == 0);


        ad.addLast(10); //lld = 10
        assertTrue(Integer.compare(10, ad.removeFirst()) == 0);


        ad.addLast(20); //lld = 20
//        System.out.println(lld);
        ad.addFirst(7); //lld = 7, 20
//        System.out.println(lld);//lld = 7, 20
        ad.addFirst(3); //lld = 3, 7, 20
        System.out.print(ad);
        ad.removeLast(); //lld = 3, 7
        assertTrue(Integer.compare(7, ad.removeLast()) == 0); //lld = 3
//
        ad.removeFirst(); //lld = empty
        assertNull(ad.removeFirst());
//
        ad.addFirst(17); //lld = 17
        assertTrue(Integer.compare(17, ad.removeFirst()) == 0);

        update();

    }

    /** Make sure that removing from an empty ArrayDeque does nothing */
    @Test
    public void removeEmptyTest() {
        assertNull("removing from an empty ArrayDeque should do nothing", ad.removeFirst());
        update();
    }

    /** Make sure your ArrayDeque also works on non-Integer types */
    @Test
    public void multipleParamsTest() {
        Deque<Object> test1 = new ArrayDeque<>();
        Object o = new Object();
        test1.addFirst(o);
//        assertTrue("test1 should now contain object o", test1.contains(o));

        test1.removeFirst();
        assertTrue("test1 should now be empty", test1.isEmpty());

        Deque<Object> test2 = new ArrayDeque<>();
        test2.addLast("hi");
//        assertTrue("test2 should now contain string hi", test2.contains("hi"));

        test2.removeLast();
        assertTrue("test2 should now be empty", test2.isEmpty());

        update();

    }

    /** Make sure that removing from an empty ArrayDeque returns null */
//    @Test
//    public void emptyNullReturn() {
//        assertNull("removing from an empty ArrayDeque should do nothing", ad.remove());
//        update();
//    }


    /** TODO: Write tests to ensure that your implementation works for really large
     * numbers of elements, and test any other methods you haven't yet tested!
     */


    @Test
    public void getTest() {
        //test for getting an index from an empty list should return null
        assertEquals(null, ad.get(5));
        //F = index 5, L = index 1

        //test for getting an index that is out of bounds of the list returns null
        ad.addFirst(5); //lld = 5, F = index 4, L = index 1
        assertEquals(null, ad.get(10));

        //test for getting the first index gets the first item, not sentinel
        assertTrue(Integer.compare(5, ad.get(0)) == 0);
        //also test that deque is not altered after using get()
        assertTrue(Integer.compare(5, ad.removeFirst()) == 0); //lld = empty, F = index 5, L = index 1


        //test for getting the last index gets the tail item, not sentinel
        //also test that deque is not altered after using get()
        ad.addFirst(5); //lld = 5, F = index 4, L = index 1
        ad.addFirst(13); //lld: 13, 5, F = index 3, L = index 1
        ad.addLast(20); // lld: 13, 5, 20
        assertTrue(Integer.compare(20, ad.get(2)) == 0);

        assertTrue(Integer.compare(20, ad.removeLast()) == 0); //lld = 13, 5

        //test getting an index somewhere within the list
        //also test that deque is not altered after using get()
        ad.addLast(3); // lld: 13, 5, 3
        ad.addFirst(9); // lld: 9, 13, 5, 3
        ad.addFirst(8); //ad: 8, 9, 13, 5, 3
        ad.addLast(2); //ad: 8, 9, 13, 5, 3, 2
        ad.addLast(1); //ad: 8, 9, 13, 5, 3, 2, 1
        ad.addLast(1); //ad: 8, 9, 13, 5, 3, 2, 1, 1
        assertTrue(Integer.compare(13, ad.get(1)) == 0);

        ad.removeLast(); // lld: 9, 13, 5
        assertTrue(Integer.compare(13, ad.get(1)) == 0);
        assertEquals(null, ad.get(3));
        System.out.println(ad); //lld: 9, 13, 5

        update();

    }



    @Test
    public void printDequeTest() {
        ad.addFirst(5);
        ad.addFirst(10);
        ad.addFirst(3);
        ad.addLast(14);
        ad.addLast(20); // lld = 3, 10, 5, 14, 20
        ad.printDeque();

        update();
    }

    @Test
    public void equalsTest() {
        // same size, same element at same position for two LLDs
        Deque<Integer> testComparison = new ArrayDeque<>();
        assertTrue(Integer.compare(ad.size(), testComparison.size()) == 0);
        assertEquals(null, ad.get(0));
        assertEquals(null, testComparison.get(0));
        ad.equals(testComparison);
        assertTrue(ad.equals(testComparison) == true);
        ad.addFirst(14); //lld = 14
        assertTrue(Integer.compare(ad.size(), testComparison.size()) > 0);
        assertTrue(ad.equals(testComparison) == false);

        testComparison.addLast(14); //TC = 14
        assertTrue(Integer.compare(ad.size(), testComparison.size()) == 0);
        assertTrue(ad.equals(testComparison) == true);

        ad.addLast(15); //lld = 14, 15
        testComparison.addLast(16); //TC = 14, 16
        assertTrue(Integer.compare(ad.size(), testComparison.size()) == 0);
        assertTrue(ad.equals(testComparison) == false);

        ad.removeLast(); //lld = 14
        ad.addLast(16); //lld = 14, 16
        ad.addLast(17); //lld = 14, 16, 17
        testComparison.addLast(17); //TC = 14, 16, 17
        assertTrue(ad.equals(testComparison) == true);

        update();
    }

    @Test
    public void sizeTest() {
        assertEquals(0, ad.size());
        ad.addLast(5);
        assertEquals(1, ad.size());

        ad.addFirst(7);
        assertEquals(2, ad.size());

        ad.removeLast();
        ad.removeLast();
        assertEquals(0, ad.size());

        ad.addLast(14);
        ad.addLast(15);
        ad.addFirst(1);
        assertEquals(3, ad.size());

        update();

    }

    public void update() {
        ad = new ArrayDeque<>();
    }


}

