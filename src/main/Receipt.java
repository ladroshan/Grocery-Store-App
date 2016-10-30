package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import database.JDBCSelect;
import database.JDBCUpdate;

/**
 * 
 * 
 * @author Jacob Killpack
 * @version 1.1
 */
public class Receipt {

	private double total;
	private String paymentMethod;
	private List<ReceiptRow> receiptBody = new ArrayList<ReceiptRow>();
	
	public Receipt(double total, String paymentMethod, List<ReceiptRow> receiptBody) {
		this.total = total;
		this.paymentMethod = paymentMethod;
		for (int i = 0; i < receiptBody.size(); i++) {
			this.receiptBody.add(receiptBody.get(i));
		}
	}
	public int[] getItemsQuant(){
		int [] items = new int [receiptBody.size()];
		for (int i = 0; i < receiptBody.size(); i++) {
			items[i]=Integer.parseInt(receiptBody.get(i).getAmount());
			
			}
		return items;
	}
	public void updateInv(){
		int[] ids=this.getItemsID();
		int [] nums = this.getItemsQuant();
		for (int i = 0;i<ids.length;i++){
			for (int j = 0;j<ids.length;j++){
				if(i!=j){
					
			if(ids[i]==ids[j]){
				nums[i]+=nums[j];
				nums[j]=0;
			}
			}
		}
			JDBCSelect item =new JDBCSelect("inventory","id",ids[i]+"");
			System.out.println(item.getList().get(3));
			if(nums[i]>Integer.parseInt(item.getList().get(3))){
				System.out.println(nums[i]);
				JOptionPane.showMessageDialog(null, "exceeded item quantity try again.", "error message", JOptionPane.ERROR_MESSAGE);
				return;
			}
	}
		for (int i = 0;i<ids.length;i++){
		JDBCSelect item =new JDBCSelect("inventory","id",ids[i]+"");
		int quant =Integer.parseInt(item.getList().get(3))-nums[i];
		
		String edit;
		edit = item.getList().get(4);
		edit = edit.substring(1);
		char[] removeCommas = edit.toCharArray();
		for (int j = 0; j < edit.length(); j++) {
			if (removeCommas[j] == ',') {
				edit = edit.substring(0, (j)) + edit.substring(j + 1);
				removeCommas = edit.toCharArray();
			}
		}
		
		double acost = Double.parseDouble(edit);
		/*for (int j=0;j<4;j++){
			System.out.println(i+item.getList().get(j));
		}*/
		JDBCUpdate update = new JDBCUpdate("inventory",  item.getList().get(1).trim(), item.getList().get(2).trim(),quant+"", acost+"", "the land of make believe", ids[i]+"");
		
		}
	}
	public int[] getItemsID(){
		int [] items = new int [receiptBody.size()];
		for (int i = 0; i < receiptBody.size(); i++) {
			items[i]=Integer.parseInt(receiptBody.get(i).getId());
			
			}
		return items;
	}
//searches for receipt based on ID
	public String searchID(String ID){
		String search = "could not find ID";
		int searchListLength = receiptBody.size();
		for (int i = 0; i < searchListLength; i++) {
		if (receiptBody.get(i).getId().contains(ID)) {
		search = receiptBody.get(i).toString();
		}
		}
		return search;
	}
	/*
	public List<String> searchTime(String date){
		boolean isThere = false;
		List <String> search = new ArrayList<String>();
		int searchListLength = receiptBody.size();
		for (int i = 0; i < searchListLength; i++) {
		if (receiptBody.get(i).getDate().contains(date)) {
		isThere = true;
		search.add(receiptBody.get(i).toString());
		}
		}
		if(!isThere){
			search.add("There were no purchases on that date");
		}
		return search;
	}*/
	public double getTotal() {
		return total;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public List<ReceiptRow> getReceiptBody() {
		return receiptBody;
	}
	
	public String toString() {
		String output;
		output = "RECEIPT\n"
				+ "---------------------------------------------\n";
		for (int i = 0; i < receiptBody.size(); i++) {
			output = output + receiptBody.get(i).toString() + "\n";
		}
		output = output + "\nTotal Price: " + Double.toString(total) + "\n";
		output = output + "\nPayment Type: " + paymentMethod;
		return output;
	}
}
