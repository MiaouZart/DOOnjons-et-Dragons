package donjon.defaultDonjon;

import dice.Dice;
import entity.Entity;
import entity.monster.Monster;
import entity.personnage.Personnage;
import entity.personnage.charclass.types.*;
import entity.personnage.race.types.*;
import equipment.armor.types.ChainMail;
import equipment.weapon.types.Longsword;

import java.util.HashMap;

public class Default2 extends Default {

    /**
     * Constructeur pour générer un donjon par défaut.
     * @param entities Liste des entités à déjà inclure.
     */
    public Default2(HashMap<Entity, int[]> entities) {
        super(entities, 15);
    }

    protected void initializeDefaultGrid() {
        String[][] grid = initialiseWalls(m_donjon);

        grid[7][7] = " # "; // Mur central
        grid[3][3] = " E "; // Entrée
        grid[11][11] = " E "; // Sortie
    }

    protected void createDefaultPlayers() {
        String[][] grid = m_donjon.getDonjonGrid();

        Personnage player1 = new Personnage("Frodo", new Human(), new Rogue());
        player1.equip(new Longsword());

        Personnage player2 = new Personnage("Boromir", new Human(), new Warrior());
        player2.equip(new Longsword());
        player2.equip(new ChainMail());

        grid[13][3] = player1.getSprite();
        m_entities.put(player1, new int[]{13, 3});

        grid[13][5] = player2.getSprite();
        m_entities.put(player2, new int[]{13, 5});
    }

    protected void createDefaultMonsters() {
        String[][] grid = m_donjon.getDonjonGrid();

        Monster monster = new Monster(25, 14, 10, 5, "Goblin", 1, 1, 13, new Dice(1, 6));

        grid[2][10] = monster.getSprite();
        m_entities.put(monster, new int[]{2, 10});
    }
}
