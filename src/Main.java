import Donjon.Donjon;
import Entity.Entity;
import Entity.Personnage.CharClass.Types.Warrior;
import Entity.Personnage.Personnage;
import Entity.Personnage.Race.Types.Elf;

import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        System.out.println("Bienvenue dans DOOnjon et Dragons");
        Personnage pierre = new Personnage("pierre",new Elf(),new Warrior());
        ArrayList<Entity> players = new ArrayList<Entity>();
        players.add(pierre);
        Donjon dn = new Donjon(25,players);
    }
}