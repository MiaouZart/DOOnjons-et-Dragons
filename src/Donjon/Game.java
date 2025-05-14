package Donjon;

import Entity.Entity;
import Entity.Personnage.Personnage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static Donjon.Donjon.promptInt;

public class Game {

    private Donjon m_donjon;
    private HashMap<Entity,int[]> m_entities;
    private ArrayList<Entity> m_playerOrder;


    public Game() {
        Scanner scanner = new Scanner(System.in);
        int nbPlayer = promptInt(scanner, "Nombre de personnages");
        m_entities = new HashMap<>();
        m_playerOrder = new ArrayList<Entity>();
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
            Entity maxEntie = null;
            for(Entity entitie : m_entities.keySet()) {
                if (m_playerOrder.contains(entitie)) {
                    continue;
                }
                if(entitie.getInitiative()>maxInit){
                    maxEntie = entitie;
                    maxInit = entitie.getInitiative();
                }
            }
            m_playerOrder.add(maxEntie);
        }
    }


    private void setUp(){
        m_donjon.setupDonjon();
        retrievePlayerOrder();
        game();
    }

    private void game(){
        for(Entity entity : m_entities.keySet()){
            m_donjon.moveEntity(entity);
        }
    }

    private void playerTurn(){

    }
    private void monsterTurn(){

    };





}
