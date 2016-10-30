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
		for(int i = 0; i < daList.size(); i++) {
			if (daList.get(i).getProduct().equals(exists)) {
				daList.get(i).incQuantity(report.getQuantity());
				daList.get(i).incRevenue(report.getRevenue());
			}
			else {
				daList.add(report);
			}
		}
		totalOrders++;
	}
	
	protected double calculateTotalRevenue() {
		for (int i = 0; i < daList.size(); i++) {
			totalRevenue += daList.get(i).getRevenue();
		}
		return totalRevenue;
	}
	
	protected int getTotalOrders() {
		return totalOrders;
	}
}
