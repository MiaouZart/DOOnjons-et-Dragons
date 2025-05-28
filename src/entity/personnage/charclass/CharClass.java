package entity.personnage.charclass;

import equipment.Equipment;
import spell.Spell;

import java.util.Objects;

public abstract class CharClass {
    private final String m_name;
    private final int m_baseHealth;
    private final Equipment[] m_baseStuff;
    private final Spell[] m_spells;

    /**
     * Constructeur classe de personnage/
     * @param name Nom de la classe
     * @param baseHealth Vie de base de la classe
     * @param baseStuff Équipement de base de la classe
     */
    public CharClass(String name, int baseHealth, Equipment[] baseStuff) {
        this(name, baseHealth, baseStuff, new Spell[0]);
    }

    /**
     * Constructeur classe de personnage/
     * @param name Nom de la classe
     * @param baseHealth Vie de base de la classe
     * @param baseStuff Équipement de base de la classe
     * @param spells Tableau des sorts
     */
    public CharClass(String name, int baseHealth, Equipment[] baseStuff, Spell[] spells) {
        m_baseHealth = baseHealth;
        m_baseStuff = baseStuff;
        m_name = name;
        m_spells = spells;
    }

    /**
     * Getter vie de base de la classe
     * @return Vie de base
     */
    public int getBaseHealth() {
        return m_baseHealth;
    }

    /**
     * Getter Équipement de base de la classe
     * @return Équipement de base
     */
    public Equipment[] getBaseStuff() {
        return m_baseStuff;
    }

    /**
     * Getter sorts de base de la classe
     * @return Sorts de base
     */
    public Spell[] getSpells() {
        return m_spells;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CharClass charClass = (CharClass) o;
        return Objects.equals(m_name, charClass.m_name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(m_name);
    }

    @Override
    public String toString() {
        return m_name ;
    }
}
