package Donjon;

import Dice.Dice;
import Entity.Entity;
import Entity.Monster.Monster;
import java.util.HashMap;
import java.util.Scanner;

import static Donjon.Donjon.*;

public class MonsterCreator {
    protected static void bulkCreate(DonjonDisplay display, String[][] grid, HashMap<Entity, int[]> entities) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            display.displayTitle("Maître du jeu : Créez vos Monstres");
            display.refreshDisplay();
            System.out.print("Entrez la race du monstre ou 'fin' : ");
            String specie = scanner.nextLine().trim();
            if (specie.equalsIgnoreCase("FIN")) break;

            Monster monster = create(scanner, specie);
            int[] pos = promptPos(scanner, grid);
            grid[pos[0]][pos[1]] = " M ";
            entities.put(monster, new int[]{pos[0], pos[1]});
        }
    }

    protected static Monster create(Scanner scanner, String specie) {
        int hp = promptInt(scanner, "Vie");
        int dex = promptInt(scanner, "Dexterité");
        int speed = promptInt(scanner, "Vitesse");
        int strength = promptInt(scanner, "Force");
        int id = promptInt(scanner, "ID");
        int atkRange = promptInt(scanner, "Portée d'attaque");

        int nbDice = promptInt(scanner, "Nombre de dés d'attaque");
        int faceDice = promptInt(scanner, "Nombre de faces par dé");
        Dice dice = new Dice(nbDice, faceDice);

        return new Monster(hp, strength, dex, speed, specie, id, atkRange, dice);
    }

    protected static int[] promptPos(Scanner scanner, String[][] grid) {
        int[] pos = new int[]{-1, -1};
        while (!checkEmptyCaseNonVerbose(pos[0], pos[1], grid, grid[0].length)) {
            System.out.print("Entrez la position du monstre (ex: A5) : ");
            String position = scanner.nextLine().trim().toUpperCase();
            pos = retrieveGridPosition(position);
            if (checkEmptyCase(pos[0], pos[1], grid, grid[0].length)) {
                return pos;
            } else {
                System.out.println("Position invalide.");
            }
        }
        throw new IllegalStateException("Bad position + loop issue");
    }
}
