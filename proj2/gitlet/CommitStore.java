package gitlet;
import java.util.HashMap;
import java.util.LinkedHashMap;


//TODO change this to a HashMap instead, since we preserve "order" in a different manner.
public class CommitStore extends LinkedHashMap<String, Commit> implements Save {
    private Repository repo;
    private Commit head;

    public CommitStore(Repository repo) {
        Commit initial = new Commit();
        this.repo = repo;
        this.head = initial;
        this.init(initial);
    }

    // Used for when we are making a new branch.
    public CommitStore(Commit commit) {
        this.head = commit;
//        this.add(commit.getHashID(), commit);
    }

    public Commit add(String sha1, Commit commit) {
        this.put(sha1, commit);
        this.head = commit;
        return commit;
    }

    public Commit getHead() {
        return this.head;
    }

    public void setHead(Commit head) {
        this.head = head;
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
