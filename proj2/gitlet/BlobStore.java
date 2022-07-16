package gitlet;
import java.util.Map;
import java.util.HashMap;

public class BlobStore extends HashMap<Map<String, String>, byte[]> implements Save {
    private Repository repo;

    public BlobStore(Repository repo) {
        this.repo = repo;
    }

    public Repository getRepo() {
        return this.repo;
    }
}
