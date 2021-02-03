package com.wYne.automation.config;

import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.InputStream;

public class ConfigManager {

	private PropertyUtil properties;

	private static ConfigManager INSTANCE = null;

	private ConfigManager() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			InputStream in = loader.getResourceAsStream("config.properties");
			properties = new PropertyUtil();
			if (in != null)
				properties.load(in);
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
	}

	public static ConfigManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ConfigManager();
		}
		return INSTANCE;
	}

	protected PropertyUtil getProps() {
		return this.properties;
	}

	public static PropertyUtil getBundle() {
		PropertyUtil props = ConfigManager.getInstance().getProps();
		//System.out.println("in getBundle");
		return props;
	}

	public void loadProps(File propertiesFile) {
		try {
			ConfigManager.getInstance().properties.load(propertiesFile);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void loadProps(InputStream inputStream) {
		try {
			ConfigManager.getBundle().load(inputStream);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
}


