package deque;

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
}

class SentinelNode extends Node {
    SentinelNode(int val) {
        super(val);
    }
}

class DLL {
    private Node _head;
    private Node _tail;
    private int _size = 0;

    DLL() {
        this(new SentinelNode(0));
    }

    DLL(Node node) {
        if (node instanceof SentinelNode) {
            this._head = node;
        } else {
            this._head.setNext(node);   
            node.setPrev(this._head);
            this._size++;
        }
        this._tail = node;
    }

    DLL(Node[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (i == 0) {
                this._head = nodes[i];
                this._tail = nodes[i];
            } else {
                nodes[i].setPrev(this._tail);
                this._tail.setNext(nodes[i]);
                this._head.setPrev(nodes[i]);
                this._tail = nodes[i];
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

	@Override
	public boolean isEmpty() {
		return this._size == 0;
	}

	public void printDeque() {
		for (int i = 0; i < this._size; i++) {
			System.out.print(this._head.getVal() + " ");
			this._head = this.etNext();
		}
		System.out.println(" ");
	}

	public Node removeFirst() {
		if (this._head instanceof SentinelNode) {
			this._head = this._head.getNext();
		}
		Node n = new Node(this._head.getVal());
		this._head.getNext().setPrev(this._tail);
		this._head = this._head.getNext();
		return n;
	}

	public Node get(int index) {
		if (this._size == 0 || index > this._size) {
			return null;
		} else {
			for (int i = 0; i <= index; i++) {

			}
		}
	}

	//hello!
	public Node removeLast() {
		Node n = new Node(this._tail.getVal());

		this._tail.getPrev().setNext(this._head);
		this._tail = this._tail.getPrev();

		return n;
	}

	public void addFirst(Node node) {
		node.setNext(this._head.getNext());
		this._tail.setPrev(node);
		node.setPrev(this._head);
		this._head.setNext(node);
	}

	public void addLast(Node node) {
		node.setPrev(this._tail);
		this._tail.setNext(node);
		this._head.setPrev(node);
		node.setNext(this._head);
		this._tail = node;
	}
}

class Main {
	public static void main(String[] args) {
		Node[] nodes = new Node[20];

		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i + 1);
		}

		DLL test = new DLL();

		for (int i = 0; i < nodes.length; i++) {
			test.addLast(nodes[i]);
		}
		DLL test2 = new DLL(nodes);

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

		System.out.println(test.isEmpty());
		System.out.println(test2.isEmpty());
		test2.printDeque();
	}
}

