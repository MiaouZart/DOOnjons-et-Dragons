package donjon;

import dice.Dice;
import entity.Entity;
import printer.StandardOut;

import java.util.*;

import static donjon.Display.promptChoice;

public class GameMaster {
    private final HashMap<Entity, int[]> m_entityHashMapCopy;
    private final Donjon m_donjon;
    private final StandardOut m_output;

    /**
     * Constructeur de la classe du Maître du jeu.
     * @param entityHashMapCopy Map des entités - positions.
     * @param donjon Donjon à utiliser.
     * @param output Système de sortie standard à utiliser.
     */
    public GameMaster(HashMap<Entity, int[]> entityHashMapCopy, Donjon donjon, StandardOut output) {
        this.m_entityHashMapCopy = entityHashMapCopy;
        this.m_donjon = donjon;
        this.m_output = output;
    }

    /**
     * Faire le MJ dire une phrase.
     * @param phrase Phrase à dire.
     */
    public void say(String phrase) {
        m_output.outLn("\033[95mMaître du jeu\033[0m - " + phrase);
    }

    /**
     * Tour du maître du jeu.
     */
    public void gameMasterTurn() {
        say("C'est votre tour :");
        say("Choisissez l'action à réaliser");

        ArrayList<String> actions = new ArrayList<>(Arrays.asList(
                "Déplacer un monstre ou un personnage",
                "Infliger des dégâts à un joueur ou un monstre",
                "Ajouter des obstacles dans le donjon"
        ));

        int choice = promptChoice(actions, true);

        if (choice == -1) return;

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

    /**
     * Affiche la liste des entités.
     */
    private void displayEntities() {
        List<Entity> entities = new ArrayList<>(m_entityHashMapCopy.keySet());
        ArrayList<String> entityNames = new ArrayList<>();
        for (Entity entity : entities) {
            entityNames.add(entity.toString() + " (PV: " + entity.getHp() + ")");
        }
        promptChoice(entityNames, false);
    }

    /**
     * Demande où déplacer une des entités.
     */
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
        m_donjon.entityPosition(selected);
    }

    /**
     * Permet au MJ d'affliger des dégâts arbitraires à une entité.
     */
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
        m_output.in();
        int totalDamage = Dice.sumUp(new Dice(nbRolls, nbFaces).roll());

        say("Vous infligez " + totalDamage + " dégâts à " + selected);
        selected.takeDamage(totalDamage);
    }

    /**
     * Méthode pour demander au MJ où créer un obstacle.
     */
    public void addObstacle() {
        m_donjon.createObstacle();
    }
}