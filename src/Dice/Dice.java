package Dice;

public class Dice {
    private final int m_value;
    private final int m_nbThrow;

    public Dice(int trhow,int value){
        m_nbThrow =trhow;
        m_value = value;
    }



    @Override
    public String toString() {
        return m_nbThrow+"d"+m_value;
    }
}
