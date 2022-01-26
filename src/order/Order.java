package order;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDateTime;
import menu.Menu;
import promo.Promo;

/**
 * Contains the attributes and methods belonging to orders from different tables.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class Order implements Serializable{
	/**
	 * Time-stamp of the order.
	 */
	private LocalDateTime timestamp;
	/**
	 * ID of the table with the order.
	 */
	private int tableId;
	/**
	 * ID of the staff handling the order.
	 */
	private int staffId;
	/**
	 * ArrayList of menu items of the order.
	 */
	private ArrayList<Menu> alaCarte = new ArrayList<Menu>();
	/**
	 * ArrayList of promotion items of the order.
	 */
	private ArrayList<Promo> setPackages = new ArrayList<Promo>();
	
	/**
	 * Constructor sets the ID of the table with the order and the ID of the staff handling the order.
	 * 
	 * @param tableId ID of the table with the order.
	 * @param staffId ID of the staff handling the order.
	 */
	public Order(int tableId, int staffId) {
		this.tableId = tableId;
		this.staffId = staffId;
		timestamp = LocalDateTime.now();
	}
	
	/**
	 * Gets time-stamp of the order.
	 * 
	 * @return Time-stamp of the order.
	 */
	public LocalDateTime getTimeStamp() { return timestamp; }
	
	/**
	 * Gets table with the order.
	 * 
	 * @return Table with the order.
	 */
	public int getTable() { return tableId; }
	
	/**
	 * Gets ID of the staff handling the order.
	 * 
	 * @return ID of the staff handling the order.
	 */
	public int getStaff() { return staffId; }
	
	/**
	 * Adds menu item to the order. 
	 * 
	 * @param item Menu item to be added to the order.
	 */
	public void addItem(Menu item) { alaCarte.add(item); }
	
	/**
	 * Removes food item from the order.
	 * 
	 * @param itemId ID of the menu item to be removed from the order.
	 * @return Outcome of the removal of the menu item.
	 */
	public boolean removeItem (int itemId) {
		boolean removed = false;
		for(int i = 0; i < alaCarte.size(); i++) {
			if (itemId == alaCarte.get(i).getId()) {
				alaCarte.remove(i);
				removed = true;
				break;
			}
		}
		return removed;
	}
	
	/**
	 * Adds promotion item to the order.
	 * 
	 * @param item Promotion item to be added to the order.
	 */
	public void addPromo (Promo item) { setPackages.add(item); }
	
	/**
	 * Removes promotion item from the order.
	 * 
	 * @param itemId ID of the promotion item to be removed from the order.
	 * @return Outcome of the removal of the promotion item.
	 */
	public boolean removePromo (int itemId) {
		boolean removed = false;
		for(int i = 0; i < setPackages.size(); i++) {
			if (itemId == setPackages.get(i).getId()) {
				setPackages.remove(i);
				removed = true;
				break;
			}
		}
		return removed;
	}
	
	/**
	 * Gets ArrayList of menu items of the order.
	 * 
	 * @return ArrayList of menu items of the order.
	 */
	public ArrayList<Menu> getOrderItems() { return alaCarte; }
	
	/**
	 * Displays menu items of the order.
	 */
	public void printOrderItems() {
		for(int i = 0; i < alaCarte.size(); i++) {
			System.out.println(alaCarte.get(i).getId()+ ". " + alaCarte.get(i).getName());
		}
	}
	
	/**
	 * Gets ArrayList of promotion items of the order.
	 * 
	 * @return ArrayList of promotion items of the order.
	 */
	public ArrayList<Promo> getOrderPromos() { return setPackages; }
	
	/**
	 * Displays promotion items of the order.
	 */
	public void printOrderPromos() {
		for(int i = 0; i < setPackages.size(); i++) {
			System.out.println(setPackages.get(i).getId()+ ". " + setPackages.get(i).getName());
		}
	}
}

