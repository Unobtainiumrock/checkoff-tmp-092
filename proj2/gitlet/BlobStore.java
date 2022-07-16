package gitlet;
import java.util.Map;
import java.util.HashMap;

public class BlobStore extends HashMap<Map<String, String>, byte[]> implements Save {
    Repository repo;
    // Add conditional logic for add, contains, and remove, if needed
    // otherwise all the usual HashMap methods are accessible via inheritance
    public BlobStore(Repository repo) {
        this.repo = repo;
    }
}
