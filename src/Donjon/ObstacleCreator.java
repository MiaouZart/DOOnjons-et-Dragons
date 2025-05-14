package Donjon;

import java.util.Scanner;

import static Donjon.Donjon.checkEmptyCase;
import static Donjon.Donjon.retrieveGridPosition;

public class ObstacleCreator {

    public static void bulkCreate(Display display, String[][] grid) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            display.displayTitle("Maitre du jeu Positionnez vos obstacle");
            display.refreshDisplay();
            System.out.print("Entrez la position d'un obstacle (ex: A5) ou 'fin' : ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("FIN")) {
                break;
            }

            create(input, grid);
        }
    }
    private static void create(String input, String[][] grid) {
        int[] pos = retrieveGridPosition(input);
        if (checkEmptyCase(pos[0], pos[1], grid, grid[0].length)) {
            grid[pos[0]][pos[1]] = " # ";
        }
    }
}
