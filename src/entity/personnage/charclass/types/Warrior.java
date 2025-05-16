package entity.personnage.charclass.types;

import entity.personnage.charclass.CharClass;
import equipment.weapon.types.Longsword;
import equipment.armor.types.ChainMail;
import equipment.weapon.types.Crossbow;
import equipment.Equipment;

public class Warrior extends CharClass {
    public Warrior() {
        super("Guerrier", 20, new Equipment[]{new ChainMail(), new Longsword(), new Crossbow()});
    }
}
