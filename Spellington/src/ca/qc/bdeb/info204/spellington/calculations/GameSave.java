package ca.qc.bdeb.info204.spellington.calculations;

import java.io.Serializable;

/**
 * The game save for the user.
 *
 * @author Cristian Aldea
 */
public class GameSave implements Serializable {

    private String saveName;
    private int sLevel;
    private int sXP;

    public GameSave(String saveName) {
        this.saveName = saveName;
        sLevel = 0;
        sXP = 0;

    }

}
