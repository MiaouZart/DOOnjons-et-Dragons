package donjon;

import equipment.armor.types.ChainMail;
import equipment.armor.types.HalfPlate;
import equipment.armor.types.Plate;
import equipment.armor.types.ScaleMail;
import equipment.Equipment;
import equipment.weapon.types.*;
import printer.StandardOut;

import java.util.ArrayList;
import java.util.HashMap;

import static donjon.Display.*;

public class EquipmentCreator {

    /**
     * Constructeur du créateur d'équipement <i>(bulk)</i>.
     * @param display Classe Display à utiliser.
     * @param grid Grille du donjon à utiliser.
     * @param equipments Map des équipements - positions.
     */
    public static void create(Display display, String[][] grid, HashMap<int[], Equipment> equipments) {
        StandardOut output = display.getOutput();

        while (true) {
            display.displayTitle("\033[95mMaître du jeu\033[0m : Ajouter un équipement");
            ArrayList<String> options = new ArrayList<>();
            options.add("Continuer");
            options.add("Terminer");

            int choice = display.promptChoice(options, true);
            if (choice == -1 || choice == 1) break;

            Equipment newEquipment = createEquipment(display);
            positionEquipment(display, newEquipment, grid, equipments);
        }
    }

    /**
     * Demande une position d'équipement, et la place sur la grille.
     */
    private static void positionEquipment(Display display, Equipment equipment,
                                          String[][] grid, HashMap<int[], Equipment> equipments) {
        StandardOut output = display.getOutput();
        output.out("Entrez la position de l'équipement (ex: A5) : ");
        String position = output.in().trim().toUpperCase();
        int[] pos = retrieveGridPosition(position);

        if (checkEmptyCase(pos[0], pos[1], grid, grid[0].length)) {
            equipments.put(pos, equipment);
            grid[pos[0]][pos[1]] = equipmentChar;
        }
    }

    /**
     * Méthode pour demander au MJ de créer un équipement.
     */
    private static Equipment createEquipment(Display display) {
        StandardOut output = display.getOutput();
        ArrayList<String> typeOptions = new ArrayList<>();
        typeOptions.add("Armure");
        typeOptions.add("Arme");

        int type = promptChoice(typeOptions, false);

        if (type == 0) {
            return createArmor(display);
        } else {
            return createWeapon(display);
        }
    }

    private static Equipment createArmor(Display display) {
        ArrayList<String> armorOptions = new ArrayList<>();
        armorOptions.add("Cotte de mailles");
        armorOptions.add("Demi-plate");
        armorOptions.add("Armure d'écailles");
        armorOptions.add("Harnois");

        int armorType = promptChoice(armorOptions, false);

        return switch (armorType) {
            case 0 -> new ChainMail();
            case 1 -> new HalfPlate();
            case 2 -> new ScaleMail();
            case 3 -> new Plate();
            default -> throw new IllegalStateException("Unexpected value: " + armorType);
        };
    }

    private static Equipment createWeapon(Display display) {
        ArrayList<String> weaponOptions = new ArrayList<>();
        weaponOptions.add("Arbalète");
        weaponOptions.add("Bâton");
        weaponOptions.add("Masse d'armes");
        weaponOptions.add("Épée longue");
        weaponOptions.add("Rapière");
        weaponOptions.add("Fronde");
        weaponOptions.add("Arc court");
        weaponOptions.add("Épée à deux mains");

        int weaponType = promptChoice(weaponOptions, false);

        return switch (weaponType) {
            case 0 -> new Crossbow();
            case 1 -> new Quarterstaff();
            case 2 -> new Mace();
            case 3 -> new Longsword();
            case 4 -> new Rapier();
            case 5 -> new Sling();
            case 6 -> new Shortbow();
            case 7 -> new Greatsword();
            default -> throw new IllegalStateException("Unexpected value: " + weaponType);
        };
    }
}