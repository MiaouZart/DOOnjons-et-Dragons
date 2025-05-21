package donjon;

import dice.Dice;
import entity.Entity;

import java.util.*;

public class GameMaster {

    private HashMap<Entity, int[]> m_entityHashMapyCopy ;
    private Donjon m_donjon;

    public GameMaster(HashMap<Entity, int[]> entityHashMapyCopy,Donjon donjon){
        m_entityHashMapyCopy = entityHashMapyCopy;
        m_donjon = donjon;
    }


    public void say(String phrase){
        System.out.println("Maitre du jeux - "+phrase);
    }

    public void gameMasterTurn(){
        say("c'est votre tour : ");
        say("Choisisez votre action à réaliser");
        List<String> actions = Arrays.asList(
                "déplacer un monstre ou un personnage",
                "infliger des dégâts à un joueur ou un monstre",
                "ajouter des obstacles dans le donjon"
        );

        for(int i =0;i<actions.size();i++){
            System.out.println("["+i+"] "+actions.get(i));
        }
        Scanner scan = new Scanner(System.in);

        int res = Display.promptInt(scan,"Choisisser votre action",0,actions.size());
        switch (res){
            case 0:
                changeEntityPos();
                break;
            case 1:

                break;
        }

    }

    public void displayPlayer(){
        int  i = 0;
        ArrayList<Entity> entities = (ArrayList<Entity>)m_entityHashMapyCopy.keySet();
        for(Entity entity : entities){
            System.out.println("["+i+"] "+entity);
            i++;
        }
    }

    public void changeEntityPos(){
        Scanner scan = new Scanner(System.in);
        displayPlayer();
        say("Entrez l'indice du joueur à déplacer");
        ArrayList<Entity> entities = (ArrayList<Entity>)m_entityHashMapyCopy.keySet();
        int res = Display.promptInt(scan,"",0,m_entityHashMapyCopy.size());
        Entity choosed = entities.get(res);
        m_donjon.moveEntity(choosed);
    }

    public void inflicDamage(){
        Scanner scan = new Scanner(System.in);
        displayPlayer();
        say("Entrez l'indice du joueur à infliger des damage");
        ArrayList<Entity> entities = (ArrayList<Entity>)m_entityHashMapyCopy.keySet();
        int res = Display.promptInt(scan,"",0,m_entityHashMapyCopy.size());
        Entity choosed = entities.get(res);
        int nbFace = Display.promptInt(scan,"Nombre de face  ");
        int nbRoll = Display.promptInt(scan,"Nombre de lancer ");
        System.out.print("Lancer vos dées : (n'importe quelle touche) :");
        scan.nextLine();
        int roll = Dice.sumUp(new Dice(nbRoll,nbFace).roll());
        System.out.println("Vous allez alors infligez "+roll+" dégats a " +choosed);

        choosed.takeDamage(roll);
    }



}
