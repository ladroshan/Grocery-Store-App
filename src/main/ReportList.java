package main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob Killpack
 *
 */
public class ReportList {
	
	private double totalRevenue;
	private int totalOrders;
	private List<Report> daList = new ArrayList<Report>();
	
	public ReportList() {
		this.totalRevenue = 0;
		this.totalOrders = 0;
		
	}
	
	public void addReport(Report report) {
		String exists;
		exists = report.getProduct();
		if (daList.size() == 0) {
			daList.add(report);
			totalOrders++;
			return;
		}
		for(int i = 0; i < daList.size(); i++) {
			if (daList.get(i).getProduct().equals(exists)) {
				daList.get(i).incQuantity(report.getQuantity());
				daList.get(i).incRevenue(report.getRevenue());
				totalOrders++;
				return;
			}
		}
		daList.add(report);
		totalOrders++;
	}
	
	public int getSize() {
		return daList.size();
	}
	
	public Report getReport(int index) {
		return daList.get(index);
	}
	
	public double calculateTotalRevenue() {
		for (int i = 0; i < daList.size(); i++) {
			totalRevenue += daList.get(i).getRevenue();
		}
		return totalRevenue;
	}
	
	public int getTotalOrders() {
		return totalOrders;
	}
	
	public void refresh() {
		for (int i = daList.size() - 1; i >= 0; i--) {
			daList.remove(i);
		}
		this.totalOrders = 0;
		this.totalRevenue = 0;
	}
	
	public String toString() {
		String output = "Product        |         Quantity        |        Revenue\n";
		for (int i = 0; i < daList.size(); i++) {
			output = output + daList.get(i).getProduct() + "     |     " + daList.get(i).getQuantity() + "     |     " 
					+ daList.get(i).getRevenue() + "\n"; 
		}
		calculateTotalRevenue();
		output = output + "\nTotal Revenue: $" + totalRevenue + "\n\nTotal Orders: " + totalOrders;
		return output;
	}
}
