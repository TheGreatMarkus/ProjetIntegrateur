package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity.ElementalType;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

/**
 * A class that corresponds to a spell that will be used by Spellington.
 *
 * @author Celtis
 */
public abstract class Spell {

    protected int id;
    protected ElementalType element;
    protected String name;
    protected String desc;
    protected String incantation;
    protected int uses;
    protected Animation animation;
    protected float height;
    protected float width;

    public Spell(int id, ElementalType element, String name, String desc, int uses, Animation animation, float width, float height) {
        this.id = id;
        this.element = element;
        this.name = name;
        this.desc = desc;
        this.uses = uses;
        this.animation = animation;
        this.height = height;
        this.width = width;
    }

    /**
     * Activates the effect of the current spell.
     *
     * @param spellington The playable protagonist.
     * @param input The Slick class where input is handled.
     * @param activeAnimations The list of active animations in the game.
     * @param activeProjectiles The list of active projectile in the game.
     * @param activeEnemy The list of active enemies in the game.
     */
    public abstract void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy);

    /**
     * End the effect of the current spell.
     *
     * @param spellington The playable protagonist.
     * @param activeAnimations The list of active animations in the game.
     */
    public abstract void endOfActivation(Spellington spellington, ArrayList<GameAnimation> activeAnimations);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ElementalType getElement() {
        return element;
    }

    public void setElement(ElementalType element) {
        this.element = element;
    }


    public String getIncantation() {
        return incantation;
    }

    public void setIncantation(String incantation) {
        this.incantation = incantation;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
    
    

}
