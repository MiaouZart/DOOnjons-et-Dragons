package entity.personnage;

import dice.Dice;
import entity.Entity;
import entity.EnumEntity;
import entity.personnage.charclass.CharClass;
import entity.personnage.race.Race;
import equipment.armor.Armor;
import equipment.Equipment;
import equipment.weapon.Weapon;

import java.util.Arrays;

public class Personnage extends Entity {
    private final String m_name;
    private final Race m_race;
    private final CharClass m_charClass;
    private Equipment[] m_inventory;
    private Weapon m_weapon;
    private Armor m_armor;

    public Personnage(String name, Race race, CharClass charClass) {
        super(charClass.getBaseHealth(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getStrength(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getDex(),
                Arrays.stream(new Dice(4, 4).roll()).sum() + 3 + race.getSpeed(),
                race.getInitiative());
        m_name = name;
        m_race = race;
        m_charClass = charClass;
        m_inventory = charClass.getBaseStuff();
        System.out.println(m_inventory.length);
        m_type = EnumEntity.PERSONNAGE;
        m_armor = null;
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

    public void equip(Armor armor) {
        if (armor == null) {
            m_armor = null;
            return;
        }

        for (Equipment e : m_inventory) {
            if (!Armor.class.isAssignableFrom(e.getClass())) continue;
            if (!armor.equals(e)) continue;

            m_armor = armor;
            return;
        }
    }

    public void equip(Weapon weapon) {
        if (weapon == null) {
            m_weapon = null;
            return;
        }

        for (Equipment e : m_inventory) {
            if (!Weapon.class.isAssignableFrom(e.getClass())) continue;
            if (!weapon.equals(e)) continue;
            m_weapon = weapon;
            return;
        }
    }

    public void take(Equipment equipment) {
        m_inventory = Arrays.copyOf(m_inventory, m_inventory.length+1);
        m_inventory[m_inventory.length-1] = equipment;
    }


    public String getInventoryString(){
        if (m_inventory == null || m_inventory.length == 0) {
            return "(aucune)";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m_inventory.length; i++) {
            sb.append("[").append(i).append("]");
            sb.append(m_inventory[i] != null ? m_inventory[i].toString() : "(vide)");
            if (i < m_inventory.length - 1) {
                sb.append(" ; ");
            }
        }

        return sb.toString();
    }



    public int getRangePoint(){
        return this.getWeapon().getRange();
    }

    @Override
    public int getArmorPoint() {
        return this.getArmor().getClassVal();
    }

    @Override
    public int attack() {
        return this.m_weapon.attack();
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
