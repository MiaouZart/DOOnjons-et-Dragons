package entity.personnage.race.types;

import entity.personnage.race.Race;

public class Halfelin extends Race {
    static final int m_defaultSpeed =2;
    static final int m_defaultDex =4;
    static final int m_defaultStrength =0;
    static final int m_defaultInitiative =0;
    public Halfelin(){
        super("Halfelin", m_defaultSpeed, m_defaultStrength, m_defaultDex, m_defaultInitiative);
    }
}
