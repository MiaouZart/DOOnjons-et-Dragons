package Donjon;

import Entity.Personnage.CharClass.CharClass;
import Entity.Personnage.CharClass.Types.*;
import Entity.Personnage.Personnage;
import Entity.Personnage.Race.Types.*;
import Entity.Personnage.Race.Race;
import java.util.Scanner;

import static Donjon.Display.promptInt;


public class CharacterCreator {

    public CharacterCreator(){

    }

    protected static Personnage create() {
        Scanner scanner = new Scanner(System.in);
        return new Personnage(promptName(scanner), promptRace(scanner), promptClass(scanner));
    }

    private static String promptName(Scanner scanner) {
        String nom = "";

        while (nom.isEmpty()) {
            System.out.print("Veuillez saisir un nom : ");
            nom = scanner.nextLine();
        }

        return nom;
    }

    private static Race promptRace(Scanner scanner) {
        int raceType = -1;
//        System.out.println("Veuillez saisir une race : ");
        while (!(0 <= raceType && raceType <= 3)) {
            raceType = promptInt(scanner,
                    "[0] Nain ; [1] Elfe ; [2] Halfelin ; [3] Humain (ex:2)");
        }
        return switch (raceType) {
            case 0 -> new Dwarf();
            case 1 -> new Elf();
            case 2 -> new Halfelin();
            case 3 -> new Human();
            default -> throw new IllegalStateException("Unexpected value: " + raceType);
        };
    }

    private static CharClass promptClass(Scanner scanner) {
        int classType = -1;
//        System.out.println("Veuillez saisir une classe : ");
        while (!(0 <= classType && classType <= 3)) {
            classType = promptInt(scanner,
                    "[0] Clerc ; [1] Guerrier ; [2] Magicien ; [3] Roublard (ex:2)");
        }
        return switch (classType) {
            case 0 -> new Clerc();
            case 1 -> new Rogue();
            case 2 -> new Warrior();
            case 3 -> new Wizard();
            default -> throw new IllegalStateException("Unexpected value: " + classType);
        };
    }

}
