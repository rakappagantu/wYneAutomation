package com.wYne.automation.tests.base;

import com.ispl.automation.common.CommonConstants;
import com.ispl.automation.common.DataUtils;
import com.ispl.automation.pages.LoginPage;
import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.exceptions.DataNotFoundException;
import com.wYne.automation.general.Utils;
import com.wYne.automation.ui.BrowserUtils;
import com.wYne.automation.ui.elements.WyneLogger;
import com.wYne.automation.general.EmailUtils;
import com.snaplogic.automation.framework.core.SlInitSettingsFactory;
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
    protected DataUtils localDataUtils;
    protected com.slqa.general.DataUtils globalDataUtils;



    protected LoginPage loginPage;
    protected String emailUserKey;
    public boolean newAssetPalette = false;


    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass() throws Exception {
        //super.baseLogger();
        System.out.println("in base before of basetest");
        driver = SlInitSettingsFactory.getDriver();

        driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
        browserUtils = new BrowserUtils(driver);
        browserUtils.maximize();
        emailUtils = new EmailUtils();
        utils = new Utils();
        localDataUtils = new DataUtils();
        globalDataUtils = new com.slqa.general.DataUtils();

        logger.info("In the BeforeClass");
        String url = ConfigManager.getBundle().getString("env.baseurl");
        logger.info("url:" + url);
        loginPage = new LoginPage();
        emailUserKey = null;
        envBaseUrl = ConfigManager.getBundle().getString("env.baseurl");
    }


    @BeforeMethod(alwaysRun = true)
    public void baseBeforeMethod(Method m, Object[] testArgs) throws Exception {

        SlInitSettingsFactory.getAssertionService().clearAll();
        assertServ = SlInitSettingsFactory.getAssertionService();

        bugDetails = m.getAnnotation(BugDetails.class);

        antTestData = m.getAnnotation(TestData.class);
        methodName = m.getName();

        // updates username and password if required
        LoginUserType loginType = getLoginType();
        Map<LoginUserType, UserBean> usersMap = SlInitSettingsFactory.getUsersMap();

        if (!usersMap.containsKey(loginType))
            throw new DataNotFoundException("No user available for the LoginType:" + loginType);

        loginName = usersMap.get(loginType).getUserName();
        loginPassword = usersMap.get(loginType).getUserPassword();
        //tracker.("Login used : [" + loginName + "]");
        TCMetaData tcmetadata = SlInitSettingsFactory.getTCMetaData();
        //if (tracker != null)
        //	tracker.log(testCaseId,methodName,tcmetadata.getClassName(),"Login used : [" + loginName + "]");

        //if(testArgs.length !=0 && testArgs[0] instanceof LinkedHashMap<?, ?>) {
        //	dataMap = (LinkedHashMap<String, String>) testArgs[0];
        //	methodName = dataMap.get(CommonConstants.TEST_CASE_ID);
        //}

        //testCaseId = methodName; // TODO: will remove this later point

        //if (ConfigManager.getBundle().getBoolean("enableTracker", false) && SlInitSettingsFactory.getTracker() != null)
        //SlInitSettingsFactory.getTracker().setTestCaseId(testCaseId);

        projectNameAttr = classLevelProjectName;
        projectSpaceAttr = classLevelProjectSpace;
        //restServ = SlInitSettingsFactory.getRestServicesMap().get(LoginUserType.ADMIN);
        // setting the map again for that thread as creating this at login and test case logout is causing 401 errors
        Map<LoginUserType, SLRestServices> slRestServicesMap = new HashMap<>();

        // forced log out
        // at times seeing an issue of session being retained inspite of logging out
        // so checking the by navigating to manager page and see if the session is still on, if so logout
        loginPage.doLogout();

        //driver.manage().deleteAllCookies();

        for (LoginUserType tmpLoginUserType : usersMap.keySet()) {
            if (!tmpLoginUserType.equals(LoginUserType.SSO_USER)) {
                String myuser = usersMap.get(tmpLoginUserType).getUserName();
                String mypwd = usersMap.get(tmpLoginUserType).getUserPassword();
                slRestServicesMap.put(tmpLoginUserType, new SLRestServices(ConfigManager.getBundle().getString("env.baseurl"), myuser, mypwd));
            }
        }

        SlInitSettingsFactory.setRestServices(slRestServicesMap);

        if (m.isAnnotationPresent(BugDetails.class)) {
            throw new SLExceptionForBug(bugDetails.bugId());
        }

        // Login to Web Services with specific user
        //TODO : have to remove this
        //restServ = new SLRestServices(loginName, loginPassword);

        //orgNameAttr = ConfigManager.getBundle().getString("org.primaryOrg");

        // As of now I added as default project space name but has to be passed
        // as mandatory
        //projectSpaceAttr = CommonConstants.DEFAULT_PROJECT_SPACE_NAME;
        if (m.isAnnotationPresent(TestData.class) || (dataMap != null && !dataMap.isEmpty())) {
            driver.manage().deleteAllCookies();
// 		driver.manage().window().maximize();
        }



    }
    @AfterClass
    public void baseTestAfterClass() {
        logger.info("In After base class");
        String envBaseUrl = ConfigManager.getBundle().getString("env.baseurl");
        String pod = envBaseUrl.replace("https://", "").split("\\.")[0];



        }


    }


}