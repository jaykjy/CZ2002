package invoice;

import java.util.ArrayList;
import java.util.List;

import database.SerializeDB;
import database.exceptions.FailReadException;
import database.exceptions.FailWriteException;
import invoice.exception.InvoiceNotFound;
import menu.Menu;
import order.Order;
import order.exception.OrderNotFound;
import promo.Promo;
import table.Table;
import table.TableController;
import table.TableStatus;

/**
 * Contains the methods to save invoices and display invoices to the user.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class InvoiceController {
    
	/**
	 * Creates a file containing an ArrayList of invoices. 
	 */
    public static void createInvoiceDatabaseFiles() {
		try {
			ArrayList<Invoice> invoices = new ArrayList<Invoice>();
			SerializeDB.writeSerializeObject("invoice.dat", invoices);
		} catch (FailWriteException e) {
            System.out.println(e.getMessage());
		}
	}
	
    /**
     * Creates the invoice of an order, saves it into the invoice file, displays it to the user and clears the order from the table.
     * 
     * @param table Table which the order belongs to.
     * @return ID of the created invoice.
     * @throws FailReadException If the invoice file cannot be read.
     * @throws FailWriteException If the created invoice cannot be written into the invoice file.
     * @throws OrderNotFound If the table does not have an order.
     */
    public static int saveInvoice(Table table) throws FailReadException, FailWriteException, OrderNotFound {
    	List<Invoice> invoices = SerializeDB.readSerializedObject("invoice.dat");
    	Order order = table.getOrder();
		if (order == null) {
			throw new OrderNotFound();
		}
    	Invoice invoice = new Invoice(order);
    	invoice.setInvoiceId(invoices.size() + 1);
    	
    	float subtotal = 0;
    	ArrayList<Menu> menuItems = invoice.getMenuItems();
    	ArrayList<Promo> promoItems = invoice.getPromoItems();
    	
    	for (Menu item : order.getOrderItems()) {
    		menuItems.add(item);
    		subtotal += item.getPrice();
    	}
    	
    	for (Promo item : order.getOrderPromos()) {
    		promoItems.add(item);
    		subtotal += item.getPrice();
    	}
    	
    	invoice.setMenuItems(menuItems);
    	invoice.setPromoItems(promoItems);
    	invoice.setSubtotal(subtotal);
		invoice.calcPayment();

    	int hour = order.getTimeStamp().getHour();
    	if (hour < 12) {
    		table.setAmStatus(TableStatus.VACATED);
    	} else {
    		table.setPmStatus(TableStatus.VACATED);
    	}
    	
    	table.clearOrder();
		TableController.saveTable(table);
    	invoices.add(invoice);
		SerializeDB.writeSerializeObject("invoice.dat", invoices);
        System.out.println("InvoiceID " + invoice.getInvoiceId() + " saved successfully.");

		return invoice.getInvoiceId();
    }

    /**
     * Displays the details of the desired invoice to the user.
     * 
     * @param invoiceId ID of the desired invoice.
     * @throws FailReadException If the invoice file cannot be read.
     * @throws InvoiceNotFound If the user enters an Invoice ID that does not exist.
     */
    public static void viewInvoice (int invoiceId) throws FailReadException, InvoiceNotFound {
    	List<Invoice> invoices = SerializeDB.readSerializedObject("invoice.dat");
		if (invoiceId > invoices.size() || invoiceId <= 0) { 
			throw new InvoiceNotFound(); 
		}
    	Invoice invoice = invoices.get(invoiceId - 1);
		
    	int tableId = invoice.getOrder().getTable();

        System.out.println("================Invoice===============");
        System.out.println("InvoiceID : " + invoiceId);
        System.out.println("Table Number: " + tableId);
        System.out.println("Date: " + invoice.getTimeStamp());
        System.out.printf("Subtotal: %.2f%n", invoice.getSubtotal());
        System.out.printf("Service Charge: %.2f%n", invoice.getServiceCharge());
        System.out.printf("GST: %.2f%n", invoice.getGst());
        System.out.printf("Total Due: %.2f%n", invoice.getTotal());
        System.out.println("");
    }
}
