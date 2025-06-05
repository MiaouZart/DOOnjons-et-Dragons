package donjon;

import dice.Dice;
import entity.Entity;
import entity.EnumEntity;
import entity.personnage.Personnage;
import equipment.Equipment;
import printer.StandardOut;

import java.util.*;

import static donjon.Display.equipmentChar;
import static donjon.Display.promptChoice;

public class Donjon {
    protected final int m_donjonSize;
    protected final String[][] m_donjonGrid;
    protected int m_turn = 0;
    protected int m_donjonNumber;
    protected boolean m_setup = false;
    protected final HashMap<int[], Equipment> m_equipments;
    protected final HashMap<Entity, int[]> m_entities;
    protected final Display m_display;
    protected boolean m_donjonWin = false;
    protected boolean m_donjonLoose = false;
    private int m_monsterNumber = 0;
    private int m_playerNumber = 0;

    public Donjon(int size, HashMap<Entity, int[]> players) {
        m_donjonSize = size;
        m_donjonGrid = new String[m_donjonSize][m_donjonSize];
        initializeGrid();
        m_equipments = new HashMap<>();
        m_entities = Objects.requireNonNullElseGet(players, HashMap::new);
        m_display = new Display(this, new printer.SystemOut());
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
        entityPosition();
        m_monsterNumber = MonsterCreator.bulkCreate(m_display, m_donjonGrid, m_entities);
        EquipmentCreator.create(m_display, m_donjonGrid, m_equipments);
        promptContext();
        initiativeInit();
        m_setup = true;
    }

    public void createObstacle() {
        ObstacleCreator.bulkCreate(m_display, m_donjonGrid);
    }

    private void entityPosition() {
        for (Entity playerName : m_entities.keySet()) {
            boolean positionOk = false;
            while (!positionOk) {
                m_display.displayTitle("\033[95mMaître du jeu\033[0m - Positionnez vos Joueurs");
                m_display.refreshDisplay();

                m_display.getOutput().out("Entrez la position du joueur " + playerName + " (ex: A5) : ");
                String input = m_display.getOutput().in().trim().toUpperCase();

                int[] pos = Display.retrieveGridPosition(input);
                if (Display.checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
                    m_donjonGrid[pos[0]][pos[1]] = " P ";
                    m_entities.replace(playerName, new int[]{pos[0], pos[1]});
                    positionOk = true;
                } else {
                    m_display.getOutput().outLn("Position déjà occupée. Veuillez réessayer.");
                }
            }
            m_playerNumber++;
        }
    }

    public void entityPosition(Entity entity) {
        boolean positionOk = false;
        while (!positionOk) {
            m_display.displayTitle("\033[95mMaître du jeu\033[0m - Positionnez vos Joueurs");
            m_display.refreshDisplay();

            m_display.getOutput().out("Entrez la position du joueur " + entity + " (ex: A5) : ");
            String input = m_display.getOutput().in().trim().toUpperCase();

            int[] pos = Display.retrieveGridPosition(input);

            if (Display.checkEmptyCase(pos[0], pos[1], m_donjonGrid, m_donjonSize)) {
                m_donjonGrid[m_entities.get(entity)[0]][m_entities.get(entity)[1]] = "  ";
                m_donjonGrid[pos[0]][pos[1]] = entity.getSprite();
                m_entities.replace(entity, new int[]{pos[0], pos[1]});
                positionOk = true;
            } else {
                m_display.getOutput().outLn("Position déjà occupée. Veuillez réessayer.");
            }
        }
        m_playerNumber++;
    }

    private void promptContext() {
        m_display.getOutput().outLn("Début du donjon...\nSaisissez le contexte : ");
        m_display.getOutput().in();
    }

    private void initiativeInit() {
        Dice initiativeDice = new Dice(1, 20);
        for (Entity entity : m_entities.keySet()) {
            m_display.displayTitle(entity.toString() + " : Faite votre jetté de dés : ");
            m_display.getOutput().in();
            int lancer = initiativeDice.roll()[0];
            m_display.getOutput().outLn("Vous avez tirez un " + lancer);
            entity.addInitiative(lancer);
            m_display.getOutput().outLn("Vous avez donc maintenant " + entity.getInitiative() + " d'initiative");
        }
    }

    public void moveEntity(Entity entity) {
        int remainingMove = entity.getSpeed();
        int moveSpeed = 1;

        ArrayList<String> directions = new ArrayList<>(Arrays.asList(
                "↑ Haut", "↓ Bas", "→ Droite", "← Gauche",
                "↗ Haut-Droite", "↘ Bas-Droite", "↙ Bas-Gauche", "↖ Haut-Gauche"
        ));

        while (remainingMove > 0) {
            m_display.refreshDisplay();
            m_display.getOutput().outLn("Vous avez " + remainingMove + " points de déplacement restants.");
            m_display.getOutput().out("Voulez-vous continuer ou taper 'fin' pour terminer : ");

            String input = m_display.getOutput().in();
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
                    !(Objects.equals(m_donjonGrid[newX][newY], " . ") || Objects.equals(m_donjonGrid[newX][newY], equipmentChar))) {
                m_display.getOutput().outLn("Vous n'avez pas le droit");
                continue;
            }

            if (m_equipments.containsKey(new int[]{newX, newY})) {
                if (entity.getType() == EnumEntity.PERSONNAGE) {
                    ((Personnage) entity).take(m_equipments.get(new int[]{newX, newY}));
                    m_equipments.remove(new int[]{newX, newY});
                }
            }

            m_donjonGrid[oldPos[0]][oldPos[1]] = " . ";
            m_donjonGrid[newX][newY] = entity.getSprite();
            m_entities.replace(entity, new int[]{newX, newY});
            remainingMove -= moveSpeed;
        }

        m_display.getOutput().outLn("Fin du déplacement.");
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
                continue;
            }
            int distance = distance(entity, enemy);
            if (distance <= range) {
                entitiesThatCanBeAttacked.add(enemy);
                attackableNames.add(enemy.toString());
            }
        }

        if (entitiesThatCanBeAttacked.isEmpty()) {
            m_display.getOutput().outLn("Personne à attaquer");
            return;
        }

        m_display.getOutput().outLn("Vous pouvez attaquer :");
        int cible = promptChoice(attackableNames, true);

        if (cible < 0 || cible >= entitiesThatCanBeAttacked.size()) {
            return;
        }

        Entity chose = entitiesThatCanBeAttacked.get(cible);
        boolean toucher = chose.getAttacked(entity);

        if (toucher) {
            m_display.getOutput().outLn("Bravo vous avez touché votre cible");
            if (chose.getDead()) {
                m_display.getOutput().outLn("Et Vous l'avez tuée");
                m_entities.remove(chose);
                checkWin();
            }
        }
    }

    public void nextTurn() {
        m_turn++;
        m_display.refreshDisplay();
    }

    private void checkWin() {
        if (m_playerNumber == 0) {
            m_donjonLoose = true;
        }
        if (m_monsterNumber == 0) {
            m_donjonWin = true;
        }
    }

    public boolean getWin() {
        return m_donjonWin;
    }

    public boolean getLoose() {
        return m_donjonLoose;
    }

    public int getDonjonSize() {
        return m_donjonSize;
    }

    public String[][] getDonjonGrid() {
        return m_donjonGrid;
    }

    public int getTurn() {
        return m_turn;
    }

    public HashMap<Entity, int[]> getEntities() {
        return m_entities;
    }
}