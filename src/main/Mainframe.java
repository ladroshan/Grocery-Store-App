package main;

import main.MenuBar;
import main.tableItems;
import main.TableUser;
import database.JDBCInsert;
import database.JDBCSelect;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


/**
 * This is the Mainframe Class for the ScanableGrocery program. 
 * It should be the first Class called in the final build.
 * 
 * @author Jacob Killpack
 * @version 1.23
 */
public class Mainframe implements ActionListener{
	
	//This is the JFrame that runs the program.
	private JFrame frame =  new JFrame("ScanableGrocery");
	
	//This is the pane in the JFrame
	private Container pane = frame.getContentPane();
	
	//This is the instance of the Menubar being created. The menu object is being initialized with the 
	//overloaded constructor to make the Menubar editable later in this program.
	//The parameter being passed is this Mainframe instance.
	MenuBar menu = new MenuBar(this);
	
	//NONUSER = User who has not logged in
	//CASHIER = User who has logged in but does not have Admin privileges
	//ADMIN = User who has logged in and does have Admin privileges
	private enum userType { NONUSER, CASHIER, ADMIN };
	
	//This variable is to track which form of user is using the application
	private userType current;
	
	//private JLabel ltotalfinal;
	
	//This is the login button
	private JButton loginBtn, submit, genRep, checkout, ckReceipt, ckOrder, inventory, users, invAdd, invEd, invDe, usrAdd, usrEd, usrDe; 
	private JButton newSubmit, dealOrNoDeal, addItem, next, searchInv, searchId, searchDate; //, payNow;
	
	//This is the JTextField for submitting a Username when logging in
	private JTextField uname, dope, dealer, grams, benjis, enterItem, searchBar;
	
	private String operand, useId = "7";
	
	//This is the JPassword for logging in
	private JPasswordField pword;
	
	private JRadioButton yeah, nope, searchPicker1, searchPicker2;
	
	private ButtonGroup searchType, radios;
	
	private final String[] calDays = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
									  "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
									  "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
									  "30", "31"};

	private final String[] calMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
										"November", "December", "YOU SUCK"};
	
	private final String[] calYears = {"1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", 
									   "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013",
									   "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025" };

	
	//These JComboBoxes are for Date selection on the report generation page
	@SuppressWarnings("rawtypes")
	private JComboBox dayList1, monthList1, yearList1, dayList2, monthList2, yearList2;
	
	private List<ReceiptRow> receiptBody = new ArrayList<ReceiptRow>();
	
	/**
	 * Initialization constructor for Mainframe
	 * This should only run once on initial load
	 */
	public Mainframe(){
		//This is initializing the current user to NONUSER since no one is logged in when the app starts
		current = userType.CASHIER;
		
		//Check what type of user is logged in and use that information to build the view they need
		checkUser();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = frame.getContentPane();
		
		//This Layout Manager is more important later in the pane design methods
		pane.setLayout(new GridBagLayout());
		
		//Add the MenuBar to the frame
		frame.setJMenuBar(menu);
		frame.pack();
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * This method is designed for reloading the frame when a new page is visited.
	 */
	private void reload(){
		//Check what type of user is logged in and use that information to build the view they need
		checkUser();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		//Add the MenuBar to the frame
		frame.setJMenuBar(menu);		
		frame.pack();
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	
	/**
	 * This method is used to set up the new layouts for the different pages that are visited
	 * by the user. 
	 * 
	 * @param page - The main page or main function that is being handled
	 * @param section - The subpages or views that are variations on the main page
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void paneEdit(String page, String section) {
		if(page.equals("main")){
			//Design the view that will display right when the Admin logs in
			if (section.equals("admin")) {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel buttonHolder = new JPanel();
				buttonHolder.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(45, 45, 10, 45);
				
				//Create and initialize some variables *SIDE NOTE* The reason why inventory, users, and checkout
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel label1 = new JLabel("Work With Your Inventory:");
				JLabel label2 = new JLabel("Manage Users:");
				JLabel label3 = new JLabel("Checkout a Cart:");
				
				inventory = new JButton("Go");
				users = new JButton("Go");
				checkout = new JButton("Go");
				
				//This listener runs the JDBC Queries to check the password
				inventory.addActionListener(this);
				users.addActionListener(this);
				checkout.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 0;
				design.gridy = 1;
				buttonHolder.add(label1, design);
				design.gridx = 0;
				design.gridy = 2;
				buttonHolder.add(inventory, design);
				design.gridx = 1;
				design.gridy = 1;
				buttonHolder.add(label2, design);
				design.gridx = 1;
				design.gridy = 2;
				buttonHolder.add(users, design);
				design.gridx = 2;
				design.gridy = 1;
				buttonHolder.add(label3, design);
				design.gridx = 2;
				design.gridy = 2;
				buttonHolder.add(checkout, design);
				pane.add(buttonHolder);
			}
			
			//Design the view that will display right when a cashier logs in
			else if (section.equals("cashier")) {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel buttonHolder = new JPanel();
				buttonHolder.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(15, 15, 15, 15);
				
				//Create and initialize some variables *SIDE NOTE* The reason why uname, pword, and submit
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel label = new JLabel("Checkout a Cart:");
				checkout = new JButton("Go");
				
				//This listener runs the JDBC Queries to check the password
				checkout.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 1;
				design.gridy = 1;
				buttonHolder.add(label, design);
				design.gridx = 1;
				design.gridy = 2;
				buttonHolder.add(checkout, design);
				pane.add(buttonHolder);
			}
		}
		else if(page.equals("login")) {
			//Design the view for the Login function that just has a Login Button
			if (section.equals("button")) {
				loginBtn = new JButton("Login");
				loginBtn.addActionListener(this);
				pane.add(loginBtn);
			}
			
			//Design the view that has the Login form with username/password fields to submit
			else if (section.equals("form")){
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel loginForm = new JPanel();
				loginForm.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(15, 15, 15, 15);
				
				//Create and initialize some variables *SIDE NOTE* The reason why uname, pword, and submit
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel luser = new JLabel("Username: ");
				JLabel lpass = new JLabel("Password: ");
				uname = new JTextField(20);
				pword = new JPasswordField(20);
				submit = new JButton("Submit");
				
				//This listener runs the JDBC Queries to check the password
				submit.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 0;
				design.gridy = 0;
				loginForm.add(luser, design);
				design.gridx = 0;
				design.gridy = 1;
				loginForm.add(lpass, design);
				design.gridx = 2;
				design.gridy = 0;
				loginForm.add(uname, design);
				design.gridx = 2;
				design.gridy = 1;
				loginForm.add(pword, design);
				design.gridx = 1;
				design.gridy = 2;
				loginForm.add(submit, design);
				pane.add(loginForm);
			}
		}
		else if (page == "inventory") {
			if (section == "menu") {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel buttonHolder = new JPanel();
				buttonHolder.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(30, 30, 10, 30);
				
				//Create and initialize some variables *SIDE NOTE* The reason why inventory, users, and checkout
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel label1 = new JLabel("Add Item to Inventory:");
				JLabel label2 = new JLabel("Edit Item from Inventory:");
				JLabel label3 = new JLabel("Delete Item from Inventory:");
				
				invAdd = new JButton("Go");
				invEd = new JButton("Go");
				invDe = new JButton("Go");
				
				//This listener runs the JDBC Queries to check the password
				invAdd.addActionListener(this);
				invEd.addActionListener(this);
				invDe.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 0;
				design.gridy = 1;
				buttonHolder.add(label1, design);
				design.gridx = 0;
				design.gridy = 2;
				buttonHolder.add(invAdd, design);
				design.gridx = 1;
				design.gridy = 1;
				buttonHolder.add(label2, design);
				design.gridx = 1;
				design.gridy = 2;
				buttonHolder.add(invEd, design);
				design.gridx = 2;
				design.gridy = 1;
				buttonHolder.add(label3, design);
				design.gridx = 2;
				design.gridy = 2;
				buttonHolder.add(invDe, design);
				pane.add(buttonHolder);
			}
			else if (section == "add") {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel newInventory = new JPanel();
				newInventory.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(10, 10, 10, 10);
				
				//Create and initialize some variables *SIDE NOTE* The reason why uname, pword, and submit
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel ldope = new JLabel("Product Name: ");
				JLabel ldealer = new JLabel("Provider: ");
				JLabel lgrams = new JLabel("Quantity: ");
				JLabel lbenjis = new JLabel("Price: ");
				dope = new JTextField(20);
				dealer = new JTextField(20);
				grams = new JTextField(20);
				benjis = new JTextField(20);
				pword = new JPasswordField(20);
				dealOrNoDeal = new JButton("Submit");
				
				//This listener runs the JDBC Queries to check the password
				dealOrNoDeal.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 0;
				design.gridy = 0;
				newInventory.add(ldope, design);
				design.gridx = 0;
				design.gridy = 1;
				newInventory.add(ldealer, design);
				design.gridx = 0;
				design.gridy = 2;
				newInventory.add(lgrams, design);
				design.gridx = 0;
				design.gridy = 3;
				newInventory.add(lbenjis, design);
				design.gridx = 1;
				design.gridy = 0;
				newInventory.add(dope, design);
				design.gridx = 1;
				design.gridy = 1;
				newInventory.add(dealer, design);
				design.gridx = 1;
				design.gridy = 2;
				newInventory.add(grams, design);
				design.gridx = 1;
				design.gridy = 3;
				newInventory.add(benjis, design);
				design.gridx = 1;
				design.gridy = 4;
				newInventory.add(dealOrNoDeal, design);
				pane.add(newInventory);
			}
			else if (section == "edit") {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel checkOutLeft = new JPanel();
				JPanel checkOutRight = new JPanel();
				JTextField enterItem = new JTextField();
				tableItems table = new tableItems();
				Dimension textDim = new Dimension(100, 25);
				enterItem.setMaximumSize(textDim);
				enterItem.setAlignmentX(BoxLayout.X_AXIS);
				searchBar = new JTextField();
				searchInv = new JButton("Search");
				searchInv.addActionListener(this);
				
				pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
				checkOutLeft.setLayout(new BoxLayout(checkOutLeft, BoxLayout.Y_AXIS));
				
				pane.add(Box.createRigidArea(new Dimension(100,0)));
				JScrollPane scrollPane = new JScrollPane(table.itemTable);
				checkOutLeft.add(scrollPane,BoxLayout.X_AXIS);
				JPanel filler1 = new JPanel();
				JPanel filler2 = new JPanel();
				JPanel filler3 = new JPanel();
				JPanel filler4 = new JPanel();
				JPanel filler5 = new JPanel();
				JPanel filler6 = new JPanel();
				searchPicker1 = new JRadioButton("By Name");
				searchPicker1.setSelected(true);
				searchPicker2 = new JRadioButton("By ID");
				searchType = new ButtonGroup();
				searchType.add(searchPicker1);
				searchType.add(searchPicker2);
				checkOutRight.setLayout(new GridLayout(15, 1, 5, 5));
				checkOutRight.add(filler1);
				checkOutRight.add(filler3);
				checkOutRight.add(filler4);
				checkOutRight.add(filler5);
				checkOutRight.add(filler6);
				checkOutRight.add(searchBar);
				checkOutRight.add(searchInv);
				checkOutRight.add(filler2);
				checkOutRight.add(searchPicker1);
				checkOutRight.add(searchPicker2);
				
				
				checkOutLeft.setAlignmentX((float) 10.0);
				pane.add(checkOutLeft);
				pane.add(checkOutRight);
			}
		}
		else if (page == "users") {
			if (section == "menu") {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel buttonHolder = new JPanel();
				buttonHolder.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(30, 30, 10, 30);
				
				//Create and initialize some variables *SIDE NOTE* The reason why inventory, users, and checkout
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel label1 = new JLabel("Add User to Database:");
				JLabel label2 = new JLabel("Edit User from Database:");
				JLabel label3 = new JLabel("Delete User from Database:");
				
				usrAdd = new JButton("Go");
				usrEd = new JButton("Go");
				usrDe = new JButton("Go");
				
				//This listener runs the JDBC Queries to check the password
				usrAdd.addActionListener(this);
				usrEd.addActionListener(this);
				usrDe.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 0;
				design.gridy = 1;
				buttonHolder.add(label1, design);
				design.gridx = 0;
				design.gridy = 2;
				buttonHolder.add(usrAdd, design);
				design.gridx = 1;
				design.gridy = 1;
				buttonHolder.add(label2, design);
				design.gridx = 1;
				design.gridy = 2;
				buttonHolder.add(usrEd, design);
				design.gridx = 2;
				design.gridy = 1;
				buttonHolder.add(label3, design);
				design.gridx = 2;
				design.gridy = 2;
				buttonHolder.add(usrDe, design);
				pane.add(buttonHolder);
			}
			else if (section == "add") {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel newUser = new JPanel();
				newUser.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(15, 15, 15, 15);
				
				//Create and initialize some variables *SIDE NOTE* The reason why uname, pword, and submit
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel luser = new JLabel("New Username: ");
				JLabel lpass = new JLabel("New Password: ");
				JLabel ladmin = new JLabel("Does this user have Admin privileges?");
				uname = new JTextField(20);
				pword = new JPasswordField(20);
				newSubmit = new JButton("Submit");
				yeah = new JRadioButton("Yes", false);
				nope = new JRadioButton("No", true);
				radios = new ButtonGroup();
				radios.add(yeah);
				radios.add(nope);
				
				//This listener runs the JDBC Queries to check the password
				yeah.addActionListener(this);
				nope.addActionListener(this);
				newSubmit.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 0;
				design.gridy = 0;
				newUser.add(luser, design);
				design.gridx = 0;
				design.gridy = 1;
				newUser.add(lpass, design);
				design.gridx = 2;
				design.gridy = 0;
				newUser.add(uname, design);
				design.gridx = 2;
				design.gridy = 1;
				newUser.add(pword, design);
				design.gridx = 1;
				design.gridy = 3;
				newUser.add(newSubmit, design);
				design.gridx = 0;
				design.gridy = 2;
				design.anchor = GridBagConstraints.WEST;
				newUser.add(ladmin, design);
				design.gridx = 2;
				design.gridy = 2;
				newUser.add(nope, design);
				design.gridx = 1;
				design.gridy = 2;
				design.anchor = GridBagConstraints.EAST;
				newUser.add(yeah, design);
				pane.add(newUser);
			}
			else if (section == "edit") {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel checkOutLeft = new JPanel();
				JPanel checkOutRight = new JPanel();
				JTextField enterItem = new JTextField();
				TableUser table = new TableUser();
				Dimension textDim = new Dimension(100, 25);
				enterItem.setMaximumSize(textDim);
				enterItem.setAlignmentX(BoxLayout.X_AXIS);
				
				pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
				checkOutLeft.setLayout(new BoxLayout(checkOutLeft, BoxLayout.Y_AXIS));
				
				pane.add(Box.createRigidArea(new Dimension(100,0)));
				JScrollPane scrollPane = new JScrollPane(table.UserTable);
				checkOutLeft.add(scrollPane,BoxLayout.X_AXIS);
				
				checkOutLeft.setAlignmentX((float) 10.0);
				pane.add(checkOutLeft);
				pane.add(checkOutRight);
			}
		}
		else if(page == "reports") {
			if (section == "menu") {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel buttonHolder = new JPanel();
				buttonHolder.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(30, 30, 10, 30);
				
				//Create and initialize some variables *SIDE NOTE* The reason why inventory, users, and checkout
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel label1 = new JLabel("Start Date:");
				JLabel label2 = new JLabel("End Date:");
	
				dayList1 = new JComboBox(calDays);
				monthList1 = new JComboBox(calMonths);
				yearList1 = new JComboBox(calYears);
	
				dayList2 = new JComboBox(calDays);
				monthList2 = new JComboBox(calMonths);
				yearList2 = new JComboBox(calYears);
				
				genRep = new JButton("Generate Report");
				
				//This listener runs the JDBC Queries to check the password
				genRep.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 0;
				design.gridy = 1;
				buttonHolder.add(label1, design);
				design.gridx = 1;
				design.gridy = 1;
				buttonHolder.add(dayList1, design);
				design.gridx = 2;
				design.gridy = 1;
				buttonHolder.add(monthList1, design);
				design.gridx = 3;
				design.gridy = 1;
				buttonHolder.add(yearList1, design);
				design.gridx = 0;
				design.gridy = 2;
				buttonHolder.add(label2, design);
				design.gridx = 1;
				design.gridy = 2;
				buttonHolder.add(dayList2, design);
				design.gridx = 2;
				design.gridy = 2;
				buttonHolder.add(monthList2, design);
				design.gridx = 3;
				design.gridy = 2;
				buttonHolder.add(yearList2, design);
				design.gridx = 2;
				design.gridy = 4;
				buttonHolder.add(genRep, design);
				
				pane.add(buttonHolder);
			}
		}
		else if(page == "checkout") {
			if (section == "menu") {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel buttonHolder = new JPanel();
				buttonHolder.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(30, 30, 10, 30);
				
				//Create and initialize some variables *SIDE NOTE* The reason why inventory, users, and checkout
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel label1 = new JLabel("Search Receipts:");
				JLabel label2 = new JLabel("Start Order:");
				
				ckReceipt = new JButton("Go");
				ckOrder = new JButton("Go");
				
				//This listener runs the JDBC Queries to check the password
				ckReceipt.addActionListener(this);
				ckOrder.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 0;
				design.gridy = 1;
				buttonHolder.add(label1, design);
				design.gridx = 0;
				design.gridy = 2;
				buttonHolder.add(ckReceipt, design);
				design.gridx = 2;
				design.gridy = 1;
				buttonHolder.add(label2, design);
				design.gridx = 2;
				design.gridy = 2;
				buttonHolder.add(ckOrder, design);
				
				pane.add(buttonHolder);
			}
			else if (section == "receipt") {
					//A JPanel is used in this section because the positioning of the form items later doesn't 
					//work if they are just directly added to the pane container. 
					JLabel label1 = new JLabel("Search for a Receipt");
					JPanel buttonHolder = new JPanel();
					buttonHolder.setLayout(new GridBagLayout());
					
					//This is necessary for the positioning code farther down in this if statement
					GridBagConstraints design = new GridBagConstraints();
					design.insets = new Insets(30, 30, 10, 30);
					
					//Create and initialize some variables *SIDE NOTE* The reason why inventory, users, and checkout
					//are declared at the header of this class is so that they can be used in the Listener code		
					dayList1 = new JComboBox(calDays);
					monthList1 = new JComboBox(calMonths);
					yearList1 = new JComboBox(calYears);
		
					searchBar = new JTextField(10);
					searchBar.setBounds(2, 3, 200, 50);
					
					searchDate = new JButton("Search By Date");
					searchId = new JButton("Search By ID");
					
					searchDate.addActionListener(this);
					searchId.addActionListener(this);
					
					genRep = new JButton("Generate Report");
					
					//This listener runs the JDBC Queries to check the password
					genRep.addActionListener(this);
					
					//Positioning for the different parts of the form
					design.gridx = 3;
					design.gridy = 0;
					buttonHolder.add(label1, design);
					design.gridx = 1;
					design.gridy = 2;
					buttonHolder.add(dayList1, design);
					design.gridx = 2;
					design.gridy = 2;
					buttonHolder.add(monthList1, design);
					design.gridx = 3;
					design.gridy = 2;
					buttonHolder.add(yearList1, design);
					design.gridx = 5;
					design.gridy = 2;
					buttonHolder.add(searchDate, design);
					design.gridx = 2;
					design.gridy = 3;
					buttonHolder.add(searchBar, design);
					design.gridx = 5;
					design.gridy = 3;
					buttonHolder.add(searchId, design);
					
					pane.add(buttonHolder);
			}
			else if (section == "order") {
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel checkOutLeft = new JPanel();
				JPanel checkOutRight = new JPanel();
				
				Object[][] info=new Object[receiptBody.size()][5];
				
				for (int i = 0; i < receiptBody.size(); i++){
					info[i][0]=receiptBody.get(i).getId();
					info[i][1]=receiptBody.get(i).getName();
					info[i][2]=receiptBody.get(i).getAmount();
					info[i][3]=receiptBody.get(i).getCost();
					info[i][4]= "delete";
					
				}
				String[] collums= {"ID",   "ITEM",   "AMOUNT",  "COST", "REMOVE"};
				JTable checkoutList = new JTable(info,collums);
				//building table 
				checkoutList.getColumn("REMOVE").setCellRenderer(new ButtonRenderer());
				checkoutList.addMouseListener(new MouseAdapter() {
					  public void mouseClicked(MouseEvent e) {
						  checkoutList.setColumnSelectionAllowed(true);
						  checkoutList.setRowSelectionAllowed(true);
							
						    if (e.getClickCount() == 1) {
						    	
						      JTable target = (JTable)e.getSource();
						      int row = target.getSelectedRow();
						      int column = target.getSelectedColumn();
						     
						      if(column==4 ){
						    	  
						    	  receiptBody.remove(row);
						    	  //need to switch when merge
						    	  System.out.println("wtf");
						    	  loadOrder();
						      }
						      
						    }
						}   
					  });
				@SuppressWarnings("unused")
				JScrollPane thingy = new JScrollPane(checkoutList);
			
				//checkoutList.setEditable(false);
				addItem = new JButton("Add Item");
				next = new JButton("     Pay    ");
				addItem.addActionListener(this);
				next.addActionListener(this);
				enterItem = new JTextField();
				//tableItems table = new tableItems();
				Dimension textDim = new Dimension(100, 25);
				enterItem.setMaximumSize(textDim);
				enterItem.setAlignmentX(BoxLayout.X_AXIS);
				
				pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
				checkOutLeft.setLayout(new BoxLayout(checkOutLeft, BoxLayout.Y_AXIS));
				checkOutRight.setBackground(Color.WHITE);
				checkOutRight.add(checkoutList);
				pane.add(Box.createRigidArea(new Dimension(100,0)));
				checkOutLeft.add(enterItem);
				checkOutLeft.add(addItem);
				checkOutLeft.add(next);
				//JScrollPane scrollPane = new JScrollPane(table.itemTable);
				//checkOutLeft.add(scrollPane,BoxLayout.X_AXIS);
				
				checkOutLeft.setAlignmentX((float) 10.0);
				pane.add(checkOutLeft);
				pane.add(checkOutRight);
			}
			else if (section == "payment") {
				/*
				//A JPanel is used in this section because the positioning of the form items later doesn't 
				//work if they are just directly added to the pane container. 
				JPanel newInventory = new JPanel();
				newInventory.setLayout(new GridBagLayout());
				
				//This is necessary for the positioning code farther down in this if statement
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(10, 10, 10, 10);
				
				//Create and initialize some variables *SIDE NOTE* The reason why uname, pword, and submit
				//are declared at the header of this class is so that they can be used in the Listener code
				JLabel lpayment = new JLabel("Method of ment: ");
				JLabel ltotal = new JLabel("Total Price: ");
				payNow = new JButton("Pay Now");
				
				//This listener runs the JDBC Queries to check the password
				payNow.addActionListener(this);
				
				//Positioning for the different parts of the form
				design.gridx = 0;
				design.gridy = 0;
				newInventory.add(lpayment, design);
				design.gridx = 0;
				design.gridy = 1;
				newInventory.add(ltotal, design);
				design.gridx = 2;
				design.gridy = 0;
				newInventory.add(payment, design);
				design.gridx = 2;
				design.gridy = 1;
				newInventory.add(ltotalfinal, design);
				design.gridx = 1;
				design.gridy = 2;
				newInventory.add(payNow, design);
				pane.add(newInventory);
				*/
			}
		}
		//Catch any possible errors in the code or unmapped cases
		else {
			JOptionPane.showMessageDialog(null, "There was an error in the paneEdit"
					+ " function. Please review the page that is being passed.", "THERE WAS AN ERROR!", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/*private void loadPayment() {
		pane.removeAll();
		frame.dispose();
		paneEdit("checkout", "payment");
		reload();
	}*/
	
	
	private void loadUserChange() {
		pane.removeAll();
		frame.dispose();
		paneEdit("users", "edit");
		reload();
	}
	
	private void loadInventoryChange() {
		pane.removeAll();
		frame.dispose();
		paneEdit("inventory", "edit");
		reload();
	}
	
	protected void loadReports() {
		pane.removeAll();
		frame.dispose();
		paneEdit("reports", "menu");
		reload();
	}
	
	protected void loadCheckOut() {
		pane.removeAll();
		frame.dispose();
		paneEdit("checkout", "menu");
		reload();
	}
	
	private void loadOrder() {
		pane.removeAll();
		frame.dispose();
		paneEdit("checkout", "order");
		reload();
	}
	
	private void loadReceipt() {
		pane.removeAll();
		frame.dispose();
		paneEdit("checkout", "receipt");
		reload();
	}

	private void loadUsrAdd() {
		pane.removeAll();
		frame.dispose();
		paneEdit("users", "add");
		reload();
	}
	
	protected void loadUsers() {
		pane.removeAll();
		frame.dispose();
		paneEdit("users", "menu");
		reload();
	}
	
	private void loadInvAdd() {
		pane.removeAll();
		frame.dispose();
		paneEdit("inventory", "add");
		reload();
	}
	
	protected void loadInventory() {
		pane.removeAll();
		frame.dispose();
		paneEdit("inventory", "menu");
		reload();
	}
	
	/**
	 * This method is for logging out of the app. 
	 * It is called primarily from the MenuBar when the "Logout" item on the File menu is selected. However, 
	 * there is a call of this method in the main function for this class as well to set up an initial frame. 
	 */
	protected void logout() {
		//set user to 'logged out' status
		current = userType.NONUSER;
		
		//Clear current view and load the login page with limited menu access
		pane.removeAll();
		frame.dispose();
		pane.setLayout(new GridBagLayout());
		paneEdit("login", "button");
		reload();
	}
	
	/**
	 * This method is for checking the userType and setting up the MenuBar with the menu items
	 * the user is allowed access to.
	 */
	private void checkUser() {
		if (current == userType.NONUSER){
			//restartMenu() sets up the MenuBar with all options so that the necessary items can be removed next
			menu.restartMenu();
			menu.hideMenu("pages", "all");
			menu.hideMenu("file", "logout");
		}
		else if (current == userType.CASHIER) {
			//restartMenu() sets up the MenuBar with all options so that the necessary items can be removed next
			menu.restartMenu();
			menu.hideMenu("pages", "inventory");
			menu.hideMenu("pages", "users");
		}
		else if (current == userType.ADMIN) {
			//restartMenu() sets up the MenuBar with all options because the Admin has full access.
			menu.restartMenu();
		}
		else {
			//NULL. NONUSER, CASHIER, and ADMIN should be the only userTypes for now
		}
	}
	
	private void generateReport(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		@SuppressWarnings("unused")
		JDBCSelect report = new JDBCSelect("receipts", "date", startCal, endCal);
		for (int i = 0; i < JDBCSelect.getList().size(); i++) {
			System.out.println(JDBCSelect.getList().get(i));
		}
	}

	/**
	 * This method is used to make sure that the code that user tries submitting to the database is valid
	 * and will not cause problems or errors in the code. It is also a protection against code injection.
	 * 
	 * @param passString - A String representation of the password that was entered for comparison
	 * @return TRUE - if the password is valid
	 */
	private boolean validatePassword (String passString) {
		//Create and initialize variables
		boolean validates, hasUpper, hasLower, specChar, hasNumber;
		validates = false;
		hasUpper = !passString.equals(passString.toLowerCase());
		hasLower = !passString.equals(passString.toUpperCase());
		
		//The matches() method thats being called here is using regular expressions to make sure there are
		//at least 1 special character and at least 1 digit 0 - 9
		specChar = passString.matches(".*[!@#$%^&*()_+=<>,.:;?~].*");
		hasNumber = passString.matches(".*[0-9].*");
		
		//Make sure password field is not empty or too big
		if (passString.length() < 1) {
			JOptionPane.showMessageDialog(null, "The Password you entered is NULL!", "Password Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		else if (passString.length() > 40) {
			JOptionPane.showMessageDialog(null, "The Password you entered is too long!", "Password Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		//**IMPORTANT** I am thinking about separating these password rules an just putting them into
		//the User Management code for when users are created. (If you try removing them, don't remove 
		//this whole else statement. Just the nested if/else statements inside it with validates = true;
		else {
			if (!hasUpper) {
				JOptionPane.showMessageDialog(null, "The Password you entered needs an "
						+ "Uppercase letter!", "Password Error", 
						JOptionPane.ERROR_MESSAGE);
			}
			else if(!hasLower) {
				JOptionPane.showMessageDialog(null, "The Password you entered needs a "
						+ "Lowercase letter!", "Password Error", 
						JOptionPane.ERROR_MESSAGE);					
			}
			else if (!specChar) {
				JOptionPane.showMessageDialog(null, "The Password you entered need a "
						+ "special character!", "Password Error", 
						JOptionPane.ERROR_MESSAGE);
			}
			else if (!hasNumber) {
				JOptionPane.showMessageDialog(null, "The Password you entered needs a "
						+ "number!", "Password Error", 
						JOptionPane.ERROR_MESSAGE);
			} 
			
			//If all error cases fail, then password validates
			else {
				validates = true;
			}
		}
		return validates;
	}
	
	/**
	 * This method is used to make sure that the code that user tries submitting to the database is valid
	 * and will not cause problems or errors in the code. It is also a protection against code injection.
	 * 
	 * @param username - A String representation of the username that was entered by the user
	 * @return TRUE - if the username is valid
	 */
	private boolean validateUsername (String username) {
		//Create and initialize variable
		boolean validates;
		validates = false;
		
		//Make sure username field is not empty or too big
		if (username.length() < 1) {
			JOptionPane.showMessageDialog(null, "The Username you entered is NULL!", "Username Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		else if (username.length() > 40) {
			JOptionPane.showMessageDialog(null, "The Username you entered is too long!", "Username Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		else {
			validates = true;
		}
		return validates;
	}

	/**
	 * Main method
	 * 
	 * @param args - None
	 */
	public static void main(String[] args) {
		//To keep the logout function in a separate method, the program initializes to a logged-in view and then 
		//immediately is logged out *SIDE NOTE* While it starts logged-in, it still starts with NONUSER 
		//privileges

		//ExcelBuilder temp = new ExcelBuilder();
		//temp.excelReader();

		Mainframe test = new Mainframe();
		test.logout();

		//Mainframe test = new Mainframe();
		//test.pane.removeAll();
		//test.frame.dispose();
		//test.current = userType.ADMIN;
		//test.paneEdit("main", "admin");
		//test.reload();
	}
	
	@SuppressWarnings("static-access") //Not sure why, but Java told me to add this SuppressWarnings tag
	@Override //This one is necessary
	public void actionPerformed(ActionEvent e) {		
		//If user clicks "Login", bring up the login form
		if (e.getSource() == loginBtn) {
			pane.removeAll();
			frame.dispose();
			paneEdit("login", "form");
			reload();
		}

		//If user clicks "Yes" to toggle radio buttons 
		if (e.getSource() == yeah) {
			yeah.setSelected(true);
		}
		
		//if user clicks "No" to toggle radio buttons
		if (e.getSource() == nope) {
			nope.setSelected(true);
		}
		
		//If user clicks "Submit" on the New User form, parse the 3 inputs into Strings, test them for 
		//validation, and submit them to the database.
		if (e.getSource() == newSubmit) {
			String user = uname.getText();
			String pass = new String(pword.getPassword());
			String admin;
			if (yeah.isSelected()) {
				admin = "true";
			}
			else {
				admin = "false";
			}
			if (validateUsername(user) && validatePassword(pass)){
				user = user.trim();
				pass = pass.trim();
				
				JDBCSelect existest = new JDBCSelect("users", "username", "'" + user + "'");
				if (existest.getList().size() > 0){
					JOptionPane.showMessageDialog(null, "The User you are trying to create already exists!"
							+ " Please choose another username.", "User Conflict!", 
							JOptionPane.ERROR_MESSAGE);
					existest.getList().clear();
				}
				else {
					JDBCInsert newGuy = new JDBCInsert("users", user, pass, admin);
					newGuy.getList().clear();
					existest.getList().clear();
					loadUsers();
				}
			}			
		}
		
		//If user clicks "Submit" on the login form, run a JDBCSelect Query based off of the username they 
		//entered and compare the password. If user exists and password is correct, log them in
		if (e.getSource() == submit) {
			//This has to be done with the password because of the way that JPasswordField stores data
			String passString = new String(pword.getPassword());
			
				//Run a Select Query on the 'users' table where the username field is equal to the entered username
				JDBCSelect getUser = new JDBCSelect("users", "username", "'" + uname.getText() + "'");
				
				
				//getUser() is a getter method of JDBCSelect that returns a vector of string data from the Query
				//results. If the Query failed, the vector will be empty.
				if ((getUser.getList()).size() == 0) {
					JOptionPane.showMessageDialog(null, "The Username or Password that you have"
							+ " entered is incorrect!", "Username Error", 
							JOptionPane.ERROR_MESSAGE);
				}
				else {					
					//This gets rid of whitespace before and after that may be before and after the entered pass
					passString = passString.trim();
					
					//The getUser() Vector should just store 1 row of the 'users' table at a time. There should
					//be 4 entries for each row with the following indices: 0-id, 1-username, 2-password, 3-is_admin
					if (passString.equals((getUser).getList().get(2).trim())) {
						useId = getUser.getList().get(0);
						JOptionPane.showMessageDialog(null, "You have been logged in as "
								+ uname.getText(), "Login Success!", 
								JOptionPane.INFORMATION_MESSAGE);
						
						//If user that is being logged in is an Admin, log them in as Admin type
						if((getUser).getList().get(3).trim().equals("t")){
							pane.removeAll();
							frame.dispose();
							current = userType.ADMIN;
							paneEdit("main", "admin");
							reload();
						}
						
						//If user is logging in but is not marked as an Admin, log in with Cashier privileges
						else {
							pane.removeAll();
							frame.dispose();
							current = userType.CASHIER;
							paneEdit("main", "cashier");
							reload();
						}
					}
					
					//Throw this error if the Password doesn't verify as correct.
					else {
						JOptionPane.showMessageDialog(null, "The Username or Password that you entered was"
								+ " wrong!", "Password Error", 
								JOptionPane.ERROR_MESSAGE);
						current = userType.NONUSER;
					}
					
					//Empty the User data that you queried so it doesn't create problems or become vulnerable 
					//data
					(getUser).getList().clear();
				}
		}
		
		//If User clicks the "Go" button for the Inventory from the main Admin page, load the frame with
		//the inventory settings
		if (e.getSource() == inventory) {
			loadInventory();
		}
		
		//If User clicks the "Go" button for the Add Item from the Inventory Menu, load the Add Item form
		if (e.getSource() == invAdd) {
			loadInvAdd();
		}
		
		//If User clicks the "Go" button for the Edit Item from the Inventory Menu, load the Edit Item form
		if (e.getSource() == invEd) {
			loadInventoryChange();
		}
		
		//If User clicks the "Go" button for the Search Item from the Inventory Menu, load the Search Item form
		if (e.getSource() == invDe) {
			loadInventoryChange();
		}
		
		//If User clicks the "Go" button for the Users section from the main Admin page, load the frame with
		//the users settings
		if (e.getSource() == users) {
			loadUsers();
		}
		
		//If User clicks the "Go" button for the Add User from the User Menu, load the Add user form
		if (e.getSource() == usrAdd) {
			loadUsrAdd();
		}

		//If User clicks the "Go" button for the Edit User from the User Menu, load the Edit Item form
		if (e.getSource() == usrEd) {
			loadUserChange();
		}

		//If User clicks the "Go" button for the Delete User from the User Menu, load the Delete Item form
		if (e.getSource() == usrDe) {
			loadUserChange();
		}
		
		//If User clicks the "Go" button for the Checkout section from the main Cashier or Admin page, 
		//load the frame with the checkout layout
		if (e.getSource() == checkout) {
			loadCheckOut();
		}
		
		//This is for inserting data into the inventory table
		if (e.getSource() == dealOrNoDeal) {
			String test, test2;
			test = grams.getText();
			test2 = benjis.getText();
			if (test.matches(".*[!@#$%^&*()_+=,<>:;?~].*") || test.matches(".*[A-Z][a-z].*") || 
					test2.matches(".*[!@#$%^&*()_+=,<>:;?~].*") || test2.matches(".*[A-Z][a-z].*")) {
				JOptionPane.showMessageDialog(null, "You entered data into the quantity or the price"
						+ " that is not valid!", "INVALID INPUT", 
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				JDBCInsert addToTheKitty = new JDBCInsert("inventory", dope.getText(), dealer.getText(), 
						grams.getText(), benjis.getText());
				addToTheKitty.getList().clear();
				loadInventory();
			}
			
		}
		
		if (e.getSource() == next) {
			
			double printTotal = 0;
			String printItAlready;
			for(int i = 0; i < receiptBody.size(); i++) {
				printTotal += Double.parseDouble(receiptBody.get(i).getCost());
				System.out.println(printTotal);
				
			}
			printItAlready = Double.toString(printTotal);
			
			String payment = JOptionPane.showInputDialog(
					null, "Thank you for your purchase! Your total is $" + printItAlready
					+ "\nWhat type of payment would you like to use?");
			
			Receipt done = new Receipt(printTotal, payment, receiptBody);
			//changing item stuff
			
			if(done.updateInv()){
			JOptionPane.showMessageDialog(null, done.toString(), "PROOF OF PURCHASE", JOptionPane.DEFAULT_OPTION);

			

			@SuppressWarnings("unused")
			JDBCInsert ImAwesome=new JDBCInsert(true, done.getBody(), done.getTotal()+"", useId);
			receiptBody.clear();
			loadCheckOut();
		}

			}
		
		if (e.getSource() == addItem) {
			JDBCSelect buildReceipt = new JDBCSelect("inventory", "id", enterItem.getText());
			String input;
			double calculate;
			input = JOptionPane.showInputDialog(null, "How many would you like?\nThere are only " + 
					buildReceipt.getList().get(3).toString() + " left.");
			if (Integer.parseInt(input) > Integer.parseInt(buildReceipt.getList().get(3))) {
				JOptionPane.showMessageDialog(null, "There are not that many available!", "Request Denied", 
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				operand = buildReceipt.getList().get(4);
				operand = operand.substring(1);
				char[] removeCommas = operand.toCharArray();
				for (int j = 0; j < operand.length(); j++) {
					if (removeCommas[j] == ',') {
						operand = operand.substring(0, (j)) + operand.substring(j + 1);
						removeCommas = operand.toCharArray();
					}
				}
				calculate = Double.parseDouble(operand);
				calculate *= Integer.parseInt(input);
				ReceiptRow addition = new ReceiptRow(buildReceipt.getList().get(0), buildReceipt.getList().get(1),
						input, Double.toString(calculate));
				System.out.println(receiptBody.add(addition));
			}
			
			buildReceipt.getList().clear();
			loadOrder();
		}
		
		if (e.getSource() == ckOrder) {
			new ItemList().upload();
			loadOrder();
		}
		
		if (e.getSource() == ckReceipt) {
			loadReceipt();
		}
		
		if (e.getSource() == genRep) {
			String date1 = monthList1.getSelectedItem().toString() + " " + dayList1.getSelectedItem().toString() + ", " + yearList1.getSelectedItem().toString();
			String date2 = monthList2.getSelectedItem().toString() + " " + dayList2.getSelectedItem().toString() + ", " + yearList2.getSelectedItem().toString();
			DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
			Date startDate, endDate;
			try {
				startDate = format.parse(date1);
				endDate = format.parse(date2);
				
				if (startDate.getTime() > endDate.getTime()) {
					JOptionPane.showMessageDialog(null, "The Start Date Must be less than the End Date! Please try again.", "DATE ERROR", 
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					generateReport(startDate, endDate);
					if (JDBCSelect.getReportList().getSize() > 0) {
						String printReport = JDBCSelect.getReportList().toString();
						JOptionPane.showMessageDialog(null, printReport, "Report for " + date1 + " - " + date2, 
								JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "There was no data stored for generating a report in the range of" 
								+ date1 + " - " + date2, "Report Generation Error", JOptionPane.ERROR_MESSAGE);
					}
					System.out.println(startDate);
					System.out.println(endDate);
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "There was a Date Parsing Error", "DATE ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (e.getSource() == searchInv) {
			Item searchedItem = new Item();
			if (searchPicker1.isSelected()) {
				if(searchBar.getText().isEmpty() == false){
					String testMe = searchBar.getText().trim();
					testMe = "'".concat(testMe).concat("'");
					@SuppressWarnings("unused")
					JDBCSelect findItem = new JDBCSelect("inventory", "producttype", testMe);
					if (JDBCSelect.getList().size() != 0) {
						searchedItem.setID(JDBCSelect.getList().get(0));
						searchedItem.setName(JDBCSelect.getList().get(1));
						searchedItem.setProvider(JDBCSelect.getList().get(2));
						searchedItem.setQuantity(JDBCSelect.getList().get(3));
						searchedItem.setPrice(JDBCSelect.getList().get(4));
						JOptionPane.showMessageDialog(null, "Search Results for '" + searchBar.getText().trim() + "':\n" + 
								searchedItem.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
						JDBCSelect.getList().clear();
					}
					else {
						JOptionPane.showMessageDialog(null, "No Inventory Items match the text, '" + searchBar.getText().trim() + "', that was "
								+ "searched", "Search Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Cannot perform a search with a blank submission!", 
							"Search Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (searchPicker2.isSelected()) {
				if(searchBar.getText().isEmpty() == false){
					@SuppressWarnings("unused")
					JDBCSelect findItem = new JDBCSelect("inventory", "id", searchBar.getText().trim());
					if (JDBCSelect.getList().size() != 0) {
						searchedItem.setID(JDBCSelect.getList().get(0));
						searchedItem.setName(JDBCSelect.getList().get(1));
						searchedItem.setProvider(JDBCSelect.getList().get(2));
						searchedItem.setQuantity(JDBCSelect.getList().get(3));
						searchedItem.setPrice(JDBCSelect.getList().get(4));
						JOptionPane.showMessageDialog(null, "Search Results for '" + searchBar.getText().trim() + "':\n" + 
								searchedItem.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
						JDBCSelect.getList().clear();
					}
					else {
						JOptionPane.showMessageDialog(null, "No Inventory Items match the ID, '" + searchBar.getText().trim() + "', that was "
								+ "searched", "Search Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Cannot perform a search with a blank submission!", 
							"Search Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "There was an error with the search method selection", "Search Error", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (e.getSource() == searchDate) {
			String date = monthList1.getSelectedItem().toString() + " " + dayList1.getSelectedItem().toString() + ", " + yearList1.getSelectedItem().toString();
			DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
			Date searchByDate = null;
			try {
				searchByDate = format.parse(date);
				System.out.println(searchByDate);

				Calendar searchCal = Calendar.getInstance();
				searchCal.setTime(searchByDate);
				date = "'" + searchCal.get(Calendar.YEAR) + "-" + (searchCal.get(Calendar.MONTH) + 1) + "-" + searchCal.get(Calendar.DATE) 
					 + "'";
				
				Receipt searchedOrder = new Receipt();
				@SuppressWarnings("unused")
				JDBCSelect findItem = new JDBCSelect("receipts", "date", date);
				if (JDBCSelect.getList().size() != 0) {
					String body;
					searchedOrder.setId(JDBCSelect.getList().get(0));
					body = JDBCSelect.getList().get(1);
					searchedOrder.setTotal(JDBCSelect.getList().get(2));
					searchedOrder.setCashierId((JDBCSelect.getList().get(3)));
					searchedOrder.setDate(JDBCSelect.getList().get(4));
					JOptionPane.showMessageDialog(null, "Search Results for '" + searchBar.getText().trim() + "':\n" + 
							searchedOrder.toString(true, body), "Search Results", JOptionPane.INFORMATION_MESSAGE);
					JDBCSelect.getList().clear();
				}
				else {
					JOptionPane.showMessageDialog(null, "No Inventory Items match the Date, '" + date + "', that was "
							+ "searched", "Search Error", JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (ParseException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "There was a Date Parsing Error", "DATE ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (e.getSource() == searchId) {
			Receipt searchedOrder = new Receipt();
			@SuppressWarnings("unused")
			JDBCSelect findItem = new JDBCSelect("receipts", "id", searchBar.getText().trim());
			if (JDBCSelect.getList().size() != 0) {
				String body;
				searchedOrder.setId(JDBCSelect.getList().get(0));
				body = JDBCSelect.getList().get(1);
				searchedOrder.setTotal(JDBCSelect.getList().get(2));
				searchedOrder.setCashierId((JDBCSelect.getList().get(3)));
				searchedOrder.setDate(JDBCSelect.getList().get(4));
				JOptionPane.showMessageDialog(null, "Search Results for '" + searchBar.getText().trim() + "':\n" + 
						searchedOrder.toString(true, body), "Search Results", JOptionPane.INFORMATION_MESSAGE);
				JDBCSelect.getList().clear();
			}
			else {
				JOptionPane.showMessageDialog(null, "No Inventory Items match the ID, '" + searchBar.getText().trim() + "', that was "
						+ "searched", "Search Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
