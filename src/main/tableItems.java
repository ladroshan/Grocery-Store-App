package main;

//import main.Mainframe;
import main.ItemList;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.Item;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;



/**
 * @author Zerin Bates
 *
 */
public class tableItems extends JTable implements ActionListener{
	JTable itemTable;

// buttons
	JButton buttonId= new JButton("Id");
	JButton buttonName= new JButton("Item Name");
	JButton buttonProv = new JButton("Provider");
	JButton buttonPrice= new JButton("Price");
	JButton buttonQuant = new JButton("Quantity");
	//misc
	Object [][] stuff;
	String[] columnNames = {"Id","Name","Provider","Price","Quantity","Update Item","Delete Item"};
	ItemList data = new ItemList();
	//private local
	private TableColumn Id;
	private TableColumn nameItem;
	private TableColumn provider;
	private TableColumn price;
	private TableColumn quantity;
	
	private void buildTable(){
		
		stuff=new Object[data.daList.size()][7];
		for(int i=0;i<data.daList.size();i++){
			stuff[i]=data.stringItem(i);
			stuff[i][5]="edit row "+i;
			stuff[i][6]="delete row"+i;
		}
		dataModel = new DefaultTableModel(stuff,columnNames);
		itemTable.setModel(dataModel);

		//buttonstuff
		
		Id = itemTable.getColumnModel().getColumn(0);
		nameItem = itemTable.getColumnModel().getColumn(1);
		provider = itemTable.getColumnModel().getColumn(2);
		price = itemTable.getColumnModel().getColumn(3);
		quantity = itemTable.getColumnModel().getColumn(4);

		Id.setHeaderRenderer(new EditableHeaderRenderer(buttonId));
		nameItem.setHeaderRenderer(new EditableHeaderRenderer(buttonName));
		provider.setHeaderRenderer(new EditableHeaderRenderer( buttonProv));
		price.setHeaderRenderer(new EditableHeaderRenderer( buttonPrice));
		quantity.setHeaderRenderer(new EditableHeaderRenderer( buttonQuant));
		itemTable.getColumn("Update Item").setCellRenderer(new ButtonRenderer());
		itemTable.getColumn("Delete Item").setCellRenderer(new ButtonRenderer());
	}
	public tableItems(){
		
		//Add headers to the Menu bar
		data.upload();
		stuff=new Object[data.daList.size()][7];
		for(int i=0;i<data.daList.size();i++){
			stuff[i]=data.stringItem(i);
			//JButton add = new JButton(i+"");
			//JButton edit = new JButton(i+"");
			
			stuff[i][5]="edit row "+i;
			stuff[i][6]="delete row"+i;
		}
		itemTable = new JTable(stuff,columnNames);
		//collumn buttons
		Id = itemTable.getColumnModel().getColumn(0);
		nameItem = itemTable.getColumnModel().getColumn(1);
		provider = itemTable.getColumnModel().getColumn(2);
		price = itemTable.getColumnModel().getColumn(3);
		quantity = itemTable.getColumnModel().getColumn(4);

		
		Id.setHeaderRenderer(new EditableHeaderRenderer(buttonId));
		nameItem.setHeaderRenderer(new EditableHeaderRenderer(buttonName));
		provider.setHeaderRenderer(new EditableHeaderRenderer( buttonProv));
		price.setHeaderRenderer(new EditableHeaderRenderer( buttonPrice));
		quantity.setHeaderRenderer(new EditableHeaderRenderer( buttonQuant));
		itemTable.getColumn("Update Item").setCellRenderer(new ButtonRenderer());
		itemTable.getColumn("Delete Item").setCellRenderer(new ButtonRenderer());
		//initialize buttons
		buttonId.addActionListener(this);
		buttonName.addActionListener(this);
		buttonProv.addActionListener(this);
		buttonPrice.addActionListener(this);
		buttonQuant.addActionListener(this);
		//cell listener
		itemTable.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
					itemTable.setColumnSelectionAllowed(true);
					itemTable.setRowSelectionAllowed(true);
					
				    if (e.getClickCount() == 1) {
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      int column = target.getSelectedColumn();
				     
				      if(column==6 ){
				       //System.out.println("stuff");
						data.deleteItem(row, (int)stuff[row][0]);
						buildTable();
				      }
				      if(column==5 ){
					       //System.out.println("stuff");
				    	  
				    	  String []x=new String [5];
				    	  for (int i =1;i<5;i++){
				    		  x[i]=itemTable.getModel().getValueAt(row, i).toString();
				    	  }
				    	  x[0]=stuff[row][0].toString();
							data.editItem(row,x);
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
			data.priceSort();
			buildTable();
		}
		if (e.getSource() == buttonId) {
				//System.out.println("stuff");
				data.idSort();
				buildTable();
			}
		if (e.getSource() == buttonName) {
			data.nameSort();
			buildTable();
		}
		if (e.getSource() == buttonQuant) {
			data.quantSort();
			buildTable();
		}
		if (e.getSource() == buttonProv) {
			data.provideSort();
			buildTable();
		}

			
		}
	}
class ButtonRenderer extends JButton implements TableCellRenderer {

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
	}

		