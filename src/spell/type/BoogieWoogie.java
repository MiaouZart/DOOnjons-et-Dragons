package spell.type;

import entity.Entity;
import spell.Spell;

import java.util.HashMap;
import java.util.Scanner;

import static donjon.Display.promptInt;

public class BoogieWoogie extends Spell {

    /**
     * Constructeur du sort Boogie Woogie.
     */
    public BoogieWoogie() {
        super("Boogie Woogie");
    }

    @Override
    public void spell(HashMap<Entity, int[]> entities) {
        int i;
        for (i = 0; i< entities.size(); i++) {
            Entity e = (Entity) entities.keySet().toArray()[i];
            System.out.printf("[\033[1;4m%d\033[0m]\t\t\033[36m[%d,%d]\033[0m\t%s\n", i, entities.get(e)[0], entities.get(e)[1], e);
        }

        int selected0 = promptInt(new Scanner(System.in), "Choix du premier personnage à déplacer", 0, i);

        for (i = 0; i< entities.size(); i++) {
            if (i == selected0)
                continue;
            Entity e = (Entity) entities.keySet().toArray()[i];
            System.out.printf("[\033[1;4m%d\033[0m]\t\t\033[36m[%d,%d]\033[0m\t%s\n", i, entities.get(e)[0], entities.get(e)[1], e);
        }

        int selected1 = -1;
        while (selected1 < 0 || selected1 > i || selected1 == selected0)
            selected1 = promptInt(new Scanner(System.in), "Choix du second personnage à déplacer", 0, i);

        Entity entity0 = (Entity) entities.keySet().toArray()[selected0];
        Entity entity1 = (Entity) entities.keySet().toArray()[selected1];
        int[] pos0 = entities.get(entity0);
        entities.replace(entity0, entities.get(entity1));
        entities.replace(entity1, pos0);
    }
}
