package deque;

public class Node<T> implements Comparable<Node<T>> {
    private T _val;
    private Node<T> _next;
    private Node<T> _prev;

    public Node(T val) {
        this._val = val;
        this._next = this;
        this._prev = this;
    }

    public void setPrev(Node<T> node) {
        this._prev = node;
    }

    public void setNext(Node<T> node) {
        this._next = node;
    }

    public T getVal() {
        return this._val;
    }

    public Node<T> getNext() {
        return this._next;
    }

    public Node<T> getPrev() {
        return this._prev;
    }

    public boolean hasNext() {
        return this._next != null;
    }

    @Override
    public int compareTo(Node<T> o) {
        if (this._val instanceof String) {
            return ((String) this._val).compareTo((String) o.getVal());
        }
        else {
            Integer a = ((Integer) this._val).intValue();
            Integer b = ((Integer) o.getVal()).intValue();
            return Integer.compare(a, b);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        else if (o == null || !(o instanceof Node)) {
            return false;
        }
        Node<?> otherNode = (Node<?>) o;

        return this._val.equals(otherNode.getVal()) && this._next.equals(otherNode.getNext());
    }

    @Override
    public String toString() {
        return "" + this._val;
    }
}
