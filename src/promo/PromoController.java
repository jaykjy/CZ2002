package promo;

import base.BaseController;
import database.SerializeDB;
import database.exceptions.FailReadException;
import database.exceptions.FailWriteException;
import exception.EmptyFieldException;
import exception.NegativeNumberException;
import menu.Menu;
import menu.MenuController;
import menu.exception.MenuNotFound;
import promo.exception.MenuAlreadyExistsException;
import promo.exception.NoMenuException;
import promo.exception.PromoNotFoundException;
import utility.InputValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the methods to create, update, remove, get and save promotion items. 
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class PromoController extends BaseController {

	/**
	 * Constructor to prevent its instantiation.
	 */
    private PromoController() {};

    /**
     * Creates a promotion item and saves it in the promotion file.
     * 
     * @param name Name of the promotion item.
     * @param description Description of the promotion item.
     * @param price Price of the promotion item.
     * @throws EmptyFieldException If the user leaves the name of the promotion item blank.
     * @throws NegativeNumberException If the user enters a negative number for the price of the item.
     * @throws FailReadException If the promotion file cannot be read.
     * @throws FailWriteException If the created promotion item cannot be written into the promotion file.
     */
    public static void createPromo(String name, String description, double price)
        throws EmptyFieldException, NegativeNumberException, FailReadException, FailWriteException {

        ArrayList<Promo> promoItems = retrievePromo();

        // Checks if promo name is empty. Throws EmptyFieldException if true
        if (InputValidator.isEmptyString(name)) {
            throw new EmptyFieldException("Promotion name cannot be empty.");
        }

        // Checks if promo price is negative. Throws NegativeNumberException if true
        if (InputValidator.isNegativeNumber(price)) {
            throw new NegativeNumberException("Promotion price cannot be negative.");
        }

        int newPromoId = SerializeDB.generateId(promoItems);
        Promo newPromoItem = new Promo(newPromoId, name, description, price); // Create new promo item
        promoItems.add(newPromoItem);

        savePromoToFile(promoItems);
    }

    /**
     * Updates a promotion item and saves it in the promotion file.
     * 
     * @param promoId ID of the promotion item.
     * @param name Name of the promotion item.
     * @param description Description of the promotion item.
     * @param price Price of the promotion item.
     * @throws EmptyFieldException If the user leaves the name of the promotion item blank.
     * @throws NegativeNumberException If the user enters a negative number for the price of the item.
     * @throws NumberFormatException If the user enters an invalid input for the price of the promotion item.
     * @throws PromoNotFoundException If the promotion item does not exist.
     * @throws FailReadException If the promotion file cannot be read. 
     * @throws FailWriteException If the promotion item cannot be written into the promotion file.
     */
    public static void updatePromoInfo(int promoId, String name, String description, String price)
        throws EmptyFieldException, NegativeNumberException, NumberFormatException,
        PromoNotFoundException, FailReadException, FailWriteException {

        // Retrieve from file
        ArrayList<Promo> promoItems = retrievePromo();
        Promo promoToBeUpdated = SerializeDB.findById(promoItems, promoId);

        if (promoToBeUpdated == null) {
            throw new PromoNotFoundException();
        }

        // If the entered input is the SKIP keyword, retain the original value
        String newPromoName = isSkipUpdate(name) ? promoToBeUpdated.getName() : name;
        String newPromoDescription = isSkipUpdate(description)
            ? promoToBeUpdated.getDescription() : description;
        double newPromoPrice = isSkipUpdate(price) ? promoToBeUpdated.getPrice() : Double.parseDouble(price);

        // Checks if promotion name is empty. Throw EmptyFieldException if true
        if (InputValidator.isEmptyString(newPromoName)) {
            throw new EmptyFieldException("Promotion name cannot be empty.");
        }

        // Checks if promotion price is negative. Throw NegativeNumberException if true
        if (InputValidator.isNegativeNumber(newPromoPrice)) {
            throw new NegativeNumberException("Promotion price cannot be negative.");
        }

        // Perform updates
        promoToBeUpdated.setName(newPromoName);
        promoToBeUpdated.setDescription(newPromoDescription);
        promoToBeUpdated.setPrice(newPromoPrice);

        savePromoToFile(promoItems);
    }

    /**
     * Adds menu items into the promotion item and saves the promotion file.
     * 
     * @param promoId ID of the promotion item.
     * @param menuId ID of the menu item.
     * @throws MenuNotFound If the menu item does not exist.
     * @throws MenuAlreadyExistsException If the menu item already exists in the promotion item.
     * @throws FailReadException If the promotion or menu files cannot be read.
     * @throws PromoNotFoundException If the promotion item does not exist.
     * @throws FailWriteException If the promotion item cannot be written into the promotion file.
     */
    public static void addMenu(int promoId, int menuId)
        throws MenuNotFound, MenuAlreadyExistsException, FailReadException, PromoNotFoundException,
        FailWriteException {

        ArrayList<Promo> promoItems = retrievePromo();
        Promo promoToBeUpdated = SerializeDB.findById(promoItems, promoId);

        if (promoToBeUpdated == null) {
            throw new PromoNotFoundException();
        }

        ArrayList<Menu> menuItems = MenuController.retrieveMenu();
        Menu menuToBeAdded = SerializeDB.findById(menuItems, menuId);

        if (menuToBeAdded == null) {
            throw new MenuNotFound();
        }

        if (isMenuInPromo(promoToBeUpdated, menuToBeAdded)) {
            throw new MenuAlreadyExistsException();
        }

        promoToBeUpdated.addMenu(menuToBeAdded);
        savePromoToFile(promoItems);
    }

    /**
     * Removes menu items from the promotion item and saves the promotion file.
     * 
     * @param promoId ID of the promotion item.
     * @param menuId ID of the menu item.
     * @throws NoMenuException If the menu item does not exist in the promotion item.
     * @throws FailReadException If the promotion file cannot be read.
     * @throws FailWriteException If the promotion item cannot be written into the promotion file.
     * @throws PromoNotFoundException If the promotion item does not exist.
     */
    public static void removeMenu(int promoId, int menuId)
        throws NoMenuException, FailReadException, FailWriteException, PromoNotFoundException {

        ArrayList<Promo> promoItems = retrievePromo();
        Promo promoToBeUpdated = SerializeDB.findById(promoItems, promoId);

        if (promoToBeUpdated == null) {
            throw new PromoNotFoundException();
        }

        ArrayList<Menu> promoMenuItems = promoToBeUpdated.getMenuItems();
        Menu menuToBeDeleted = SerializeDB.findById(promoMenuItems, menuId);

        if (menuToBeDeleted == null) {
            throw new NoMenuException();
        }

        promoToBeUpdated.removeMenu(menuToBeDeleted);

        savePromoToFile(promoItems);
    }

    /**
     * Removes a promotion item and saves the promotion file.
     * 
     * @param promoId ID of the promotion item.
     * @throws PromoNotFoundException If the promotion item does not exist.
     * @throws FailReadException If the promotion file cannot be read.
     * @throws FailWriteException If the promotion item cannot be written into the promotion file.
     */
    public static void removePromo(int promoId)
        throws PromoNotFoundException, FailReadException, FailWriteException {
        ArrayList<Promo> promoItems = retrievePromo();

        Promo promotionToBeDeleted = SerializeDB.findById(promoItems, promoId);

        if (promotionToBeDeleted == null) {
            throw new PromoNotFoundException();
        }

        promoItems.remove(promotionToBeDeleted);
        savePromoToFile(promoItems);
    }

    /**
     * Checks if the menu item exists in the promotion item.
     * 
     * @param promo Promotion item to be checked.
     * @param menu Menu item to be found.
     * @return True if the menu item exists in the promotion item. Else, false.
     */
    public static boolean isMenuInPromo(Promo promo, Menu menu) {
        ArrayList<Menu> menuItems = promo.getMenuItems();

        for (Menu m: menuItems) {
            if (m.getId() == menu.getId()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets an ArrayList of promotion items.
     * 
     * @return ArrayList of promotion items.
     * @throws FailReadException If the promotion file cannot be read.
     */
    public static ArrayList<Promo> retrievePromo() throws FailReadException {
        List list = SerializeDB.readSerializedObject("promo.dat");
        return list == null ? new ArrayList<>() : (ArrayList) list;
    }

    /**
     * Saves changes made to the promotion items in the promotion file.
     * 
     * @param promoItems ArrayList of promotion items.
     * @throws FailWriteException If a promotion item cannot be written into the promotion file.
     */
    private static void savePromoToFile(ArrayList<Promo> promoItems) throws FailWriteException {
        SerializeDB.writeSerializeObject("promo.dat", promoItems);
    }
}
