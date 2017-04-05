package ca.qc.bdeb.info204.spellington.gameentities.enemies;

import ca.qc.bdeb.info204.spellington.gameentities.LivingEntity;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

/**
 * A LivingEntity opposing the player.
 *
 * @author Celtis
 * @see LivingEntity
 */
public abstract class Enemy extends LivingEntity {

    public static enum EnemyType {
        KEEPER,
        GUARD,
        ARCHER,
        CROSSBOWMAN,
        PYROMANCER,
        CRYOMANCER,
        ELECTROMANCER,
        FIRE_SLIME,
        ICE_SLIME,
        LIGHTNING_SLIME,
        BOSS
    }

    protected ArrayList<String> droppableSpells = new ArrayList<>();
    protected int xpOnKill;
    protected ElementalType damageType;
    protected EnemyType enemyType;
    protected int damage;

    public Enemy(float x, float y, float width, float height, MouvementState mouvementState, float GRAVITY_MODIFIER, int maxLifePoint, EnemyType enemyType) {
        super(x, y, width, height, mouvementState, GRAVITY_MODIFIER, maxLifePoint);
        this.enemyType = enemyType;
        switch (this.enemyType) {
            case KEEPER:
                this.xpOnKill = 0;
                this.damageType = ElementalType.NEUTRAL;
                this.damage = 12;
                break;
            case GUARD:
                this.xpOnKill = 0;
                this.damageType = ElementalType.LIGHTNING;
                this.damage = 12;
                break;
            case ARCHER:
                this.xpOnKill = 0;
                this.damageType = ElementalType.NEUTRAL;
                this.damage = 5;
                break;
            case CROSSBOWMAN:
                this.xpOnKill = 0;
                this.damageType = ElementalType.LIGHTNING;
                this.damage = 20;
                break;
            case PYROMANCER:
                this.xpOnKill = 0;
                this.damageType = ElementalType.FIRE;
                this.damage = 30;
                break;
            case CRYOMANCER:
                this.xpOnKill = 0;
                this.damageType = ElementalType.ICE;
                this.damage = 20;
                break;
            case ELECTROMANCER:
                this.xpOnKill = 0;
                this.damageType = ElementalType.LIGHTNING;
                this.damage = 50;
                break;
            case FIRE_SLIME:
                this.xpOnKill = 0;
                this.damageType = ElementalType.FIRE;
                this.damage = 10;
                break;
            case ICE_SLIME:
                this.xpOnKill = 0;
                this.damageType = ElementalType.ICE;
                this.damage = 10;
                break;
            case LIGHTNING_SLIME:
                this.xpOnKill = 0;
                this.damageType = ElementalType.LIGHTNING;
                this.damage = 10;
                break;
            case BOSS:
                this.xpOnKill = 0;
                this.damageType = ElementalType.LIGHTNING;
                this.damage = 12;
                break;
        }

    }

    public ArrayList<String> getDroppableSpells() {
        return droppableSpells;
    }

    public void setDroppableSpells(ArrayList<String> droppableSpells) {
        this.droppableSpells = droppableSpells;
    }

    public abstract void update(float time);

    public abstract void render(Graphics g);

}
