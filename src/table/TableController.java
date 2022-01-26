package table;

import java.util.ArrayList;
import java.util.List;
import database.SerializeDB;
import database.exceptions.FailReadException;
import database.exceptions.FailWriteException;
import table.exception.TableNotFound;

/**
 * Contains the methods to create, save and retrieve tables, check and display table status, and retrieve occupied and available tables.  
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class TableController {
	
	/**
	 * Creates 10 size 2 tables, 10 size 4 tables, 5 size 8 tables and 5 size 10 tables in an ArrayList and saves them in a file.
	 */
	public static void createTableDatabaseFiles() {
		try {
			ArrayList<Table> tables = new ArrayList<Table>();
			for (int i = 1; i <= 10; i++) tables.add(new Table(i, 2));
			for (int i = 11; i <= 20; i++) tables.add(new Table(i, 4));
			for (int i = 21; i <= 25; i++) tables.add(new Table(i, 8));
			for (int i = 26; i <= 30; i++) tables.add(new Table(i, 10));
			SerializeDB.writeSerializeObject("table.dat", tables);
		} catch (FailWriteException e) {
            System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Saves all changes made to a table.
	 * 
	 * @param newTable Table that has new changes made.
	 * @throws FailReadException If the table file cannot be read.
	 */
	public static void saveTable (Table newTable) throws FailReadException {
		try {
			List<Table> tables = SerializeDB.readSerializedObject("table.dat");
			int tableId = newTable.getTableId();
			for (Table table : tables) {
				if (table.getTableId() == tableId) {
					tables.set(tables.indexOf(table), newTable);
				}
			}
			SerializeDB.writeSerializeObject("table.dat", tables);
		} catch (FailWriteException e) {
            System.out.println(e.getMessage());
		}
	}

	/**
	 * Gets a desired table.
	 * 
	 * @param tableId ID of the desired table.
	 * @return Desired table.
	 * @throws FailReadException If the table file cannot be read.
	 * @throws TableNotFound If the table does not exist.
	 */
	public static Table getTable(int tableId) throws FailReadException, TableNotFound {
		List<Table> tables = SerializeDB.readSerializedObject("table.dat");
		for (Table table : tables) {
			if (table.getTableId() == tableId) return table;
		}
		throw new TableNotFound();
	}
	
	/**
	 * Checks if there are any available tables, regardless of AM or PM.
	 * 
	 * @return True if there are available tables. Else, false.
	 * @throws FailReadException If the table file cannot be read.
	 */
	public static boolean tableAvailable() throws FailReadException {
		List<Table> tables = SerializeDB.readSerializedObject("table.dat");
		for (Table table : tables) {
			if (table.amAvailable() || table.pmAvailable()) return true;
		}
		return false;
	}

	/**
	 * Displays all available tables, regardless of AM or PM.
	 * 
	 * @throws FailReadException If the table file cannot be read.
	 */
	public static void showAvailableTables() throws FailReadException {
		List<Table> tables = SerializeDB.readSerializedObject("table.dat");
		System.out.println("Available Tables:");
		for (Table table : tables) {
			if (table.amAvailable() || table.pmAvailable()) {
				System.out.println("Table Id: " + table.getTableId());
				System.out.println("Size: " + table.getSize());
				System.out.println("AM Status: " + table.getAmStatus());
				System.out.println("PM Status: " + table.getPmStatus());
				System.out.println("");
			}
		}
	}
	
	/**
	 * Gets all tables that are available in the AM.
	 * 
	 * @return ArrayList of all tables that are available in the AM.
	 * @throws FailReadException If the table file cannot be read.
	 */
	public static ArrayList<Table> amAvailableTables() throws FailReadException {
		List<Table> tables = SerializeDB.readSerializedObject("table.dat");
		ArrayList<Table> availableTables = new ArrayList<Table>();
		for (Table table : tables) {
			if (table.amAvailable()) availableTables.add(table);
		}
		return availableTables;
	}
	
	/**
	 * Gets all tables that are available in the PM.
	 * 
	 * @return ArrayList of all tables that are available in the PM.
	 * @throws FailReadException If the table file cannot be read.
	 */
	public static ArrayList<Table> pmAvailableTables() throws FailReadException {
		List<Table> tables = SerializeDB.readSerializedObject("table.dat");
		ArrayList<Table> availableTables = new ArrayList<Table>();
		for (Table table : tables) {
			if (table.pmAvailable()) availableTables.add(table);
		}
		return availableTables;
	}
	
	/**
	 * Displays all occupied tables, regardless of AM or PM. 
	 * 
	 * @throws FailReadException If the table file cannot be read.
	 */
	public static void showOccupiedTables() throws FailReadException {
		List<Table> tables = SerializeDB.readSerializedObject("table.dat");
		boolean occupied = false;
		
		System.out.println("Tables with Orders:");
		for (Table table : tables) {
			if (table.amOccupied() || table.pmOccupied()) {
				System.out.println("Table Id: " + table.getTableId());
				occupied = true;
			}
		}
		if (occupied == false) System.out.println("No orders yet.");
	}
	
	/**
	 * Displays the status of all tables.
	 * @throws FailReadException If the table file cannot be read.
	 */
	public static void showTableStatus() throws FailReadException {
		List<Table> tables = SerializeDB.readSerializedObject("table.dat");
		System.out.println("Table Status:");
		for (Table table : tables) {
			System.out.println("Table Id: " + table.getTableId());
			System.out.println("Size: " + table.getSize());
			System.out.println("AM Status: " + table.getAmStatus());
			System.out.println("PM Status: " + table.getPmStatus());
			System.out.println("");
		}
	}
	
}