package Entity.Personnage.Race;

public class Dwarf extends Race{
    private static int defaultSpeed=0;
    private static int defaultDex=0;
    private static int defaultStrength=6;
    private static int defaultInitiative=0;
    public Dwarf(){
       super(defaultSpeed,defaultStrength,defaultDex,defaultInitiative);
    }

}
