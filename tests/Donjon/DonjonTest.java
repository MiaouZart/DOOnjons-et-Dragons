package Donjon;

import Entity.Entity;
import Entity.Personnage.CharClass.Types.Warrior;
import Entity.Personnage.Personnage;
import Entity.Personnage.Race.Types.Elf;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DonjonTest {

    @Test
    void display() {
        Personnage pierre = new Personnage("pierre",new Elf(),new Warrior());
        ArrayList<Entity> players = new ArrayList<Entity>();
        players.add(pierre);
    }
}