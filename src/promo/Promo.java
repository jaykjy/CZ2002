package promo;

import base.BaseModel;
import exception.EmptyFieldException;
import exception.NegativeNumberException;
import menu.Menu;
import utility.InputValidator;

import java.util.ArrayList;

/**
 * Contains the attributes and methods belonging to promotion items.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class Promo extends BaseModel {
	/**
	 * Name of the promotion item.
	 */
    private String name;
    /**
     * Description of the promotion item.
     */
    private String description;
    /**
     * Price of the promotion item.
     */
    private double price;
    /**
     * ArrayList of menu items in the promotion item.
     */
    private final ArrayList<Menu> menuItems = new ArrayList<>();

    /**
     * Constructor sets the ID, name, description and price of the promotion item.
     * 
     * @param id ID of the promotion item.
     * @param name Name of the promotion item.
     * @param description Description of the promotion item.
     * @param price Price of the promotion item.
     */
    public Promo(int id, String name, String description, double price) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Gets the name of the promotion item.
     * 
     * @return Name of the promotion item.
     */
    public String getName() { return name; }

    /**
     * Sets the name of the promotion item.
     * 
     * @param name New name of the promotion item.
     * @throws EmptyFieldException If the name of the promotion item is empty.
     */
    public void setName(String name) throws EmptyFieldException {
        if (InputValidator.isEmptyString(name)) {
            throw new EmptyFieldException("Promo name cannot be empty.");
        }
        this.name = name;
    }

    /**
     * Gets the description of the promotion item.
     * 
     * @return Description of the promotion item.
     */
    public String getDescription() { return description; }

    /**
     * Sets the description of the promotion item.
     * 
     * @param description New description of the promotion item.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets the price of the promotion item.
     * 
     * @return Price of the promotion item.
     */
    public double getPrice() { return price; }

    /**
     * Sets the price of the promotion item.
     * 
     * @param price New price of the promotion item.
     * @throws NegativeNumberException If the price of the promotion item is empty.
     */
    public void setPrice(double price) throws NegativeNumberException {
        if (InputValidator.isNegativeNumber(price)) {
            throw new NegativeNumberException("Promo price cannot be empty.");
        }
        this.price = price;
    }

    /**
     * Gets the ArrayList of menu items in the promotion item.
     * 
     * @return ArrayList of menu items in the promotion item.
     */
    public ArrayList<Menu> getMenuItems() { return menuItems; }

    /**
     * Adds a menu item into the ArrayList of menu items in the promotion item.
     * 
     * @param menu Menu item to be added to the ArrayList of menu items in the promotion item.
     */
    public void addMenu(Menu menu) { menuItems.add(menu); }

    /**
     * Removes a menu item from the ArrayList of menu items in the promotion item.
     * 
     * @param menu Menu item to be removed from the ArrayList of menu items in the promotion item.
     */
    public void removeMenu(Menu menu) { menuItems.remove(menu); }
}
