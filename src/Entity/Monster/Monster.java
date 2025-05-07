package Entity.Monster;

import Entity.Entity;

public class Monster extends Entity {
    private String m_specie;
    private int m_id;
    private int atckRange;
    public Monster(int X,int Y,int HP,int Strength,int Dex,int Speed,int Initiative){
        super(X,Y,HP,Strength,Dex,Speed,Initiative);
    }
    public Monster(int HP,int Strength,int Dex,int Speed,int Initiative){
        super(HP,Strength,Dex,Speed,Initiative);
    }

}
