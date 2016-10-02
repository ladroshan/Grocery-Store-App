package main;

import main.MenuBar;
import database.JDBCSelect;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
 * This is the Mainframe Class for the ScanableGrocery program. 
 * It should be the first Class called in the final build.
 * 
 * @author Jacob Killpack
 * @version 1.4
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
	
	//This is the login button
	private JButton loginBtn, submit, checkout, inventory, users;
	
	//This is the JTextField for submitting a Username when logging in
	private JTextField uname;
	
	//This is the JPassword for logging in
	private JPasswordField pword;
	
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
		
		//Catch any possible errors in the code or unmapped cases
		else {
			JOptionPane.showMessageDialog(null, "There was an error in the paneEdit"
					+ " function. Please review the page that is being passed.", "THERE WAS AN ERROR!", 
					JOptionPane.ERROR_MESSAGE);
		}
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
			JOptionPane.showMessageDialog(null, "The Username you entered is NULL!", "Password Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		else if (username.length() > 40) {
			JOptionPane.showMessageDialog(null, "The Username you entered is too long!", "Password Error", 
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
		//Mainframe test = new Mainframe();
		//test.logout();
		
		Mainframe test = new Mainframe();
		test.pane.removeAll();
		test.frame.dispose();
		test.current = userType.ADMIN;
		test.paneEdit("main", "admin");
		test.reload();
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
		
		//If user clicks "Submit" on the login form, run a JDBCSelect Query based off of the username they 
		//entered and compare the password. If user exists and password is correct, log them in
		if (e.getSource() == submit) {
			//This has to be done with the password because of the way that JPasswordField stores data
			String passString = new String(pword.getPassword());
			
			//If the username and password entered validate then run JDBC Queries
			if (validateUsername(uname.getText()) && validatePassword(passString)) {
				
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
			else {
				//This is only reached if the username and password did not validate before being sent to the
				//JDBC Query Builder. Nothing needs to happen here because error windows were already cast
			}
		}
		
		//If User clicks the "Go" button for the Inventory from the main Admin page, load the frame with
		//the inventory settings
		if (e.getSource() == inventory) {
			
		}
		
		//If User clicks the "Go" button for the Users section from the main Admin page, load the frame with
		//the users settings
		if (e.getSource() == users) {
			//WORK NEEDED - This needs to be edited so that it contains code to get to the users page.
			
		}
		
		//If User clicks the "Go" button for the Checkout section from the main Cashier or Admin page, 
		//load the frame with the checkout layout
		if (e.getSource() == checkout) {
			//WORK NEEDED - This needs to be edited so that it contains code to get to the checkout page.
		}
				
	}
}