package donjon;

import dice.Dice;
import entity.Entity;
import entity.EnumEntity;
import equipment.Equipment;

import java.util.*;

import static donjon.Display.promptChoice;


public class Donjon {
    protected final int m_donjonSize;
    protected final String[][] m_donjonGrid;
    protected int m_turn = 0;
    protected int m_donjonNumber;
    protected boolean m_setup = false;
    protected final HashMap<Equipment, int[]> m_equipments;
    protected final HashMap<Entity, int[]> m_entities;
    protected final Display m_display;
    protected boolean m_donjonWin=false;
    protected boolean m_donjonLoose = false;
    private int m_monsterNumber=0;
    private int m_playerNumber=0;

    /**
     * Constructeur du Donjon.
     * @param size Taille de la grille (carré).
     * @param players Dictionnaire de position - joueurs.
     */
    public Donjon(int size, HashMap<Entity, int[]> players) {

        m_donjonSize = size;
        m_donjonGrid = new String[m_donjonSize][m_donjonSize];
        initializeGrid();
        m_equipments = new HashMap<>();

        m_entities = Objects.requireNonNullElseGet(players, HashMap::new);

        m_display = new Display(this);
    }

    /**
     * Initialise la grille.
     */
    private void initializeGrid() {
        for (int i = 0; i < m_donjonSize; i++)
            for (int j = 0; j < m_donjonSize; j++)
                m_donjonGrid[i][j] = " . ";
    }

    /**
     * Met en place le donjon.
     */
    public void setupDonjon() {
        ObstacleCreator.bulkCreate(m_display, m_donjonGrid);
        entityPosition();
        m_monsterNumber= MonsterCreator.bulkCreate(m_display, m_donjonGrid, m_entities);
        EquipmentCreator.create(m_display, m_donjonGrid, m_equipments);
        promptContext();
        initiativeInit();
        m_setup = true;
    }

    /**
     * Demande au MJ des emplacements pour créer un nombre d'obstacles à sa convenience.
     */
    public void createObstacle(){
        ObstacleCreator.bulkCreate(m_display, m_donjonGrid);
    }

    /**
     * Setup la position de chaque entités, par le MJ.
     */
    private void entityPosition() {
        Scanner scanner = new Scanner(System.in);

        for (Entity playerName : m_entities.keySet()) {
            boolean positionOk = false;
            while (!positionOk) {
                m_display.displayTitle("Maître du jeu - Positionnez vos Joueurs");
                m_display.refreshDisplay();

                System.out.print("Entrez la position du joueur " + playerName + " (ex: A5) : ");
                String input = scanner.nextLine().trim().toUpperCase();

                int[] pos = Display.retrieveGridPosition(input);
                if (Display.checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
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

    /**
     * Demande au MJ de déplacer une entité.
     * @param entity Entité à déplacer.
     */
    public void entityPosition(Entity entity) {
        Scanner scanner = new Scanner(System.in);
        boolean positionOk = false;
        while (!positionOk) {
            m_display.displayTitle("Maître du jeu - Positionnez vos Joueurs");
            m_display.refreshDisplay();

            System.out.print("Entrez la position du joueur " + entity + " (ex: A5) : ");
            String input = scanner.nextLine().trim().toUpperCase();

            int[] pos = Display.retrieveGridPosition(input);

            if (Display.checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
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

    /**
     * Demande au MJ de donner le contexte.
     */
    private void promptContext() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Début du donjon...\nSaisissez le contexte : ");
        scanner.nextLine();
    }

    /**
     * Toutes les entités font des jets d'initiative.
     */
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

    /**
     * Permet à une entité de se déplacer.
     * @param entity Entité qui peut se déplacer.
     */
    public void moveEntity(Entity entity) {
        int remainingMove = entity.getSpeed();
        int moveSpeed = 1;
        Scanner scan = new Scanner(System.in);

        ArrayList<String> directions = new ArrayList<>(Arrays.asList(
                "↑ Haut", "↓ Bas", "→ Droite", "← Gauche",
                "↗ Haut-Droite", "↘ Bas-Droite", "↙ Bas-Gauche", "↖ Haut-Gauche"
        ));

        while (remainingMove > 0) {
            m_display.refreshDisplay();
            System.out.println("Vous avez " + remainingMove + " points de déplacement restants.");
            System.out.print("Voulez-vous continuer ou taper 'fin' pour terminer : ");

            String input = scan.nextLine();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }

            int direction = promptChoice(directions, false);

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

            if (newX < 0 || newX > m_donjonSize || newY < 0 || newY > m_donjonSize ||
                    !(Objects.equals(m_donjonGrid[newX][newY], " . ") || Objects.equals(m_donjonGrid[newX][newY], " E "))) {
                System.out.println("Vous n'avez pas le droit");
                continue;
            }

            m_donjonGrid[oldPos[0]][oldPos[1]] = " . ";
            m_donjonGrid[newX][newY] = entity.getSprite();
            m_entities.replace(entity, new int[]{newX, newY});
            remainingMove -= moveSpeed;
        }

        System.out.println("Fin du déplacement.");
    }


    /**
     * Calcule la distance entre deux entités.
     * @param entity1 Première entité de l'équation
     * @param entity2 Seconde entité de l'équation
     * @return Distance des entités.
     */
    private int distance(Entity entity1, Entity entity2) {
        int x1 = m_entities.get(entity1)[0];
        int y1 = m_entities.get(entity1)[1];
        int x2 = m_entities.get(entity2)[0];
        int y2 = m_entities.get(entity2)[1];
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    /**
     * Permet à une entité d'en attaquer une autre.<br>
     * <i>Une entité ne peut pas attaquer ses alliés.</i>
     * @param entity Entité auteur de l'attaque.
     */
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
        int cible = promptChoice(attackableNames, true);

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

    /**
     * Passe au tour suivant.
     */
    public void nextTurn() {
        m_turn++;
        m_display.refreshDisplay();
    }

    /**
     * Vérifie l'état du donjon ; Si les joueurs ont perdu ou gagné.
     */
    private void checkWin(){
        if(m_playerNumber==0){
            m_donjonLoose=true;
        }
        if(m_monsterNumber==0){
            m_donjonWin=true;
        }
    }

    /**
     * Getter pour si le donjon est gagnant.
     * @return État du donjon, s'il a gagné.
     */
    public boolean getWin(){
        return m_donjonWin;
    }

    /**
     * Getter pour si le donjon est perdant.
     * @return État du donjon, s'il a perdu.
     */
    public boolean getLoose(){
        return m_donjonLoose;
    }

    /**
     * Getter taille du Donjon
     * @return Taille de la grille du Donjon.
     */
    public int getDonjonSize() { return m_donjonSize; }

    /**
     * Getter de la grille du Donjon.
     * @return Grille du Donjon.
     */
    public String[][] getDonjonGrid() { return m_donjonGrid; }

    /**
     * Getter du tour.
     * @return Numéro du tour.
     */
    public int getTurn() { return m_turn; }

    /**
     * Getter des entités.
     * @return Les entités et leur position.
     */
    public HashMap<Entity, int[]> getEntities() { return m_entities; }
}