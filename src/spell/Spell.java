package spell;

import entity.Entity;

import java.util.HashMap;
import java.util.Objects;

public abstract class Spell {
    private final String m_name;

    /**
     * Constructeur du sort.
     * @param name Nom du sort.
     */
    public Spell(String name) {
        m_name = name;
    }

    @Override
    public String toString() {
        return m_name;
    }

    /**
     * Exécution du sort.
     * @param entities Map des entités.
     * @param grid Grid en chaîne de caractères.
     */
    public abstract void spell(HashMap<Entity, int[]> entities, String[][] grid);

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Spell spell = (Spell) o;
        return Objects.equals(m_name, spell.m_name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(m_name);
    }
}
