package Entity.Personnage;

import Entity.Entity;
import Entity.Personnage.CharClass.CharClass;
import Entity.Personnage.Race.Race;
import Equipment.Armor.Armor;
import Equipment.Equipment;
import Equipment.Weapon.Weapon;

public class Personnage extends Entity {
    private final String m_name;
    private final Race m_race;
    private final CharClass m_charClass;
    private final Equipment[] m_inventory;
    private Weapon m_weapon;
    private Armor m_armor;

    public Personnage(int X, int Y, String nom, Race race, CharClass charClass) {
        super(X, Y, 0, 0, 0, 0, 0);
    }

    public Personnage(String nom, Race race, CharClass charClass) {
        super(0, 0, 0, 0, 0);
    }
}
