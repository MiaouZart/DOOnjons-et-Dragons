package entity;

import dice.Dice;
import entity.personnage.Personnage;
import equipment.weapon.EnumWeaponType;

import static dice.Dice.sumUp;

public abstract class Entity {
    private int m_hp;
    private final int m_strength;
    private final int m_dex;
    private final int m_speed;
    private int m_initiative;
    private boolean m_dead=false;

    protected EnumEntity m_type;


    public Entity(int HP, int Strength, int Dex, int Speed, int Initiative){
        m_hp = HP;
        m_speed= Speed;
        m_dex = Dex;
        m_strength = Strength;
        m_initiative = Initiative;
    }
    public Entity(int HP, int Strength, int Dex, int Speed){
        m_hp = HP;
        m_speed = Speed;
        m_dex = Dex;
        m_strength = Strength;
    }


    public int getHp(){
        return m_hp;
    }
    public int getStrength(){
        return m_strength;
    }
    public int getInitiative(){
        return m_initiative;
    }
    public int getDex(){
        return m_dex;
    }
    public int getSpeed(){
        return m_speed;
    }
    public void setInitiative(int initiative){
        m_initiative = initiative;
    }
    public void addInitiative(int initiative){
        m_initiative += initiative;
    }


    public abstract int getRangePoint();
    public abstract int getArmorPoint();

    public boolean getDead(){return m_dead;}


    public EnumEntity getType(){
        return m_type;
    }

    public boolean getAttacked(Entity entity){
        Dice dice = new Dice(1,20);
        int resultat = sumUp(dice.roll());
        if(entity.getRangePoint()>1){
            resultat+=entity.getDex();
        }else {
            resultat+=entity.getStrength();
        }

        int armor = this.getArmorPoint();

        if(armor<resultat){
            this.takeDamage(resultat);
            return true;
        }
        return false;


    }
    private void takeDamage(int damage) {
        this.m_hp-=damage;
        if(m_hp<=0){
            this.m_dead =true;
        }
    }

}
