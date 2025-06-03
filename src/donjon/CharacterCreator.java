package donjon;

import entity.personnage.charclass.CharClass;
import entity.personnage.charclass.types.*;
import entity.personnage.Personnage;
import entity.personnage.race.types.*;
import entity.personnage.race.Race;
import printer.StandardOut;

import java.util.ArrayList;

public class CharacterCreator {
    private final StandardOut m_output;

    public CharacterCreator(StandardOut output) {
        this.m_output = output;
    }

    /**
     * Crée un personnage en demandant avec des utilisateurs
     * @return Nouveau personnage crée.
     */
    protected static Personnage create(StandardOut output) {
        return new Personnage(
                promptName(output),
                promptRace(output),
                promptClass(output)
        );
    }

    /**
     * Demande un nom.
     * @param output Système de sortie standard à utiliser
     * @return Chaîne de caractère du nom demandé.
     */
    private static String promptName(StandardOut output) {
        String nom = "";

        while (nom.isEmpty()) {
            output.out("Veuillez saisir un nom : ");
            nom = output.in();
        }

        return nom;
    }

    /**
     * Demande une race
     * @param output Système de sortie standard à utiliser
     * @return Race sélectionnée
     */
    private static Race promptRace(StandardOut output) {
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
     * @param output Système de sortie standard à utiliser
     * @return Classe sélectionnée
     */
    private static CharClass promptClass(StandardOut output) {
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