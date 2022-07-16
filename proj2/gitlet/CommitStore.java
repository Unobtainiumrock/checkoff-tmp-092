package gitlet;
import java.util.LinkedHashMap;

public class CommitStore extends LinkedHashMap<String, Commit> implements Save {
    Repository repo;
    Commit head;

    public CommitStore(Repository repo) {
        this.repo = repo;
        this.init(new Commit());
    }

    public Commit add(String sha1, Commit commit) {
        this.put(sha1, commit);
        this.head = commit;
        return commit;
    }

    public Commit getHead() {
        return this.repo.getInitialized() ? this.head : null;
    }

    private void init(Commit firstCommit) {
        if (!this.repo.getInitialized()) {
            String sha1 = firstCommit.getHashID();
            this.add(sha1, firstCommit);
            this.repo.setInitialized(true);
        } else {
            System.out.println("The commit store has already been initialized\nif you wish to add " +
                    "a commit to the store, use add() instead.");
        }
    }
}
