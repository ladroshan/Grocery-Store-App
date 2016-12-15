package main;

import main.Mainframe;
import main.ExcelBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


/**
 * This class is used to build the MenuBar that is added to the Mainframe.
 * It should only be called from the Mainframe.
 * The ActionListener is implemented so that button clicks to the Menu are kept out of the logic for the 
 * rest of the Mainframe.
 * 
 * @author Jacob Killpack
 * @version 1.8
 */
public class MenuBar extends JMenuBar implements ActionListener{
	
	//Java just wanted me to add this, so I did
	private static final long serialVersionUID = -1187579188283252389L;
	
	//The following variable declarations are organized for clarity. Please keep this order as items are added.
	//The JMenuBar object at the top is the main menu. 
	//The JMenu objects are all the initial headers that the user clicks. 
	//The JMenuItems are the options that are included in each JMenu (For example, logout and exit are both
	//options in the 'file' Menu)
	JMenuBar menuBar;
	JMenu file, reports, pages, about, help;
	JMenuItem logout, exit, read;
	JMenuItem generate;
	JMenuItem inventory, users, checkout;
	JMenuItem details;
	JMenuItem instructions, bugReport;

	
	//This is an instance of the Mainframe that has been imported so that the Menu can invoke some frame methods
	Mainframe frame;
	
	/**
	 * This is the initialization constructor that is called by the main method of this class.
	 * This constructor is only called on the initial build and then becomes obsolete as the Mainframe
	 * only calls the overloaded constructor that follows.  
	 */
	public MenuBar(){
		//Add headers to the Menubar
		file = new JMenu("File");
		this.add(file);
		reports = new JMenu("Reports");
		this.add(reports);
		pages = new JMenu("Pages");
		this.add(pages);
		about = new JMenu("About");
		this.add(about);
		help = new JMenu("Help");
		this.add(help);
		
		//Add the items to the file menu
		logout = new JMenuItem("Logout");
		logout.addActionListener(this);
		file.add(logout);
		
		read = new JMenuItem("Load Excel");
		read.addActionListener(this);
		file.add(read);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(exit);
		
		//Add the items to the reports menu
		generate = new JMenuItem("Generate Report");
		generate.addActionListener(this);
		reports.add(generate);
		
		//Add the items to the pages menu
		inventory = new JMenuItem("Inventory");
		inventory.addActionListener(this);
		pages.add(inventory);
		
		users = new JMenuItem("User Management");
		users.addActionListener(this);
		pages.add(users);
		
		checkout = new JMenuItem("Checkout");
		checkout.addActionListener(this);
		pages.add(checkout);
		
		//Add the items to the about menu
		details = new JMenuItem("About the Program");
		details.addActionListener(this);
		about.add(details);
		
		//Add the items to the help menu
		instructions = new JMenuItem("Instructions");
		instructions.addActionListener(this);
		help.add(instructions);
		
		bugReport = new JMenuItem("Report a Bug");
		bugReport.addActionListener(this);
		help.add(bugReport);
	}
	
	/**
	 * This is the overloaded constructor that is used by the Mainframe class to load the MenuBar.
	 * This has to be used from the Mainframe class so that this MenuBar can interact with the current frame.
	 * 
	 * @param mainframe - The instance of the mainframe that is currently being run.
	 */
	public MenuBar(Mainframe mainframe){
		//Set the frame variable to the Mainframe instance that was passed in
		frame = mainframe;
		
		//Add headers to the Menubar
		file = new JMenu("File");
		this.add(file);
		reports = new JMenu("Reports");
		this.add(reports);
		pages = new JMenu("Pages");
		this.add(pages);
		about = new JMenu("About");
		this.add(about);
		help = new JMenu("Help");
		this.add(help);
		
		//Add the items to the file menu
		logout = new JMenuItem("Logout");
		logout.addActionListener(this);
		file.add(logout);
		
		read = new JMenuItem("Read Excel");
		read.addActionListener(this);
		file.add(read);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(exit);
		
		//Add the items to the reports menu
		generate = new JMenuItem("Generate Report");
		generate.addActionListener(this);
		reports.add(generate);
		
		//Add the items to the pages menu
		inventory = new JMenuItem("Inventory");
		inventory.addActionListener(this);
		pages.add(inventory);
		
		users = new JMenuItem("User Management");
		users.addActionListener(this);
		pages.add(users);
		
		checkout = new JMenuItem("Checkout");
		checkout.addActionListener(this);
		pages.add(checkout);
		
		//Add the items to the about menu
		details = new JMenuItem("About the Program");
		details.addActionListener(this);
		about.add(details);
		
		//Add the items to the help menu
		instructions = new JMenuItem("Instructions");
		instructions.addActionListener(this);
		help.add(instructions);
		
		bugReport = new JMenuItem("Report a Bug");
		bugReport.addActionListener(this);
		help.add(bugReport);
	}
	
	/**
	 * This method is used to hide menu options that are not accessible by certain users.
	 * This method is called from the Mainframe when userType changes occur to ensure app security.
	 * 
	 * @param hide - The JMenu that will be affected by the change.
	 * @param subs - The JMenuItems that will be hidden. If a valid JMenuItem is not entered, the whole JMenu
	 * 				 will be hidden.
	 */
	protected void hideMenu(String hide, String subs) {
		//Check if the 'pages' JMenu is affected
		if (hide == "pages") {
			//Remove specified JMenuItems or the whole pages menu
			if (subs == "inventory"){
				pages.remove(inventory);
				file.remove(read);
			}
			else if (subs == "users"){
				pages.remove(users);
				file.remove(read);
			}
			else if (subs == "checkout"){
				pages.remove(checkout);
			}
			else if (subs == "all"){
				this.remove(pages);
				file.remove(read);
			}
		}
		
		//Check if the 'file' JMenu is affected
		if (hide == "file") {
			//Remove specified JMenuItems or the whole file menu
			if (subs == "logout") {
				file.remove(logout);
				
			}
			else {
				this.remove(file);
			}
		}
		
		//Check if the 'reports' JMenu is affected
		if (hide == "reports") {
			if (subs == "all") {
				this.remove(reports);
			}
		}
	}
	
	/**
	 * This method is used to restart the MenuBar with all items added in their proper order.
	 * This method is called from the Mainframe when userType changes occur to ensure app security.
	 * It is run before the hideMenu() method so that the menu order remains consistent and items don't just
	 * keep getting removed.
	 * 
	 * Remember: ORDER OF ELEMENT ADDING IS IMPORTANT HERE
	 */
	protected void restartMenu() {
		this.add(file);
		file.add(logout);
		file.add(read);
		file.add(exit);
		
		this.add(reports);
		reports.add(generate);
		
		this.add(pages);
		pages.add(inventory);
		pages.add(users);
		pages.add(checkout);
		
		this.add(about);
		about.add(details);
		
		this.add(help);
		help.add(instructions);
		help.add(bugReport);
	}

	/**
	 * Main method
	 * 
	 * @param args - None
	 */
	public static void main(String[] args) {
		//Initialize the MenuBar before the Mainframe is able to be passed in
		new MenuBar();
	}
	/*
	JMenu file, pages, about, help;
	JMenuItem logout, exit;
	JMenuItem inventory, users, checkout;
	JMenuItem details;
	JMenuItem instructions, bugReport;
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		//If user clicks "Logout", invoke the logout method from the Mainframe
		if (e.getSource() == logout) {
			frame.logout();
		}
		if (e.getSource() == read) {
			frame.read();
		}
		
		//If user clicks "Exit", close the program
		if (e.getSource() == exit) {
			System.exit(0);
		}
		
		//If user clicks "Generate Report", run the report generation page
		if (e.getSource() == generate) {
			frame.loadReports();
		}
		
		//If user clicks "Inventory", reload the frame with the inventory layout
		if (e.getSource() == inventory) {
			frame.loadInventory();
		}

		//If user clicks "Users", reload the frame with the users layout
		if (e.getSource() == users) {
			frame.loadUsers();
		}

		//If user clicks "Checkout", reload the frame with the checkout layout
		if (e.getSource() == checkout) {
			frame.loadCheckOut();
		}
		
		//If the user clicks "Details", show the development information for the app in a separate window
		if (e.getSource() == details) {
			JOptionPane.showMessageDialog(null, "This is the ScanableGrocery program.\n\nCreated"
					+ " September 27, 2016.\n1st released October 2,"
					+ " 2016.\n\nCreators: Jacob Killpack and Zerin Bates\n", "About", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		
		//If the user clicks "Instructions", show a list of instructions for how to use the app in a separate 
		//window
		if (e.getSource() == instructions) {
			JOptionPane.showMessageDialog(null, "How To Use ScanableGrocery:\n1. Press 'Login"
					+ "\n2. Enter the Username and Password and click Submit. If you are unable to"
					+ " login, contact a Manager.\n3. Enjoy the program!", "Instructions", 
					JOptionPane.INFORMATION_MESSAGE);
		}
		
		//If the user clicks "Submit a Bug Report", show instructions for how to contact the developers in a 
		//separate window
		if (e.getSource() == bugReport) {
			JOptionPane.showMessageDialog(null, "To submit a bug report, please do the following:\n1."
					+ "Take a screen shot of the errors you are getting. \n2. Send an email with the "
					+ "screenshot attached to scanablegrocery@gmail.com \n3. "
					+ "Include in the email body a description of the error you are "
					+ "experiencing.", "Instructions", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
