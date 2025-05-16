package equipment;

import dice.Dice;
import equipment.weapon.types.*;
import equipment.weapon.Weapon;
import org.junit.jupiter.api.Test;

class WeaponTest {
    @Test
    void Crossbow() {
        Weapon weapon = new Crossbow();
        assert weapon.getName().equals("Arbalète");
        assert weapon.getDamageDice().equals(new Dice(1, 8));
        assert weapon.getRange() == 16;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }

    @Test
    void Longsword() {
        Weapon weapon = new Longsword();
        assert weapon.getName().equals("Épée Longue");
        assert weapon.getDamageDice().equals(new Dice(1, 8));
        assert weapon.getRange() == 1;
        assert weapon.getSpeedModifier() == 2;
        assert weapon.getStrengthModifier() == 4;
    }

    @Test
    void Mace() {
        Weapon weapon = new Mace();
        assert weapon.getName().equals("Masse d'Armes");
        assert weapon.getDamageDice().equals(new Dice(1, 6));
        assert weapon.getRange() == 1;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }

    @Test
    void Quarterstaff() {
        Weapon weapon = new Quarterstaff();
        assert weapon.getName().equals("Bâton");
        assert weapon.getDamageDice().equals(new Dice(1, 6));
        assert weapon.getRange() == 1;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }

    @Test
    void Rapier() {
        Weapon weapon = new Rapier();
        assert weapon.getName().equals("Rapière");
        assert weapon.getDamageDice().equals(new Dice(1, 8));
        assert weapon.getRange() == 1;
        assert weapon.getSpeedModifier() == 2;
        assert weapon.getStrengthModifier() == 4;
    }

    @Test
    void Shortbow() {
        Weapon weapon = new Shortbow();
        assert weapon.getName().equals("Arc Court");
        assert weapon.getDamageDice().equals(new Dice(1, 6));
        assert weapon.getRange() == 16;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }

    @Test
    void Sling() {
        Weapon weapon = new Sling();
        assert weapon.getName().equals("Fronde");
        assert weapon.getDamageDice().equals(new Dice(1, 4));
        assert weapon.getRange() == 6;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }
}