package main;
import main.User;

import database.JDBCDelete;
import database.JDBCSelect;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Zerin Bates
 * @version 1.5
 */
public class UserList {
	
   private List<User> daList=new ArrayList<User>();
   
   protected void upload(){
	   daList.clear();
	   JDBCSelect uploadData = new JDBCSelect("users");
	   uploadData.equals(daList);
	   int max = JDBCSelect.getDaUdderUsrList().size(), count = 0;
	   while (count < max) {
		   daList.add(JDBCSelect.getDaUdderUsrList().get(count));
		   count++;
	   }
	   JDBCSelect.getDaUdderUsrList().clear();
   }
   
  protected Object[]stringUser(int n){
	  Object[]a=new Object[6];
	   a[0]=daList.get(n).getId()+"";
	   a[1]=daList.get(n).getuserName();
	   a[2]=daList.get(n).getPassword();
	   a[3]=daList.get(n).getAdmin()+"";

	   return a;
   }
  
  protected void editUser(int n,String it[]){
	  daList.remove(n);
	  User x= new User(Integer.valueOf(it[0]),it[1],it[2],Boolean.valueOf(it[3]));
	  
	  daList.add(n,x);
  }
  
  protected void deleteUser(int n, int id){
	  daList.remove(n);
	  //remove from data base
	  JDBCDelete removeRow = new JDBCDelete("users", "id", Integer.toString(id));
	  JDBCDelete.getList().clear();
	  //Pointless 
	  removeRow.equals(daList);
  }
  
   protected void adminSort(){
	   //System.out.println(daList.toString().replaceAll(",", "\n"));
	   Collections.sort(daList, new Comparator<User>() {
		   public int compare(User c1, User c2) {
		     if (c1.getAdmin()) return 1;
		     else{ return -1;}

		   }

		});
      
   }

  protected void provideSort(){
	  Collections.sort(daList, new Comparator<User>() {
		   public int compare(User c1, User c2) {
			   int comp =c1.getPassword().toLowerCase().compareTo(c2.getPassword().toLowerCase());
		     if (comp<0) return -1;
		     if (comp>0) return 1;
		     return 0;
		   }

		});
  }
  
  protected void nameSort(){
	  Collections.sort(daList, new Comparator<User>() {
		   public int compare(User c1, User c2) {
			   int comp =c1.getuserName().toLowerCase().compareTo(c2.getuserName().toLowerCase());
		     if (comp<0) return -1;
		     if (comp>0) return 1;
		     return 0;
		   }

		});
   }
  
  protected void IdSort(){
	  Collections.sort(daList, new Comparator<User>() {
		   public int compare(User c1, User c2) {
		     if (c1.getId()> c2.getId()) return -1;
		     if (c1.getId()> c2.getId())  return 1;
		     return 0;
		   }

		});
   }
  
  protected List<User> getdaList() {
	  return daList;
  }
}
