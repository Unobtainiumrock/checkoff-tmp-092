package gitlet;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

/**
 * The stage is a focal point between staged files in the directory and commit objects.
 * It will instantiate new commits, link the new commits to their parent commits by hashID,
 * and then populate the commits with
 */
public class Stage {
    List <File> toBeStaged;
    List <File> toBeUnstaged;

    public Stage() {

    }


}
