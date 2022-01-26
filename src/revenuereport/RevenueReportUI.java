package revenuereport;

import java.time.Month;
import java.util.List;
import java.util.Scanner;
import database.SerializeDB;
import database.exceptions.FailReadException;
import invoice.Invoice;
import invoice.InvoiceController;
import invoice.exception.InvoiceNotFound;

/**
 * Displays the Revenue Report UI when the user selects Revenue in the main UI.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class RevenueReportUI {

	/**
	 * Prompts the user to choose between viewing all invoices, viewing revenue by period, or quit.
	 */
    public void mainApp() {
        int option;
        Scanner sc = new Scanner(System.in);

        do {
        	System.out.println("*******************");
			System.out.println("Revenue Report Page");
			System.out.println("*******************");
            System.out.println("1. Load Invoices");
            System.out.println("2. View Revenue by Period");
            System.out.println("3. Quit");

            option = sc.nextInt(); // Read user option
            sc.nextLine(); // Read newline character after hitting Enter key

            switch (option) {
                case 1:
                    loadInvoices();
                    break;
                case 2:
                    revenueByPeriod();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Please choose a valid option.");
                    break;
            }
        } while (option != 3);
    }

    /**
     * Displays all the invoices.
     */
    private static void loadInvoices() {
    	try {
	    	List<Invoice> invoices = SerializeDB.readSerializedObject("invoice.dat");
	        if (invoices.size() == 0) {
	            System.out.println("No invoices found.");
	        } else {
	        	System.out.println("*******************");
	            System.out.println("Restaurant Invoices");
	            System.out.println("*******************");
	            for (Invoice invoice : invoices) {
	                InvoiceController.viewInvoice(invoice.getInvoiceId());
	            }
	        }
    	} catch (FailReadException | InvoiceNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays the revenue of a period. Each period (1, 2, 3 and 4) represents a quarter of a year. 
     */
    private static void revenueByPeriod(){
    	try {
    		List<Invoice> invoices = SerializeDB.readSerializedObject("invoice.dat");
	    	float revenue = 0;
	    	
	    	Scanner sc = new Scanner(System.in);
	    	System.out.println("Enter Period (1/2/3/4): ");
	    	int option = sc.nextInt();
	
			switch(option) {
				case 1:
					for (Invoice invoice : invoices) {
						Month month = invoice.getTimeStamp().getMonth();
						if (month == Month.JANUARY || month == Month.FEBRUARY || month == Month.MARCH) revenue += invoice.getTotal();
	                }
	                System.out.println("Total Revenue for Q1: " + revenue);
					break;
				case 2:
					for (Invoice invoice : invoices) {
						Month month = invoice.getTimeStamp().getMonth();
						if (month == Month.APRIL || month == Month.MAY || month == Month.JUNE) revenue += invoice.getTotal();
	                }
	                System.out.println("Total Revenue for Q2: " + revenue);
					break;
				case 3:
					for (Invoice invoice : invoices) {
						Month month = invoice.getTimeStamp().getMonth();
						if (month == Month.JULY || month == Month.AUGUST || month == Month.SEPTEMBER) revenue += invoice.getTotal();
	                }
	                System.out.println("Total Revenue for Q3: " + revenue);
					break;
				case 4:
					for (Invoice invoice : invoices) {
						Month month = invoice.getTimeStamp().getMonth();
						if (month == Month.OCTOBER || month == Month.NOVEMBER || month == Month.DECEMBER) revenue += invoice.getTotal();
	                }
	                System.out.println("Total Revenue for Q4: " + revenue);
					break;
				default:
					System.out.println("Please choose a valid option.");
			}
    	} catch (FailReadException e) {
            System.out.println(e.getMessage());
        }
    }
}
