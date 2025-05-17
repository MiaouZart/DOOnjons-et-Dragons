package entity.personnage.race.types;

import entity.personnage.race.Race;

public class Dwarf extends Race {
    static final int m_defaultSpeed =0;
    static final int m_defaultDex =0;
    static final int m_defaultStrength =6;
    static final int m_defaultInitiative =0;
    public Dwarf(){
       super("Dwarf", m_defaultSpeed, m_defaultStrength, m_defaultDex, m_defaultInitiative);
    }

}
