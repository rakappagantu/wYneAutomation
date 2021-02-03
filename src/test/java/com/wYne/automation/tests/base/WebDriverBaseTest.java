package com.wYne.automation.tests.base;

import com.ispl.automation.pages.TemplatePage;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.slqa.config.ConfigManager;
import com.slqa.config.PropertyUtil;
import com.slqa.datatypes.LoginUserType;
import com.slqa.datatypes.UserBean;
import com.slqa.datatypes.UserInfo;
import com.slqa.exceptions.DataNotFoundException;
import com.slqa.exceptions.SLRestServiceException;
import com.slqa.general.ResourceLoader;
import com.slqa.general.SlResultEntity;
import com.slqa.general.Utils;
import com.slqa.ui.core.BrowserCapabilities;
import com.slqa.ui.core.SlAssertionService;
import com.slqa.ui.core.TCMetaData;
import com.slqa.ui.core.Waiting;
import com.slqa.ui.driver.SlExtendedWebDriver;
import com.slqa.ui.driver.SlWebDriver;
import com.snaplogic.automation.framework.Listener;
import com.snaplogic.automation.framework.NewTrackerLog;
import com.snaplogic.automation.framework.TrackerFactory;
import com.snaplogic.automation.framework.core.SlInitSettingsFactory;
import com.wYne.automation.config.*;
import com.wYne.automation.ui.core.BrowserCapabilities;
import com.wYne.automation.ui.driver.SlExtendedWebDriver;
import com.wYne.automation.ui.driver.SlWebDriver;
import io.restassured.response.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.service.DriverCommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.Optional;
import org.testng.annotations.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;


public class WebDriverBaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    //protected List<String> tcLog = new ArrayList<String>();
    protected WebDriver driver;
    protected String testPassName;


    public static PropertyUtil props;
    protected Waiting waiting;
    public String envBaseUrl;


    protected static long startedMilliSecs; //variable to capture the class level duration in milliseconds

    private String getSystemProperty(String key){
        if (System.getProperties().containsKey(key)){
            return System.getProperty(key);
        }
        return "";
    }

    private void setChromeDriver(){
        PropertyUtil props = ConfigManager.getBundle();

        if (!ConfigManager.getBundle().getString("selenium.defaultBrowser").contains("chrome"))
            return;

        String os = System.getProperty("os.name").toLowerCase();
        String chromeDriverPath = ConfigManager.getBundle().getString("webdriver.chrome.driver");
        if (os.toLowerCase().contains("windows")) {
            chromeDriverPath = "./server/chromedriver.exe";
        }

        if (os.toLowerCase().contains("linux")) {
            chromeDriverPath = "./server/chromedriver";
        }

        if (os.toLowerCase().contains("mac")) {
            chromeDriverPath = "./server/mac_chromedriver";
        }
        System.out.println("current system path is ------------> "+System.getProperty("user.dir"));
        //System.out.println("Chrome driver path -----------> "+System.getProperty("user.dir")+chromeDriverPath);
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        System.out.println("set chrome driver path ----------> "+System.getProperty("webdriver.chrome.driver"));
    }

    private void setFirefoxDriver(){

        if (!ConfigManager.getBundle().getString("selenium.defaultBrowser").equalsIgnoreCase("firefox"))
            return;

        String os = System.getProperty("os.name").toLowerCase();
        String firefoxDriverPath = ConfigManager.getBundle().getString("webdriver.gecko.driver");
        if (os.contains("windows")) {
            firefoxDriverPath = "./server/geckodriver.exe";
        }

        if (os.contains("linux")) {
            firefoxDriverPath = "./server/geckodriver";
        }

        if (os.contains("mac")) {
            firefoxDriverPath = "./server/geckodriver";
        }

        System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
        //System.setProperty("webdriver.firefox.marionette",firefoxDriverPath);
        //System.setProperty("webdriver.firefox.marionette","true");
    }

    private void setTrackerProp() {
        String enableTracker = getSystemProperty("enableTracker");

        if (!enableTracker.isEmpty()){
            ConfigManager.getBundle().setProperty("enableTracker",enableTracker);
        }
    }



    private void updateConfigurations(@Optional("") String baseUrl, @Optional("") String browser, @Optional("") String server){
        if (!(baseUrl.isEmpty())){
            //System.out.println("setting env.baseurl from xml file:" + baseUrl);
            ConfigManager.getBundle().setProperty("env.baseurl", baseUrl);
        }
        if (!(browser.isEmpty())){
            ConfigManager.getBundle().setProperty("selenium.defaultBrowser", browser);
        }
        if (!(server.isEmpty())){
            ConfigManager.getBundle().setProperty("selenium.server", server);
        }
    }

    private Set keysToBeDeletedFromProps() throws Exception {
        String[] excludedFiles =  {"Canary_config.properties","UAT_config.properties","Budgy_config.properties","Pebv2_config.properties"};
        Properties prop = new Properties();
        for (String s:excludedFiles){
            prop.load(new FileInputStream(new File("resources/"+s)));
        }
        return prop.keySet();
    }

    @BeforeSuite(alwaysRun = true)
    @Parameters({"env.baseurl", "selenium.defaultBrowser", "selenium.server","testrail.testrun"})
    public void beforeSuite(@Optional("") String baseUrl, @Optional("") String browser,
                            @Optional("") String server, @Optional("") String testrun) throws Exception {
        ResourceLoader.getInstance();
        setLogsAndOutPutDir();
        updateConfigurations(baseUrl, browser, server);
        this.envBaseUrl = ConfigManager.getBundle().getString("env.baseurl");

        testPassName = ConfigManager.getBundle().getString("testPassName");
        String tmpUrl = ConfigManager.getBundle().getString("env.baseurl").replace("https://","");
        podName = tmpUrl.substring(0,tmpUrl.indexOf("."));

        if (podName.equalsIgnoreCase("stagexl"))
            podName = "stage";
        if (podName.equalsIgnoreCase("uatv2lbtest"))
            podName = "uat";

        Set keysToBeDeleted = keysToBeDeletedFromProps();
        for (Object key:keysToBeDeleted) {
            ConfigManager.getBundle().clearProperty(key.toString());
        }
        baseUrl = ConfigManager.getBundle().getString("env.baseurl");
        if (podName.contains("canary")){
            ConfigManager.getInstance().loadProps(new File("resources/Canary_config.properties"));
        }else if (baseUrl.contains("uat")) {
            ConfigManager.getInstance().loadProps(new File("resources/UAT_config.properties"));
        }else if (baseUrl.contains("budgy")) {
            ConfigManager.getInstance().loadProps(new File("resources/Budgy_config.properties"));
        }else if (baseUrl.contains("ux")) {
            ConfigManager.getInstance().loadProps(new File("resources/UX_config.properties"));
        }else if (baseUrl.contains("stage")) {
            ConfigManager.getInstance().loadProps(new File("resources/Stage_config.properties"));
        }else if (baseUrl.contains("pebv2")) {
            ConfigManager.getInstance().loadProps(new File("resources/Pebv2_config.properties"));
        }else if (baseUrl.contains("tahoe")) {
            ConfigManager.getInstance().loadProps(new File("resources/Tahoe_config.properties"));
        }else if (baseUrl.contains("condor")) {
            ConfigManager.getInstance().loadProps(new File("resources/Condor_config.properties"));
        }else if (podName.contains("elastic")) {
            ConfigManager.getInstance().loadProps(new File("resources/Elastic_config.properties"));
        }
        else if (podName.contains("perfxl")) {
        ConfigManager.getInstance().loadProps(new File("resources/Perf_config.properties"));
        }

        setTrackerProp();


        String port = getSystemProperty("selenium.port");
        if (!port.isEmpty()){
            ConfigManager.getBundle().setProperty("selenium.port", port);
        }

        if(ConfigManager.getBundle().getString("selenium.defaultBrowser").contains("chrome")){
            setChromeDriver();
        }else if (ConfigManager.getBundle().getString("selenium.defaultBrowser").contains("firefox")){
            setFirefoxDriver();
        }
    }


    private void setLogsAndOutPutDir(){

        String envUrl = getSystemProperty("env.baseurl");
        //System.out.println("fromLogsAndOutPut:" + envUrl.isEmpty() + "--" + envUrl);

        if (!envUrl.isEmpty()){
            ConfigManager.getBundle().setProperty("env.baseurl", envUrl);
        }

        String outputDir = ConfigManager.getBundle().getString("outputDir");
        String screenshotsDir = ConfigManager.getBundle().getString("selenium.screenshots.dir");

        logger.info("OUTPUT DIR --" + outputDir);

        //int lowLevelLog = Integer.parseInt(ResourceLoader.getBundle().getString("lowLevelLog"));
        String lowLevelLog = getSystemProperty("lowLevelLog");

        if (!lowLevelLog.isEmpty()){
            ConfigManager.getBundle().setProperty("lowLevelLog", lowLevelLog);
        }

        String testPassName = getSystemProperty("testPassName");

        if (!testPassName.isEmpty()){
            ConfigManager.getBundle().setProperty("testPassName",testPassName);
        }


        if ((outputDir == null) || outputDir.isEmpty()) {
            //System.out.println("Is it NULL ?");
            outputDir = getSystemProperty("user.dir") + "/test-results";
            screenshotsDir = getSystemProperty("user.dir") + "/test-results/img";
            System.out.println("outputDir-- " + outputDir);
            ConfigManager.getBundle().setProperty("outputDir", outputDir);
            System.setProperty("outputDir", outputDir);
            ConfigManager.getBundle().setProperty("selenium.screenshots.dir", screenshotsDir);
        }

        String log4jConfigFile = getSystemProperty("user.dir") + File.separator + "lib" + File.separator+ "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
    }

    private SlWebDriver getDriverObject(MutableCapabilities capabilities){
        SlWebDriver driver = null;
        props = ConfigManager.getBundle();

        if (props.getString("selenium.server") == null ||
                props.getString("selenium.server").isEmpty() ||
                props.getString("selenium.server").equalsIgnoreCase("localhost")){
            logger.debug("grid is not set, going with local webdriver");

            if (capabilities.getBrowserName().contains(BrowserCapabilities.BrowserType.CHROME.getBrowserName())){
                driver = new SlExtendedWebDriver(new DriverCommandExecutor(ChromeDriverService.createDefaultService()), capabilities);
            }
        }
        else
            try {
                logger.info("Initiating the driver with :" + props.getString("selenium.server")  + ":" + props.getString("selenium.port"));
                driver = new SlExtendedWebDriver(new URL("http://"+props.getString("selenium.server")+":"+ props.getString("selenium.port") + "/wd/hub"), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        return driver;

    }

    @Parameters({"selenium.defaultBrowser"})
    @BeforeClass(alwaysRun = true)
    public void setupClass(@Optional("") String browserName) throws Exception {
        this.envBaseUrl = ConfigManager.getBundle().getString("env.baseurl");

        MutableCapabilities capabilities;
        SlWebDriver tempDriver;

        logger.info("baseurl in setup class ---------> " + ConfigManager.getBundle().getString("env.baseurl"));
        logger.debug(ConfigManager.getBundle().getString("selenium.defaultBrowser"));

        /*ConfigManager.getBundle().setProperty("ignore.skipped.testResult", "false");
        ConfigManager.getBundle().setProperty("capture.screenshot", "true");
        ConfigManager.getBundle().setProperty("configFailure", null);*/

        if (browserName.isEmpty())
            browserName = ConfigManager.getBundle().getString("selenium.defaultBrowser");
        capabilities = BrowserCapabilities.getChromeOptions(browserName);
        tempDriver = getDriverObject(capabilities);



    }
    @AfterMethod(alwaysRun = true)
    public void afterMethodBase(ITestResult result, Object[] testArgs) {

        try {
            SlInitSettingsFactory.setTCEntity(new TCMetaData());
            SlInitSettingsFactory.getDriver().navigate().refresh();
            new TemplatePage().logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass(alwaysRun = true)
    public synchronized void tearDown() throws Exception {
        Map<String, SlResultEntity> resultEntityMap;

        logger.debug("********************* TEAR DOWN " + Thread.currentThread().getName() + "*************" + this.getClass().getCanonicalName()+ "********");
        try {
            //if (ResourceLoader.getBundle().getString("users.from.local","true").equalsIgnoreCase("true"))
            //    removeHashCodesFromList();
            //else
            releaseUsers(SlInitSettingsFactory.getUsersMap(), podName);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        logger.debug("********************* TEAR DOWN  ------ END ----" + Thread.currentThread().getName() + "*********************");

        try {
            SlInitSettingsFactory.getDriver().close();
            SlInitSettingsFactory.getDriver().quit();
        }catch (Exception e){
            e.printStackTrace();
        }

       if (ConfigManager.getBundle().getString("testrail.enable").equalsIgnoreCase("true")){
           resultEntityMap = SlInitSettingsFactory.getResultsMap();
           updateTestRail(resultEntityMap);
           logger.info("#### Updating TestRail with results for className : " + this.getClass().getCanonicalName());
       }

        long duration = new Date().getTime() - startedMilliSecs;
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        logger.info("Time Taken for class :[{}] is [{}]", this.getClass().getCanonicalName(), hms);
        SlInitSettingsFactory.setTracker(null);
        SlInitSettingsFactory.setAssertionService(null);
        SlInitSettingsFactory.setDriver(null);
        SlInitSettingsFactory.setUsersMap(null);
        SlInitSettingsFactory.setResultsMap(null);
    }
}
