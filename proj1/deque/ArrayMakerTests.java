package deque;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayMakerTests {

    private static ArrayMaker<Object> testArr;

    public static void main(String[] args) {
        Object[] reference = {0, 0, 0, 0, 0, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0, 0};

        Object[] halved = new Object[reference.length / 2];
        double A = 4;

        // grab elements from A to capacity - (A + 1)
        for (int i = 0; i < halved.length; i ++) {
            halved[i] = reference[(int) A + i];
        }
        // Cycle back by A - 1
        halved = ArrayMaker.cyclicShift(halved, (int)A - 1);

        for (Object i : halved) {
            System.out.print(i + " ");
        }

    }


    public static void reset(ArrayMaker arr) {
        testArr = arr;
    }

    @Test
    public void constructorTests() {
        reset(new ArrayMaker<>());
        System.out.println("");
        System.out.println("Expect an empty constructor to initialize with a capacity of 8");
        int capacity = testArr.getCapacity(); // Includes the extra padding i.e. isn't representative of number of elements.
        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

        System.out.println("");
        System.out.println("Expect the Array to construct, when typed as having Integers and has a capacity of at least 8");
        reset(new ArrayMaker<Integer>(6));
        capacity = testArr.getCapacity();
        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

        System.out.println("");
        System.out.println("Expect the Array to construct, when typed as having Strings and has a capacity of at least 8");
        reset(new ArrayMaker<String>(6));
        capacity = testArr.getCapacity();
        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

        System.out.println("");
        System.out.println("Expect the Array to construct, when typed as having Objects and has a capacity of at least 8");
        reset(new ArrayMaker<Object>(6));
        capacity = testArr.getCapacity();
        System.out.println("Capacity is: " + capacity);
        assertTrue(capacity == 8);

        System.out.println("");
        System.out.println("Expect the array's capacity to always be in powers of 2, if given non-power of two values in constructor.");

        for (int i = 0; i < 69; i++) {
            System.out.println("");
            reset(new ArrayMaker<Object>(i));
            capacity = testArr.getCapacity();
            System.out.println("Constructing with i: " + i + " Resulting Capacity is: " + capacity);
            System.out.println(Math.floor(Math.log(capacity) / Math.log(2)));
            assertTrue(Math.floor(Math.log(capacity) / Math.log(2)) == Math.log(capacity) / Math.log(2));
        }

        reset(new ArrayMaker<>());
        System.out.println("");
        System.out.println("Expect the 'size' of a newly constructed array to be zero.");
        System.out.println("The array's size is :" + testArr.size());
        assertEquals(testArr.size(), 0);

        System.out.println("");
        System.out.println("The F pointer used to indicate where to insert elements using addFirst should be initialized to floor((capacity - 1) / 2)");

        for (int i = 0; i < 69; i++) {
            System.out.println("");
            reset(new ArrayMaker<Object>(i));
            double F = testArr.getF();
            capacity = testArr.getCapacity();
            System.out.println("The F pointer is at: " + F + " For an array with capacity: " + capacity);
            assertTrue(F == Math.floor((capacity - 1) / 2));
        }

        System.out.println("");
        System.out.println("The L pointer used to indicate where to insert elements using addFirst should be initialized to floor((capacity + 1) / 2)");

        for (int i = 0; i < 69; i++) {
            System.out.println("");
            reset(new ArrayMaker<Object>(i));
            double L = testArr.getL();
            capacity = testArr.getCapacity();
            System.out.println("The L pointer is at: " + L + " For an array with capacity: " + capacity);
            assertTrue(L == Math.floor((capacity + 1) / 2));
        }

    }

    @Test
    public void addFirstTests() {
        reset(new ArrayMaker<Integer>());

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

    }
}
