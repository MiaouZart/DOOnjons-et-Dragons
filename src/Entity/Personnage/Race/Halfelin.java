package Entity.Personnage.Race;

public class Halfelin extends Race{
    private static int defaultSpeed=2;
    private static int defaultDex=4;
    private static int defaultStrength=0;
    private static int defaultInitiative=0;
    public Halfelin(){
        super(defaultSpeed,defaultStrength,defaultDex,defaultInitiative);
    }
}
