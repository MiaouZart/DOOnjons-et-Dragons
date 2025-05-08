package test;

import Dice.Dice;
import Equipment.Weapon.Types.*;
import Equipment.Weapon.Weapon;

class WeaponTest {
    @org.junit.jupiter.api.Test
    void CrossbowTest() {
        Weapon weapon = new Crossbow();
        assert weapon.getName().equals("Arbalète");
        assert weapon.getDamageDice().equals(new Dice(1, 8));
        assert weapon.getRange() == 16;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }

    @org.junit.jupiter.api.Test
    void LongswordTest() {
        Weapon weapon = new Longsword();
        assert weapon.getName().equals("Épée Longue");
        assert weapon.getDamageDice().equals(new Dice(1, 8));
        assert weapon.getRange() == 1;
        assert weapon.getSpeedModifier() == 2;
        assert weapon.getStrengthModifier() == 4;
    }

    @org.junit.jupiter.api.Test
    void MaceTest() {
        Weapon weapon = new Mace();
        assert weapon.getName().equals("Masse d'Armes");
        assert weapon.getDamageDice().equals(new Dice(1, 6));
        assert weapon.getRange() == 1;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }

    @org.junit.jupiter.api.Test
    void QuarterstaffTest() {
        Weapon weapon = new Quarterstaff();
        assert weapon.getName().equals("Bâton");
        assert weapon.getDamageDice().equals(new Dice(1, 6));
        assert weapon.getRange() == 1;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }

    @org.junit.jupiter.api.Test
    void RapierTest() {
        Weapon weapon = new Rapier();
        assert weapon.getName().equals("Rapière");
        assert weapon.getDamageDice().equals(new Dice(1, 8));
        assert weapon.getRange() == 1;
        assert weapon.getSpeedModifier() == 2;
        assert weapon.getStrengthModifier() == 4;
    }

    @org.junit.jupiter.api.Test
    void ShortbowTest() {
        Weapon weapon = new Shortbow();
        assert weapon.getName().equals("Arc Court");
        assert weapon.getDamageDice().equals(new Dice(1, 6));
        assert weapon.getRange() == 16;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }

    @org.junit.jupiter.api.Test
    void SlingTest() {
        Weapon weapon = new Sling();
        assert weapon.getName().equals("Fronde");
        assert weapon.getDamageDice().equals(new Dice(1, 4));
        assert weapon.getRange() == 6;
        assert weapon.getSpeedModifier() == 0;
        assert weapon.getStrengthModifier() == 0;
    }
}