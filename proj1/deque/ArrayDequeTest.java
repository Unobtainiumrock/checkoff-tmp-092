package deque;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    private static ArrayDeque<Integer> testArr;

    public static void reset(ArrayDeque<Integer> arr) {
        testArr = arr;
    }

    @Test
    public void constructorTests() {
        reset(new ArrayDeque<>());
        System.out.println("");
        System.out.println("Expect an empty constructor to initialize with a capacity of 8");
        int capacity = testArr.getCapacity(); // Includes the extra padding i.e. isn't representative of number of elements.
        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

        System.out.println("");
        System.out.println("Expect the Array to construct, when typed as having Integers and has a capacity of at least 8");
        reset(new ArrayDeque<Integer>(6));
        capacity = testArr.getCapacity();
        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

        System.out.println("");
        System.out.println("Expect the Array to construct, when typed as having Strings and has a capacity of at least 8");
        reset(new ArrayDeque<>(6));
        capacity = testArr.getCapacity();
        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

        System.out.println("");
        System.out.println("Expect the Array to construct, when typed as having Objects and has a capacity of at least 8");
        reset(new ArrayDeque<>(6));
        capacity = testArr.getCapacity();
        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

        System.out.println("");
        System.out.println("Expect the array's capacity to always be in powers of 2, if given non-power of two values in constructor.");

        for (int i = 0; i < 69; i++) {
            System.out.println("");
            reset(new ArrayDeque<>(i));
            capacity = testArr.getCapacity();
            System.out.println("Constructing with i: " + i + " Resulting Capacity is: " + capacity);
            System.out.println(Math.floor(Math.log(capacity) / Math.log(2)));
            assertTrue(Math.floor(Math.log(capacity) / Math.log(2)) == Math.log(capacity) / Math.log(2));
        }

        reset(new ArrayDeque<>());
        System.out.println("");
        System.out.println("Expect the 'size' of a newly constructed array to be zero.");
        System.out.println("The array's size is :" + testArr.size());
        assertEquals(testArr.size(), 0);

        System.out.println("");
        System.out.println("The F pointer used to indicate where to insert elements using addFirst should be initialized to floor((capacity - 1) / 2)");

        for (int i = 0; i < 69; i++) {
            System.out.println("");
            reset(new ArrayDeque<>(i));
            double F = testArr.getF();
            capacity = testArr.getCapacity();
            System.out.println("The F pointer is at: " + F + " For an array with capacity: " + capacity);
            assertTrue(F == Math.floor((capacity - 1) / 2));
        }

        System.out.println("");
        System.out.println("The L pointer used to indicate where to insert elements using addFirst should be initialized to floor((capacity + 1) / 2)");

        for (int i = 0; i < 69; i++) {
            System.out.println("");
            reset(new ArrayDeque<>(i));
            double L = testArr.getL();
            capacity = testArr.getCapacity();
            System.out.println("The L pointer is at: " + L + " For an array with capacity: " + capacity);
            assertTrue(L == Math.floor((capacity + 1) / 2));
        }

    }

    @Test
    public void addTests() {
        reset(new ArrayDeque<Integer>());

        System.out.println("");
        System.out.println("Expect array to be all zeroes when initialized");
        testArr.printArrayStats();
        assertTrue(testArr.size() == 0);

        System.out.println("Expect addFirst to prepend elements in the array");
        testArr.addFirst(1);
        testArr.printArrayStats();
        // 3, 1
        assertTrue(Integer.compare((Integer) testArr.get(3), 1) == 0);

        testArr.addFirst(2);
        testArr.printArrayStats();
        assertTrue(Integer.compare((Integer) testArr.get(2), 2) == 0);

        testArr.addFirst(3);
        testArr.printArrayStats();
        assertTrue(Integer.compare((Integer) testArr.get(1), 3) == 0);

        System.out.println("");
        System.out.println("The F pointer should cycle back to the end of the array when we reach out of bounds");
        testArr.addFirst(4);
        testArr.addFirst(5);
        testArr.printArrayStats();
        assertTrue(testArr.getF() == 6); // Accounts for moving to the next position to write at.

        System.out.println("");
        System.out.println("When capacity reaches 75%, the array should upsize.");
        testArr.addFirst(6);
        testArr.printArrayStats();
        assertTrue(testArr.getPercentageFull() == 0.375);

        System.out.println("");
        System.out.println("Elements should continue to be prepended properly for addFirst");
        testArr.addFirst(69);
        testArr.printArrayStats();
        assertTrue(Integer.compare((Integer) testArr.get(4), 69) == 0);

        System.out.println("");
        System.out.println("Add Last should properly append");
        testArr.addLast(144);
        testArr.printArrayStats();
        assertTrue(Integer.compare((Integer) testArr.get(11), 144) == 0);

        System.out.println("");
        System.out.println("Alternating adds should work");
        testArr.addLast(13);
        testArr.printArrayStats();
        testArr.addFirst(11);
        testArr.printArrayStats();
        assertTrue(Integer.compare((Integer) testArr.get(3), 11) == 0);
        assertTrue(Integer.compare((Integer) testArr.get(12), 13) == 0);

        System.out.println("");
        System.out.println("The array should continue to grow properly when hitting 75% capacity");
        testArr.addFirst(123);
        testArr.addFirst(456);
        testArr.printArrayStats();
        assertTrue(testArr.getCapacity() == 32);
        assertTrue(testArr.getPercentageFull() < 0.75);
    }

    @Test
    public void removeTests() {
        reset(new ArrayDeque<Integer>());

        for (int i = 0; i < 6; i++) {
            testArr.addFirst(i + 1);
        }

        testArr.printArrayStats();

        System.out.println("");
        System.out.println("Remove first should remove elements from the front");
        testArr.removeFirst();
        testArr.printArrayStats();
        testArr.removeFirst();
        testArr.printArrayStats();

        System.out.println("");
        System.out.println("Attempting to upsize after downsizing should work properly");
        testArr.addFirst(100);
        testArr.printArrayStats();
        testArr.addLast(200);
        testArr.printArrayStats();


        System.out.println("");
        System.out.println("Remove last should remove elements from the back");
        testArr.removeLast();
        testArr.printArrayStats();
        testArr.removeLast();
        testArr.printArrayStats();

        System.out.println("");
        System.out.println("Should still addLast correctly");
        testArr.addLast(69);
        testArr.printArrayStats();

        System.out.println("");
        System.out.println("Should still addLast correctly");
        testArr.addFirst(96);
        testArr.printArrayStats();


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
}
