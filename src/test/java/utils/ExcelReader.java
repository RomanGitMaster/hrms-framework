package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ExcelReader {
    public static DataFormatter formatter = new DataFormatter();


    public static List<Map<String, String>> readExcelAsListOfMaps(String path, String sheetName)  {
        List<Map<String, String>> excelData = new ArrayList<>();

        try (InputStream is = new FileInputStream(path);
             Workbook book = new XSSFWorkbook(is)) {

            Sheet sheet = book.getSheet(sheetName);
            if (sheet == null) throw new IllegalArgumentException("No sheet: " + sheetName);

            Row headerRow = sheet.getRow(0);
            int numOfRows = sheet.getLastRowNum();

            for (int i = 1; i <= numOfRows; i++) {
                Row row = sheet.getRow(i);
                if (isRowEmpty(row)) continue;
                int numOfCells = headerRow.getLastCellNum();
                Map<String, String> rowMap = new LinkedHashMap<>();
                for (int y = 0; y < numOfCells; y++) {
                    String key = getCellAsString(headerRow.getCell(y, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
                    String value = getCellAsString(row.getCell(y, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL));
                    rowMap.put(key.trim(), value.trim());
                }
                excelData.add(rowMap);
            }

        } catch (FileNotFoundException e) {
            System.out.println(" File not found: " + path);
        } catch (IOException e) {
            System.out.println("Error in Excel file reading: " + e);
        }
        return excelData;
    }

    private static String getCellAsString(Cell cell) {//helps format data the way they are in Excel file
        if (cell == null) return "";
        return formatter.formatCellValue(cell);
    }

    private static boolean isRowEmpty(Row row) {//checks for empty rows, verifies each cell for being blank, null or empty
        if (row == null) return true;

        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {

            Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null && !formatter.formatCellValue(cell).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }


}
