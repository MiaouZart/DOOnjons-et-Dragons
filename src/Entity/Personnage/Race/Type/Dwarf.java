package Entity.Personnage.Race.Type;

import Entity.Personnage.Race.Race;

public class Dwarf extends Race {
    static int defaultSpeed=0;
    static int defaultDex=0;
    static int defaultStrength=6;
    static int defaultInitiative=0;
    public Dwarf(){
       super(defaultSpeed,defaultStrength,defaultDex,defaultInitiative);
    }

}
