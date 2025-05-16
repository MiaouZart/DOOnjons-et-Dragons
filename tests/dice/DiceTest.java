package dice;

import org.junit.jupiter.api.Test;

class DiceTest {
    public static double moyenneTableau(int[] tableau) {
        int somme = 0;
        for (int nombre : tableau) {
            somme += nombre;
        }
        return (double) somme / tableau.length;
    }
    @Test
    void roll() {
        Dice dice = new Dice(100000,11);
        System.out.println(moyenneTableau(dice.roll()));
    }
}