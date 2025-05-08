package Dice;
import java.util.Objects;
import java.util.Random;
import java.util.ArrayList;

public class Dice {
    private final int m_value;
    private final int nb_Roll;

    public Dice(int roll,int value){
        nb_Roll =roll;
        m_value = value;
    }


    public int[] roll(){

        int[] rolls = new int[nb_Roll];
        Random rand = new Random();
        for(int i = 0; i< nb_Roll; i++){
            rolls[i] = rand.nextInt(m_value)+1;
        }
        return rolls;
    }


    @Override
    public String toString() {
        return nb_Roll +"d"+m_value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dice dice = (Dice) o;
        return m_value == dice.m_value && nb_Roll == dice.nb_Roll;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_value, nb_Roll);
    }
}
