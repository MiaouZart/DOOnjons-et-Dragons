package donjon.defaultDonjon;

import donjon.Donjon;
import entity.Entity;

import java.util.HashMap;

public abstract class Default {
    protected final HashMap<Entity, int[]> m_entities;
    protected final Donjon m_donjon;

    protected Default(HashMap<Entity, int[]> entities, int size) {
        m_entities = entities;
        m_donjon = new Donjon(size, m_entities);
        setupDefaultDonjon();
    }

    /**
     * Initialise des murs par défaut.
     * @param donjon Donjon à utiliser.
     * @return Nouvelle map avec les murs par défaut.
     */
    protected static String[][] initialiseWalls(Donjon donjon) {
        String[][] grid = donjon.getDonjonGrid();
        int size = donjon.getDonjonSize();

        for (int i = 0; i < size; i++) {
            grid[0][i] = " # ";  // Mur nord
            grid[size - 1][i] = " # ";  // Mur sud
            grid[i][0] = " # ";  // Mur ouest
            grid[i][size - 1] = " # ";  // Mur est
        }

        return grid;
    }


    /**
     * Initialise le donjon.
     */
    private void setupDefaultDonjon() {
        initializeDefaultGrid();
        createDefaultPlayers();
        createDefaultMonsters();
    }

    /**
     * Initialise la grille par défaut.
     */
    abstract void initializeDefaultGrid();

    /**
     * Crée les joueurs par défaut.
     */
    abstract void createDefaultPlayers();

    /**
     * Crée les monstres par défaut.
     */
    abstract void createDefaultMonsters();

    /**
     * Getter du donjon
     * @return Le donjon sympa
     */
    public Donjon getDonjon() {
        return m_donjon;
    }
}
