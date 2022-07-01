package deque;
import java.util.*;


public class LinkedListDeque<T> implements deque.Deque {

    private Node<T> _head;

    private Node<T> _tail;

    private int _size = 0;

    LinkedListDeque() {
        this._head = new SentinelNode<>(null);
        this._tail = this._head;
    }

    LinkedListDeque (Node<T> node) {
        this._head.setNext(node);
        node.setPrev(this._head);
        this._size++;
        this._tail = node;
    }

    LinkedListDeque(ArrayList<Node<T>> nodes) {
        int k = 0;
        for (Node<T> node : nodes) {
            if (k == 0) {
                this._head = node;
                this._tail = node;
                k++;
            } else {
                node.setPrev(this._tail);
                this._tail.setNext(node);
                this._head.setPrev(node);
                this._tail = node;
            }
        }
        this._size += nodes.size();
    }

    @Override
    public void addFirst(Node<T> node) {
        node.setNext(this._head.getNext());
        this._head.getNext().setPrev(node);
        node.setPrev(this._head);
        this._head.setNext(node);
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
