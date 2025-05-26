package donjon;

import dice.Dice;
import entity.Entity;
import entity.EnumEntity;
import equipment.Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import static donjon.Display.promptInt;


public class Donjon {
    protected final int m_donjonSize;
    protected final String[][] m_donjonGrid;
    protected int m_turn = 0;
    protected int m_donjonNumber;
    protected boolean m_setup = false;
    protected final HashMap<Equipment, int[]> m_equipments;
    protected final HashMap<Entity, int[]> m_entities;
    protected Display m_display;
    protected boolean m_donjonWin=false;
    protected boolean m_donjonLoose = false;
    private int m_monsterNumber=0;
    private int m_playerNumber=0;

    public Donjon(int size, HashMap<Entity, int[]> players) {

        m_donjonSize = size;
        m_donjonGrid = new String[m_donjonSize][m_donjonSize];
        initializeGrid();
        m_equipments = new HashMap<>();

        m_entities = Objects.requireNonNullElseGet(players, HashMap::new);

        m_display = new Display(this);
    }

    private void initializeGrid() {
        for (int i = 0; i < m_donjonSize; i++) {
            for (int j = 0; j < m_donjonSize; j++) {
                m_donjonGrid[i][j] = " . ";
            }
        }
    }

    public void setupDonjon() {
        ObstacleCreator.bulkCreate(m_display, m_donjonGrid);
        EntityPosition();
        m_monsterNumber= MonsterCreator.bulkCreate(m_display, m_donjonGrid, m_entities);
        EquipmentCreator.create(m_display, m_donjonGrid, m_equipments);
        promptContext();
        initiativeInit();
        m_setup = true;
    }

    public void createObstacle(){
        ObstacleCreator.bulkCreate(m_display, m_donjonGrid);
    }

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

    protected static boolean checkEmptyCaseNonVerbose(int x, int y, String[][] donjonGrid, int donjonSize) {
        if (x < 0 || x >= donjonSize || y < 0 || y >= donjonSize) {
            return false;
        }
        return !donjonGrid[x][y].equals(" # ");
    }

    protected static int[] retrieveGridPosition(String position) {
        if (position.isEmpty()) {
            return new int[]{-1, -1};
        }
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


    private void EntityPosition() {
        Scanner scanner = new Scanner(System.in);

        for (Entity playerName : m_entities.keySet()) {
            boolean positionOk = false;
            while (!positionOk) {
                m_display.displayTitle("Maître du jeu - Positionnez vos Joueurs");
                m_display.refreshDisplay();

                System.out.print("Entrez la position du joueur " + playerName + " (ex: A5) : ");
                String input = scanner.nextLine().trim().toUpperCase();

                int[] pos = retrieveGridPosition(input);

                if (checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
                    m_donjonGrid[pos[0]][pos[1]] = " P ";
                    m_entities.replace(playerName, new int[]{pos[0], pos[1]});
                    positionOk = true;
                } else {
                    System.out.println("Position déjà occupée. Veuillez réessayer.");
                }
            }
            m_playerNumber++;
        }
    }


    public void EntityPosition(Entity entity) {
        Scanner scanner = new Scanner(System.in);
        boolean positionOk = false;
        while (!positionOk) {
            m_display.displayTitle("Maître du jeu - Positionnez vos Joueurs");
            m_display.refreshDisplay();

            System.out.print("Entrez la position du joueur " + entity + " (ex: A5) : ");
            String input = scanner.nextLine().trim().toUpperCase();

            int[] pos = retrieveGridPosition(input);

            if (checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
                m_donjonGrid[m_entities.get(entity)[0]][m_entities.get(entity)[1]]="  ";
                m_donjonGrid[pos[0]][pos[1]] =entity.getSprite();
                m_entities.replace(entity, new int[]{pos[0], pos[1]});
                positionOk = true;
            } else {
                System.out.println("Position déjà occupée. Veuillez réessayer.");
            }
        }
        m_playerNumber++;
    }




    private void promptContext() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Début du donjon...\nSaisissez le contexte : ");
        scanner.nextLine();
    }

    private void initiativeInit(){
        Dice initiativeDice = new Dice(1,20);
        Scanner scan = new Scanner(System.in);
        for(Entity entity : m_entities.keySet()){
            m_display.displayTitle(entity.toString()+" : Faite votre jetté de dés : ");
            scan.nextLine();
            int lancer = initiativeDice.roll()[0];
            System.out.println("Vous avez tirez un "+lancer);
            entity.addInitiative(lancer);
            System.out.println("Vous avez donc maintenant "+entity.getInitiative()+" d'initiative");
        }
    }

    public void moveEntity(Entity entity) {
        int remainingMove = entity.getSpeed();
        int moveSpeed = 1;
        Scanner scan = new Scanner(System.in);

        while (remainingMove > 0) {
            m_display.refreshDisplay();
            System.out.println("Vous avez " + remainingMove + " points de déplacement restants.");
            System.out.print("voulez-vous vous continuer ou 'fin' pour terminer) : ");

            String input = scan.nextLine();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }

            int direction = -1;
            while (direction > 7 || direction < 0) {
                direction = promptInt(scan, "Vers où [0] ↑ [1] ↓ [2] → [3] ← [4] ↗ [5] ↘ [6] ↙ [7] ↖ ");
            }

            int[] moveFactor = switch (direction) {
                case 0 -> new int[]{-moveSpeed, 0};
                case 1 -> new int[]{moveSpeed, 0};
                case 2 -> new int[]{0, moveSpeed};
                case 3 -> new int[]{0, -moveSpeed};
                case 4 -> new int[]{-moveSpeed, moveSpeed};
                case 5 -> new int[]{moveSpeed, moveSpeed};
                case 6 -> new int[]{moveSpeed, -moveSpeed};
                case 7 -> new int[]{-moveSpeed, -moveSpeed};
                default -> new int[]{0, 0};
            };

            int[] oldPos = m_entities.get(entity);
            int newX = oldPos[0] + moveFactor[0];
            int newY = oldPos[1] + moveFactor[1];

            if(newX<0||newX>m_donjonSize||newY<0||newY>m_donjonSize|| !(Objects.equals(m_donjonGrid[newX][newY], " . ")||(Objects.equals(m_donjonGrid[newX][newY], " E ")))){
                System.out.println("Vous n'avez pas le droit");
                continue;
            }

            // Mise à jour de la grille
            m_donjonGrid[oldPos[0]][oldPos[1]] = " . ";
            m_donjonGrid[newX][newY] = entity.getSprite();
            int[] newPos = new int[]{newX, newY};
            m_entities.replace(entity, newPos);
            remainingMove -= moveSpeed;
        }
        System.out.println("Fin du déplacement.");
    }

    private int distance(Entity entity1, Entity entity2) {
        int x1 = m_entities.get(entity1)[0];
        int y1 = m_entities.get(entity1)[1];
        int x2 = m_entities.get(entity2)[0];
        int y2 = m_entities.get(entity2)[1];
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }



    public void playerAttack(Entity entity) {
        EnumEntity entityType = entity.getType();
        ArrayList<Entity> entitiesThatCanBeAttacked = new ArrayList<>();
        ArrayList<String> attackableNames = new ArrayList<>();
        int range = entity.getRangePoint();

        for (Entity enemy : m_entities.keySet()) {
            if (enemy.getType() == entityType) {
                continue; // même camp
            }
            int distance = distance(entity, enemy);
            if (distance <= range) {
                entitiesThatCanBeAttacked.add(enemy);
                attackableNames.add(enemy.toString());
            }
        }

        if (entitiesThatCanBeAttacked.isEmpty()) {
            System.out.println("Personne à attaquer");
            return;
        }

        System.out.println("Vous pouvez attaquer :");
        int cible = Display.promptChoice(attackableNames, true);

        Entity chose = entitiesThatCanBeAttacked.get(cible);
        boolean toucher = chose.getAttacked(entity);

        if (toucher) {
            System.out.println("Bravo vous avez touché votre cible");
            if (chose.getDead()) {
                System.out.println("Et Vous l'avez tuée");
                m_entities.remove(chose);
                checkWin();
            }
        }
    }


    public void nextTurn() {
        m_turn++;
        m_display.refreshDisplay();
    }


    private void checkWin(){
        if(m_playerNumber==0){
            m_donjonLoose=true;
        }
        if(m_monsterNumber==0){
            m_donjonWin=true;
        }
    }

    public boolean getWin(){
        return m_donjonWin;
    }
    public boolean getLoose(){
        return m_donjonLoose;
    }



    // Getters pour DonjonDisplay
    public int getDonjonSize() { return m_donjonSize; }
    public String[][] getDonjonGrid() { return m_donjonGrid; }
    public int getTurn() { return m_turn; }
    public HashMap<Entity, int[]> getEntities() { return m_entities; }
}