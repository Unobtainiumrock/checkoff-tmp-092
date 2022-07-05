package deque;

import java.lang.Math;

public class ArrayDeque<T> implements Deque<T> {
    private double _F;
    private double _L;
    private int _size;
    private int _capacity;
    private Object[] _items;

    public ArrayDeque() {
        this._items = createArr(8);
        this._capacity = this._items.length;
        double[] pointers = setFandL(this._items);
        this._F = pointers[0];

        this._L = pointers[1];
    }

    public ArrayDeque(int size) {
        this._items = createArr(size);
        this._capacity = this._items.length;
        double[] pointers = setFandL(this._items);
        this._F = pointers[0];
        this._L = pointers[1];
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

    ;

//    public void addFirst(int val) {
//        System.out.println("Adding value: " + val + " to the front");
//        this._items[(int) this._F] = val;
//        double nextF = this.mod((int) (this._F - 1), this._capacity);
//        System.out.println("Moving pointer F from " + this._F + " to " + nextF);
//        this._size++;
//        this._F = nextF;
//        if (((double) (this._size) / this._capacity) >= 0.75) {
//            System.out.println("Array is growing. F and L positions will change");
//            this.grow();
//        }
//    }

//    public void addLast(int val) {
//        System.out.println("Adding value: " + val + " to the back");
//        this._items[(int) this._L] = val;
//        double nextL = this.mod((int) (this._L + 1), this._capacity);
//        System.out.println("Moving pointer L from " + this._L + " to " + nextL);
//        this._size++;
//        this._L = nextL;
//        if (((double) (this._size) / this._capacity) >= 0.75) {
//            this.grow();
//        }
//    }

    @Override
    public void addFirst(T val) {
        System.out.println("Adding value: " + val + " to the front");
        this._items[(int) this._F] = val;
        double nextF = this.mod((int) (this._F - 1), this._capacity);
        System.out.println("Moving pointer F from " + this._F + " to " + nextF);
        this._size++;
        this._F = nextF;
        if (((double) (this._size) / this._capacity) >= 0.75) {
            System.out.println("Array is growing. F and L positions will change");
            this.grow();
        }
    }

    @Override
    public void addLast(T val) {
        System.out.println("Adding value: " + val + " to the back");
        this._items[(int) this._L] = val;
        double nextL = this.mod((int) (this._L + 1), this._capacity);
        System.out.println("Moving pointer L from " + this._L + " to " + nextL);
        this._size++;
        this._L = nextL;
        if (((double) (this._size) / this._capacity) >= 0.75) {
            this.grow();
        }
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T thing = (T) this._items[(int) this._F + 1];
        System.out.println("Removing value: " + thing);
        this._items[(int) this._F + 1] = null;
        double nextF = this.mod((int) (this._F + 1), this._capacity);
        System.out.println("Moving pointer F from " + this._F + " to " + nextF);
        this._F = nextF;

        if (((double) (this._size - 1) / this._capacity) <= 0.25 && this._capacity >= 16) {
            System.out.println("Array is shrinking. F and L positions will change");
            this.shrink();
        }
        if (this.size() > 0) {
            this._size--;
        }
        return thing;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        T thing = (T) this._items[(int) this._L - 1];
        System.out.println("Removing value: " + thing);
        this._items[(int) this._L - 1] = null;
        double nextL = this.mod((int) (this._L - 1), this._capacity);
        System.out.println("Moving pointer L from " + this._L + " to " + nextL);
        this._L = nextL;

        if (((double) (this._size - 1) / this._capacity) <= 0.25 && this._capacity >= 16) {
            System.out.println("Array is shrinking. F and L positions will change");
            this.shrink();
        }
        if (this.size() > 0) {
            this._size--;
        }
        return thing;
    }

    @Override
    public boolean isEmpty() {
        return this._size == 0;
    }

    /**
     * Doubles the size of the array and evenly distributes half of the upsize padding
     * before F, and the other half of the upsize padding for after L.
     * The doubled array is then filled from
     * pre-pad null, to F, to L to post-pad null.
     */
    private void grow() {
        Object[] doubled = new Object[this._capacity * 2];
        double A = this._capacity / 2;
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

    private void shrink() {
        Object[] halved = new Object[this._capacity / 2];
        double A = this._capacity / 4;

        // grab elements from A to capacity - (A + 1)
        for (int i = 0; i < halved.length; i++) {
            halved[i] = this._items[(int) A + i];
        }
        // Cycle back by A - 1
        halved = cyclicShift(halved, (int) A - 1);
        this._items = halved;

        this._items = halved;
        this._capacity = halved.length;
        this._F = Math.floorMod((int) ((int) this._F - (2 * A) + 1), halved.length);
        this._L = Math.floorMod((int) ((int) this._L - (2 * A) + 1), halved.length);
    }

    // Positive corresponds to shifting cyclically left
    // Negative corresponds to shifting cyclically right
    private Object[] cyclicShift(Object[] reference, int by) {
        Object[] shifted = new Object[reference.length];
        int len = shifted.length;

        for (int i = 0; i < len; i++) {
            int a = i + (len + by);
            int b = len;
            shifted[i] = reference[Math.floorMod(a, b)];
        }

        return shifted;
    }

    private double[] setFandL(Object[] arr) {
        double[] pointers = new double[2];

        pointers[0] = Math.floor((arr.length - 1) / 2); // F
        pointers[1] = Math.floor((arr.length + 1) / 2);// L
        return pointers;
    }

    @Override
    public T get(int k) {
        return (T) this._items[k];
//        if (k > this._capacity - 1 || this._size == 0) {
//            return null;
//        }
//        double L = this._L;
//        double F = this._F;
//        if (F < L) {
//            return (T) this._items[cyclicShift(( F + k + 1), this._capacity)];
////
//        }
//        return (T) this._items[cyclicShift((L + k + 1), this._capacity)];

    }


    @Override
    public void printDeque() {
        // Needs to print from F to L
        // note: May break with the new implementation we have.
        double a = this._F;
        double b = this._L;
        int c = this._capacity;
        int F = Math.floorMod((int) a + 1, c);
        int L = Math.floorMod((int) b - 1, c);

        for (int i = F; i <= L; i = Math.floorMod(i + 1, c)) {
            Object val = this.get(i);
            if (val != null) {
                System.out.print(val + " ");
            }
        }
        System.out.println("");
    }

    /**
     * Used for testing purposes.
     *
     * @return the total capacity of the _items
     */
    //used to be public int... changed to satisfy AG API
    int getCapacity() {
        return this._capacity;
    }

    //used to be public double...changed to satisfy AG API
    double getPercentageFull() {
        return ((double) this._size / (double) this._capacity);
    }

    @Override
    public int size() {
        return this._size;
    }

    private int mod(int a, int b) {
        return ((a % b + b) % b);
    }

    void printArrayStats() {
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
