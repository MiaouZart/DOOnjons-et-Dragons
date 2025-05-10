package Entity.Monster;

import Dice.Dice;
import Entity.Entity;

public class Monster extends Entity {
    private String m_specie;
    private int m_id;
    private int m_atckRange;
    private Dice m_atckDamage;
    public Monster(int X,int Y,int HP,int Strength,int Dex,int Speed,String species,int id,int atckRange,Dice dice){
        super(X,Y,HP,Strength,Dex,Speed);
        m_specie = species;
        m_id =id;
        m_atckRange =atckRange;
        m_atckDamage = dice;

    }
    public Monster(int HP, int Strength, int Dex, int Speed, String specie, int id, int atckRange, Dice dice){
        this(-1,-1,HP,Strength,Dex,Speed,specie,id,atckRange,dice);
    }

}
