package main;

import database.JDBCSelect;

/**
 * SOMETHING HERE
 * 
 * @author Zerin Bates
 * @version 1.2
 */
public class Item {
 private int itemID=0;
  private double cost= 6.66;
  private String provider = "acme";
  private String Name = "Inego Montoya";
 private int quant=100;
  protected int getId(){
	  return itemID;
  }
  protected double getCost(){
	  return cost;
  }
  protected String getProvider(){
	  return provider;
  }
  protected String getName(){
	  return Name;
  }
  protected int getQuantity(){
	  return quant;
  }
  /**
   * this is the overload constructor for creating an item object
   * @param itemNumber is the Id number of said item
   * @param itemName is the name of the item
   * @param aprovider a string with the providers name
   * @param quantity number of items
   * @param acost the cost of the item
   */
  public Item(int itemNumber,String itemName,String aprovider, int quantity,double acost){
	 itemID=itemNumber;
	 cost=acost;
	 provider=aprovider;
	 Name = itemName;
	 quant = quantity;
 }
  public Item(){
	  itemID=0000;
      cost=6.66;
	  provider="acme";
	  Name = "Inego Montoya";
	  quant = 100;
  }
  
  public void setID(String id) {
	  this.itemID = Integer.parseInt(id);
	  return;
  }
  
  public void setName(String name) {
	  this.Name = name;
	  return;
  }
  
  public void setProvider(String provider){
	  this.provider = provider;
	  return;
  }
  
  public void setQuantity(String quantity){
	  this.quant = Integer.parseInt(quantity);
	  return;
  }
  
  public void setPrice(String price){
	  String rawCost = price, cost;
	  rawCost = JDBCSelect.getList().get(4);
	  rawCost = rawCost.substring(1);
	  char[]removeCommas = rawCost.toCharArray();
	  for (int i = 0; i < removeCommas.length; i++) {
		  if(removeCommas[i] == ','){
			  rawCost = rawCost.substring(0, i) + rawCost.substring(i + 1);
			  removeCommas = rawCost.toCharArray();
			  i = 0;
		  }
	  }
	  cost = rawCost;
	  this.cost = Double.parseDouble(cost);
	  return;
  }
  
  public String toString(){
	  String output;
	  output = "ID: " + this.itemID + "\nProduct Name: " + this.Name + "\nProvider: " + this.provider + "\nQuantity: " + this.quant 
			  + "\nPrice: " + this.cost;
	  return output;
  }
  
}
