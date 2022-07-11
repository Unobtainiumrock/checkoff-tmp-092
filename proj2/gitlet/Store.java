package gitlet;
import java.util.Map;
import java.util.LinkedHashMap;
import static gitlet.Utils.*;

public class Store {
    boolean initialized = false;
    gitlet.Commit firstCommit;
    private Map<String, byte[]> commitsMap;

    public Store() {
        this.commitsMap = new LinkedHashMap<>();
    }

    public Map<String, byte[]> getCommitsMap() {
        return this.commitsMap;
    }

    public void add(String sha, byte[] serializedObj) {
        this.commitsMap.put(sha, serializedObj);
    }

    // May not need this.
    public void remove(String sha) {
        this.commitsMap.remove(sha);
    }

    public gitlet.Commit getFirstCommit() {
        return this.initialized ? this.firstCommit : null;
    }

    public void init(gitlet.Commit firstCommit) {
        String sha = sha1(serialize(firstCommit));
        byte[] serializedObj = serialize(firstCommit);

        if (!this.initialized) {
            this.commitsMap.put(sha, serializedObj);
            this.firstCommit = firstCommit;
            this.initialized = true;
        } else {
            System.out.println("The store has already been initialized\nif you wish to add " +
                    "a commit to the store, use add() instead.");
        }
    }
}
