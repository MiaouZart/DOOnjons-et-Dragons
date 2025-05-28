package entity.personnage.charclass.types;

import entity.personnage.charclass.CharClass;
import equipment.weapon.types.*;
import spell.type.*;
import equipment.Equipment;
import spell.Spell;

public class Wizard extends CharClass {
    /**
     * Constructeur Wizard
     */
    public Wizard() {
        super("Magicien", 12,
                new Equipment[]{new Quarterstaff(), new Sling()},
                new Spell[]{
                new MagicWeapon(),
                new BoogieWoogie(),
                new Heal(),
        });
    }
}
