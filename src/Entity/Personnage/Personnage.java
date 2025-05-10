package Entity.Personnage;

import Dice.Dice;
import Entity.Entity;
import Entity.Personnage.CharClass.CharClass;
import Entity.Personnage.Race.Race;
import Equipment.Armor.Armor;
import Equipment.Equipment;
import Equipment.Weapon.Weapon;

import java.util.Arrays;

public class Personnage extends Entity {
    private final String m_name;
    private final Race m_race;
    private final CharClass m_charClass;
    private Equipment[] m_inventory;
    private Weapon m_weapon;
    private Armor m_armor;

    public Personnage(int X, int Y, String name, Race race, CharClass charClass) {
        super(X, Y, charClass.getBaseHealth(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getStrenght(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getDex(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getSpeed(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getInitiative());
        m_name = name;
        m_race = race;
        m_charClass = charClass;
        m_inventory = charClass.getBaseStuff();;

    }

    public Personnage(String name, Race race, CharClass charClass) {
        super(charClass.getBaseHealth(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getStrenght(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getDex(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getSpeed(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getInitiative());
        m_name = name;
        m_race = race;
        m_charClass = charClass;
        m_inventory = charClass.getBaseStuff();
    }

    public CharClass getCharClass() {
        return m_charClass;
    }

    public Equipment[] getInventory() {
        return m_inventory;
    }

    public Weapon getWeapon() {
        return m_weapon;
    }

    public Armor getArmor() {
        return m_armor;
    }

    public Race getRace() {
        return m_race;
    }

    public void equip(Equipment equipment) {
        if (equipment == null) {
            m_weapon = null;
            m_armor = null;
            return;
        }

        if (Weapon.class.isAssignableFrom(equipment.getClass())) {
            for (Equipment e : m_inventory) {
                if (Weapon.class.isAssignableFrom(e.getClass())) {
                    if (((Weapon)equipment).equals((Weapon)e)) {
                        m_weapon = (Weapon) equipment;
                        return;
                    }
                }
            }
            return;
        }
        if (Armor.class.isAssignableFrom(equipment.getClass())) {
            for (Equipment e : m_inventory) {
                if (Armor.class.isAssignableFrom(e.getClass())) {
                    if (((Armor)equipment).equals((Armor) e)) {
                        m_armor = (Armor) equipment;
                        return;
                    }
                }
            }
        }
    }

    public void take(Equipment equipment) {
        m_inventory = Arrays.copyOf(m_inventory, m_inventory.length+1);
        m_inventory[m_inventory.length-1] = equipment;
    }

    @Override
    public int getSpeed() {
        return super.getSpeed() - (m_weapon == null ? 0 : m_weapon.getSpeedModifier() - (m_armor == null ? 0 : m_armor.getSpeedModifier()));
    }

    @Override
    public int getStrength() {
        return super.getStrength() + (m_weapon == null ? 0 : m_weapon.getStrengthModifier());
    }

    @Override
    public String toString() {
        return this.m_name+" ( "+this.m_charClass+" )";
    }
}
