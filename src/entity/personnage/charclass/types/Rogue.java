package entity.personnage.charclass.types;

import entity.personnage.charclass.CharClass;
import equipment.Equipment;
import equipment.weapon.types.Rapier;
import equipment.weapon.types.Shortbow;

public class Rogue extends CharClass {
    /**
     * Constructeur Rogue
     */
    public Rogue() {
        super("Roublard", 16, new Equipment[]{new Rapier(), new Shortbow()});
    }
}
