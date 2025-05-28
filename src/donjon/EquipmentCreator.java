package donjon;

import equipment.armor.types.ChainMail;
import equipment.armor.types.HalfPlate;
import equipment.armor.types.Plate;
import equipment.armor.types.ScaleMail;
import equipment.Equipment;
import equipment.weapon.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static donjon.Display.*;

public class EquipmentCreator {

    /**
     * Constructeur du créateur d'équipement <i>(bulk)</i>.
     * @param display Classe Display à utiliser.
     * @param grid Grille du donjon à utiliser.
     * @param equipments Map des équipements - positions.
     */
    public static void create(Display display, String[][] grid, HashMap<Equipment, int[]> equipments) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            display.displayTitle("Maître du jeu : Ajouter un équipement");
            ArrayList<String> options = new ArrayList<>();
            options.add("Continuer");
            options.add("Terminer");

            int choice = promptChoice(options, true);
            if (choice == -1 || choice == 1) break;

            Equipment newEquipment = createEquipment(scanner);
            positionEquipment(newEquipment, grid, equipments, scanner);
        }
    }

    /**
     * Demande une position d'équipement, et la place sur la grille d'équipement avec un sprite.
     * @param equipment Équipement à placer.
     * @param grid Grille à afficher l'équipement.
     * @param equipments Map des équipements - positions.
     * @param scanner Scanner à utiliser pour les affichages consoles.
     */
    private static void positionEquipment(Equipment equipment, String[][] grid, HashMap<Equipment, int[]> equipments, Scanner scanner) {
        System.out.print("Entrez la position de l'équipement (ex: A5) : ");
        String position = scanner.nextLine().trim().toUpperCase();
        int[] pos = retrieveGridPosition(position);
        if (checkEmptyCase(pos[0], pos[1], grid, grid[0].length)) {
            equipments.put(equipment, pos);
            grid[pos[0]][pos[1]] = " E ";
        }
    }

    /**
     * Méthode pour demander au MJ de créer un équipement.
     * @param scanner Scanner à utiliser pour l'affichage console.
     * @return Nouvel équipement crée.
     */
    private static Equipment createEquipment(Scanner scanner) {
        ArrayList<String> typeOptions = new ArrayList<>();
        typeOptions.add("Armure");
        typeOptions.add("Arme");

        int type = promptChoice(typeOptions, false);

        if (type == 0) {
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
        } else {
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
}
