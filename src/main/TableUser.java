package main;

//import main.Mainframe;
import main.UserList;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;



/**
 * @author Zerin Bates
 *
 */
public class TableUser extends JTable implements ActionListener{
	JTable UserTable;

// buttons
	JButton buttonId= new JButton("Id");
	JButton buttonName= new JButton("User Name");
	JButton buttonProv = new JButton("Password");
	JButton buttonPrice= new JButton("isAdmin");
	//misc
	Object [][] stuff;
	String[] columnNames = {"Id","Name","Password","isAdmin","Update User","Delete User"};
	UserList data = new UserList();
	//private local
	private TableColumn Id;
	private TableColumn nameUser;
	private TableColumn provider;
	private TableColumn price;
	
	private void buildTable(){
		
		stuff=new Object[data.daList.size()][7];
		for(int i=0;i<data.daList.size();i++){
			stuff[i]=data.stringUser(i);
			stuff[i][4]="edit row "+i;
			stuff[i][5]="delete row"+i;
		}
		dataModel = new DefaultTableModel(stuff,columnNames);
		UserTable.setModel(dataModel);

		//buttonstuff
		
		Id = UserTable.getColumnModel().getColumn(0);
		nameUser = UserTable.getColumnModel().getColumn(1);
		provider = UserTable.getColumnModel().getColumn(2);
		price = UserTable.getColumnModel().getColumn(3);

		Id.setHeaderRenderer(new EditableHeaderRenderer(buttonId));
		nameUser.setHeaderRenderer(new EditableHeaderRenderer(buttonName));
		provider.setHeaderRenderer(new EditableHeaderRenderer( buttonProv));
		price.setHeaderRenderer(new EditableHeaderRenderer( buttonPrice));
		UserTable.getColumn("Update User").setCellRenderer(new ButtonRenderer());
		UserTable.getColumn("Delete User").setCellRenderer(new ButtonRenderer());
	}
	public TableUser(){
		
		//Add headers to the Menu bar
		data.upload();
		stuff=new Object[data.daList.size()][7];
		for(int i=0;i<data.daList.size();i++){
			stuff[i]=data.stringUser(i);
			//JButton add = new JButton(i+"");
			//JButton edit = new JButton(i+"");
			
			stuff[i][4]="edit row "+i;
			stuff[i][5]="delete row"+i;
		}
		UserTable = new JTable(stuff,columnNames);
		//collumn buttons
		Id = UserTable.getColumnModel().getColumn(0);
		nameUser = UserTable.getColumnModel().getColumn(1);
		provider = UserTable.getColumnModel().getColumn(2);
		price = UserTable.getColumnModel().getColumn(3);

		
		Id.setHeaderRenderer(new EditableHeaderRenderer(buttonId));
		nameUser.setHeaderRenderer(new EditableHeaderRenderer(buttonName));
		provider.setHeaderRenderer(new EditableHeaderRenderer( buttonProv));
		price.setHeaderRenderer(new EditableHeaderRenderer( buttonPrice));
		UserTable.getColumn("Update User").setCellRenderer(new ButtonRenderer());
		UserTable.getColumn("Delete User").setCellRenderer(new ButtonRenderer());
		//initialize buttons
		buttonId.addActionListener(this);
		buttonName.addActionListener(this);
		buttonProv.addActionListener(this);
		buttonPrice.addActionListener(this);
		//cell listener
		UserTable.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
					UserTable.setColumnSelectionAllowed(true);
					UserTable.setRowSelectionAllowed(true);
					
				    if (e.getClickCount() == 1) {
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      int column = target.getSelectedColumn();
				     
				      if(column==5 ){
				       //System.out.println("stuff");
						data.deleteUser(row);
						buildTable();
				      }
				      if(column==4 ){
					       
				    	  
				    	  String []x=new String [4];
				    	  for (int i =1;i<4;i++){
				    		  x[i]=UserTable.getModel().getValueAt(row, i).toString();
				    	  }
				    	  
				    	  x[0]=stuff[row][0].toString();
				    	  x[3]=stuff[row][3].toString();
							data.editUser(row,x);
							buildTable();
					      } 
				    }
				  }
				});
	  }
	

	@Override
	public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		if (e.getSource() == buttonPrice) {
			data.adminSort();
			buildTable();
		}
		if (e.getSource() == buttonName) {
			data.nameSort();
			buildTable();
		}
		if (e.getSource() == buttonProv) {
			data.provideSort();
			buildTable();
		}

			
		}
	}
/*class ButtonRenderer extends JButton implements TableCellRenderer {

	  public ButtonRenderer() {
	    setOpaque(true);
	  }

	  public Component getTableCellRendererComponent(JTable table, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
	    if (isSelected) {
	      setForeground(table.getSelectionForeground());
	      setBackground(table.getSelectionBackground());
	    } else {
	      setForeground(table.getForeground());
	      setBackground(UIManager.getColor("Button.background"));
	    }
	    setText((value == null) ? "" : value.toString());
	    return this;
	  }
	}*/
