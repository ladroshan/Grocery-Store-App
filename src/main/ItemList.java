package main;

import main.Item;

import database.JDBCDelete;
import database.JDBCSelect;
import database.JDBCUpdate;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class ItemList {
   private List<Item> daList=new ArrayList<Item>();
   
   protected void upload(){
	  //upload from data base
	  daList.clear();
	  JDBCSelect uploadData = new JDBCSelect("inventory");
	  uploadData.equals(daList);
	  int max = JDBCSelect.getDaUdderInvList().size(), count = 0;
	  while (count < max) {
		  daList.add(JDBCSelect.getDaUdderInvList().get(count));
		  count++;
	  }
	  JDBCSelect.getDaUdderInvList().clear();
   }
  
   protected Object[]stringItem(int n){
	  Object[]a=new Object[7];
	   a[0]=daList.get(n).getId()+"";
	   a[1]=daList.get(n).getName();
	   a[2]=daList.get(n).getProvider();
	   a[3]=daList.get(n).getQuantity()+"";
	   a[4]=daList.get(n).getCost()+"";
	   return a;
   }
   
   protected void editItem(int n,String it[]){
	  //Pick row out of the table
	  daList.remove(n);
	  //Get ID to pass to the Update Query for specification of where to locate the record
	  int id =Integer.valueOf(it[0]);
	  //Update database
	  JDBCUpdate editAway = new JDBCUpdate("inventory", it[1], it[2], it[3], it[4], "id", it[0]);
	  JDBCUpdate.getList().clear();
	  //Pointless
	  editAway.equals(daList);
	  //Create new row for the table
	  Item x= new Item(id,it[1],it[2], Integer.valueOf(it[3]),Double.valueOf(it[4]));
	  //Add item back into the table
	  daList.add(n,x);
  }
  
  protected void deleteItem(int n,int id){
	  //Pick row out of the table
	  daList.remove(n);
	  //Remove record from database
	  JDBCDelete removeRow = new JDBCDelete("inventory", "id", Integer.toString(id));
	  JDBCDelete.getList().clear();
	  //Pointless 
	  removeRow.equals(daList);
  }
  
   protected void idSort(){
	   //System.out.println(daList.toString().replaceAll(",", "\n"));
	   Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
		     if (c1.getId() > c2.getId()) return -1;
		     if (c1.getId() > c2.getId()) return 1;
		     return 0;
		   }

		});
      
   }
   
  protected void priceSort(){
	  Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
		     if (c1.getCost()> c2.getCost()) return -1;
		     if (c1.getCost()> c2.getCost())  return 1;
		     return 0;
		   }

		});
   }
  
  protected void provideSort(){
	  Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
			   int comp =c1.getProvider().toLowerCase().compareTo(c2.getProvider().toLowerCase());
		     if (comp<0) return -1;
		     if (comp>0) return 1;
		     return 0;
		   }

		});
  }
  
  protected void nameSort(){
	  Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
			   int comp =c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase());
		     if (comp<0) return -1;
		     if (comp>0) return 1;
		     return 0;
		   }

		});
   }
  
  protected void quantSort(){
	  Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
		     if (c1.getQuantity()> c2.getQuantity()) return -1;
		     if (c1.getQuantity()> c2.getQuantity())  return 1;
		     return 0;
		   }

		});
   }

	public List<Item> getdaList() {
		return daList;
	}
}

