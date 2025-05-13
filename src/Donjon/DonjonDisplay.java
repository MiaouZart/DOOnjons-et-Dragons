// DonjonDisplay.java
package Donjon;

public class DonjonDisplay {
    private Donjon m_donjon;
    private int m_cellWidth = 5;
    private int m_repeat;

    public DonjonDisplay(Donjon donjon) {
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
        int asciiA = (int) 'A';
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
}