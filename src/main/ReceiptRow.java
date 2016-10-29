package main;

/**
 * SOMETHING HERE
 * 
 * @author Jacob Killpack
 * @version 1.1
 */
public class ReceiptRow {

	private String id, name, amount, cost,date;
	
	public ReceiptRow(String id, String name, String amount, String cost) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.cost = cost;
		this.date = "oct 31, 2016";
	}
	
	public String getId(){
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public String getCost() {
		return cost;
	}
	public String getDate(){
		return date;
	}
	public String toString() {
		return id + "    |    " + name.trim() + "    |    " + amount + "    |    " + cost;
	}

}