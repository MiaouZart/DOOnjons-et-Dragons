package spell;

import entity.Entity;
import entity.personnage.Personnage;
import entity.personnage.charclass.types.*;
import entity.personnage.race.types.*;
import org.junit.jupiter.api.Test;
import spell.type.*;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

public class SpellTest {
    @Test
    public void Heal() {/*
        System.setIn(new ByteArrayInputStream("0".getBytes()));
        Spell spell = new Heal();
        Entity testEntity0 = new Personnage("Pierre", new Dwarf(), new Wizard());
        Entity testEntity1 = new Personnage("Bruno", new Elf(), new Rogue());
        HashMap<Entity, int[]> map = new HashMap<>();
        map.put(testEntity0, new int[]{0, 0});
        map.put(testEntity1, new int[]{0, 1});
        testEntity0.takeDamage(10);
        spell.spell(map);
        assert testEntity0.getHp() <= testEntity0.getMaxHp();
        assert testEntity0.getHp() > (testEntity0.getMaxHp() - 10);*/
    }

    @Test
    public void BoogieWoogie() {/*
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        Spell spell = new BoogieWoogie();
        Entity testEntity0 = new Personnage("Pierre", new Dwarf(), new Wizard());
        Entity testEntity1 = new Personnage("Bruno", new Elf(), new Rogue());
        HashMap<Entity, int[]> entities = new HashMap<>();
        entities.put(testEntity0, new int[]{0, 0});
        entities.put(testEntity1, new int[]{0, 5});
        spell.spell(entities);
        for (int i = 0; i< entities.size(); i++) {
            Entity e = (Entity) entities.keySet().toArray()[i];
            System.out.printf("[%d]\t\t[%d,%d] %s\n", i, entities.get(e)[0], entities.get(e)[1], e);
        }*/
    }

    @Test
    public void MagicWeapon() {/*
        System.setIn(new ByteArrayInputStream("0".getBytes()));
        Spell spell = new MagicWeapon();
        Personnage testEntity0 = new Personnage("Pierre", new Dwarf(), new Wizard());
        HashMap<Entity, int[]> entities = new HashMap<>();
        entities.put(testEntity0, new int[]{0, 0});
        spell.spell(entities);
        testEntity0.equip(testEntity0.getInventory()[0]);
        assert testEntity0.attackBonus() == 1;*/
    }

    @Test
    public void HasSpells() {
        Personnage testEntity0 = new Personnage("Pierre", new Dwarf(), new Wizard());
        assert testEntity0.getSpells().length == 3;
        assert testEntity0.getSpells()[0].equals(new MagicWeapon());
        assert testEntity0.getSpells()[1].equals(new BoogieWoogie());
        assert testEntity0.getSpells()[2].equals(new Heal());

        testEntity0 = new Personnage("Pierre II", new Dwarf(), new Clerc());
        assert testEntity0.getSpells().length == 1;
        assert testEntity0.getSpells()[0].equals(new Heal());

        testEntity0 = new Personnage("Pierre III", new Dwarf(), new Warrior());
        assert testEntity0.getSpells().length == 0;

        testEntity0 = new Personnage("Pierre IV", new Dwarf(), new Rogue());
        assert testEntity0.getSpells().length == 0;
    }
}
