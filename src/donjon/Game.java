package donjon;

import donjon.defaultDonjon.Default1;
import donjon.defaultDonjon.Default2;
import donjon.defaultDonjon.Default3;
import entity.Entity;
import entity.EnumEntity;
import entity.personnage.Personnage;
import equipment.Equipment;
import equipment.armor.Armor;
import equipment.weapon.Weapon;

import java.util.*;

import static donjon.Display.promptChoice;
import static donjon.Display.promptInt;

public class Game {
    private Donjon m_donjon;
    private HashMap<Entity,int[]> m_entities;
    private ArrayList<Entity> m_playerOrder;
    private final GameMaster m_gameMaster;

    /**
     * Demande le choix du donjon et lance le jeu.
     */
    public Game() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Tapez 0-1-2 si vous voulez utilisez un donjon par défault ou 3 si création Manuelle");
        int choice = -1;
        while (choice<0||choice>3){
            choice = promptInt(scanner,"Choix ");
        }
        m_playerOrder = new ArrayList<>();
        m_entities = new HashMap<>();
        if(choice==0){
            Default1 default1 = new Default1(m_entities);
            m_donjon = default1.getDonjon();
            retrievePlayerOrder();
        }
        if(choice==1){
            Default2 default2 = new Default2(m_entities);
            m_donjon = default2.getDonjon();
            retrievePlayerOrder();
        }
        if(choice==2){
            Default3 default3 = new Default3(m_entities);
            m_donjon = default3.getDonjon();
            retrievePlayerOrder();
        }

        if(choice ==3) {
            int nbPlayer = promptInt(scanner, "Nombre de personnages", 1, 10);
            m_entities = new HashMap<>();
            m_playerOrder = new ArrayList<>();
            for (int i = 0; i < nbPlayer; i++) {
                System.out.println("Création du personnage " + (i + 1));
                Personnage p = CharacterCreator.create();
                m_entities.put(p, new int[]{0, 0});
            }

            int size = 0;
            while (size < 15 || size > 25) {
                System.out.print("Taille de la grille (comprise entre 15 et 25) : ");
                size = scanner.nextInt();
            }

            m_donjon = new Donjon(size, m_entities);
            setUp();
        }
        m_gameMaster = new GameMaster(m_donjon.m_entities,m_donjon);
        game();
    }

    /**
     * Récupère l'ordre du joueur, dans m_playerOrder.
     */
    private void retrievePlayerOrder(){
        while (m_playerOrder.size() != m_entities.size()){
            int maxInit = -1;
            Entity maxEntity = null;
            for(Entity entity : m_entities.keySet()) {
                if (m_playerOrder.contains(entity)) {
                    continue;
                }
                if(entity.getInitiative()>maxInit){
                    maxEntity = entity;
                    maxInit = entity.getInitiative();
                }
            }
            m_playerOrder.add(maxEntity);
        }
    }

    /**
     * Initialise le jeu.
     */
    private void setUp(){
        m_donjon.setupDonjon();
        retrievePlayerOrder();
    }

    /**
     * Gestion de la boucle des tours.
     */
    private void game() {
        while (!m_donjon.getLoose() && !m_donjon.getWin()) {
            for (Entity entity : m_playerOrder) {
                if (m_donjon.getLoose() || m_donjon.getWin())
                    break;
                m_donjon.m_display.refreshDisplay();
                entityTurn(entity);
                m_gameMaster.gameMasterTurn();
            }
            m_donjon.nextTurn();
        }

        System.out.println(m_donjon.getLoose() ? "Vous avez perdu" : "Vous avez gagné");
    }

    /**
     * Tour d'une entité.
     * @param entity Entité à qui faire le tour.
     */
    private void entityTurn(Entity entity) {
        int health = entity.getHp();
        Armor armor = null;
        Weapon weapon = null;
        Equipment[] inventory = null;
        int strength = entity.getStrength();
        int dex = entity.getDex();
        int speed = entity.getSpeed();

        ArrayList<String> actions = new ArrayList<>(Arrays.asList(
                "attaquer",
                "se déplacer",
                "s'équiper",
                "commenter action",
                "maître du jeu commente"
        ));

        if (entity.getType() == EnumEntity.PERSONNAGE) {
            Personnage personnage = ((Personnage) entity);
            armor = personnage.getArmor();
            weapon = personnage.getWeapon();
            inventory = personnage.getInventory();
            if (personnage.getSpells().length > 0) {
                actions.add("utiliser un sort");
            }
        }

        int nbAction = 0;
        while (nbAction < 3) {
            m_donjon.m_display.refreshDisplay();
            System.out.println(entity);
            System.out.println("    Vie : " + health);
            System.out.println("    Armure : " + (armor != null ? armor : "(aucune)"));
            System.out.println("    Arme : " + (weapon != null ? weapon : "(aucune)"));
            System.out.print("    Inventaire : ");
            System.out.println(inventory != null ? ((Personnage) entity).getInventoryString() : "(aucun)");
            System.out.println("    Force : " + strength);
            System.out.println("    Dextérité : " + dex);
            System.out.println("    Vitesse : " + speed);

            System.out.println("\n--- Tour de " + entity + " ---");
            System.out.println("Vous avez " + (3 - nbAction) + " actions restantes.");

            // Création d'une copie des actions disponibles
            ArrayList<String> availableActions = new ArrayList<>(actions);

            // Si c'est un monstre, on retire l'option de commentaire
            if (entity.getType() == EnumEntity.MONSTER) {
                availableActions.remove("commenter action");
            }

            // Si pas d'arme, on retire l'option d'attaque
            if (weapon == null && (entity.getType() == EnumEntity.PERSONNAGE)) {
                availableActions.remove("attaquer");
            }

            // Utilisation de promptChoice
            int choix = promptChoice(availableActions, true);

            if (choix == -1) { // L'utilisateur a tapé 'fin'
                break;
            }

            String selectedAction = availableActions.get(choix);

            switch (selectedAction) {
                case "attaquer":
                    m_donjon.playerAttack(entity);
                    break;
                case "se déplacer":
                    m_donjon.moveEntity(entity);
                    break;
                case "s'équiper":
                    if (inventory == null || inventory.length == 0) {
                        System.out.println("Votre inventaire est vide.");
                        break;
                    }
                    System.out.println("Inventaire :");
//                    System.out.println(((Personnage)entity).getInventoryString());
                    ArrayList<String> inventoryItems = new ArrayList<>();
                    for (Equipment item : inventory) {
                        inventoryItems.add(item.toString());
                    }
                    int itemChoice = promptChoice(inventoryItems, false);
                    System.out.println("Vous avez choisi d'équiper : " + inventory[itemChoice]);
                    ((Personnage)entity).equip(inventory[itemChoice]);
                    break;
                case "commenter action":
                    commenter(entity);
                    break;
                case "maître du jeu commente":
                    commenter(entity); // À changer par la suite
                    break;
                case "utiliser un sort":
                    Personnage personnage = ((Personnage) entity);
                    ArrayList<String> spells = new ArrayList<>();
                    for (int i = 0; i < personnage.getSpells().length; i++) {
                        spells.add(personnage.getSpells()[i].toString());
                    }
                    int spellChoice = promptChoice(spells, false);
                    personnage.getSpells()[spellChoice].spell(m_entities);
                    break;
                default:
                    System.out.println("Choix inconnu.");
            }

            nbAction++;
        }

        System.out.println("Fin du tour de " + entity + ".");
    }

    /**
     * Commente l'action d'une entité.
     * @param entity Entité à qui commenter l'action.
     */
    public void commenter(Entity entity){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Saisissez votre commentaire : ");
        entity.say(scanner);
    }
}
