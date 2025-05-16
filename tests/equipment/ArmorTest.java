package equipment;

import equipment.armor.Armor;
import equipment.armor.types.*;

class ArmorTest {
    @org.junit.jupiter.api.Test
    void ChainMailTest() {
        Armor armor = new ChainMail();
        assert armor.getName().equals("Cotte de Mailles");
        assert armor.getClassVal() == 11;
        assert armor.getSpeedModifier() == 4;
    }

    @org.junit.jupiter.api.Test
    void HalfPlateTest() {
        Armor armor = new HalfPlate();
        assert armor.getName().equals("Demi-Plate");
        assert armor.getClassVal() == 10;
        assert armor.getSpeedModifier() == 0;
    }

    @org.junit.jupiter.api.Test
    void PlateTest() {
        Armor armor = new Plate();
        assert armor.getName().equals("Harnois");
        assert armor.getClassVal() == 12;
        assert armor.getSpeedModifier() == 4;
    }

    @org.junit.jupiter.api.Test
    void ScaleMailTest() {
        Armor armor = new ScaleMail();
        assert armor.getName().equals("Armure d'Écailles");
        assert armor.getClassVal() == 9;
        assert armor.getSpeedModifier() == 0;
    }
}