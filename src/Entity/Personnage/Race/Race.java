package Entity.Personnage.Race;
import java.util.Objects;

public abstract class Race {
    static int m_baseSpeed;
    static int m_baseDex;
    static int m_baseStrength;
    static int m_baseInitiative;
    static String m_name;

    /**
     * Create a new Race with default param
     * @param name
     * @param speed
     * @param strength
     * @param dex
     * @param initiative
     */
    public Race(String name, int speed, int strength,int dex, int initiative){
        m_name = name;
        m_baseDex = dex;
        m_baseSpeed = speed;
        m_baseInitiative = initiative;
        m_baseStrength =strength;
    }

    public int getSpeed(){
        return m_baseSpeed;
    }
    public int getDex(){
        return m_baseDex;
    }
    public int getStrenght(){
        return m_baseStrength;
    }
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
