package com.common.function.library;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.driver.factory.Driverscript;

public class TestListener implements ITestListener {
	WebFunctionLibrary functionlibrary = new WebFunctionLibrary();
	static Driverscript ds = new Driverscript();

	@Override
	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));

	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
		try {
//			WebFunctionLibrary.raiseDefectInBugzilla(result.getMethod().getMethodName());
		} catch (Exception e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause());
		} catch (Throwable e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause());
		}
	}

}
