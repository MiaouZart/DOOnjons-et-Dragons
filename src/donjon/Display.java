package donjon;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Display {
    private final Donjon m_donjon;
    private final int m_repeat;

    /**
     * Constructeur du Display relatif à un Donjon
     * @param donjon Donjon à utiliser
     */
    public Display(Donjon donjon) {
        m_donjon = donjon;
        int m_cellWidth = 5;
        m_repeat = m_donjon.getDonjonSize() * m_cellWidth;
    }

    /**
     * Vérifie si la case du donjon est vide.<br>
     * <i>Version verbeuse de checkEmptyCaseNonVerbose(int x, int y, String[][] donjonGrid, int donjonSize).</i>
     * @param x Coordonnée X de la case à vérifier.
     * @param y Coordonnée Y de la case à vérifier.
     * @param donjonGrid Grille du Donjon à vérifier.
     * @param donjonSize Taille totale du Donjon.
     * @return Si oui ou non la case est prise.
     */
    protected static boolean checkEmptyCase(int x, int y, String[][] donjonGrid, int donjonSize) {
        if (x < 0 || x >= donjonSize || y < 0 || y >= donjonSize) {
            System.out.println("Coordonnées hors limites.");
            return false;
        }
        if (!donjonGrid[x][y].equals(" . ")) {
            System.out.println("Il y a un obstacle ici.");
            return false;
        }
        return true;
    }

    /**
     * Vérifie si la case du donjon est vide.<br>
     * <i>Version silencieuse de checkEmptyCase(int x, int y, String[][] donjonGrid, int donjonSize).</i>
     * @param x Coordonnée X de la case à vérifier.
     * @param y Coordonnée Y de la case à vérifier.
     * @param donjonGrid Grille du Donjon à vérifier.
     * @param donjonSize Taille totale du Donjon.
     * @return Si oui ou non la case est prise.
     */
    protected static boolean checkEmptyCaseNonVerbose(int x, int y, String[][] donjonGrid, int donjonSize) {
        if (x < 0 || x >= donjonSize || y < 0 || y >= donjonSize) {
            return false;
        }
        return !donjonGrid[x][y].equals(" # ");
    }

    /**
     * Retourne une position de la grille à partir de positions de carte.
     * @param position Position de carte (ex. B3, A2...)
     * @return Position sous format numériques {x, y}.
     */
    protected static int[] retrieveGridPosition(String position) {
        if (position.isEmpty())
            return new int[]{-1, -1};
        if (position.length() < 2 || position.length() > 3) {
            System.out.println("Entrée invalide. Format attendu : Lettre+chiffre (ex: B3).");
            return new int[]{-1, -1};
        }

        char colChar = position.charAt(0);
        int row;
        try {
            row = Integer.parseInt(position.substring(1));
        } catch (NumberFormatException e) {
            System.out.println("Numéro de ligne invalide.");
            return new int[]{-1, -1};
        }

        int col = colChar - 'A';
        return new int[]{row, col};
    }

    /**
     * Essaie de convertir une chaîne de caractère en nombre entier.
     * @param input Chaîne de caractère à convertir.
     * @return Nombre récupéré.
     */
    protected static int retrieveInt(String input) {
        int result;
        try {
            result = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Valeur Invalide.");
            return -1;
        }
        return result;
    }

    /**
     * Met à jour les informations.
     */
    public void refreshDisplay() {
        displayTurn();
        displayGrid();
    }

    /**
     * Affiche le tour en cours.
     */
    protected void displayTurn() {
        System.out.println("Tour " + m_donjon.getTurn());
    }

    /**
     * Affiche un titre.
     * @param message Chaîne de caractère à afficher.
     */
    protected void displayTitle(String message) {
        System.out.println("*".repeat(m_repeat + 3));
        System.out.printf("Donjon : %d\n", m_donjon.m_donjonNumber);
        if (!m_donjon.getEntities().isEmpty()) {
            System.out.printf(" ".repeat(m_repeat / 3) + "%2s\n", message);
        }
        System.out.println();
        System.out.println("*".repeat(m_repeat + 3));
    }

    /**
     * Affiche la grille, avec les informations dans les cellules.
     */
    protected void displayGrid() {
        int asciiA = 'A';
        int rows = 0;

        // En-tête des colonnes (A, B, C...)
        System.out.print("    ");
        for (int i = 0; i < m_donjon.getDonjonSize(); i++) {
            System.out.printf(" %2s  ", (char)(asciiA + i));
        }
        System.out.println();

        System.out.println("   /" + "-".repeat(m_repeat) + "\\");

        // Affichage des lignes
        for (int i = 0; i < m_donjon.getDonjonSize(); i++) {
            System.out.printf("%s %s|", rows, rows < 10 ? " " : "");
            rows++;
            for (int j = 0; j < m_donjon.getDonjonSize(); j++) {
                String cell = m_donjon.getDonjonGrid()[i][j];
                if (cell == null) cell = " ";
                System.out.printf(" %s ", cell);
            }
            System.out.println("|");
        }
        System.out.println("  \\" + "-".repeat(m_repeat) + "/");
    }

    /**
     * Demande à l'utilisateur une entrée de type nombre entier.
     * @param scanner Scanner à utiliser pour demander le nombre.
     * @param label Texte à afficher pour demander le nombre.
     * @return Nombre entier que l'utilisateur a entré.
     */
    public static int promptInt(Scanner scanner, String label) {
        int result = -1;
        while (result == -1) {
            System.out.print("Entrez " + label + " : ");
            result = retrieveInt(scanner.nextLine().trim());
        }
        return result;
    }

    /**
     * Demande à l'utilisateur une entrée de type nombre entier, inclus entre deux nombres.<br>
     * <i>Version clamp de promptInt(Scanner scanner, String label)</i>
     * @param scanner Scanner à utiliser pour demander le nombre.
     * @param label Texte à afficher pour demander le nombre.
     * @param min Nombre minimum accepté.
     * @param max Nombre max accepté.
     * @return Nombre entier que l'utilisateur a entré.
     */
    public static int promptInt(Scanner scanner, String label, int min, int max) {
        int result = -1;
        while (result == -1 || !(min <= result && result < max)) {
            System.out.print("Entrez " + label + " : ");
            result = retrieveInt(scanner.nextLine().trim());
        }
        return result;
    }

    /**
     * Permet de demander à l'utilisateur de sélectionner un des choix possible.
     * @param choices Tableau des choix possibles.
     * @param allowFin Autorise le joueur à skip (taper 'fin' pour ignorer).
     * @return Choix entre les prompts.
     */
    public static int promptChoice(ArrayList<String> choices, boolean allowFin) {
        int nbChoice = choices.size();
        int res;

        for (int i = 0; i < nbChoice; i++) {
            System.out.println("[" + i + "] " + choices.get(i));
        }

        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Faites votre choix" + (allowFin ? " ou tapez 'fin' pour annuler" : "") + " :");
            String Choice = scan.nextLine();

            if (allowFin && Objects.equals(Choice, "fin")) {
                res = -1; // Option "fin" permet de sortir
                break;
            }

            res = retrieveInt(Choice);
            if (res >= 0 && res < nbChoice) {
                break;
            } else {
                System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }

        return res;
    }


}