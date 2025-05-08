package test.CharClass;

import Entity.Personnage.CharClass.CharClass;
import Entity.Personnage.CharClass.Types.*;
import Equipment.Armor.Types.*;
import Equipment.Weapon.Types.*;

import java.util.Objects;

public class CharClassTest {
    @org.junit.jupiter.api.Test
    void ClercTest() {
        CharClass testClass = new Clerc();
        assert testClass.getM_name().equals("Clerc");
        assert testClass.getBaseHealth() == 16;
        assert testClass.getBaseStuff().length == 3;
        assert Objects.equals(testClass.getBaseStuff()[0], new Mace());
        assert Objects.equals(testClass.getBaseStuff()[1], new ScaleMail());
        assert Objects.equals(testClass.getBaseStuff()[2], new Crossbow());
    }

    @org.junit.jupiter.api.Test
    void RogueTest() {
        CharClass testClass = new Rogue();
        assert testClass.getM_name().equals("Roublard");
        assert testClass.getBaseHealth() == 16;
        assert testClass.getBaseStuff().length == 2;
        assert Objects.equals(testClass.getBaseStuff()[0], new Rapier());
        assert Objects.equals(testClass.getBaseStuff()[1], new Shortbow());
    }

    @org.junit.jupiter.api.Test
    void WarriorTest() {
        CharClass testClass = new Warrior();
        assert testClass.getM_name().equals("Guerrier");
        assert testClass.getBaseHealth() == 20;
        assert testClass.getBaseStuff().length == 3;
        assert Objects.equals(testClass.getBaseStuff()[0], new ChainMail());
        assert Objects.equals(testClass.getBaseStuff()[1], new Longsword());
        assert Objects.equals(testClass.getBaseStuff()[2], new Crossbow());
    }

    @org.junit.jupiter.api.Test
    void WizardTest() {
        CharClass testClass = new Wizard();
        assert testClass.getM_name().equals("Magicien");
        assert testClass.getBaseHealth() == 12;
        assert testClass.getBaseStuff().length == 2;
        assert Objects.equals(testClass.getBaseStuff()[0], new Quarterstaff());
        assert Objects.equals(testClass.getBaseStuff()[1], new Sling());
    }
}
