package deque;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {


    private static ArrayDeque<Integer> ad = new ArrayDeque<>();

    public static void reset(ArrayDeque<Integer> arr) {
        ad = arr;
    }

    @Test
    public void constructorTests() {
        reset(new ArrayDeque<>());
//        System.out.println("");
//        System.out.println("Expect an empty constructor to initialize with a capacity of 8");
        int capacity = ad.getCapacity(); // Includes the extra padding i.e. isn't representative of number of elements.
//        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

//        System.out.println("");
//        System.out.println("Expect the Array to construct, when typed as having Integers and has a capacity of at least 8");
        reset(new ArrayDeque<Integer>(6));
        capacity = ad.getCapacity();
//        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

//        System.out.println("");
//        System.out.println("Expect the Array to construct, when typed as having Strings and has a capacity of at least 8");
        reset(new ArrayDeque<>(6));
        capacity = ad.getCapacity();
//        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

//        System.out.println("");
//        System.out.println("Expect the Array to construct, when typed as having Objects and has a capacity of at least 8");
        reset(new ArrayDeque<>(6));
        capacity = ad.getCapacity();
//        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

//        System.out.println("");
//        System.out.println("Expect the array's capacity to always be in powers of 2, if given non-power of two values in constructor.");

        for (int i = 0; i < 69; i++) {
            System.out.println("");
            reset(new ArrayDeque<>(i));
            capacity = ad.getCapacity();
//            System.out.println("Constructing with i: " + i + " Resulting Capacity is: " + capacity);
//            System.out.println(Math.floor(Math.log(capacity) / Math.log(2)));
            assertTrue(Math.floor(Math.log(capacity) / Math.log(2)) == Math.log(capacity) / Math.log(2));
        }

        reset(new ArrayDeque<>());
//        System.out.println("");
//        System.out.println("Expect the 'size' of a newly constructed array to be zero.");
//        System.out.println("The array's size is :" + testArr.size());
        assertEquals(ad.size(), 0);

//        System.out.println("");
//        System.out.println("The F pointer used to indicate where to insert elements using addFirst should be initialized to floor((capacity - 1) / 2)");

//        for (int i = 0; i < 69; i++) {
//            System.out.println("");
//            reset(new ArrayDeque<>(i));
//            double F = testArr.getF();
//            capacity = testArr.getCapacity();
//            System.out.println("The F pointer is at: " + F + " For an array with capacity: " + capacity);
//            assertTrue(F == Math.floor((capacity - 1) / 2));
//        }

//        System.out.println("");
//        System.out.println("The L pointer used to indicate where to insert elements using addFirst should be initialized to floor((capacity + 1) / 2)");

//        for (int i = 0; i < 69; i++) {
//            System.out.println("");
//            reset(new ArrayDeque<>(i));
//            double L = testArr.getL();
//            capacity = testArr.getCapacity();
//            System.out.println("The L pointer is at: " + L + " For an array with capacity: " + capacity);
//            assertTrue(L == Math.floor((capacity + 1) / 2));
//        }

    }

    @Test
    public void addTests() {
        reset(new ArrayDeque<Integer>());

//        System.out.println("");
//        System.out.println("Expect array to be all zeroes when initialized");
        ad.printArrayStats();
        assertTrue(ad.size() == 0);

//        System.out.println("Expect addFirst to prepend elements in the array");
        ad.addFirst(1);
        ad.printArrayStats();
        // 3, 1
        assertTrue(Integer.compare((Integer) ad.get(3), 1) == 0);

        ad.addFirst(2);
        ad.printArrayStats();
        assertTrue(Integer.compare((Integer) ad.get(2), 2) == 0);

        ad.addFirst(3);
        ad.printArrayStats();
        assertTrue(Integer.compare((Integer) ad.get(1), 3) == 0);

//        System.out.println("");
//        System.out.println("The F pointer should cycle back to the end of the array when we reach out of bounds");
//        testArr.addFirst(4);
//        testArr.addFirst(5);
//        testArr.printArrayStats();
//        assertTrue(testArr.getF() == 6); // Accounts for moving to the next position to write at.

//        System.out.println("");
//        System.out.println("When capacity reaches 75%, the array should upsize.");
        ad.addFirst(6);
        ad.printArrayStats();
        assertTrue(ad.getPercentageFull() == 0.375);

//        System.out.println("");
//        System.out.println("Elements should continue to be prepended properly for addFirst");
        ad.addFirst(69);
        ad.printArrayStats();
        assertTrue(Integer.compare((Integer) ad.get(4), 69) == 0);

//        System.out.println("");
//        System.out.println("Add Last should properly append");
        ad.addLast(144);
        ad.printArrayStats();
        assertTrue(Integer.compare((Integer) ad.get(11), 144) == 0);

//        System.out.println("");
//        System.out.println("Alternating adds should work");
        ad.addLast(13);
        ad.printArrayStats();
        ad.addFirst(11);
        ad.printArrayStats();
        assertTrue(Integer.compare((Integer) ad.get(3), 11) == 0);
        assertTrue(Integer.compare((Integer) ad.get(12), 13) == 0);

//        System.out.println("");
//        System.out.println("The array should continue to grow properly when hitting 75% capacity");
        ad.addFirst(123);
        ad.addFirst(456);
        ad.printArrayStats();
        assertTrue(ad.getCapacity() == 32);
        assertTrue(ad.getPercentageFull() < 0.75);
    }

    @Test
    public void removeTests() {
        reset(new ArrayDeque<Integer>());

        for (int i = 0; i < 6; i++) {
            ad.addFirst(i + 1);
        }

        ad.printArrayStats();

//        System.out.println("");
//        System.out.println("Remove first should remove elements from the front");
        ad.removeFirst();
        ad.printArrayStats();
        ad.removeFirst();
        ad.printArrayStats();

//        System.out.println("");
//        System.out.println("Attempting to upsize after downsizing should work properly");
        ad.addFirst(100);
        ad.printArrayStats();
        ad.addLast(200);
        ad.printArrayStats();


//        System.out.println("");
//        System.out.println("Remove last should remove elements from the back");
        ad.removeLast();
        ad.printArrayStats();
        ad.removeLast();
        ad.printArrayStats();

//        System.out.println("");
//        System.out.println("Should still addLast correctly");
        ad.addLast(69);
        ad.printArrayStats();

//        System.out.println("");
//        System.out.println("Should still addLast correctly");
        ad.addFirst(96);
        ad.printArrayStats();


    }


    @Test
    public void getTest() {
//        ad.addLast(0);
//        ad.addLast(1);
//        ad.addLast(2);
//        ad.addLast(3);
//        ad.addLast(4);
//        ad.printArrayStats();
//        ad.addLast(5);
//        ad.printArrayStats();
//        ad.addLast(6);
//        ad.addLast(7);
//        ad.addLast(8);
//        ad.addLast(9);
//        ad.addLast(10);
//        ad.printArrayStats();
//        ad.addLast(11);
//        ad.printArrayStats();
//        ad.addLast(12);
//        ad.printArrayStats();
//        ad.addLast(13);
//        ad.addLast(14);
//        ad.addLast(15);
//        ad.addLast(16);
//        ad.addLast(17);
//        ad.addLast(18);
//        ad.addLast(19);
//        ad.addLast(20);
//        ad.addLast(21);
//        ad.addLast(22);
//        ad.addLast(23);
//        ad.addLast(24);
//        ad.printArrayStats();
//
//        assertTrue(Integer.compare(2, ad.get(2)) == 0);
//        assertTrue(Integer.compare(12, ad.get(12)) == 0);
//        assertTrue(Integer.compare(21, ad.get(21)) == 0);
//        //test for getting an index from an empty list should return null
        assertEquals(null, ad.get(5));
//        //F = index 5, L = index 1
//
//        //test for getting an index that is out of bounds of the list returns null
        ad.addFirst(5);//lld = 5, F = index 4, L = index 1
        ad.printArrayStats();
        assertEquals(null, ad.get(10));
//
//        //test for getting the first index gets the first item, not sentinel
        assertTrue(Integer.compare(5, ad.get(0)) == 0);
//        //also test that deque is not altered after using get()
        assertTrue(Integer.compare(5, ad.removeFirst()) == 0); //lld = empty, F = index 5, L = index 1
//
//
//        //test for getting the last index gets the tail item, not sentinel
//        //also test that deque is not altered after using get()
        ad.addFirst(5); //lld = 5, F = index 4, L = index 1
        ad.printArrayStats();
        ad.addFirst(13); //lld: 13, 5, F = index 3, L = index 1
        ad.printArrayStats();
        ad.addLast(20); // lld: 13, 5, 20
        ad.printArrayStats();
        assertTrue(Integer.compare(20, ad.get(2)) == 0);
//
        assertTrue(Integer.compare(20, ad.removeLast()) == 0); //lld = 13, 5
//
//        //test getting an index somewhere within the list
//        //also test that deque is not altered after using get()
        ad.addLast(3); // lld: 13, 5, 3
        ad.printArrayStats();
        ad.addFirst(9); // lld: 9, 13, 5, 3
        ad.printArrayStats();
        ad.addFirst(8); //ad: 8, 9, 13, 5, 3
        ad.printArrayStats();
        ad.addLast(2); //ad: 8, 9, 13, 5, 3, 2
        ad.printArrayStats();
        ad.addLast(1); //ad: 8, 9, 13, 5, 3, 2, 1
        ad.printArrayStats();
        ad.addLast(1); //ad: 8, 9, 13, 5, 3, 2, 1, 1
        ad.printArrayStats();
        assertTrue(Integer.compare(9, ad.get(1)) == 0);
        assertTrue(Integer.compare(5, ad.get(3)) == 0);
        assertTrue(Integer.compare(8, ad.get(0)) == 0);
        assertNull(ad.get(10));
//
        ad.removeLast(); // ad: 8, 9, 13, 5, 3, 2, 1
        ad.printArrayStats();
        assertTrue(Integer.compare(9, ad.get(1)) == 0);
        assertTrue(Integer.compare(1, ad.get(6)) == 0);
        assertTrue(Integer.compare(2, ad.get(5)) == 0);
        assertEquals(null, ad.get(7));
        ad.printArrayStats();
        ad.removeFirst(); // ad: 9, 13, 5, 3, 2, 1
        assertTrue(Integer.compare(2, ad.get(4)) == 0);
        assertTrue(Integer.compare(9, ad.get(0)) == 0);
        ad.printArrayStats();
        ad.removeFirst(); // ad: 13, 5, 3, 2, 1
        assertTrue(Integer.compare(13, ad.get(0)) == 0);
        assertNull(ad.get(5));
        ad.printArrayStats();
        ad.removeLast(); // ad: 13, 5, 3, 2
        ad.printArrayStats();
        assertNull(ad.get(4));
        assertTrue(Integer.compare(2, ad.get(3)) == 0);
        ad.removeLast(); // ad: 13, 5, 3
        ad.removeLast(); // ad: 13, 5
        ad.removeLast(); // ad: 13
        ad.removeLast(); // ad:
        assertNull(ad.get(0));
        ad.addLast(7); // ad: 7
        assertTrue(Integer.compare(7, ad.get(0)) == 0);
        assertNull(ad.get(1));
        ad.addLast(14); // ad: 7, 14
        ad.addFirst(0); // ad: 0, 7, 14
        assertTrue(Integer.compare(0, ad.get(0)) == 0);
        assertTrue(Integer.compare(14, ad.get(2)) == 0);
    }



    @Test
    public void printDequeTest() {
        ad.addFirst(5);
        ad.addFirst(10);
        ad.addFirst(3);
        ad.addLast(14);
        ad.addLast(20); // lld = 3, 10, 5, 14, 20
        ad.printDeque();
    }



}


    // To do:
    // Make get() and all of the other @Override methods work
    //
    // Make sure that LLD and AD can communicate seamlessly between one another
    // there as a test case where you have to do..

    // Deque<Integer> = new ArrayDeque<>();
    // Deque<Integer> = new LinkedListDeque<>();

    // they should behave the same, regardless of the underlying data structure.


    // note: Get is going to be the most difficult, since the array's are not in proper order.
    // This can be resolved by playing with the cyclicShift method
    // Positive corresponds to shifting cyclically left
    // Negative corresponds to shifting cyclically right
    //

