//class Main {
//    public static void main(String[] args) {
//        // Node[] nodes = new Node[20];
//
//        // for (int i = 0; i < nodes.length; i++) {
//        //   nodes[i] = new Node(i + 1);
//        // }
//
//        DLL test = new DLL();
//
//        // for (int i = 0; i < nodes.length; i++) {
//        //   test.addLast(nodes[i]);
//        // }
//        // DLL test2 = new DLL(nodes);
//        // DLL test2 = new DLL(1,2,3,4);
//
//        // Node curr = test.getHead();
//        // System.out.println(curr.getVal());
//
//        // while (curr.hasNext()) {
//        //   curr = curr.getNext();
//        //   System.out.println(curr.getVal());
//        // }
//
//        // DLL testTwo = new DLL(new Node(69));
//        // System.out.println(testTwo.getHead().getVal());
//        // System.out.println(testTwo.getHead().getVal());
//        // System.out.println(testTwo.getHead().getPrev().getVal());
//        // System.out.println(testTwo.getHead().getNext().getVal());
//        // testTwo.add(new Node(99));
//        // System.out.println(testTwo.getHead().getNext().getVal());
//
//        // DLL testThree = new DLL();
//
//        // testThree.add(new Node(19));
//        // System.out.println(testThree.getHead().getVal());
//        // System.out.println(testThree.getHead().getNext().getVal());
//
//        // Test for RemoveFirst()
//        // System.out.println(test.removeFirst().getVal());
//        // Node current = test.getHead();
//        // // System.out.println(current.getVal());
//        // for (int i = 0; i < 19; i++) {
//        //   System.out.println(current.getVal());
//        //   current = current.getNext();
//        // }
//
//        //test for RemoveLast()
//        // System.out.println("=========");
//
//        // Node current = test.getHead();
//        // System.out.println(test.removeLast().getVal());
//        //   for (int i = 0; i < 20; i++) {
//        //   System.out.println(current.getVal());
//        //   current = current.getNext();
//
//        // System.out.println(test.isEmpty());
//        // System.out.println(test2.isEmpty());
//        // test2.printDeque();
//
//
//        test.addFirst(new Node(69));
//        test.addFirst(new Node(70));
//        test.addFirst(new Node(49));
//        test.printDeque();
//        Node curr = test.getHead().getNext();
//
//        while (!(curr instanceof SentinelNode)) {
//            System.out.println(curr.getVal());
//            curr = curr.getNext();
//        }
//
//        Node[] nodes = new Node[10];
//
//        for (int i = 0; i < nodes.length; i++) {
//            nodes[i] = new Node(i + 1);
//        }
//
//        DLL testThree = new DLL(nodes);
//        testThree.printDeque();
//        testThree.addFirst(new Node(69));
//        testThree.printDeque();
//    }
//
//}
//
//}
//=======
////package deque;
////
////public class ReplWork2 {
////    class DLL {
////        private SentinelNode _sentinel;
////        private int _size = 0;
////
////        DLL() {
////            this(new SentinelNode(0));
////        }
////
////        DLL(Node node) {
////            this._sentinel.setNext(node);
////            this._sentinel.setPrev(node);
////            node.setPrev(this._sentinel);
////            node.setNext(this._sentinel);
////            this._size++;
////        }
////
////        DLL(Node[] nodes) {
////            this._sentinel = new SentinelNode(0);
////
////            for (int i = 0; i < nodes.length; i++) {
////                Node node = nodes[i];
////
////                if (i == 0) {
////                    this._sentinel.setNext(node);
////                    this._sentinel.setPrev(node);
////                    node.setPrev(this._sentinel);
////                    node.setNext(this._sentinel);
////                }
////                this._sentinel.getPrev().setNext(node);
////                node.setNext(this._sentinel);
////            }
////            this._size +=nodes.length;
////        }
////
////
//////    ======
////
////        public Node getHead() {
////            return this._head;
////        }
////
////        public Node getTail() {
////            return this._tail;
////        }
////
////        public int size() {
////            return this._size;
////        }
////
////        // @Override
////        public boolean isEmpty() {
////            return this._size == 0;
////        }
////
////        public void printDeque() {
////            Node curr = this._sentinel;
////
////            for (int i = 0; i < this._size; i++) {
////                System.out.print(curr + " ");
////                curr = curr.getNext();
////            }
////
////        }
////
////        public Node removeFirst() {
////            Node oldHead = this._sentinel.getNext(); // Variable for clarity.
////            Node oldHeadVal = new Node(oldHead.getVal());
////
////            this._sentinel.setNext(oldHead.getNext()); // Update head.
////
////            return oldHeadVal;
////        }
////
////        public Node removeLast() {
////            Node oldTail = this._sentinel.getPrev(); // Variable for clarity.
////            Node oldTailVal = new Node(oldTail.getVal());
////
////            this._sentinel.setPrev(oldTail.getPrev()); // Update tail.
////
////            return oldTailVal;
////        }
////
////        public void addFirst(Node node) {
////            Node head = this._sentinel.getNext(); // Variable for clarity.
////            Node tail = this._sentinel.getPrev(); // Variable for clarity.
////
////            node.setNext(head);
////            head.setPrev(node);
////            node.setPrev(tail);
////            tail.setNext(node);
////        }
////
////        public void addLast(Node node) {
////            Node head = this._sentinel.getNext(); // Variable for clarity.
////            Node tail = this._sentinel.getPrev(); // Variable for clarify.
////
////            node.setPrev(tail);
////            tail.setNext(node);
////            node.setNext(head);
////            head.setPrev(node);
////
////        }
////
////        public Node get(int index) {
////            if (this._size == 0 || index > this._size) {
////                return null;
////            } else {
////                DLL current = this;
////                for (int i = 0; i < index; i++) {
////                    current._head = current._head._next;
////                }
////                return current._head; //fix the return type
////            }
////        }
////    }
////}
////
////class Main {
////    public static void main(String[] args) {
////        // Node[] nodes = new Node[20];
////
////        // for (int i = 0; i < nodes.length; i++) {
////        //   nodes[i] = new Node(i + 1);
////        // }
////
////        DLL test = new DLL();
////
////        // for (int i = 0; i < nodes.length; i++) {
////        //   test.addLast(nodes[i]);
////        // }
////        // DLL test2 = new DLL(nodes);
////        // DLL test2 = new DLL(1,2,3,4);
////
////        // Node curr = test.getHead();
////        // System.out.println(curr.getVal());
////
////        // while (curr.hasNext()) {
////        //   curr = curr.getNext();
////        //   System.out.println(curr.getVal());
////        // }
////
////        // DLL testTwo = new DLL(new Node(69));
////        // System.out.println(testTwo.getHead().getVal());
////        // System.out.println(testTwo.getHead().getVal());
////        // System.out.println(testTwo.getHead().getPrev().getVal());
////        // System.out.println(testTwo.getHead().getNext().getVal());
////        // testTwo.add(new Node(99));
////        // System.out.println(testTwo.getHead().getNext().getVal());
////
////        // DLL testThree = new DLL();
////
////        // testThree.add(new Node(19));
////        // System.out.println(testThree.getHead().getVal());
////        // System.out.println(testThree.getHead().getNext().getVal());
////
////        // Test for RemoveFirst()
////        // System.out.println(test.removeFirst().getVal());
////        // Node current = test.getHead();
////        // // System.out.println(current.getVal());
////        // for (int i = 0; i < 19; i++) {
////        //   System.out.println(current.getVal());
////        //   current = current.getNext();
////        // }
////
////        //test for RemoveLast()
////        // System.out.println("=========");
////
////        // Node current = test.getHead();
////        // System.out.println(test.removeLast().getVal());
////        //   for (int i = 0; i < 20; i++) {
////        //   System.out.println(current.getVal());
////        //   current = current.getNext();
////
////        // System.out.println(test.isEmpty());
////        // System.out.println(test2.isEmpty());
////        // test2.printDeque();
////
////
////        test.addFirst(new Node(69));
////        test.addFirst(new Node(70));
////        test.addFirst(new Node(49));
////        test.printDeque();
////        Node curr = test.getHead().getNext();
////
////        while (!(curr instanceof SentinelNode)) {
////            System.out.println(curr.getVal());
////            curr = curr.getNext();
////        }
////
////        Node[] nodes = new Node[10];
////
////        for (int i = 0; i < nodes.length; i++) {
////            nodes[i] = new Node(i + 1);
////        }
////
////        DLL testThree = new DLL(nodes);
////        testThree.printDeque();
////        testThree.addFirst(new Node(69));
////        testThree.printDeque();
////    }
////
////}
////
