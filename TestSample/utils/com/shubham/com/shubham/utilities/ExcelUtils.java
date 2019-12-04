package com.shubham.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;

public class ExcelUtils {
	// Main Directory of the project
	public static final String currentDir = System.getProperty("user.dir");

	public static String excelfileName = "TestData_Automation.xlsx";

	// Location of Test data excel file
	public static String testDataExcelPath = null;

	/**
	 * @author srajput
	 * @description This method is to read excel data based on passed header and row value
	 *              value.
	 * @param RowNum
	 * @return
	 * @throws IOException
	 */
	public static String getTestDataRowColumnData(String sheetName,String rowName, String columnName) throws IOException {
		ArrayList<String> testdata = new ArrayList<String>();
		String cellData ="";
		try {
			if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
				testDataExcelPath = currentDir + "//testdata//" + excelfileName;
			} else if (Platform.getCurrent().toString().contains("WIN")) {
				testDataExcelPath = currentDir + "\\testdata\\" + excelfileName;
			}                                            
			FileInputStream fis = new FileInputStream(testDataExcelPath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			System.out.println("Input sheet: " + sheetName + " is read from location: " + testDataExcelPath);
			XSSFRow row = sheet.getRow(0);
			int col_num = -1;
			int count = 0;

			for (int i = 0; i < row.getLastCellNum(); i++) {
				String val=row.getCell(i).getStringCellValue();
				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(columnName)){
					col_num = i;
					break;
				}                                                           
			}                                            
			XSSFCell cell = row.getCell(0);
			row = sheet.getRow(count);
			cell = row.getCell(0);                                     
			while (!cell.getStringCellValue().equalsIgnoreCase(rowName) || cell.getStringCellValue().equals("")) {
				if(cell.getStringCellValue().equalsIgnoreCase("")){
					System.out.println("Invalid input.");
					break;
				}
				count++;
				row = sheet.getRow(count);
				cell = row.getCell(0);                                                                                                                
			}                                            
			XSSFCell cell1 = row.getCell(col_num);
			row = sheet.getRow(count);
			cell1 = row.getCell(col_num);
			cellData = cell1.getStringCellValue();
			System.out.println("Data value of the Excel Cell is - " + cellData);
			return cellData;
		} catch (IllegalArgumentException e1) {
			System.out.println("Invalid Data has been given");
			throw (e1);
		}
		catch (NullPointerException e) {
			return cellData;
		}
		catch (Exception e) {
			throw (e);
		}
	}




}
