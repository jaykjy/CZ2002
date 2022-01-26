package menu;

import base.BaseModel;
import exception.EmptyFieldException;
import exception.NegativeNumberException;
import utility.InputValidator;

/**
 * Contains the attributes and methods belonging to menu items in the Main Menu.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class Menu extends BaseModel {
	/**
	 * Name of the menu item.
	 */
    private String name;
    /**
     * Description of the menu item.
     */
    private String description;
    /**
     * Price of the menu item.
     */
    private double price;
    /**
     * Type of the menu item (MainCourse, Dessert, Drink).
     */
    private MenuType type;

    /**
     * Constructor sets the ID, name, description, price and type of the menu item.
     * 
     * @param id ID of the menu item.
     * @param name Name of the menu item.
     * @param description Description of the menu item.
     * @param price Price of the menu item.
     * @param type Type of the menu item (MainCourse, Dessert, Drink).
     */
    public Menu(int id, String name, String description, double price, MenuType type) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    /** 
     * Gets name of the menu item.
     * 
     * @return Name of the menu item.
     */
    public String getName() { return name; }

    /**
     * Sets name of the menu item.
     * 
     * @param name New name of the menu item.
     * @throws EmptyFieldException If the name of the menu item is empty. 
     */
    public void setName(String name) throws EmptyFieldException {
        if (InputValidator.isEmptyString(name)) {
            throw new EmptyFieldException("Menu name cannot be empty.");
        }
        this.name = name;
    }

    /**
     * Gets description of the menu item.
     * 
     * @return Description of the menu item. 
     */
    public String getDescription() { return description; }

    /**
     * Sets description of the menu item.
     * 
     * @param description New description of the menu item.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets price of the menu item.
     * 
     * @return Price of the menu item. 
     */
    public double getPrice() { return price; }

    /**
     * Sets price of the menu item.
     * 
     * @param price New price of the menu item
     * @throws NegativeNumberException If the price of the menu item is negative.
     */
    public void setPrice(double price) throws NegativeNumberException {
        if (InputValidator.isNegativeNumber(price)) {
            throw new NegativeNumberException("Menu price cannot be negative");
        }
        this.price = price;
    }

    /**
     * Gets type of the menu item.
     * 
     * @return Type of the menu item.
     */
    public MenuType getType() { return type; }

    /**
     * Sets type of the menu item.
     * 
     * @param type New type of the menu item.
     */
    public void setType(MenuType type) { this.type = type; }
}
