import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

public class HashMap<K, V> implements Map61BL<K, V> {
    LinkedList<Entry<K, V>>[] table;
    //    ArrayList<LinkedList<Entry<K, V>>> table;
    private int size = 0;
    private int capacity;
    private double loadFactor;

    public HashMap() {
        this(16);
    }

    public HashMap(int capacity) {
        this(capacity, 0.75);
    }

    public HashMap(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.table = new LinkedList[this.capacity];
    }

    // Convert hash code to a valid index for the table.
    private int helper(int hashCode) {
        // primes
        int a = 3;
        int b = 5;
        int c = 137;
        int withinRange = ((a * hashCode + b) % c) % table.length;

        if (withinRange < 0) {
            withinRange = withinRange + table.length;
        }
        return withinRange;
    }

    public int capacity() {
        return this.capacity;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.table.length; i++) {
            table[i] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return table[helper(key.hashCode())] != null;
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            LinkedList<Entry<K, V>> el = table[helper(key.hashCode())];
            return el.getFirst().getValue();
        }
        return null;
    }

    @Override
    public void put(K key, V value) {

        if (!this.containsKey(key)) {
            this.size++;
        }

        double lf = this.size() / ((double) (this.capacity));

        if (lf > this.loadFactor) {
            LinkedList<Entry<K, V>>[] tmp = new LinkedList[this.capacity * 2];
            this.capacity *= 2;

            for (int i = 0; i < this.table.length; i++) {
                tmp[i] = this.table[i];
            }

            this.table = tmp;
        }


        Entry<K, V> e = new Entry(key, value);

        int hashCode = helper(key.hashCode());

        if (table[hashCode] == null) {
            table[hashCode] = new LinkedList<>();

        }

        table[hashCode].addFirst(e);

    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            int k = helper(key.hashCode());
            LinkedList<Entry<K, V>> el = table[k];
            V val = el.getFirst().getValue();
            table[k] = null; // Either I null it out, or I removeFist from the thing.
            this.size--;
            return val;
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<K> iterator() {
        List<K> keys = Arrays.stream(this.table)
                .filter((item) -> item != null)
                .map((ll) -> ll.getFirst())
                .map((entry) -> entry.getKey())
                .collect(Collectors.toList());
        return keys.iterator();
    }

    private static class Entry<K, V> {
        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public final K getKey() {
            return this.key;
        }

        public final V getValue() {
            return this.value;
        }

        public final V setValue(V newValue) {
            V oldValue = this.value;
            this.value = newValue;
            return oldValue;
        }

        public final String toString() {
            return this.key + "=" + this.value;
        }


        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}