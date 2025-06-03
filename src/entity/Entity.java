package entity;

import dice.Dice;
import printer.StandardOut;

import java.util.Scanner;

import static dice.Dice.sumUp;

public abstract class Entity {
    private int m_hp;
    private final int m_hpMax;
    private final int m_strength;
    private final int m_dex;
    private final int m_speed;
    private int m_initiative;
    private boolean m_dead=false;
    protected EnumEntity m_type;

    /**
     * Constructeur de l'entité.
     * @param hp Nombre de PV de l'entité.
     * @param strength Points de force de l'entité.
     * @param dex Dextérité de l'entité.
     * @param speed Vitesse de l'entité.
     * @param initiative Initiative de l'entité.
     */
    public Entity(int hp, int strength, int dex, int speed, int initiative){
        this(hp, strength, dex, speed);
        m_initiative = initiative;
    }

    /**
     * Constructeur de l'entité.
     * @param hp Nombre de PV de l'entité.
     * @param strength Points de force de l'entité.
     * @param dex Dextérité de l'entité.
     * @param speed Vitesse de l'entité.
     */
    public Entity(int hp, int strength, int dex, int speed){
        m_hp = hp;
        m_hpMax = hp;
        m_dex = dex;
        m_strength = strength;
        m_speed = speed;
    }

    /**
     * Getter pour les PV de l'entité
     * @return Nombre entier des PV actuel.
     */
    public int getHp(){
        return m_hp;
    }

    /**
     * Getter pour les points de Force de l'entité.
     * @return Nombre entier de la force actuel.
     */
    public int getStrength(){
        return m_strength;
    }

    /**
     * Getter pour l'initiative de l'entité.
     * @return Initiative de l'entité.
     */
    public int getInitiative(){
        return m_initiative;
    }

    /**
     * Getter pour la dextérité de l'entité.
     * @return Dextérité de l'entité.
     */
    public int getDex(){
        return m_dex;
    }

    /**
     * Getter pour la vitesse de l'entité.
     * @return Vitesse de l'entité.
     */
    public int getSpeed(){
        return m_speed;
    }

    /**
     * Setter pour l'initiative.
     * @param initiative Valeur à laquelle définir l'initiative.
     */
    public void setInitiative(int initiative){
        m_initiative = initiative;
    }

    /**
     * Ajout additionner l'initiative actuelle.
     * @param initiative Valeur à additionner à l'initiative.
     */
    public void addInitiative(int initiative){
        m_initiative += initiative;
    }

    /**
     * Getter pour la portée actuelle de l'entité.
     * @return Range actuelle de l'entité.
     */
    public abstract int getRangePoint();

    /**
     * Getter pour les points d'armure actuelle de l'entité.
     * @return Points d'armure.
     */
    public abstract int getArmorPoint();

    /**
     * Getter pour si l'entité est décédé.
     * @return Booléen de si l'entité est morte.
     */
    public boolean getDead(){
        return m_dead;
    }

    /**
     * Renvois le type de l'entité.
     * @return Type de l'entité.
     */
    public EnumEntity getType(){
        return m_type;
    }

    /**
     * Getter pour les dommages de l'entité.
     * @return Dommage relatif à l'entité
     */
    public abstract int damage();
    public int attackBonus() {
        return 0;
    }

    /**
     * Action se faire attaquer par entité
     * @param entity Entité qui attaque cette entité.
     * @return Si l'attaque a été fructueuse ou non.
     */
    public boolean getAttacked(Entity entity){
        Dice dice = new Dice(1,20);
        int roll = sumUp(dice.roll());
        System.out.println("Faite votre lancer : (appuyer sur n'importe quelle touche)");
        System.out.print("Vous avez fait : " + roll);
        int resultat = roll;
        resultat += entity.attackBonus();
        if(entity.getRangePoint() > 1){
            resultat+=entity.getDex();
            System.out.println(" + "+entity.getDex()+"(Dex) ");
        }else {
            resultat+=entity.getStrength();
            System.out.print(" + "+entity.getStrength()+"(Force) ");
        }

        System.out.println(" = "+resultat);
        int armor;
        try {
            armor = this.getArmorPoint();
        }catch (Exception _){
            return false;
        }

        if(armor < resultat){
            int damage;
            System.out.println("Votre attaque Transperce l'armure de "+this+" ("+armor+")");
            System.out.println("Lancer votre dé "+entity.getDice()+" d'attaque (appuyer sur n'importe quelle touche)");
            Scanner sca = new Scanner(System.in);
            sca.nextLine();
            damage = entity.damage();
            this.takeDamage(damage);
            return true;
        }
        return false;


    }

    /**
     * Prend des dégâts.<br>
     * Gère la mort de l'entité.
     * @param damage Dommage reçu par cette entité.
     */
    public void takeDamage(int damage) {
        this.m_hp-=damage;
        if(m_hp<=0){
            this.m_dead =true;
        }
    }

    /**
     * Faire dire quelque-chose à l'entité.
     */
    public void say(StandardOut out){
        out.outLn("oui");
    }

    /**
     * Getter pour le dé de dégât.
     * @return Dé de dégât relatif à l'entité.
     */
    public abstract Dice getDice();

    /**
     * Sprite à utiliser pour représenter l'entité sur la carte.
     * @return Sprite de l'entité.
     */
    public abstract String getSprite();

    /**
     * Getter pour les PV maximum.
     * @return PV maximum.
     */
    public int getMaxHp() {
        return m_hpMax;
    }

    /**
     * Récupère des points de vie, jusqu'à atteindre les points de vie maximum de l'entité.
     * @param heal Points de vie à ajouter.
     */
    public void takeHeal(int heal) {
        m_hp = Math.min(m_hp+heal, m_hpMax);
    }
}
