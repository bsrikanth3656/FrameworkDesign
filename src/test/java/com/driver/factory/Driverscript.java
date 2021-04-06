package com.driver.factory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.common.function.library.Functionlibrary;
import com.utilities.Excelfileutil;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Driverscript {
	public static WebDriver driver;
	public static AndroidDriver<MobileElement> mobiledriver;
//  add extent report
	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports report;
	public static ExtentTest logger;

	public static Excelfileutil excel;

	public void startTest() throws Throwable {
// creating object for constructor
		excel = new Excelfileutil();
		// defining modulestatus
		String Modulestatus = "";
//module sheet
		for (int i = 1; i <= Excelfileutil.rowcount("mastertestcases"); i++) {
			if (excel.getdata("mastertestcases", i, 2).equalsIgnoreCase("y")) {
				String TCmodule = excel.getdata("mastertestcases", i, 1);
				// generate extent report
				htmlreporter = new ExtentHtmlReporter("./Reports/" +TCmodule + Functionlibrary.generateDate() + ".html");
				htmlreporter.config().setReportName("Execution results for test" + TCmodule);
				report = new ExtentReports();
				report.attachReporter(htmlreporter);
				logger = report.createTest(TCmodule);

				int rowcount = Excelfileutil.rowcount(TCmodule);
				int rows = rowcount;
				System.out.println(rows);
				for (int j = 1; j <=rows ; j++) {
					String DESCRIPTION = excel.getdata(TCmodule, j, 0);
					String OBJECT_TYPE = excel.getdata(TCmodule, j, 1);
					String LOCATOR_TYPE = excel.getdata(TCmodule, j, 2);
					String LOCATOR_VALUE = excel.getdata(TCmodule, j, 3);
					String TEST_DATA = excel.getdata(TCmodule, j, 4);
					// int test_data=excel.getdata(TCmodule,j,5);
					try {
						if (OBJECT_TYPE.equalsIgnoreCase("startbrowser")) {
							driver = Functionlibrary.startbrowser(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (OBJECT_TYPE.equalsIgnoreCase("startmobilebrowser")) {
							mobiledriver = Functionlibrary.setUpAppium(mobiledriver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (OBJECT_TYPE.equalsIgnoreCase("deleteapp")) {
							Functionlibrary.deleteApp();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (OBJECT_TYPE.equalsIgnoreCase("openapplication")) {
							Functionlibrary.openapplication(driver);
							logger.log(Status.INFO, DESCRIPTION);
							
							logger.log(Status.INFO,DESCRIPTION ,
	                                MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
						captureImage(driver);
//
//							File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//							String destination = ("./Screenshot/" + DESCRIPTION + "_" + Functionlibrary.generateDate()
//									+ ".png");
//							File dest = new File(destination);
//							FileUtils.copyFile(src, dest);

							// add screenshot to extent report
//			logger.log(Status.INFO, "FAQs button clicked", MediaEntityBuilder.createScreenCaptureFromPath("." + screenshot()).build());
//			//				String image = logger.addScreenCapture(destination);
//							logger.log(Status.INFO, image);

						}
						if (OBJECT_TYPE.equalsIgnoreCase("clickaction")) {
							Functionlibrary.clickaction(driver, LOCATOR_TYPE, LOCATOR_VALUE);
//							logger.log(Status.INFO, DESCRIPTION);
							
							logger.log(Status.INFO,DESCRIPTION ,
	                                MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
						captureImage(driver);
						}
						if (OBJECT_TYPE.equalsIgnoreCase("typeaction")) {
							Functionlibrary.typeaction(driver, LOCATOR_TYPE, LOCATOR_VALUE, TEST_DATA);
							logger.log(Status.INFO, DESCRIPTION);

						}
						if (OBJECT_TYPE.equalsIgnoreCase("waiting")) {
							Functionlibrary.waiting(driver, LOCATOR_TYPE, LOCATOR_VALUE);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (OBJECT_TYPE.equalsIgnoreCase("closeapplication")) {
							Functionlibrary.closeapplication(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (OBJECT_TYPE.equalsIgnoreCase("titlevalidation")) {
							Functionlibrary.titlevalidation(driver, TEST_DATA);
							logger.log(Status.INFO, DESCRIPTION);

						}
						if (OBJECT_TYPE.equalsIgnoreCase("dropdownaction")) {
							Functionlibrary.dropdownaction(driver, LOCATOR_TYPE, LOCATOR_VALUE, TEST_DATA);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (OBJECT_TYPE.equalsIgnoreCase("Scrolldownaction")) {
							Functionlibrary.Scrolldownaction(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
					
						Excelfileutil.setData(TCmodule, j, 5, "passed");
						Modulestatus = "true";
						logger.log(Status.INFO, DESCRIPTION);
						
						report.flush();
						
					} 
					catch (Exception e) {
						Excelfileutil.setData(TCmodule, j, 5, "failed");
						Modulestatus = "false";
						logger.log(Status.FAIL, DESCRIPTION + " failed");
						// take screenshot
						 logger.log(Status.FAIL,DESCRIPTION ,
	                                MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
						captureImage(driver);
						
						File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						String destination = ("./Screenshot/" + DESCRIPTION + "_" + Functionlibrary.generateDate()
								+ ".png");
						File dest = new File(destination);
						FileUtils.copyFile(src, dest);

//						// add screenshot to extent report
////			logger.log(Status.INFO, "FAQs button clicked", MediaEntityBuilder.createScreenCaptureFromPath("." + screenshot()).build());
//						String image = logger.addScreenCapture(destination);
//						logger.log(Status.FAIL, image);

						report.flush();
						
						break;
					}

					catch (AssertionError a) // to handle assert type error
					{
						Excelfileutil.setData(TCmodule, j, 5, "failed");
						Modulestatus = "false";

						// take screenshot
						logger.log(Status.FAIL,DESCRIPTION ,
                                MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
					captureImage(driver);
						File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						String destination = ("./Screenshot/" + DESCRIPTION + "_" + Functionlibrary.generateDate()
								+ ".png");
						File dest = new File(destination);
						FileUtils.copyFile(src, dest);

//						// add screenshot to extent report
//						String image = logger.addScreenCapture(destination);
//						logger.log(Status.FAIL, image);
						report.flush();
						
						break;
					} 
					
					
			
			}
				
				if (Modulestatus.equalsIgnoreCase("true")) {
					Excelfileutil.setData("mastertestcases", i, 3, "Passed");
				} else {
					Excelfileutil.setData("mastertestcases", i, 3, "Failed");
				}
				
				} 
			else {
				Excelfileutil.setData("mastertestcases", i, 3, "Not Executed");
			}

		}
		
		report.flush();
	}
	public String captureImage(WebDriver driver) {
        try {
           Thread.sleep(5000);
       } catch (InterruptedException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
      
       TakesScreenshot ts = (TakesScreenshot) driver;
      
       String base64string = ts.getScreenshotAs(OutputType.BASE64);
               return base64string;
   }
}
