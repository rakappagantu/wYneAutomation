package com.wYne.automation.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PropertyUtil extends PropertiesConfiguration {

	Log logger;


	public PropertyUtil() {

		this.logger = LogFactoryImpl.getLog(PropertyUtil.class);
		this.setLogger(this.logger);

		ArrayList<File> a = new ArrayList<File>();

		//InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		listpropertyfiles ("resources", a);
		try {
			//load(inputStream);
			for (File f : a) {
				load(f.getAbsolutePath());
			}
		}


		catch (ConfigurationException e) {
			e.printStackTrace();
		}

		//Iterator iterator = null; //System.getProperties().keySet().iterator();

		//while(iterator.hasNext()) {
		//	Object key = iterator.next();
		//	this.addPropertyDirect(String.valueOf(key), System.getProperties().getProperty(String.valueOf(key)));
		//}

		//iterator = getKeys();

		//while (iterator.hasNext()){
		//	Object key = iterator.next();
			//System.out.println(String.valueOf(key) + "--" + getPropertyValueOrNull(String.valueOf(key)));
		//}


	}

	private void listpropertyfiles(String directoryName, ArrayList<File> files) {
		File directory = new File(directoryName);

		if (!directory.exists())
			return;
		File[] fList = directory.listFiles();

		for (File file : fList) {
			if (file.isFile()&&file.getName().endsWith(".properties"))
			{
				files.add(file);
			} else if (file.isDirectory()) {
				listpropertyfiles(file.getAbsolutePath(), files);
			}
		}
	}

	/*protected void addPropertyDirect(String key, Object value) {
		if(System.getProperties().containsKey(key) && !System.getProperty(key).equalsIgnoreCase((String)value)) {
			this.logger.info("property [" + key + "] value [" + value + "] ignored! It is overriden with System provided value: [" + System.getProperty(key) + "]");
		} else {
			if(key.toLowerCase().startsWith("system.")) {
				key.substring(key.indexOf(".") + 1);
				System.setProperty(key, (String)value);
			}

			super.addPropertyDirect(key, value);
		}

	}*/

	public PropertyUtil(PropertyUtil prop) {
		this();
		this.append(prop);
	}

	public PropertyUtil(String ... file) {
		this();
		this.load(file);
	}

	public void addAll(Map props) {
		props.entrySet().removeAll(System.getProperties().entrySet());
		Iterator iterator = props.keySet().iterator();

		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			ConfigManager.getBundle().setProperty(key, props.get(key));
		}

	}

	public PropertyUtil(File... file) {
		this();
		this.load(file);
	}

	public boolean load(String ... files) {
		boolean r = true;
		String[] var6 = files;
		int var5 = files.length;

		for(int var4 = 0; var4 < var5; ++var4) {
			String file = var6[var4];
			file = this.getSubstitutor().replace(file);
			this.loadFile(new File(file));
		}

		return r;
	}

	public boolean load(File ... files) {
		boolean r = true;
		File[] var6 = files;
		int var5 = files.length;

		for(int var4 = 0; var4 < var5; ++var4) {
			File file = var6[var4];
			this.loadFile(file);
		}

		return r;
	}

	private boolean loadFile(File file) {
		try {
			if(!file.getName().endsWith("xml") && !file.getName().contains(".xml.")) {
				super.load(new FileInputStream(file));
			} else {
				XMLConfiguration e = new XMLConfiguration(file);
				this.copy(e);
				e.clear();
			}

			return true;
		} catch (ConfigurationException var3) {
			this.logger.error(var3.getMessage());
		} catch (FileNotFoundException var4) {
			this.logger.error(var4.getMessage());
		}

		return false;
	}

	public boolean load(Class cls, String propertyFile) {
		boolean success = false;
		InputStream in = null;

		try {
			in = cls.getResourceAsStream(propertyFile);
			this.load(in);
			success = true;
		} catch (Exception var13) {
			this.logger.error("Unable to load properties from file:" + propertyFile, var13);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException var12) {
					// do nothing
				}
			}

		}

		return success;
	}

	public boolean getBoolean(String key) {
		return this.getBoolean(key, false);
	}

	public String[] getStringArray(String key, String ... defaultValue) {
		String[] retVal = super.getStringArray(key);
		return retVal != null && retVal.length > 0?retVal:(defaultValue == null?new String[0]:defaultValue);
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		try {
			String e = this.getString(key, "").trim();
			boolean val = StringUtils.isBlank(e)?(defaultValue == null?false:defaultValue.booleanValue()):(StringUtils.isNumeric(e)?Integer.parseInt(e) != 0:Boolean.parseBoolean(e) || e.equalsIgnoreCase("T") || e.equalsIgnoreCase("Y") || e.equalsIgnoreCase("YES"));
			return Boolean.valueOf(val);
		} catch (Exception var5) {
			this.logger.error(key + " value not provided properly", var5);
			this.logger.info("Retruning default value for " + key + " : " + defaultValue);
			return Boolean.valueOf(defaultValue == null?false:defaultValue.booleanValue());
		}
	}

	public Object getObject(String key) {
		return super.getProperty(key);
	}

	public String getPropertyValueOrNull(String sPropertyName) {
		return this.getString(sPropertyName);
	}

	public void storePropertyFile(File f) {
		try {
			this.save(f);
		} catch (ConfigurationException var3) {
			this.logger.error(var3.getMessage());
		}

	}

	public void addProperty(String key, Object value) {
		this.clearProperty(key);
		super.addProperty(key, value);
	}	
}

