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

import static donjon.Display.promptInt;

public class Game {
    private Donjon m_donjon;
    private HashMap<Entity,int[]> m_entities;
    private ArrayList<Entity> m_playerOrder;


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
            game();
        }
        if(choice==1){
            Default2 default2 = new Default2(m_entities);
            m_donjon = default2.getDonjon();
            retrievePlayerOrder();
            game();
        }
        if(choice==2){
            Default3 default3 = new Default3(m_entities);
            m_donjon = default3.getDonjon();
            retrievePlayerOrder();
            game();
        }

        if(choice ==3) {
            int nbPlayer = promptInt(scanner, "Nombre de personnages");
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
    }

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


    private void setUp(){
        m_donjon.setupDonjon();
        retrievePlayerOrder();
        game();
    }

    private void game() {
        while (!m_donjon.getLoose() && !m_donjon.getWin()) {
            for (Entity entity : m_playerOrder) {
                if (m_donjon.getLoose() || m_donjon.getWin()) {
                    break;
                }
                m_donjon.m_display.refreshDisplay();
                entityTurn(entity);
            }
        }

        if(m_donjon.getLoose()){
            System.out.println("Vous avez perdu");
        }else{
            System.out.println("Vous avez gagnez");
        }

    }


    private void entityTurn(Entity entity) {
        int health = entity.getHp();
        Armor armor = null;
        Weapon weapon = null;
        Equipment[] inventory = null;
        int strength = entity.getStrength();
        int dex = entity.getDex();
        int speed = entity.getSpeed();

        if (entity.getType() == EnumEntity.PERSONNAGE) {
            armor = ((Personnage) entity).getArmor();
            weapon = ((Personnage) entity).getWeapon();
            inventory = ((Personnage) entity).getInventory();
        }

        // Affichage stats
        List<String> actions = Arrays.asList(
                "attaquer",
                "se déplacer",
                "s'équiper",
                "commenter action",
                "maître du jeu commente"
        );

        List<Integer> possibleChoices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        int nbAction = 0;
        Scanner scan = new Scanner(System.in);
        if(entity.getType() == EnumEntity.MONSTER){
            possibleChoices.remove(3);//on enleve car le monstre ne peut pas s'équiper
        }

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

            possibleChoices = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
            System.out.println("\n--- Tour de " + entity + " ---");
            System.out.println("Vous avez " + (3 - nbAction) + " actions restantes.");

            for (int i = 0; i < actions.size(); i++) {
                    System.out.println("[" + i + "] " + actions.get(i));
            }
            if (entity.getType() == EnumEntity.PERSONNAGE) {
                weapon = ((Personnage) entity).getWeapon();
                inventory = ((Personnage) entity).getInventory();
            }

            if(weapon ==null&&(entity.getType() == EnumEntity.PERSONNAGE)){
                possibleChoices.remove(0);
            }

            System.out.print(entity + ", faire une aciton ou tapez 'fin' : ");
            String input = scan.nextLine();

            if (input.equalsIgnoreCase("fin")) {
                break;
            }

            int choix = -1;
            while (!possibleChoices.contains(choix)){
                choix = promptInt(scan,"Choisisez votre action ");
            }
            switch (choix) {
                case 0:
                    m_donjon.playerAttack(entity);
                    break;
                case 1:
                    m_donjon.moveEntity(entity);
                    break;
                case 2:
                    if (inventory == null || inventory.length == 0) {
                        System.out.println("Votre inventaire est vide.");
                        break;
                    }
                    int ivenChoix = -1;
                    System.out.println("Inventaire :");
                    System.out.println(((Personnage)entity).getInventoryString());
                    while (ivenChoix<0||ivenChoix>inventory.length){
                        ivenChoix = promptInt(scan,"Choisisez votre action ");
                    }
                    System.out.println("Vous avez choisi d'équiper : " + inventory[ivenChoix]);
                    ((Personnage)entity).equip(inventory[ivenChoix]);
                    break;
                case 3:
                    commenter(entity);
                    break;
                case 4:
                    commenter(entity);//à changer par la suite
                    break;
                default:
                    System.out.println("Choix inconnu.");
            }

            nbAction++;
        }

        System.out.println("Fin du tour de " + entity + ".");
    }



    public void commenter(Entity entity){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Saisissez votre commentaire : ");
        entity.say(scanner.nextLine());
    }






}
