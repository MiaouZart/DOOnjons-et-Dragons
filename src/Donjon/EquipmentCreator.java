package Donjon;

import Equipment.Armor.Types.ChainMail;
import Equipment.Armor.Types.HalfPlate;
import Equipment.Armor.Types.Plate;
import Equipment.Armor.Types.ScaleMail;
import Equipment.Equipment;
import Equipment.Weapon.Types.*;

import java.util.HashMap;
import java.util.Scanner;

import static Donjon.Donjon.*;

public class EquipmentCreator {

    public static void create(DonjonDisplay display, String[][] grid, HashMap<Equipment, int[]> equipments) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            display.displayTitle("Maître du jeu : Ajouter un équipement");
            System.out.print("Continuez ou 'fin' : ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("FIN")) break;

            Equipment newEquipment = createEquipment(scanner);
            positionEquipment(newEquipment, grid, equipments, scanner);
        }
    }

    private static void positionEquipment(Equipment equipment, String[][] grid, HashMap<Equipment, int[]> equipments, Scanner scanner) {
            System.out.print("Entrez la position de l'équipement (ex: A5) : ");
            String position = scanner.nextLine().trim().toUpperCase();
            int[] pos = retrieveGridPosition(position);
            if (checkEmptyCase(pos[0], pos[1], grid, grid[0].length)) {
                equipments.put(equipment, pos);
                grid[pos[0]][pos[1]] = " E ";
            }
    }

    private static Equipment createEquipment(Scanner scanner) {
        int type = -1;
        while (type > 1 || type < 0) {
            type = promptInt(scanner, "[0] Armure ; [1] Armes (ex:1)");
        }

        if (type == 0) {
            System.out.println("Création d'une Armure : ");
            int armorType = -1;
            while (armorType > 3 || armorType < 0) {
                armorType = promptInt(scanner,
                        "[0] Côtte de mailles ; [1] Demi-plate ; [2] Armure d'écailles ; [3] Harnois (ex:2)");
            }

            return switch (armorType) {
                case 0 -> new ChainMail();
                case 1 -> new HalfPlate();
                case 2 -> new ScaleMail();
                case 3 -> new Plate();
                default -> throw new IllegalStateException("Unexpected value: " + armorType);
            };
        } else {
            System.out.println("Création d'une Arme : ");
            int weaponType = -1;
            while (weaponType > 6 || weaponType < 0) {
                weaponType = promptInt(scanner,
                        "[0] Arbalète ; [1] Bâton ; [2] Masse d'armes ; [3] Épée longue ; " +
                                "[4] Rapière ; [5] Fronde ; [6] Arc court (ex:2)");
            }

            return switch (weaponType) {
                case 0 -> new Crossbow();
                case 1 -> new Quarterstaff();
                case 2 -> new Mace();
                case 3 -> new Longsword();
                case 4 -> new Rapier();
                case 5 -> new Sling();
                case 6 -> new Shortbow();
                default -> throw new IllegalStateException("Unexpected value: " + weaponType);
            };
        }
    }

}
