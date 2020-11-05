package com.amazon.util;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Microsoft XL file reader, fetches the data based on the data passed
 * data: file, sheet, method(test method) in the sheet
 *
 */
public class XlDataReader {
    private Workbook workbook;
    private Sheet sheet;
    private Map<String, Integer> testMethods = new HashMap<>();

    public XlDataReader(String fileName) throws IOException {
        workbook = WorkbookFactory.create(new File(fileName));
    }

    /**
     * When sheet is selected, it fetches all the tests (first column in the sheet) with it's row number
     * later it's used in the fetching test data for a given method
     *
     */
    public void setSheet(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        getAllTests();
    }

    /**
     * fetch all the non empty first columns and store in testMethods object of Map class
     */
    private void getAllTests() {
        try {
            for(int i = 0; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Cell testCell = null;
                try {
                    testCell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                } catch (NullPointerException npe){}
                if (!isCellEmpty(testCell)) {
                    System.out.println(testCell.getStringCellValue());
                    testMethods.put(testCell.getStringCellValue(), i);
                }
            }
        } catch (NullPointerException npe){}
    }

    /**
     * Get all test method data rows for a given test name
     *
     * @return List map object
     */
    public List<Map<String, Object>> getTestData(String testMethodName) {
        if (!testMethods.containsKey(testMethodName)) {
            return new ArrayList<>();
        }
        int startIndex = testMethods.get(testMethodName);
        Row headerRow = sheet.getRow(startIndex);
        Map<String, Integer> headers = getHeaders(headerRow);

        List<Map<String, Object>> dataList = new ArrayList<>();
        while(true) {
            Row row = sheet.getRow(++startIndex);
            if (isRowEmpty(row)) {
                break;
            }
            Map<String, Object> data = new HashMap<>();
            for(String key : headers.keySet()) {
                Cell cell = row.getCell(headers.get(key));
                if (cell!=null) {
                    switch (cell.getCellType()) {
                        case BOOLEAN:
                            data.put(key, cell.getBooleanCellValue());
                            break;
                        case NUMERIC:
                            data.put(key, cell.getNumericCellValue());
                            break;
                        case STRING:
                            data.put(key, cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
            }
            if (!data.isEmpty()) {
                dataList.add(data);
            }
        }
        return dataList;
    }

    /**
     * Check if Cell/Column is empty
     *
     * @return boolean
     */
    private boolean isCellEmpty(Cell c) {
        return c == null || c.getCellType() == CellType.BLANK;
    }


    /**
     * Check if row is empty
     *
     * @return boolean
     */
    private boolean isRowEmpty(Row row) {
        boolean isEmpty = true;
        DataFormatter dataFormatter = new DataFormatter();
        if(row != null) {
            for(Cell cell: row) {
                if(dataFormatter.formatCellValue(cell).trim().length() > 0) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }

    /**
     * Given row fields will be read a returns as a map.
     * key: filed/cell value
     * value: index of the cell
     */
    private Map<String, Integer> getHeaders(Row headerRow) {
        Map<String, Integer> headers = new HashMap<>();
        if (headerRow.getLastCellNum() > 1) {
            for (int i = 1; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (!isCellEmpty(cell)) {
                    headers.put(cell.getStringCellValue(), i);
                }
            }
        }
        return headers;
    }
}
