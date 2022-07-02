package deque;

import java.util.*;
import java.util.function.Consumer;

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

    public T recursiveGet(int index) {
        Node<T> curr = this._sentinel.getNext();
        return this.recursiveHelper(curr, index).getVal();
    }

    private Node<T> recursiveHelper(Node<T> node, int index) {
        if (index == 0) {

        }
        return recursiveHelper(node.next, index - 1);
    }

    public int size() {
        return this._size;
    }

    @Override
    public String toString() {
        Node<T> curr = this._sentinel.getNext();
        String out = "(";

        return out;

    }

    @Override
    public boolean contains(Object o) {
        Node<T> curr = this._sentinel.getNext();
        boolean res = false;

        if (curr.equals(o)) {
            return !res;
        }

        while (curr.hasNext()) {
            curr = curr.getNext();

            if (curr.equals(o)) {
                return !res;
            }
        }
        return res;
    }

    public void printDeque() {
        Node curr = this._sentinel.getNext();

        for (int i = 0; i < this._size; i++) {
            System.out.print(curr + " ");
            curr = curr.getNext();
        }
    }

    @Override
    public Iterator iterator() {
        return (Iterator) this._sentinel.getNext();
    }

    @Override
    public Object[] toArray() {
        Node<T> head = this._sentinel.getNext();
        Object[] arr = new Object[this._size];
        int k = 0;

        while (head.hasNext()) {
            arr[k] = head.getVal();
            head = head.getNext();
            k++;
        }
        return arr;
    }

    // Nuh uh. Deal with deep nesting later.
    @Override
    public Object[] toArray(Object[] objects) {
        return new Object[0];
    }

    // Simply add to the end by default
    @Override
    public boolean add(Object o) {
        T val = (T) o;
        this.addLast(val);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> curr = this._sentinel.getNext();
        boolean res = false;

        if (curr.equals(o)) {
            this.removeFirst();
            return !res;
        }

        while (curr.hasNext()) {
            curr = curr.getNext();

            // Make sure the linked list re-links, if removing something
            // in the middle.
            if (curr.equals(o)) {
                curr.getPrev().setNext(curr.getNext());
                curr.getNext().setPrev(curr.getPrev());
                return !res;
            }
        }
        return res;
    }

    @Override
    public boolean addAll(Collection collection) {
        Node<T> head = this._sentinel.getNext();
        Arrays.asList(collection).forEach((val) -> {
            head.setNext(new Node(val));
        });
        return true;
    }

    // Nahh..
    @Override
    public void clear() {

    }

    // The rest don't technically need to be implemented according to the project spec,
    // I can add them later if we really want to have some fun..
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
