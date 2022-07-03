package deque;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Math;

public class ArrayDeque<E> implements Deque<E> {
    int _size;
    double _capacity;
    Object[] _items;
    int _nextFirst, _nextLast;

    ArrayDeque() {
        this._capacity = 8;
        this._size = 0;
        this._items = new Object[this._size];
        this._nextFirst = (int) (this._capacity - 1) / 2;
        this._nextLast = (int) (this._capacity + 1) / 2;
    }

    ArrayDeque(int numElements) {
        this._capacity = numElements;
        this._size = numElements;
        this._upsize();
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
            this._size++;
        }

    }

    private double _log2(double k) {
        return Math.log(k) / Math.log(2);
    }

    private void _upsize() {
        // Increase capacity s.t. the size (number of elements) is at least 25% of the capacity
        double logTwo = this._log2(this._capacity);

        while (Math.floor(logTwo) != logTwo) {
            this._capacity++;
            logTwo = this._log2(this._capacity);
        }
    }

    @Override
    public void addFirst(E e) throws IllegalStateException, ClassCastException, NullPointerException, IllegalArgumentException {
        System.arraycopy();
        //src_arr, src_pos, dest_arr, dest_pos, len

        // copy inclusive range of elements from src_pos to src_pos + len - 1 from src array to..
        // dest_arr at inclusive range of dest_post to dest_pos + len - 1


 //       if ((double) this._size * 0.75 >= ) {

   //     }
    }

    @Override
    public void addLast(E e) throws IllegalStateException, ClassCastException, NullPointerException, IllegalArgumentException {

    }

    @Override
    public E removeFirst() throws NoSuchElementException {
        return null;
    }

    @Override
    public E removeLast() throws NoSuchElementException {
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
    public E recursiveGet(int index) {
        return null;
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
