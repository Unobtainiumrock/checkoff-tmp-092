package gitlet;
import java.util.Map;

public abstract class Store<K, V> implements Storable<K, V> {
    private Map<K,V> map;

    Store(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public boolean add(K key, V value) {
        if (!this.map.containsKey(key)) {
            this.map.put(key, value);
            return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        return this.map.get(key);
    }
}
