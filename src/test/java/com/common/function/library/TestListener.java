package com.common.function.library;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.driver.factory.Driverscript;

import io.qameta.allure.Attachment;

public class TestListener implements ITestListener {
	Functionlibrary functionlibrary = new Functionlibrary();
	Driverscript ds = new Driverscript();
	

	@Attachment(value = "Page Screenshot", type = "image/png")
	public byte[] saveFailureScreenShot() {
		return ds.captureImageBytes();
	}

	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}
	
	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
	}

	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));

	}

	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));

	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");

	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		
		WebDriver driver = Driverscript.driver;
		if(driver instanceof WebDriver) {
			System.out.println("Screenshot is captured for test case: "+result.getMethod().getMethodName()+saveFailureScreenShot());
		}
		saveTextLog(result.getMethod().getMethodName() + " failed and screen shot is taken");
		try {
//			 Functionlibrary.raiseDefectInBugzilla(result.getMethod().getMethodName());
		} catch (Exception e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause());
		} catch (Throwable e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause());
		}
	}
	

}
