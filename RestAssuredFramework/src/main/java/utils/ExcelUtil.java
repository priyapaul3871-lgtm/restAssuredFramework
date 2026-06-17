package utils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.*;


//This class is used to open excel, read excel and return data.
public class ExcelUtil {
    public static List<Map<String, String>> readSheet(String sheetName) throws Exception {       //One row becomes Map<String,String> whereas multiple rows become List<Map<String,String>>
        List<Map<String, String>> data = new ArrayList<>();       //This will store all excel rows. Why List? Ans: Excel has multiple rows and List stores multiple rows. Why Map? Ans: Each row has column and value and Map stores value in key-value pairs.
        FileInputStream fis = new FileInputStream("TestData/UserData.xlsx");  //FileInputStream is used to open Excel file.
        Workbook workbook = new XSSFWorkbook(fis);     //Workbook represents the entire Excel file.
        Sheet sheet = workbook.getSheet(sheetName);    //Sheet represents one sheet/tab inside Excel.
        //System.out.println(sheet.getSheetName());    //Prints the sheet name

        //Apache POI represents every Excel row using the Row interface. Here, sheet.getRow(0) returns the first row object. Therefore, the return type is Row.
        Row headerRow = sheet.getRow(0);            //Read 1st row (i.e. Header/Column Row). Here, headerRow variable contains Cell0 (TCID), Cell1(Endpoint), Cell2(Name), Cell3(Job), Cell4(ExpectedStatus).


        int totalRows = sheet.getLastRowNum();       //It will give total count of rows starting from Row 0 (i.e. Header Row).
        DataFormatter formatter = new DataFormatter();

        for(int i=1; i<=totalRows; i++) {            //Row loop reads every row starting from row 1.
            Row dataRow = sheet.getRow(i);           // Here, dataRow contains the entire row starting from Row 1.
            Map<String, String> rowData = new LinkedHashMap<>();   //LinkedHashMap preserves Excel column order whereas HashMap does not guarantee insertion order.
            int totalColumns = headerRow.getLastCellNum();   // It will give total count of columns.

            for (int j = 0; j < totalColumns; j++) {  //Column loop reads every columns.

                String columnName = formatter.formatCellValue(headerRow.getCell(j));   //Reads Column names
                String columnValue = formatter.formatCellValue(dataRow.getCell(j));    //Reads column values
                rowData.put(columnName, columnValue);   //Store in Map as key-value pairs.

            }
            data.add(rowData);      // Adds all the rows into the List
        }
           //Close the resources which releases memory and file handles.
            workbook.close();
            fis.close();

            return data;  //return() used to return the data so that other methods can use it.
        }
    }

