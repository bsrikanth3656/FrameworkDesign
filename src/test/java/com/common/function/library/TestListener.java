package com.common.function.library;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.driver.factory.Driverscript;

public class TestListener implements ITestListener {
	Web_Functionlibrary functionlibrary = new Web_Functionlibrary();
	static Driverscript ds = new Driverscript();

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
		try {
			// Functionlibrary.raiseDefectInBugzilla(result.getMethod().getMethodName());
		} catch (Exception e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause());
		} catch (Throwable e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause());
		}
	}

}
