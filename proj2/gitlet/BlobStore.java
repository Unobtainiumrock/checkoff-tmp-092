package gitlet;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Grandpa Weekend
 */
public class BlobStore extends HashMap<Map<String, String>,
        byte[]> implements Save {

    /**
     *
     * @param repo
     */
    public BlobStore() {
    }
}
