package com.wYne.automation.tests.base;

import com.wYne.automation.general.CommonConstants;
import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.exceptions.DataNotFoundException;
import com.wYne.automation.general.Utils;
import com.wYne.automation.ui.BrowserUtils;
import com.wYne.automation.ui.elements.WyneLogger;
import com.wYne.automation.general.EmailUtils;
import com.wYne.automation.core.SlInitSettingsFactory;

import org.testng.SkipException;
import com.wYne.automation.general.Utils;
import com.wYne.automation.ui.BrowserUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


//@Listeners({Listener.class})
public class BaseTest extends BaseLogger {


    protected BrowserUtils browserUtils;
    protected EmailUtils emailUtils;
    protected Utils utils;
    protected String emailUserKey;



    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass() throws Exception {
        //super.baseLogger();
        System.out.println("in base before of basetest");
        driver = SlInitSettingsFactory.getDriver();
        driver.manage().deleteAllCookies();
        browserUtils = new BrowserUtils(driver);
        browserUtils.maximize();
        emailUtils = new EmailUtils();
        utils = new Utils();

        logger.info("In the BeforeClass");
        String url = ConfigManager.getBundle().getString("env.baseurl");
        logger.info("url:" + url);
        envBaseUrl = ConfigManager.getBundle().getString("env.baseurl");
    }


    @BeforeMethod(alwaysRun = true)
    public void baseBeforeMethod(Method m, Object[] testArgs) throws Exception {





    }
    @AfterClass
    public void baseTestAfterClass() {
        logger.info("In After base class");
        String envBaseUrl = ConfigManager.getBundle().getString("env.baseurl");
        String pod = envBaseUrl.replace("https://", "").split("\\.")[0];



        }


    }