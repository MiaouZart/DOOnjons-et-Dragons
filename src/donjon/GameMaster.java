package donjon;

import dice.Dice;
import entity.Entity;

import java.util.*;

import static donjon.Display.promptChoice;

public class GameMaster {

    private final HashMap<Entity, int[]> m_entityHashMapCopy;
    private final Donjon m_donjon;
    private final Scanner scan;

    public GameMaster(HashMap<Entity, int[]> entityHashMapCopy, Donjon donjon) {
        this.m_entityHashMapCopy = entityHashMapCopy;
        this.m_donjon = donjon;
        this.scan = new Scanner(System.in);
    }

    public void say(String phrase) {
        System.out.println("Maître du jeu - " + phrase);
    }

    public void gameMasterTurn() {
        say("C'est votre tour :");
        say("Choisissez l'action à réaliser");

        ArrayList<String> actions = new ArrayList<>(Arrays.asList(
                "Déplacer un monstre ou un personnage",
                "Infliger des dégâts à un joueur ou un monstre",
                "Ajouter des obstacles dans le donjon"
        ));

        int choice = promptChoice(actions, true);

        if (choice == -1) return; // L'utilisateur a choisi 'fin'

        switch (choice) {
            case 0:
                changeEntityPos();
                break;
            case 1:
                inflictDamage();
                break;
            case 2:
                addObstacle();
                break;
            default:
                say("Action inconnue.");
        }
    }

    private void displayEntities() {
        List<Entity> entities = new ArrayList<>(m_entityHashMapCopy.keySet());
        ArrayList<String> entityNames = new ArrayList<>();
        for (Entity entity : entities) {
            entityNames.add(entity.toString() + " (PV: " + entity.getHp() + ")");
        }
        promptChoice(entityNames, false); // Affiche seulement la liste
    }

    public void changeEntityPos() {
        say("Choisissez l'entité à déplacer :");
        List<Entity> entities = new ArrayList<>(m_entityHashMapCopy.keySet());
        ArrayList<String> entityOptions = new ArrayList<>();

        for (Entity entity : entities) {
            entityOptions.add(entity.toString() + " - Position actuelle: "
                    + Arrays.toString(m_entityHashMapCopy.get(entity)));
        }

        int choice = promptChoice(entityOptions, true);
        if (choice == -1) return;

        Entity selected = entities.get(choice);
        m_donjon.EntityPosition(selected);
    }

    public void inflictDamage() {
        say("Choisissez la cible pour les dégâts :");
        List<Entity> entities = new ArrayList<>(m_entityHashMapCopy.keySet());
        int targetIndex = promptChoice(
                new ArrayList<>(entities.stream().map(Entity::toString).toList()),
                true
        );

        if (targetIndex == -1) return;
        Entity selected = entities.get(targetIndex);

        say("Configuration des dés :");
        ArrayList<String> diceOptions = new ArrayList<>(List.of(
                "1d4", "1d6", "1d8", "1d10",
                "1d12", "1d20", "2d6", "3d6"
        ));

        int diceChoice = promptChoice(diceOptions, true);
        if (diceChoice == -1) return;

        String[] diceParts = diceOptions.get(diceChoice).split("d");
        int nbRolls = Integer.parseInt(diceParts[0]);
        int nbFaces = Integer.parseInt(diceParts[1]);

        say("Lancement des dés... (appuyez sur Entrée)");
        scan.nextLine();
        int totalDamage = Dice.sumUp(new Dice(nbRolls, nbFaces).roll());

        say("Vous infligez " + totalDamage + " dégâts à " + selected);
        selected.takeDamage(totalDamage);
    }

    public void addObstacle() {
        m_donjon.createObstacle();
    }
}