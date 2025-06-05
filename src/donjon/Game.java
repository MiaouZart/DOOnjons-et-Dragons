package donjon;

import donjon.defaultDonjon.Default1;
import donjon.defaultDonjon.Default2;
import donjon.defaultDonjon.Default3;
import entity.Entity;
import entity.EnumEntity;
import entity.personnage.Personnage;
import equipment.Equipment;
import equipment.armor.Armor;
import equipment.weapon.Weapon;
import printer.StandardOut;

import java.util.*;

import static donjon.Display.promptChoice;
import static donjon.Display.promptInt;

public class Game {
    private Donjon m_donjon;
    private HashMap<Entity,int[]> m_entities;
    private ArrayList<Entity> m_playerOrder;
    private final GameMaster m_gameMaster;
    private final StandardOut m_output;

    public Game() {
        m_playerOrder = new ArrayList<>();
        m_entities = new HashMap<>();
        m_output = new printer.SystemOut();
        Display.init(m_output);
        m_output.outLn("Tapez 0-1-2 si vous voulez utilisez un donjon par défaut ou 3 si création Manuelle");
        int choice = -1;
        while (choice < 0 || choice > 3) {
            choice = promptInt("Choix");
        }

        if (choice == 0) {
            Default1 default1 = new Default1(m_entities);
            m_donjon = default1.getDonjon();
            retrievePlayerOrder();
        } else if (choice == 1) {
            Default2 default2 = new Default2(m_entities);
            m_donjon = default2.getDonjon();
            retrievePlayerOrder();
        } else if (choice == 2) {
            Default3 default3 = new Default3(m_entities);
            m_donjon = default3.getDonjon();
            retrievePlayerOrder();
        } else if (choice == 3) {
            int nbPlayer = promptInt( "Nombre de personnages", 1, 10);
            for (int i = 0; i < nbPlayer; i++) {
                m_output.outLn("Création du personnage " + (i + 1));
                Personnage p = CharacterCreator.create(m_output);
                m_entities.put(p, new int[]{0, 0});
            }

            int size = 0;
            while (size < 15 || size > 25) {
                m_output.out("Taille de la grille (comprise entre 15 et 25) : ");
                size = Integer.parseInt(m_output.in());
            }

            m_donjon = new Donjon(size, m_entities);
            setUp();
        }

        m_gameMaster = new GameMaster(m_donjon.m_entities, m_donjon,m_output);
        game();
    }

    private void retrievePlayerOrder() {
        while (m_playerOrder.size() != m_entities.size()) {
            int maxInit = -1;
            Entity maxEntity = null;
            for (Entity entity : m_entities.keySet()) {
                if (m_playerOrder.contains(entity)) continue;
                if (entity.getInitiative() > maxInit) {
                    maxEntity = entity;
                    maxInit = entity.getInitiative();
                }
            }
            m_playerOrder.add(maxEntity);
        }
    }

    private void setUp() {
        m_donjon.setupDonjon();
        retrievePlayerOrder();
    }

    private void game() {
        while (!m_donjon.getLoose() && !m_donjon.getWin()) {
            for (Entity entity : m_playerOrder) {
                if (m_donjon.getLoose() || m_donjon.getWin()) break;
                m_donjon.m_display.refreshDisplay();
                entityTurn(entity);
                m_gameMaster.gameMasterTurn();
            }
            m_donjon.nextTurn();
        }

        m_output.outLn(m_donjon.getLoose() ? "\033[31mVous avez perdu\033[0m" : "\033[32mVous avez gagné\033[0m");
    }

    private void entityTurn(Entity entity) {
        int health = entity.getHp();
        Armor armor = null;
        Weapon weapon = null;
        Equipment[] inventory = null;
        int strength = entity.getStrength();
        int dex = entity.getDex();
        int speed = entity.getSpeed();

        ArrayList<String> actions = new ArrayList<>(Arrays.asList(
                "attaquer",
                "se déplacer",
                "s'équiper",
                "commenter action",
                "maître du jeu commente"
        ));

        if (entity.getType() == EnumEntity.PERSONNAGE) {
            Personnage personnage = ((Personnage) entity);
            armor = personnage.getArmor();
            weapon = personnage.getWeapon();
            inventory = personnage.getInventory();
            if (personnage.getSpells().length > 0) {
                actions.add("utiliser un sort");
            }
        }

        int nbAction = 0;
        while (nbAction < 3) {
            m_donjon.m_display.refreshDisplay();
            displayEntityInfo(entity, health, armor, weapon, inventory, strength, dex, speed, nbAction);

            ArrayList<String> availableActions = getAvailableActions(entity, actions, weapon);
            int choix = promptChoice(availableActions, true);

            if (choix == -1) break;

            handleAction(availableActions.get(choix), entity, inventory);
            nbAction++;
        }

        m_output.outLn("Fin du tour de " + entity + ".");
    }

    private void displayEntityInfo(Entity entity, int health, Armor armor, Weapon weapon,
                                   Equipment[] inventory, int strength, int dex, int speed, int nbAction) {
        m_output.outLn(entity.toString());
        m_output.outLn("\tVie : \033[1m" + health + "\033[0m");
        m_output.outLn("\tArmure : \033[1m" + (armor != null ? armor : "(aucune)") + "\033[0m");
        m_output.outLn("\tArme : \033[1m" + (weapon != null ? weapon : "(aucune)") + "\033[0m");
        m_output.out("\tInventaire : ");
        m_output.outLn((inventory != null ? ((Personnage) entity).getInventoryString() : "(aucun)"));
        m_output.outLn("\tForce : \033[1m" + strength + "\033[0m");
        m_output.outLn("\tDextérité : \033[1m" + dex + "\033[0m");
        m_output.outLn("\tVitesse : \033[1m" + speed + "\033[0m");

        m_output.outLn("\n--- Tour de " + entity + " ---");
        m_output.outLn("Vous avez " + "\033[1m" + (3 - nbAction) + "\033[0m" + " actions restantes.");
    }

    private ArrayList<String> getAvailableActions(Entity entity, ArrayList<String> actions, Weapon weapon) {
        ArrayList<String> availableActions = new ArrayList<>(actions);
        if (entity.getType() == EnumEntity.MONSTER) {
            availableActions.remove("commenter action");
        }
        if (weapon == null && (entity.getType() == EnumEntity.PERSONNAGE)) {
            availableActions.remove("attaquer");
        }
        return availableActions;
    }

    private void handleAction(String action, Entity entity, Equipment[] inventory) {
        switch (action) {
            case "attaquer":
                m_donjon.playerAttack(entity);
                break;
            case "se déplacer":
                m_donjon.moveEntity(entity);
                break;
            case "s'équiper":
                handleEquipment(entity, inventory);
                break;
            case "commenter action":
            case "maître du jeu commente":
                commenter(entity);
                break;
            case "utiliser un sort":
                handleSpell((Personnage) entity);
                break;
            default:
                m_output.outLn("Choix inconnu.");
        }
    }

    private void handleEquipment(Entity entity, Equipment[] inventory) {
        if (inventory == null || inventory.length == 0) {
            m_output.outLn("Votre inventaire est vide.");
            return;
        }
        m_output.outLn("Inventaire :");
        ArrayList<String> inventoryItems = new ArrayList<>();
        for (Equipment item : inventory) {
            inventoryItems.add(item.toString());
        }
        int itemChoice = promptChoice(inventoryItems, false);
        m_output.outLn("Vous avez choisi d'équiper : " + inventory[itemChoice]);
        ((Personnage) entity).equip(inventory[itemChoice]);
    }

    private void handleSpell(Personnage personnage) {
        ArrayList<String> spells = new ArrayList<>();
        for (int i = 0; i < personnage.getSpells().length; i++) {
            spells.add(personnage.getSpells()[i].toString());
        }
        int spellChoice = promptChoice(spells, false);
        personnage.getSpells()[spellChoice].spell(m_entities, m_donjon.m_donjonGrid);
    }

    public void commenter(Entity entity) {
        m_output.outLn("Saisissez votre commentaire : ");
        entity.say(m_output);
    }
}