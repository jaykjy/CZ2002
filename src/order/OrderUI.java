package order;

import java.util.Scanner;
import database.exceptions.FailReadException;
import menu.exception.MenuNotFound;
import promo.exception.PromoNotFoundException;
import table.TableController;
import table.exception.TableNotFound;
import order.exception.OrderNotFound;

/**
 * Displays the Order UI when the user selects Order in the Main UI.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class OrderUI {
	
	/**
	 * Prompts the user to choose between creating, viewing, adding items to, or removing items from an order, or quit. 
	 */
	public void mainApp() {
		int option;
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.println("***********");
			System.out.println("Orders Page");
			System.out.println("***********");
			System.out.println("1. Create Order");
			System.out.println("2. View Order");
			System.out.println("3. Add Items to Order");
			System.out.println("4. Remove Items from Order");
			System.out.println("5. Quit");
			
			option = sc.nextInt(); // Read user option
            sc.nextLine(); // Read newline character after hitting Enter key
            
			switch(option) {
				case 1:
					newOrderUI();
					break;
				case 2:
					viewOrderUI();
					break;
				case 3:
					addItemsUI();
					break;
				case 4:
					removeItemsUI();
					break;
				case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Please choose a valid option.");
                    break;
			}
		} while (option != 5);
	}
	
	/**
	 * Prompts the user to enter the ID of the staff handling the new order and the ID of the table making the order.
	 */
	public static void newOrderUI() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter staff ID.");
			int staffID = sc.nextInt();
			System.out.println("Enter table ID: ");
			int tableID = sc.nextInt();
			OrderController.newOrder(tableID, staffID);
		} catch (FailReadException | OrderNotFound | MenuNotFound | PromoNotFoundException | TableNotFound e) {
            System.out.println(e.getMessage());
        }
	}
	
	/**
	 * Prompts the user to enter the ID of the table that has made the order.
	 */
	public static void viewOrderUI() {
		try {
			Scanner sc = new Scanner(System.in);
			TableController.showOccupiedTables();
			System.out.println("Enter table ID: ");
			int tableID = sc.nextInt();
			if (TableController.getTable(tableID).getOrder() == null) {
				throw new OrderNotFound();
			}
			OrderController.printOrder(TableController.getTable(tableID).getOrder()); 
		} catch (FailReadException | OrderNotFound | TableNotFound e) {
            System.out.println(e.getMessage());
        }
	}
	
	/**
	 * Prompts the user to enter the ID of the table that wants to add items to their order.
	 */
	public static void addItemsUI() {
		try {
			Scanner sc = new Scanner(System.in);
			TableController.showOccupiedTables();
			System.out.println("Enter table ID: ");
			int tableID = sc.nextInt();
			OrderController.addItemsToOrder(tableID);
		} catch (FailReadException | OrderNotFound | TableNotFound e) {
            System.out.println(e.getMessage());
        }
	}
	
	/**
	 * Prompts the user to enter the ID of the table that wants to remove items from their order.
	 */
	public static void removeItemsUI() {
		try {
			Scanner sc = new Scanner(System.in);
			TableController.showOccupiedTables();
			System.out.println("Enter table ID: ");
			int tableID = sc.nextInt();
			OrderController.removeItemsFromOrder(tableID);
		} catch (FailReadException | OrderNotFound | MenuNotFound | TableNotFound e) {
            System.out.println(e.getMessage());
        }
	}
}
