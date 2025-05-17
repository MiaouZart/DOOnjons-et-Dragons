package entity.monster;

import dice.Dice;
import entity.Entity;
import entity.EnumEntity;
import entity.personnage.Personnage;

public class Monster extends Entity {
    private final String m_specie;
    private final int m_id;
    private final int m_atkRange;
    private final Dice m_atkDamage;
    private final int m_armor;

    public Monster(int HP, int Strength, int Dex, int Speed, String species, int id, int atkRange,int armor, Dice dice){
        super(HP, Strength, Dex, Speed);
        m_specie = species;
        m_id =id;
        m_atkRange =atkRange;
        m_atkDamage = dice;
        m_armor = armor;
        m_type = EnumEntity.PERSONNAGE;
    }

    @Override
    public String toString() {
        return this.m_specie+"-"+this.m_id;
    }

    public int getRangePoint(){
        return m_atkRange;
    }

    @Override
    public int getArmorPoint() {
        return m_armor;
    }


}
