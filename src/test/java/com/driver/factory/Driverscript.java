package com.driver.factory;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.common.function.library.Functionlibrary;
import com.common.function.library.TestListener;
import com.utilities.Excelfileutil;
import com.utilities.Propertiesfileutil;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

@Listeners(TestListener.class)
public class Driverscript {
	public static WebDriver driver;
	public static AndroidDriver<MobileElement> mobiledriver;
	// add extent report
	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports report;
	public static ExtentTest logger;

	public static Excelfileutil excel;
	public static Propertiesfileutil prop;
	public static SoftAssert softAssertion = new SoftAssert();
	public String failMsg;

	public void startTest() throws Throwable {
		// creating object for constructor
		excel = new Excelfileutil();
		// defining modulestatus
		String Modulestatus = "";
		// module sheet
		for (int i = 1; i <= Excelfileutil.rowcount("mastertestcases"); i++) {
			if (excel.getdata("mastertestcases", i, 2).equalsIgnoreCase("y")) {
				String TCmodule = excel.getdata("mastertestcases", i, 1);
				// generate extent report
				htmlreporter = new ExtentHtmlReporter(
						"./Reports/" + TCmodule + Functionlibrary.generateDate() + ".html");
				htmlreporter.config().setReportName("Execution results for test" + TCmodule);
				report = new ExtentReports();
				report.attachReporter(htmlreporter);
				logger = report.createTest(TCmodule);

				int rows = Excelfileutil.rowcount(TCmodule);
				System.out.println(rows);
				for (int j = 1; j <= rows; j++) {
					String DESCRIPTION = excel.getdata(TCmodule, j, 0);
					String KEYWORD = excel.getdata(TCmodule, j, 1);
					String OBJECTNAME = excel.getdata(TCmodule, j, 2);
					String LOCATOR = excel.getdata(TCmodule, j, 3);
					String TEST_DATA = excel.getdata(TCmodule, j, 4);

					ArrayList<String> list = new ArrayList<String>();
					list.add(excel.getdata(TCmodule, j, 0));
					list.add(excel.getdata(TCmodule, j, 1));
					list.add(excel.getdata(TCmodule, j, 2));
					list.add(excel.getdata(TCmodule, j, 3));
					list.add(excel.getdata(TCmodule, j, 4));
					list.add(excel.getdata(TCmodule, j, 5));
					list.add(excel.getdata(TCmodule, j, 6));
					list.add(excel.getdata(TCmodule, j, 7));
					// key.test(rp.getName(), list);

					// int test_data=excel.getdata(TCmodule,j,5);
					try {
						if (KEYWORD.equalsIgnoreCase("startbrowser")) {
							driver = Functionlibrary.startbrowser(driver, Propertiesfileutil.getEnvValues(), list);
							// logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("openapplication")) {
							Functionlibrary.openapplication(driver, Propertiesfileutil.getEnvValues(), list);
							// logger.log(Status.INFO, DESCRIPTION);

							logger.log(Status.INFO, DESCRIPTION, MediaEntityBuilder
									.createScreenCaptureFromBase64String(captureImage(driver)).build());
							captureImage(driver);

							File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
							String destination = ("./Screenshot/" + DESCRIPTION + "_" + Functionlibrary.generateDate()
									+ ".png");
							File dest = new File(destination);
							FileUtils.copyFile(src, dest);

							// add screenshot to extent report
							// logger.log(Status.INFO, "FAQs button clicked",
							// MediaEntityBuilder.createScreenCaptureFromPath("." + screenshot()).build());
							// // String image = logger.addScreenCapture(destination);
							// logger.log(Status.INFO, image);

						}
						if (KEYWORD.equalsIgnoreCase("clickaction")) {
							Functionlibrary.clickaction(driver, Propertiesfileutil.getObjectLocators(), list);
							// logger.log(Status.INFO, DESCRIPTION);

							logger.log(Status.INFO, DESCRIPTION, MediaEntityBuilder
									.createScreenCaptureFromBase64String(captureImage(driver)).build());
							captureImage(driver);
						}
						if (KEYWORD.equalsIgnoreCase("typeaction")) {
							Functionlibrary.typeaction(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);

						}
						if (KEYWORD.equalsIgnoreCase("waiting")) {
							Functionlibrary.waiting(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("closeapplication")) {
							Functionlibrary.closeapplication(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("titlevalidation")) {
							Functionlibrary.titlevalidation(driver, TEST_DATA);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("textvalidation")) {
							Functionlibrary.textvalidation(driver, Propertiesfileutil.getObjectLocators(), TEST_DATA,
									list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("alertPopUp")) {
							Functionlibrary.alertPopUp(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("switchWindow")) {
							Functionlibrary.switchWindow(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("switchFrame")) {
							Functionlibrary.switchFrame(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("mouseHover")) {
							Functionlibrary.mouseHover(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("rightClick")) {
							Functionlibrary.rightClick(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("doubleClick")) {
							Functionlibrary.doubleClick(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("dragDrop")) {
							Functionlibrary.dragDrop(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("highlightElement")) {
							Functionlibrary.highlightElement(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("dehighlightElement")) {
							Functionlibrary.dehighlightElement(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("verifyElementPresent")) {
							Functionlibrary.verifyElementPresent(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("verifyElementEnabled")) {
							Functionlibrary.verifyElementEnabled(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("LinkPresent")) {
							Functionlibrary.LinkPresent(driver, list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("dropdownaction")) {
							Functionlibrary.dropdownaction(driver, LOCATOR, OBJECTNAME, TEST_DATA);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("Scrolldownaction")) {
							Functionlibrary.ScrollDownAction(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}

						Excelfileutil.setData(TCmodule, j, 8, "passed");
						Modulestatus = "true";
						logger.log(Status.INFO, DESCRIPTION);

						report.flush();

					} catch (Exception e) {
						Excelfileutil.setData(TCmodule, j, 8, "failed");
						Modulestatus = "false";
						logger.log(Status.FAIL, DESCRIPTION + " failed");
						// take screenshot
						logger.log(Status.FAIL, DESCRIPTION,
								MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
						captureImage(driver);

						File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						String destination = ("./Screenshot/" + DESCRIPTION + "_" + Functionlibrary.generateDate()
								+ ".png");
						File dest = new File(destination);
						FileUtils.copyFile(src, dest);

						// // add screenshot to extent report
						//// logger.log(Status.INFO, "FAQs button clicked",
						// MediaEntityBuilder.createScreenCaptureFromPath("." + screenshot()).build());
						// String image = logger.addScreenCapture(destination);
						// logger.log(Status.FAIL, image);

						report.flush();
						break;

					}

					catch (AssertionError a) // to handle assert type error
					{
						Excelfileutil.setData(TCmodule, j, 8, "failed");
						Modulestatus = "false";
						failMsg = a.getMessage();
						// take screenshot
						logger.log(Status.FAIL, DESCRIPTION,
								MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
						captureImage(driver);
						File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						String destination = ("./Screenshot/" + DESCRIPTION + "_" + Functionlibrary.generateDate()
								+ ".png");
						File dest = new File(destination);
						FileUtils.copyFile(src, dest);

						// // add screenshot to extent report
						// String image = logger.addScreenCapture(destination);
						// logger.log(Status.FAIL, image);
						report.flush();

						break;

					}
				}
				if (Modulestatus.equalsIgnoreCase("true")) {
					Excelfileutil.setData("mastertestcases", i, 3, "Passed");
				} else {
					Excelfileutil.setData("mastertestcases", i, 3, "Failed");
					Assert.fail(failMsg);
					Functionlibrary.closeapplication(driver);
				}

			} else {
				Excelfileutil.setData("mastertestcases", i, 3, "Not Executed");
			}

		}

		report.flush();
	}

	public void failstatusonexcel(String TCmodule, String Modulestatus, int j) {
		try {
			Excelfileutil.setData(TCmodule, j, 8, "failed");
			Modulestatus = "false";
			report.flush();

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public byte[] captureImageBytes() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

}