package entity.personnage;

import dice.Dice;
import entity.Entity;
import entity.EnumEntity;
import entity.personnage.charclass.CharClass;
import entity.personnage.race.Race;
import equipment.EquipmentType;
import equipment.armor.Armor;
import equipment.Equipment;
import equipment.weapon.Weapon;
import spell.Spell;

import java.util.Arrays;

public class Personnage extends Entity {
    private final String m_name;
    private final Race m_race;
    private final CharClass m_charClass;
    private Equipment[] m_inventory;
    private Weapon m_weapon;
    private Armor m_armor;

    /**
     * Constructeur du personnage
     * @param name Nom du personnage
     * @param race Race du personnage
     * @param charClass Classe du personnage
     */
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
        m_type = EnumEntity.PERSONNAGE;
        m_armor = null;
    }

    /**
     * Getter de la classe
     * @return Classe du personnage
     */
    public CharClass getCharClass() {
        return m_charClass;
    }

    /**
     * Getter de l'inventaire
     * @return Inventaire du personnage
     */
    public Equipment[] getInventory() {
        return m_inventory;
    }

    /**
     * Getter de l'arme équipé
     * @return Arme du personnage
     */
    public Weapon getWeapon() {
        return m_weapon;
    }

    /**
     * Getter de l'armure équipé
     * @return Armure équipée du personnage
     */
    public Armor getArmor() {
        return m_armor;
    }

    /**
     * Getter de la race
     * @return Race du personnage
     */
    public Race getRace() {
        return m_race;
    }

    /**
     * Getter de la liste des sorts
     * @return Liste des sorts du personnage
     */
    public Spell[] getSpells() {
        return m_charClass.getSpells();
    }

    /**
     * Équiper une armure<br>
     * Voir equip(arme) pour équiper une arme.
     * @param armor Armure à équiper
     */
    public void equip(Armor armor) {
        if (armor == null) {
            m_armor = null;
            return;
        }
        for (Equipment e : m_inventory) {
            if (!(e.getEquipmentType() == EquipmentType.ARMOR)) continue;
            if (!armor.equals(e)) continue;

            m_armor = armor;
            return;
        }
    }

    /**
     * Équiper une arme.<br>
     * Voir equip(armor) pour équiper une armure.
     * @param weapon Arme à équiper
     */
    public void equip(Weapon weapon) {
        if (weapon == null) {
            m_weapon = null;
            return;
        }

        for (Equipment e : m_inventory) {
            if (!(e.getEquipmentType() == EquipmentType.WEAPON)) continue;
            if (!weapon.equals(e)) continue;
            m_weapon = weapon;
            return;
        }
    }

    public void equip(Equipment equipment) {
        if (equipment == null) return;
        if (equipment.getEquipmentType() == EquipmentType.ARMOR)
            equip((Armor) equipment);
        if (equipment.getEquipmentType() == EquipmentType.WEAPON)
            equip((Weapon) equipment);
    }

    /**
     * Ajoute un nouvel équipement dans l'inventaire.
     * @param equipment Équipement à ajouter
     */
    public void take(Equipment equipment) {
        m_inventory = Arrays.copyOf(m_inventory, m_inventory.length + 1);
        m_inventory[m_inventory.length - 1] = equipment;
    }

    /**
     * Récupère l'inventaire sous chaîne de caractère.
     * @return Chaîne de caractère représentant l'inventaire.
     */
    public String getInventoryString() {
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

    /**
     * Getter pour la portée de l'arme équipée.
     * @return Nombre entier représentant la portée.
     */
    public int getRangePoint() {
        return this.getWeapon().getRange();
    }

    @Override
    public int getArmorPoint() {
        return this.getArmor().getClassVal();
    }

    @Override
    public int damage() {
        return this.m_weapon.damage();
    }

    @Override
    public int attackBonus() {
        return this.m_weapon.getBonusAttack();
    }

    @Override
    public Dice getDice() {
        return m_weapon.getDamageDice();
    }

    @Override
    public String getSprite() {
        return "\033[32m" + this.m_name.trim().substring(0,3).toUpperCase() + "\033[0m";
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
