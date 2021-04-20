package com.common.function.library;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.driver.factory.Driverscript;
import com.jcraft.jsch.Logger;
import com.utilities.Excelfileutil;
import com.utilities.Propertiesfileutil;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Listeners(TestListener.class)
public class Web_Functionlibrary {

	public static JavascriptExecutor js;
	public static Excelfileutil excel;
	static DesiredCapabilities capa = new DesiredCapabilities();
	public static String authToken = null;
	static DesiredCapabilities caps = new DesiredCapabilities();
	public static RequestSpecification httpRequest;
	public static String responsebody;
	public static Response response;

	public static By getLocator(Properties p, String objectName, String locator) {
		if (locator.equalsIgnoreCase("id")) {
			return By.id(p.getProperty(objectName));
		} else if (locator.equalsIgnoreCase("xpath")) {
			return By.xpath(p.getProperty(objectName));
		} else if (locator.equalsIgnoreCase("css")) {
			return By.cssSelector(p.getProperty(objectName));
		} else if (locator.equalsIgnoreCase("name")) {
			return By.name(p.getProperty(objectName));
		} else if (locator.equalsIgnoreCase("class")) {
			return By.className(p.getProperty(objectName));
		} else if (locator.equalsIgnoreCase("link")) {
			return By.linkText(p.getProperty(objectName));
		} else {
			return null;
		}
	}

	// method for kickstart of browser // returntype is WebDriver
	public static WebDriver startbrowser(WebDriver driver, Properties prop, List<String> columns) throws Throwable {
		if (prop.getProperty(columns.get(5)).contains("firefox"))

		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (prop.getProperty(columns.get(5)).equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			// System.setProperty("webdriver.chrome.driver","D:\\Automation\\chromedriver_win32");
			driver = new ChromeDriver();
		} else if (prop.getProperty(columns.get(5)).equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			// System.setProperty("webdriver.ie.driver","commonjarfiles/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if (prop.getProperty(columns.get(6)).equalsIgnoreCase("mobile")) {

			{
				if (prop.getProperty(columns.get(7)).equalsIgnoreCase("ios"))

				{
					caps.setCapability(MobileCapabilityType.DEVICE_NAME, Propertiesfileutil.getEnvValue("deviceName"));
					caps.setCapability(CapabilityType.PLATFORM_NAME, Propertiesfileutil.getEnvValue("platformName"));
					caps.setCapability(MobileCapabilityType.UDID, Propertiesfileutil.getEnvValue("deviceUDID"));
					caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,
							Propertiesfileutil.getEnvValue("automationName"));
					// caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,
					// AutomationName.IOS_XCUI_TEST);
					caps.setCapability(MobileCapabilityType.PLATFORM_VERSION,
							Propertiesfileutil.getEnvValue("platformVersion"));
					caps.setCapability(MobileCapabilityType.NO_RESET, "true");
					caps.setCapability(MobileCapabilityType.APP, Propertiesfileutil.getEnvValue("ipaFilePath"));
					try {
						driver = new IOSDriver<IOSElement>(new URL("http://localhost:4723/wd/hub"), caps);
						Thread.sleep(10000);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (prop.getProperty(columns.get(7)).equalsIgnoreCase("android"))

				{
					caps.setCapability(MobileCapabilityType.DEVICE_NAME,
							Propertiesfileutil.getEnvValue("androidDeviceName"));
					caps.setCapability(CapabilityType.PLATFORM_NAME,
							Propertiesfileutil.getEnvValue("androidPlatformName"));
					caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,
							Propertiesfileutil.getEnvValue("androidAutomationName"));
					caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
							Propertiesfileutil.getEnvValue("appPackage"));
					caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
							Propertiesfileutil.getEnvValue("appActivity"));
					caps.setCapability(MobileCapabilityType.PLATFORM_VERSION,
							Propertiesfileutil.getEnvValue("androidPlatformVersion"));
					caps.setCapability(MobileCapabilityType.APP, Propertiesfileutil.getEnvValue("androidFilePath"));
					caps.setCapability(MobileCapabilityType.NO_RESET, "true");
					try {
						driver = new AndroidDriver<AndroidElement>(new URL("http://localhost:4723/wd/hub"), caps);
						Thread.sleep(10000);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (prop.getProperty(columns.get(6)).equalsIgnoreCase("browserstack")) {
					String USERNAME = Propertiesfileutil.getEnvValue("BROWSERSTACK_USERNAME");
					String AUTOMATE_KEY = Propertiesfileutil.getEnvValue("BROWSERSTACK_AUTOMATEKEY");
					String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
					Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
					if (prop.getProperty(columns.get(5)).equalsIgnoreCase("browserstackchrome")) {
						capsHashtable.put("browser", "chrome");
						capsHashtable.put("browser_version", "89.0");
						capsHashtable.put("os", "Windows");
						capsHashtable.put("os_version", "10");
						capsHashtable.put("build", "browserstack-build-1");
						capsHashtable.put("name", "BrowserStack-Chrome-89");
					}
					if (prop.getProperty(columns.get(5)).equalsIgnoreCase("browserstackfirefox")) {
						capsHashtable.put("browser", "firefox");
						capsHashtable.put("browser_version", "87.0");
						capsHashtable.put("os", "OS X");
						capsHashtable.put("os_version", "Big Sur");
						capsHashtable.put("build", "browserstack-build-1");
						capsHashtable.put("name", "BrowserStack-Firefox-87");
					}
					if (prop.getProperty(columns.get(5)).equalsIgnoreCase("browserstackie")) {
						capsHashtable.put("browser", "ie");
						capsHashtable.put("browser_version", "11.0");
						capsHashtable.put("os", "Windows");
						capsHashtable.put("os_version", "10");
						capsHashtable.put("build", "browserstack-build-1");
						capsHashtable.put("name", "BrowserStack-IE-11");
					}

					String key;
					DesiredCapabilities caps = new DesiredCapabilities();
					// Iterate over the hashtable and set the capabilities
					Set<String> keys = capsHashtable.keySet();
					Iterator<String> itr = keys.iterator();
					while (itr.hasNext()) {
						key = itr.next();
						caps.setCapability(key, capsHashtable.get(key));
					}

					try {
						driver = new RemoteWebDriver(new URL(URL), caps);

					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}

				else if (prop.getProperty(columns.get(6)).equalsIgnoreCase("saucelabs")) {
					String USERNAME = Propertiesfileutil.getEnvValue("SAUCELABS_USERNAME");
					String ACCESS_KEY = Propertiesfileutil.getEnvValue("SAUCELAB_SACCESSKEY");
					// String sauceUserName = System.getenv("SAUCE_USERNAME");
					// String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
					String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
					Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
					if (prop.getProperty(columns.get(5)).equalsIgnoreCase("saucelabschrome")) {

						capsHashtable.put("platform", "Windows 10");
						capsHashtable.put("version", "89.0");
					}

					String key;
					DesiredCapabilities caps = DesiredCapabilities.chrome();
					// Iterate over the hashtable and set the capabilities
					Set<String> keys = capsHashtable.keySet();
					Iterator<String> itr = keys.iterator();
					while (itr.hasNext()) {
						key = itr.next();
						caps.setCapability(key, capsHashtable.get(key));
					}

					try {
						driver = new RemoteWebDriver(new URL(URL), caps);

					} catch (Exception e) {
						e.printStackTrace();

					}

				}

			}
		}
		return driver;

	}

	// method for open application
	@Step("Open Application")
	public static void openapplication(WebDriver driver, Properties prop, List<String> columns) throws Throwable {
		try {
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.get(prop.getProperty(columns.get(4)));
			// driver.get(Propertiesfileutil.getEnvValue("URL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// method for close application
	@Step("Close Application")
	public static void closeapplication(WebDriver driver) {
		try {
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("Close Driver")
	public static void closedriver(WebDriver driver) {
		try {
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Refresh the page
	@Step("Refresh Page")
	public static void refreshPage(WebDriver driver) {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// clickAction
	@Step("Click Action")
	public static void clickaction(WebDriver driver, Properties prop, List<String> columns) {
		try {
			System.out.println(Logger.INFO);
			driver.findElement(getLocator(prop, columns.get(2), columns.get(3))).click();
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}

	}

	// typeAction or to enter the text in textbox
	@Step("Type Action")
	public static void typeaction(WebDriver driver, Properties prop, List<String> columns) {
		try {
			System.out.println(Logger.INFO);
			driver.findElement(getLocator(prop, columns.get(2), columns.get(3))).sendKeys(columns.get(4));

		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}

	}

	// method for wait for element to display statement
	@Step("Waiting")
	public static void waiting(WebDriver driver, Properties prop, List<String> columns) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(getLocator(prop, columns.get(2), columns.get(3)))));
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// method for title validation
	@Step("Title Validation")
	public static void titlevalidation(WebDriver driver, String exp_title) throws Exception {

		String act_title = driver.getTitle();
		try {
			Assert.assertEquals(act_title, exp_title);
		} catch (AssertionError e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;

		}
	}

	// element Text validation
	@Step("Text Validation")
	public static void textvalidation(WebDriver driver, Properties prop, String act_text, List<String> columns) {
		try {
			String exp_text = driver.findElement(getLocator(prop, columns.get(2), columns.get(3))).getText();
			System.out.println(exp_text);
			Assert.assertEquals(act_text, exp_text);
		} catch (AssertionError e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// Alerts
	@Step("Alert Popup")
	public static void alertPopUp(WebDriver driver) {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// switching the window
	@Step("Switch Window")
	public static void switchWindow(WebDriver driver) {
		try {
			String mainWindow = driver.getWindowHandle();
			Set<String> otherWindow = driver.getWindowHandles();
			java.util.Iterator<String> it = otherWindow.iterator();
			while (it.hasNext()) {
				String childWindow = it.next();
				if (!mainWindow.equalsIgnoreCase(childWindow)) {
					driver.switchTo().window(childWindow);
				}
			}
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// switch to new frame
	@Step("Switch Frame")
	public static void switchFrame(WebDriver driver, Properties prop, List<String> columns) {
		try {
			WebElement ele = driver.findElement(getLocator(prop, columns.get(2), columns.get(3)));
			driver.switchTo().frame(ele);
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// doubleClick on an element
	@Step("Double Click on Element")
	public static void doubleClick(WebDriver driver, Properties prop, List<String> columns) {
		try {
			Actions action = new Actions(driver);
			action.doubleClick(driver.findElement(getLocator(prop, columns.get(2), columns.get(3)))).build().perform();
			;
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// drag and drop the element
	@Step("Drag and Drop")
	public static void dragDrop(WebDriver driver, Properties prop, List<String> columns) {
		try {
			Actions action = new Actions(driver);
			action.dragAndDrop(driver.findElement(getLocator(prop, columns.get(2), columns.get(3))),
					driver.findElement(getLocator(prop, columns.get(2), columns.get(3)))).build().perform();
			;
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// Mousehovering on the element
	@Step("Mouse Hover")
	public static void mouseHover(WebDriver driver, Properties prop, List<String> columns) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(getLocator(prop, columns.get(2), columns.get(3)))).build()
					.perform();
			;
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// right click on the element
	@Step("Right Click")
	public static void rightClick(WebDriver driver, Properties prop, List<String> columns) {
		try {
			Actions action = new Actions(driver);
			action.contextClick(driver.findElement(getLocator(prop, columns.get(2), columns.get(3)))).build().perform();
			;
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// highlight the element
	@Step("Highlight Element")
	public static void highlightElement(WebDriver driver, Properties prop, List<String> columns)
			throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
					driver.findElement(getLocator(prop, columns.get(2), columns.get(3))),
					"color: yellow; border: 3px solid yellow;");
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
					driver.findElement(getLocator(prop, columns.get(2), columns.get(3))), "");
			js.executeScript("arguments[0].setAttribute('style', 'background: aqua; border: 2px solid red;');",
					driver.findElement(getLocator(prop, columns.get(2), columns.get(3))));
		}
	}

	// dehighlight the highlighted element
	@Step("Dehighlight Element")
	public static void dehighlightElement(WebDriver driver, Properties prop, List<String> columns)
			throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			// dehighlight
			js.executeScript("arguments[0].removeAttribute('style','')",
					driver.findElement(getLocator(prop, columns.get(2), columns.get(3))));
		}
	}

	// Verify that the element is present on the page or not
	@Step("Verify Element Present")
	public static void verifyElementPresent(WebDriver driver, Properties prop, List<String> columns) {
		try {
			driver.findElement(getLocator(prop, columns.get(2), columns.get(3))).isDisplayed();

		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}

	}

	// Verify that element is enabled
	@Step("Verify Element Enabled")
	public static void verifyElementEnabled(WebDriver driver, Properties prop, List<String> columns) {
		try {
			driver.findElement(getLocator(prop, columns.get(2), columns.get(3))).isEnabled();

		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}

	}

	// Verify link is present on the page
	@Step("Verify Link Present")
	public static void LinkPresent(WebDriver driver, List<String> columns) {
		@SuppressWarnings("unused")
		boolean isPresent = false;
		try {
			List<WebElement> element = driver.findElements(By.xpath("//a"));
			for (int i = 1; i <= element.size(); i++) {
				WebElement ele = driver.findElement(By.xpath("(//a)[" + i + "]"));
				if (ele.getText().trim().contains(columns.get(4))) {
					isPresent = true;
					break;
				}
			}

		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}

	// select from dropdown list
	@Step("Drop Down Actions")
	public static void dropdownaction(WebDriver driver, String locatortype, String locatorvalue, String data) {
		Actions action = new Actions(driver);
		if (locatortype.equalsIgnoreCase("id")) {
			WebElement element = driver.findElement(By.id(locatorvalue));
			action.moveToElement(element).perform();
			action.click().sendKeys(data).sendKeys(Keys.ENTER).build().perform();
		}

		else if (locatortype.equalsIgnoreCase("xpath")) {
			WebElement element = driver.findElement(By.xpath(locatorvalue));
			action.moveToElement(element).perform();
			action.click().sendKeys(data).sendKeys(Keys.ENTER).build().perform();
		}
	}

	// scrolling to view the element
	@Step("Scroll Down Actions")
	public static void ScrollDownAction(WebDriver driver, Properties prop, List<String> columns) throws Throwable {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(getLocator(prop, columns.get(2), columns.get(3))));
		Thread.sleep(6000);
	}

	// create method for date generation
	public static String generateDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh_mm_ss");
		return sdf.format(date);
	}

	@Step("Raise Defect In Bugzilla")
	public static void raiseDefectInBugzilla(String Summary) throws Throwable {
		try {
			RequestSpecification httpRequest;
			Response response;
			RestAssured.baseURI = Propertiesfileutil.getEnvValue("BUGZILLA_URL");
			PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
			authScheme.setUserName(Propertiesfileutil.getEnvValue("BUGZILLA_USERNAME"));
			authScheme.setPassword(Propertiesfileutil.getEnvValue("BUGZILLA_PASSWORD"));
			RestAssured.authentication = authScheme;
			httpRequest = RestAssured.given();

			httpRequest.header("Content-Type", "application/json");
			httpRequest.queryParam("Bugzilla_api_key", Propertiesfileutil.getEnvValue("BUGZILLA_KEY"));

			httpRequest.body("{ \"summary\":\"" + Summary
					+ "\",\"product\" : \"Bugzilla\",\"component\" : \"Query/Bug List\",\"version\" : \"unspecified\",\"op_sys\" : \"All\",\"priority\" : \"P1\",\"platform\" : \"All\",\"type\" : \"defect\",\"comment\" : \"Test\"}");

			response = httpRequest.request(Method.POST, "/rest/bug");
			String ResponseBody = response.getBody().asString();
			System.out.println("Response status code is: " + response.getStatusCode());
			System.out.println("Response status line is: " + response.getStatusLine());
			System.out.println(ResponseBody);

			String bugId = ResponseBody.substring(6, 13);
			System.out.println(bugId);

			RequestSpecification httpRequest2 = RestAssured.given();
			httpRequest2.header("Content-Type", "application/json");
			httpRequest2.queryParam("Bugzilla_api_key", Propertiesfileutil.getEnvValue("BUGZILLA_KEY"));
			httpRequest2.queryParam("id", bugId);
			httpRequest2.queryParam("include_fields", "summary,status,resolution");
			Response response2 = httpRequest2.request(Method.GET, "/rest/bug");
			System.out.println(response2.getBody().asString());
		} catch (Exception e) {
			Driverscript.saveFailureScreenShot();
			Driverscript.saveTextLog("Title Validation failed and screen shot is taken");
			Driverscript.testFail = true;
			e.printStackTrace();
			throw e;
		}
	}
}
