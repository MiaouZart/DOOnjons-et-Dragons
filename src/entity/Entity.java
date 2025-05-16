package entity;

public abstract class Entity {
    private final int m_hp;
    private final int m_strength;
    private final int m_dex;
    private final int m_speed;
    private int m_initiative;


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

}
