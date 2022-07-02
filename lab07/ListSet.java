import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represent a set of ints.
 */
public class ListSet implements SimpleSet {

    List<Integer> elems;

    public ListSet() {
        elems = new ArrayList<Integer>();
    }

    /** Adds k to the set. */
    public void add(int k) {
        if(!(this.contains(k))) {
            this.elems.add(k);
        }
    }

    /** Removes k from the set. */
    public void remove(int k) {
        Integer toRemove = k;
        this.elems.remove(toRemove);
    }


    /** Return true if k is in this set, false otherwise. */
    public boolean contains(int k) {
        return this.elems.contains(k);
    }

    @Override
    /** Return true if this set is empty, false otherwise. */
    public boolean isEmpty() {
        return this.elems.isEmpty();
    }

    @Override
    /** Returns the number of items in the set. */
    public int size() {
        return this.elems.size();
    }

    /** Returns an array containing all of the elements in this collection. */
    public int[] toIntArray() {
        int[] res = new int[this.size()];

        if (this.isEmpty()) {
            return res;
        }

        int i = 0;
        Iterator<Integer> iter = this.elems.iterator();

        while (iter.hasNext()) {
            res[i] = iter.next();
        }

        return res;
    }
}
