package gitlet;

import java.io.Serial;
import java.io.Serializable;

public class SerializeMe implements Serializable {

    int val;

    public SerializeMe(int val) {
        this.val = val;
    }

}
