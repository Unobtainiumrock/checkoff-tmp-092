package gitlet;
import java.util.Map;
import java.util.LinkedHashMap;
import static gitlet.Utils.*;

public class CommitsMap {
    private Map<String, byte[]> commitsMap;

    public CommitsMap() {
        this.commitsMap = new LinkedHashMap<>();
    }

    public Map<String, byte[]> getCommitsMap() {
        return this.commitsMap;
    }
}
