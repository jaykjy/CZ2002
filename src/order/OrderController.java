package order;

import java.util.Scanner;
import java.time.LocalTime;
import database.exceptions.FailReadException;
import menu.Menu;
import menu.MenuController;
import menu.exception.MenuNotFound;
import promo.Promo;
import promo.PromoController;
import promo.exception.PromoNotFoundException;
import table.Table;
import table.TableController;
import table.TableStatus;
import table.exception.TableNotFound;
import order.exception.OrderNotFound;

/**
 * Contains the methods to create, edit and display orders to the user.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class OrderController {
	
	/**
	 * Creates a new order for a table.
	 * 
	 * @param tableID ID of the table that is making the order.
	 * @param staffID ID of the staff handling the order.
	 * @throws FailReadException If the table or menu files cannot be read.
	 * @throws OrderNotFound If the order does not exist.
	 * @throws MenuNotFound If the menu or promotion item does not exist.
	 * @throws PromoNotFoundException If the promotion item does not exist.
	 * @throws TableNotFound If the table does not exist.
	 */
	public static void newOrder(int tableID, int staffID) throws FailReadException, OrderNotFound, MenuNotFound, PromoNotFoundException, TableNotFound {
		Table table = TableController.getTable(tableID);
		if (table.getAmStatus() != TableStatus.OCCUPIED || table.getPmStatus() != TableStatus.OCCUPIED) {
			int hour = LocalTime.now().getHour();
			Order order = new Order(tableID, staffID);
			table.setOrder(order);
			if (hour < 12) {
				table.setAmStatus(TableStatus.OCCUPIED);
			} else {
				table.setPmStatus(TableStatus.OCCUPIED);
			}
			TableController.saveTable(table);
			addItemsToOrder(tableID);
		} else {
			System.out.println("Table " + tableID + " already occupied, please edit its existing order.");
		}
	}
	
	/**
	 * Adds menu and promotion items to the order.
	 * 
	 * @param tableID ID of the table that is making the order.
	 * @throws FailReadException If the table or menu files cannot be read.
	 * @throws OrderNotFound If the order does not exist.
	 * @throws TableNotFound If the table does not exist.
	 */
	public static void addItemsToOrder(int tableID) throws FailReadException, OrderNotFound, TableNotFound {
		Table table = TableController.getTable(tableID);
		Order order = table.getOrder();
		if ( order == null) {
			throw new OrderNotFound();
		}
		int itemID;
		Scanner sc = new Scanner(System.in);

		do {
			boolean found = false;
			System.out.println("Please enter menu item ID. Enter -1 to quit.");
			itemID = sc.nextInt();
			for (Menu menu : MenuController.retrieveMenu()) {
				if (itemID == menu.getId()) {
					order.addItem(menu);
					found = true;
					break;
				}
			}
			if (!found && itemID != -1) {
				System.out.println("Menu not found!");
			}
		} while (itemID != -1);
		
		do {
			boolean found = false;
			System.out.println("Please enter promo item ID. Enter -1 to quit.");
			itemID = sc.nextInt();
			for (Promo promo : PromoController.retrievePromo()) {
				if (itemID == promo.getId()) {
					order.addPromo(promo);
					found = true;
					break;
				}
			}
			if (!found && itemID != -1) {
				System.out.println("Promotion not found!");
			}
		} while (itemID != -1);

		table.setOrder(order);
		TableController.saveTable(table);
		
		printOrder(order);
	}
	
	/**
	 * Removes menu and promotion items from the order.
	 * 
	 * @param tableID ID of the table making the order.
	 * @throws FailReadException If the table or menu files cannot be read.
	 * @throws OrderNotFound If the order does not exist.
	 * @throws MenuNotFound If the menu item does not exist.
	 * @throws TableNotFound If the table does not exist.
	 */
	public static void removeItemsFromOrder(int tableID) throws FailReadException, OrderNotFound, MenuNotFound, TableNotFound{
		Table table = TableController.getTable(tableID);
		Order order = table.getOrder();
		if ( order == null) {
			throw new OrderNotFound();
		}
		
		int itemID;
		boolean removed;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Please enter menu item ID. Enter -1 to quit.");
			itemID = sc.nextInt();
			removed = order.removeItem(itemID);
			if (itemID != -1 && removed == false) {throw new MenuNotFound(); }
		} while (itemID != -1);
		
		do {
			System.out.println("Please enter promo item ID. Enter -1 to quit.");
			itemID = sc.nextInt();
			removed = order.removePromo(itemID);
			if (itemID != -1 && removed == false) {throw new MenuNotFound(); }
		} while (itemID != -1);
		
		table.setOrder(order);
		TableController.saveTable(table);

		printOrder(order);
	}
	
	/**
	 * Displays the menu and promotion items of the order.
	 * 
	 * @param order Desired order that the user wants to view.
	 */
	public static void printOrder(Order order) {
		System.out.println("Menu items ordered for table " + order.getTable() + " are: ");
		order.printOrderItems();
		System.out.println("Promo items ordered for table " + order.getTable() + " are: ");
		order.printOrderPromos();
	}
}