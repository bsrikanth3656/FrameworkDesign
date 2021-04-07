package com.driver.factory;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

public class Apptest {

	@Test(priority = 1)
	@Story("Base support for bdd annotations")
	@Description("Some detailed test description")
	@Step("Type {user.name} / {user.password}.")
	@Severity(SeverityLevel.CRITICAL)
	@Epic("Allure examples 1")
	@Feature("TestNG support 2")
	public void kickStart() throws Throwable {

		Driverscript ds = new Driverscript();

		try

		{
			ds.startTest();
			// Assert.fail("Failed intentionally");
		} catch (Exception e)

		{
			e.printStackTrace();

		}

	}

	@Test(priority = 2)
	@Story("Base support for bdd annotations")
	@Description("Some detailed test description")
	@Step("Type {user.name} / {user.password}.")
	@Severity(SeverityLevel.CRITICAL)
	@Epic("Allure examples 2")
	@Feature("TestNG support 2")
	public void kickStart2() throws Throwable {

		Driverscript ds = new Driverscript();

		try

		{
			ds.startTest();
			Assert.fail("Failed intentionally");
		} catch (Exception e)

		{
			e.printStackTrace();

		}

	}
	
	@Test(priority = 3)
	@Story("Base support for bdd annotations")
	@Description("Some detailed test description")
	@Step("Type {user.name} / {user.password}.")
	@Severity(SeverityLevel.CRITICAL)
	@Epic("Allure examples 3")
	@Feature("TestNG support 3")
	public void kickStart3() throws Throwable {

		Driverscript ds = new Driverscript();

		try

		{
			ds.startTest();
		} catch (Exception e)

		{
			e.printStackTrace();

		}

	}

}
