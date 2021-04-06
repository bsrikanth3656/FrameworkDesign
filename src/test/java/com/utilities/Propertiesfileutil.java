package com.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class Propertiesfileutil {

	
	public static String getvalueforkey(String key) throws Throwable, Throwable
	{
	Properties configproperties = new Properties();
	configproperties.load(new FileInputStream(new String("./ConfigFile/Environment.properties")));
	return configproperties.getProperty(key);
	}
}
