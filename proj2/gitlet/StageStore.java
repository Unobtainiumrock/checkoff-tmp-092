package gitlet;

import java.io.*;
import java.util.*;
import static gitlet.Utils.*;
//TODO de-couple the StageStore and removeStage. See the abstract class Store which this will eventually
//TODO extend.
/**
 * The StageStore contains a HashSet of dual-keys which are used as a single lookup key for the BlobStore.
 * Each key consists of a HashMap<file name, sha1(filename, file contents)>
 *
 * Example of grabbing something from the BlobStore:
 *  Map<String, String> dualKey = new HashMap<>();
 *  dualKey.put(filename, sha1(fileName, readContents(file)));
 *
 *  blobStore.get(dualKey);
 */
public class StageStore extends HashSet<Map<String, String>> implements Save {
    private Repository repo;
    private Set<Map<String, String>> removeStage;

    public StageStore(Repository repo) {
        this.repo = repo;
          this.removeStage = new HashSet<>();
    }

    private boolean canAdd(Map<String, String> dualKey) {
        boolean isOldVersion = this.repo.getBlobStore().containsKey(dualKey); // might bug out later.
        return !isOldVersion;
    }

    public boolean add(File file, BlobStore blobStore) {
        String fileName = file.getName();
        String version = sha1(fileName, readContents(file));
        Map<String, String> dualKey = new HashMap<>();
        dualKey.put(fileName, version);

            if (canAdd(dualKey)) {
            super.add(dualKey); // Add a dual key of <fileName, version> to the StageStore's Set.
            blobStore.put(dualKey, readContents(file)); // Map the dualKey to the current file version.
            return true;
        }
        return false;
    }

    public Set<Map<String, String>> getRemoveStage() {
        return this.removeStage;
    }

    public boolean canRemove(Map<String, String> fileDualKey) {
        return this.contains(fileDualKey);
    }

    public boolean removeFromStage(File file) {
        String fileName = file.getName();
        String version = sha1(fileName, readContents(file));
        Map<String, String> deleteKey = new HashMap<>();
        deleteKey.put(fileName, version);

        if (canRemove(deleteKey)) {
            this.remove(deleteKey);
            this.repo.getBlobStore().remove(deleteKey);
            return true;
        }
        return false;
    }
}
