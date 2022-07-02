/**
 * Represent a set of nonnegative ints from 0 to maxElement for some initially
 * specified maxElement.
 */
public class BooleanSet implements SimpleSet {

    private boolean[] contains;
    private int size;

    /** Initializes a set of ints from 0 to maxElement. */
    public BooleanSet(int maxElement) {
        contains = new boolean[maxElement + 1];
        size = 0;
    }

    @Override
    /** Adds k to the set. */
    public void add(int k) {
        if (!(this.contains(k))) {
            this.contains[k] = true;
            size++;
        }
    }

    @Override
    /** Removes k from the set. */
    public void remove(int k) {
        this.contains[k] = false;
        this.size--;
    }

    @Override
    /** Return true if k is in this set, false otherwise. */
    public boolean contains(int k) {
        return contains[k];
    }

    @Override
    /** Return true if this set is empty, false otherwise. */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    /** Returns the number of items in the set. */
    public int size() {
        return this.size;
    }

    /** Returns an array containing all of the elements in this collection. */
    public int[] toIntArray() {
        int[] res = new int[this.size()];

        for (int i = 0; i < this.size(); i++) {
            if (this.contains(i)) {
                res[i] = i + 1;
            }
        }
        return res;
    }
}
