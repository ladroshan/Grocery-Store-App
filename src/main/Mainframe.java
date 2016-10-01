package main;

import main.MenuBar;

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
	
	/**
	 * Initialization constructor for Mainframe
	 * 
	 */
	public Mainframe(){
		current = userType.ADMIN;
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
		if(page == "login") {
			if (section == "button") {
				loginBtn = new JButton("Login");
				loginBtn.addActionListener(this);
				pane.add(loginBtn);
			}
			else if (section == "form"){
				JPanel loginForm = new JPanel();
				loginForm.setLayout(new GridBagLayout());
				GridBagConstraints design = new GridBagConstraints();
				design.insets = new Insets(15, 15, 15, 15);
				JLabel luser = new JLabel("Username: ");
				JLabel lpass = new JLabel("Password: ");
				JTextField uname = new JTextField(20);
				JPasswordField pword = new JPasswordField(20);
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
			menu.hideMenu("pages", "all");
			menu.hideMenu("file", "logout");
		}
		else if (current == userType.CASHIER) {
			menu.hideMenu("pages", "inventory");
			menu.hideMenu("pages", "users");
		}
		else if (current == userType.ADMIN) {
			//Don't hide any options from the ADMIN
		}
		else {
			//NULL
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Mainframe();
		//Mainframe test = new Mainframe();
		//test.logout();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginBtn) {
			pane.removeAll();
			frame.dispose();
			paneEdit("login", "form");
			reload();
		}
		if (e.getSource() == submit) {
			
		}
	}
	/**
	 * REMOVE ME!! This method was used for testing only.
	 */
	protected void cash() {
		current = userType.CASHIER;
		pane.removeAll();
		frame.dispose();
		paneEdit("cashier", "button");
		reload();
	}

}
