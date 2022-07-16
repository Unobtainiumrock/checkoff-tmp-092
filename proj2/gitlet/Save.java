package gitlet;
import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public interface Save extends Serializable {
    File CWD = new File(System.getProperty("user.dir"));
    File GITLET_DIR = Utils.join(CWD, ".gitlet");
    File STAGE_DIR = Utils.join(GITLET_DIR, ".staging.ser");
    File BLOB_DIR = Utils.join(GITLET_DIR, ".blobs.ser");
    File COMMIT_DIR = Utils.join(GITLET_DIR, ".commits.ser");
    File BRANCH_DIR = Utils.join(GITLET_DIR, ".branches");

    default void save(File file) {
        writeObject(file, this);
    };
}
