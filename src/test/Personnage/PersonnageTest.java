package test.Personnage;

import Entity.Personnage.CharClass.Types.*;
import Entity.Personnage.Personnage;
import Entity.Personnage.Race.Types.*;
import Equipment.Weapon.Types.*;
import Equipment.Armor.Types.*;

import org.junit.jupiter.api.Test;

import java.util.Objects;

class PersonnageTest {
    @Test
    void getCharClass() {
        Personnage personnage = new Personnage("Bruno", new Dwarf(), new Rogue());
        assert personnage.getCharClass().equals(new Rogue());
    }

    @Test
    void getInventory() {
        Personnage personnage = new Personnage("Christophe", new Elf(), new Warrior());
        assert personnage.getInventory().length == 3;
        assert personnage.getInventory()[0].equals(new ChainMail());
        assert personnage.getInventory()[1].equals(new Longsword());
        assert personnage.getInventory()[2].equals(new Crossbow());
    }

    @Test
    void getWeapon() {
        Personnage personnage = new Personnage("Valérie", new Human(), new Clerc());
        assert personnage.getWeapon() == null;
    }

    @Test
    void getArmor() {
        Personnage personnage = new Personnage("Christian", new Halfelin(), new Warrior());
        assert personnage.getArmor() == null;
    }

    @Test
    void getRace() {
        Personnage personnage = new Personnage("Dupont", new Halfelin(), new Wizard());
        assert personnage.getRace().equals(new Halfelin());
    }

    @Test
    void equip() {
        Personnage personnage = new Personnage("Sébastien", new Dwarf(), new Rogue());
        assert personnage.getWeapon() == null;
        assert personnage.getArmor() == null;

        personnage.equip(new Shortbow());
        assert personnage.getWeapon().equals(new Shortbow());
        assert personnage.getArmor() == null;
        personnage.equip(new Longsword());
        assert personnage.getWeapon().equals(new Shortbow());
        assert personnage.getArmor() == null;
        personnage.equip(null);
        assert personnage.getWeapon() == null;
        assert personnage.getArmor() == null;
        personnage.equip(new Plate());
        assert personnage.getWeapon() == null;
        assert personnage.getArmor() == null;
        personnage.take(new Plate());
        personnage.equip(new Plate());
        assert personnage.getWeapon() == null;
        assert Objects.equals(personnage.getArmor(), new Plate());
        personnage.equip(new Shortbow());
        assert Objects.equals(personnage.getWeapon(), new Shortbow());
        assert Objects.equals(personnage.getArmor(), new Plate());
    }

    @Test
    void take() {
        Personnage personnage = new Personnage("Sébastien 2", new Dwarf(), new Clerc());
        assert personnage.getInventory().length == 3;
        personnage.take(new Plate());
        assert personnage.getInventory().length == 4;
        assert personnage.getInventory()[3].equals(new Plate());
    }
}