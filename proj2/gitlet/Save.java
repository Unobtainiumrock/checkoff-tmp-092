package gitlet;
import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public interface Save {
    File CWD = new File(System.getProperty("user.dir"));
    File GITLET_DIR = Utils.join(CWD, ".gitlet");
    File STAGE_DIR = Utils.join(GITLET_DIR, ".staging");
    File BLOB_DIR = Utils.join(GITLET_DIR, ".blobs");
    File COMMIT_DIR = Utils.join(GITLET_DIR, ".commits");
    File BRANCH_DIR = Utils.join(GITLET_DIR, ".branches");
    
    default void save(File file) {
        writeObject(file, (Serializable) this);
    };
}
