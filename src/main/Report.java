package main;

/**
 * @author Jacob Killpack
 *
 */
public class Report {
	
	private String product;
	private int quantity;
	private double revenue;
	
	public Report(String product, int quantity, double revenue) {
		this.product = product;
		this.quantity = quantity;
		this.revenue = revenue;
	}
	
	protected String getProduct() {
		return product;
	}
	
	protected int getQuantity() {
		return quantity;
	}
	
	protected double getRevenue() {
		return revenue;
	}
	
	protected void incQuantity(int quantity) {
		this.quantity += quantity;
	}
	
	protected void incRevenue(double revenue) {
		this.revenue += revenue;
	}
}
