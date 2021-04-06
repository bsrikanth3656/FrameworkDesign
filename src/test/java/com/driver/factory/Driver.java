package com.driver.factory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Driver {

	private static ThreadLocal<AndroidDriver<MobileElement>> androidDriver = new ThreadLocal<AndroidDriver<MobileElement>>();

	public static void setAndroidDriver(AndroidDriver<MobileElement> driver) {
		androidDriver.set(driver);
	}

	public static AndroidDriver<MobileElement> getAndroidDriver(){
		return androidDriver.get();
	}

}