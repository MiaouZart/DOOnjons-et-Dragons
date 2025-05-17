package entity.personnage.charclass.types;

import entity.personnage.charclass.CharClass;
import equipment.weapon.types.Quarterstaff;
import equipment.weapon.types.Sling;
import equipment.Equipment;

public class Wizard extends CharClass {
    public Wizard() {
        super("Magicien", 12, new Equipment[]{new Quarterstaff(), new Sling()});
    }
}
