package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excelfileutil {

	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	
	public Excelfileutil() throws Throwable //creating of constructor
	{
		File file = new File("./Testinput/Inputsheet.xlsx");
	FileInputStream  fis = new FileInputStream(file);	
	workbook = new XSSFWorkbook(fis);
	}
	public static  int rowcount(String sheetname)
	{
		return workbook.getSheet(sheetname).getLastRowNum();
	}
	public  int colcount(String sheetname ,int row)
	{
		return workbook.getSheet(sheetname).getRow(row).getLastCellNum();
	}
	public String getdata(String sheetname,int row ,int col)
	{
		//creating a space for status writing bcz at first it was vacant.
		String data ="";
		
		/*if(cell != null){
		if(cell.getCellType().name().equals("NUMERIC")){
		return NumberToTextConverter.toText(cell.getNumericCellValue());
		}
		else{
		data=cell.toString();
		return data;
		}*/
	
		/*
		 * if(workbook.getSheet(sheetname).getRow(row).getCell(col).getCellType()==CellType.
		 * NUMERIC) // .getCellType()==Cell.CELL_TYPE_NUMERIC) { return
		 * NumberToTextConverter.toText(workbook.getSheet(sheetname).getRow(row).getCell(col).
		 * getNumericCellValue()); }
		 */
	    
	    if(workbook.getSheet(sheetname).getRow(row).getCell(col).getCellType()==CellType.NUMERIC)
		{
	    	return NumberToTextConverter.toText(workbook.getSheet(sheetname).getRow(row).getCell(col).getNumericCellValue());
//		int celldata =(int) workbook.getSheet(sheetname).getRow(row).getCell(col).getCellType();
//		data = String.valueOf(celldata);
		}
		else 
		{
			data= workbook.getSheet(sheetname).getRow(row).getCell(col).getStringCellValue();
		}
		return data;
		
	}
	
	public static void setData(String sheetname, int row,int col,String status) throws Throwable
	{
		Sheet sh = workbook.getSheet(sheetname);
		Row rownum = sh.getRow(row);
		Cell cell = rownum.createCell(col);// creating one row for result writing
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("Passed"))
		{
		//create cell style
			
		CellStyle style = workbook.createCellStyle();
		
		// create font
		Font font = workbook.createFont();
		
		//apply colors to text
		font.setColor(IndexedColors.GREEN.getIndex());
		
		//apply bold to text
		font.setItalic(true);
		
		//set font
		style.setFont(font);
		
		rownum.getCell(col).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Failed"))
		{
			//create cell style
			
			CellStyle style = workbook.createCellStyle();
			
			// create font
			Font font = workbook.createFont();
			
			//apply colors to text
			font.setColor(IndexedColors.RED.getIndex());
			
			//apply bold to text
			font.setItalic(true);
			
			//set font
			style.setFont(font);
			
			rownum.getCell(col).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Not Executed"))
		{
			//create cell style
			
			CellStyle style = workbook.createCellStyle();
			
			// create font
			Font font = workbook.createFont();
			
			//apply colors to text
			font.setColor(IndexedColors.BROWN.getIndex());
			
			//apply bold to text
			font.setItalic(true);
			
			//set font
			style.setFont(font);
			
			rownum.getCell(col).setCellStyle(style);
			
		}
		FileOutputStream fos = new FileOutputStream("./TestOutput/Outputsheet.xlsx");
		workbook.write(fos);
		fos.close();
		
	}
	
}
