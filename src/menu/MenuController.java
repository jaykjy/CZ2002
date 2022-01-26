package menu;

import base.BaseController;
import database.SerializeDB;
import database.exceptions.FailReadException;
import database.exceptions.FailWriteException;
import exception.EmptyFieldException;
import exception.NegativeNumberException;
import menu.exception.MenuInPromoException;
import menu.exception.MenuNotFound;
import promo.Promo;
import promo.PromoController;
import utility.InputValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the methods to create, update, remove, get and save menu items.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class MenuController extends BaseController {

	/**
	 * Constructor to prevent its instantiation.
	 */
    private MenuController() { super(); }; // Prevent the instantiation of MenuController

    /**
     * Creates a menu item and saves it in the menu file.
     * 
     * @param name Name of the menu item.
     * @param description Description of the menu item.
     * @param price Price of the menu item.
     * @param type Type of the menu item.
     * @throws EmptyFieldException If the user leaves the name of the menu item blank.
     * @throws NegativeNumberException If the user enters a negative number for the price of the item. 
     * @throws FailReadException If the menu file cannot be read.
     * @throws FailWriteException If the created menu item cannot be written into the menu file.
     */
    public static void createMenu(String name, String description, double price, MenuType type)
        throws EmptyFieldException, NegativeNumberException, FailReadException, FailWriteException {

        // Retrieve from file
        ArrayList<Menu> menuItems = retrieveMenu();

        // Checks if menu name is empty. Throws EmptyFieldException if true
        if (InputValidator.isEmptyString(name)) {
            throw new EmptyFieldException("Menu name cannot be empty.");
        }

        // Checks if menu price is negative. Throws NegativeNumberException if true
        if (InputValidator.isNegativeNumber(price)) {
            throw new NegativeNumberException("Menu price cannot be negative.");
        }

        int newMenuId = SerializeDB.generateId(menuItems);
        Menu newMenuItem = new Menu(newMenuId, name, description, price, type); // Create new menu item
        menuItems.add(newMenuItem);

        saveMenuToFile(menuItems);
    }

    /**
     * Updates a menu item and saves it in the menu file.
     * 
     * @param menuId ID of the menu item.
     * @param name Name of the menu item.
     * @param description Description of the menu item.
     * @param price Price of the menu item.
     * @param type Type of the menu item.
     * @throws EmptyFieldException If the user leaves the name of the menu item blank. 
     * @throws NegativeNumberException If the user enters a negative number for the price of the item.
     * @throws FailReadException If the menu file cannot be read.
     * @throws FailWriteException If the menu item cannot be written into the menu file
     * @throws MenuNotFound If the menu item does not exist.
     * @throws NumberFormatException If the user enters an invalid input for the price of the menu item.
     */
    public static void updateMenu(int menuId, String name, String description, String price, MenuType type)
        throws EmptyFieldException, NegativeNumberException, FailReadException, FailWriteException,
        MenuNotFound, NumberFormatException {

        // Retrieve from file
        ArrayList<Menu> menuItems = retrieveMenu();
        Menu menuToBeUpdated = SerializeDB.findById(menuItems, menuId);

        // If menu is not found, throw MenuNotFound exception
        if (menuToBeUpdated == null) {
            throw new MenuNotFound();
        }

        // If the entered input is the SKIP keyword, retain the original value
        String newMenuName = isSkipUpdate(name) ? menuToBeUpdated.getName() : name;
        String newMenuDescription = isSkipUpdate(description) ? menuToBeUpdated.getDescription() : description;
        double newMenuPrice = isSkipUpdate(price) ? menuToBeUpdated.getPrice() : Double.parseDouble(price);

        // Checks if menu name is empty. Throws EmptyFieldException if true
        if (InputValidator.isEmptyString(newMenuName)) {
            throw new EmptyFieldException("Menu name cannot be empty");
        }

        // Checks if menu price is negative. Throws NegativeNumberException if true
        if (InputValidator.isNegativeNumber(newMenuPrice)) {
            throw new NegativeNumberException("Menu price cannot be negative.");
        }

        // Perform updates
        menuToBeUpdated.setName(newMenuName);
        menuToBeUpdated.setDescription(newMenuDescription);
        menuToBeUpdated.setPrice(newMenuPrice);
        menuToBeUpdated.setType(type);

        // Save array list of menu into file
        saveMenuToFile(menuItems);
    }

    /**
     * Removes a menu item and saves the menu file.
     * 
     * @param id ID of the menu item.
     * @throws MenuNotFound If the menu item does not exist.
     * @throws FailReadException If the menu file cannot be read.
     * @throws FailWriteException If the menu item cannot be written into the menu file.
     * @throws MenuInPromoException If the user tries to remove the menu item before removing it from the promotion item.
     */
    public static void removeMenu(int id)
        throws MenuNotFound, FailReadException, FailWriteException, MenuInPromoException {
        ArrayList<Menu> menuItems = retrieveMenu();

        Menu menuToBeDeleted = SerializeDB.findById(menuItems, id);

        if (menuToBeDeleted == null) {
            throw new MenuNotFound();
        }

        ArrayList<Promo> promos = PromoController.retrievePromo();
        for (Promo promo: promos) {
            if (PromoController.isMenuInPromo(promo, menuToBeDeleted)) {
                throw new MenuInPromoException(promo.getId());
            }
        }

        menuItems.remove(menuToBeDeleted);

        saveMenuToFile(menuItems);
    }

    /**
     * Gets an ArrayList of menu items. 
     * 
     * @return ArrayList of menu items.
     * @throws FailReadException If the menu file cannot be read.
     */
    public static ArrayList<Menu> retrieveMenu() throws FailReadException  {
        List list = SerializeDB.readSerializedObject("menu.dat");
        return list == null ? new ArrayList<>() : (ArrayList) list;
    }

    /**
     * Saves changes made to the menu items in the menu file.
     * 
     * @param menuItems ArrayList of menu items.
     * @throws FailWriteException If a menu item cannot be written into the menu file.
     */
    private static void saveMenuToFile(ArrayList<Menu> menuItems) throws FailWriteException {
        SerializeDB.writeSerializeObject("menu.dat", menuItems);
    }
}
