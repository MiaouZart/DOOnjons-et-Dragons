package donjon;

import java.util.Scanner;

import static donjon.Donjon.retrieveInt;

public class Display {
    private Donjon m_donjon;
    private final int m_cellWidth = 5;
    private final int m_repeat;

    public Display(Donjon donjon) {
        m_donjon = donjon;
        m_repeat = m_donjon.getDonjonSize() * m_cellWidth;
    }

    public void refreshDisplay() {
        displayTurn();
        displayGrid();
    }

    protected void displayTurn() {
        System.out.println("Tour " + m_donjon.getTurn());
    }

    protected void displayTitle(String message) {
        System.out.println("*".repeat(m_repeat + 3));
        System.out.printf("Donjon : %d\n", m_donjon.m_donjonNumber);
        if (!m_donjon.getEntities().isEmpty()) {
            System.out.printf(" ".repeat(m_repeat / 3) + "%2s\n", message);
        }
        System.out.println();
        System.out.println("*".repeat(m_repeat + 3));
    }

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

    public static int promptInt(Scanner scanner, String label) {
        int result = -1;
        while (result == -1) {
            System.out.print("Entrez " + label + " : ");
            result = retrieveInt(scanner.nextLine().trim());
        }
        return result;
    }

    public static int promptInt(Scanner scanner, String label, int min, int max) {
        int result = -1;
        while (result == -1 || !(min <= result && result < max)) {
            System.out.print("Entrez " + label + " : ");
            result = retrieveInt(scanner.nextLine().trim());
        }
        return result;
    }

}