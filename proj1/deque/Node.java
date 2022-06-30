package deque;

public class Node<T> implements Comparable<Node<T>> {
    private T _val;
    private T _next;
    private T _prev;

    public Node() {
        this._val = null;
        this._next = (T) this;
        this._prev = (T) this;
    }

    public Node(T val) {
        this._val = val;
        this._next = null;
    }

    public void setPrev(T val) {
        this._prev = (T) new Node(val);
    }

    public void setNext(T val) {
        this._next = (T) new Node(val);
    }

    public T getVal() {
        return this._val;
    }

    public T getNext() {
        return this._next;
    }

    public T getPrev() {
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

//    @Override
//    public String toString() {
//        String nullChecker;
//        Node<?> castNexttoNode = null;
//        if (this._next == null) {
//            nullChecker = "null";
//        }
//        else {
//            castNexttoNode = (Node<?>) this._next;
//            nullChecker = (String) castNexttoNode;
//        }
//        return  "{ \n val: " + this._val +  ",\n next: " + this._next. + "}";
//    }
}
