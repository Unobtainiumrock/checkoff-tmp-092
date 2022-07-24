package gitlet;

import java.io.File;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import static gitlet.Utils.*;

/**
 * @author grandpa
 */
public class StageStore extends HashSet<Map<String, String>> implements Save {

    /**
     *
     */
    private Repository repo;

    /**
     *
     * @param r rr
     */
    public StageStore(Repository r) {
        this.repo = r;
    }

    /**
     *
     * @param dualKey dk
     * @param blobStore bs
     * @return
     */
    private boolean canAdd(Map<String, String> dualKey, BlobStore blobStore) {
        boolean isOldVersion = blobStore.containsKey(dualKey);
        return !isOldVersion;
    }

    /**
     *
     * @param file a
     * @param blobStore e
     * @return
     */
    public boolean add(File file, BlobStore blobStore) {
        String fileName = file.getName();
        String version = sha1(fileName, readContents(file));
        Map<String, String> dualKey = new HashMap<>();
        dualKey.put(fileName, version);

        if (canAdd(dualKey, blobStore)) {
            super.add(dualKey);
            blobStore.put(dualKey, readContents(file));
            return true;
        }
        return false;
    }

    /**
     *
     * @param fileDualKey f
     * @return
     */
    public boolean canRemove(Map<String, String> fileDualKey) {
        return this.contains(fileDualKey);
    }

    /**
     *
     * @param file e
     * @param blobStore b
     * @return
     */
    public boolean removeFromAddStage(File file, BlobStore blobStore) {
        String fileName = file.getName();
        String version = sha1(fileName, readContents(file));
        Map<String, String> deleteKey = new HashMap<>();
        deleteKey.put(fileName, version);

        if (canRemove(deleteKey)) {
            this.remove(deleteKey);
            blobStore.remove(deleteKey);
            return true;
        }
        return false;
    }
}
