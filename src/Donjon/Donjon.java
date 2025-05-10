package Donjon;

import Entity.Personnage.Personnage;

import java.util.ArrayList;

public class Donjon {

    private int m_donjonSize;
    private String[][] m_donjonGrid;
    private int m_round= 0;
    private int m_donjonNumber;
    private ArrayList<Personnage> m_players;
    private String m_title;
    private int m_cellWidth = 5;
    private int m_repeat;
    private Personnage m_currentPlayer;

    public  Donjon(int size,ArrayList<Personnage> Players){
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
        m_repeat = m_donjonSize * m_cellWidth;

        m_players = Players;
        m_currentPlayer = m_players.getFirst();
    }



    public void turn(){
        displayTitle();
        displayGrid();
    }


    protected void displayTitle(){
        System.out.println("*".repeat(m_repeat+3));
        System.out.printf("Donjon : %d\n",m_donjonNumber);
        System.out.printf(" ".repeat(m_repeat/3)+"%2s\n",m_currentPlayer.toString());
        System.out.println();
        System.out.println("*".repeat(m_repeat+3));
    }

    protected void displayGrid() {
        int asciiA = (int) 'A';
        int rows = 0;

        System.out.print("   ");
        for (int i = 0; i < m_donjonSize; i++) {
            System.out.printf(" %2s  ", (char)(asciiA + i));
        }
        System.out.println();

        System.out.println("  /" + "-".repeat(m_repeat) + "\\");

        for (String[] row : m_donjonGrid) {
            System.out.printf("%c |", (char)(asciiA + rows));
            rows++;
            for (String cell : row) {
                if (cell == null) cell = " ";
                System.out.printf(" %s ", cell);
            }
            System.out.println("|");
        }
        System.out.println("  \\" + "-".repeat(m_repeat) + "/");
    }



}
