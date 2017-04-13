package ca.qc.bdeb.info204.spellington.spell;

import ca.qc.bdeb.info204.spellington.calculations.GameAnimation;
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
    protected String incantation;
    protected int uses;
    protected Animation animation;
    protected int height;
    protected int width;
    protected Image icon;

    public Spell(int id, ElementalType element, String name, int uses, Animation animation, int width, int height) {
        this.id = id;
        this.element = element;
        this.name = name;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

}
