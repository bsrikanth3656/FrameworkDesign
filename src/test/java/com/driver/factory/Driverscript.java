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
import com.common.function.library.Web_Functionlibrary;
import com.common.function.library.API_Functionlibrary;
import com.common.function.library.TestListener;
import com.utilities.Excelfileutil;
import com.utilities.Propertiesfileutil;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Attachment;

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
	public static StringBuilder failMsg = new StringBuilder(" ");
	public static boolean testFail = false;
	public static String TCmodule;

	public void startTest() throws Throwable {
		// creating object for constructor
		excel = new Excelfileutil();
		// defining modulestatus
		String Modulestatus = "";
		// module sheet
		for (int i = 1; i <= Excelfileutil.rowcount("mastertestcases"); i++) {
			if (excel.getdata("mastertestcases", i, 2).equalsIgnoreCase("y")) {
				TCmodule = excel.getdata("mastertestcases", i, 1);
				// generate extent report
				htmlreporter = new ExtentHtmlReporter(
						"./Reports/" + TCmodule + Web_Functionlibrary.generateDate() + ".html");
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
					String API_PAYLOAD = excel.getdata(TCmodule, j, 8);

					ArrayList<String> list = new ArrayList<String>();
					list.add(excel.getdata(TCmodule, j, 0));
					list.add(excel.getdata(TCmodule, j, 1));
					list.add(excel.getdata(TCmodule, j, 2));
					list.add(excel.getdata(TCmodule, j, 3));
					list.add(excel.getdata(TCmodule, j, 4));
					list.add(excel.getdata(TCmodule, j, 5));
					list.add(excel.getdata(TCmodule, j, 6));
					list.add(excel.getdata(TCmodule, j, 7));
					list.add(excel.getdata(TCmodule, j, 8));

					try {
						if (KEYWORD.equalsIgnoreCase("startbrowser")) {
							driver = Web_Functionlibrary.startbrowser(driver, Propertiesfileutil.getEnvValues(), list);
							// logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("openapplication")) {
							Web_Functionlibrary.openapplication(driver, Propertiesfileutil.getEnvValues(), list);
							// logger.log(Status.INFO, DESCRIPTION);

							logger.log(Status.INFO, DESCRIPTION, MediaEntityBuilder
									.createScreenCaptureFromBase64String(captureImage(driver)).build());
							captureImage(driver);

							File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
							String destination = ("./Screenshot/" + DESCRIPTION + "_"
									+ Web_Functionlibrary.generateDate() + ".png");
							File dest = new File(destination);
							FileUtils.copyFile(src, dest);

						}
						if (KEYWORD.equalsIgnoreCase("clickaction")) {
							Web_Functionlibrary.clickaction(driver, Propertiesfileutil.getObjectLocators(), list);
							// logger.log(Status.INFO, DESCRIPTION);

							logger.log(Status.INFO, DESCRIPTION, MediaEntityBuilder
									.createScreenCaptureFromBase64String(captureImage(driver)).build());
							captureImage(driver);
						}
						if (KEYWORD.equalsIgnoreCase("typeaction")) {
							Web_Functionlibrary.typeaction(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);

						}
						if (KEYWORD.equalsIgnoreCase("waiting")) {
							Web_Functionlibrary.waiting(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("closeapplication")) {
							Web_Functionlibrary.closeapplication(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("titlevalidation")) {
							Web_Functionlibrary.titlevalidation(driver, TEST_DATA);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("textvalidation")) {
							Web_Functionlibrary.textvalidation(driver, Propertiesfileutil.getObjectLocators(),
									TEST_DATA, list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("alertPopUp")) {
							Web_Functionlibrary.alertPopUp(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("switchWindow")) {
							Web_Functionlibrary.switchWindow(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("switchFrame")) {
							Web_Functionlibrary.switchFrame(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("mouseHover")) {
							Web_Functionlibrary.mouseHover(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("rightClick")) {
							Web_Functionlibrary.rightClick(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("doubleClick")) {
							Web_Functionlibrary.doubleClick(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("dragDrop")) {
							Web_Functionlibrary.dragDrop(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("highlightElement")) {
							Web_Functionlibrary.highlightElement(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("dehighlightElement")) {
							Web_Functionlibrary.dehighlightElement(driver, Propertiesfileutil.getObjectLocators(),
									list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("verifyElementPresent")) {
							Web_Functionlibrary.verifyElementPresent(driver, Propertiesfileutil.getObjectLocators(),
									list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("verifyElementEnabled")) {
							Web_Functionlibrary.verifyElementEnabled(driver, Propertiesfileutil.getObjectLocators(),
									list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("LinkPresent")) {
							Web_Functionlibrary.LinkPresent(driver, list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("dropdownaction")) {
							Web_Functionlibrary.dropdownaction(driver, LOCATOR, OBJECTNAME, TEST_DATA);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("Scrolldownaction")) {
							Web_Functionlibrary.ScrollDownAction(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("PostMethod")) {
							API_Functionlibrary.apiPost(Propertiesfileutil.getEnvValue("API_URI"), API_PAYLOAD,
									Propertiesfileutil.getEnvValue("API_Post_BasePath"));
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("PutMethod")) {
							API_Functionlibrary.apiPut(Propertiesfileutil.getEnvValue("API_URI"), API_PAYLOAD,
									Propertiesfileutil.getEnvValue("API_Put_BasePath"));
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("DeleteMethod")) {
							API_Functionlibrary.apiDelete(Propertiesfileutil.getEnvValue("API_URI"),
									Propertiesfileutil.getEnvValue("API_Delete_BasePath"));
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetMethod")) {
							API_Functionlibrary.apiGet(Propertiesfileutil.getEnvValue("API_URI"),
									Propertiesfileutil.getEnvValue("API_Get_BasePath"));
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetStatusCode")) {
							API_Functionlibrary.getStatusCode();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetResponse")) {
							API_Functionlibrary.getStatusCode();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetStatusLine")) {
							API_Functionlibrary.getStatusLine();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetErrorCodeInResponse")) {
							API_Functionlibrary.getErrorCodeInResponse();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetErrordescriptionInResponse")) {
							API_Functionlibrary.getErrordescriptionInResponse();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("CheckErrorCodeResponse")) {
							API_Functionlibrary.checkErrorCodeResponse("200");
							logger.log(Status.INFO, DESCRIPTION);
						}

						Excelfileutil.setData(TCmodule, j, 9, "passed");
						Modulestatus = "true";
						logger.log(Status.INFO, DESCRIPTION);

						report.flush();

					} catch (Exception e) {
						Excelfileutil.setData(TCmodule, j, 9, "failed");
						Modulestatus = "false";
						logger.log(Status.FAIL, DESCRIPTION + " failed");
						// take screenshot
						logger.log(Status.FAIL, DESCRIPTION,
								MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
						captureImage(driver);

						File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						String destination = ("./Screenshot/" + DESCRIPTION + "_" + Web_Functionlibrary.generateDate()
								+ ".png");
						File dest = new File(destination);
						FileUtils.copyFile(src, dest);
						report.flush();
						break;

					}

					catch (AssertionError a) {
						Excelfileutil.setData(TCmodule, j, 9, "failed");
						Modulestatus = "false";
						failMsg.append(a.getMessage() + "in " + TCmodule + "sheet , ");
						// take screenshot
						logger.log(Status.FAIL, DESCRIPTION,
								MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
						captureImage(driver);
						File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						String destination = ("./Screenshot/" + DESCRIPTION + "_" + Web_Functionlibrary.generateDate()
								+ ".png");
						File dest = new File(destination);
						FileUtils.copyFile(src, dest);
						report.flush();
						break;
					}
				}
				if (Modulestatus.equalsIgnoreCase("true")) {
					Excelfileutil.setData("mastertestcases", i, 3, "Passed");
				} else {
					Excelfileutil.setData("mastertestcases", i, 3, "Failed");
				}

			} else {
				Excelfileutil.setData("mastertestcases", i, 3, "Not Executed");
			}

		}
		Web_Functionlibrary.closedriver(Driverscript.driver);
		System.out.println("Browsers Closed");
		if (testFail == true) {
			Assert.fail(failMsg.toString());
		}
		report.flush();
	}

	public void failstatusonexcel(String TCmodule, String Modulestatus, int j) {
		try {
			Excelfileutil.setData(TCmodule, j, 9, "failed");
			Modulestatus = "false";
			report.flush();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static String captureImage(WebDriver driver) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TakesScreenshot ts = (TakesScreenshot) driver;
		String base64string = ts.getScreenshotAs(OutputType.BASE64);
		return base64string;
	}

	public static byte[] captureImageBytes() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Attachment(value = "Page Screenshot", type = "image/png")
	public static byte[] saveFailureScreenShot() {
		return captureImageBytes();
	}

	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}
}