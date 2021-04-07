package com.common.function.library;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import com.driver.factory.Driver;
import com.utilities.Excelfileutil;
import com.utilities.Propertiesfileutil;
import org.openqa.selenium.JavascriptExecutor;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Functionlibrary {

	public static SoftAssert sa;
	public static JavascriptExecutor js;
	public static Excelfileutil excel;
	static DesiredCapabilities capa = new DesiredCapabilities();
	public static String authToken = null;
//	public static AndroidDriver<MobileElement> mdriver;

	// method for kickstart of browser // returntype is WebDriver
	// (.......................)
	public static WebDriver startbrowser(WebDriver driver) throws Throwable {
		if (Propertiesfileutil.getvalueforkey("Browser").equalsIgnoreCase("firefox"))

		{
			// System.setProperty("webdriver.gecko.driver","E:\\ahmed\\eclipse-workspace\\Adactin\\geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (Propertiesfileutil.getvalueforkey("Browser").equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			// System.setProperty("webdriver.chrome.driver","D:\\Automation\\chromedriver_win32");
			driver = new ChromeDriver();
		} else if (Propertiesfileutil.getvalueforkey("Browser").equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			// System.setProperty("webdriver.ie.driver","commonjarfiles/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if (Propertiesfileutil.getvalueforkey("Execution").equalsIgnoreCase("browserstack")) {
			String USERNAME = "srikanthb2";
			String AUTOMATE_KEY = "skPLxEmXoLUqsMx5fzZE";
			String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
			Hashtable<String, String> capsHashtable = new Hashtable<String, String>();
			if (Propertiesfileutil.getvalueforkey("Browser").equalsIgnoreCase("browserstackchrome")) {
				capsHashtable.put("browser", "chrome");
				capsHashtable.put("browser_version", "89.0");
				capsHashtable.put("os", "Windows");
				capsHashtable.put("os_version", "10");
				capsHashtable.put("build", "browserstack-build-1");
				capsHashtable.put("name", "BrowserStack-Chrome-89");
			}
			if (Propertiesfileutil.getvalueforkey("Browser").equalsIgnoreCase("browserstackfirefox")) {
				capsHashtable.put("browser", "firefox");
				capsHashtable.put("browser_version", "87.0");
				capsHashtable.put("os", "OS X");
				capsHashtable.put("os_version", "Big Sur");
				capsHashtable.put("build", "browserstack-build-1");
				capsHashtable.put("name", "BrowserStack-Firefox-87");
			}
			if (Propertiesfileutil.getvalueforkey("Browser").equalsIgnoreCase("browserstackie")) {
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

		return driver;
	}

//	public static AndroidDriver<MobileElement> setUpAppium(AndroidDriver<MobileElement> mdriver) throws Throwable {
//
//		if (Propertiesfileutil.getvalueforkey("Execution").equalsIgnoreCase("cloud")) {
//
//			Connector con = new Connector("https://skillsoft.pcloudy.com");
//			try {
//				authToken = con.authenticateUser(Propertiesfileutil.getvalueforkey("Username"),
//						Propertiesfileutil.getvalueforkey("Apikey"));
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (ConnectError e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			// System.out.println("Going to delete apk");
//			// try {
//			// con.deleteFileFromCloud(authToken, "Artisan.apk", "data");
//			// } catch (IOException e1) {
//			// // TODO Auto-generated catch block
//			// e1.printStackTrace();
//			// }
//			// System.out.println("Able to delete apk");
//
//			// Adding capabilities
//			capa.setCapability("pCloudy_Username", Propertiesfileutil.getvalueforkey("Username"));
//			capa.setCapability("pCloudy_ApiKey", Propertiesfileutil.getvalueforkey("Apikey"));
//			capa.setCapability("pCloudy_DurationInMinutes", 10);
//			capa.setCapability("newCommandTimeout", 600);
//			capa.setCapability("launchTimeout", 90000);
//			capa.setCapability("pCloudy_DeviceFullName", "GOOGLE_Pixel3XL_Android_11.0.0_7fb3a");
//			capa.setCapability("platformVersion", "11.0.0");
//			capa.setCapability("platformName", "Android");
//			capa.setCapability("automationName", "uiautomator2");
//			capa.setCapability("pCloudy_ApplicationName", "Percipio_Android_release847.apk");
//			capa.setCapability("appPackage", "asdf");
//			capa.setCapability("appActivity", "asfd");
//			capa.setCapability("pCloudy_WildNet", "false");
//			capa.setCapability("pCloudy_EnableVideo", "false");
//			capa.setCapability("pCloudy_EnablePerformanceData", "false");
//			capa.setCapability("pCloudy_EnableDeviceLogs", "false");
//
//			try {
//				System.out.println("Going to Initiate Driver");
//				mdriver = new AndroidDriver<MobileElement>(new URL("https://skillsoft.pcloudy.com/appiumcloud/wd/hub"),
//						capa);
//				Driver.setAndroidDriver(mdriver);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//		return mdriver;
//	}
//
//	// method for open application
//	public static void deleteApp() throws Throwable {
//		System.out.println("Going to delete apk");
//		try {
//			Connector con = new Connector("https://skillsoft.pcloudy.com");
//			try {
//				authToken = con.authenticateUser(Propertiesfileutil.getvalueforkey("Username"),
//						Propertiesfileutil.getvalueforkey("Apikey"));
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (ConnectError e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			con.deleteFileFromCloud(authToken, "Artisan.apk", "data");
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}

	// method for open application
	public static void openapplication(WebDriver driver) throws Throwable {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(Propertiesfileutil.getvalueforkey("url1"));
	}

	// method for close application
	public static void closeapplication(WebDriver driver) {
		driver.close();
//		driver.quit();
	}

	// method for click operation
	public static void clickaction(WebDriver driver, String locatortype, String locatorvalue) {
		if (locatortype.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorvalue)).click();
		} else if (locatortype.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorvalue)).click();
		} else if (locatortype.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorvalue)).click();
		} else if (locatortype.equalsIgnoreCase("linkText")) {
			driver.findElement(By.linkText(locatorvalue)).click();
		} else if (locatortype.equalsIgnoreCase("partialLinkText")) {
			driver.findElement(By.partialLinkText(locatorvalue)).click();
		} else if (locatortype.equalsIgnoreCase("tagName")) {
			driver.findElement(By.tagName(locatorvalue)).click();
		}
	}

	// method for actions
	public static void typeaction(WebDriver driver, String locatortype, String locatorvalue, String data)
			throws Throwable {
		if (locatortype.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorvalue)).clear();
			Thread.sleep(2000);
			driver.findElement(By.id(locatorvalue)).sendKeys(data);
		} else if (locatortype.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(data);
		} else if (locatortype.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorvalue)).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath(locatorvalue)).sendKeys(data);

		} else if (locatortype.equalsIgnoreCase("linkText")) {
			driver.findElement(By.linkText(locatorvalue)).clear();
			Thread.sleep(2000);
			driver.findElement(By.linkText(locatorvalue)).sendKeys(data);

		} else if (locatortype.equalsIgnoreCase("partialLinkText")) {
			driver.findElement(By.partialLinkText(locatorvalue)).clear();
			Thread.sleep(2000);
			driver.findElement(By.partialLinkText(locatorvalue)).sendKeys(data);

		} else if (locatortype.equalsIgnoreCase("tagName")) {
			driver.findElement(By.tagName(locatorvalue)).clear();
			Thread.sleep(2000);
			driver.findElement(By.tagName(locatorvalue)).sendKeys(data);

		}
	}

	// method for wait for element to display statement
	public static void waiting(WebDriver driver, String locatortype, String locatorvalue) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		if (locatortype.equalsIgnoreCase("id")) {
			wait.until((ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue))));
		} else if (locatortype.equalsIgnoreCase("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
		} else if (locatortype.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}
	}

	// method for title validation
	public static void titlevalidation(WebDriver driver, String exp_title) {
		String act_title = driver.getTitle();
		System.out.println(act_title);
		sa.assertEquals(exp_title, act_title);
	}

	// select from dropdown list

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

	public static void Scrolldownaction(WebDriver driver) throws Throwable {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,2200)", "");
		Thread.sleep(6000);
	}

	// create method for date generation
	public static String generateDate() {
		Date date = new Date();// import Date from java.util
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh_mm_ss");
		return sdf.format(date);
	}
	
	public static void raiseDefectInBugzilla(String Summary) {
		RequestSpecification httpRequest;
		Response response;
		RestAssured.baseURI = "https://bugzilla.mozilla.org";
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("srikanth281191shirmisha@zohomail.in");
		authScheme.setPassword("EnS3UV&2+_TV$ag#!@");
		RestAssured.authentication = authScheme;
		httpRequest = RestAssured.given();

		httpRequest.header("Content-Type", "application/json");
		httpRequest.queryParam("Bugzilla_api_key", "1kOtiCOAA3F83ajiIFSqQl9OwidLowBYLILUM48n");

		httpRequest.body("{ \"summary\":\"" + Summary + "\",\"product\" : \"Bugzilla\",\"component\" : \"Query/Bug List\",\"version\" : \"unspecified\",\"op_sys\" : \"All\",\"priority\" : \"P1\",\"platform\" : \"All\",\"type\" : \"defect\",\"comment\" : \"Test\"}");

		response = httpRequest.request(Method.POST, "/rest/bug");
		String ResponseBody = response.getBody().asString();
		System.out.println("Response status code is: " + response.getStatusCode());
		System.out.println("Response status line is: " + response.getStatusLine());
		System.out.println(ResponseBody);
		
		String bugId = ResponseBody.substring(6,13);
		System.out.println(bugId);

		RequestSpecification httpRequest2 = RestAssured.given();
		httpRequest2.header("Content-Type", "application/json");
		httpRequest2.queryParam("Bugzilla_api_key", "1kOtiCOAA3F83ajiIFSqQl9OwidLowBYLILUM48n");
		httpRequest2.queryParam("id", bugId);
		httpRequest2.queryParam("include_fields", "summary,status,resolution");
		Response response2 = httpRequest2.request(Method.GET, "/rest/bug");
		System.out.println(response2.getBody().asString());
	}
}