package entity.personnage.race.types;

import entity.personnage.race.Race;

public class Human extends Race {
    static final int m_defaultSpeed =2;
    static final int m_defaultDex =2;
    static final int m_defaultStrength =2;
    static final int m_defaultInitiative =2;
    public Human(){
        super("Human", m_defaultSpeed, m_defaultStrength, m_defaultDex, m_defaultInitiative);
    }
}
