package gitlet;
import java.util.Map;
import java.util.HashMap;

public class BlobStore extends Store<String, byte[]> {

    public BlobStore() {
        super(new HashMap<>());
    }
}
