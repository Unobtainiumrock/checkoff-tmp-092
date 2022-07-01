package deque;
import java.util.*;


public class LinkedListDeque<T> implements Deque<T> {
    private SentinelNode<T> _sentinel;
    private int _size = 0;

    LinkedListDeque(T t) {
        Node<T> n = new Node<>(t);
        this._sentinel = new SentinelNode<>(null);

        this._sentinel.setNext(n);
        this._sentinel.setPrev(n);
        n.setPrev(this._sentinel);
        n.setNext(this._sentinel);
        this._size++;
    }

    // ArrayList is used to ease iteration of my
    // helper constructor.
    LinkedListDeque(ArrayList<Node<T>> nodes) {
        this._sentinel = new SentinelNode<>(null);
        int k = 0;

        for (Node<T> node : nodes) {
            if (k == 0) {
                this._sentinel.setNext(node);
                this._sentinel.setPrev(node);
                node.setPrev(this._sentinel);
                node.setNext(this._sentinel);
                k++;
            }
            this._sentinel.getPrev().setNext(node);
            node.setNext(this._sentinel);
        }
        this._size += nodes.size();
    }

    @Override
    public T removeFirst() {
        Node<T> oldHead = this._sentinel.getNext();
        T oldHeadVal = oldHead.getVal();

        this._sentinel.setNext(oldHead.getNext());

        return oldHeadVal;
    }

    @Override
    public T removeLast() {
        Node<T> oldTail = this._sentinel.getPrev();
        T oldTailVal = oldTail.getVal();

        this._sentinel.setPrev(oldTail.getPrev());

        return oldTailVal;
    }

    @Override
    public void addFirst(T val) {
        Node<T> node = new Node<>(val);
        Node<T> head = this._sentinel.getNext();
        Node<T> tail = this._sentinel.getPrev();

        node.setNext(head);
        head.setPrev(node);
        node.setPrev(tail);
        tail.setNext(node);
    }

    @Override
    public void addLast(T val) {
        Node<T> node = new Node<>(val);
        Node<T> head = this._sentinel.getNext();
        Node<T> tail = this._sentinel.getPrev();

        node.setPrev(tail);
        tail.setNext(node);
        node.setNext(head);
        head.setPrev(node);

    }

    public T get(int index) {
        if (this._size == 0 || index > this._size) {
            return null;
        } else {
            Node<T> curr = this._sentinel.getNext();
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getVal();
        }
    }

    @Override
    public int size() {
        return this._size;
    }

    @Override
    public boolean contains(Object o) {
        if () {

        }
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
