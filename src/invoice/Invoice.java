package invoice;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.Serializable;
import order.Order;
import menu.Menu;
import promo.Promo;

/**
 * Contains the attributes and methods belonging to invoices.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class Invoice implements Serializable {
	/**
	 * Service charge is fixed at 10%.
	 */
	private static final float SERVICE_CHARGE = (float) 0.1;
	/**
	 * GST is fixed at 7%.
	 */
	private static final float GST = (float) 0.07;
	/**
	 * ID of the invoice.
	 */
	private int invoiceId;
	/**
	 * Sub-total of the invoice.
	 */
	private float subtotal;
	/**
	 * Service charge of the invoice.
	 */
	private float serviceCharge;
	/**
	 * GST of the invoice.
	 */
    private float gst;
    /**
     * Total bill of the invoice.
     */
    private float total;
    /**
     * Order of the invoice.
     */
	private Order order;
	/**
	 * Time-stamp of the invoice.
	 */
	private LocalDateTime timestamp;
	/**
	 * ArrayList of menu items of the invoice.
	 */
	private ArrayList<Menu> menuItems = new ArrayList<Menu>();
	/**
	 * ArrayList of promotion items of the invoice.
	 */
	private ArrayList<Promo> promoItems = new ArrayList<Promo>();
	
	/**
	 * Constructor sets the time-stamp and order of the invoice.
	 * 
	 * @param order Order of the invoice.
	 */
	public Invoice(Order order) {
		timestamp = LocalDateTime.now();
		this.order = order;
	}

	/**
	 * Gets the sub-total of the invoice.
	 * 
	 * @return Sub-total of the invoice.
	 */
	public float getSubtotal() { return subtotal; }
	
	/**
	 * Sets the sub-total of the invoice.
	 * 
	 * @param subtotal New sub-total of the invoice.
	 */
	public void setSubtotal(float subtotal) { this.subtotal = subtotal; }
	
	/**
	 * Gets invoice ID of the invoice.
	 * 
	 * @return Invoice ID of the invoice.
	 */
	public int getInvoiceId() { return invoiceId; }
	
	/**
	 * Sets invoice ID of the invoice.
	 * 
	 * @param invoiceId New invoice ID of the invoice.
	 */
	public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }
	
	/**
	 * Gets ArrayList of menu items of the invoice.
	 * 
	 * @return ArrayList of menu items of the invoice.
	 */
	public ArrayList<Menu> getMenuItems() { return menuItems; }
	
	/**
	 * Sets ArrayList of menu items of the invoice.
	 * 
	 * @param menuItems New ArrayList of menu items of the invoice.
	 */
	public void setMenuItems(ArrayList<Menu> menuItems) { this.menuItems = menuItems; }
	
	/**
	 * Gets ArrayList of promotion items of the invoice.
	 * 
	 * @return ArrayList of promotion items of the invoice.
	 */
	public ArrayList<Promo> getPromoItems() { return promoItems; }
	
	/**
	 * Sets ArrayList of promotion items of the invoice.
	 * 
	 * @param promoItems New ArrayList of promotion items of the invoice.
	 */
	public void setPromoItems(ArrayList<Promo> promoItems) { this.promoItems = promoItems; }
	
	/**
	 * Gets service charge of the invoice.
	 * 
	 * @return Service charge of the invoice.
	 */
	public float getServiceCharge() { return serviceCharge; }
	
	/**
	 * Gets GST of the invoice.
	 * 
	 * @return GST of the invoice.
	 */
    public float getGst() { return gst; }
    
    /**
     * Gets total bill of the invoice.
     * 
     * @return Total bill of the invoice.
     */
    public float getTotal() { return total; }
    
    /**
     * Gets order of the invoice.
     * 
     * @return Order of the invoice.
     */
	public Order getOrder() { return order; }
	
	/**
	 * Gets time-stamp of the invoice.
	 * 
	 * @return Time-stamp of the invoice.
	 */
	public LocalDateTime getTimeStamp() { return timestamp; }
	
	/**
	 * Calculates service charge, GST and total bill of the invoice.
	 */
	public void calcPayment() {
		serviceCharge = subtotal * SERVICE_CHARGE;
		gst = subtotal * GST;
		total = subtotal + serviceCharge + gst;
	}
}
