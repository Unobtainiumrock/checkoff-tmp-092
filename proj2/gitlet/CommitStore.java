package gitlet;
import java.io.File;
import java.util.LinkedHashMap;
import static gitlet.Utils.*;

public class CommitStore extends LinkedHashMap<String, byte[]> implements Save {
    boolean initialized = false;
    Commit firstCommit;

    public CommitStore() {
        super(new LinkedHashMap<>());
        this.init(new Commit());
    }

    public Commit getFirstCommit() {
        return this.initialized ? this.firstCommit : null;
    }

    private void init(Commit firstCommit) {
        if (!this.initialized) {
            String sha = firstCommit.getHashID();
            byte[] serializedObj = serialize(firstCommit);
            super.put(sha, serializedObj);
            this.firstCommit = firstCommit;
            this.initialized = true;
        } else {
            System.out.println("The commit store has already been initialized\nif you wish to add " +
                    "a commit to the store, use add() instead.");
        }
    }
}
