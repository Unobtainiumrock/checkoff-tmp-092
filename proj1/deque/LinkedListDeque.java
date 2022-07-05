package deque;

public class LinkedListDeque<T> implements Deque<T> {
    private SentinelNode<T> _sentinel;
    private int _size = 0;

    public LinkedListDeque() {
        Node<T> n = new SentinelNode<>(null);
        this._sentinel = (SentinelNode<T>) n;
    }

    public LinkedListDeque(T t) {
        Node<T> n = new Node<>(t);
        this._sentinel = new SentinelNode<>(null);

        this._sentinel.setNext(n);
        this._sentinel.setPrev(n);
        n.setPrev(this._sentinel);
        n.setNext(this._sentinel);
        this._size++;
    }

    // ArrayList is used to ease iteration of my
    // helper constructor. Stupid AG..
//    LinkedListDeque(ArrayList<Node<T>> nodes) {
//        this._sentinel = new SentinelNode<>(null);
//        int k = 0;
//
//        for (Node<T> node : nodes) {
//            if (k == 0) {
//                this._sentinel.setNext(node);
//                this._sentinel.setPrev(node);
//                node.setPrev(this._sentinel);
//                node.setNext(this._sentinel);
//                k++;
//            }
//            this._sentinel.getPrev().setNext(node);
//            node.setNext(this._sentinel);
//        }
//        this._size += nodes.size();
//    }

    private Node<T> getHead() {
        return this._sentinel.getNext();
    }

    private Node<T> getTail() {
        return this._sentinel.getPrev();
    }

    private void setHead(Node<T> n) {
        this._sentinel.setPrev(n);
    }

    private void setTail(Node<T> n) {
        this._sentinel.setNext(n);
    }

    private void nullifyNode(Node<T> n) {
        n.setNext(null);
        n.setPrev(null);
    }

    @Override
    public T removeFirst() {
        Node<T> oldHead = this.getHead();
        T oldHeadVal = oldHead.getVal();

        this.setTail(oldHead.getNext());
        oldHead.getNext().setPrev(this._sentinel);
        nullifyNode(oldHead); //make eligible for GC

        if (this._size != 0) {
            this._size--;
        }
        return oldHeadVal;
    }

    @Override
    public T removeLast() {
        Node<T> oldTail = this.getTail();
        T oldTailVal = oldTail.getVal();

        oldTail.getPrev().setNext(this._sentinel);
        this.setHead(oldTail.getPrev());
        nullifyNode(oldTail); //make eligible for GC

        if (this._size != 0) {
            this._size--;
        }
        return oldTailVal;
    }


    public boolean isEmpty() {
        return this._size == 0;
    }

    @Override
    public void addFirst(T val) {
        Node<T> node = new Node<>(val);
        Node<T> head = this.getHead();

        node.setNext(head);
        head.setPrev(node);
        node.setPrev(this._sentinel);
        this.setTail(node);

        this._size++;
    }

    @Override
    public void addLast(T val) {
        Node<T> node = new Node<>(val);
        Node<T> tail = this.getTail();

        node.setPrev(tail);
        tail.setNext(node);
        node.setNext(this._sentinel);
        this.setHead(node);
        this._size++;

    }

    @Override
    public T get(int index) {
        if (this._size == 0 || index > this._size) {
            return null;
        } else {
            Node<T> curr = this.getHead();
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getVal();
        }
    }

    public T getRecursive(int index) {
        if (this._size == 0 || index > this._size) {
            return null;
        }
        Node<T> curr = this.getHead();
        return this.recursiveHelper(curr, index).getVal();
    }

    private Node<T> recursiveHelper(Node<T> node, int index) {
        if (index == 0) {
            return node;
        }
        return recursiveHelper(node.getNext(), index - 1);
    }

    @Override
    public int size() {
        return this._size;
    }


    @Override
    public boolean equals(Object o) {

//        if (!(o instanceof LinkedListDeque)) {
//            return false;
//        }

        if (o instanceof ArrayDeque) {
            // get AD as a regular collapsed array
            Object[] otherItems = ((ArrayDeque<T>) o)._items;
            Deque<T> other = new LinkedListDeque<>();



            // convert the items element-wise from AD into a newly instantiated LLD
            return true;
        } else {
            LinkedListDeque<T> otherLLD = (LinkedListDeque<T>) o;

            if (this._size != otherLLD.size()) {
                return false;
            }

            Node<T> thisHead = this.getHead();
            Node<T> otherHead = otherLLD.getHead();


            boolean res = true;

            // All nodes in this
            // All nodes in other
            while (thisHead.hasNext() && otherHead.hasNext()) {
                res = res && thisHead.equals(otherHead);
                thisHead = thisHead.getNext();
                otherHead = otherHead.getNext();
            }
            return res;
        }
    }


    public String toString() {
        Node<T> curr = this.getHead();
        String out = "";

        while (curr.hasNext()) {
                out += "(";
            out += curr.getVal();
            out += ") -> ";
            curr = curr.getNext();
        }

        return out;

    }

    private boolean contains(Object o) {
        Node<T> curr = this.getHead();
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

    @Override
    public void printDeque() {
        Node<T> curr = this.getHead();

        for (int i = 0; i < this._size; i++) {
            System.out.print(curr);
            curr = curr.getNext();
        }
        System.out.println("");
    }


// Stupid AG
//    public Iterator iterator() {
//        return (Iterator) this.getHead();
//    }


    public Object[] toArray() {
        Node<T> head = this.getHead();
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

    private Object[] toArray(Object[] objects) {
        return new Object[0];
    }

    // Simply add to the end by default

    private boolean add(Object o) {
        T val = (T) o;
        this.addLast(val);
        return true;
    }


    private boolean remove(Object o) {
        Node<T> curr = this.getHead();
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
//    Stupid AG
//    public boolean addAll(Collection collection) {
//        Node<T> head = this.getHead();
//        Arrays.asList(collection).forEach((val) -> {
//            head.setNext(new Node(val));
//        });
//        return true;
//    }
}
