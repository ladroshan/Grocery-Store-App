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
 * @author Jacob Killpack
 *
 */
public class Mainframe implements ActionListener{
	
	//This is the JFrame that runs the program.
	private JFrame frame =  new JFrame("ScanableGrocery");
	
	//This is the pane in the JFrame
	private Container pane;
	
	//This is the instance of the Menubar being created. The menu object is being initialized with the 
	//overloaded constructor to make the Menubar editable later in this program.
	//The parameter being passed is this Mainframe instance.
	MenuBar menu = new MenuBar(this);
	
	//NONUSER is just someone who is not logged in.
	private enum userType { NONUSER, CASHIER, ADMIN };
	
	//This variable is to track which form of user is using the application
	private userType current;
	
	//This is the login button
	private JButton loginBtn, submit;
	
	//REMOVE ME!! label is a test JLabel
	private JLabel label;
	
	private JTextField uname;
	
	private JPasswordField pword;
	
	/**
	 * Initialization constructor for Mainframe
	 * 
	 */
	public Mainframe(){
		current = userType.NONUSER;
		checkUser();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = frame.getContentPane();
		pane.setLayout(new GridBagLayout());
		frame.setJMenuBar(menu);
		frame.pack();
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void reload(){
		checkUser();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = frame.getContentPane();
		pane.setLayout(new GridBagLayout());
		frame.setJMenuBar(menu);
		frame.pack();
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void paneEdit(String page, String section) {
		if(page.equals("login")) {
			if (section.equals("button")) {
				loginBtn = new JButton("Login");
				loginBtn.addActionListener(this);
				pane.add(loginBtn);
			}
			else if (section.equals("form")){
				JPanel loginForm = new JPanel();
				loginForm.setLayout(new GridBagLayout());
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(15, 15, 15, 15);
				JLabel luser = new JLabel("Username: ");
				JLabel lpass = new JLabel("Password: ");
				uname = new JTextField(20);
				pword = new JPasswordField(20);
				submit = new JButton("Submit");
				submit.addActionListener(this);
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
		//REMOVE ME!! This else statement was used for testing only. 
		else if (page == "cashier") {
			label = new JLabel("This is the Cashier Page");
			pane.add(label);
		}
		else {
			JOptionPane.showMessageDialog(null, "There was an error in the paneEdit"
					+ " function. Please review the page that is being passed.", "THERE WAS AN ERROR!", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	protected void logout() {
		//set user to 'logged out' status
		current = userType.NONUSER;
		pane.removeAll();
		frame.dispose();
		paneEdit("login", "button");
		reload();
	}
	
	private void checkUser() {
		if (current == userType.NONUSER){
			menu.restartMenu();
			menu.hideMenu("pages", "all");
			menu.hideMenu("file", "logout");
		}
		else if (current == userType.CASHIER) {
			menu.restartMenu();
			menu.hideMenu("pages", "inventory");
			menu.hideMenu("pages", "users");
		}
		else if (current == userType.ADMIN) {
			//Don't hide any options from the ADMIN
			menu.restartMenu();
		}
		else {
			//NULL
		}
	}

	private boolean validatePassword (String passString) {
		boolean validates, hasUpper, hasLower, specChar, hasNumber;
		validates = false;
		hasUpper = !passString.equals(passString.toLowerCase());
		hasLower = !passString.equals(passString.toUpperCase());
		specChar = passString.matches(".*[!@#$%^&*()_+=<>,.:;?~].*");
		hasNumber = passString.matches(".*[0-9].*");
		if (passString.length() < 1) {
			JOptionPane.showMessageDialog(null, "The Password you entered is NULL!", "Password Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		else if (passString.length() > 40) {
			JOptionPane.showMessageDialog(null, "The Password you entered is too long!", "Password Error", 
					JOptionPane.ERROR_MESSAGE);
		}
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
			else {
				validates = true;
			}
		}
		return validates;
	}
	
	private boolean validateUsername (String username) {
		boolean validates;
		validates = false;
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
	 * @param args
	 */
	public static void main(String[] args) {
		//new Mainframe();
		Mainframe test = new Mainframe();
		test.logout();
	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginBtn) {
			pane.removeAll();
			frame.dispose();
			paneEdit("login", "form");
			reload();
		}
		if (e.getSource() == submit) {
			String passString = new String(pword.getPassword());
			if (validateUsername(uname.getText()) && validatePassword(passString)) {
				//Run JDBC stuff
				JDBCSelect getUser = new JDBCSelect("users", "username", "'" + uname.getText() + "'");
				if ((getUser.getList()).size() == 0) {
					JOptionPane.showMessageDialog(null, "The Username or Password that you have"
							+ " entered is incorrect!", "Username Error", 
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					passString = passString.trim();
					if (passString.equals((getUser).getList().get(2).trim())) {
						JOptionPane.showMessageDialog(null, "You have been logged in as "
								+ uname.getText(), "Login Success!", 
								JOptionPane.INFORMATION_MESSAGE);
						if((getUser).getList().get(3).trim().equals("t")){
							pane.removeAll();
							frame.dispose();
							current = userType.ADMIN;
							reload();
						}
						else {
							pane.removeAll();
							frame.dispose();
							current = userType.CASHIER;
							reload();
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "The Username or Password that you entered was"
								+ " wrong!", "Password Error", 
								JOptionPane.ERROR_MESSAGE);
						current = userType.NONUSER;
					}
					(getUser.getList()).clear();
				}
			}
			else {
				System.out.println("You didn't validate...");
			}
		}
	}
}
