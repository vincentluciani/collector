package reader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelReader {

    Workbook workbook;

    //https://www.callicoder.com/java-read-excel-file-apache-poi/

    public static final String SAMPLE_XLSX_FILE_PATH = "C:\\Users\\sesa201795\\Documents\\Copy of Completed - Translation of categories - 09-01-20206VL8-1.xlsm";

    // Creating a Workbook from an Excel file (.xls or .xlsx)

    public ExcelReader() throws IOException, InvalidFormatException {

        this.workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        // Obtain a sheetIterator and iterate over it
        /*Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
            getL1labels(sheet.getSheetName());
        }*/

        for ( int sheetIndex=2; sheetIndex<workbook.getNumberOfSheets(); sheetIndex++ ){
            getF1F2labels(sheetIndex);
        }

        // Closing the workbook
    }

    public void getF1F2labels(int sheetIndex){
        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        String sheetName = sheet.getSheetName();

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++){

            Row row = sheet.getRow(rowIndex);
            Cell cellF1 = row.getCell(1);
            String testValue="";
            if (cellF1!=null) {
                String labelValue = cellF1.getStringCellValue();
                if (labelValue.length() > 1) {
                    testValue = String.format("%s;%s;%s", sheetName, cellF1.getStringCellValue(),"F1");
                    System.out.println(testValue);
                }
            }

            Cell cellF2 = row.getCell(3);
            testValue="";
            if (cellF1!=null) {
                String labelValue = cellF2.getStringCellValue();
                if (labelValue.length() > 1) {
                    testValue = String.format("%s;%s;%s", sheetName, cellF2.getStringCellValue(),"F2");
                    System.out.println(testValue);
                }
            }

        }


    }

    void processL1label(String language){



    }
    public void browseAllCells()  throws IOException, InvalidFormatException{

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // Now let's iterate over the columns of the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
            }
            System.out.println();
        }
        // Closing the workbook
        this.workbook.close();


    }
}
