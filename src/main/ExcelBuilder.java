/*
***THIS HAS BEEN COMMENTED OUT FOR THE TIME BEING BECAUSE IT DOES NOT APPEAR TO BE WORKING OR IT IS NOT FINISHED!***
package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import database.JDBCInsert;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
public class ExcelBuilder {
	private String filePath="C:\\Users\\zerin\\desktop\\readfile.xls";
	public void setFilePath (String n){
		filePath =n;
	}
	@SuppressWarnings("deprecation")
	public void excelReader(){
try {
			
			FileInputStream file = new FileInputStream(new File(filePath));
			
			//Get the workbook instance for XLS file 
			HSSFWorkbook workbook = new HSSFWorkbook(file);

			//Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			//Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				//For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				int i=-2;
				String []holder=null;
				while(cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					
					switch(cell.getCellType()) {
						case Cell.CELL_TYPE_BOOLEAN:
							//System.out.print(cell.getBooleanCellValue() + "\t\t");
							break;
						case Cell.CELL_TYPE_NUMERIC:
							//System.out.print(cell.getNumericCellValue() + "\t\t");
							break;
						case Cell.CELL_TYPE_STRING:
							//System.out.print(cell.getStringCellValue() + "\t\t");
							break;

					}
					//creates inventory
					if (row.getRowNum()==0){
						//System.out.println(i);
						if (cell.toString().equals("id")){
							//System.out.println(cell.toString());
							i=3;
							holder = new String [4];
						}else if (i>=0){
							holder[3-i]=cell.toString();
							i--;
					      }

						 if (i==-1){
							 //System.out.println(holder[0]+holder[1]+holder[2]+holder[3]);
							 int squints =(int)(Double.parseDouble(holder[2]));
							 @SuppressWarnings("unused")
							JDBCInsert wendyPefercorn =new JDBCInsert("inventory", holder[0], holder[1], squints+"", holder[3]);
					      }
				     }
					//creates users
					if (row.getRowNum()==1){
						//System.out.println(i);
						if (cell.toString().equals("id")){
							//System.out.println(cell.toString());
							i=2;
							holder = new String [3];
						}else if (i>=0){
							holder[2-i]=cell.toString();
							i--;
					      }

						 if (i==-1){
							// System.out.println(holder[0]+holder[1]+holder[2]);
							String squints ="'"+holder[2]+"'";
							 @SuppressWarnings("unused")
							JDBCInsert wendyPefercorn =new JDBCInsert("users", holder[0], holder[1], squints);
					      }
				     }
					//creates receipts
					if (row.getRowNum()==2){
						System.out.println(i);
						if (cell.toString().equals("id")){
							System.out.println(cell.toString());
							i=3;
							holder = new String [4];
						}else if (i>=0){
							holder[3-i]=cell.toString();
							i--;
					      }

						 if (i==-1){
							 System.out.println(holder[0]+holder[1]+holder[2]+holder[3]);
							 int squints =(int)(Double.parseDouble(holder[2]));
							 String smalls="'"+holder[3]+"'";
							 @SuppressWarnings("unused")
							JDBCInsert wendyPefercorn =new JDBCInsert( true,holder[0], holder[1], squints+"",smalls );
					      }
				     }
				//end of cell iterator
				}
				

							
				
				System.out.println("");
			}
			file.close();
			 workbook.close();
//			FileOutputStream out = 
//				new FileOutputStream(new File("C:\\test.xls"));
//			workbook.write(out);
//			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
}}
*/
