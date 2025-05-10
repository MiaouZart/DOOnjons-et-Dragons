package Donjon;

public class Donjon {

    private int m_donjonSize;
    private String[][] m_donjonGrid;
    private final static int m_characterSize = 3;

    public  Donjon(int size){
        if(size<15||size>25){
            throw new IllegalArgumentException("Grid Size is to small or to large");
        }
        m_donjonSize = size;
        m_donjonGrid = new String[m_donjonSize][m_donjonSize];
        for (int i = 0; i < m_donjonSize; i++) {
            for (int j = 0; j < m_donjonSize; j++) {
                m_donjonGrid[i][j] = " . ";
            }
        }
    }


    public void Display() {
        int asciiA = (int) 'A';
        int rows = 0;

        int cellWidth = 5;
        int repeat = m_donjonSize * cellWidth;

        System.out.print("   ");
        for (int i = 0; i < m_donjonSize; i++) {
            System.out.printf(" %2s  ", (char)(asciiA + i));
        }
        System.out.println();

        System.out.println("  /" + "-".repeat(repeat) + "\\");

        for (String[] row : m_donjonGrid) {
            System.out.printf("%c |", (char)(asciiA + rows));
            rows++;
            for (String cell : row) {
                if (cell == null) cell = " ";
                System.out.printf(" %s ", cell);
            }
            System.out.println("|");
        }

        System.out.println("  \\" + "-".repeat(repeat) + "/");
    }



}
