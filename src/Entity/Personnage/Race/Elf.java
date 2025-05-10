package Entity.Personnage.Race;

public class Elf extends Race{
    static int defaultSpeed=0;
    static int defaultDex=6;
    static int defaultStrength=0;
    static int defaultInitiative=0;
    public Elf(){
        super("Elf",defaultSpeed,defaultStrength,defaultDex,defaultInitiative);
    }
}
