package gitlet;
import java.io.File;
import java.util.LinkedHashMap;
import static gitlet.Utils.*;
import static gitlet.Repository.*;

public class CommitStore extends LinkedHashMap<String, Commit> implements Save {
    Commit firstCommit;

    public CommitStore() {
        this.init(new Commit());
    }

    public Commit getFirstCommit() {
        return initialized ? this.firstCommit : null;
    }

    private void init(Commit firstCommit) {
        if (!initialized) {
            String sha1 = firstCommit.getHashID();
            this.put(sha1, firstCommit);
            this.firstCommit = firstCommit;
            initialized = true;
        } else {
            System.out.println("The commit store has already been initialized\nif you wish to add " +
                    "a commit to the store, use add() instead.");
        }
    }
}
