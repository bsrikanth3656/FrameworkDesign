package com.common.function.library;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.clipboard.HasClipboard;

@Listeners(TestListener.class)
public class MobileFunctionLibrary {
	Robot re = null;

	public static void toBack(AppiumDriver<MobileElement> driver) {
		TouchActions tc = new TouchActions(driver);
		tc.sendKeys(Keys.BACK_SPACE);
	}

	// LongPress
	public static void longPress(AppiumDriver<MobileElement> driver, String locatorvalue) {
		TouchActions action = new TouchActions(driver);
		action.longPress(driver.findElement(By.xpath(locatorvalue)));
		action.perform();
	}

	// ScrollDown
	public static void scrollDown(AppiumDriver<MobileElement> driver) {
		JavascriptExecutor js = driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);
	}

	// ScrollUp
	public static void scrollup(AppiumDriver<MobileElement> driver) {
		JavascriptExecutor js = driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "up");
		js.executeScript("mobile: scroll", scrollObject);

	}

	public static boolean isElementPresent(AppiumDriver<MobileElement> driver, Properties prop, List<String> columns) {
		try {

			return driver.findElements(WebFunctionLibrary.getLocator(prop, columns.get(2), columns.get(3))).size() > 0;
		} catch (Exception e) {
			System.out.println("INFO : Element is not displayed to clickable");
			return false;
		}

	}

	public static void pasteTxt(AppiumDriver<MobileElement> driver) {
		try {
			driver.hideKeyboard();
		} catch (Exception ign) {
		}

		String pasteTxt = ((HasClipboard) driver).getClipboardText();
		System.out.println("Paste content" + pasteTxt);
	}

	public static String pasteTxt() {
		String pasteValue = null;
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		DataFlavor flavor = DataFlavor.stringFlavor;
		if (clipboard.isDataFlavorAvailable(flavor)) {
			try {
				pasteValue = (String) clipboard.getData(flavor);
				System.out.println(pasteValue);
			} catch (UnsupportedFlavorException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		return pasteValue;
	}

	public static By getByValue(WebElement element) {
		By by = null;
		// System.out.println("element=" + element);
		String byValue[] = element.toString().split("By.chained");
		String locator = byValue[1].replaceAll("[({})]*", "").split(":")[0].trim();
		String value = byValue[1].replaceAll("[({})]*", "").split(" ")[1].trim();

		switch (locator.toLowerCase()) {
		case "by.id":
			by = By.id(value);
			break;
		case "by.className":
			by = By.className(value);
			break;
		case "by.xpath":
			by = By.xpath(value);
			break;
		case "by.cssSelector":
			by = By.cssSelector(value);
			break;
		case "by.name":
			by = By.name(value);
			break;
		default:
			throw new IllegalStateException("locator : " + locator + " not found!!!");
		}
		return by;
	}

	public static void threadSleep(int timeInSeconds) {
		try {
			Thread.sleep(timeInSeconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void isElementLoaded(WebElement elementToBeLoaded, AppiumDriver<MobileElement> driver) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(elementToBeLoaded));
	}

	public static By locatortype(String type, String value) {

		By locName = null;
		if (type.equalsIgnoreCase("xPath")) {
			locName = By.xpath(value);
		} else if (type.equalsIgnoreCase("id")) {
			locName = By.id(value);
		} else if (type.equalsIgnoreCase("linkText")) {
			locName = By.linkText(value);
		} else if (type.equalsIgnoreCase("classname")) {
			locName = By.className(value);
		} else if (type.equalsIgnoreCase("name")) {
			locName = By.name(value);
		} else
			locName = By.partialLinkText(value);
		return locName;

	}

	public boolean isWebElementPresent(By locator, int maxWaitTime, AppiumDriver<MobileElement> driver) {
		Boolean flag = false;
		int timeOut = 0;
		while (timeOut < maxWaitTime) {
			if (driver.findElements(locator).size() > 0) {
				flag = true;
				break;
			}

			timeOut = timeOut + 2;

		}

		return flag;

	}

	public static boolean isWebElementVisible(String path, String type, AppiumDriver<MobileElement> driver) {
		Boolean flag = false;
		if (driver.findElements(locatortype(type, path)).size() > 0) {
			flag = true;
		}

		return flag;

	}

	public static boolean writeInInput(String path, String type, String data, AppiumDriver<MobileElement> driver) {
		WebElement element = driver.findElement(locatortype(type, path));
		if (element.isDisplayed()) {
			// element.clear();
			element.sendKeys(data);
			driver.hideKeyboard();

			// System.out.println("Value Entered");
			return true;
		} else {
			return false;
		}
	}

	public static boolean hideKeyboard(AppiumDriver<MobileElement> driver) {

		driver.hideKeyboard();
		return true;

	}

	public static boolean type(AppiumDriver<MobileElement> driver, Properties prop, List<String> columns, String data) {

		WebElement element = driver.findElement(WebFunctionLibrary.getLocator(prop, columns.get(2), columns.get(3)));
		if (element == null) {
			return false;
		} else {
			// element.clear();
			element.sendKeys(data);
			hideKeyboard(driver);

			return true;
		}

	}

	public static boolean setValue(String path, String type, String data, AppiumDriver<MobileElement> driver) {
		MobileElement element = driver.findElement(locatortype(type, path));
		element.sendKeys(data);
		System.out.println("Value Entered");
		return true;

	}

	public static boolean enter(AppiumDriver<MobileElement> driver) throws IOException {
		Runtime.getRuntime().exec("adb shell input keyevent 66");
		return true;

	}

	public static boolean clearAppData(AppiumDriver<MobileElement> driver) throws IOException {
		Runtime.getRuntime().exec("adb shell pm clear com.cloudfmgroup.cloudFMApp");
		return true;

	}

	public static boolean swipeIOS(AppiumDriver<MobileElement> driver, Properties prop, List<String> columns,
			String data) {
		MobileElement element = driver.findElement(WebFunctionLibrary.getLocator(prop, columns.get(2), columns.get(3)));
		element.setValue(data);

		System.out.println("Value Entered");
		return true;

	}

	public static boolean enterInput(AppiumDriver<MobileElement> driver, Properties prop, List<String> columns,
			String data) {
		WebElement element = driver.findElement(WebFunctionLibrary.getLocator(prop, columns.get(2), columns.get(3)));

		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", element, data);

		System.out.println("Value Entered");
		return true;

	}

	public static boolean click(String path, String type, AppiumDriver<MobileElement> driver)
			throws InterruptedException {
		Thread.sleep(1500);

		// WebElement element = (explicitWaitForElement(path, type));
		WebElement element = driver.findElement(locatortype(type, path));
		element.click();
		return true;
	}

	public static WebElement getWebElement(AppiumDriver<MobileElement> driver, Properties prop, List<String> columns) {

		return driver.findElement(WebFunctionLibrary.getLocator(prop, columns.get(2), columns.get(3)));
	}

	public static WebElement getWebElement2(By locator, AppiumDriver<MobileElement> driver) {

		return driver.findElement(locator);
	}

	public static boolean tap(AppiumDriver<MobileElement> driver, Properties prop, List<String> columns) {
		// MobileElement element =
		// driver.findElement(WebFunctionLibrary.getLocator(prop, columns.get(2),
		// columns.get(3)));
		// TouchAction action = new TouchAction(driver);
		// action.tap(element).perform();
		return true;
	}

	public boolean verifyAllVauesOfDropDown(String path, String type, String data) {

		boolean flag = false;
		WebElement element = explicitWaitForElement(path, type, null);
		List<WebElement> options = element.findElements(By.tagName("option"));
		String temp = data;
		String allElements[] = temp.split(",");
		String actual;
		for (int i = 0; i < allElements.length; i++) {

			System.out.println("Actual : " + options.get(i).getText());

			System.out.println("Expected: " + allElements[i].trim());
			actual = options.get(i).getText().trim();
			if (actual.equals(allElements[i].trim())) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}

		return flag;

	}

	public boolean verifyCurrentDateInput(String path, String type) {
		boolean flag = false;
		WebElement element = explicitWaitForElement(path, type, null);
		String actual = element.getAttribute("value").trim();
		DateFormat DtFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		DtFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
		String expected = DtFormat.format(date).toString().trim();
		if (actual.trim().contains(expected)) {
			flag = true;

		}
		return flag;

	}

	public boolean selectList(final String path, String type, String data) {

		Boolean flag = false;

		WebElement select = explicitWaitForElement(path, type, null);

		List<WebElement> options = select.findElements(By.tagName("option"));
		String expected = data.trim();
		System.out.println("Expected: " + expected);
		for (WebElement option : options) {

			String actual = option.getText().trim();
			System.out.println("Actual: " + actual);
			if (actual.equals(expected)) {

				option.click();
				flag = true;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return flag;
			}
		}

		return flag;
	}

	public boolean verifyDropdownSelectedValue(String path, String type, String data) {

		Boolean flag = false;
		WebElement select = explicitWaitForElement(path, type, null);

		Select sel = new Select(select);
		String defSelectedVal = sel.getFirstSelectedOption().getText();

		if (defSelectedVal.trim().equals(data.trim())) {
			flag = true;

			return flag;
		} else {
			return flag;
		}
	}

	public static boolean verifyElementSize(String path, String type, int size, AppiumDriver<MobileElement> driver) {

		List<MobileElement> elements = driver.findElements(locatortype(type, path));

		if (elements.size() == size) {
			System.out.println("Element is Present " + size + "times");
			return true;
		} else {
			System.out.println("Element is not Present with required size");

			return false;
		}
	}

	public static boolean uploadFilesUsingSendKeys(String path, String type, String data,
			AppiumDriver<MobileElement> driver) throws InterruptedException {
		WebElement element = driver.findElement(locatortype(type, path));
		element.clear();
		element.sendKeys(System.getProperty("user.dir") + "\\src\\test\\resources\\uploadFiles\\" + data);
		System.out.println("Value Entered");
		return true;

	}

	public static boolean writeInInputCharByChar(String path, String type, String data,
			AppiumDriver<MobileElement> driver) throws InterruptedException {
		WebElement element = driver.findElement(locatortype(type, path));
		element.clear();
		String b[] = data.split("");

		for (int i = 0; i < b.length; i++) {

			element.sendKeys(b[i]);
			Thread.sleep(1000);

		}
		System.out.println("Value Entered");
		return true;

	}

	public static boolean isRadioSelected(String path, String type) {

		WebElement element = (explicitWaitForElement(path, type, null));
		if (element.isSelected()) {
			return true;
		} else {

			return false;
		}
	}

	public static boolean isRadioNotSelected(String path, String type) {

		WebElement element = (explicitWaitForElement(path, type, null));
		if (element.isSelected()) {
			return false;
		} else {

			return true;
		}
	}

	public static boolean clearInput(String path, String type, AppiumDriver<MobileElement> driver) {
		WebElement element = driver.findElement(locatortype(type, path));

		element.clear();

		System.out.println("input field cleared");
		return true;

	}

	public boolean delDirectory() {
		File delDestination = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\downloadFile");
		if (delDestination.exists()) {
			File[] files = delDestination.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					delDirectory();
				} else {
					files[i].delete();
				}
			}
		}
		return (delDestination.delete());
	}

	public boolean verifyCssProperty(String path, String type, String data) {

		String property[] = data.split(":", 2);
		String exp_prop = property[0];
		String exp_value = property[1];
		boolean flag = false;
		String prop = (explicitWaitForElement(path, type, null)).getCssValue(exp_prop);
		System.out.println(prop);

		if (prop.trim().equals(exp_value.trim())) {
			flag = true;
			return flag;
		}

		else {
			return flag;

		}

	}

	public static boolean switchContext(AppiumDriver<MobileElement> driver) {
		boolean colFlag1 = false;
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains("WEBVIEW")) {
				driver.context(contextName);
				// System.out.println("switched to webview");
				colFlag1 = true;
				break;
			}

		}
		return colFlag1;

	}

	public static boolean switchContext1(AppiumDriver<MobileElement> driver) {
		boolean colFlag1 = false;
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.contains("NATIVE")) {
				driver.context(contextName);
				// System.out.println("switched to native");
				colFlag1 = true;
				break;
			}

		}
		return colFlag1;

	}

	public static void HideKeyboard(AppiumDriver<MobileElement> driver) {
		driver.hideKeyboard();
	}

	public static void SpiralWheel(AppiumDriver<MobileElement> driver, By locator) {
		// String txt1 = "PRE_UAT Test Location";
		// MobileElement el1 = driver.findElement(locator);
		JavascriptExecutor js = driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);

	}

	public static String verifyCurrentDate() {

		DateFormat DtFormat = new SimpleDateFormat("MMM dd yyyy");
		Date date = new Date();
		DtFormat.setTimeZone(TimeZone.getTimeZone("BST"));
		String expected = DtFormat.format(date).toString().trim();
		return expected;
	}

	public static WebElement explicitWaitForElement(String path, String type, AppiumDriver<MobileElement> driver) {
		WebDriverWait wait = new WebDriverWait(driver, (10));

		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locatortype(type, path)));

		return element;

	}

	public void runBackGround(Duration time, AppiumDriver<MobileElement> driver) {
		driver.runAppInBackground(time);
	}

	/* To ZoomOut */
	public void robotZoomOut() {
		try {
			re = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		re.keyPress(KeyEvent.VK_CONTROL);
		re.keyPress(KeyEvent.VK_SUBTRACT);
		re.keyRelease(KeyEvent.VK_SUBTRACT);
		re.keyRelease(KeyEvent.VK_CONTROL);
	}

	/* To ZoomIn */
	public void robotZoomIn() {
		try {
			re = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		re.keyPress(KeyEvent.VK_CONTROL);
		re.keyPress(KeyEvent.VK_ADD);
		re.keyRelease(KeyEvent.VK_ADD);
		re.keyRelease(KeyEvent.VK_CONTROL);
	}

	/* To ScrollUp using ROBOT */
	public void robotScrollUp(By locator, String message) {
		try {
			re = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		re.keyPress(KeyEvent.VK_PAGE_UP);
		re.keyRelease(KeyEvent.VK_PAGE_UP);
	}

	/* To ScrollDown using ROBOT */
	public void robotScrollDown(By locator, String message) throws Exception {
		re = new Robot();
		re.keyPress(KeyEvent.VK_PAGE_DOWN);
		re.keyRelease(KeyEvent.VK_PAGE_DOWN);
	}

}
