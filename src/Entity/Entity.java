package Entity;

public abstract class Entity {
    private int m_posX;
    private int m_posY;
    private int m_hp;
    private int m_strength;
    private int m_dex;
    private int m_initiative;
    private int m_speed;


    public Entity(int X,int Y,int HP,int Strength,int Dex,int Speed,int Initiative){
        m_posX = X;
        m_posY = Y;
        m_hp = HP;
        m_speed= Speed;
        m_dex = Dex;
        m_initiative = Initiative;
        m_strength = Strength;
    }

    public Entity(int HP,int Strength,int Dex,int Speed,int Initiative){
        this(-1,-1,HP,Strength,Dex,Speed,Initiative);//Si création sans position alors on place l'entité en dehors de la girlle
    }

    public void setX_Y(int X,int Y){
        m_posX = X;
        m_posY = Y;
    }




    public int getPosX(){
        return m_posX;
    }
    public int getPosY(){
        return m_posY;
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




}
