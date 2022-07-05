package deque;
import java.lang.Math;

public class Array {
    private double _F;
    private double _L;
    private int _size;
    private int _capacity;
    private int[] _stack;
    private int[] _items;

    public Array(int size) {
        this._items = createArr(size);
        double[] pointers = setFandL(this._items);
        this._F = pointers[0];
        this._L = pointers[1];
    }

    public  void main(String[] args) {
        int[] test = createArr(10);
        double[] pointers = setFandL(test);
        this._F = pointers[0];
        this._L = pointers[1];

        System.out.println("Test array length: " + test.length);
        printPointerPositions();
        System.out.println("Expect array to be all zeroes");

        addFirstPrinter(1);
        addFirst(test, 1);
        printArray(test);
        printPointerPositions();

        addFirstPrinter(2);
        addFirst(test, 2);
        printArray(test);
        printPointerPositions();
    }
    public static int[] createArr(int size) {
        if (size < 8 && size > 0) {
            return new int[8];
        }

        double k = log2(size);

        while (Math.floor(k) != k) {
            size++;
            k = log2(size);
        }
        return new int[size];
    }

    public static double log2(int x) {
        return Math.log(x) / Math.log(2);
    };

    public int[] addFirst(int[] arr, int val) {
        if ((arr.length + 1) >= 0.75) {
            grow(arr);
        }
        arr[(int) this._F] = val;
        this._F = (this._F - 1) % arr.length;
        this._size++;
        return arr;
    }

    public int[] addLast(int[] arr, int val) {
        if ((arr.length + 1) >= 0.75) {
            grow(arr);
        }
        arr[(int) this._L] = val;
        this._L = (this._L + 1) % arr.length;
        this._size++;
        return arr;
    }

    public static int[] grow(int[] arr){
        int[] doubled = new int[arr.length * 2];

        for (int i = 0; i < arr.length; i++) {
            doubled[i] = arr[i];
        }
        return doubled;
    }

//    public static int[] shrink() {
//
//    }

    public double[] setFandL(int[] arr) {
        double[] pointers = new double[2];

        pointers[0] = Math.floor((arr.length - 1) / 2); // F
        pointers[1] = Math.floor((arr.length + 1) / 2);// L
        return pointers;
    }

    public void addFirstPrinter(int k) {
        System.out.println("Adding value: " + k + " to the front");
    }

    public void printArray(int[] arr) {
        System.out.print("Values: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.print("\nIndices: ");

        for (int i = 0; i < arr.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }

    public void printPointerPositions() {
        System.out.println("Pointer positions updated!");
        System.out.println("Pointer F is at: " + this._F);
        System.out.println("Pointer L is at: " + this._L);
    }

    public void initStack(int k) {
        this._stack = new int[k];
    }

    public void updateStack(int val, int idx) {
        this._stack[idx] = val;
    }

    public void emptyStack() {
        this._stack = null;
    }

    public int[] getItems() {
        return this._items;
    }

    public double getF() {
        return this._F;
    }

    public double getL() {
        return this._L;
    }

    public void setF(int k) {
        this._F = (double) k;
    }

    public void setL(int k) {
        this._L = (double) k;
    }
}
