package test.Monster;

import Dice.Dice;
import Entity.Entity;
import Entity.Monster.Monster;
import org.junit.jupiter.api.Test;

class MonsterTest {

    @Test
    void setX_Y() {
        Entity monster = new Monster(0, 0, 0, 0, 0, "Test", 0, 0, new Dice(1, 4));
        assert monster.getPosX() == -1;
        assert monster.getPosY() == -1;
        monster.setX_Y(10, 10);
        assert monster.getPosX() == 10;
        assert monster.getPosY() == 10;
    }
}