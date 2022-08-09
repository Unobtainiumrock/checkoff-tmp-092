package byow.Core;

import java.io.File;
import java.io.Serializable;

public interface Save extends Serializable {
    File CWD = new File(System.getProperty("user.dir"));
    File STATE_DIR = Utils.join(CWD, ".state");

}

