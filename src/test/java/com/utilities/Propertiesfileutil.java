package com.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class Propertiesfileutil {

	public static String getEnvValue(String key) throws Throwable, Throwable {
		Properties configproperties = new Properties();
		configproperties.load(new FileInputStream(new String("./ConfigFile/Environment.properties")));
		return configproperties.getProperty(key);
	}

	public static Properties getObjectLocators() throws Throwable, Throwable {
		Properties configproperties = new Properties();
		configproperties.load(new FileInputStream(new String("./ConfigFile/ObjectRepository.properties")));
		return configproperties;
	}
	public static Properties getEnvValues() throws Throwable, Throwable {
		Properties configproperties = new Properties();
		configproperties.load(new FileInputStream(new String("./ConfigFile/Environment.properties")));
		return configproperties;
	}
}
