/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info204.spellington.gameentities;

import java.util.ArrayList;

/**
 *
 * @author Celtis
 */
public abstract class Enemy extends LivingEntity {

    protected ArrayList<String> ListSpellDropable = new ArrayList<>();

    public Enemy(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public ArrayList<String> getListSpellDropable() {
        return ListSpellDropable;
    }

    public void setListSpellDropable(ArrayList<String> ListSpellDropable) {
        this.ListSpellDropable = ListSpellDropable;
    }

}
