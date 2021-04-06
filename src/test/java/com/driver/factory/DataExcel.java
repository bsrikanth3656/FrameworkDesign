package com.driver.factory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class DataExcel {
	static WebDriver driver;
	public static void main(String[] args) throws Exception {

//		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://www.nyse.com/ipo-center/filings");
		
		Thread.sleep(3000);
		
		List<WebElement> rows = driver.findElements(By.tagName("tr"));
        row: for (WebElement eachRow : rows) {

            List<WebElement> allCells = eachRow.findElements(By.tagName("td"));

            for (WebElement ecahCell : allCells) {
//            	System.out.println(eachRow.getText());
                if (ecahCell.getText().equalsIgnoreCase("comp")) {

                    System.out.println(eachRow.getText());
//                    break row;

                }

            }            }
		
		
//		WebElement upc = driver.findElement(By.xpath("//*[contains(text(),'UPC')]"));
//		JavascriptExecutor jse = (JavascriptExecutor)driver;
//		jse.executeScript("arguments[0].scrollIntoView();", upc);
//		
//		Thread.sleep(3000);
//		List<WebElement> rows = driver.findElements(By.tagName("//table//td"));
//		System.out.println("rows : "+rows.size());
//		
//		List<WebElement> cols = driver.findElements(By.xpath("//table[1]//thead[1]//th"));
//		System.out.println("columns : "+cols.size());
//		
//		for (WebElement cell : rows) { 
//			        System.out.println(cell.getText());
//			        			        
//			    }
		driver.close();

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		File folder = new File("C:\\Users\\srinivasj\\Downloads");
//		File[] listOfFiles = folder.listFiles();
//		Arrays.sort(listOfFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
//
//		for (int i = 0; i < 1; i++) {
//		  if (listOfFiles[i].isFile()) {
//			  String fileName = listOfFiles[i].getName();
//		    System.out.println("File " + fileName);
//		  } else if (listOfFiles[i].isDirectory()) {
//		    System.out.println("Directory " + listOfFiles[i].getName());
//		  }
//		  FileInputStream fis = new FileInputStream(folder+"\\"+listOfFiles[i].getName());
//		  System.out.println(fis);
//		  XSSFWorkbook wb = new XSSFWorkbook(fis);
//			XSSFSheet sheet = wb.getSheet("data");
//			int rows = sheet.getLastRowNum();
//			System.out.println(rows);
//		  
//		}
//		
//		
//		
//		
//		
////		int n, a = 0, b = 0, c = 1;
////        Scanner s = new Scanner(System.in);
////        System.out.print("Enter value of n:");
////        n = s.nextInt();
////        System.out.print("Fibonacci Series:");
////        for(int i = 1; i <= n; i++)
////        {
////            a = b;
////            b = c;
////            c = a + b;
////            System.out.print(a+" ");
////        }
//		

	
	
	}


	}
