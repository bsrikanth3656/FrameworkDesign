package com.driver.factory;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.common.function.library.TestListener;
import com.google.common.collect.ImmutableMap;
import com.utilities.Propertiesfileutil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import ru.yandex.qatools.allure.annotations.Title;

@Listeners(TestListener.class)
@Epic("Mouritech Common framework")
@Feature("Apptest")
public class Apptest {

	Driverscript ds = new Driverscript();

	@BeforeSuite
	void setAllureEnvironment() throws Throwable {
		allureEnvironmentWriter(
				ImmutableMap.<String, String>builder().put("Browser", Propertiesfileutil.getEnvValue("Browser"))
						.put("Browser.Version", "89").put("URL", Propertiesfileutil.getEnvValue("URL")).build(),
				System.getProperty("user.dir") + "/allure-results/");
	}

	@Test()
	@Title("Common framework")
	@Story("Kick Start")
	@Description("Starting point of framework")
	@Severity(SeverityLevel.CRITICAL)
	public void KickStart() throws Throwable {

		try

		{
			ds.startTest();
		} catch (Exception e)

		{
			e.printStackTrace();
		}
	}
}
