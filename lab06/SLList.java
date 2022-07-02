/**
 * An SLList is a list of integers, which encapsulates the
 * naked linked list structure.
 */
public class SLList {

    /**
     * IntListNode is a nested class that represents a single node in the
     * SLList, storing an item and a reference to the next IntListNode.
     */
    private static class IntListNode {
        /*
         * The access modifiers inside a private nested class are irrelevant:
         * both the inner class and the outer class can access these instance
         * variables and methods.
         */
        public int item;
        public IntListNode next;

        public IntListNode(int item, IntListNode next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntListNode that = (IntListNode) o;
            return item == that.item;
        }

        @Override
        public String toString() {
            return item + "";
        }

    }

    /* The first item (if it exists) is at sentinel.next. */
    private IntListNode sentinel;
    private int size;

    /** Creates an empty SLList. */
    public SLList() {
        sentinel = new IntListNode(42, null);
        sentinel.next = sentinel;
        size = 0;
    }

    public SLList(int x) {
        sentinel = new IntListNode(42, null);
        sentinel.next = new IntListNode(x, null);
        sentinel.next.next = sentinel;
        size = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SLList slList = (SLList) o;
        if (size != slList.size) return false;

        IntListNode l1 = sentinel.next;
        IntListNode l2 = slList.sentinel.next;

        while (l1 != sentinel && l2 != slList.sentinel) {
            if (!l1.equals(l2)) return false;
            l1 = l1.next;
            l2 = l2.next;
        }
	if (l1 != sentinel || l2 != slList.sentinel) {
		return false;
	}
        return true;
    }

    @Override
    public String toString() {
        IntListNode l = sentinel.next;
        String result = "";

        while (l != sentinel) {
            result += l + " ";
            l = l.next;
        }

        return result.trim();
    }

    /** Returns an SLList consisting of the given values. */
    public static SLList of(int... values) {
        SLList list = new SLList();
        for (int i = values.length - 1; i >= 0; i -= 1) {
            list.addFirst(values[i]);
        }
        return list;
    }

    /** Returns the size of the list. */
    public int size() {
        return size;
    }

    /** Adds x to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntListNode(x, sentinel.next);
        size += 1;
    }

    /** Return the value at the given index. */
    public int get(int index) {
        IntListNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /** Adds x to the list at the specified index. */
    public void add(int index, int x) {
        IntListNode curr = index == 0 ? this.sentinel : this.sentinel.next;

        if (index  - 1 >= this.size) {
            IntListNode node = new IntListNode(x, curr.next);
            curr.next = node;
            this.size++;
            return;
        }

        for (int i = 0; i < index - 1; i++) {
            curr = curr.next;
        }

        IntListNode node = new IntListNode(x, curr.next);
        curr.next = node;
        this.size++;
    }

    /** Destructively reverses this list. */
    public void reverse() {
        if (this.size == 0) {
            return;
        }

        IntListNode curr = this.sentinel.next;
        int[] stack = new int[this.size];

        for (int i = 0; i < this.size; i++) {
            stack[i] = curr.item;
            curr = curr.next;
        }

        curr = this.sentinel;

        for (int i = this.size - 1; i > 0; i--) {
            curr.next = new IntListNode(stack[i], this.sentinel);
            curr = curr.next;
        }
        curr.next = new IntListNode(stack[0], this.sentinel);
    }

//    public IntListNode recurse(IntListNode node) {
//        if (node.item == 42) {
//            return node;
//        }
//        addFirst(node.item);
//
//        return recurse(node.next);
//    }
}
