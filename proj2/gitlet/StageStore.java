package gitlet;

import java.io.*;
import java.util.*;
import static gitlet.Utils.*;

/**
 * The stage is a focal point between staged files in the directory and commit objects.
 * It will instantiate new commits, link the new commits to their parent commits by hashID,
 * and then populate the commits with the files staged.
 */
public class StageStore extends HashSet<Map<String, String>> implements Save {
    private Repository repo;
    private Set<String> removeStage;

    public StageStore(Repository repo) {
        this.repo = repo;
        this.removeStage = new HashSet<>();
    }

    private boolean canAdd(Map<String, String> dualKey) {
        boolean isOldVersion = this.repo.getBlobStore().containsKey(dualKey);
        return !isOldVersion;
    }

    public boolean stage(File file) {
        String fileName = file.getName();
        String version = sha1(fileName, readContents(file));
        Map<String, String> dualKey = new HashMap<>();
        dualKey.put(fileName, version);

        if (canAdd(dualKey)) {
            this.add(dualKey); // Add a dual key of <fileName, version> to the StageStore's Set
            this.repo.getBlobStore().put(dualKey, readContents(file)); // Map the dualKey to the current file version.
            return true;
        }
        return false;
    }

    public boolean canRemove(File file) {
        return this.contains(file);
    }
}
