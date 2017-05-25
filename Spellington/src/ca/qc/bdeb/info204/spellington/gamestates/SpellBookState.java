package ca.qc.bdeb.info204.spellington.gamestates;

import ca.qc.bdeb.info204.spellington.GameCore;
import ca.qc.bdeb.info204.spellington.calculations.SpellingSystem;
import ca.qc.bdeb.info204.spellington.textEntities.MenuItem;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import static ca.qc.bdeb.info204.spellington.gamestates.MainMenuState.fontMenu;
import ca.qc.bdeb.info204.spellington.spell.BurstSpell;
import ca.qc.bdeb.info204.spellington.spell.ExplosionSpell;
import ca.qc.bdeb.info204.spellington.spell.HealingSpell;
import ca.qc.bdeb.info204.spellington.spell.PassiveSpell;
import ca.qc.bdeb.info204.spellington.spell.ProjectileSpell;
import java.awt.Font;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * A BasicGameState that corresponds to the Spellbook of the game, where
 * information on spells and enemies are listed.
 *
 * @author Tarik
 */
public class SpellBookState extends BasicGameState {

    private UnicodeFont fontSpellChant, fontDetails;

    private Image background, icon, iconImage;
    private Animation iconAnimation;

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

    private MenuItem mnuItemBar;
    private MenuItem mnuItemBar2;
    private MenuItem mnuItemBar3;
    private MenuItem mnuItemBar4;
    private MenuItem mnuItemBar5;
    private MenuItem mnuItemBar6;
    private MenuItem mnuItemBar7;
    private MenuItem mnuItemBar8;

    private float infoBarWidth = 540 * GameCore.SCALE;
    private float infoBarHeight = 60 * GameCore.SCALE;
    private float centerXInfoBar = (GameCore.SCREEN_SIZE.width / 4) - (infoBarWidth / 2);
    private float topInfoBarY = (GameCore.SCREEN_SIZE.height / 4);
    private float textGap;
    private float topTitleY;
    private float bottomButtonY;

    private int pageState = 0; 
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
    private String activationButton;
    private String shortDescription;
    private String effect;
    private String page;

    private int pageNumber = 1;
    private int spellKind, potionKind;
    private boolean showRightPage = false;

    /**
     * Initialises the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @throws SlickException General Slick exception
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        background = new Image("res/image/spellbook/Grimoire.jpg");

        textGap = 10.0f * GameCore.SCALE;
        
        fontSpellChant = new UnicodeFont(GameCore.getFontMeath(Font.PLAIN, 50.0f * GameCore.SCALE));
        fontSpellChant.addAsciiGlyphs();
        fontSpellChant.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        fontSpellChant.loadGlyphs();
        
        fontDetails = new UnicodeFont(GameCore.getFontMeath(Font.PLAIN, 30.0f * GameCore.SCALE));
        fontDetails.addAsciiGlyphs();
        fontDetails.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        fontDetails.loadGlyphs();

        mnuItemMainTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_MAINTITLE, false, false, 0, (2*textGap) + topTitleY, fontMenu.getWidth(SB_MAINTITLE), fontMenu.getHeight(SB_MAINTITLE));
        mnuItemSpells = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_SPELLS, false, false, 0, mnuItemMainTitle.getY() + (mnuItemMainTitle.getHeight() * 2) + (8*textGap), fontMenu.getWidth(SB_SPELLS), fontMenu.getHeight(SB_SPELLS));
        mnuItemPotions = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_POTIONS, false, false, 0, mnuItemSpells.getY() + mnuItemSpells.getHeight() + textGap, fontMenu.getWidth(SB_POTIONS), fontMenu.getHeight(SB_POTIONS));
        mnuItemEnnemies = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_ENNEMIES, false, false, 0, mnuItemPotions.getY() + mnuItemPotions.getHeight() + textGap, fontMenu.getWidth(SB_ENNEMIES), fontMenu.getHeight(SB_ENNEMIES));
        mnuItemRetour = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_RETOUR, false, false, 0, mnuItemEnnemies.getY() + mnuItemEnnemies.getHeight() + (8*textGap), fontMenu.getWidth(SB_RETOUR), fontMenu.getHeight(SB_RETOUR));
        mnuItemSpellTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_SPELLTITLE, false, false, 0, textGap + topTitleY, fontSpellChant.getWidth(SB_SPELLTITLE), fontSpellChant.getHeight(SB_SPELLTITLE));
        mnuItemPotionsTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_POTIONSTITLE, false, false, 0, textGap + topTitleY, fontSpellChant.getWidth(SB_POTIONSTITLE), fontSpellChant.getHeight(SB_POTIONSTITLE));
        mnuItemEnnemiesTitle = new MenuItem(gc, MenuItem.MenuItemType.TEXT, SB_ENNEMIESTITLE, false, false, 0, textGap + topTitleY, fontSpellChant.getWidth(SB_ENNEMIESTITLE), fontSpellChant.getHeight(SB_ENNEMIESTITLE));
        mnuItemBack = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_BACK, false, false, 0, bottomButtonY, fontSpellChant.getWidth(SB_BACK), fontSpellChant.getHeight(SB_BACK));
        mnuItemNext = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_NEXT, false, false, 0, bottomButtonY, fontSpellChant.getWidth(SB_NEXT), fontSpellChant.getHeight(SB_NEXT));
        mnuItemChangeType = new MenuItem(gc, MenuItem.MenuItemType.BUTTON, SB_BACK, false, false, 0, (4*textGap), fontSpellChant.getWidth(SB_BACK), fontSpellChant.getHeight(SB_BACK));

        mnuItemBar = new MenuItem(gc, MenuItem.MenuItemType.INFO, "", false, false, centerXInfoBar, topInfoBarY + textGap, infoBarWidth, infoBarHeight);
        mnuItemBar2 = new MenuItem(gc, MenuItem.MenuItemType.INFO, "", false, false, centerXInfoBar, topInfoBarY + (infoBarHeight * 1) + textGap, infoBarWidth, infoBarHeight);
        mnuItemBar3 = new MenuItem(gc, MenuItem.MenuItemType.INFO, "", false, false, centerXInfoBar, topInfoBarY + (infoBarHeight * 2) + textGap, infoBarWidth, infoBarHeight);
        mnuItemBar4 = new MenuItem(gc, MenuItem.MenuItemType.INFO, "", false, false, centerXInfoBar, topInfoBarY + (infoBarHeight * 3) + textGap, infoBarWidth, infoBarHeight);
        mnuItemBar5 = new MenuItem(gc, MenuItem.MenuItemType.INFO, "", false, false, centerXInfoBar, topInfoBarY + (infoBarHeight * 4) + textGap, infoBarWidth, infoBarHeight);
        mnuItemBar6 = new MenuItem(gc, MenuItem.MenuItemType.INFO, "", false, false, centerXInfoBar, topInfoBarY + (infoBarHeight * 5) + textGap, infoBarWidth, infoBarHeight);
        mnuItemBar7 = new MenuItem(gc, MenuItem.MenuItemType.INFO, "", false, false, centerXInfoBar, topInfoBarY + (infoBarHeight * 6) + textGap, infoBarWidth, infoBarHeight);
        mnuItemBar8 = new MenuItem(gc, MenuItem.MenuItemType.INFO, "", false, false, centerXInfoBar, topInfoBarY + (infoBarHeight * 7) + textGap, infoBarWidth, infoBarHeight);

        float mnuItemMainTitleX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemMainTitle.getWidth() / 2);
        float mnuItemSpellsX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemSpells.getWidth() / 2);
        float mnuItemPotionsX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemPotions.getWidth() / 2);
        float mnuItemEnnemiesX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemEnnemies.getWidth() / 2);
        float mnuItemMenuX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemRetour.getWidth() / 2);
        float mnuItemSpellsTitleX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemSpellTitle.getWidth() / 2);
        float mnuItemPotionsTitleX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemPotionsTitle.getWidth() / 2);
        float mnuItemEnnemiesTitleX = (GameCore.SCREEN_SIZE.width / 4) - (mnuItemEnnemiesTitle.getWidth() / 2);
        float mnuItemBackX = mnuItemBack.getWidth() + 5 * GameCore.SCALE;
        float mnuItemNextX = GameCore.SCREEN_SIZE.width - mnuItemNext.getWidth() - 50 * GameCore.SCALE;

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
        bottomButtonY = GameCore.SCREEN_SIZE.height - mnuItemBack.getHeight() - (4 * textGap);

        infoList.add("1");
        infoList.add("2");
        infoList.add("3");
        infoList.add("4");
        infoList.add("5");
        infoList.add("6");
        infoList.add("7");
        infoList.add("8");

        this.icon = new Image("res/image/spellbook/icon.png");
        this.iconImage = iconImage;
        
    }

    /**
     * Renders the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @param g The Graphics component
     * @throws SlickException General Slick exception
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, GameCore.SCREEN_SIZE.width, GameCore.SCREEN_SIZE.height);
        switch (pageState) {
            case 0:
                g.setFont(fontMenu);
                displayMenu(g, gc);
                break;

            case 1:
                g.setFont(fontSpellChant);
                displaySpellsLeftPage(g, gc);
                break;

            case 2:
                g.setFont(fontSpellChant);
                displayPotionsLeftPage(g, gc);
                break;

//            case 3:
//                displayEnnemiesLeftPage(g, gc);
//                break;
        }
        MainMenuState.renderMouseCursor(gc);
    }

    /**
     * Updates the BasicGameState
     *
     * @param gc the GameContainer
     * @param game the StateBasedGame
     * @param delta the delta of the frame
     * @throws SlickException General Slick exception
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(GameCore.ID_PAUSE_MENU_STATE);
        }
        int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
        boolean triedToClick = gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);

        switch (pageState) {
            case 0:
                mnuItemSpells.detHoveredOver(mouseX, mouseY);
                mnuItemPotions.detHoveredOver(mouseX, mouseY);
//                mnuItemEnnemies.detHoveredOver(mouseX, mouseY);
                mnuItemRetour.detHoveredOver(mouseX, mouseY);

                if (mnuItemSpells.getHoveredOver() && triedToClick) {
                    pageState = 1;
                    spellsSubPageState = 0;
                }

                if (mnuItemPotions.getHoveredOver() && triedToClick) {
                    pageState = 2;
                    potionsSubPageState = 0;
                }

//                if (mnuItemEnnemies.getHoveredOver() && triedToClick) {
//                    pageState = 3;
//                    ennemiesSubPageState = 0;
//                }

                if (mnuItemRetour.getHoveredOver() && triedToClick) {
                    game.enterState(GameCore.ID_PAUSE_MENU_STATE);
                    pageState = 0;
                }
                break;

            case 1:
                mnuItemBack.detHoveredOver(mouseX, mouseY);
                mnuItemNext.detHoveredOver(mouseX, mouseY);
                mnuItemChangeType.detHoveredOver(mouseX, mouseY);
                mnuItemBar.detHoveredOver(mouseX, mouseY);
                mnuItemBar2.detHoveredOver(mouseX, mouseY);
                mnuItemBar3.detHoveredOver(mouseX, mouseY);
                mnuItemBar4.detHoveredOver(mouseX, mouseY);
                mnuItemBar5.detHoveredOver(mouseX, mouseY);
                mnuItemBar6.detHoveredOver(mouseX, mouseY);
                mnuItemBar7.detHoveredOver(mouseX, mouseY);
                mnuItemBar8.detHoveredOver(mouseX, mouseY);

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

                if (mnuItemBar.getHoveredOver() && triedToClick) {
                    switch (spellsSubPageState) {
                        case 0:
                            spellKind = 0;
                            showRightPage = true;
                            break;

                        case 1:
                            if (SpellingSystem.getKnownSpells().size() >= 9) {
                                spellKind = 8;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;

                        case 2:
                            if (SpellingSystem.getKnownSpells().size() >= 17) {
                                spellKind = 16;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;
                    }
                }

                if (mnuItemBar2.getHoveredOver() && triedToClick) {
                    switch (spellsSubPageState) {
                        case 0:
                            spellKind = 1;
                            showRightPage = true;
                            break;

                        case 1:
                            if (SpellingSystem.getKnownSpells().size() >= 10) {
                                spellKind = 9;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;

                        case 2:
                            if (SpellingSystem.getKnownSpells().size() >= 18) {
                                spellKind = 17;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;
                    }
                }

                if (mnuItemBar3.getHoveredOver() && triedToClick) {
                    switch (spellsSubPageState) {
                        case 0:
                            spellKind = 2;
                            showRightPage = true;
                            break;
                        case 1:
                            if (SpellingSystem.getKnownSpells().size() >= 11) {
                                spellKind = 10;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;

                        case 2:
                            if (SpellingSystem.getKnownSpells().size() >= 19) {
                                spellKind = 18;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;
                    }
                }

                if (mnuItemBar4.getHoveredOver() && triedToClick) {
                    switch (spellsSubPageState) {
                        case 0:
                            spellKind = 3;
                            showRightPage = true;
                            break;

                        case 1:
                            if (SpellingSystem.getKnownSpells().size() >= 12) {
                                spellKind = 11;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;

                        case 2:
                            if (SpellingSystem.getKnownSpells().size() >= 20) {
                                spellKind = 19;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;
                    }
                }

                if (mnuItemBar5.getHoveredOver() && triedToClick) {
                    switch (spellsSubPageState) {
                        case 0:
                            spellKind = 4;
                            showRightPage = true;
                            break;

                        case 1:
                            if (SpellingSystem.getKnownSpells().size() >= 13) {
                                spellKind = 12;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;

                        case 2:
                            if (SpellingSystem.getKnownSpells().size() >= 21) {
                                spellKind = 20;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;
                    }
                }

                if (mnuItemBar6.getHoveredOver() && triedToClick) {
                    switch (spellsSubPageState) {
                        case 0:
                            spellKind = 5;
                            showRightPage = true;
                            break;

                        case 1:
                            if (SpellingSystem.getKnownSpells().size() >= 14) {
                                spellKind = 13;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;

                        case 2:
                            if (SpellingSystem.getKnownSpells().size() >= 22) {
                                spellKind = 21;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;
                    }
                }

                if (mnuItemBar7.getHoveredOver() && triedToClick) {
                    switch (spellsSubPageState) {
                        case 0:
                            spellKind = 6;
                            showRightPage = true;
                            break;

                        case 1:
                            if (SpellingSystem.getKnownSpells().size() >= 15) {
                                spellKind = 14;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;

                        case 2:
                            if (SpellingSystem.getKnownSpells().size() >= 23) {
                                spellKind = 22;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;
                    }
                }

                if (mnuItemBar8.getHoveredOver() && triedToClick) {
                    switch (spellsSubPageState) {
                        case 0:
                            spellKind = 7;
                            showRightPage = true;
                            break;

                        case 1:
                            if (SpellingSystem.getKnownSpells().size() >= 16) {
                                spellKind = 15;
                                showRightPage = true;
                            } else {
                                showRightPage = false;
                            }
                            break;

                        case 2:
                            if (SpellingSystem.getKnownSpells().size() >= 24) {
                                spellKind = 23;
                                showRightPage = true;
                            } else {
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
                mnuItemBar.detHoveredOver(mouseX, mouseY);
                mnuItemBar2.detHoveredOver(mouseX, mouseY);
                mnuItemBar3.detHoveredOver(mouseX, mouseY);
                mnuItemBar4.detHoveredOver(mouseX, mouseY);

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

                if (mnuItemBar.getHoveredOver() && triedToClick) {
                    potionKind = 0;
                    showRightPage = true;
                }

                if (mnuItemBar2.getHoveredOver() && triedToClick) {
                    if (SpellingSystem.getPotionList().size() >= 2) {
                        potionKind = 1;
                        showRightPage = true;
                    } else {
                        showRightPage = false;
                    }
                }

                if (mnuItemBar3.getHoveredOver() && triedToClick) {
                    if (SpellingSystem.getPotionList().size() >= 3) {
                        potionKind = 2;
                        showRightPage = true;
                    } else {
                        showRightPage = false;
                    }
                }

                if (mnuItemBar4.getHoveredOver() && triedToClick) {
                    if (SpellingSystem.getPotionList().size() >= 4) {
                        potionKind = 3;
                        showRightPage = true;
                    } else {
                        showRightPage = false;
                    }
                }
                break;
//            case 3:
//                mnuItemBack.detHoveredOver(mouseX, mouseY);
//                mnuItemNext.detHoveredOver(mouseX, mouseY);
//                mnuItemChangeType.detHoveredOver(mouseX, mouseY);
//                mnuItemBar.detHoveredOver(mouseX, mouseY);
//                mnuItemBar2.detHoveredOver(mouseX, mouseY);
//                mnuItemBar3.detHoveredOver(mouseX, mouseY);
//                mnuItemBar4.detHoveredOver(mouseX, mouseY);
//                mnuItemBar5.detHoveredOver(mouseX, mouseY);
//                mnuItemBar6.detHoveredOver(mouseX, mouseY);
//                mnuItemBar7.detHoveredOver(mouseX, mouseY);
//                mnuItemBar8.detHoveredOver(mouseX, mouseY);
//
//                if (mnuItemBack.getHoveredOver() && triedToClick && spellsSubPageState == 0) {
//                    ennemiesSubPageState = 0;
//                } else if (mnuItemBack.getHoveredOver() && triedToClick) {
//                    ennemiesSubPageState--;
//                    showRightPage = false;
//                }
//
//                if (mnuItemNext.getHoveredOver() && triedToClick && spellsSubPageState == 2) {
//                    ennemiesSubPageState = 2;
//                } else if (mnuItemNext.getHoveredOver() && triedToClick) {
//                    ennemiesSubPageState++;
//                    showRightPage = false;
//                }
//
//                if (mnuItemChangeType.getHoveredOver() && triedToClick) {
//                    pageState = 0;
//                    showRightPage = false;
//                }
//
//                if (mnuItemBar.getHoveredOver() && triedToClick) {
//
//                }
//
//                if (mnuItemBar2.getHoveredOver() && triedToClick) {
//
//                }
//
//                if (mnuItemBar3.getHoveredOver() && triedToClick) {
//
//                }
//
//                if (mnuItemBar4.getHoveredOver() && triedToClick) {
//
//                }
//
//                if (mnuItemBar5.getHoveredOver() && triedToClick) {
//
//                }
//
//                if (mnuItemBar6.getHoveredOver() && triedToClick) {
//
//                }
//
//                if (mnuItemBar7.getHoveredOver() && triedToClick) {
//
//                }
//
//                if (mnuItemBar8.getHoveredOver() && triedToClick) {
//
//                }
//                break;
        }

        GameCore.clearInputRecord(gc);
    }

    private void displayMenu(Graphics g, GameContainer gc) throws SlickException {
        mnuItemMainTitle.render(g);
        mnuItemSpells.render(g);
        mnuItemPotions.render(g);
//        mnuItemEnnemies.render(g);
        mnuItemRetour.render(g);
    }

    private void displaySpellsLeftPage(Graphics g, GameContainer gc) throws SlickException {
        mnuItemSpellTitle.render(g);
        mnuItemBack.render(g);
        mnuItemNext.render(g);
        mnuItemChangeType.render(g);
        mnuItemBar.render(g);
        mnuItemBar2.render(g);
        mnuItemBar3.render(g);
        mnuItemBar4.render(g);
        mnuItemBar5.render(g);
        mnuItemBar6.render(g);
        mnuItemBar7.render(g);
        mnuItemBar8.render(g);

        float iconX = (GameCore.SCREEN_SIZE.width / 2) + (icon.getWidth() / 2) + 25 * GameCore.SCALE;
        float iconY = ((GameCore.SCREEN_SIZE.height / 18) * 3) - 30 * GameCore.SCALE;
        float iconSize = 200 * GameCore.SCALE;
        float gap = 30 * GameCore.SCALE;
        int i;

        switch (spellsSubPageState) { //Spell name title attributed in the information rectangles according to the page where the player stands
            case 0:
                for (infoBarFill = 0, i = 0; i < 8; infoBarFill++, i++) {
                    if (infoBarFill < SpellingSystem.getKnownSpells().size()) {
                        infoList.set(i, SpellingSystem.getKnownSpells().get(i).getName());
                    } else {
                        infoList.set(i, " ");
                    }
                }
                break;

            case 1:
                for (infoBarFill = 8, i = 0; i < 8; infoBarFill++, i++) {
                    if (infoBarFill < SpellingSystem.getKnownSpells().size()) {
                        infoList.set(i, SpellingSystem.getKnownSpells().get(infoBarFill).getName());
                    } else {
                        infoList.set(i, " ");
                    }
                }
                break;

            case 2:
                for (infoBarFill = 16, i = 0; i < 8; infoBarFill++, i++) {
                    if (infoBarFill < SpellingSystem.getKnownSpells().size()) {
                        infoList.set(i, SpellingSystem.getKnownSpells().get(infoBarFill).getName());
                    } else {
                        infoList.set(i, " ");
                    }
                }
                break;
        }

        g.setColor(new Color(1, 1, 1, 1f));
        g.drawString(infoList.get(0), mnuItemBar.getX() + (gap / 3), mnuItemBar.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(1), mnuItemBar.getX() + (gap / 3), mnuItemBar2.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(2), mnuItemBar.getX() + (gap / 3), mnuItemBar3.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(3), mnuItemBar.getX() + (gap / 3), mnuItemBar4.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(4), mnuItemBar.getX() + (gap / 3), mnuItemBar5.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(5), mnuItemBar.getX() + (gap / 3), mnuItemBar6.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(6), mnuItemBar.getX() + (gap / 3), mnuItemBar7.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(7), mnuItemBar.getX() + (gap / 3), mnuItemBar8.getY() + (infoBarHeight / 12));

        displaySpellsRightPage(g, iconX, iconY, iconSize, gap);
    }

    private void displayPotionsLeftPage(Graphics g, GameContainer gc) throws SlickException {
        mnuItemPotionsTitle.render(g);
        mnuItemBack.render(g);
        mnuItemChangeType.render(g);
        mnuItemBar.render(g);
        mnuItemBar2.render(g);
        mnuItemBar3.render(g);
        mnuItemBar4.render(g);

        float iconX = (GameCore.SCREEN_SIZE.width / 2) + (icon.getWidth() / 2) + 25 * GameCore.SCALE;
        float iconY = ((GameCore.SCREEN_SIZE.height / 18) * 3) - 30 * GameCore.SCALE;
        float iconSize = 200 * GameCore.SCALE;
        float gap = 30 * GameCore.SCALE;
        int i;

        for (infoBarFill = 0, i = 0; i < 4; infoBarFill++, i++) {
            if (infoBarFill < SpellingSystem.getPotionList().size()) {
                infoList.set(i, SpellingSystem.getPotionList().get(i).getName());
            } else {
                infoList.set(i, " ");
            }
        }
        g.setColor(new Color(1, 1, 1, 1f));
        g.drawString(infoList.get(0), mnuItemBar.getX() + (gap / 3), mnuItemBar.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(1), mnuItemBar.getX() + (gap / 3), mnuItemBar2.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(2), mnuItemBar.getX() + (gap / 3), mnuItemBar3.getY() + (infoBarHeight / 12));
        g.drawString(infoList.get(3), mnuItemBar.getX() + (gap / 3), mnuItemBar4.getY() + (infoBarHeight / 12));

        displayPotionsRightPage(g, iconX, iconY, iconSize, gap);
    }

//    private void displayEnnemiesLeftPage(Graphics g, GameContainer gc) throws SlickException {
//        mnuItemEnnemiesTitle.render(g);
//        mnuItemBack.render(g);
//        mnuItemNext.render(g);
//        mnuItemChangeType.render(g);
//        mnuItemBar.render(g);
//        mnuItemBar2.render(g);
//        mnuItemBar3.render(g);
//        mnuItemBar4.render(g);
//        mnuItemBar5.render(g);
//        mnuItemBar6.render(g);
//        mnuItemBar7.render(g);
//        mnuItemBar8.render(g);
//
//        //g.setFont(fontSpellChant);
//        float iconX = (GameCore.SCREEN_SIZE.width / 2) + (icon.getWidth() / 2) - 20 * GameCore.SCALE;
//        float iconY = ((GameCore.SCREEN_SIZE.height / 18) * 3) - 30 * GameCore.SCALE;
//        float iconSize = 200 * GameCore.SCALE;
//        float gap = 30 * GameCore.SCALE;
//        int i;
//
//        switch (ennemiesSubPageState) { //Spell name title attributed in the information rectangles according to the page where the player stands
//            case 0:
//                for (infoBarFill = 0, i = 0; i < 8; infoBarFill++, i++) {
//                    if (infoBarFill < SpellingSystem.getKnownSpells().size()) {
//                        infoList.set(i, SpellingSystem.getKnownSpells().get(i).getName());
//                    } else {
//                        infoList.set(i, " ");
//                    }
//                }
//
//            case 1:
//                for (infoBarFill = 0, i = 0; i < 8; infoBarFill++, i++) {
//                    if (infoBarFill < SpellingSystem.getKnownSpells().size()) {
//                        infoList.set(i, SpellingSystem.getKnownSpells().get(i).getName());
//                    } else {
//                        infoList.set(i, " ");
//                    }
//                }
//                break;
//        }
//
//        g.setColor(new Color(1, 1, 1, 1f));
//        g.drawString(infoList.get(0), mnuItemBar.getX() + (gap / 3), mnuItemBar.getY() + (infoBarHeight / 12) + textGap);
//        g.drawString(infoList.get(1), mnuItemBar.getX() + (gap / 3), mnuItemBar2.getY() + (infoBarHeight / 12) + textGap);
//        g.drawString(infoList.get(2), mnuItemBar.getX() + (gap / 3), mnuItemBar3.getY() + (infoBarHeight / 12) + textGap);
//        g.drawString(infoList.get(3), mnuItemBar.getX() + (gap / 3), mnuItemBar4.getY() + (infoBarHeight / 12) + textGap);
//        g.drawString(infoList.get(4), mnuItemBar.getX() + (gap / 3), mnuItemBar5.getY() + (infoBarHeight / 12) + textGap);
//        g.drawString(infoList.get(5), mnuItemBar.getX() + (gap / 3), mnuItemBar6.getY() + (infoBarHeight / 12) + textGap);
//        g.drawString(infoList.get(6), mnuItemBar.getX() + (gap / 3), mnuItemBar7.getY() + (infoBarHeight / 12) + textGap);
//        g.drawString(infoList.get(7), mnuItemBar.getX() + (gap / 3), mnuItemBar8.getY() + (infoBarHeight / 12) + textGap);
//
//        displayEnnemiesRightPage(g, iconX, iconY, iconSize, gap);
//    }

    private void displaySpellsRightPage(Graphics g, float iconX, float iconY, float iconSize, float gap) {
        g.setFont(fontDetails);        
        
        page = Integer.toString(pageNumber);
        g.drawString(page, ((GameCore.SCREEN_SIZE.width * 3) / 4) + 30 * GameCore.SCALE, GameCore.SCREEN_SIZE.height - (gap * 3));
        
        if (showRightPage) {

            icon.draw(iconX, iconY, iconSize, iconSize);
            
            if (SpellingSystem.getKnownSpells().get(spellKind) instanceof BurstSpell) {
                type = "Sort de souffle";
            } else if (SpellingSystem.getKnownSpells().get(spellKind) instanceof ExplosionSpell) {
                type = "Explosion";
            } else if (SpellingSystem.getKnownSpells().get(spellKind) instanceof ProjectileSpell) {
                type = "Projectile";
            } else if (SpellingSystem.getKnownSpells().get(spellKind) instanceof HealingSpell) {
                type = "Guérison";
            } else if (SpellingSystem.getKnownSpells().get(spellKind) instanceof PassiveSpell) {
                type = "Passif";
            }

            name = "Nom: " + SpellingSystem.getKnownSpells().get(spellKind).getName();
            incantationText = "Mot déclencheur: " + SpellingSystem.getKnownSpells().get(spellKind).getIncantation();
            shortDescription = "Petite description: " + SpellingSystem.getKnownSpells().get(spellKind).getDesc();

            switch (type) {//damage and effects relative to the spell's type
                case "Sort de souffle":
                    String damagePoints = Integer.toString(((BurstSpell) SpellingSystem.getKnownSpells().get(spellKind)).getDamage());
                    damage = "Dégâts: " + damagePoints;
                    g.drawString(damage, iconX + iconSize + gap, iconY + 4 * gap);
                    break;

                case "Explosion":
                    damagePoints = Integer.toString(((ExplosionSpell) SpellingSystem.getKnownSpells().get(spellKind)).getDamage());
                    damage = "Dégâts: " + damagePoints;
                    g.drawString(damage, iconX + iconSize + gap, iconY + 4 * gap);
                    break;

                case "Projectile":
                    damagePoints = Integer.toString(((ProjectileSpell) SpellingSystem.getKnownSpells().get(spellKind)).getDamage());
                    damage = "Dégâts: " + damagePoints;
                    g.drawString(damage, iconX + iconSize + gap, iconY + 4 * gap);
                    break;

                case "Guérison":
                    String healingPoints = Integer.toString(((HealingSpell) SpellingSystem.getKnownSpells().get(spellKind)).getHealing());
                    effect = "Effet: " + healingPoints + " points de santé";
                    g.drawString(effect, iconX + iconSize + gap, iconY + 4 * gap);
                    break;

                case "Passif":
                    switch (SpellingSystem.getKnownSpells().get(spellKind).getName()) {

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

            switch (SpellingSystem.getKnownSpells().get(spellKind).getElement()) {
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
            float x = 864f * GameCore.SCALE;
            float y = 119f * GameCore.SCALE;
            Animation temp = SpellingSystem.getKnownSpells().get(spellKind).getAnimation();
            float width = temp.getWidth();
            float height = temp.getHeight();
            float ratio = width / height;
            if (width < height) {
                height = 178f * GameCore.SCALE;
                width = height * ratio;
            } else {
                width = 178f * GameCore.SCALE;
                height = width / ratio;
            }
            temp.draw(x + (145f * GameCore.SCALE) - (width / 2), y + (100f * GameCore.SCALE) - (height / 2), width, height);
            g.drawString(name, iconX + iconSize + gap, iconY);
            g.drawString("Type: " + type, iconX + iconSize + gap, iconY + 2 * gap);
            g.drawString(element, iconX + iconSize + gap, iconY + 6 * gap);
            g.drawString(incantationText, iconX, iconY + 8 * gap);
            g.drawString(shortDescription, iconX, iconY + 10 * gap);
        }
    }

    private void displayPotionsRightPage(Graphics g, float iconX, float iconY, float iconSize, float gap) {
        g.setFont(fontDetails);
        
        page = Integer.toString(pageNumber);
        g.drawString(page, ((GameCore.SCREEN_SIZE.width * 3) / 4) + 30 * GameCore.SCALE, GameCore.SCREEN_SIZE.height - (gap * 3));
        if (showRightPage) {
            icon.draw(iconX, iconY, iconSize, iconSize);
            name = "Nom: " + SpellingSystem.getPotionList().get(potionKind).getName();
            shortDescription = "Petite description: " + SpellingSystem.getPotionList().get(potionKind).getDesc();
            element = "Élément: Aucun";

            switch (SpellingSystem.getPotionList().get(potionKind).getName()) {
                case "Soin":
                    String healingPoints = Integer.toString(((HealingSpell) SpellingSystem.getPotionList().get(potionKind)).getHealing());
                    effect = "Effet: " + healingPoints + " points de santé";
                    activationButton = "Bouton déclencheur: 1";
                    g.drawString(effect, iconX + iconSize + gap, iconY + 2 * gap);
                    break;
                case "Acide":
                    String damagePoints = Integer.toString(((ProjectileSpell) SpellingSystem.getPotionList().get(potionKind)).getDamage());
                    damage = "Dégâts: " + damagePoints;
                    element = "Élément: Neutre";
                    activationButton = "Bouton déclencheur: 2";
                    g.drawString(damage, iconX + iconSize + gap, iconY + 2 * gap);
                    break;
                case "Passé":
                    effect = "Effet: Sort précédent";
                    activationButton = "Bouton déclencheur: 3";
                    g.drawString(effect, iconX + iconSize + gap, iconY + 2 * gap);
                    break;
                case "Ralentissement du temps":
                    effect = "Effet: Invicibilité temporaire";
                    activationButton = "Bouton déclencheur: 4";
                    g.drawString(effect, iconX + iconSize + gap, iconY + 2 * gap);
                    break;
            }
            SpellingSystem.getPotionList().get(potionKind).getAnimation().draw(iconX + 10, iconY + 10, iconSize - (20 * GameCore.SCALE), iconSize - (20 * GameCore.SCALE));
            
            g.drawString(name, iconX + iconSize + gap, iconY);
            g.drawString(element, iconX + iconSize + gap, iconY + 4 * gap);
            g.drawString(activationButton, iconX + iconSize + gap, iconY + 6 * gap);
            g.drawString(shortDescription, iconX, iconY + 8 * gap);
        }
    }

//    private void displayEnnemiesRightPage(Graphics g, float iconX, float iconY, float iconSize, float gap) {
//
//    }

    @Override
    public int getID() {
        return GameCore.ID_SPELLBOOK_STATE;
    }

}
