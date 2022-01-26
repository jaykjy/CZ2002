package invoice;

import java.util.Scanner;
import table.TableController;
import table.exception.TableNotFound;
import database.exceptions.FailReadException;
import database.exceptions.FailWriteException;
import order.exception.OrderNotFound;
import invoice.exception.InvoiceNotFound;

/**
 * Displays the Invoice UI when the user selects Invoice in the main UI.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class InvoiceUI {

	/**
	 * Prompts the user to choose between saving an invoice, viewing an invoice, or quit. 
	 */
	public void mainApp() {
		int option;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("************");
			System.out.println("Invoice Page");
			System.out.println("************");
			System.out.println("1. Save Invoice");
			System.out.println("2. View Invoice");
			System.out.println("3. Quit");

			option = sc.nextInt();
			sc.nextLine();

			switch(option) {
				case 1:
					saveInvoiceUI();
					break;
				case 2:
					viewInvoiceUI();
					break;
				case 3:
					System.out.println("Exiting...");
					break;
				default:
					System.out.println("Please choose a valid option.");
			}
		} while (option != 3);
	}
	
	/**
	 * Prompts the user to enter the number of the table that is ready to pay for their order. 
	 */
	public static void saveInvoiceUI() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Table Number:");
			int tableId = sc.nextInt();
			int invoiceID = InvoiceController.saveInvoice(TableController.getTable(tableId));
			InvoiceController.viewInvoice(invoiceID);
		} catch (FailReadException | FailWriteException | OrderNotFound | InvoiceNotFound | TableNotFound e) {
            System.out.println(e.getMessage());
        }
	};
	
	/**
	 * Prompts the user to enter the invoice ID of the invoice they wish to view.
	 */
	public static void viewInvoiceUI() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Invoice ID:");
			int invoiceId = sc.nextInt();
			InvoiceController.viewInvoice(invoiceId);
		} catch (FailReadException | InvoiceNotFound e) {
            System.out.println(e.getMessage());
        }
	};

}
