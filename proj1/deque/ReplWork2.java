package deque;

public class ReplWork2 {
    class Node {
        int _val;
        Node _next;
        Node _prev;

        Node(int val) {
            this._val = val;
            this._next = this;
            this._prev = this;
        }

        public int getVal() {
            return this._val;
        }

        public Node getNext() {
            return this._next;
        }

        public Node getPrev() {
            return this._prev;
        }

        public void setNext(Node next) {
            this._next = next;
        }

        public void setPrev(Node prev) {
            this._prev = prev;
        }

        public boolean hasNext() {
            return this._next != null;
        }

        @Override
        public String toString() {
            return "" + this._val;
        }
    }

    class SentinelNode extends Node {
        SentinelNode(int val) {
            super(val);
        }

        @Override
        public String toString() {
            return "(Sentinel: " + this._val + ")";
        }
    }

    class DLL {
        private SentinelNode _sentinel;
        private int _size = 0;

        DLL() {
            this(new SentinelNode(0));
        }

        DLL(Node node) {
            this._sentinel.setNext(node);
            this._sentinel.setPrev(node);
            node.setPrev(this._sentinel);
            node.setNext(this._sentinel);
            this._size++;
        }

        DLL(Node[] nodes) {
            this._sentinel = new SentinelNode(0);

            for (int i = 0; i < nodes.length; i++) {
                Node node = nodes[i];

                if (i == 0) {
                    this._sentinel.setNext(node);
                    this._sentinel.setPrev(node);
                    node.setPrev(this._sentinel);
                    node.setNext(this._sentinel);
                }
                this._sentinel.getPrev().setNext(node);
                node.setNext(this._sentinel);
            }
        }
      this._size += nodes.length;
    }

    public Node getHead() {
        return this._head;
    }

    public Node getTail() {
        return this._tail;
    }

    public int size() {
        return this._size;
    }

    // @Override
    public boolean isEmpty() {
        return this._size == 0;
    }

    public void printDeque() {
        Node curr = this._sentinel;

        for (int i = 0; i < this._size; i++) {
            System.out.print(curr + " ");
            curr = curr.getNext();
        }

    }

    public Node removeFirst() {
        Node oldHead = this._sentinel.getNext(); // Variable for clarity.
        Node oldHeadVal = new Node(oldHead.getVal());

        this._sentinel.setNext(oldHead.getNext()); // Update head.

        return oldHeadVal;
    }

    public Node removeLast() {
        Node oldTail = this._sentinel.getPrev(); // Variable for clarity.
        Node oldTailVal = new Node(oldTail.getVal());

        this._sentinel.setPrev(oldTail.getPrev()); // Update tail.

        return oldTailVal;
    }

    public void addFirst(Node node) {
        Node head = this._sentinel.getNext(); // Variable for clarity.
        Node tail = this._sentinel.getPrev(); // Variable for clarity.

        node.setNext(head);
        head.setPrev(node);
        node.setPrev(tail);
        tail.setNext(node);
    }

    public void addLast(Node node) {
        Node head = this._sentinel.getNext(); // Variable for clarity.
        Node tail = this._sentinel.getPrev(); // Variable for clarify.

        node.setPrev(tail);
        tail.setNext(node);
        node.setNext(head);
        head.setPrev(node);

    }

    public T get(int index) {
        if (this._size == 0 || index > this._size) {
            return null;
        }
        else {
            Node pointer = this._sentinel.getNext();
            while (index > 0) {
                pointer = pointer._next;
                index -= 1;
            }
            return pointer._val;
        }
    }
}

class Main {
    public static void main(String[] args) {
        // Node[] nodes = new Node[20];

        // for (int i = 0; i < nodes.length; i++) {
        //   nodes[i] = new Node(i + 1);
        // }

        DLL test = new DLL();

        // for (int i = 0; i < nodes.length; i++) {
        //   test.addLast(nodes[i]);
        // }
        // DLL test2 = new DLL(nodes);
        // DLL test2 = new DLL(1,2,3,4);

        // Node curr = test.getHead();
        // System.out.println(curr.getVal());

        // while (curr.hasNext()) {
        //   curr = curr.getNext();
        //   System.out.println(curr.getVal());
        // }

        // DLL testTwo = new DLL(new Node(69));
        // System.out.println(testTwo.getHead().getVal());
        // System.out.println(testTwo.getHead().getVal());
        // System.out.println(testTwo.getHead().getPrev().getVal());
        // System.out.println(testTwo.getHead().getNext().getVal());
        // testTwo.add(new Node(99));
        // System.out.println(testTwo.getHead().getNext().getVal());

        // DLL testThree = new DLL();

        // testThree.add(new Node(19));
        // System.out.println(testThree.getHead().getVal());
        // System.out.println(testThree.getHead().getNext().getVal());

        // Test for RemoveFirst()
        // System.out.println(test.removeFirst().getVal());
        // Node current = test.getHead();
        // // System.out.println(current.getVal());
        // for (int i = 0; i < 19; i++) {
        //   System.out.println(current.getVal());
        //   current = current.getNext();
        // }

        //test for RemoveLast()
        // System.out.println("=========");

        // Node current = test.getHead();
        // System.out.println(test.removeLast().getVal());
        //   for (int i = 0; i < 20; i++) {
        //   System.out.println(current.getVal());
        //   current = current.getNext();

        // System.out.println(test.isEmpty());
        // System.out.println(test2.isEmpty());
        // test2.printDeque();


        test.addFirst(new Node(69));
        test.addFirst(new Node(70));
        test.addFirst(new Node(49));
        test.printDeque();
        Node curr = test.getHead().getNext();

        while (!(curr instanceof SentinelNode)) {
            System.out.println(curr.getVal());
            curr = curr.getNext();
        }

        Node[] nodes = new Node[10];

        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i + 1);
        }

        DLL testThree = new DLL(nodes);
        testThree.printDeque();
        testThree.addFirst(new Node(69));
        testThree.printDeque();
    }

}

}
