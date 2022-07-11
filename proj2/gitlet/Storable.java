package gitlet;

public interface Storable<K, V> {
    public V get(K key);

    public boolean add(K key, V value);
}
