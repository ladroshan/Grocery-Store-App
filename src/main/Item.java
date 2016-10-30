package main;


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
  
}
