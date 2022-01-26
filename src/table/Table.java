package table;

import java.io.Serializable;
import order.Order;

/**
 * Contains the attributes and methods belonging to all 30 tables.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class Table implements Serializable{
	/**
	 * ID of the table.
	 */
	private int tableId;
	/**
	 * Size of the table. (2, 4, 8 and 10 only)
	 */
	private int size;
	/**
	 * Status of the table in the AM.
	 */
	private TableStatus amStatus;
	/**
	 * Status of the table in the PM.
	 */
	private TableStatus pmStatus;
	/**
	 * Order of the table.
	 */
	private Order order;
	
	/**
	 * Constructor sets the ID and size of the table.
	 * 
	 * @param tableId ID of the table.
	 * @param size Size of the table.
	 */
	public Table(int tableId, int size) {
		this.tableId = tableId;
		this.size = size;
		amStatus = TableStatus.VACATED;
		pmStatus = TableStatus.VACATED;
	}
	
	/**
	 * Gets the status of the table in the AM.
	 * 
	 * @return Status of the table in the AM.
	 */
	public TableStatus getAmStatus() { return amStatus; }
	
	/**
	 * Sets the status of the table in the AM.
	 * 
	 * @param amStatus Status of the table in the AM.
	 */
	public void setAmStatus(TableStatus amStatus) { this.amStatus = amStatus; }
	
	/**
	 * Gets the status of the table in the PM.
	 * 
	 * @return Status of the table in the PM.
	 */
	public TableStatus getPmStatus() { return pmStatus; }
	
	/**
	 * Sets the status of the table in the PM.
	 * 
	 * @param pmStatus Status of the table in the PM.
	 */
	public void setPmStatus(TableStatus pmStatus) { this.pmStatus = pmStatus; }
	
	/**
	 * Gets the size of the table.
	 * 
	 * @return Size of the table.
	 */
	public int getSize() { return size; }
	
	/**
	 * Gets the ID of the table.
	 * 
	 * @return ID of the table.
	 */
	public int getTableId() { return tableId; }
	
	/**
	 * Gets the order of the table.
	 * 
	 * @return Order of the table.
	 */
	public Order getOrder() { return order; }
	
	/**
	 * Sets the order of the table.
	 * 
	 * @param order New order of the table.
	 */
	public void setOrder(Order order) { this.order = order; }
	
	/**
	 * Clears the order of the table.
	 */
	public void clearOrder() { order = null; }
	
	/**
	 * Checks if the status of the table in the AM is vacant or not.
	 * 
	 * @return True if the table is available. Else, false.
	 */
	public boolean amAvailable() {
		if (amStatus == TableStatus.VACATED) return true;
		return false;
	}
	
	/**
	 * Checks if the status of the table in the PM is vacant or not.
	 * 
	 * @return True if the table is available. Else, false.
	 */
	public boolean pmAvailable() {
		if (pmStatus == TableStatus.VACATED) return true;
		return false;
	}
	
	/**
	 * Checks if the status of the table in the AM is occupied or not.
	 * 
	 * @return True if the table is occupied. Else, false.
	 */
	public boolean amOccupied() {
		if (amStatus == TableStatus.OCCUPIED) return true;
		return false;
	}
	
	/**
	 * Checks if the status of the table in the PM is occupied or not.
	 * 
	 * @return True if the table is occupied. Else, false.
	 */
	public boolean pmOccupied() {
		if (pmStatus == TableStatus.OCCUPIED) return true;
		return false;
	}
	
}