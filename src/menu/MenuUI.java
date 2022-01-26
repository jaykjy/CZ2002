package menu;

import base.BaseCRUD;
import database.SerializeDB;
import database.exceptions.FailReadException;
import database.exceptions.FailWriteException;
import exception.EmptyFieldException;
import exception.NegativeNumberException;
import menu.exception.MenuInPromoException;
import menu.exception.MenuNotFound;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Displays the Menu UI when the user selects Menu in the main UI.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class MenuUI extends BaseCRUD {
	
	/**
	 * Sets the placeholder in BaseCRUD to Menu to display the string 'Menu' in the console accordingly.
	 */
    public MenuUI() { this.resource = "Menu"; }

    /**
     * Prompts the user to enter the name, description and price of the menu item to be created. 
     */
    @Override
    public void create() {
        Scanner sc = new Scanner(System.in);

        try {
            // Get name of menu item from user
            System.out.print("Menu name: ");
            String menuName = sc.nextLine();

            // Get description of menu item from user
            System.out.print("Menu description: ");
            String menuDescription = sc.nextLine();

            // Get price of menu item from user
            System.out.print("Menu price: ");
            double menuPrice = sc.nextDouble();

            // Get the type of menu
            MenuType type = chooseMenuType();

            // Create menu item
            MenuController.createMenu(menuName, menuDescription, menuPrice, type);
            // Success message
            System.out.println("Menu successfully created!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
        } catch (NegativeNumberException | EmptyFieldException | FailReadException | FailWriteException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prompts the user to enter the ID of the menu item to be updated and saves the changes in the menu file.
     */
    @Override
    public void update() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Your current menu: ");
        read();

        System.out.print("Please provide the Id of menu to be updated: ");
        try {
            int menuId = sc.nextInt();
            sc.nextLine();

            ArrayList<Menu> menuItems = MenuController.retrieveMenu();
            Menu menuToBeUpdated = SerializeDB.findById(menuItems, menuId);

            if (menuToBeUpdated == null) {
                throw new MenuNotFound();
            }

            // Special keyword that indicates no changes to original value
            String skipKeyword = MenuController.getSkipKeyword();

            System.out.println("Update Menu (Id: " + menuId + ")");
            System.out.println("<Enter " + skipKeyword + " to retain old value>");
            System.out.println("***************");

            System.out.print("New menu name: " );
            String menuNameUserInput = sc.nextLine();

            System.out.print("New menu description: " );
            String menuDescriptionUserInput = sc.nextLine();

            System.out.print("New menu price: " );
            String menuPriceUserInput = sc.nextLine();

            // Initialise to original menu type
            MenuType menuTypeUserInput = menuToBeUpdated.getType();
            String yesNoInput;
            do {
                System.out.println("Do you wish to update menu type (Y/N): ");
                yesNoInput = sc.nextLine().toLowerCase();

                switch (yesNoInput) {
                    case "y":
                        menuTypeUserInput = chooseMenuType();
                        break;
                    case "n":
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            } while (!yesNoInput.equals("y") && !yesNoInput.equals("n"));

            // Update menu item
            MenuController.updateMenu(menuId, menuNameUserInput,
                menuDescriptionUserInput, menuPriceUserInput, menuTypeUserInput);
            // Success message
            System.out.println("Menu successfully updated!");
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
        } catch (MenuNotFound | NegativeNumberException |
            EmptyFieldException | FailReadException | FailWriteException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the menu items from an ArrayList in the menu file.
     */
    @Override
    public void read() {
        try {
            ArrayList<Menu> menuItems = MenuController.retrieveMenu();
            displayMenuItems(menuItems);
        } catch (FailReadException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Displays the menu items from an ArrayList input.
     * 
     * @param menuItems ArrayList of menu items.
     */
    public void read(ArrayList<Menu> menuItems) {
        displayMenuItems(menuItems);
    }

    /**
     * Prompts the user to enter the ID of the menu item to be deleted and saves the changes in the menu file.
     */
    @Override
    public void delete() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Please provide the Id of menu to be deleted: ");
        try {
            int menuId = sc.nextInt();

            // Remove menu item
            MenuController.removeMenu(menuId);
            // Success message
            System.out.println("Menu successfully deleted!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid menu Id");
        } catch (MenuNotFound | FailReadException | FailWriteException | MenuInPromoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the menu items and their details from an ArrayList of menu items. 
     * 
     * @param menuItems ArrayList of menu items.
     */
    private void displayMenuItems(ArrayList<Menu> menuItems) {
        if (menuItems.size() == 0) {
            System.out.println("Menu is empty.");
        } else {
            System.out.println("***************");

            for (int i = 0; i < menuItems.size(); i++) {
                Menu currentMenu = menuItems.get(i);
                System.out.println("Menu No: " + currentMenu.getId());
                System.out.println("Name: " + currentMenu.getName());
                System.out.println("Description: " + currentMenu.getDescription());
                System.out.println("Price: " + currentMenu.getPrice());
                System.out.println("Type: " + currentMenu.getType());
                System.out.println("***************");
            }
        }
    }

    /**
     * Prompts user to select the type of menu item when creating one.
     * 
     * @return Type of menu item.
     * @throws InputMismatchException If the user enters an input that is not an enum value in MenuType (MainCourse, Dessert, Drink).
     */
    private MenuType chooseMenuType() throws InputMismatchException {
        int option;
        int i = 1;
        Scanner sc = new Scanner(System.in);

        System.out.println("Please select a menu type: ");

        do {
            for (MenuType type: MenuType.values()) {
                System.out.printf("%d. %s\n", i, type);
                i++;
            }

            option = sc.nextInt();

            if (option <= 0 || option > MenuType.values().length) {
                System.out.println("Invalid option");
                i = 1;
            }

        } while (option <= 0 || option > MenuType.values().length);

        return MenuType.values()[option - 1];
    }
}
