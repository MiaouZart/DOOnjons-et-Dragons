package spell.type;

import entity.Entity;
import entity.EnumEntity;
import entity.personnage.Personnage;
import equipment.Equipment;
import equipment.EquipmentType;
import equipment.weapon.Weapon;
import spell.Spell;

import java.util.HashMap;
import java.util.Scanner;

import static donjon.Display.promptInt;

public class MagicWeapon extends Spell {
    /**
     * Constructeur du sort Arme Magique.
     */
    public MagicWeapon() {
        super("Arme Magique");
    }

    @Override
    public void spell(HashMap<Entity, int[]> entities, String[][] grid) {
        int i = 0;
        System.out.println("Veuillez choisir un personnage à qui améliorer une arme :");
        for (Entity e : entities.keySet())
            if (e.getType() == EnumEntity.PERSONNAGE) {
                System.out.printf("[\033[1;4m%d\033[0m]\t\t%s\n", i, e);
                i++;
            }

        int choixPerso = promptInt(new Scanner(System.in), "Choix", 0, i);

        i = 0;
        for (Entity e : entities.keySet())
            if (e.getType() == EnumEntity.PERSONNAGE) {
                if (i == choixPerso) {
                    System.out.println("Veuillez choisir une arme à améliorer\n");
                    Personnage p = (Personnage) e;
                    int j = 0;
                    for (Equipment eq: p.getInventory()) {
                        if (eq.getEquipmentType() == EquipmentType.WEAPON) {
                            System.out.printf("[\033[1;4m%d\033[0m]\t\t%s\n", j, eq);
                            j++;
                        }
                    }
                    int choixArme = promptInt(new Scanner(System.in), "Choix", 0, j);
                    j = 0;
                    for (Equipment eq: p.getInventory()) {
                        if (eq.getEquipmentType() == EquipmentType.WEAPON) {
                            if (j == choixArme) {
                                Weapon w = (Weapon) eq;
                                w.addBonusAttack(1);
                                w.addBonusDamage(1);
                                System.out.printf("%s - Bonus d'attaque : %d; Bonus de dommages : %d\n", w, w.getBonusAttack(), w.getBonusDamage());
                                return;
                            }
                            j++;
                        }
                    }
                }
                i++;
            }
    }
}
