package main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob Killpack
 *
 */
public class Receipt {

	private double total;
	private String paymentMethod;
	private List<ReceiptRow> receiptBody = new ArrayList<ReceiptRow>();
	
	public Receipt(double total, String paymentMethod, List<ReceiptRow> receiptBody) {
		this.total = total;
		this.paymentMethod = paymentMethod;
		for (int i = 0; i < receiptBody.size(); i++) {
			this.receiptBody.add(receiptBody.get(i));
		}
	}
	
	public double getTotal() {
		return total;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public List<ReceiptRow> getReceiptBody() {
		return receiptBody;
	}
	
	public String toString() {
		String output;
		output = "RECEIPT\n"
				+ "---------------------------------------------\n";
		for (int i = 0; i < receiptBody.size(); i++) {
			output = output + receiptBody.get(i).toString() + "\n";
		}
		output = output + "\nTotal Price: " + Double.toString(total) + "\n";
		output = output + "\nPayment Type: " + paymentMethod;
		return output;
	}
}
