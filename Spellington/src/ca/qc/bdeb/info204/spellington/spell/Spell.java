package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.gameentities.GameAnimation;
import ca.qc.bdeb.info204.spellington.gameentities.GameEntity.ElementalType;
import ca.qc.bdeb.info204.spellington.gameentities.Projectile;
import ca.qc.bdeb.info204.spellington.gameentities.Spellington;
import ca.qc.bdeb.info204.spellington.gameentities.enemies.Enemy;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Celtis
 */
public abstract class Spell {

    protected int id;
    protected ElementalType element;
    protected String name;
    protected String shortDescription;
    protected String incantation;
    protected int uses;
    protected Animation animation;
    protected float height;
    protected float width;
    protected Image icon;

    public Spell(int id, ElementalType element, String name, String shortDescription /**/, int uses, Animation animation, float width, float height) {
        this.id = id;
        this.element = element;
        this.name = name;
        this.shortDescription = shortDescription;
        this.uses = uses;
        this.animation = animation;
        this.height = height;
        this.width = width;
    }

    public abstract void spellActivation(Spellington spellington, Input input, ArrayList<GameAnimation> activeAnimations, ArrayList<Projectile> activeProjectiles, ArrayList<Enemy> activeEnemy);

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

}
