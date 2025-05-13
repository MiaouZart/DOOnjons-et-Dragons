package Donjon;

import Dice.Dice;
import Entity.Entity;
import Entity.Monster.Monster;
import Entity.Personnage.Personnage;
import Equipment.Armor.Armor;
import Equipment.Armor.Types.ChainMail;
import Equipment.Armor.Types.HalfPlate;
import Equipment.Armor.Types.Plate;
import Equipment.Armor.Types.ScaleMail;
import Equipment.Equipment;
import Equipment.Weapon.Types.*;

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
    private HashMap<Equipment,int[]>m_Equipments;
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
        m_Entities = new HashMap<Entity,int[]>();
        m_Equipments = new HashMap<Equipment,int[]>();
        if(Players!=null){
            for (int i = 0; i < Players.size(); i++) {
                m_Entities.put(Players.get(i),new int[]{-1,-1});//mettre par défault les player en dehors de la grille;
            }
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

            String monsterSpecie = input;
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
        equipmentPosition();
    }
    private void equipmentPosition(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            displayTitle("Maître du jeu : Créez vos Monstres");
            refreshDisplay();
            System.out.print("continuez ou 'fin' : ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("FIN")) break;
            int type = promptInt(scanner,"[0] Armure ; [1] Armes (ex:1)");
            Equipment newEquipment =null;
            while (type>1||type<0){
                type = promptInt(scanner,"[0] Armure ; [1] Armes (ex:1)");
            }
            if(type==0) {
                System.out.println("Création d'une Armure : ");
                do {
                    type = promptInt(scanner, "[0] Côtte de mailles ; [1] Demi-plate  ; [2] Armure d'écailles ; [3] Harnois (ex:2)");
                } while (type > 3 || type < 0);
                newEquipment = switch (type) {
                    case 0 -> new ChainMail();
                    case 1 -> new HalfPlate();
                    case 2 -> new ScaleMail();
                    case 3 -> new Plate();
                    default -> newEquipment;
                };
            }else {
                System.out.println("Création d'une Armes : ");
                do {
                    type = promptInt(scanner, "[0] Arbalète ; [1] Bâton  ; [2] Masse d'armes ; [3] Épée longue  ; [4] Rapière ; [5] Fronde ; [6] Arc court (ex:2)");
                } while (type > 6 || type < 0);
                newEquipment = switch (type) {
                    case 0 -> new Crossbow();
                    case 1 -> new Quarterstaff();
                    case 2 -> new Mace();
                    case 3 -> new Rapier();
                    case 4 -> new Sling();
                    case 5 -> new Shortbow();
                    case 6 -> new Longsword();
                    default -> newEquipment;
                };
            }
                System.out.print("Entrez la position de l'équipement (ex: A5) : ");
                String position = scanner.nextLine().trim().toUpperCase();
                int[] pos = retrievGridPosition(position);
                if(checkEmptyCase(pos[0],pos[1])) {
                    m_Equipments.put(newEquipment,pos);
                    m_donjonGrid[pos[0]][pos[1]] = " E ";
                }
        }
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
