package donjon;

import dice.Dice;
import entity.Entity;

import java.util.*;

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

        List<String> actions = Arrays.asList(
                "Déplacer un monstre ou un personnage",
                "Infliger des dégâts à un joueur ou un monstre",
                "Ajouter des obstacles dans le donjon"
        );

        for (int i = 0; i < actions.size(); i++) {
            System.out.println("[" + i + "] " + actions.get(i));
        }

        int res = Display.promptInt(scan, "Choisissez votre action", 0, actions.size());

        switch (res) {
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

    public void displayPlayer() {
        List<Entity> entities = new ArrayList<>(m_entityHashMapCopy.keySet());
        for (int i = 0; i < entities.size(); i++) {
            System.out.println("[" + i + "] " + entities.get(i));
        }
    }

    public void changeEntityPos() {
        displayPlayer();
        say("Entrez l'indice du joueur à déplacer :");

        List<Entity> entities = new ArrayList<>(m_entityHashMapCopy.keySet());
        int res = Display.promptInt(scan, "", 0, m_entityHashMapCopy.size());

        Entity selected = entities.get(res);
        m_donjon.EntityPosition(selected);
    }

    public void inflictDamage() {
        displayPlayer();
        say("Entrez l'indice du joueur à qui infliger des dégâts :");

        List<Entity> entities = new ArrayList<>(m_entityHashMapCopy.keySet());
        int res = Display.promptInt(scan, "", 0, m_entityHashMapCopy.size());
        Entity selected = entities.get(res);

        int nbFaces = Display.promptInt(scan, "Nombre de faces du dé :");
        int nbRolls = Display.promptInt(scan, "Nombre de lancers :");

        System.out.print("Lancez vos dés (appuyez sur Entrée) : ");
        scan.nextLine(); // Pause utilisateur
        int totalDamage = Dice.sumUp(new Dice(nbRolls, nbFaces).roll());

        System.out.println("Vous infligez " + totalDamage + " dégâts à " + selected);
        selected.takeDamage(totalDamage);
    }

    public void addObstacle() {
        m_donjon.createObstacle();
    }
}
