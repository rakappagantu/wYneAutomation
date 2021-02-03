package com.wYne.automation.ui.core;


import com.wYne.automation.exceptions.WyneException;
import com.wYne.automation.ui.elements.WyneLogger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaian on 2/8/16.
 */
public class BrowserCapabilities {


    public enum BrowserType{
        FIREFOX("firefox"),
        CHROME("chrome"),
        CHROME_HEADLESS("chrome-headless"),
        IE("ie"),
        SAFARI("safari");

        String browserName;
        BrowserType(String browserName) { this.browserName = browserName;}

        public String getBrowserName(){
            return this.browserName;
        }
    }

    private static BrowserType getBrowserType(String name) throws WyneException {
        for (BrowserType browserType :  BrowserType.values()){
            if (name.equalsIgnoreCase(browserType.browserName))
                return browserType;
        }
        throw new WyneException("No browser type identified for the given browser name : " + name);
    }

    // This is no more required as we only support the chrome browser and hence rewrote this
    @Deprecated
    public static DesiredCapabilities getDesiredCapabilities (String browserName) throws WyneException {
        DesiredCapabilities capabilities = null;
        BrowserType browserType = getBrowserType(browserName);

        switch (browserType){

            case FIREFOX:
                /*FirefoxOptions ffOptions = new FirefoxOptions();
                String os = System.getProperty("os.name").toLowerCase();
                String ffBinary = "";
                if (os.contains("windows")) {
                    ffBinary = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
                }
                if (os.contains("linux")) {
                    ffBinary = "/usr/bin/firefox";
                }
                if (os.contains("mac")) {
                    ffBinary = "/usr/bin/firefox";
                }
                ffOptions.setBinary(ffBinary); //Location where Firefox is installed
                capabilities.setCapability("moz:firefoxOptions", ffOptions);*/

                //set more capabilities as per your requirements
                capabilities = DesiredCapabilities.firefox();

                //ffoptions.addArguments("--disable-extensions");
                //capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, ffoptions);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("marionette", true);
                capabilities.setBrowserName("firefox");
                break;

            case CHROME:

                ChromeOptions options = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("profile.default_content_settings.popups", 0);
                options.addArguments("disable-extensions");
                prefs.put("credentials_enable_service", false);
                prefs.put("password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);

                capabilities = DesiredCapabilities.chrome();
                options.addArguments("--disable-extensions");
                options.addArguments("disable-infobars");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setBrowserName("chrome");
                //capabilities.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));

                capabilities.setPlatform(Platform.WIN8);
                //String chromeDriverPath = "./server/chromedriver";
                //System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                break;

            case SAFARI:
                SafariOptions sfoptions = new SafariOptions();
                sfoptions.setUseCleanSession(true);
                capabilities = DesiredCapabilities.safari();
                capabilities.setCapability(SafariOptions.CAPABILITY, sfoptions);
                capabilities.setBrowserName("safari");
                break;

            case IE:
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, false);
                capabilities.setBrowserName("internet explorer");
                break;

            case CHROME_HEADLESS:

                ChromeOptions choptions = new ChromeOptions();

                Map<String, Object> chprefs = new HashMap<String, Object>();
                chprefs.put("profile.default_content_settings.popups", 0);
                choptions.addArguments("disable-extensions");
                chprefs.put("credentials_enable_service", false);
                chprefs.put("password_manager_enabled", false);
                choptions.setExperimentalOption("prefs", chprefs);

                //capabilities = DesiredCapabilities.chrome();
                capabilities = DesiredCapabilities.chrome();
                choptions.addArguments("--disable-extensions");
                choptions.addArguments("disable-infobars");
                choptions.addArguments("--headless", "--disable-gpu", "--window-size=1024,768");
                capabilities.setCapability(ChromeOptions.CAPABILITY, choptions);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setBrowserName("chrome");
                capabilities.setPlatform(Platform.LINUX);
                //capabilities.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));

                //capabilities.setPlatform(Platform.WIN8);
                //String chromeDriverPath = "./server/chromedriver";
                //System.setProperty("webdriver.chrome.driver", chromeDriverPath);
                break;
        }
        WyneLogger.LOGGER.debug("Setting the Browser : [{}] with capabilities : [{}]", browserName, capabilities);
        return capabilities;
    }

    /***
     * New method to get the chrome options for either the headless or the real browser
     * @param browserName
     * @return
     * @throws WyneException
     */
    public static ChromeOptions getChromeOptions (String browserName) throws WyneException {
        BrowserType browserType = getBrowserType(browserName);
        ChromeOptions options = null;

        switch (browserType){

            case CHROME:
                options = new ChromeOptions();
                options.addArguments("disable-extensions");
                options.addArguments("disable-infobars");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-infobars");
                options.addArguments("ignore-certificate-errors");
                break;

            case CHROME_HEADLESS:

                options = new ChromeOptions();
                options.addArguments("disable-extensions");
                options.addArguments("--disable-extensions");
                options.addArguments("disable-infobars");
                options.addArguments("--disable-infobars");
                options.addArguments("ignore-certificate-errors");
                options.setHeadless(true);
                options.addArguments("window-size=1920,1080");
                break;
        }
        WyneLogger.LOGGER.debug("Setting the Browser : [{}] with capabilities : [{}]", browserName, options);
        return options;
    }

}
