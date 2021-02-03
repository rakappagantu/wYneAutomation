package com.wYne.automation.general;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ExcelReader {
	private static final Logger LOGGER = Logger.getLogger(ExcelReader.class);
	public static Object[][] readExcelData(String excelFileName,
			String wSheetName) {
		try {
			
			
			// get input Stream
//			InputStream is = ClassLoader.getSystemClassLoader()
//					.getSystemResourceAsStream("resourses/" + excelFileName);
			
			File initialFile = new File("src/test/resources/" + excelFileName);
		    InputStream is = new FileInputStream(initialFile);
		    
		    
			// Create the object of workbook
			XSSFWorkbook excelFile = null;
			if (is != null) {
				excelFile = new XSSFWorkbook(is);
			}
			// get The worksheet
			XSSFSheet workSheet = excelFile.getSheet(wSheetName);
			

			System.out.println(workSheet.getPhysicalNumberOfRows());
			int noOfRows = workSheet.getLastRowNum();
			int noOfCol = workSheet.getRow(0).getLastCellNum();

			Object[][] data = new Object[noOfRows - 1][noOfCol];

			for (int i = 0; i < noOfRows - 1; i++) {

				XSSFRow row = workSheet.getRow(i + 1);
				if (row ==null){
					continue;
				}
				Object[] tempData = new Object[noOfCol];
				boolean isRowContainValue = false;
				for (int j = 0; j < noOfCol; j++) {
					XSSFCell cell = row.getCell(j);
					Object value = cellToObject(cell);
					tempData[j] = value;

					if (cell == null) {
						continue;
					}
					isRowContainValue = true;
				}

				if (isRowContainValue) {
					data[i] = tempData;
				}
			}
			return data;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	private static Object cellToObject(XSSFCell cell) {
		int type;
		Object result;
		if (cell == null) {
			return null;
		}
		type = cell.getCellType();

		switch (type) {
		case XSSFCell.CELL_TYPE_NUMERIC: // 0
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			// result = cell.getNumericCellValue();
			result = cell.getStringCellValue();

			if ("-".equals(result)) {
				result = null;
			}
			break;
		case XSSFCell.CELL_TYPE_STRING: // 1
			result = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_FORMULA: // 2
			throw new RuntimeException("We can't evaluate formulas in Java");
		case XSSFCell.CELL_TYPE_BLANK: // 3
			result = "-";
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN: // 4
			result = cell.getBooleanCellValue();
			break;
		case XSSFCell.CELL_TYPE_ERROR: // 5
			throw new RuntimeException("This cell has an error");
		default:
			throw new RuntimeException("We don't support this cell type: "
					+ type);
		}
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Object[][] dataObj = readExcelData("calcdata.xlsx", "Sheet1");
		int noRows = dataObj.length;
		int noOfCols = dataObj[0].length;
		for (int i = 0; i < noRows; i++) {
			for (int j = 0; j < noOfCols; j++) {
				System.out.print(dataObj[i][j] + "  ");
			}
			System.out.println();
		}
	}

	/**
	 * Method to get two dimensional Object array from Excel Sheet data.
	 * If input has more than 5 columns, you can use this dataMap.
	 *  
	 * @param fileName -  File Name or Complete File Path if External Source 
	 * @param sheetName - Name of the Excel Sheet to fetch records from.
	 * 
	 * @return Object[][] - Contains maps.  
	 * @throws FileNotFoundException 
	 */
	public static List<Map<String, String>> createDataMap(String fileName , String sheetName) throws FileNotFoundException {
		LOGGER.info("==>createDataMap()");
		
		final String NULLROW = "NULLROW";
		List<Map<String, String>> excelDataList = null;
		List<String> keyList = null;
		Map<String, String> dataMap = null;

		InputStream xlFile = getExcelFile(fileName);

		try 
		{
			keyList = new ArrayList<String>();
			OPCPackage opc = OPCPackage.open(xlFile);

			Workbook wb1 = WorkbookFactory.create(opc);
			//HSSFWorkbook wb1 = new HSSFWorkbook(xlFile);
			Sheet sheet = wb1.getSheet(sheetName);
			excelDataList = new ArrayList<Map<String, String>>();

			for (Row row : sheet) {

				if(row.getRowNum() == sheet.getFirstRowNum()) {
					for (int fr = 0; fr < row.getLastCellNum(); fr++) {
						if(null != row.getCell(fr)){
							keyList.add(row.getCell(fr).toString());
						} else {
							keyList.add(NULLROW);
						}          				
					}

				} else if (row.getRowNum() > sheet.getFirstRowNum() ){
					dataMap = new LinkedHashMap<String, String>();
					for (int cn = 0 ; cn < keyList.size();cn++ ) {

						if(!keyList.get(cn).equals(NULLROW)) {

							if(null == row.getCell(cn)){

								dataMap.put(keyList.get(cn).toString(), "");        

							} else {

								Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);

								if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
									dataMap.put(keyList.get(cn).toString(),"");
								} 
								else if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
									dataMap.put(keyList.get(cn).toString(),(row.getCell(cn).toString()));
								} 
								else {
									dataMap.put(keyList.get(cn).toString(),getValueAsString(row.getCell(cn).toString()));
								}
							}
						}
					}
					//datProviderData [row.getRowNum() - (sheet.getFirstRowNum()) - 1][0] = dataMap;
					excelDataList.add(dataMap);
				}
			}
			opc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOGGER.info("<==createDataMap()");
		return excelDataList;
	}

	/**
	 * This method is used to convert all the values to strings
	 * 
	 * @param value
	 * @return
	 */
	private static String getValueAsString(String value){
		LOGGER.debug("==>getValueAsString()");
		BigDecimal decimal = null;
		Double checkForDbl = Double.valueOf(value);

		if( (checkForDbl - (checkForDbl.intValue())) > 0 ) {
			decimal = new BigDecimal(value);
		} else {
			decimal = new BigDecimal(Double.valueOf(value));
		}
		LOGGER.debug("<==getValueAsString()");
		return decimal.toPlainString();
	}

	/**
	 * This method is used to read the excel file
	 * 
	 * @param xlFileName
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static InputStream getExcelFile(String xlFileName) throws FileNotFoundException{
		LOGGER.debug("==>getExcelFile()");
		InputStream xlFile = ClassLoader.getSystemResourceAsStream(CommonConstants.TEST_DATA_LOCATION+xlFileName);

		// If not found, try again without base path
		if (null==xlFile){
			xlFile = ClassLoader.getSystemResourceAsStream(xlFileName);
		}
		
		if(null == xlFile) {
			File initialFile = new File(CommonConstants.TEST_DATA_LOCATION + xlFileName);
		    xlFile = new FileInputStream(initialFile);
		}
		LOGGER.debug("<==getExcelFile()");
		return xlFile;
	}

	public static void storeDataIntoExcelFile(List<Map<String, String>> data, String outputFilePath, String sheetName) throws Exception {
		for (Map<String, String> listData : data) {
			System.out.println(listData);
		}

		if (data.size() < 1){
			return;
		}

		File file = new File(outputFilePath);
		//    if(file.exists()) file.delete();
		XSSFWorkbook workbook = null;
		if (file.exists()) {
			FileInputStream fileInputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(fileInputStream);
			int i = workbook.getSheetIndex(sheetName);
			if (i >= 0) {
				workbook.removeSheetAt(i);
				FileOutputStream output = new FileOutputStream(file);
				workbook.write(output);
				output.close();
			}
		} else {
			workbook = new XSSFWorkbook();
		}
		XSSFSheet sheet;
		sheet = workbook.createSheet(sheetName);
		Set<String> headers = data.get(0).keySet();

		XSSFFont font = workbook.createFont();
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);

		XSSFRow rowZero = sheet.createRow(0);
		int count =0;
		for (String s:headers) {
			XSSFCell cell = rowZero.createCell(count);
			cell.setCellValue(s);
			cell.setCellStyle(style);
			count++;
		}

		for (int i = 0; i < data.size(); i++) {
			XSSFRow row = sheet.createRow(i + 1);
			int count1 =0;
			for (String s:headers) {
				XSSFCell cell = row.createCell(count1);
				cell.setCellValue(data.get(i).get(s));
				count1++;
			}
		}

		try {
			//Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(outputFilePath));
			workbook.write(out);
			out.close();
			//System.out.println("Accounts verification test completed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
