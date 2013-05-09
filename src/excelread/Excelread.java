
package excelread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 *
 * @author bretbartlett
 */
public class Excelread {
    
        public static class INGRED {
            public String number;
            public String ingredient;
            //constructor
            public void setINGREDIENT(String ingredient1) {
                this.ingredient = ingredient1;
            }
            
            public void setNumber (String Number){
                this.number = Number;
            }
            
            public String getIng (){
                return this.ingredient;
            }
            
            public String getNumber(){
                return this.number;
            }
            
        }

    
    public static List<INGRED> main() throws FileNotFoundException, IOException {
            
    List<INGRED> IngString = new ArrayList<INGRED>();
  
    
    String inputFile = "/Users/bretbartlett/Documents/Kitchology/alpha.xls";

    File inputWorkbook = new File(inputFile);
    Workbook w;
        
    try {
      w = Workbook.getWorkbook(inputWorkbook);
      // Get the first sheet
      
       for (int k = 0; k < w.getNumberOfSheets() ; k++) {
            Sheet sheet = w.getSheet(k);  
            for (int i = 0; i < sheet.getRows(); i++) {
            INGRED tmpIng = new INGRED(); 
            Cell cell = sheet.getCell(1, i);
            Cell cell0 = sheet.getCell(0, i);
            CellType type = cell.getType();
            CellType type0 = cell0.getType();
               if (type == CellType.LABEL) {
                  tmpIng.setINGREDIENT(cell.getContents().toLowerCase());
               }
               if (type0 == CellType.LABEL) {
                  tmpIng.setNumber(cell0.getContents());
               }
               IngString.add(tmpIng);
        }
    }
    } catch (BiffException e) {
    }
 
        
        return sortNames.SortIngredients(IngString);        
    
    }
    
    public static List<INGRED> getBeta() throws FileNotFoundException, IOException {
        
    List<INGRED> IngString = new ArrayList<INGRED>();
    
    String inputFile = "/Users/bretbartlett/Documents/Kitchology/beta.xls";

    File inputWorkbook = new File(inputFile);
    Workbook w;
    
    try {
      w = Workbook.getWorkbook(inputWorkbook);
      // Get the first sheet
      
      for (int k = 0; k < w.getNumberOfSheets() ; k++) {
          
      Sheet sheet = w.getSheet(k);  
      for (int i = 0; i < sheet.getRows(); i++) {
            INGRED tmpIng = new INGRED(); 
            Cell cell = sheet.getCell(1, i);
            Cell cell0 = sheet.getCell(0, i);
            CellType type = cell.getType();
            CellType type0 = cell0.getType();
               if (type == CellType.LABEL) {
                  tmpIng.setINGREDIENT(cell.getContents().toLowerCase());
               }
               if (type0 == CellType.LABEL) {
                  tmpIng.setNumber(cell0.getContents());
               }
               IngString.add(tmpIng);
        }
    }
    } catch (BiffException e) {
    }

        return sortNames.SortIngredients(IngString);
        
        
    }
    
   /* public void excelWrite() throws IOException, WriteException{
        
        WritableWorkbook workbook = Workbook.createWorkbook(new FileOutputStream("AFHDAJKDJKFAHLKFJHADSL.xls"));
        
        WritableSheet sheet = workbook.createSheet("Sheet0", 0);

        Label label = new Label(0, 2, "A label record"); 
        sheet.addCell(label); 
        
        workbook.write(); 
        workbook.close();
    
    
    }
    * */
}
