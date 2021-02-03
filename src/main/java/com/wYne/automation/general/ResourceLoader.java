package com.wYne.automation.general;

import com.wYne.automation.config.PropertyUtil;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.InputStream;

public class ResourceLoader {
    private static ResourceLoader instance = null;
    private PropertyUtil properties = null;
    static InputStream in = null;

    private ResourceLoader(){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            in = loader.getResourceAsStream("config.properties");
            properties = new PropertyUtil();
            properties.load(in);

        } catch (ConfigurationException e1) {
            e1.printStackTrace();
        }
    }

    public static ResourceLoader getInstance(){

        if (instance == null) {
            instance = new ResourceLoader();
        }

        return instance;
    }

    protected PropertyUtil getProps(){
        return this.properties;
    }

    public static PropertyUtil getBundle(){
        PropertyUtil props = ResourceLoader.getInstance().getProps();
        //System.out.println("in getBundle");
        return props;
    }

    public void loadProps(File propertiesFile){
        try {
            ResourceLoader.getInstance().properties.load(propertiesFile);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

}
