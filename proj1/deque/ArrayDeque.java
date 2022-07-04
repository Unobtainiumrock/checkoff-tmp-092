package deque;

import java.lang.Math;

public class ArrayDeque<E> implements Deque<E> {
    int _size; // No time to refactor to private
    int _capacity; // ditto
    Object[] _items; // ditto
    int _nextFirst, _nextLast; // ditto

    public ArrayDeque() {
        this._capacity = 8;
        this._size = 0;
        this._items = new Object[this._capacity];
        this._nextFirst = (this._capacity - 1) / 2;
        this._nextLast = (this._capacity + 1) / 2;
    }

    public ArrayDeque(int numElements) {
        this._capacity = numElements;
        this._size = 0;
        this.powerOfTwoUpsize(); // Make sure the capacity is a clean power of 2.
        this._items = new Object[this._capacity]; // Capacity is changed by power2 upsize.
        this._nextFirst = (int) (this._capacity - 1) / 2;
        this._nextLast = (int) (this._capacity + 1) / 2;
    }

// Stupid AG
//    ArrayDeque(Collection<? extends E> c) {
//        this._size = c.size();
//        this._capacity = this._size;
//        this.powerOfTwoUpsize(); // Make sure the capacity is a clean power of 2.
//        // Bumps up to 4 at minimum
//        if (this._capacity < 8) {
//            this._capacity *= 2;
//        }
//        this._items = new Object[this._capacity]; // Capacity is changed by power2 upsize.
//        this._nextLast = 0;
//        this._nextFirst = this._capacity - 1;
//        this._size = 0; // temporarily reset size, it will be updated in the addLast within while loop.
//
//        Iterator<? extends E> it = c.iterator();
//
//        while (it.hasNext()) {
//            this.addLast(it.next());
//        }
//
//    }

    public double getCapacity() {
        return this._capacity;
    }

    private double _log2(double k) {
        return Math.log(k) / Math.log(2);
    }

    /**
     * The general idea is that we plan to double the size of the array every time we hit 75% capacity.
     * This doubling will serve to prevent the nextFirst and nextLast pointers from overlapping one another as
     * the pointers are set up to cyclicly iterate.
     * {1, 2, 3, 4, 5, 6}
     *  0  1  2  3  4  5
     *  length: 6
     *
     *  Example of cyclic iteration
     *  0 % 6 = 0
     *  1 % 6 = 1
     *  2 % 6 = 2
     *  3 % 6 = 3
     *  4 % 6 = 4
     *  5 % 6 = 5
     *  6 % 6 = 0 new cycle begins
     *  7 % 6 = 1
     *  ...
     *
     * Similarly, if working with negative numbers, we can observe that.
     * 0 % 6 = 0
     * -1 % 6 = 5
     * -2 % 6 = 4
     * -3 % 6 = 3
     * -4 % 6  = 2
     * -5 % 6 = 1
     * -6 % 6 = 0
     * -7 % 6 = 5
     * -8 % 6 = 4
     * ...
     *
     * Now that we know what cyclic iteration looks like, how
     * can you prevent advancing the nextFirst pointer and/or the
     * nextLast pointer from moving past one another?
     * This can be done by doubling whenever the amount of elements reaches
     * 75% of the array's capacity.
     *
     * Example for an array of 8 being filled and then doubled to 16
     *
     * Initial state 1
     * {_, _, _, _, _, _, _, _}
     *           F  L
     * AddLast(1)
     * {_, _, _, _, 1, _, _, _}
     *           F     L
     * AddLast(2)
     * {_, _, _, _, 1, 2, _, _}
     *           F        L
     *  AddLast(3)
     *  {_, _, _, _, 1, 2, 3, _}
     *            F           L
     *  AddLast(4)
     *  {_, _, _, _, 1, 2, 3, 4}
     *   L        F
     *  AddLast(5)
     *  {5, _, _, _, 1, 2, 3, 4}
     *      L     F
     *  AddLast(6)
     *  {5, 6, _, _, 1, 2, 3, 4}
     *         L  F
     * About to overlap, if we were to advance any pointers. Conveniently enough,
     * this happens to be when the number of elements if 75% of the available capacity!
     * 6 / 8 = 0.75
     *
     * note: observe that the "length" of the empty spaces is two, this will
     * come into play later when observing a pattern.
     *
     * This is when we will double the capacity from 8 to 16.
     *
     * In order to determine how best to distribute this new padding, it helps to see
     * how the initial state of an arbitrary emtpy array looks like with respect to
     * the initial positions of their nextFirst and nextLast pointers.
     * (they should always be next to each other)
     *
     * {_,_,_,_,_,_,_,_}
     *        F L
     * Padding should always be distributed evenly, meaning that when doubling the size from
     * 8 to 16, padding of 4 should go after the L pointer, and a padding of 8 should
     * go before the F pointer.
     *
     * The tricky part to take into account is what happens for each
     *
     *
     * source {5, 6, _, _, 1, 2, 3, 4}
     *               L  F
     *
     * L = 2
     * F = 3
     *
     * a, 0, b, 0, F
     * a, F, b, len(b) - (len(a) - F), len(a) - F
     *
     *
     * dest {_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _}
     *
     * out {5, 6, _, _, _, _, _, _, _, _, _, _, 1, 2, 3, 4}
     *
     * args: source, 0, dest, 0, F yields
     *  {5, 6, _, _, _, _, _, _, _, _, _, _, _, _, _, _}
     *   0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15
     * args: source, F, dest, dest length - (src length - F) , (len - F)
     *
     * [L, F] => F to know this config, check if index L < index F
     * [F, L] => L ..., check if index F < index L
     *
     * {4, 3, 2, 1, _, _, 6, 5}
     *              L  F
     *
     * transformation 1: a, 0, b, 0, L
     * transformation 2: a, L, b, len(b) - (len(a) - L), len(a) - L
     *
     * expect: {4, 3, 2, 1, _, _, _, _, _, _, _, _, _, _, 6, 5}
     * Comfirmed!
     *
     * {_,_,_,_,_,_,_,_}
     *        F L
     *  F(1)
     * {_,_,_,1,_,_,_,_}
     *      F   L
     *
     * L(2)
     * {_,_,_,1,2,_,_,_}
     *      F     L
     *
     * F(3)
     * {_,_,3,1,2,_,_,_}
     *    F       L
     *
     * L(4)
     * {_,_,3,1,2,4,_,_}
     *    F       L
     * L(69)
     * {_,_,3,1,2,4,69,_}
     *   F             L
     * L(69)
     * {_,_,3,1,2,4,69,69}
     *  L F
     *
     *  Transformation 1: a, 0, b, 0, L
     *  Transformation 2: a, L, b, len(b) - (len(a) - L), len(a) - L
     *
     *  expect: {_,_,_,_,_,_,_,_,_,_,3,1,2,4,69,69}
     *           L                 F
     *  confirmed!
     *
     *
     *  List of things to implement.
     *  transformation
     *  checkCapacity
     *  upsize
     *  cyclic iteration
     *  pointers for first and last
     *  logic for advancing pointers
     *  a way to check configurations [L, F] and [F, L]
     *  downsizing (last)
     *
     *
     * Tests for downsizing
     *                 0  1  2  3  4  5  6  7  8  9 10 11 12  13 14 15
     * transform this {_, _, _, _, _, _, _, _, _, _, _, 4, 3, 2, 1, _} into...
     *                                               F              L
     *
     * this
     *  0  1  2  3  4  5  6  7
     * {_, _, _, 4, 3, 2, 1, _}
     *        F              L
     *
     * gap is defined as length of array - the number of elements between L and F
     *
     * F: 10 -> 2, example: 10 / gcd(10, 15) = 2, possible function: F / gcd(L, F) is the new position of F
     * L: 15 -> 7,  example: (15 - 1) / 2 = 7, possible function: (L - 1) / 2, or possibly (L - 1) / [1/2 number of elements between F and L]
     *
     *
     *
     *
     * len(gap): 12 -> 4, 12 - 8 = 4, possible function: len(gap) - (1/2 of downsize)
     *
     *
     * src, src_start, dest, dest_start, length of copy
     *
     * Let newF = oldF / gcd(oldL, oldF)
     * Let newL = (oldL - 1) / [1/2 of number of elements between oldF and oldL] <- currently not used.
     * newL is maybe (oldL - 1) / [1/2 * [(oldL - oldF - 1)]
     *
     *  Transformation 1: src, oldF, dest, newF + 1, L - F
     *
     *  Transformation 2:
     *
     *
     *
     * To D: Implement the updating of pointers for upsizing, as we are doing for downsizing.
     *
     */

    /**
     * Increase capacity s.t. the size (number of elements) is at least 25% of the capacity --used for
     * non-powers of two when instantiating an ArrayDeque i.e. preemptively add padding.
     */
    private void powerOfTwoUpsize() {
        double logTwo = this._log2(this._capacity);

        while (Math.floor(logTwo) != logTwo) {
            this._capacity++;
            logTwo = this._log2(this._capacity);
        }
    }

    private int cyclicIndexing(int k, int capacity) {
        return k % capacity;
    }

    private void grow() {
        Object[] src = this._items;
        Object[] dest = new Object[this._capacity * 2];
        int L = this._nextLast;
        int F = this._nextFirst;

        if (L < F) {
            System.arraycopy(src, 0, dest, 0, F);
            System.arraycopy(src, F, dest, dest.length - (src.length - F), src.length - F);
        } else {
            System.arraycopy(src, 0, dest, 0, L);
            System.arraycopy(src, L, dest, dest.length - (src.length - L), src.length - L);
        }
        // Grow
        //      newF = cyclic16(oldF + double size)
        //      newL = cyclic16(oldL + double size)
        int a = this._nextFirst + (2 * this._capacity);
        int b = this._nextLast + (2 * this._capacity);
        int c = (int) (2 * this._capacity);
        this._nextFirst = cyclicIndexing(a, c);
        this._nextLast = cyclicIndexing(b, c);
        this._items = dest;
        this._capacity *= 2;
    }

    private void shrink() {
        int oldCapacity = this._capacity;
        Object[] src = this._items;
        Object[] dest = new Object[oldCapacity / 2];
        int F = this._nextFirst;

        // Build thing
        for (int i = 0; i < dest.length; i++) {
            int a = F + i;
            int b = this._capacity;
            dest[i] = src[cyclicIndexing(a, b)];
        }

        // Make copy
        Object[] newSrc = new Object[oldCapacity / 2];

        for (int i = 0; i < newSrc.length; i++) {
            newSrc[i] = dest[i];
        }

//        // Offset with a cyclic-back-shift
//        for (int i = 0; i < dest.length; i++) {
//            int a = i + (src.length - F);
//            int b = dest.length;
//            dest[i] = newSrc[cyclicIndexing(a, b)];
//        }

        // Shrink
        //      newF = cyclic8(oldF - half size)
        //      newL = cyclic8(oldL - half size)
        int a = this._nextFirst - ((int) (0.5 * this._capacity));
        int b = this._nextLast - ((int) 0.5 * this._capacity);
        int c = (int) (0.5 * this._capacity);
        this._nextFirst = cyclicIndexing(a, c);
        this._nextLast = cyclicIndexing(b, c);
        this._items = cyclicBackShift(dest, newSrc, F);
        this._capacity /= 2;
    }

    private Object[] cyclicBackShift(Object[] toBeShifted, Object[] reference, int by) {
        int len = toBeShifted.length;
        // Offset with a cyclic-back-shift
        for (int i = 0; i < toBeShifted.length; i++) {
            int a = i + (len - by);
            int b = toBeShifted.length;
            toBeShifted[i] = reference[cyclicIndexing(a, b)];
        }
        return toBeShifted;
    }

    // Super freaking naive approach
    private Object[] rotateUntilAligned(Object[] toBeShifted, Object[] reference) {
        boolean aligned = false;
        int k = 0;

        while (!aligned) {
            aligned = elementWiseCompare(toBeShifted, reference);
            if (aligned) {
                break;
            }
            cyclicBackShift(toBeShifted, reference, 1);
            if (k > this._capacity) {
                break;
            }
            k++;
        }
        return toBeShifted;
    }

    // Part of super naive approach. Assume a & b have same length
    private boolean elementWiseCompare(Object[] a, Object[] b) {
        boolean res = true;

        for (int i = 0; i < a.length; i++) {
            if (a[i] != null && b[i] != null) {
                res = res && a[i].equals(b[i]);
            }
        }
        return res;
    }

    // May not need this.
    private int gcd(int a, int b) {
        int k = a > b ? b : a;

        while (a % k > 0 || b % k > 0) {
            k--;
        }
        return k;
    }

    @Override
    public void addFirst(E e) {
        int a = this._nextFirst;
        int b = this._capacity;
        this._items[a] = e;
        this._nextFirst = cyclicIndexing(a - 1, b);
        this._size++;

        if (this._size / this._capacity >= 0.75) {
            grow();
        }

    }

    @Override
    public void addLast(E e) {
        int a = this._nextLast;
        int b = this._capacity;
        this._items[a] = e;
        this._nextLast = cyclicIndexing(a + 1, b);
        this._size++;

        if (this._size / this._capacity >= 0.75) {
            grow();
        }
    }

    @Override
    public E removeFirst() {
        if ((this._size - 1) / this._capacity < 0.25 && this._capacity >= 16) {
            shrink();
        }
        int a = this._nextFirst;
        int b = this._capacity;
        Object temp = this._items[cyclicIndexing(a + 1, b)];
        this._items[cyclicIndexing(a + 1, b)] = null;
        this._nextFirst = cyclicIndexing(a + 1, b);
        this._size--;

        return (E) temp;
    }

    @Override
    public E removeLast() {
        if ((this._size - 1) / this._capacity < 0.25 && this._capacity >= 16) {
            shrink();
        }
        int a = this._nextLast;
        int b = this._capacity;
        Object temp = this._items[cyclicIndexing(a - 1, b)];
        this._items[cyclicIndexing(a - 1, b)] = null;
        this._nextLast = cyclicIndexing(a - 1, b);
        this._size--;

        return (E) temp;
    }

    @Override
    public E get(int index) {
        if (index > this._capacity - 1 || this._size == 0) {
            return null;
        }
        Object[] a = {4, null, null, null, 0, 1, 2, 3};
        Object[] b = new Object[8];
        int F = 3;

        int i = 0;

        while (i < 7) {
            b[i] = a[(F + i + 1) % 8];
            i++;
        }


        for (int j = 0; j < b.length; j++) {
            System.out.printf("idx: %s, val: %s\n", j, b[j]);
        }

    }
//  0 1 2 3 4 5 6 7
//    // {4 _ _ _ 0 1 2 3 } = a
//      // {0 1 3 _ _ _ _ _ } = b


    @Override
    public void printDeque() {
        // Needs to print from F to L
        int a = this._nextFirst;
        int b = this._nextLast;
        int c = this._capacity;
        int F = cyclicIndexing(a + 1, c);
        int L = cyclicIndexing(b - 1, c);

        for (int i = F; i <= L; i = cyclicIndexing(i + 1, c)) {
            Object val = this.get(i);
            if (val != null) {
                System.out.print(val + " ");
            }
        }
        System.out.println("");
    }

    @Override
    public int size() {
        return this._size;
    }

    @Override
    public boolean isEmpty() {
        return this._size == 0;
    }

    @Override
    public boolean equals(Object o) {
        ArrayDeque<? extends E> other = ((ArrayDeque<? extends E>) o);
        int oSize = other._size;
        Object[] oItems = other._items;

        boolean res = true;

        // Case for different sizes
        if (oSize != this._size) {
            return !res;
        }

        // Case for both empty
        if (((ArrayDeque<?>) o).isEmpty() && this.isEmpty()) {
            return true;
        }

        // Corresponds to "this"
        int a = this._nextFirst;
        int b = this._nextLast;
        int c = this._capacity;
        int F = cyclicIndexing(a + 1, c);
        int L = cyclicIndexing(b - 1, c);
        // end corresponds to "this"


        // Corresponds to other
        int d = other._nextFirst;
        int e = other._nextLast;
        int f = other._capacity;
        int otherF = cyclicIndexing(d + 1, f);
        int otherL = cyclicIndexing(e - 1, f);
        // ends corresponds to other

        // Edge case for two equivalent single element arrays.
        if (Math.abs(F - L) == 0 && Math.abs(otherF - otherL) == 0) {
            return this._items[F].equals(other._items[otherF]);
        }

        Object[] tmpThis = new Object[this._size];
        Object[] tmpOther = new Object[oSize];

        // Corresponds to this
        int k = 0;

        for (int i = F; i <= L; i = cyclicIndexing(i + 1, c)) {
            Object val = this.get(i);
            if (val != null) {
                tmpThis[k] = val;
                k++;
            }
        }
        // end corresponds to this

        // corresponds to other
        int j = 0;
        for (int i = otherF; i <= otherL; i = cyclicIndexing(i + 1, f)) {
            Object val = other.get(i);
            if (val != null) {
                tmpOther[j] = val;
                j++;
            }
        }
        // end corresponds to other

        // Compare the two tmp arrays
        for (int i = 0; i < tmpThis.length; i++) {
            if (!(tmpThis[i].equals(tmpOther[i]))) {
                return false;
            }
        }

        System.out.println("");
        return true;
    }

    public boolean contains(Object o) {
        for (int i = 0; i < this._items.length; i++) {
            if (o.equals(this._items[i])) {
                return true;
            }
        }
        return false;
    }

//    public Iterator iterator() {
//        return Arrays.asList(this._items).iterator();
//    }

    public Object[] toArray() {
        ArrayDeque<? extends E> other = ((ArrayDeque<? extends E>) this);

        int a = this._nextFirst;
        int b = this._nextLast;
        int c = this._capacity;
        int F = cyclicIndexing(a + 1, c);
        int L = cyclicIndexing(b - 1, c);

        Object[] tmpThis = new Object[Math.abs(F - L)];
        int k = 0;

        for (int i = F; i <= L; i = cyclicIndexing(i + 1, c)) {
            Object val = this.get(i);
            if (val != null) {
                tmpThis[k] = val;
            }
            k++;
        }
        return this._items;
    }

}
