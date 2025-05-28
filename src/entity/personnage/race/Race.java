package entity.personnage.race;
import java.util.Objects;

public abstract class Race {
    static int m_baseSpeed;
    static int m_baseDex;
    static int m_baseStrength;
    static int m_baseInitiative;
    static String m_name;

    /**
     * Constructeur de la Race
     * @param name Nom de la Race
     * @param speed Vitesse de déplacement
     * @param strength Points de force
     * @param dex Points de dextérité
     * @param initiative Points d'initiative
     */
    public Race(String name, int speed, int strength,int dex, int initiative){
        m_name = name;
        m_baseDex = dex;
        m_baseSpeed = speed;
        m_baseInitiative = initiative;
        m_baseStrength =strength;
    }

    /**
     * Getter de la vitesse
     * @return Vitesse de base de la race
     */
    public int getSpeed(){
        return m_baseSpeed;
    }

    /**
     * Getter de la dextérité
     * @return Dextérité de base de la race
     */
    public int getDex(){
        return m_baseDex;
    }

    /**
     * Getter de la force
     * @return Force de base de la race
     */
    public int getStrength(){
        return m_baseStrength;
    }

    /**
     * Getter de l'initiative
     * @return Initiative de base de la race
     */
    public int getInitiative(){
        return m_baseInitiative;
    }

    @Override
    public String toString() { return m_name; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return Objects.equals(this.toString(), race.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(m_name);
    }
}
