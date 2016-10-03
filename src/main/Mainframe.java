package main;

import main.MenuBar;
import main.TableUser;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 * @author Jacob Killpack
 *
 */
public class Mainframe{
	private JPanel itemTable = new JPanel();
	private JFrame frame =  new JFrame("ScanableGrocery");
	private Container pane;
	MenuBar menu = new MenuBar();
	TableUser table = new TableUser();
	public Mainframe(){

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = frame.getContentPane();	
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		itemTable.setLayout(new BorderLayout());
		frame.setJMenuBar(menu);
		//stuff
		JScrollPane scrollPane = new JScrollPane(table.UserTable);
		table.setFillsViewportHeight(true);
		//scrollPane.setPreferredSize(new Dimension(400,400));
		pane.add(scrollPane,BoxLayout.X_AXIS);
		//itemTable.add(scrollPane);
		//scrollPane.setVisible(true);
		//itemTable.add(table.getTableHeader(), BorderLayout.PAGE_START);
		//itemTable.add(table, BorderLayout.CENTER);
		//pane.add(itemTable, BoxLayout.X_AXIS);
		
		
		frame.pack();
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Mainframe();
	}

}
