package Race;

import Entity.Personnage.Race.Dwarf;
import Entity.Personnage.Race.Elf;
import Entity.Personnage.Race.Halfelin;
import Entity.Personnage.Race.Human;

import static org.junit.jupiter.api.Assertions.*;

class RaceTest {

    @org.junit.jupiter.api.Test
    void testHuman() {
        Human human = new Human();
        assertEquals(2,human.getSpeed());
        assertEquals(2,human.getDex());
        assertEquals(2,human.getInitiative());
        assertEquals(2,human.getStrenght());
    }

    @org.junit.jupiter.api.Test
    void testDwarf() {
        Dwarf dwarf = new Dwarf();
        assertEquals(0,dwarf.getSpeed());
        assertEquals(0,dwarf.getDex());
        assertEquals(0,dwarf.getInitiative());
        assertEquals(6,dwarf.getStrenght());
    }

    @org.junit.jupiter.api.Test
    void testElf(){
        Elf elf = new Elf();
        assertEquals(0,elf.getSpeed());
        assertEquals(6,elf.getDex());
        assertEquals(0,elf.getInitiative());
        assertEquals(0,elf.getStrenght());
    }

    @org.junit.jupiter.api.Test
    void testHalfelin() {
        Halfelin halfelin = new Halfelin();
        assertEquals(2,halfelin.getSpeed());
        assertEquals(4,halfelin.getDex());
        assertEquals(0,halfelin.getInitiative());
        assertEquals(0,halfelin.getStrenght());
    }
}