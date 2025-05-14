package Donjon;

import Entity.Entity;
import Entity.Personnage.Personnage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {

    private Donjon m_donjon;
    private HashMap<Entity,int[]> m_Entities;
    private ArrayList<Entity> m_playerOrder;


    public Game(){
        System.out.print("Nombre de personnages : ");
        Scanner scan = new Scanner(System.in);
        int nbPlayer = scan.nextInt();
        m_Entities = new HashMap<>();
        m_playerOrder = new ArrayList<Entity>();
        for (int i = 0; i < nbPlayer; i++) {
            System.out.println("Création du personnage " + (i + 1));
            Personnage p = CharacterCreator.Create();
            m_Entities.put(p, new int[]{0, 0});
        }
        System.out.print("Taille de la grille : ");
        int gridSize = scan.nextInt();
        m_donjon = new Donjon(gridSize,m_Entities);
        setUp();
    }

    private void retrievPlayerOrder(){
        while (m_playerOrder.size()!=m_Entities.size()){
            int maxInit = -1;
            Entity maxEntie = null;
            for(Entity entitie : m_Entities.keySet()) {
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
        retrievPlayerOrder();
        game();
    }

    private void game(){

    }

    private void playerTurn(){

    }
    private void monsterTurn(){

    };





}
