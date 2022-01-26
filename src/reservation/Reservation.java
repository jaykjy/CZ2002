package reservation;

import java.util.Date;
import java.io.Serializable;

/**
 * Contains the attributes and methods of the reservations made.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class Reservation implements Serializable{
	/**
	 * Date and time of the reservation.
	 */
	private Date datetime;
	/**
	 * Number of customers of the reservation.
	 */
	private int pax;
	/**
	 * Contact number of the customer who made the reservation.
	 */
	private int contactNumber;
	/**
	 * ID of the table reserved for the reservation.
	 */
	private int tableId;

	/**
	 * Constructor sets the date, time and number of customers of the reservation,
	 * the contact number of the customer who made the reservation,
	 * and the ID of the table reserved for the reservation.
	 * 
	 * @param datetime Date and time of the reservation.
	 * @param pax Number of customers of the reservation.
	 * @param contactNumber Contact number of the customer who made the reservation.
	 * @param tableId ID of the table reserved for the reservation.
	 */
	public Reservation(Date datetime, int pax, int contactNumber, int tableId) {
		this.datetime = datetime;
		this.pax = pax;
		this.contactNumber = contactNumber;
		this.tableId = tableId;
	}

	/**
	 * Gets the date and time of the reservation.
	 * 
	 * @return Date and time of the reservation.
	 */
	public Date getDatetime() { return datetime; }
	
	/**
	 * Gets the number of customers of the reservation.
	 * 
	 * @return Number of customers of the reservation.
	 */
	public int getPax() { return pax; }
	
	/**
	 * Gets the contact number of the customer who made the reservation.
	 * 
	 * @return Contact number of the customer who made the reservation.
	 */
	public int getContactNumber() { return contactNumber; }
	
	/**
	 * Gets the ID of the table reserved for the reservation.
	 * 
	 * @return ID of the table reserved for the reservation.
	 */
	public int getTableID() { return tableId; }

}
