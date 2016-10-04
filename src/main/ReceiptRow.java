package main;

/**
 * @author Jacob Killpack
 *
 */
public class ReceiptRow {

	private String id, name, amount, cost;
	
	public ReceiptRow(String id, String name, String amount, String cost) {
		this.id = id;
		this.name = name;
		this.amount = amount;
		this.cost = cost;
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
	
	public String toString() {
		return id + "    |    " + name.trim() + "    |    " + amount + "    |    " + cost;
	}

}