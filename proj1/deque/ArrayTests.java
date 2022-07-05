package deque;

public class ArrayTests {

    public static void main(String[] args) {
        Array testArr = new Array(10);
        int[] test = testArr.getItems();


        System.out.println("Test array length: " + test.length);
        testArr.printPointerPositions();
        System.out.println("Expect array to be all zeroes");

        testArr.addFirstPrinter(1);
        testArr.addFirst(test, 1);
        testArr.printArray(test);
        testArr.printPointerPositions();

        testArr.addFirstPrinter(2);
        testArr.addFirst(test, 2);
        testArr.printArray(test);
        testArr.printPointerPositions();
    }
}
