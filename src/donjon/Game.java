package donjon;

import entity.Entity;
import entity.EnumEntity;
import entity.personnage.Personnage;
import equipment.Equipment;
import equipment.armor.Armor;
import equipment.weapon.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static donjon.Display.promptInt;

public class Game {

    private Donjon m_donjon;
    private HashMap<Entity,int[]> m_entities;
    private ArrayList<Entity> m_playerOrder;


    public Game() {
        Scanner scanner = new Scanner(System.in);
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

    private void game(){
        for(Entity entity : m_entities.keySet()){
            entityTurn((entity));
        }
    }

    private  void entityTurn(Entity entity){

        int health = entity.getHp();
        Armor armor = null;
        Weapon weapon = null;
        Equipment[] inventory = null;
        int strength = entity.getStrength();
        int dex = entity.getDex();
        int speed = entity.getSpeed();
        if(entity.getType() == EnumEntity.PERSONNAGE) {
            armor = ((Personnage) entity).getArmor();
            weapon = ((Personnage) entity).getWeapon();
            inventory = ((Personnage) entity).getInventory();
        }
        System.out.println(entity);
        System.out.println("    Vie : "+health);
        System.out.println("    Armure : " + (armor != null ? armor : "(aucune)"));
        System.out.println("    Arme : " + (weapon != null ? weapon : "(aucune)"));
        System.out.print("    Inventaire : ");
        if (inventory != null){
           System.out.println(((Personnage) entity).getInventoryString());
        }else {
            System.out.println("(aucun)");
        }
        System.out.println("    Force : "+strength);
        System.out.println("    Dextérité : "+dex);
        System.out.println("    Vitesse : "+speed);



        ArrayList<Integer> PossibleChoice = new ArrayList<Integer>();
        int nbAction = 0;
        Scanner scan = new Scanner(System.in);
        while (nbAction <= 3) {
            System.out.println("Vous avez " + (3-nbAction) + " actions restantes.");
            System.out.print("voulez-vous vous faire une action (ou 'fin' pour terminer) : ");

            String input = scan.nextLine();
            if (input.equalsIgnoreCase("fin")) {
                break;
            }
        }
    }





}
