package donjon;

import entity.Entity;
import entity.personnage.charclass.types.Warrior;
import entity.personnage.Personnage;
import entity.personnage.race.types.Elf;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DonjonTest {

    @Test
    void display() {
        Personnage pierre = new Personnage("pierre",new Elf(),new Warrior());
        ArrayList<Entity> players = new ArrayList<>();
        players.add(pierre);
    }
}