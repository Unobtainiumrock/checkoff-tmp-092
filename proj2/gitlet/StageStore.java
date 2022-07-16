package gitlet;

import java.io.*;
import java.util.*;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

/**
 * The stage is a focal point between staged files in the directory and commit objects.
 * It will instantiate new commits, link the new commits to their parent commits by hashID,
 * and then populate the commits with the files staged.
 */
public class StageStore extends HashSet<String> implements Save {
    private Set<String> removeStage;

    public StageStore() {
        super(); // The addStage is implicit. It is a HashSet that can be accessed using "this"
        this.removeStage = new HashSet<>();
    }

    private boolean canAdd(File file) {
        String k = sha1(file.getPath(), serialize(file));
        boolean existsInBlob = blobStore.containsKey(k);
        return !existsInBlob;
    }

    public String stage(File file) {
        String k = sha1(file.getPath(), readContents(file));
        if (canAdd(file)) {
            this.add(k);
            blobStore.put(k, readContents(file));
        }
        return k;
    }

    public boolean canRemove(File file) {
        return this.contains(file);
    }
}
