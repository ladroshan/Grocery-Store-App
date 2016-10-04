package main;
import main.Item;
import java.util.List;

import database.JDBCDelete;
import database.JDBCSelect;
import database.JDBCUpdate;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
public class ItemList {
   List<Item> daList=new ArrayList<Item>();
  public void upload(){
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
  Object[]stringItem(int n){
	  Object[]a=new Object[7];
	   a[0]=daList.get(n).getId()+"";
	   a[1]=daList.get(n).getName();
	   a[2]=daList.get(n).getProvider();
	   a[3]=daList.get(n).getQuantity()+"";
	   a[4]=daList.get(n).getCost()+"";
	   return a;
   }
  void editItem(int n,String it[]){
	  daList.remove(n);
	  int id =Integer.valueOf(it[0]);
	  for (int i = 0; i < 5; i++) {

		  System.out.println(it[i]);
		  
	  }
	  JDBCUpdate editAway = new JDBCUpdate("inventory", it[1], it[2], it[3], it[4], "id", it[0]);
	  //Pointless
	  editAway.equals(daList);
	  Item x= new Item(id,it[1],it[2], Integer.valueOf(it[3]),Double.valueOf(it[4]));
	  daList.add(n,x);
	  //edit full row
  }
  void deleteItem(int n,int id){
	  daList.remove(n);
	  //remove from data base
	  JDBCDelete removeRow = new JDBCDelete("inventory", "id", Integer.toString(id));
	  JDBCDelete.getList().clear();
	  //Pointless 
	  removeRow.equals(daList);
  }
   void idSort(){
	   System.out.println(daList.toString().replaceAll(",", "\n"));
	   Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
		     if (c1.getId() > c2.getId()) return -1;
		     if (c1.getId() > c2.getId()) return 1;
		     return 0;
		   }

		});
      
   }
  void priceSort(){
	  Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
		     if (c1.getCost()> c2.getCost()) return -1;
		     if (c1.getCost()> c2.getCost())  return 1;
		     return 0;
		   }

		});
   }
  void provideSort(){
	  Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
			   int comp =c1.getProvider().toLowerCase().compareTo(c2.getProvider().toLowerCase());
		     if (comp<0) return -1;
		     if (comp>0) return 1;
		     return 0;
		   }

		});
  }
  void nameSort(){
	  Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
			   int comp =c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase());
		     if (comp<0) return -1;
		     if (comp>0) return 1;
		     return 0;
		   }

		});
   }
void quantSort(){
	  Collections.sort(daList, new Comparator<Item>() {
		   public int compare(Item c1, Item c2) {
		     if (c1.getQuantity()> c2.getQuantity()) return -1;
		     if (c1.getQuantity()> c2.getQuantity())  return 1;
		     return 0;
		   }

		});
   }
}

