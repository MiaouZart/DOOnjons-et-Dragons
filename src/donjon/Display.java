package donjon;

import java.util.ArrayList;
import java.util.Objects;
import printer.StandardOut;

public class Display {
    private final Donjon m_donjon;
    private final int m_repeat;
    private static StandardOut m_output = null;
    public static String obstacleChar = " \033[35m#\033[0m ";
    public static String equipmentChar = " \033[93mE\033[0m ";

    /**
     * Constructeur du Display relatif à un Donjon
     * @param donjon Donjon à utiliser
     * @param output Systeme de sortie standard à utiliser
     */
    public Display(Donjon donjon, StandardOut output) {
        m_donjon = donjon;
        Display.init(output);
        m_output = output;
        int m_cellWidth = 5;
        m_repeat = m_donjon.getDonjonSize() * m_cellWidth;
    }

    public static void init(StandardOut output) {
        m_output = output;
    }

    protected static boolean checkEmptyCase(int x, int y, String[][] donjonGrid, int donjonSize) {
        if (x < 0 || x >= donjonSize || y < 0 || y >= donjonSize) {
            m_output.outLn("Coordonnées hors limites.");
            return false;
        }
        if (!donjonGrid[x][y].equals(" . ")) {
            m_output.outLn("Il y a un obstacle ici.");
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
        return !donjonGrid[x][y].equals(obstacleChar);
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
            m_output.outLn("Entrée invalide. Format attendu : Lettre+chiffre (ex: B3).");
            return new int[]{-1, -1};
        }

        char colChar = position.charAt(0);
        int row;
        try {
            row = Integer.parseInt(position.substring(1));
        } catch (NumberFormatException e) {
            m_output.outLn("Numéro de ligne invalide.");
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
            m_output.outLn("Valeur Invalide.");
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
        m_output.outLn("\033[1mTour " + m_donjon.getTurn() + "\033[0m");
    }

    /**
     * Affiche un titre.
     * @param message Chaîne de caractère à afficher.
     */
    protected void displayTitle(String message) {
        m_output.outLn("*".repeat(m_repeat + 3));
        m_output.printf("Donjon : %d\n", m_donjon.m_donjonNumber);
        if (!m_donjon.getEntities().isEmpty()) {
            m_output.printf(" ".repeat(m_repeat / 3) + "%2s\n", message);
        }
        m_output.outLn("");
        m_output.outLn("*".repeat(m_repeat + 3));
    }

    /**
     * Affiche la grille, avec les informations dans les cellules.
     */
    protected void displayGrid() {
        int asciiA = 'A';
        int rows = 0;

        // En-tête des colonnes (A, B, C...)
        m_output.out("    ");
        for (int i = 0; i < m_donjon.getDonjonSize(); i++) {
            m_output.printf(" %2s  ", (char)(asciiA + i));
        }
        m_output.outLn("");

        m_output.outLn("   /" + "-".repeat(m_repeat) + "\\");

        // Affichage des lignes
        for (int i = 0; i < m_donjon.getDonjonSize(); i++) {
            m_output.printf("%s %s|", rows, rows < 10 ? " " : "");
            rows++;
            for (int j = 0; j < m_donjon.getDonjonSize(); j++) {
                String cell = m_donjon.getDonjonGrid()[i][j];
                if (cell == null) cell = " ";
                m_output.printf(" %s ", cell);
            }
            m_output.outLn("|");
        }
        m_output.outLn("  \\" + "-".repeat(m_repeat) + "/");
    }

    /**
     * Demande à l'utilisateur une entrée de type nombre entier.
     * @param label Texte à afficher pour demander le nombre.
     * @return Nombre entier que l'utilisateur a entré.
     */
    public static int promptInt(String label) {
        int result = -1;
        while (result == -1) {
            m_output.out("Entrez " + label + " : ");
            result = retrieveInt(m_output.in().trim());
        }
        return result;
    }

    /**
     * Demande à l'utilisateur une entrée de type nombre entier, inclus entre deux nombres.
     * @param label Texte à afficher pour demander le nombre.
     * @param min Nombre minimum accepté.
     * @param max Nombre max accepté.
     * @return Nombre entier que l'utilisateur a entré.
     */
    public static int promptInt(String label, int min, int max) {
        int result = -1;
        while (result == -1 || !(min <= result && result < max)) {
            m_output.out("Entrez " + label + " : ");
            result = retrieveInt(m_output.in().trim());
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
            m_output.outLn("[" + "\033[1;4m" + i + "\033[0m" + "] " + choices.get(i));
        }

        while (true) {
            m_output.out("Faites votre choix" + (allowFin ? " ou tapez 'fin' pour annuler" : "") + " : ");
            String Choice = m_output.in();

            if (allowFin && Objects.equals(Choice, "fin")) {
                res = -1;
                break;
            }

            res = retrieveInt(Choice);
            if (res >= 0 && res < nbChoice) {
                break;
            } else {
                m_output.outLn("Choix invalide. Veuillez réessayer.");
            }
        }

        return res;
    }

    public StandardOut getOutput() {
        return m_output;
    }
}