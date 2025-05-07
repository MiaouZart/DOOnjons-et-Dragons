package Entity.Personnage.Race;

public abstract class Race {
    private static int m_baseSpeed;
    private static int m_baseDex;
    private static int m_baseStrength;
    private static int m_baseInitiative;

    /**
     * Create a new Race with default param
     * @param speed
     * @param strength
     * @param dex
     * @param initiative
     */
    public Race(int speed, int strength,int dex, int initiative){
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
