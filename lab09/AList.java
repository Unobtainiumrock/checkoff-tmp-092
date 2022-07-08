import java.lang.reflect.Array;
import java.util.*;
import java.lang.Iterable;

/**
 * A AList is a list of integers. Like SLList, it also hides the terrible
 * truth of the nakedness within, but uses an array as it's base.
 */
public class AList<Item> extends ArrayList<Item> implements Iterable<Item> {

    /** Creates an empty AList. */
    public AList() {
        super();
    }

    /** Returns the size of the list. */
    public int size() {
        return super.size();
    }

    /** Adds item to the back of the list. */
    public void addLast(Item item) {
        super.add(item);
    }

    @Override
    public String toString() {
        String result = "";
        int p = 0;
        boolean first = true;
        while (p != super.size()) {
            if (first) {
                result += super.get(p).toString();
                first = false;
            } else {
                result += " " + super.get(p).toString();
            }
            p += 1;
        }
        return result;
    }

    /** Returns whether this and the given list or object are equal. */
    public boolean equals(Object o) {
        AList other = (AList) o;
        return Arrays.deepEquals(super.toArray(), other.getItems());
    }

    private Item[] getItems() {
        return (Item[]) super.toArray();
    }

    public Iterator<Item> iterator() {
        return super.iterator();
    }
}
