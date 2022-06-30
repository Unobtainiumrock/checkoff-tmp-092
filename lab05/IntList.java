/** A data structure to represent a Linked List of Integers.
 * Each IntList represents one node in the overall Linked List.
 *
 * @author Maurice Lee and Wan Fung Chui
 */


public class IntList {
    // Y'all shoulda made these private. I tried making them private and then using getters/setters
    // to practice good encapsulation.
    // I'm changing them back to public, but I'm too lazy to remove all my getters/setters and change the rest of the code.
    public int item;
    public IntList next;

    public IntList(int item, IntList next) {
        this.item = item;
        this.next = next;
    }

    public IntList(int item) {
        this(item, null);
    }

    public IntList() {}

    public int getItem() {
        return this.item;
    }


    public IntList getNext() {
        return this.next;
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public static IntList of(int... items) {
        if (items.length == 0) {
            return null;
        }

        IntList head = new IntList(items[0]);
        IntList last = head;

        for (int i = 1; i < items.length; i++) {
            last.next = new IntList(items[i]);
            last = last.next;
        }

        return head;
    }

    /**
     * Returns [position]th item in this list. Throws IllegalArgumentException
     * if index out of bounds.
     *
     * @param position, the position of element.
     * @return The element at [position]
     */
    public int get(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Index out of bounds.");
        }

        IntList curr = this;
        int k = 0;

        while(k < position) {
            if (!(curr.hasNext())) {
                throw new IllegalArgumentException("Index out of bounds.");
            }
            curr = curr.next;
            k++;
        }
        return curr.getItem();
    }

    /**
     * Returns the string representation of the list. For the list (1, 2, 3),
     * returns "1 2 3".
     *
     * @return The String representation of the list.
     */
    public String toString() {
        IntList curr = this;
        String result = Integer.toString(curr.item);
        while (curr.hasNext()) {
            result += " ";
            result += Integer.toString(curr.next.item);
            curr = curr.getNext();
        }
        return result;
    }

    /**
     * Returns whether this and the given list or object are equal.
     *
     * NOTE: A full implementation of equals requires checking if the
     * object passed in is of the correct type, as the parameter is of
     * type Object. This also requires we convert the Object to an
     * IntList, if that is legal. The operation we use to do this is called
     * casting, and it is done by specifying the desired type in
     * parenthesis. An example of this is on line 84.
     *
     * @param obj, another list (object)
     * @return Whether the two lists are equal.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || !(obj instanceof IntList)) {
            return false;
        }

        IntList otherLst = (IntList) obj;
        IntList a = this;
        boolean res = otherLst.getItem() == a.getItem();

        while(otherLst.hasNext() && a.hasNext()) {
            otherLst = otherLst.getNext();
            a = a.getNext();
            res = res && (a.getItem() == otherLst.getItem());
        }

        return res && !(otherLst.hasNext() || a.hasNext());

    }

    /**
     * Adds the given value at the end of the list.
     *
     * @param value, the int to be added.
     */
    // The code we were given doesn't allow for add to be O(1)
    // It is O(1) since we don't have a direct reference to the tail.
    public void add(int value) {
        IntList curr = this;
        while (curr.hasNext()) {
            curr = curr.next;
        }
        curr.next = new IntList(value);
    }

    /**
     * Returns the smallest element in the list.
     *
     * @return smallest element in the list
     */
    public int smallest() {
        IntList curr = this;
        int smallest = curr.item;

        while(curr.hasNext()) {
            curr = curr.getNext();
            if (curr.getItem() < smallest) {
                smallest = curr.getItem();
            }
        }
        return smallest;
    }

    /**
     * Returns the sum of squares of all elements in the list.
     *
     * @return The sum of squares of all elements.
     */
    public int squaredSum() {
        IntList curr = this;
        int res = curr.getItem() * curr.getItem();

        while (curr.hasNext()) {
            curr = curr.getNext();
            res += (curr.getItem() * curr.getItem());
        }
        return res;
    }

    /**
     * Destructively squares each item of the list.
     *
     * @param L list to destructively square.
     */
    public static void dSquareList(IntList L) {
        while (L.hasNext()) {
            L.item = L.item * L.item;
            L = L.next;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.item * L.item, null);
        IntList ptr = res;
        L = L.next;
        while (L != null) {
            ptr.next = new IntList(L.item * L.item, null);
            L = L.next;
            ptr = ptr.next;
        }
        return res;
    }

    /** Returns a list equal to L with all elements squared. Non-destructive.
     *
     * @param L list to non-destructively square.
     * @return the squared list.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.item * L.item, squareListRecursive(L.next));
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
    public static IntList dcatenate(IntList A, IntList B) {
        //TODO: YOUR CODE HERE
        return null;
    }

    /**
     * Returns a new IntList consisting of A followed by B,
     * non-destructively.
     *
     * @param A list to be on the front of the new list.
     * @param B list to be on the back of the new list.
     * @return new list with A followed by B.
     */
     public static IntList catenate(IntList A, IntList B) {
        //TODO: YOUR CODE HERE
        return null;
     }
}