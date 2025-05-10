package Entity.Personnage.Race;

public class Human extends Race{
    static int defaultSpeed=2;
    static int defaultDex=2;
    static int defaultStrength=2;
    static int defaultInitiative=2;
    public Human(){
        super("Human",defaultSpeed,defaultStrength,defaultDex,defaultInitiative);
    }
}
