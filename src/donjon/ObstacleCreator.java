package donjon;

import java.util.Scanner;

import static donjon.Display.*;

public class ObstacleCreator {
    /**
     * Permet de créer autant d'obstacle que le MJ veut.
     * @param display Display à utiliser relatif au donjon.
     * @param grid Grille en cours, afin de voir s'il y a déjà quelque-chose, et placer le nouvel obstacle.
     */
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

    /**
     * Permet de créer un nouvel obstacle.
     * @param input Position où placer le nouvel obstacle.
     * @param grid Grille où placer le nouvel obstacle si l'emplacement est vide.
     */
    private static void create(String input, String[][] grid) {
        int[] pos = retrieveGridPosition(input);
        if (checkEmptyCase(pos[0], pos[1], grid, grid[0].length)) {
            grid[pos[0]][pos[1]] = obstacleChar;
        }
    }
}
