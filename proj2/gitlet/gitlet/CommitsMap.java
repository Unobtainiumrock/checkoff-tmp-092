package gitlet;
import java.util.Map;
import java.util.HashMap;
import static gitlet.Utils.*;

public class CommitsMap {
    private Map<String, byte[]> commitsMap;

    public CommitsMap() {
        this.commitsMap = new HashMap<>();
    }

    public Map<String, byte[]> getCommitsMap() {
        return this.commitsMap;
    }
}
