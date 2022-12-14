package deque;

import java.lang.Math;

public class ArrayDeque<T> implements Deque<T> {
    private double _F;
    private double _L;
    private int _size;
    private int _capacity;
    private Object[] _items;
    private int p = 1;

    public ArrayDeque() {
        this._items = createArr(8);
        this._capacity = this._items.length;
        double[] pointers = setFandL(this._items);
        this._F = pointers[0];

        this._L = pointers[1];
    }

//    public ArrayDeque(int size) {
//        this._items = createArr(size);
//        this._capacity = this._items.length;
//        double[] pointers = setFandL(this._items);
//        this._F = pointers[0];
//        this._L = pointers[1];
//    }

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

    @Override
    public void addFirst(T val) {
//        System.out.println("Adding value: " + val + " to the front");
        this._items[(int) this._F] = val;
        double nextF = Math.floorMod((int) (this._F - 1), this._capacity);
//        System.out.println("Moving pointer F from " + this._F + " to " + nextF);
        this._size++;
        this._F = nextF;
        if (((double) (this._size) / this._capacity) >= 0.75) {
//            System.out.println("Array is growing. F and L positions will change");
            this.grow();
        }
    }

    @Override
    public void addLast(T val) {
//        System.out.println("Adding value: " + val + " to the back");
        this._items[(int) this._L] = val;
        double nextL = Math.floorMod((int) (this._L + 1), this._capacity);
//        System.out.println("Moving pointer L from " + this._L + " to " + nextL);
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
        T thing = (T) this._items[Math.floorMod(((int) this._F + 1), this._capacity)];
//        System.out.println("Removing value: " + thing);
        this._items[Math.floorMod(((int) this._F + 1), this._capacity)] = null;
        double nextF = Math.floorMod((int) (this._F + 1), this._capacity);
//        System.out.println("Moving pointer F from " + this._F + " to " + nextF);
        this._F = nextF;

        if (((double) (this._size - 1) / this._capacity) <= 0.25 && this._capacity == 16) {
            Object[] newArr = new Object[8];
            newArr[0] = null;
            newArr[7] = null;

            int j = (int) this._F + 1;
            int k = (int) this._L - 1;
            int i = 0;
            while (j <= k) {
                newArr[i] = this._items[j];
                i++;
                j++;
            }
            this._F = 1;
            this._L = 6;
            this._items = cyclicShift(newArr, -2);
            newArr = null;
            this._capacity = 8;
        } else if (((double) (this._size - 1) / this._capacity) <= 0.25 && this._capacity >= 16) {
//            System.out.println("Array is shrinking. F and L positions will change");
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
        T thing = (T) this._items[Math.floorMod(((int) this._L - 1), this._capacity)];
//        System.out.println("Removing value: " + thing);
        this._items[Math.floorMod(((int) this._L - 1), this._capacity)] = null;
        double nextL = Math.floorMod((int) (this._L - 1), this._capacity);
//        System.out.println("Moving pointer L from " + this._L + " to " + nextL);
        this._L = nextL;

        if (((double) (this._size - 1) / this._capacity) <= 0.25 && this._capacity == 16) {
            Object[] newArr = new Object[8];
            newArr[0] = null;
            newArr[7] = null;

            int j = (int) this._F + 1;
            int k = (int) this._L - 1;
            int i = 0;
            while (j <= k) {
                newArr[i] = this._items[j];
                i++;
                j++;
            }
            this._F = 1;
            this._L = 6;
            this._items = cyclicShift(newArr, -2);
            newArr = null;
            this._capacity = 8;
        } else if (((double) (this._size - 1) / this._capacity) <= 0.25 && this._capacity > 16) {
//            System.out.println("Array is shrinking. F and L positions will change");
            this.shrink();
        }
        if (this.size() > 0) {
            this._size--;
        }
        return thing;
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
            doubled[i] = this._items[Math.floorMod((int) this._F + k, this._capacity)];
            k++;
        }

        this._items = doubled;
        this._capacity = doubled.length;
        this._F = A;
        this._L = A + this._size + 1;
    }

    private void shrink() {
        Object[] halved = new Object[this._capacity / 2];
        double A = (this._capacity / 4);

        // grab elements from A to capacity - (A + 1)
        int j = (int) this._F + 1;
        int k = (int) this._L - 1;
        int i = 0;
        while (j <= k) {
            halved[i] = this._items[j];
            i++;
            j++;
        }
//        for (int i = 0; i < halved.length; i++) {
//            halved[i] = this._items[(int) A + i];
//        }
        // Cycle back by A - 1
        halved = cyclicShift(halved, (int) A - 1);

//        Object[] temp = new Object[halved.length];
//        temp[0] = "F";
//        temp[halved.length - 1] = "L";
//        temp = cyclicShift(temp, (int) A);
//        for (int a = 0; a < temp.length; a++) {
//            if (temp[a] == "F") {
//                this._F = a;
//                System.out.println("entered");
//            }
//            if (temp[a] == "L") {
//                this._L = a;
//                System.out.println("entered");
//            }
//        }
//        temp = null;
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
    public boolean equals(Object o) {
//        if (Deque.class.isAssignableFrom((Class<?>) o)) {
        Deque<T> other = null;
        if (o instanceof LinkedListDeque) {
            other = (LinkedListDeque<T>) o;
        }

        if (o instanceof ArrayDeque) {
            other = (ArrayDeque<T>) o;
        }

        if (other.size() != this._size) {
            return false;
        }

        for (int i = 0; i < other.size(); i++) {
            if (!(other.get(i).equals(this.get(i)))) {
                return false;
            }
        }
//        } else {
//            return false;
//        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index > this._capacity - 1 || this._size == 0) {
            return null;
        }
        int process = (int) this._F + 1 + index;
        int passInIndex = Math.floorMod(process, this._capacity);
        return (T) this._items[passInIndex];
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
    private int getCapacity() {
        return this._capacity;
    }

    //used to be public double...changed to satisfy AG API
    private double getPercentageFull() {
        return ((double) this._size / (double) this._capacity);
    }

    @Override
    public int size() {
        return this._size;
    }

    /**
     * Superior to printDeque ;)
     */
    private void printArrayStats() {
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
