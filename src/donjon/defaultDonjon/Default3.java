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

public class Default3 extends Default {

    /**
     * Constructeur pour générer un donjon par défaut.
     * @param entities Liste des entités à déjà inclure.
     */
    public Default3(HashMap<Entity, int[]> entities) {
        super(entities, 25);
    }

    protected void initializeDefaultGrid() {
        String[][] grid = initialiseWalls(m_donjon);

        // Quelques murs internes
        for (int i = 8; i <= 16; i++) {
            grid[12][i] = " # ";
            grid[i][12] = " # ";
        }

        // Entrées / sorties
        grid[2][2] = " E ";
        grid[22][22] = " E ";
        grid[2][22] = " E ";
    }

    protected void createDefaultPlayers() {
        String[][] grid = m_donjon.getDonjonGrid();

        Personnage player1 = new Personnage("Thorin", new Dwarf(), new Warrior());
        player1.equip(new Longsword());
        player1.equip(new ChainMail());

        Personnage player2 = new Personnage("Elrond", new Elf(), new Wizard());

        Personnage player3 = new Personnage("Tauriel", new Elf(), new Rogue());
        player3.equip(new Longsword());

        grid[23][5] = player1.getSprite();
        m_entities.put(player1, new int[]{23, 5});

        grid[23][8] = player2.getSprite();
        m_entities.put(player2, new int[]{23, 8});

        grid[23][11] = player3.getSprite();
        m_entities.put(player3, new int[]{23, 11});
    }

    protected void createDefaultMonsters() {
        String[][] grid = m_donjon.getDonjonGrid();

        Monster goblin = new Monster(20, 12, 10, 4, "Goblin", 1, 1, 12, new Dice(1, 6));
        Monster ogre = new Monster(50, 18, 8, 3, "Ogre", 2, 1, 14, new Dice(2, 6));
        Monster wraith = new Monster(35, 16, 14, 5, "Wraith", 3, 2, 15, new Dice(2, 4));

        grid[3][3] = goblin.getSprite();
        m_entities.put(goblin, new int[]{3, 3});

        grid[3][21] = ogre.getSprite();
        m_entities.put(ogre, new int[]{3, 21});

        grid[10][10] = wraith.getSprite();
        m_entities.put(wraith, new int[]{10, 10});
    }
}
