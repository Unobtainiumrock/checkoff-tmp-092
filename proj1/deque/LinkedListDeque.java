package deque;
import java.util.*;


public class LinkedListDeque<T> implements deque.Deque {

    private Node<T> head;

    private Node<T> tail;

    public LinkedListDeque() {
        this.head = new Node<T>();
        this.tail = this.head;
    }

    public LinkedListDeque (T val) {
        Node<T> newNode = new Node<>(val);
        this.head.getNext() = (T) newNode;
        this.head.getPrev() = (T) newNode;
        this.tail = newNode;
    }

    @Override
    public void addFirst(Object o) {

    }

    @Override
    public void addLast(Object o) {

    }

    @Override
    public E removeFirst() {


    }

    @Override
    public E removeLast() {

    }

    @Override
    public E get(int index) {

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
    public Object[] toArray(Object[] a) {
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
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
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
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    @Override
    public String toString() {
        return "";
    }
}
