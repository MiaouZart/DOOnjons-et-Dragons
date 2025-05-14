package Donjon;

import Dice.Dice;
import Entity.Entity;
import Entity.Monster.Monster;
import Entity.Personnage.Personnage;
import Equipment.Armor.Types.*;
import Equipment.Equipment;
import Equipment.Weapon.Types.*;
import java.util.HashMap;
import java.util.Scanner;

public class Donjon {
    protected int m_donjonSize;
    protected String[][] m_donjonGrid;
    protected int m_turn = 0;
    protected int m_donjonNumber;
    protected boolean m_setup = false;
    protected Personnage m_currentPlayer;
    protected HashMap<Equipment, int[]> m_Equipments;
    protected HashMap<Entity, int[]> m_Entities;
    protected DonjonDisplay m_display;

    public Donjon(int size, HashMap<Entity, int[]> players) {
        if (size < 15 || size > 25) {
            throw new IllegalArgumentException("La Grille est trop petite ou trop grande ");
        }
        m_donjonSize = size;
        m_donjonGrid = new String[m_donjonSize][m_donjonSize];
        initializeGrid();
        m_Equipments = new HashMap<Equipment, int[]>();

        if (players != null) {
            m_Entities = players;
        }else{
            m_Entities = new HashMap<Entity, int[]>();
        }

        m_display = new DonjonDisplay(this);
    }

    private void initializeGrid() {
        for (int i = 0; i < m_donjonSize; i++) {
            for (int j = 0; j < m_donjonSize; j++) {
                m_donjonGrid[i][j] = " . ";
            }
        }
    }

    public void setupDonjon() {
        obstaclePosition();
        MonsterCreator.bulkCreate(m_display, m_donjonGrid, m_Entities);
        playerPosition();
        monsterCreation();
        equipmentPosition();
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

    protected static int retrievInt(String input) {
        int result = 0;
        try {
            result = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Valeur Invalide.");
            return -1;
        }
        return result;
    }

    protected static int promptInt(Scanner scanner, String label) {
        int result = -1;
        while (result == -1) {
            System.out.print("Entrez " + label + " : ");
            result = retrievInt(scanner.nextLine().trim());
        }
        return result;
    }

    private void obstaclePosition() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            m_display.displayTitle("Maitre du jeu Positionnez vos obstacle");
            m_display.refreshDisplay();
            System.out.print("Entrez la position d'un obstacle (ex: A5) ou 'fin' : ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("FIN")) {
                break;
            }

            int[] pos = retrieveGridPosition(input);
            if (checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
                m_donjonGrid[pos[0]][pos[1]] = " # ";
            }
        }
    }

    private void playerPosition() {
        Scanner scanner = new Scanner(System.in);

        for (Entity playerName : m_Entities.keySet()) {
            boolean positionOk = false;
            while (!positionOk) {
                m_display.displayTitle("Maître du jeu - Positionnez vos Joueurs");
                m_display.refreshDisplay();

                System.out.print("Entrez la position du joueur " + playerName + " (ex: A5) : ");
                String input = scanner.nextLine().trim().toUpperCase();

                int[] pos = retrieveGridPosition(input);
                if (pos == null) {
                    System.out.println("Format invalide. Veuillez réessayer.");
                    continue;
                }

                if (checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
                    m_donjonGrid[pos[0]][pos[1]] = " P ";
                    m_Entities.replace(playerName, new int[]{pos[0], pos[1]});
                    positionOk = true;
                } else {
                    System.out.println("Position déjà occupée. Veuillez réessayer.");
                }
            }
        }
    }




    private void monsterCreation() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            m_display.displayTitle("Maître du jeu : Créez vos Monstres");
            m_display.refreshDisplay();
            System.out.print("Entrez la race du monstre ou 'fin' : ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("FIN")) break;

            String monsterSpecie = input;
            int hp = promptInt(scanner, "Vie");
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
            int[] pos = retrieveGridPosition(position);
            if (checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
                Monster newMonster = new Monster(hp, strength, dex, speed, monsterSpecie, id, atckRange, dice);
                m_donjonGrid[pos[0]][pos[1]] = " M ";
                m_Entities.put(newMonster, new int[]{pos[0], pos[1]});
            } else {
                System.out.println("Position invalide. Monstre non placé.");
            }
        }
    }

    private void equipmentPosition() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            m_display.displayTitle("Maître du jeu : Positionnez les équipements");
            m_display.refreshDisplay();
            System.out.print("Placez un nouvelle élement ou 'fin' : ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("FIN")) break;

            int type = promptInt(scanner, "[0] Armure ; [1] Armes (ex:1)");
            while (type > 1 || type < 0) {
                type = promptInt(scanner, "[0] Armure ; [1] Armes (ex:1)");
            }

            Equipment newEquipment = createEquipment(scanner, type);

            if (newEquipment != null) {
                System.out.print("Entrez la position de l'équipement (ex: A5) : ");
                String position = scanner.nextLine().trim().toUpperCase();
                int[] pos = retrieveGridPosition(position);
                if (checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
                    m_Equipments.put(newEquipment, pos);
                    m_donjonGrid[pos[0]][pos[1]] = " E ";
                }
            }
        }
    }

    private Equipment createEquipment(Scanner scanner, int type) {
        Equipment newEquipment = null;
        if (type == 0) {
            System.out.println("Création d'une Armure : ");
            int armorType = promptInt(scanner,
                    "[0] Côtte de mailles ; [1] Demi-plate ; [2] Armure d'écailles ; [3] Harnois (ex:2)");
            while (armorType > 3 || armorType < 0) {
                armorType = promptInt(scanner,
                        "[0] Côtte de mailles ; [1] Demi-plate ; [2] Armure d'écailles ; [3] Harnois (ex:2)");
            }

            newEquipment = switch (armorType) {
                case 0 -> new ChainMail();
                case 1 -> new HalfPlate();
                case 2 -> new ScaleMail();
                case 3 -> new Plate();
                default -> newEquipment;
            };
        } else {
            System.out.println("Création d'une Arme : ");
            int weaponType = promptInt(scanner,
                    "[0] Arbalète ; [1] Bâton ; [2] Masse d'armes ; [3] Épée longue ; " +
                            "[4] Rapière ; [5] Fronde ; [6] Arc court (ex:2)");
            while (weaponType > 6 || weaponType < 0) {
                weaponType = promptInt(scanner,
                        "[0] Arbalète ; [1] Bâton ; [2] Masse d'armes ; [3] Épée longue ; " +
                                "[4] Rapière ; [5] Fronde ; [6] Arc court (ex:2)");
            }

            newEquipment = switch (weaponType) {
                case 0 -> new Crossbow();
                case 1 -> new Quarterstaff();
                case 2 -> new Mace();
                case 3 -> new Longsword();
                case 4 -> new Rapier();
                case 5 -> new Sling();
                case 6 -> new Shortbow();
                default -> newEquipment;
            };
        }
        return newEquipment;
    }

    private void promptContext() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Début du donjon...\nSaisissez le contexte : ");
        scanner.nextLine();
    }

    private void initiativeInit(){
        Dice initiativeDice = new Dice(1,20);
        Scanner scan = new Scanner(System.in);
        for(Entity entity : m_Entities.keySet()){
            m_display.displayTitle(entity.toString()+" : Faite votre jetté de dées : ");
            scan.nextLine();
            int lancer = initiativeDice.roll()[0];
            System.out.println("Vous avez tirez un "+lancer);
            entity.addInitiative(lancer);
            System.out.println("Vous avez donc maintenant "+entity.getInitiative()+" d'initiative");
        }
    }


    public void mooveEntity(Entity entity){
        int maxMoove = entity.getSpeed();
        Scanner scan = new Scanner(System.in);
        int mooveSpeed = promptInt(scan,"Combien de case voulez vous vous déplacer : ");
        while(maxMoove< mooveSpeed){
           mooveSpeed=  promptInt(scan,"Combien de case voulez vous vous déplacer : ");
        };
        int direction = promptInt(scan,"Vers oû [0] ↑ [1] ↓ [2] → [3] ← [4] ↗ [5] ↘ [6] ↙ [7] ↖ : ");
        while(direction>7||direction<0){
            direction = promptInt(scan,"Vers oû [0] ↑ [1] ↓ [2] → [3] ← [4] ↗ [5] ↘ [6] ↙ [7] ↖ : ");
        };

        int[] mooveFactor = switch (direction) {
            case 0 -> new int[]{0,mooveSpeed};
            case 1 -> new int[]{0,-mooveSpeed};
            case 2 -> new int[]{mooveSpeed,0};
            case 3 -> new int[]{-mooveSpeed,0};
            case 4 -> new int[]{mooveSpeed,mooveSpeed};
            case 5 -> new int[]{mooveSpeed,-mooveSpeed};
            case 6 -> new int[]{-mooveSpeed,-mooveSpeed};
            case 7 -> new int[]{-mooveSpeed,mooveSpeed};
            default -> new  int[]{0,0};
        };
        int[] newPos = new int[]{m_Entities.get(entity)[0]+mooveFactor[0],m_Entities.get(entity)[1]+mooveFactor[1]};
        m_Entities.replace(entity,newPos);
    }



    public void nextTurn() {
        m_turn++;
        m_display.refreshDisplay();
    }

    // Getters pour DonjonDisplay
    public int getDonjonSize() { return m_donjonSize; }
    public String[][] getDonjonGrid() { return m_donjonGrid; }
    public int getTurn() { return m_turn; }
    public HashMap<Entity, int[]> getEntities() { return m_Entities; }
}