package Entity.Personnage.Race;

public class Elf extends Race{
    private static int defaultSpeed=0;
    private static int defaultDex=6;
    private static int defaultStrength=0;
    private static int defaultInitiative=0;
    public Elf(){
        super(defaultSpeed,defaultStrength,defaultDex,defaultInitiative);
    }
}
