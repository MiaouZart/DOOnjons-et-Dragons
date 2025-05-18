package donjon.defaultDonjon;

import dice.Dice;
import donjon.Donjon;
import entity.Entity;
import entity.monster.Monster;
import entity.personnage.Personnage;
import entity.personnage.charclass.types.*;
import entity.personnage.race.types.*;
import equipment.armor.types.ChainMail;
import equipment.weapon.types.Longsword;

import java.util.HashMap;

public class Default1 {
    private Donjon m_donjon;
    private HashMap<Entity, int[]> m_entities;
    public Default1() {
        int size = 20;
        this.m_entities = new HashMap<>();

        this.m_donjon = new Donjon(size, m_entities);

        setupDefaultDonjon();
    }
    public Default1(HashMap<Entity, int[]> enti){
        m_entities = enti;
        int size = 20;
        this.m_donjon = new Donjon(size, m_entities);

        setupDefaultDonjon();
    }
    private void setupDefaultDonjon() {
        initializeDefaultGrid();

        createDefaultPlayers();

        createDefaultMonsters();
    }

    private void initializeDefaultGrid() {
        String[][] grid = m_donjon.getDonjonGrid();
        int size = m_donjon.getDonjonSize();

        for (int i = 0; i < size; i++) {
            grid[0][i] = " # ";  // Mur nord
            grid[size-1][i] = " # ";  // Mur sud
            grid[i][0] = " # ";  // Mur ouest
            grid[i][size-1] = " # ";  // Mur est
        }

        for (int i = 5; i < 15; i++) {
            grid[10][i] = " # ";  // Mur horizontal central
            grid[i][10] = " # ";  // Mur vertical central
        }

        grid[5][5] = " # ";
        grid[5][15] = " # ";
        grid[15][5] = " # ";
        grid[15][15] = " # ";

        grid[3][3] = " E ";
        grid[3][17] = " E ";
        grid[17][3] = " E ";
        grid[17][17] = " E ";
    }

    private void createDefaultPlayers() {
        String[][] grid = m_donjon.getDonjonGrid();

        Personnage player1 = new Personnage("Gimli", new Dwarf(), new Warrior());
        player1.equip(new Longsword());
        player1.equip(new ChainMail());

        Personnage player2 = new Personnage("Legolas", new Elf(), new Rogue());
        player2.equip(new Longsword());

        Personnage player3 = new Personnage("Gandalf", new Human(), new Wizard());

        Personnage player4 = new Personnage("Aragorn", new Human(), new Warrior());
        player4.equip(new Longsword());
        player4.equip(new ChainMail());

        grid[18][5] = player1.getSprite();
        m_entities.put(player1, new int[]{18, 5});

        grid[18][8] = player2.getSprite();
        m_entities.put(player2, new int[]{18, 8});

        grid[18][11] = player3.getSprite();
        m_entities.put(player3, new int[]{18, 11});

        grid[18][14] = player4.getSprite();
        m_entities.put(player4, new int[]{18, 14});
    }

    private void createDefaultMonsters() {
        String[][] grid = m_donjon.getDonjonGrid();

        Monster monster1 = new Monster(30, 16, 12, 6, "Orc", 1, 1, 14, new Dice(1, 8));
        Monster monster2 = new Monster(45, 18, 8, 4, "Troll", 2, 1, 16, new Dice(2, 6));

        grid[2][5] = monster1.getSprite();
        m_entities.put(monster1, new int[]{2, 5});

        grid[2][15] = monster2.getSprite();
        m_entities.put(monster2, new int[]{2, 15});
    }

    public Donjon getDonjon() {
        return m_donjon;
    }
}