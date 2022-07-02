package deque;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<E> implements Deque<E> {
    int _size;
    Object[] _items;

    ArrayDeque() {
        this._size = 8;
        this._items = new Object[this._size];
    }

    ArrayDeque(int numElements) {
        this._size = numElements;
        this._items = new Object[numElements];
    }

    ArrayDeque(Collection<? extends E> c) {
        this._size = c.size();
        this._items = new Object[this._size];
        Iterator<? extends  E> it = c.iterator();
        int i = 0;

        while (it.hasNext()) {
            this._items[i] = it.next();
            i++;
            this._size++;
        }

    }


    @Override
    public int size() {
        return 0;
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

    @Override
    public void addFirst(E e) throws IllegalStateException, ClassCastException, NullPointerException, IllegalArgumentException {

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
}