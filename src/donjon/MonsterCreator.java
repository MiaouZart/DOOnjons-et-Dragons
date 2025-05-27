package donjon;

import dice.Dice;
import entity.Entity;
import entity.monster.Monster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static donjon.Display.promptChoice;
import static donjon.Display.promptInt;
import static donjon.Donjon.*;

public class MonsterCreator {
    // Liste des races de monstres prédéfinies
    private static final ArrayList<String> PREDEFINED_SPECIES = new ArrayList<>(List.of(
            "Orc", "Gobelin", "Troll", "Dragonnet",
            "Loup-garou", "Squelette", "Zombie", "Araignée géante"
    ));

    protected static int bulkCreate(Display display, String[][] grid, HashMap<Entity, int[]> entities) {
        Scanner scanner = new Scanner(System.in);
        int monsterCount = 0;

        while (true) {
            display.displayTitle("Maître du jeu : Créez vos Monstres");
            display.refreshDisplay();

            ArrayList<String> options = new ArrayList<>();
            options.add("Nouveau monstre");

            int choice = promptChoice(options, true);
            if (choice == -1 || choice == 1) break;

            // Sélection de la race
            ArrayList<String> speciesOptions = new ArrayList<>(PREDEFINED_SPECIES);
            speciesOptions.add("Autre race...");

            int speciesChoice = promptChoice(speciesOptions, true);
            if (speciesChoice == -1) continue;

            String specie;
            if (speciesChoice == speciesOptions.size() - 1) {
                System.out.print("Entrez le nom de la race du monstre : ");
                specie = scanner.nextLine().trim();
            } else {
                specie = PREDEFINED_SPECIES.get(speciesChoice);
            }

            Monster monster = createMonster(scanner, specie);
            int[] pos = promptPosition(scanner, grid);
            grid[pos[0]][pos[1]] = " M ";
            entities.put(monster, pos);
            monsterCount++;

            System.out.println("Monstre créé : " + monster);
        }
        return monsterCount;
    }

    protected static Monster createMonster(Scanner scanner, String specie) {
        ArrayList<String> statNames = new ArrayList<>(List.of(
                "Vie", "Dextérité", "Vitesse", "Force",
                "ID", "Portée d'attaque", "Points d'armure"
        ));

        ArrayList<Integer> stats = new ArrayList<>();

        for (String statName : statNames) {
            stats.add(promptInt(scanner, statName + " :"));
        }

        System.out.println("\nConfiguration des dés d'attaque :");
        ArrayList<String> diceOptions = new ArrayList<>(List.of(
                "1d4", "1d6", "1d8", "1d10",
                "2d6", "3d4", "1d12", "2d8"
        ));

        int diceChoice = promptChoice(diceOptions, false);
        String[] diceParts = diceOptions.get(diceChoice).split("d");
        int nbDice = Integer.parseInt(diceParts[0]);
        int faceDice = Integer.parseInt(diceParts[1]);

        return new Monster(
                stats.get(0),  // hp
                stats.get(3),  // strength
                stats.get(1),  // dex
                stats.get(2),  // speed
                specie,
                stats.get(4),  // id
                stats.get(5),  // atkRange
                stats.get(6),  // armor
                new Dice(nbDice, faceDice)
        );
    }

    protected static int[] promptPosition(Scanner scanner, String[][] grid) {
        while (true) {
            System.out.print("Entrez la position du monstre (ex: A5) : ");
            String position = scanner.nextLine().trim().toUpperCase();
            int[] pos = retrieveGridPosition(position);

            if (checkEmptyCase(pos[0], pos[1], grid, grid[0].length)) {
                return pos;
            }
            System.out.println("Position invalide ou occupée. Veuillez réessayer.");
        }
    }
}