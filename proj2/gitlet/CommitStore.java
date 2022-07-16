package gitlet;
import java.util.LinkedHashMap;

public class CommitStore extends LinkedHashMap<String, Commit> implements Save {
    Repository repo;
    Commit firstCommit;

    public CommitStore(Repository repo) {
        this.repo = repo;
        this.init(new Commit());
    }

    public Commit getFirstCommit() {
        return this.repo.getInitialized() ? this.firstCommit : null;
    }

    private void init(Commit firstCommit) {
        if (!this.repo.getInitialized()) {
            String sha1 = firstCommit.getHashID();
            this.put(sha1, firstCommit);
            this.firstCommit = firstCommit;
            this.repo.setInitialized(true);
        } else {
            System.out.println("The commit store has already been initialized\nif you wish to add " +
                    "a commit to the store, use add() instead.");
        }
    }
}
