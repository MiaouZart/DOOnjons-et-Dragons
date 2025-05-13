package Donjon;

import Dice.Dice;
import Entity.Entity;
import Entity.Monster.Monster;
import Entity.Personnage.Personnage;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Scanner;

public class Donjon {

    private int m_donjonSize;
    private String[][] m_donjonGrid;
    private int m_turn= 0;
    private int m_donjonNumber;
    private int m_cellWidth = 5;
    private int m_repeat;
    private boolean m_setup =false;
    private Personnage m_currentPlayer;
    private HashMap<Entity,int[]> m_Entities;

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
        for (int i = 0; i < Players.size(); i++) {
            m_Entities.put(Players.get(i),new int[]{-1,-1});//mettre par défault les player en dehors de la grille;
        }
        obstaclePosition();
    }


    private boolean checkEmptyCase(int x ,int y){
        if (x < 0 || x >= m_donjonSize || y < 0 || y >= m_donjonSize) {
            System.out.println("Coordonnées hors limites.");
            return false;
        }
        if (m_donjonGrid[x][y].equals(" # ")) {
            System.out.println("Il y a un obstacle ici.");
            return false;
        }
        return true;
    }
    private int[] retrievGridPosition(String position){
        if(position.isEmpty()){
            return new int[]{-1,-1};
        }
        if (position.length() < 2 || position.length() > 3) {
            System.out.println("Entrée invalide. Format attendu : Lettre+chiffre (ex: B3).");
            return new int[]{-1,-1};
        }

        char colChar = position.charAt(0);
        int row;
        try {
            row = Integer.parseInt(position.substring(1));
        } catch (NumberFormatException e) {
            System.out.println("Numéro de ligne invalide.");
            return new int[]{-1,-1};
        }

        int col = colChar - 'A';

        return new int[]{row,col};
    }

    private int retrievInt(String input){
        int result=0;
        try {
            result = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Valeur Invalide.");
            return -1;
        }
        return result;
    }

    private int promptInt(Scanner scanner, String label) {
        int result = -1;
        while (result == -1) {
            System.out.print("Entrez " + label + " : ");
            result = retrievInt(scanner.nextLine().trim());
        }
        return result;
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
            int[] pos = retrievGridPosition(input);

            if(checkEmptyCase(pos[0],pos[1])) {
                m_donjonGrid[pos[0]][pos[1]] = " # ";
            }
        }
        monsterCreation();
    }

    private void monsterCreation() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayTitle("Maître du jeu : Créez vos Monstres");
            refreshDisplay();
            System.out.print("Entrez la race du monstre ou 'fin' : ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("FIN")) break;

            String monsterSpecie = scanner.nextLine().trim();
            int hp = promptInt(scanner,"Vie");
            int dex = promptInt(scanner, "Dexterité");
            int speed = promptInt(scanner, "Vitesse");
            int strength = promptInt(scanner, "Force");
            int id = promptInt(scanner, "ID");
            int atckRange = promptInt(scanner, "Portée d'attaque");

            int nbDice = promptInt(scanner, "Nombre de dés d'attaque");
            int faceDice = promptInt(scanner, "Nombre de faces par dé");
            Dice dice = new Dice(nbDice, faceDice);

            System.out.print("Entrez la position du monstre (ex: A5) : ");
            String position = scanner.nextLine().trim().toUpperCase();
            int[] pos = retrievGridPosition(position);
            if (checkEmptyCase(pos[0], pos[1])) {
                Monster newMonster = new Monster(hp,strength,dex, speed,monsterSpecie, id, atckRange, dice);
                m_donjonGrid[pos[0]][pos[1]] = " M ";
                m_Entities.put(newMonster ,new int[]{pos[0],pos[1]});
            } else {
                System.out.println("Position invalide. Monstre non placé.");
            }
        }
    }
    private void equipmentPosition(){

    }




    public void refreshDisplay(){
        displayTurn();
        displayGrid();
    }


    private void displayTurn(){
        System.out.println("Tour "+m_turn);
    }

    protected void displayTitle(String message){
        System.out.println("*".repeat(m_repeat+3));
        System.out.printf("Donjon : %d\n",m_donjonNumber);
        if(!m_Entities.isEmpty()) {
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
