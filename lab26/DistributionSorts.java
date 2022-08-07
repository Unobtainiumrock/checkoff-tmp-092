import java.util.Arrays;

public class DistributionSorts {

    /* Destructively sorts ARR using counting sort. Assumes that ARR contains
       only 0, 1, ..., 9. */
    public static void countingSort(int[] arr) {
        int[] res = new int[arr.length];
        int[] count = new int[10];
        int[] startingPoint = new int[10];

        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }

        int sum = 0;

        for (int i = 0; i < count.length; i++) {
            startingPoint[i] = sum;
            sum += count[i];
        }


        for (int i = 0; i < arr.length; i++) {
            res[startingPoint[arr[i]]] = arr[i];
            startingPoint[arr[i]]++;
        }

        for (int i =0; i < res.length; i++) {
            arr[i] = res[i];
        }

    }

    public static int[] countingSortTwo(int[] arr, int[] p) {
        int[] count = new int[10];
        int[] startingPoint = new int[10];

        for (int i = 0; i < p.length; i++) {
            count[p[i]]++;
        }

        int sum = 0;

        for (int i = 0; i < count.length; i++) {
            startingPoint[i] = sum + count[i];
            sum += count[i];
        }

        return startingPoint;
    }

    /* Destructively sorts ARR using LSD radix sort. */
    public static void lsdRadixSort(int[] arr) {
        // if given, [66, 12, 13, 14, 15, 21], then 2
        int maxDigit = mostDigitsIn(arr);
        // iterate twice, performing countingSortOnDigit twice, therefore we need to
        // make sure that within countingSortOnDigit that we are iterating the entirety of arr
        //
//        for (int d = 0; d < maxDigit; d++) {
//            countingSortOnDigit(arr, d);
//        }
        countingSortOnDigit(arr, maxDigit);
    }

    /* A helper method for radix sort. Modifies ARR to be sorted according to
       DIGIT-th digit. When DIGIT is equal to 0, sort the numbers by the
       rightmost digit of each number. */
    private static void countingSortOnDigit(int[] arr, int digit) {
        int[] digitLopper = new int[arr.length];
        int[] res = new int[arr.length];
        int[] T;

        // Make a copy
        for (int i = 0; i < arr.length; i++) {
            digitLopper[i] = arr[i];
        }

        // Grab current last digits and update for next digit beheading.
        for (; digit > 0; digit--)  {
            int[] p = new int[digitLopper.length];

            for (int i = 0; i < digitLopper.length; i++) {
                p[i] = digitLopper[i] % 10;
                digitLopper[i] = digitLopper[i] / 10;
            }

            // Sort based on the current digit-lopping.
            T = countingSortTwo(arr, p);

            // Mutate the thing.
            for (int i = 0; i < arr.length; i++) {
                res[T[p[i]]] = arr[i];
                T[p[i]]++;
            }
        }

        // Overwrite
        for (int i = 0; i < res.length; i++) {
            arr[i] = res[i];
        }
    }

    /* Returns the largest number of digits that any integer in ARR has. */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }

    /* Returns a random integer between 0 and 9999. */
    private static int randomInt() {
        return (int) (10000 * Math.random());
    }

    /* Returns a random integer between 0 and 9. */
    private static int randomDigit() {
        return (int) (10 * Math.random());
    }

    private static void runCountingSort(int len) {
        int[] arr1 = new int[len];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr1));
        countingSort(arr1);
        if (arr1 != null) {
            System.out.println("Should be sorted: " + Arrays.toString(arr1));
        }
    }

    private static void runLSDRadixSort(int len) {
        int[] arr2 = new int[len];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomInt();
        }
        System.out.println("Original array: " + Arrays.toString(arr2));
        lsdRadixSort(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));

    }

    public static void main(String[] args) {
//        runCountingSort(20);
        runLSDRadixSort(3);
//        runLSDRadixSort(30);
    }
}