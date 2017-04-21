package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import static ca.qc.bdeb.info204.spellington.GameCore.fontPaladin;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.IMG_MENU_CURSOR;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import ca.qc.bdeb.info204.spellington.textEntities.InfoItem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.fontMenu;
import ca.qc.bdeb.info204.spellington.spell.BreathSpell;
import ca.qc.bdeb.info204.spellington.spell.ExplosionSpell;
import ca.qc.bdeb.info204.spellington.spell.HealingSpell;
import ca.qc.bdeb.info204.spellington.spell.Potions;
import ca.qc.bdeb.info204.spellington.spell.ProjectileSpell;
import java.awt.Font;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


/**
 *
 * @author Tarik
 */
public class SpellBookState extends BasicGameState {

    private UnicodeFont fontSpellChant;

    private Image background, icon, iconImage;

    private static final String SB_MAINTITLE = "Grimoire";
    private static final String SB_SPELLS = "Sorts";
    private static final String SB_POTIONS = "Potions";
    private static final String SB_ENNEMIES = "Ennemis";
    private static final String SB_RETOUR = "Retour";
    private static final String SB_SPELLTITLE = "Sorts";
    private static final String SB_POTIONSTITLE = "Potions";
    private static final String SB_ENNEMIESTITLE = "Ennemis";
    private static final String SB_BACK = "<-";
    private static final String SB_NEXT = "->";

    private MenuItem mnuItemMainTitle;
    private MenuItem mnuItemSpells;
    private MenuItem mnuItemPotions;
    private MenuItem mnuItemEnnemies;
    private MenuItem mnuItemRetour;
    private MenuItem mnuItemSpellTitle;
    private MenuItem mnuItemPotionsTitle;
    private MenuItem mnuItemEnnemiesTitle;
    private MenuItem mnuItemBack;
    private MenuItem mnuItemNext;
    private MenuItem mnuItemChangeType;

    private InfoItem infItemBar;
    private InfoItem infItemBar2;
    private InfoItem infItemBar3;
    private InfoItem infItemBar4;
    private InfoItem infItemBar5;
    private InfoItem infItemBar6;
    private InfoItem infItemBar7;
    private InfoItem infItemBar8;

    private float infoBarWidth = 550 * GameCore.SCALE;
    private float infoBarHeight = 60 * GameCore.SCALE;
    private float centerXInfoBar = (GameCore.SCREEN_SIZE.width / 4) - (infoBarWidth / 2) + 40 * GameCore.SCALE;
    private float topInfoBarY = (GameCore.SCREEN_SIZE.height / 4);
    private float textGap;
    private float topTitleY;
    private float bottomButtonY;

    private int pageState = 0; //All of the different pages according to what they show... 0 = main menu ; 1 = spells ; 2 = potions; 3 = ennemies
    private int spellsSubPageState = 0;
    private int potionsSubPageState = 0;
    private int ennemiesSubPageState = 0;
    private int infoBarFill;

    private ArrayList<String> infoList = new ArrayList<>();

    private String name;
    private String type;
    private String damage;
    private String element;
    private String incantationText;
    private String shortDescription;
    private String effect;
    private String page;
    
    private int pageNumber = 1;
    private int spellKind, potionKind;
    private boolean showRightPage = false;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        background = new Image("src/res/map/spellbook/Grimoire.png");

        fontPaladin = fontPaladin.deriveFont(Font.PLAIN, 30.0f * GameCore.SCALE);
        fontSpellChant = new UnicodeFont(fontPaladin);
        fontSpellChant.addAsciiGlyphs();
        fontSpellChant.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontSpellChant.loadGlyphs();

        textGap = 10.0f * GameCore.SCALE;

        mnuItemMainTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_MAINTITLE, false, false, 0, textGap + topTitleY, fontMenu.getWidth(SB_MAINTITLE), fontMenu.getHeight(SB_MAINTITLE));
        mnuItemMainTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_MAINTITLE, false, false, 0, textGap + topTitleY, fontMenu.getWidth(SB_MAINTITLE), fontMenu.getHeight(SB_MAINTITLE));
        mnuItemSpells = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_SPELLS, false, false, 0, mnuItemMainTitle.getY() + (mnuItemMainTitle.getHeight() * 2), fontMenu.getWidth(SB_SPELLS), fontMenu.getHeight(SB_SPELLS));
        mnuItemPotions = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_POTIONS, false, false, 0, mnuItemSpells.getY() + mnuItemSpells.getHeight() + textGap, fontMenu.getWidth(SB_POTIONS), fontMenu.getHeight(SB_POTIONS));
        mnuItemEnnemies = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_ENNEMIES, false, false, 0, mnuItemPotions.getY() + mnuItemPotions.getHeight() + textGap, fontMenu.getWidth(SB_ENNEMIES), fontMenu.getHeight(SB_ENNEMIES));
        mnuItemRetour = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_RETOUR, false, false, 0, mnuItemEnnemies.getY() + mnuItemEnnemies.getHeight() + textGap, fontMenu.getWidth(SB_RETOUR), fontMenu.getHeight(SB_RETOUR));
        mnuItemSpellTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_SPELLTITLE, false, false, 0, textGap + topTitleY, fontMenu.getWidth(SB_SPELLTITLE), fontMenu.getHeight(SB_SPELLTITLE));
        mnuItemPotionsTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_POTIONSTITLE, false, false, 0, textGap + topTitleY, fontMenu.getWidth(SB_POTIONSTITLE), fontMenu.getHeight(SB_POTIONSTITLE));
        mnuItemEnnemiesTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_ENNEMIESTITLE, false, false, 0, textGap + topTitleY, fontMenu.getWidth(SB_ENNEMIESTITLE), fontMenu.getHeight(SB_ENNEMIESTITLE));
        mnuItemSpellTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_SPELLTITLE, false, false, 0, textGap + topTitleY, fontMenu.getWidth(SB_SPELLTITLE), fontMenu.getHeight(SB_SPELLTITLE));
        mnuItemPotionsTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_POTIONSTITLE, false, false, 0, textGap + topTitleY, fontMenu.getWidth(SB_POTIONSTITLE), fontMenu.getHeight(SB_POTIONSTITLE));
        mnuItemEnnemiesTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_ENNEMIESTITLE, false, false, 0, textGap + topTitleY, fontMenu.getWidth(SB_ENNEMIESTITLE), fontMenu.getHeight(SB_ENNEMIESTITLE));
        mnuItemBack = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_BACK, false, false, 0, bottomButtonY, fontMenu.getWidth(SB_BACK), fontMenu.getHeight(SB_BACK));
        mnuItemNext = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_NEXT, false, false, 0, bottomButtonY, fontMenu.getWidth(SB_NEXT), fontMenu.getHeight(SB_NEXT));
        mnuItemChangeType = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_BACK, false, false, 0, textGap, fontMenu.getWidth(SB_BACK), fontMenu.getHeight(SB_BACK));
        infItemBar = new InfoItem(gc, InfoItem.InfoItemType.TRANSPARENT, centerXInfoBar, topInfoBarY + textGap, infoBarWidth, infoBarHeight);
        infItemBar2 = new InfoItem(gc, InfoItem.InfoItemType.TRANSPARENT, centerXInfoBar, topInfoBarY + infoBarHeight + textGap, infoBarWidth, infoBarHeight);
        infItemBar3 = new InfoItem(gc, InfoItem.InfoItemType.TRANSPARENT, centerXInfoBar, topInfoBarY + (infoBarHeight * 2) + textGap, infoBarWidth, infoBarHeight);
        infItemBar4 = new InfoItem(gc, InfoItem.InfoItemType.TRANSPARENT, centerXInfoBar, topInfoBarY + (infoBarHeight * 3) + textGap, infoBarWidth, infoBarHeight);
        infItemBar5 = new InfoItem(gc, InfoItem.InfoItemType.TRANSPARENT, centerXInfoBar, topInfoBarY + (infoBarHeight * 4) + textGap, infoBarWidth, infoBarHeight);
        infItemBar6 = new InfoItem(gc, InfoItem.InfoItemType.TRANSPARENT, centerXInfoBar, topInfoBarY + (infoBarHeight * 5) + textGap, infoBarWidth, infoBarHeight);
        infItemBar7 = new InfoItem(gc, InfoItem.InfoItemType.TRANSPARENT, centerXInfoBar, topInfoBarY + (infoBarHeight * 6) + textGap, infoBarWidth, infoBarHeight);
        infItemBar8 = new InfoItem(gc, InfoItem.InfoItemType.TRANSPARENT, centerXInfoBar, topInfoBarY + (infoBarHeight * 7) + textGap, infoBarWidth, infoBarHeight);

        float mnuItemMainTitleX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemMainTitle.getWidth() / 2) + 35 * GameCore.SCALE;
        float mnuItemSpellsX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemSpells.getWidth() / 2) + +35 * GameCore.SCALE;
        float mnuItemPotionsX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemPotions.getWidth() / 2) + 35 * GameCore.SCALE;
        float mnuItemEnnemiesX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemEnnemies.getWidth() / 2) + 35 * GameCore.SCALE;
        float mnuItemMenuX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemRetour.getWidth() / 2) + 35 * GameCore.SCALE;
        float mnuItemSpellsTitleX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemSpellTitle.getWidth() / 2) + 35 * GameCore.SCALE;
        float mnuItemPotionsTitleX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemPotionsTitle.getWidth() / 2) + 35 * GameCore.SCALE;
        float mnuItemEnnemiesTitleX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemEnnemiesTitle.getWidth() / 2) + 35 * GameCore.SCALE;
        float mnuItemBackX = mnuItemBack.getWidth() - 20 * GameCore.SCALE;
        float mnuItemNextX = GameCore.SCREEN_SIZE.width - mnuItemNext.getWidth() - textGap - 85 * GameCore.SCALE;
        mnuItemMainTitle.setX(mnuItemMainTitleX);
        mnuItemSpells.setX(mnuItemSpellsX);
        mnuItemPotions.setX(mnuItemPotionsX);
        mnuItemEnnemies.setX(mnuItemEnnemiesX);
        mnuItemRetour.setX(mnuItemMenuX);
        mnuItemSpellTitle.setX(mnuItemSpellsTitleX);
        mnuItemPotionsTitle.setX(mnuItemPotionsTitleX);
        mnuItemEnnemiesTitle.setX(mnuItemEnnemiesTitleX);
        mnuItemBack.setX(mnuItemBackX);
        mnuItemNext.setX(mnuItemNextX);
        mnuItemChangeType.setX(mnuItemBackX);
        topTitleY = GameCore.SCREEN_SIZE.height / 36;
        bottomButtonY = GameCore.SCREEN_SIZE.height - mnuItemBack.getHeight() - textGap;

        infoList.add("1");
        infoList.add("2");
        infoList.add("3");
        infoList.add("4");
        infoList.add("5");
        infoList.add("6");
        infoList.add("7");
        infoList.add("8");

        this.icon = new Image("src/res/map/spellbook/icon.png");
        this.iconImage = iconImage;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, GameCore.SCREEN_SIZE.width, GameCore.SCREEN_SIZE.height);
        g.setFont(fontMenu);

        switch (pageState) {
            case 0:
                displayMenu(g, gc);
                break;

            case 1:
                displaySpellsLeftPage(g, gc);
                break;

            case 2:
                displayPotionsLeftPage(g, gc);
                break;

            case 3:
                displayEnnemiesLeftPage(g, gc);
                break;
        }

        float renderMouseX = gc.getInput().getMouseX();
        float renderMouseY = gc.getInput().getMouseY();
        IMG_MENU_CURSOR.draw(renderMouseX, renderMouseY, 25f * GameCore.SCALE, 25f * GameCore.SCALE);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        switch (pageState) {
            case 0:
                mnuItemSpells.detHoveredOver(mouseX, mouseY);
                mnuItemPotions.detHoveredOver(mouseX, mouseY);
                mnuItemEnnemies.detHoveredOver(mouseX, mouseY);
                mnuItemRetour.detHoveredOver(mouseX, mouseY);

                if (mnuItemSpells.getHoveredOver() && triedToClick) {
                    pageState = 1;
                    spellsSubPageState = 0;
                }

                if (mnuItemPotions.getHoveredOver() && triedToClick) {
                    pageState = 2;
                    potionsSubPageState = 0;
                }

                if (mnuItemEnnemies.getHoveredOver() && triedToClick) {
                    pageState = 3;
                    ennemiesSubPageState = 0;
                }

                if (mnuItemRetour.getHoveredOver() && triedToClick) {
                    game.enterState(GameCore.PAUSE_MENU_STATE_ID);
                    pageState = 0;
                }
                break;

            case 1:
                mnuItemBack.detHoveredOver(mouseX, mouseY);
                mnuItemNext.detHoveredOver(mouseX, mouseY);
                mnuItemChangeType.detHoveredOver(mouseX, mouseY);
                infItemBar.detHoveredOver(mouseX, mouseY);
                infItemBar2.detHoveredOver(mouseX, mouseY);
                infItemBar3.detHoveredOver(mouseX, mouseY);
                infItemBar4.detHoveredOver(mouseX, mouseY);
                infItemBar5.detHoveredOver(mouseX, mouseY);
                infItemBar6.detHoveredOver(mouseX, mouseY);
                infItemBar7.detHoveredOver(mouseX, mouseY);
                infItemBar8.detHoveredOver(mouseX, mouseY);

                if (mnuItemBack.getHoveredOver() && triedToClick && spellsSubPageState == 0) {
                    spellsSubPageState = 0;
                    pageNumber = 1;
                } else if (mnuItemBack.getHoveredOver() && triedToClick) {
                    spellsSubPageState--;
                    pageNumber--;
                    showRightPage = false;
                }

                if (mnuItemNext.getHoveredOver() && triedToClick && spellsSubPageState == 2) {
                    spellsSubPageState = 2;
                    pageNumber = 3;
                } else if (mnuItemNext.getHoveredOver() && triedToClick) {
                    spellsSubPageState++;
                    pageNumber++;
                    showRightPage = false;
                }

                if (mnuItemChangeType.getHoveredOver() && triedToClick) {
                    pageState = 0;
                    pageNumber = 1;
                    showRightPage = false;
                }

                if (infItemBar.getHoveredOver() && triedToClick) {
                    switch(spellsSubPageState){
                        case 0:
                            spellKind = 0;
                            showRightPage = true;
                            break;
                        
                        case 1:
                            if(SpellingSystem.knownSpell.size()>=9){
                                spellKind = 8;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;
                            
                        case 2:
                            if(SpellingSystem.knownSpell.size()>=17){
                                spellKind = 16;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;      
                    }
                }

                if (infItemBar2.getHoveredOver() && triedToClick) {
                    switch(spellsSubPageState){
                        case 0:
                            spellKind = 1;
                            showRightPage = true;
                            break;
                        
                        case 1:
                            if(SpellingSystem.knownSpell.size()>=10){
                                spellKind = 9;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;
                            
                        case 2:
                            if(SpellingSystem.knownSpell.size()>=18){
                                spellKind = 17;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;      
                    }
                }

                if (infItemBar3.getHoveredOver() && triedToClick) {
                    switch(spellsSubPageState){
                        case 0:
                            spellKind = 2;
                            showRightPage = true;
                            break;               
                        case 1:
                            if(SpellingSystem.knownSpell.size()>=11){
                                spellKind = 10;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;
                            
                        case 2:
                            if(SpellingSystem.knownSpell.size()>=19){
                                spellKind = 18;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;      
                    }
                }

                if (infItemBar4.getHoveredOver() && triedToClick) {
                    switch(spellsSubPageState){
                        case 0:
                            spellKind = 3;
                            showRightPage = true;
                            break;
                        
                        case 1:
                            if(SpellingSystem.knownSpell.size()>=12){
                                spellKind = 11;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;
                            
                        case 2:
                            if(SpellingSystem.knownSpell.size()>=20){
                                spellKind = 19;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;      
                    }
                }

                if (infItemBar5.getHoveredOver() && triedToClick) {
                    switch(spellsSubPageState){
                        case 0:
                            spellKind = 4;
                            showRightPage = true;
                            break;
                            
                        case 1:
                            if(SpellingSystem.knownSpell.size()>=13){
                                spellKind = 12;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;
                            
                        case 2:
                            if(SpellingSystem.knownSpell.size()>=21){
                                spellKind = 20;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;      
                    }
                }

                if (infItemBar6.getHoveredOver() && triedToClick) {
                    switch(spellsSubPageState){
                        case 0:
                            spellKind = 5;
                            showRightPage = true;
                            break;
                        
                        case 1:
                            if(SpellingSystem.knownSpell.size()>=14){
                                spellKind = 13;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;
                            
                        case 2:
                            if(SpellingSystem.knownSpell.size()>=22){
                                spellKind = 21;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;      
                    }
                }

                if (infItemBar7.getHoveredOver() && triedToClick) {
                    switch(spellsSubPageState){
                        case 0:
                            spellKind = 6;
                            showRightPage = true;
                            break;
                        
                        case 1:
                            if(SpellingSystem.knownSpell.size()>=15){
                                spellKind = 14;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;
                            
                        case 2:
                            if(SpellingSystem.knownSpell.size()>=23){
                                spellKind = 22;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;      
                    }
                }

                if (infItemBar8.getHoveredOver() && triedToClick) {
                    switch(spellsSubPageState){
                        case 0:
                            spellKind = 7;
                            showRightPage = true;
                            break;
                        
                        case 1:
                            if(SpellingSystem.knownSpell.size()>=16){
                                spellKind = 15;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;
                            
                        case 2:
                            if(SpellingSystem.knownSpell.size()>=24){
                                spellKind = 23;
                                showRightPage = true;
                            }else{
                                showRightPage = false;
                            }
                            break;      
                    }
                }
                break;

            case 2:
                mnuItemBack.detHoveredOver(mouseX, mouseY);
                mnuItemNext.detHoveredOver(mouseX, mouseY);
                mnuItemChangeType.detHoveredOver(mouseX, mouseY);
                infItemBar.detHoveredOver(mouseX, mouseY);
                infItemBar2.detHoveredOver(mouseX, mouseY);
                infItemBar3.detHoveredOver(mouseX, mouseY);
                infItemBar4.detHoveredOver(mouseX, mouseY);

                if (mnuItemBack.getHoveredOver() && triedToClick && spellsSubPageState == 0) {
                    potionsSubPageState = 0;
                } else if (mnuItemBack.getHoveredOver() && triedToClick) {
                    potionsSubPageState--;
                    showRightPage = false;
                }

                if (mnuItemNext.getHoveredOver() && triedToClick && spellsSubPageState == 2) {
                    potionsSubPageState = 2;
                } else if (mnuItemNext.getHoveredOver() && triedToClick) {
                    potionsSubPageState++;
                    showRightPage = false;
                }

                if (mnuItemChangeType.getHoveredOver() && triedToClick) {
                    pageState = 0;
                    showRightPage = false;
                }

//                if (infItemBar.getHoveredOver() && triedToClick) {
//                    potionKind = 0;
//                    showRightPage = true;
//                }
//
//                if (infItemBar2.getHoveredOver() && triedToClick) {
//                    if (SpellingSystem.knownPotion.size() >= 2) {
//                        potionKind = 1;
//                        showRightPage = true;
//                    } else {
//                        showRightPage = false;
//                    }
//                }
//
//                if (infItemBar3.getHoveredOver() && triedToClick) {
//                    if (SpellingSystem.knownPotion.size() >= 3) {
//                        potionKind = 2;
//                        showRightPage = true;
//                    } else {
//                        showRightPage = false;
//                    }   
//                }
//
//                if (infItemBar4.getHoveredOver() && triedToClick) {
//                    if (SpellingSystem.knownPotion.size() >= 4) {
//                        potionKind = 3;
//                        showRightPage = true;
//                    } else {
//                        showRightPage = false;
//                    }
//                }
//                break;

            case 3:
                mnuItemBack.detHoveredOver(mouseX, mouseY);
                mnuItemNext.detHoveredOver(mouseX, mouseY);
                mnuItemChangeType.detHoveredOver(mouseX, mouseY);
                infItemBar.detHoveredOver(mouseX, mouseY);
                infItemBar2.detHoveredOver(mouseX, mouseY);
                infItemBar3.detHoveredOver(mouseX, mouseY);
                infItemBar4.detHoveredOver(mouseX, mouseY);
                infItemBar5.detHoveredOver(mouseX, mouseY);
                infItemBar6.detHoveredOver(mouseX, mouseY);
                infItemBar7.detHoveredOver(mouseX, mouseY);
                infItemBar8.detHoveredOver(mouseX, mouseY);

                if (mnuItemBack.getHoveredOver() && triedToClick && spellsSubPageState == 0) {
                    ennemiesSubPageState = 0;
                } else if (mnuItemBack.getHoveredOver() && triedToClick) {
                    ennemiesSubPageState--;
                    showRightPage = false;
                }

                if (mnuItemNext.getHoveredOver() && triedToClick && spellsSubPageState == 2) {
                    ennemiesSubPageState = 2;
                } else if (mnuItemNext.getHoveredOver() && triedToClick) {
                    ennemiesSubPageState++;
                    showRightPage = false;
                }

                if (mnuItemChangeType.getHoveredOver() && triedToClick) {
                    pageState = 0;
                    showRightPage = false;
                }

                if (infItemBar.getHoveredOver() && triedToClick) {

                }

                if (infItemBar2.getHoveredOver() && triedToClick) {

                }

                if (infItemBar3.getHoveredOver() && triedToClick) {

                }

                if (infItemBar4.getHoveredOver() && triedToClick) {

                }

                if (infItemBar5.getHoveredOver() && triedToClick) {

                }

                if (infItemBar6.getHoveredOver() && triedToClick) {

                }

                if (infItemBar7.getHoveredOver() && triedToClick) {

                }

                if (infItemBar8.getHoveredOver() && triedToClick) {

                }
                break;
        }

        GameCore.clearInputRecord(gc);
    }

    @Override
    public int getID() {
        return GameCore.SPELLBOOK_STATE_ID;
    }

    private void displayMenu(Graphics g, GameContainer gc) throws SlickException {
        mnuItemMainTitle.render(g, gc);
        mnuItemSpells.render(g, gc);
        mnuItemPotions.render(g, gc);
        mnuItemEnnemies.render(g, gc);
        mnuItemRetour.render(g, gc);
    }

    private void displaySpellsLeftPage(Graphics g, GameContainer gc) throws SlickException {
        mnuItemSpellTitle.render(g, gc);
        mnuItemBack.render(g, gc);
        mnuItemNext.render(g, gc);
        mnuItemChangeType.render(g, gc);
        infItemBar.renderTransparent(g, gc);
        infItemBar2.renderTransparent(g, gc);
        infItemBar3.renderTransparent(g, gc);
        infItemBar4.renderTransparent(g, gc);
        infItemBar5.renderTransparent(g, gc);
        infItemBar6.renderTransparent(g, gc);
        infItemBar7.renderTransparent(g, gc);
        infItemBar8.renderTransparent(g, gc);

        g.setFont(fontSpellChant);

        float iconX = (GameCore.SCREEN_SIZE.width / 2) + (icon.getWidth() / 2) - 20 * GameCore.SCALE;
        float iconY = ((GameCore.SCREEN_SIZE.height / 18) * 3) - 30 * GameCore.SCALE;
        float iconSize = 200 * GameCore.SCALE;
        float gap = 30 * GameCore.SCALE;
        int i;

        switch (spellsSubPageState) { //Spell name title attributed in the information rectangles according to the page where the player stands
            case 0:
                for (infoBarFill = 0, i = 0; i < 8; infoBarFill++, i++) {
                    if (infoBarFill < SpellingSystem.knownSpell.size()) {
                        infoList.set(i, SpellingSystem.knownSpell.get(i).getName());
                    } else {
                        infoList.set(i, " ");
                    }
                }
                break;

            case 1:
                for (infoBarFill = 8, i = 0; i < 8; infoBarFill++, i++) {
                    if (infoBarFill < SpellingSystem.knownSpell.size()) {
                        infoList.set(i, SpellingSystem.knownSpell.get(infoBarFill).getName());
                    } else {
                        infoList.set(i, " ");
                    }
                }
                break;

            case 2:
                for (infoBarFill = 16, i = 0; i < 8; infoBarFill++, i++) {
                    if (infoBarFill < SpellingSystem.knownSpell.size()) {
                        infoList.set(i, SpellingSystem.knownSpell.get(infoBarFill).getName());
                    } else {
                        infoList.set(i, " ");
                    }
                }
                break;
        }

        g.setColor(new Color(1, 1, 1, 1f));
        g.drawString(infoList.get(0), infItemBar.getX() + (gap / 3), infItemBar.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(1), infItemBar.getX() + (gap / 3), infItemBar2.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(2), infItemBar.getX() + (gap / 3), infItemBar3.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(3), infItemBar.getX() + (gap / 3), infItemBar4.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(4), infItemBar.getX() + (gap / 3), infItemBar5.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(5), infItemBar.getX() + (gap / 3), infItemBar6.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(6), infItemBar.getX() + (gap / 3), infItemBar7.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(7), infItemBar.getX() + (gap / 3), infItemBar8.getY() + (infoBarHeight / 12) + textGap);

        displaySpellsRightPage(g, iconX, iconY, iconSize, gap);
    }

    private void displayPotionsLeftPage(Graphics g, GameContainer gc) throws SlickException {
        mnuItemPotionsTitle.render(g, gc);
        mnuItemBack.render(g, gc);
        mnuItemChangeType.render(g, gc);
        infItemBar.renderTransparent(g, gc);
        infItemBar2.renderTransparent(g, gc);
        infItemBar3.renderTransparent(g, gc);
        infItemBar4.renderTransparent(g, gc);

        g.setFont(fontSpellChant);

        float iconX = (GameCore.SCREEN_SIZE.width / 2) + (icon.getWidth() / 2) - 20 * GameCore.SCALE;
        float iconY = ((GameCore.SCREEN_SIZE.height / 18) * 3) - 30 * GameCore.SCALE;
        float iconSize = 200 * GameCore.SCALE;
        float gap = 30 * GameCore.SCALE;
        int i;
        
//        for (infoBarFill = 0, i = 0; i < 4; infoBarFill++, i++) {
//            if (infoBarFill < SpellingSystem.knownPotion.size()) {
//                infoList.set(i, SpellingSystem.knownPotion.get(i).getName());
//            } else {
//                infoList.set(i, " ");
//            }
//        }

        g.setColor(new Color(1, 1, 1, 1f));
        g.drawString(infoList.get(0), infItemBar.getX() + (gap / 3), infItemBar.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(1), infItemBar.getX() + (gap / 3), infItemBar2.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(2), infItemBar.getX() + (gap / 3), infItemBar3.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(3), infItemBar.getX() + (gap / 3), infItemBar4.getY() + (infoBarHeight / 12) + textGap);

        displayPotionsRightPage(g, iconX, iconY, iconSize, gap);
    }

    private void displayEnnemiesLeftPage(Graphics g, GameContainer gc) throws SlickException {
        mnuItemEnnemiesTitle.render(g, gc);
        mnuItemBack.render(g, gc);
        mnuItemNext.render(g, gc);
        mnuItemChangeType.render(g, gc);
        infItemBar.renderTransparent(g, gc);
        infItemBar2.renderTransparent(g, gc);
        infItemBar3.renderTransparent(g, gc);
        infItemBar4.renderTransparent(g, gc);
        infItemBar5.renderTransparent(g, gc);
        infItemBar6.renderTransparent(g, gc);
        infItemBar7.renderTransparent(g, gc);
        infItemBar8.renderTransparent(g, gc);

        g.setFont(fontSpellChant);

        float iconX = (GameCore.SCREEN_SIZE.width / 2) + (icon.getWidth() / 2) - 20 * GameCore.SCALE;
        float iconY = ((GameCore.SCREEN_SIZE.height / 18) * 3) - 30 * GameCore.SCALE;
        float iconSize = 200 * GameCore.SCALE;
        float gap = 30 * GameCore.SCALE;
        int i;

        switch (ennemiesSubPageState) { //Spell name title attributed in the information rectangles according to the page where the player stands
            case 0:
                for (infoBarFill = 0, i = 0; i < 8; infoBarFill++, i++) {
                    if (infoBarFill < SpellingSystem.knownSpell.size()) {
                        infoList.set(i, SpellingSystem.knownSpell.get(i).getName());
                    } else {
                        infoList.set(i, " ");
                    }
                }
                break;
        }

        g.setColor(new Color(1, 1, 1, 1f));
        g.drawString(infoList.get(0), infItemBar.getX() + (gap / 3), infItemBar.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(1), infItemBar.getX() + (gap / 3), infItemBar2.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(2), infItemBar.getX() + (gap / 3), infItemBar3.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(3), infItemBar.getX() + (gap / 3), infItemBar4.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(4), infItemBar.getX() + (gap / 3), infItemBar5.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(5), infItemBar.getX() + (gap / 3), infItemBar6.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(6), infItemBar.getX() + (gap / 3), infItemBar7.getY() + (infoBarHeight / 12) + textGap);
        g.drawString(infoList.get(7), infItemBar.getX() + (gap / 3), infItemBar8.getY() + (infoBarHeight / 12) + textGap);

        displayEnnemiesRightPage(g, iconX, iconY, iconSize, gap);
    }

    private void displaySpellsRightPage(Graphics g, float iconX, float iconY, float iconSize, float gap) {
        page = Integer.toString(pageNumber);
        g.drawString(page, ((GameCore.SCREEN_SIZE.width * 3)/4) - 20*GameCore.SCALE, GameCore.SCREEN_SIZE.height - gap*2);
        
        if (showRightPage) {
            icon.draw(iconX, iconY, iconSize, iconSize);
            
            name = "Nom: " + SpellingSystem.knownSpell.get(spellKind).getName();
            type = "Type: " + SpellingSystem.knownSpell.get(spellKind).getType();
            incantationText = "Mot déclencheur: " + SpellingSystem.knownSpell.get(spellKind).getIncantation();
            shortDescription = "Petite description: " + SpellingSystem.knownSpell.get(spellKind).getShortDescription();
            iconImage = SpellingSystem.knownSpell.get(spellKind).getIcon();

            switch (SpellingSystem.knownSpell.get(spellKind).getType()) {//damage and effects relative to the spell's type
                case "Sort de souffle":
                    String damagePoints = Integer.toString(((BreathSpell) SpellingSystem.knownSpell.get(spellKind)).getDamage());
                    damage = "Dégâts: " + damagePoints;
                    g.drawString(damage, iconX + iconSize + gap, iconY + 4 * gap);
                    break;
                    
                case "Explosion":
                    damagePoints = Integer.toString(((ExplosionSpell) SpellingSystem.knownSpell.get(spellKind)).getDamage());
                    damage = "Dégâts: " + damagePoints;
                    g.drawString(damage, iconX + iconSize + gap, iconY + 4 * gap);
                    break;
                    
                case "Projectile":
                    damagePoints = Integer.toString(((ProjectileSpell) SpellingSystem.knownSpell.get(spellKind)).getDamage());
                    damage = "Dégâts: " + damagePoints;
                    g.drawString(damage, iconX + iconSize + gap, iconY + 4 * gap);
                    break;
                    
                case "Guérison":
                    String healingPoints = Integer.toString(((HealingSpell) SpellingSystem.knownSpell.get(spellKind)).getHealing());
                    effect = "Effet: " + healingPoints + " points de santé";
                    g.drawString(effect, iconX + iconSize + gap, iconY + 4 * gap);
                    break;
                
                case "Passif":
                    
                    switch(SpellingSystem.knownSpell.get(spellKind).getName()){
                        case "Courant ascendant":
                            effect = "Effet: Saut additionnel";
                            break;
                        case "Résistance feu":
                            effect = "Effet: 10 points de résistance";
                            break;
                            
                        case "Résistance glace":
                            effect = "Effet: 10 points de résistance";
                            break;     
                            
                        case "Résistance electrique":
                            effect = "Effet: 10 points de résistance";
                            break;
                            
                        case "Immunite feu":
                            effect = "Effet: 999 points de résistance";
                            break;
                        
                        case "Immunite électrique":
                            effect = "Effet: 999 points de résistance";
                            break;
                            
                        case "Immunite glace":
                            effect = "Effet: 999 points de résistance";
                            break;
                    }
                    
                    g.drawString(effect, iconX + iconSize + gap, iconY + 4 * gap);
                    break;   
            }
            
            switch(SpellingSystem.knownSpell.get(spellKind).getElement()){
                case FIRE:
                    element = "Élément: Feu";
                    break;
                    
                case ICE:
                    element = "Élément: Glace";
                    break;
                    
                case LIGHTNING:
                    element = "Élément: Éclair";
                    break;
                    
                case NEUTRAL:
                    element = "Élément: Neutre";
                    break;
            }
            //iconImage.draw(iconX, iconY, iconSize - 10*GameCore.SCALE, iconSize - 10*GameCore.SCALE);
            
            g.drawString(name, iconX + iconSize + gap, iconY);
            g.drawString(type, iconX + iconSize + gap, iconY + 2 * gap);
            g.drawString(element, iconX + iconSize + gap, iconY + 6 * gap);
            g.drawString(incantationText, iconX, iconY + 8 * gap);
            g.drawString(shortDescription, iconX, iconY + 10 * gap);
        }
    }

    private void displayPotionsRightPage(Graphics g, float iconX, float iconY, float iconSize, float gap) {
        page = Integer.toString(pageNumber);
        g.drawString(page, ((GameCore.SCREEN_SIZE.width * 3)/4) - 20*GameCore.SCALE, GameCore.SCREEN_SIZE.height - gap*2);
        
        if (showRightPage) {
            icon.draw(iconX, iconY, iconSize, iconSize);
            
//            name = "Nom: " + SpellingSystem.knownPotion.get(potionKind).getName();
//            type = "Type: " + SpellingSystem.knownPotion.get(potionKind).getType();
//            element = "Élément: Neutre";
//            incantationText = "Mot déclencheur: " + SpellingSystem.knownPotion.get(potionKind).getIncantation();
//            shortDescription = "Petite description: " + SpellingSystem.knownPotion.get(potionKind).getShortDescription();
//            iconImage = SpellingSystem.knownPotion.get(potionKind).getIcon();
//
//            switch (SpellingSystem.knownPotion.get(potionKind).getName()) {
//                case "Soin instantané":
//                    String healingPoints = Integer.toString(((Potions) SpellingSystem.knownPotion.get(potionKind)).getHealing());
//                    effect = "Effet: " + healingPoints + " points de santé";
//                    g.drawString(effect, iconX + iconSize + gap, iconY + 4 * gap);
//                    break;
//                    
//                case "Acide":
//                    String damagePoints = Integer.toString(((Potions) SpellingSystem.knownPotion.get(potionKind)).getDamage());
//                    damage = "Dégâts: " + damagePoints;
//                    g.drawString(damage, iconX + iconSize + gap, iconY + 4 * gap);
//                    break;
//                    
//                case "Passé":
//                    effect = "Effet: Sort précédent";
//                    g.drawString(effect, iconX + iconSize + gap, iconY + 4 * gap);
//                    break;
//                    
//                case "Acier":
//                    effect = "Effet: Invicibilité temporaire";
//                    g.drawString(effect, iconX + iconSize + gap, iconY + 4 * gap);
//                    break;  
//            }

            //iconImage.draw(iconX, iconY, iconSize - 10*GameCore.SCALE, iconSize - 10*GameCore.SCALE);
            
            g.drawString(name, iconX + iconSize + gap, iconY);
            g.drawString(type, iconX + iconSize + gap, iconY + 2 * gap);
            g.drawString(element, iconX + iconSize + gap, iconY + 6 * gap);
            g.drawString(incantationText, iconX, iconY + 8 * gap);
            g.drawString(shortDescription, iconX, iconY + 10 * gap);
        }
    }
    
    private void displayEnnemiesRightPage(Graphics g, float iconX, float iconY, float iconSize, float gap) {

    }

}
