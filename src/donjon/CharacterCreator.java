package donjon;

import entity.personnage.charclass.CharClass;
import entity.personnage.charclass.types.*;
import entity.personnage.Personnage;
import entity.personnage.race.types.*;
import entity.personnage.race.Race;

import java.util.ArrayList;
import java.util.Scanner;

public class CharacterCreator {

    public CharacterCreator() {
    }

    /**
     * Crée un personnage en demandant avec des utilisateurs
     * @return Nouveau personnage crée.
     */
    protected static Personnage create() {
        Scanner scanner = new Scanner(System.in);
        return new Personnage(promptName(scanner), promptRace(), promptClass());
    }

    /**
     * Demande un nom.
     * @param scanner Scanner à utiliser pour demander le nom.
     * @return Chaîne de caractère du nom demandé.
     */
    private static String promptName(Scanner scanner) {
        String nom = "";

        while (nom.isEmpty()) {
            System.out.print("Veuillez saisir un nom : ");
            nom = scanner.nextLine();
        }

        return nom;
    }

    /**
     * Demande une race
     * @return Chaîne de caractère de la race demandé.
     */
    private static Race promptRace() {
        ArrayList<String> raceChoices = new ArrayList<>();
        raceChoices.add("Nain");
        raceChoices.add("Elfe");
        raceChoices.add("Halfelin");
        raceChoices.add("Humain");

        int raceType = Display.promptChoice(raceChoices, false); // Pas de "fin", choix obligatoire

        return switch (raceType) {
            case 0 -> new Dwarf();
            case 1 -> new Elf();
            case 2 -> new Halfelin();
            case 3 -> new Human();
            default -> throw new IllegalStateException("Valeur inattendue : " + raceType);
        };
    }

    /**
     * Demande une classe
     * @return Chaîne de caractère de la classe demandé.
     */
    private static CharClass promptClass() {
        ArrayList<String> classChoices = new ArrayList<>();
        classChoices.add("Clerc");
        classChoices.add("Guerrier");
        classChoices.add("Magicien");
        classChoices.add("Roublard");

        int classType = Display.promptChoice(classChoices, false); // Pas de "fin", choix obligatoire

        return switch (classType) {
            case 0 -> new Clerc();
            case 1 -> new Warrior();
            case 2 -> new Wizard();
            case 3 -> new Rogue();
            default -> throw new IllegalStateException("Valeur inattendue : " + classType);
        };
    }
}
