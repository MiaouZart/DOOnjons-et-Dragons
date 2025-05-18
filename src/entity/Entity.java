package entity;

import dice.Dice;
import entity.monster.Monster;
import entity.personnage.Personnage;
import equipment.weapon.EnumWeaponType;
import equipment.weapon.Weapon;

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

    public  abstract int attack();

    public boolean getAttacked(Entity entity){
        Dice dice = new Dice(1,20);
        int roll = sumUp(dice.roll());
        System.out.println("Faite votre lancer : (appuyer sur n'imporque quelle touche)");
        System.out.print("Vous avez fait : " + roll);
        int resultat = roll;
        if(entity.getRangePoint()>1){
            resultat+=entity.getDex();
            System.out.println(" + "+entity.getDex()+"(Dex) ");
        }else {
            resultat+=entity.getStrength();
            System.out.println(" + "+entity.getStrength()+"(Force) ");
        }

        System.out.println(" = "+resultat);

        int armor = this.getArmorPoint();

        if(armor<resultat){
            int damage = 0;
            System.out.println("Votre attaque Transperce l'armure de "+this+" ("+armor+")");
            System.out.println("Lancer votre dée"+0+" d'attaque (appuyer sur n'imporque quelle touche)");
            damage =  entity.attack();
            this.takeDamage(damage);
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
