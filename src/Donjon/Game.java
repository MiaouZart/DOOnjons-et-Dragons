package Donjon;

import Entity.Entity;
import Entity.Personnage.Personnage;

import java.util.HashMap;
import java.util.Scanner;

public class Game {

    private Donjon m_donjon;
    private HashMap<Entity,int[]> m_Entities;




    public Game(){
        System.out.print("Nombre de personnages : ");
        Scanner scan = new Scanner(System.in);
        int nbPlayer = scan.nextInt();
        m_Entities = new HashMap<>();
        for (int i = 0; i < nbPlayer; i++) {
            System.out.println("Création du personnage " + (i + 1));
            Personnage p = CharacterCreator.Create();
            m_Entities.put(p, new int[]{0, 0});
        }

        System.out.print("Taille de la grille : ");
        int gridSize = scan.nextInt();
        m_donjon = new Donjon(gridSize,m_Entities);
    }

    private void setUp(){

    }

    private void game(){

    }

    private void playerTurn(){

    }
    private void monsterTurn(){

    };





}
