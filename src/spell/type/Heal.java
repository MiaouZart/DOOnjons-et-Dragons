package spell.type;

import dice.Dice;
import entity.Entity;
import entity.EnumEntity;
import spell.Spell;

import java.util.HashMap;
import java.util.Scanner;

import static donjon.Display.promptInt;

public class Heal extends Spell {
    /**
     * Constructeur du sort Heal.
     */
    public Heal() {
        super("Guérison");
    }

    @Override
    public void spell(HashMap<Entity, int[]> entities) {
        int i = 0;
        System.out.println("Veuillez choisir un personnage à soigner (1d10) :");
        for (Entity e : entities.keySet())
            if (e.getType() == EnumEntity.PERSONNAGE) {
                System.out.printf("[%d]\t\t%d/%d | %s\n", i, e.getHp(), e.getMaxHp(), e);
                i++;
            }

        int choixPerso = promptInt(new Scanner(System.in), "Choix", 0, i);

        i = 0;
        for (Entity e : entities.keySet())
            if (e.getType() == EnumEntity.PERSONNAGE) {
                if (i == choixPerso) {
                    int dice = Dice.sumUp(new Dice(1, 10).roll());
                    System.out.printf("1d10 lancé...\t\t%d\n", dice);
                    e.takeHeal(dice);
                    System.out.printf("%s à maintenant %d/%d\n", e, e.getHp(), e.getMaxHp());
                    return;
                }
                i++;
            }
    }
}
