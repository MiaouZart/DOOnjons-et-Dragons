package Race;

public abstract class Race {
    static int m_baseSpeed;
    static int m_baseDex;
    static int m_baseStrength;
    static int m_baseInitiative;

    /**
     * Create a new Race with default param
     * @param speed
     * @param strength
     * @param dex
     * @param initiative
     */
    Race(int speed, int strength,int dex, int initiative){
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


}
