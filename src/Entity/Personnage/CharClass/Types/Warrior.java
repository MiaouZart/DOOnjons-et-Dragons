package Entity.Personnage.CharClass.Types;

import Entity.Personnage.CharClass.CharClass;
import Equipment.Weapon.Types.Longsword;
import Equipment.Armor.Types.ChainMail;
import Equipment.Weapon.Types.Crossbow;
import Equipment.Equipment;

public class Warrior extends CharClass {
    public Warrior() {
        super("Guerrier", 20, new Equipment[]{new ChainMail(), new Longsword(), new Crossbow()});
    }
}
