package Race;

public class Human extends Race{
    static int defaultSpeed=2;
    static int defaultDex=2;
    static int defaultStrength=2;
    static int defaultInitiative=2;
    Human(){
        super(defaultSpeed,defaultStrength,defaultDex,defaultInitiative);
    }
}
