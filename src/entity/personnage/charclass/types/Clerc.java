package entity.personnage.charclass.types;

import entity.personnage.charclass.CharClass;
import equipment.armor.types.ScaleMail;
import equipment.weapon.types.Crossbow;
import equipment.weapon.types.Mace;
import equipment.Equipment;
import spell.Spell;
import spell.type.Heal;

public class Clerc extends CharClass {
    /**
     * Constructeur Clerc
     */
    public Clerc() {
        super("Clerc", 16,
                new Equipment[]{new Mace(), new ScaleMail(), new Crossbow()},
                new Spell[]{new Heal()}
        );
    }
}
