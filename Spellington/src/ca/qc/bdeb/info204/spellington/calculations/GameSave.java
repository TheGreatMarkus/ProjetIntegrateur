package ca.qc.bdeb.info204.spellington.calculations;

import java.io.Serializable;

/**
 *
 * @author Fallen Angel
 */
public class GameSave implements Serializable {

    private String saveName;
    private int spellingtonLevel;

    public GameSave(String saveName) {
        spellingtonLevel = 0;
    }

}
