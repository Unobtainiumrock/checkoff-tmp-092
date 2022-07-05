package deque;
import java.lang.Math;

public class ArrayMaker<E> {
    private double _F;
    private double _L;
    private int _size;
    private int _capacity;
    private Object[] _items;

    public ArrayMaker() {
        this._items = createArr(8);
        this._capacity = this._items.length;
        double[] pointers = setFandL(this._items);
        this._F = pointers[0];
        this._L = pointers[1];
    }

    public ArrayMaker(int size) {
        this._items = createArr(size);
        this._capacity = this._items.length;
        double[] pointers = setFandL(this._items);
        this._F = pointers[0];
        this._L = pointers[1];
    }

    /**
     * Used by the Array constructor. It creates an array and bumps up its size to
     * a nice power of two to make it friendly to doubling and handling the position of F and L pointers.
     */
    private Object[] createArr(int capacity) {
        if (capacity < 8 || capacity == 0) {
            return new Object[8];
        }

        double k = log2(capacity);

        while (Math.floor(k) != k) {
            capacity++;
            k = log2(capacity);
        }
        return new Object[capacity];
    }

    /**
     * Used by createArr to initialize "weird" numbered arrays to
     * have capacities that are powers of 2.
     *
     * @param x
     * @return
     */
    private static double log2(int x) {
        return Math.log(x) / Math.log(2);
    };

    public void addFirst(int val) {
        System.out.println("Adding value: " + val + " to the front");
        this._items[(int) this._F] = val;
        double nextF = this.mod((int) (this._F - 1), this._capacity);
        System.out.println("Moving pointer F from " + this._F + " to " + nextF);
        this._size++;
        this._F = nextF;
        if (((double) (this._size) / this._capacity) >= 0.75) {
            this.grow();
        }
    }

    public int[] addLast(int[] arr, int val) {
        if ((arr.length + 1) >= 0.75) {
            this.grow();
        }
        arr[(int) this._L] = val;
        this._L = (this._L + 1) % arr.length;
        this._size++;
        return arr;
    }

    /**
     * Doubles the size of the array and evenly distributes half of the upsize padding
     * before F, and the other half of the upsize padding for after L.
     * The doubled array is then filled from
     * pre-pad null, to F, to L to post-pad null.
     */
    public void grow(){
        Object[] doubled = new Object[this._capacity * 2];
        double A = 0.5 * this._capacity;

//        this.setF((int) A + 1);

        // fill in doubled by starting at the end of the padding before F ,
        // reading from F + 1 until L - 1 on the _items array
        // example _items = [5, 4, 2, 1, 3, L, F, 6]
        //         doubled = [0, 0, 0, 0, F, 6, 5, 4, 2, 1, 3, L, 0, 0, 0, 0]
        int k = 1;

        for (int i = (int) A + 1; i < doubled.length - (A + 1); i++) {
            doubled[i] = this._items[mod((int) this._F + k, this._capacity)];
            k++;
        }

        this._items = doubled;
        this._capacity = doubled.length;
        this._F = A;
        this._L = doubled.length - (A + 1);
    }

//    public static int[] shrink() {
//
//    }

    public double[] setFandL(Object[] arr) {
        double[] pointers = new double[2];

        pointers[0] = Math.floor((arr.length - 1) / 2); // F
        pointers[1] = Math.floor((arr.length + 1) / 2);// L
        return pointers;
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

    public E get(int k) {
        return (E) this._items[k];
    }

    /**
     * Used for testing purposes.
     * @return the total capacity of the _items
     */
    public int getCapacity() {
        return this._capacity;
    }

    public int size() {
        return this._size;
    }

    private int mod(int a, int b) {
        return ((a % b + b) % b);
    }

    public void printArrayStats() {

        System.out.println("Values (including 'gaps'): ");
        for (int i = 0; i < this._capacity; i++) {
            System.out.print(this._items[i] + " ");
        }

        System.out.println("\nIndices: ");

        for (int i = 0; i < this._capacity; i++) {
            System.out.print(i + " ");
        }

        System.out.println("");

        System.out.println("Pointer F is at: " + this._F);
        System.out.println("Pointer L is at: " + this._L);
        System.out.println("Size: " + this._size);
        System.out.println("Capacity: " + this._capacity);
        System.out.println("Percentage of array filled: " + (100 * ((double) this._size / (double) this._capacity)) + "%");

        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
}
