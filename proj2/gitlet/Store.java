package gitlet;
import java.io.File;
import java.io.Serializable;
import java.util.Map;

public abstract class Store<K, V> implements Storable<K, V>, Serializable {

    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");
    public static final File BLOB_DIR = Utils.join(GITLET_DIR, ".blob");


    private Map<K,V> map;

    Store(Map<K, V> map) {
        this.map = map;
    }

    public Map<K, V> getMap() {
        return this.map;
    }

    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    };

    public boolean isEmpty() {
        return this.map.isEmpty();
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
