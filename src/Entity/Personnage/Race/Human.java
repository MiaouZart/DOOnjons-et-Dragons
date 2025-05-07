package Entity.Personnage.Race;

public class Human extends Race{
    private static int defaultSpeed=2;
    private static int defaultDex=2;
    private static int defaultStrength=2;
    private static int defaultInitiative=2;
    public Human(){
        super(defaultSpeed,defaultStrength,defaultDex,defaultInitiative);
    }
}
