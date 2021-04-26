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
import com.common.function.library.APIFunctionLibrary;
import com.common.function.library.MobileFunctionLibrary;
import com.common.function.library.TestListener;
import com.common.function.library.WebFunctionLibrary;
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
						"./Reports/" + TCmodule + WebFunctionLibrary.generateDate() + ".html");
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
					String TEST_DATA_1 = excel.getdata(TCmodule, j, 4);
					String TEST_DATA_2 = excel.getdata(TCmodule, j, 5);
					String API_PAYLOAD = excel.getdata(TCmodule, j, 9);

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
					list.add(excel.getdata(TCmodule, j, 9));

					try {
						if (KEYWORD.equalsIgnoreCase("startbrowser")) {
							driver = WebFunctionLibrary.startbrowser(driver, Propertiesfileutil.getEnvValues(), list);
							// logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("openapplication")) {
							WebFunctionLibrary.openapplication(driver, Propertiesfileutil.getEnvValues(), list);
							// logger.log(Status.INFO, DESCRIPTION);

							logger.log(Status.INFO, DESCRIPTION, MediaEntityBuilder
									.createScreenCaptureFromBase64String(captureImage(driver)).build());
							captureImage(driver);

							File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
							String destination = ("./Screenshot/" + DESCRIPTION + "_"
									+ WebFunctionLibrary.generateDate() + ".png");
							File dest = new File(destination);
							FileUtils.copyFile(src, dest);

						}
						if (KEYWORD.equalsIgnoreCase("clickaction")) {
							WebFunctionLibrary.clickaction(driver, Propertiesfileutil.getObjectLocators(), list);
							// logger.log(Status.INFO, DESCRIPTION);

							logger.log(Status.INFO, DESCRIPTION, MediaEntityBuilder
									.createScreenCaptureFromBase64String(captureImage(driver)).build());
							captureImage(driver);
						}
						if (KEYWORD.equalsIgnoreCase("typeaction")) {
							WebFunctionLibrary.typeaction(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);

						}
						if (KEYWORD.equalsIgnoreCase("waiting")) {
							WebFunctionLibrary.waiting(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("closeapplication")) {
							WebFunctionLibrary.closeapplication(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("titlevalidation")) {
							WebFunctionLibrary.titlevalidation(driver, TEST_DATA_1);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("textvalidation")) {
							WebFunctionLibrary.textvalidation(driver, Propertiesfileutil.getObjectLocators(),
									TEST_DATA_1, list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("alertPopUp")) {
							WebFunctionLibrary.alertPopUp(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("switchWindow")) {
							WebFunctionLibrary.switchWindow(driver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("switchFrame")) {
							WebFunctionLibrary.switchFrame(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("mouseHover")) {
							WebFunctionLibrary.mouseHover(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("rightClick")) {
							WebFunctionLibrary.rightClick(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("doubleClick")) {
							WebFunctionLibrary.doubleClick(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("dragDrop")) {
							WebFunctionLibrary.dragDrop(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("highlightElement")) {
							WebFunctionLibrary.highlightElement(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("dehighlightElement")) {
							WebFunctionLibrary.dehighlightElement(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("verifyElementPresent")) {
							WebFunctionLibrary.verifyElementPresent(driver, Propertiesfileutil.getObjectLocators(),
									list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("verifyElementEnabled")) {
							WebFunctionLibrary.verifyElementEnabled(driver, Propertiesfileutil.getObjectLocators(),
									list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("LinkPresent")) {
							WebFunctionLibrary.LinkPresent(driver, list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("dropdownaction")) {
							WebFunctionLibrary.dropdownaction(driver, LOCATOR, OBJECTNAME, TEST_DATA_1);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("Scrolldownaction")) {
							WebFunctionLibrary.ScrollDownAction(driver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetWebServiceURI")) {
							APIFunctionLibrary.getWebServiceURI();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("AddQueryParameterInURI")) {
							APIFunctionLibrary.addQueryParameterInWebServiceURI(
									Propertiesfileutil.getEnvValue("API_Query_param"));
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("BasicAuthentication")) {
							APIFunctionLibrary.requestWithBasicAuthentication(
									Propertiesfileutil.getEnvValue("API_Username"),
									Propertiesfileutil.getEnvValue("API_Password"));
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("PreemptiveAuthentication")) {
							APIFunctionLibrary.requestWithPreemptiveAuthentication(
									Propertiesfileutil.getEnvValue("API_Username"),
									Propertiesfileutil.getEnvValue("API_Password"));
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("FormAuthentication")) {
							APIFunctionLibrary.formAuthentication(Propertiesfileutil.getEnvValue("API_Username"),
									Propertiesfileutil.getEnvValue("API_Password"));
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("DigestAuthentication")) {
							APIFunctionLibrary.digestAuthentication(Propertiesfileutil.getEnvValue("API_Username"),
									Propertiesfileutil.getEnvValue("API_Password"));
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("BearerTokenAuthentication")) {
							APIFunctionLibrary.bearerTokenAuthentication(Propertiesfileutil.getEnvValue("API_Username"),
									Propertiesfileutil.getEnvValue("API_Password"),
									Propertiesfileutil.getEnvValue("API_Bearer_Token"));
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("OATH2Authentication")) {
							APIFunctionLibrary.OATH2Authentication(Propertiesfileutil.getEnvValue("API_Username"),
									Propertiesfileutil.getEnvValue("API_Password"));
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("PostMethod")) {
							APIFunctionLibrary.apiPost(API_PAYLOAD,
									Propertiesfileutil.getEnvValue("API_Post_BasePath"));
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("PutMethod")) {
							APIFunctionLibrary.apiPut(API_PAYLOAD, Propertiesfileutil.getEnvValue("API_Put_BasePath"));
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("DeleteMethod")) {
							APIFunctionLibrary.apiDelete(Propertiesfileutil.getEnvValue("API_Delete_BasePath"));
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetMethod")) {
							APIFunctionLibrary.apiGet(Propertiesfileutil.getEnvValue("API_Get_BasePath"));
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetStatusCode")) {
							APIFunctionLibrary.getStatusCode();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetResponse")) {
							APIFunctionLibrary.getResponse();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetStatusLine")) {
							APIFunctionLibrary.getStatusLine();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetValueForIntKeyInResponse")) {
							APIFunctionLibrary.getValueForIntKeyInResponse(TEST_DATA_1);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetValueForStringKeyInResponse")) {
							APIFunctionLibrary.getValueForStringKeyInResponse(TEST_DATA_1);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetValueForKeyInResponseArray")) {
							APIFunctionLibrary.getValueForKeyInResponseArray(TEST_DATA_1, TEST_DATA_2);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetErrorCodeInResponse")) {
							APIFunctionLibrary.getErrorCodeInResponse();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("GetErrordescriptionInResponse")) {
							APIFunctionLibrary.getErrordescriptionInResponse();
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("CheckErrorCodeResponse")) {
							APIFunctionLibrary.checkErrorCodeResponse("200");
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("ToBack")) {
							MobileFunctionLibrary.toBack(mobiledriver);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("LongPress")) {
							MobileFunctionLibrary.longPress(mobiledriver, LOCATOR);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("ScrollDown")) {
							MobileFunctionLibrary.scrollDown(mobiledriver);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("Scrollup")) {
							MobileFunctionLibrary.scrollup(mobiledriver);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("IsElementPresent")) {
							MobileFunctionLibrary.isElementPresent(mobiledriver, Propertiesfileutil.getObjectLocators(),
									list);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("HideKeyboard")) {
							MobileFunctionLibrary.hideKeyboard(mobiledriver);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("Type")) {
							MobileFunctionLibrary.type(mobiledriver, Propertiesfileutil.getObjectLocators(), list,
									TEST_DATA_1);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("Enter")) {
							MobileFunctionLibrary.enter(mobiledriver);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("ClearAppData")) {
							MobileFunctionLibrary.clearAppData(mobiledriver);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("SwipeIOS")) {
							MobileFunctionLibrary.swipeIOS(mobiledriver, Propertiesfileutil.getObjectLocators(), list,
									TEST_DATA_1);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("EnterInput")) {
							MobileFunctionLibrary.enterInput(mobiledriver, Propertiesfileutil.getObjectLocators(), list,
									TEST_DATA_1);
							logger.log(Status.INFO, DESCRIPTION);
						}

						if (KEYWORD.equalsIgnoreCase("Tap")) {
							MobileFunctionLibrary.tap(mobiledriver, Propertiesfileutil.getObjectLocators(), list);
							logger.log(Status.INFO, DESCRIPTION);

						}
						if (KEYWORD.equalsIgnoreCase("GetWebElement")) {
							MobileFunctionLibrary.getWebElement(mobiledriver, Propertiesfileutil.getObjectLocators(),
									list);
							logger.log(Status.INFO, DESCRIPTION);
						}
						if (KEYWORD.equalsIgnoreCase("PasteTxt")) {
							MobileFunctionLibrary.pasteTxt(mobiledriver);
							logger.log(Status.INFO, DESCRIPTION);
						}
						Excelfileutil.setData(TCmodule, j, 10, "passed");
						Modulestatus = "true";
						logger.log(Status.INFO, DESCRIPTION);

						report.flush();

					} catch (Exception e) {
						Excelfileutil.setData(TCmodule, j, 10, "failed");
						Modulestatus = "false";
						logger.log(Status.FAIL, DESCRIPTION + " failed");
						// take screenshot
						logger.log(Status.FAIL, DESCRIPTION,
								MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
						captureImage(driver);

						File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						String destination = ("./Screenshot/" + DESCRIPTION + "_" + WebFunctionLibrary.generateDate()
								+ ".png");
						File dest = new File(destination);
						FileUtils.copyFile(src, dest);
						report.flush();
						break;

					}

					catch (AssertionError a) {
						Excelfileutil.setData(TCmodule, j, 10, "failed");
						Modulestatus = "false";
						failMsg.append(a.getMessage() + "in " + TCmodule + "sheet , ");
						// take screenshot
						logger.log(Status.FAIL, DESCRIPTION,
								MediaEntityBuilder.createScreenCaptureFromBase64String(captureImage(driver)).build());
						captureImage(driver);
						File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						String destination = ("./Screenshot/" + DESCRIPTION + "_" + WebFunctionLibrary.generateDate()
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
		if (driver != null) {
			WebFunctionLibrary.closedriver(Driverscript.driver);
			System.out.println("Browsers Closed");
		}
		if (testFail == true) {
			Assert.fail(failMsg.toString());
		}
		report.flush();
	}

	public void failstatusonexcel(String TCmodule, String Modulestatus, int j) {
		try {
			Excelfileutil.setData(TCmodule, j, 10, "failed");
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