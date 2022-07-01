package deque;
import java.util.*;


public class LinkedListDeque<T> implements Deque<T> {

    private SentinelNode _sentinel;

    private int _size = 0;

    LinkedListDeque() {
        this(new SentinelNode(0));
    }

    LinkedListDeque (Node<T> node) {
        this._sentinel.setNext(node);
        this._sentinel.setPrev(node);
        node.setPrev(this._sentinel);
        node.setNext(this._sentinel);
        this._size++;
    }

    LinkedListDeque(ArrayList<Node<T>> nodes) {
        this._sentinel = new SentinelNode(0);

        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i); //orig was nodes[i]

            if (i == 0) {
                this._sentinel.setNext(node);
                this._sentinel.setPrev(node);
                node.setPrev(this._sentinel);
                node.setNext(this._sentinel);
            }
            this._sentinel.getPrev().setNext(node);
            node.setNext(this._sentinel);
        }
        this._size += nodes.size();
    }

    @Override
    public void addFirst(T t) {
        Node<T> head = this._sentinel.getNext();
        Node<T> tail = this._sentinel.getPrev();

        Node test = this._sentinel.getNext();

    }
    //Node<T> node

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
