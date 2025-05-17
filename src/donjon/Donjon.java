package donjon;

import dice.Dice;
import entity.Entity;
import equipment.Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import static donjon.Display.promptInt;

import static Dice.Dice.someUp;

public class Donjon {
    protected final int m_donjonSize;
    protected final String[][] m_donjonGrid;
    protected int m_turn = 0;
    protected int m_donjonNumber;
    protected boolean m_setup = false;
    protected final HashMap<Equipment, int[]> m_equipments;
    protected final HashMap<Entity, int[]> m_entities;
    protected Display m_display;

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
        playerPosition();
        MonsterCreator.bulkCreate(m_display, m_donjonGrid, m_entities);
        EquipmentCreator.create(m_display, m_donjonGrid, m_equipments);
        promptContext();
        initiativeInit();
        m_setup = true;
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


    private void playerPosition() {
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
        }
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
            System.out.print("Combien de cases voulez-vous vous déplacer (ou 'fin' pour terminer) : ");

            String input = scan.nextLine();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }

            int direction = -1;
            while (direction > 7 || direction < 0) {
                direction = promptInt(scan, "Vers où [0] ↑ [1] ↓ [2] → [3] ← [4] ↗ [5] ↘ [6] ↙ [7] ↖ : ");
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
            m_donjonGrid[newX][newY] = " P ";
            int[] newPos = new int[]{newX, newY};
            m_entities.replace(entity, newPos);
            remainingMove -= moveSpeed;
        }
        System.out.println("Fin du déplacement.");
    }
    public void playerAttack(Personnage player) {

        if(player.getWeapon()==null) {
            System.out.println("Pas d'armes équiper ");
            return;
        }

        int atkRange = player.getWeapon().getRange();
        Dice atcDice = player.getWeapon().getDamageDice();
        ArrayList<Monster> attackable = new ArrayList<Entity>();
        for(Entity entity : m_entities.keySet()){
            if(entity.getClass() != Monster.class){
                continue;
            }
            int[] MonsterPos = m_entities.get(entity);
            int[] PlayerPos = m_entities.get(player);
            int xDif = Math.abs(MonsterPos[0]-PlayerPos[0]);
            int yDif = Math.abs(MonsterPos[1]-PlayerPos[1]);
            if(xDif>atkRange||yDif>atkRange){
                continue;
            }
            attackable.add((Monster) entity);
        }
        if(!attackable.isEmpty()){
            System.out.println("Selectionnez votre cible : ");
            for(int i =0;i<attackable.size();i++){
                System.out.println("["+i+"] "+attackable.get(i));
            }
            int selected = -1;
            Scanner scan = new Scanner(System.in);
            while (selected > 7 || selected < 0) {
                selected = promptInt(scan, "Entrez la cible ");
            }
            int damage = someUp(atcDice.roll());
            attackable.get(selected);

        }


        System.out.println("Fin du déplacement.");
    }

    public void nextTurn() {
        m_turn++;
        m_display.refreshDisplay();
    }

    // Getters pour DonjonDisplay
    public int getDonjonSize() { return m_donjonSize; }
    public String[][] getDonjonGrid() { return m_donjonGrid; }
    public int getTurn() { return m_turn; }
    public HashMap<Entity, int[]> getEntities() { return m_entities; }
}