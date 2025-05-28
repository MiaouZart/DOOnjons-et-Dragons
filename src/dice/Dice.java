package dice;
import java.util.Objects;
import java.util.Random;

public class Dice {
    private final int m_value;
    private final int m_nbRoll;

    /**
     * Constructeur pour les dés.
     * @param roll Nombre de lancés pour le dé.
     * @param value Valeur maximale du dé.
     */
    public Dice(int roll,int value){
        m_nbRoll =roll;
        m_value = value;
    }

    /**
     * Lance les dés.
     * @return Liste des résultats pour tous les dés (voir méthode sumUp).
     */
    public int[] roll(){

        int[] rolls = new int[m_nbRoll];
        Random rand = new Random();
        for(int i = 0; i< m_nbRoll; i++){
            rolls[i] = rand.nextInt(m_value)+1;
        }
        return rolls;
    }

    /**
     * Renvois la somme des dés (int) fournis.
     * @param dices Dés à additionner.
     * @return Addition des dés.
     */
    static public int sumUp(int[] dices){
        int sum = 0;
        for(int i : dices){
            sum+=i;
        }
        return  sum;
    }

    @Override
    public String toString() {
        return m_nbRoll +"d"+m_value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dice dice = (Dice) o;
        return m_value == dice.m_value && m_nbRoll == dice.m_nbRoll;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_value, m_nbRoll);
    }
}
