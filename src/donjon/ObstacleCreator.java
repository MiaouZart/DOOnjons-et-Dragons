package donjon;

import printer.StandardOut;

import static donjon.Display.*;

public class ObstacleCreator {
    /**
     * Permet de créer autant d'obstacle que le MJ veut.
     * @param display Display à utiliser relatif au donjon.
     * @param grid Grille en cours, afin de voir s'il y a déjà quelque-chose, et placer le nouvel obstacle.
     */
    public static void bulkCreate(Display display, String[][] grid) {
        StandardOut output = display.getOutput();

        while (true) {
            display.displayTitle("Maitre du jeu Positionnez vos obstacles");
            display.refreshDisplay();

            output.out("Entrez la position d'un obstacle (ex: A5) ou 'fin' : ");
            String input = output.in().trim().toUpperCase();

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