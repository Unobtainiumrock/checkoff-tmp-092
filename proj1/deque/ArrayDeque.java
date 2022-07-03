package deque;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Math;

public class ArrayDeque<E> implements Deque<E> {
    int _size;
    int _capacity;
    Object[] _items;
    int _nextFirst, _nextLast;

    ArrayDeque() {
        this._capacity = 8;
        this._size = 0;
        this._items = new Object[this._size];
        this._nextFirst = (this._capacity - 1) / 2;
        this._nextLast = (this._capacity + 1) / 2;
    }

    ArrayDeque(int numElements) {
        this._capacity = numElements;
        this._size = 0;
        this.notPowerOfTwoCommaUpsize();
        this._nextFirst = (int) (this._capacity - 1) / 2;
        this._nextLast = (int) (this._capacity + 1) / 2;
        this._items = new Object[numElements];
    }

    ArrayDeque(Collection<? extends E> c) {
        this._size = c.size();
        this._capacity = this._size;


        this._items = new Object[this._size];
        Iterator<? extends E> it = c.iterator();
        int i = 0;

        while (it.hasNext()) {
            this._items[i] = it.next();
            i++;
        }

    }

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
     * non-powers of two.
     */
    private void notPowerOfTwoCommaUpsize() {
        double logTwo = this._log2(this._capacity);

        while (Math.floor(logTwo) != logTwo) {
            this._capacity++;
            logTwo = this._log2(this._capacity);
        }
    }

    private int cyclicIndexing(int k) {
        return k % this._capacity;
    }

    // Rename to grow checker
    private void grow() {
        double percentageFilled = this._size / this._capacity;

        if (percentageFilled >= 0.75) {
            Object[] doubledCapacity = new Object[this._capacity * 2];
            transform(this._items, doubledCapacity);
            this._items = doubledCapacity;
            this._capacity = doubledCapacity.length;
        }
    }

    // rename to shrink transform. [L, F] => F
    private void shrink() {
        int oldCapacity = this._capacity;
        Object[] src = this._items;
        Object[] halvedCapacity = new Object[oldCapacity / 2];
        int F = this._nextFirst;

        // Build thing
        for (int i = 0; i < halvedCapacity.length; i++) {
            halvedCapacity[i] = src[cyclicIndexing(F + i)];
        }

        Object[] newSrc = new Object[oldCapacity / 2];

        // Make copy
        for (int i = 0; i < newSrc.length; i++) {
            newSrc[i] = halvedCapacity[i];
        }

        // Offset with a cyclic-back-shift
        for (int i = 0; i < halvedCapacity.length; i++) {
            int a = i + (src.length - F);
            int b = oldCapacity / 2;
            halvedCapacity[i] = newSrc[a % b];
        }
        this._items = halvedCapacity;
    }

    // Rename to grow transform
    private void transform(Object[] src, Object[] dest) {
        int L = this._nextLast;
        int F = this._nextFirst;
        if (L < F) {
            System.arraycopy(src, 0, dest, 0, F);
            System.arraycopy(src, F, dest, dest.length - (src.length - F), src.length - F);
        } else {
            System.arraycopy(src, 0, dest, 0, L);
            System.arraycopy(src, L, dest, dest.length - (src.length - L), src.length - L);
        }
    }

    @Override
    public void addFirst(E e) {
        int i = this._capacity;
        this._items[cyclicIndexing(i)] = e;
        this._nextFirst = cyclicIndexing(i - 1);
        this._size++;
        grow();
    }

    @Override
    public void addLast(E e) {
        int i = this._capacity;
        this._items[cyclicIndexing(i)] = e;
        this._nextLast = cyclicIndexing(i + 1);
        this._size++;
        grow();
    }

    @Override
    public E removeFirst() {
        if ((this._size - 1) / this._capacity < 0.25) {
            shrink();
        }
        this._size--;
        return null;
    }

    @Override
    public E removeLast() {
        if ((this._size - 1) / this._capacity < 0.25) {
            shrink();
        }
        this._size--;
        return null;
    }

    @Override
    public E get(int index) throws NoSuchElementException {
        return null;
    }

    @Override
    public void printDeque() {

    }

    @Override
    public int size() {
        return this._size;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] objects) {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }

    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object element() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }

}
