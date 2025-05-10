package Donjon;

import Entity.Entity;
import Entity.Personnage.Personnage;

import java.util.ArrayList;
import java.util.Scanner;

public class Donjon {

    private int m_donjonSize;
    private String[][] m_donjonGrid;
    private int m_turn= 0;
    private int m_donjonNumber;
    private ArrayList<Entity> m_players;
    private int m_cellWidth = 5;
    private int m_repeat;
    private boolean m_setup =false;
    private Personnage m_currentPlayer;

    public  Donjon(int size,ArrayList<Entity> Players){
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
        obstaclePosition();
    }

    private void obstaclePosition(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayTitle("Maitre du jeu Positionnez vos obstacle");
            refreshDisplay();
            System.out.print("Entrez la position d'un obstacle (ex: A5) ou 'fin' : ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("FIN")) {
                break;
            }

            if (input.length() < 2 || input.length() > 3) {
                System.out.println("Entrée invalide. Format attendu : Lettre+chiffre (ex: B3).");
                continue;
            }

            char colChar = input.charAt(0);
            int row;
            try {
                row = Integer.parseInt(input.substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Numéro de ligne invalide.");
                continue;
            }

            int col = colChar - 'A';

            if (row < 0 || row >= m_donjonSize || col < 0 || col >= m_donjonSize) {
                System.out.println("Coordonnées hors limites.");
                continue;
            }

            if (m_donjonGrid[row][col].equals(" # ")) {
                System.out.println("Il y a déjà un obstacle ici.");
                continue;
            }

            m_donjonGrid[row][col] = " # ";
        }

        m_setup = true;
    }


    private void equipmentPosition(){

    }

    private  void monsterCreation(){

    }



    public void refreshDisplay(){
        if(m_setup){
            displayTitle(m_players.getFirst().toString());
        }
        displayTurn();
        displayGrid();
    }


    private void displayTurn(){
        System.out.println("Tour "+m_turn);
    }

    protected void displayTitle(String message){
        System.out.println("*".repeat(m_repeat+3));
        System.out.printf("Donjon : %d\n",m_donjonNumber);
        if(!m_players.isEmpty()) {
            System.out.printf(" ".repeat(m_repeat / 3) + "%2s\n", message);
        }
        System.out.println();
        System.out.println("*".repeat(m_repeat+3));
    }

    protected void displayGrid() {
        int asciiA = (int) 'A';
        int rows = 0;

        System.out.print("    ");
        for (int i = 0; i < m_donjonSize; i++) {
            System.out.printf(" %2s  ", (char)(asciiA + i));
        }
        System.out.println();

        System.out.println("   /" + "-".repeat(m_repeat) + "\\");

        for (String[] row : m_donjonGrid) {
            System.out.printf("%s %s|",rows,rows<10?" ":"");
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
