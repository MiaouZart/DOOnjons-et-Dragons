package entity.monster;

import dice.Dice;
import entity.Entity;
import entity.EnumEntity;

import static dice.Dice.sumUp;

public class Monster extends Entity {
    private final String m_specie;
    private final int m_id;
    private final int m_atkRange;
    private final Dice m_atkDamage;
    private final int m_armor;

    /**
     * Constructeur du monstre
     * @param HP Nombre PV du monstre
     * @param Strength Points de Force du monstre
     * @param Dex Points de Dextérité du monstre
     * @param Speed Points de vitesse du monstre
     * @param species Espèce du monstre
     * @param id Id du monstre de son espèce
     * @param atkRange Distance d'attaque du monstre
     * @param armor Points d'armure du monstre
     * @param dice Dé de dommage de monstre.
     */
    public Monster(int HP, int Strength, int Dex, int Speed, String species, int id, int atkRange,int armor, Dice dice){
        super(HP, Strength, Dex, Speed);
        m_specie = species;
        m_id =id;
        m_atkRange =atkRange;
        m_atkDamage = dice;
        m_armor = armor;
        m_type = EnumEntity.MONSTER;
    }

    @Override
    public String toString() {
        return  "\033[31;1m" + this.m_specie+"-"+this.m_id + "\033[0m";
    }

    /**
     * Getter pour la distance d'attaque
     * @return Distance d'attaque
     */
    public int getRangePoint(){
        return m_atkRange;
    }

    @Override
    public int getArmorPoint() {
        return m_armor;
    }

    @Override
    public int damage() {
        return sumUp(m_atkDamage.roll());
    }

    @Override
    public Dice getDice() {
        return  m_atkDamage;
    }

    @Override
    public String getSprite() {
        return "\033[31m"+this.m_specie.trim().substring(0,3).toUpperCase() + "\033[0m";
    }


}
