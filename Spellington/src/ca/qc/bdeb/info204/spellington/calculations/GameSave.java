package ca.qc.bdeb.info204.spellington.calculations;

import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy.EnemyType;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The game save for the user.
 *
 * @author Cristian Aldea
 */
public class GameSave implements Serializable {

    private int sLevel;
    private int sXP;
    private boolean lvl1Complete;
    private boolean lvl2Complete;
    private boolean lvl3Complete;
    private boolean lvl4Complete;
    private ArrayList<Integer> knownSpellsIDs;
    private ArrayList<EnemyType> knownEnemies;

    public GameSave() {
        sLevel = 0;
        sXP = 0;

        lvl1Complete = true;
        lvl2Complete = true;
        lvl3Complete = true;
        lvl4Complete = true;

        knownSpellsIDs = new ArrayList<>();
        knownEnemies = new ArrayList<>();

    }

    public void completeLevel(int level) {
        switch (level) {
            case 1:
                lvl1Complete = true;
                break;
            case 2:
                if (lvl1Complete) {
                    lvl2Complete = true;
                }
                break;
            case 3:
                if (lvl1Complete && lvl2Complete) {
                    lvl3Complete = true;
                }
                break;
            case 4:
                if (lvl1Complete && lvl2Complete && lvl3Complete) {
                    lvl4Complete = true;
                }
                break;

        }
    }

    public void newKnownSpell(Integer newSpell) {
        knownSpellsIDs.add(newSpell);
    }

    public void newKnownEnnemy(EnemyType enemyType) {
        knownEnemies.add(enemyType);
    }

    public int getsLevel() {
        return sLevel;
    }

    public void setsLevel(int sLevel) {
        this.sLevel = sLevel;
    }

    public int getsXP() {
        return sXP;
    }

    public void setsXP(int sXP) {
        this.sXP = sXP;
    }

    public boolean isLvl1Complete() {
        return lvl1Complete;
    }

    public void setLvl1Complete(boolean lvl1Complete) {
        this.lvl1Complete = lvl1Complete;
    }

    public boolean isLvl2Complete() {
        return lvl2Complete;
    }

    public void setLvl2Complete(boolean lvl2Complete) {
        this.lvl2Complete = lvl2Complete;
    }

    public boolean isLvl3Complete() {
        return lvl3Complete;
    }

    public void setLvl3Complete(boolean lvl3Complete) {
        this.lvl3Complete = lvl3Complete;
    }

    public boolean isLvl4Complete() {
        return lvl4Complete;
    }

    public void setLvl4Complete(boolean lvl4Complete) {
        this.lvl4Complete = lvl4Complete;
    }

    public ArrayList<Integer> getKnownSpellsIDs() {
        return knownSpellsIDs;
    }

    public ArrayList<EnemyType> getKnownEnemies() {
        return knownEnemies;
    }

    public void setKnownSpellsIDs(ArrayList<Integer> knownSpellsIDs) {
        this.knownSpellsIDs = knownSpellsIDs;
    }

}
