package donjon;

import dice.Dice;
import entity.Entity;
import entity.monster.Monster;
import printer.StandardOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static donjon.Display.*;

public class MonsterCreator {

    // Liste des races de monstres prédéfinies
    private static final ArrayList<String> PREDEFINED_SPECIES = new ArrayList<>(List.of(
            "Orc", "Gobelin", "Troll", "Cobold",
            "Loup-garou", "Squelette", "Zombie", "Araignée géante"
    ));

    /**
     * Crée des nouveaux monstres tant que le MJ le veut.
     * @param display Display du Donjon à utiliser.
     * @param grid Grille à utiliser pour voir si l'espace est disponible.
     * @param entities Map des Entités - Position où placer les nouveaux monstres.
     * @return Nombre de nouveaux monstres ajoutés.
     */
    protected static int bulkCreate(Display display, String[][] grid, HashMap<Entity, int[]> entities) {
        int monsterCount = 0;
        StandardOut output = display.getOutput();

        while (true) {
            display.displayTitle("\033[95mMaître du jeu\033[0m : Créez vos Monstres");
            display.refreshDisplay();

            ArrayList<String> options = new ArrayList<>();
            options.add("Nouveau monstre");

            int choice = display.promptChoice(options, true);
            if (choice == -1 || choice == 1) break;

            // Sélection de la race
            ArrayList<String> speciesOptions = new ArrayList<>(PREDEFINED_SPECIES);
            speciesOptions.add("Autre race...");

            int speciesChoice = display.promptChoice(speciesOptions, true);
            if (speciesChoice == -1) continue;

            String specie;
            if (speciesChoice == speciesOptions.size() - 1) {
                output.out("Entrez le nom de la race du monstre : ");
                specie = output.in().trim();
            } else {
                specie = PREDEFINED_SPECIES.get(speciesChoice);
            }

            Monster monster = createMonster(specie, display);
            int[] pos = promptPosition(display, grid);
            grid[pos[0]][pos[1]] = " M ";
            entities.put(monster, pos);
            monsterCount++;

            output.outLn("Monstre créé : " + monster);
        }
        return monsterCount;
    }

    /**
     * Crée un (1) nouveau monstre.
     * @param specie Espèce du monstre.
     * @param display Display pour les interactions
     * @return Objet du nouveau monstre.
     */
    protected static Monster createMonster(String specie, Display display) {
        StandardOut output = display.getOutput();
        ArrayList<String> statNames = new ArrayList<>(List.of(
                "Vie", "Dextérité", "Vitesse", "Force",
                "ID", "Portée d'attaque", "Points d'armure"
        ));

        ArrayList<Integer> stats = new ArrayList<>();

        for (String statName : statNames) {
            stats.add(display.promptInt(statName + " :"));
        }

        output.outLn("\nConfiguration des dés d'attaque :");
        ArrayList<String> diceOptions = new ArrayList<>(List.of(
                "1d4", "1d6", "1d8", "1d10",
                "2d6", "3d4", "1d12", "2d8"
        ));

        int diceChoice = display.promptChoice(diceOptions, false);
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

    protected static int[] promptPosition(Display display, String[][] grid) {
        StandardOut output = display.getOutput();
        while (true) {
            output.out("Entrez la position du monstre (ex: A5) : ");
            String position = output.in().trim().toUpperCase();
            int[] pos = retrieveGridPosition(position);

            if (checkEmptyCase(pos[0], pos[1], grid, grid[0].length)) {
                return pos;
            }
            output.outLn("Position invalide ou occupée. Veuillez réessayer.");
        }
    }
}