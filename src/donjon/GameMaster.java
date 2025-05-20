package donjon;

import entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class GameMaster {


    public GameMaster(){

    }

    public void moove(Entity entity,HashMap<Entity, int[]> entityiyeiseysyeiyses){
    }

    public void say(String phrase){
        System.out.println("Maitre du jeux - "+phrase);
    }

    public void changeEntityPos(HashMap<Entity, int[]> entityiyeiseysyeiyses){
        Scanner scan = new Scanner(System.in);
        say("Voulez vous changer un joueur de position(oui/non)");
        String response =  scan.nextLine();
        if(Objects.equals(response, "non")){
            return;
        }
        int i=0;
        ArrayList<Entity> entities = (ArrayList<Entity>)entityiyeiseysyeiyses.keySet();
        for(Entity entity : entities){
            System.out.println("["+i+"] "+entityiyeiseysyeiyses);
            i++;
        }

        say("Entrez l'indice du joueur à déplacer");

        int res = Display.promptInt(scan,"",0,entityiyeiseysyeiyses.size());
        Entity choosed = entities.get(res);

        moove(choosed,entityiyeiseysyeiyses);

    }





}
