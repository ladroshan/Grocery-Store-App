package main;

public class Item {
 private int itemID=0;
  private double cost= 6.66;
  private String provider = "acme";
  private String Name = "Inego Montoya";
 private int quant=100;
  int getId(){
	  return itemID;
  }
  double getCost(){
	  return cost;
  }
  String getProvider(){
	  return provider;
  }
  String getName(){
	  return Name;
  }
  int getQuantity(){
	  return quant;
  }
  Item(int itemNumber,double acost,String itemName,String aprovider, int quantity){
	 itemID=itemNumber;
	 cost=acost;
	 provider=aprovider;
	 Name = itemName;
	 quant = quantity;
 }
  Item(){
	  itemID=0000;
      cost=6.66;
	  provider="acme";
	  Name = "Inego Montoya";
	  quant = 100;
  }
  
}
