package com.wYne.automation.tests.base;

import com.ispl.automation.common.CommonConstants;
import com.ispl.automation.common.DataUtils;
import com.ispl.automation.pages.LoginPage;
import com.ispl.automation.pages.dashboard.DashboardPage;
import com.ispl.automation.pages.designer_new.DesignerPage_New;
import com.ispl.automation.pages.manager.ManagerPage;
import com.slqa.annotations.BugDetails;
import com.slqa.annotations.TestData;
import com.slqa.api.SLRestServices;
import com.slqa.config.ConfigManager;
import com.slqa.datatypes.LoginUserType;
import com.slqa.datatypes.UserBean;
import com.slqa.exceptions.DataNotFoundException;
import com.slqa.exceptions.SLExceptionForBug;
import com.slqa.exceptions.SLRestServiceException;
import com.slqa.general.Utils;
import com.slqa.ui.BrowserUtils;
import com.slqa.ui.core.TCMetaData;
import com.slqa.ui.elements.SnapLogger;
import com.snaplogic.automation.framework.EmailUtils;
import com.snaplogic.automation.framework.core.SlInitSettingsFactory;
import org.testng.SkipException;
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

    public SLRestServices restServ;
    protected BrowserUtils browserUtils;
    protected EmailUtils emailUtils;
    protected Utils utils;
    protected DataUtils localDataUtils;
    protected com.slqa.general.DataUtils globalDataUtils;
    protected BugDetails bugDetails;



    protected LoginPage loginPage;
    protected Map<SnaplexMapKeys, String> snaplexMap;
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
                slRestServicesMap.put(tmpLoginUserType, new SLRestServices(ConfigManager.getBundle().getString("env.baseurl"),myuser, mypwd));
            }
        }

        SlInitSettingsFactory.setRestServices(slRestServicesMap);
		
		/*
        SLRestServices tmpRestService = new SLRestServices(usersMap.get(loginType).getUserName(), usersMap.get(loginType).getUserPassword());
        slRestServicesMap.put(loginType, tmpRestService);
		if(usersMap.get(LoginUserType.ADMIN) != null) {
			tmpRestService = new SLRestServices(usersMap.get(LoginUserType.ADMIN).getUserName(), usersMap.get(LoginUserType.ADMIN).getUserPassword());
			slRestServicesMap.put(LoginUserType.ADMIN, tmpRestService);
		}*/

        SlInitSettingsFactory.setRestServices(slRestServicesMap);
        restServ = SlInitSettingsFactory.getRestServicesMap().get(loginType);

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
            //projectNameAttr = CommonConstants.DEFAULT_PROJECT_NAME;

            // Set values from Test data annotation or dataMap or get default
            // values
            orgNameAttr = setValue(antTestData.orgName(), CommonConstants.ORG_NAME, orgNameAttr);
            projectSpaceAttr = setValue(antTestData.projectSpace(), CommonConstants.PROJECT_SPACE, projectSpaceAttr);
            projectNameAttr = setValue(antTestData.projectName(), CommonConstants.PROJECT_NAME, projectNameAttr);
            pipeLineNameAttr = setValue(antTestData.pipeLineName(), CommonConstants.PIPELINE_NAME, pipeLineNameAttr);
            importPipelineFileAttr = setValue(antTestData.importPipelineFile(), CommonConstants.FILE_NAME,
                    importPipelineFileAttr);
            taskNameAttr = setValue(antTestData.taskName(), CommonConstants.TASK_NAME, taskNameAttr);
            snaplexNameAttr = setValue(antTestData.snaplexName(), CommonConstants.SNAPLEX_NAME, snaplexNameAttr);
            accountNameAttr = setValue(antTestData.accountName(), CommonConstants.ACCOUNT_NAME, accountNameAttr);
            orgPhaseName = setValue(antTestData.orgPhaseName(), CommonConstants.ORG_PHASE_NAME, orgPhaseName);
            snappackNameAttr = setValue(antTestData.snappackName(), CommonConstants.SNAPPACK_NAME, snappackNameAttr);
            fileNameAttr = setValue(antTestData.fileName(), CommonConstants.FILE_NAME, fileNameAttr);
            tableNameAttr = setValue(antTestData.tableName(), CommonConstants.TABLE_NAME, tableNameAttr);


            SLRestServices delRestServ = null;

            delRestServ = SlInitSettingsFactory.getRestServicesMap().get(LoginUserType.ADMIN);

            SnapLogger.LOGGER.info("%%% -- " + Thread.currentThread().getName() + " -- ");

            if (projectNameAttr != null && !projectNameAttr.equalsIgnoreCase("1_Automation_Project")) {
                if (antTestData.deleteProject()) {
                    delRestServ.deleteProject(orgNameAttr, projectSpaceAttr, projectNameAttr);
                }
            }


            if (projectSpaceAttr != null && !projectSpaceAttr.equalsIgnoreCase("projects")) {
                if (antTestData.deleteProjectSpace()) {
                    delRestServ.deleteProjectSpace(orgNameAttr, projectSpaceAttr);
                }
            }

            if (null != pipeLineNameAttr && !pipeLineNameAttr.isEmpty() && !pipeLineNameAttr.equalsIgnoreCase("")) {
                if (antTestData.deletePipeline()) {
                    delRestServ.deletePipeline(orgNameAttr, projectSpaceAttr, projectNameAttr, pipeLineNameAttr);
                }
            }

            if (null != taskNameAttr && !taskNameAttr.isEmpty() && !taskNameAttr.equalsIgnoreCase("")) {
                if (antTestData.deleteTask()) {
                    delRestServ.deleteTask(orgNameAttr, projectSpaceAttr, projectNameAttr, taskNameAttr);
                }
                if (antTestData.createTask()) {
                    // reset service to create task
                }
            }

            if (null != snaplexNameAttr && !snaplexNameAttr.isEmpty() && !snaplexNameAttr.equalsIgnoreCase("")) {
                if (antTestData.checkIfSnaplexIsUp()) {
                    if (!delRestServ.isSnaplexUp(orgNameAttr, snaplexNameAttr)) {
                        throw new SkipException("Skipping tests because snaplex was not available.");
                    }
                }
            }
            if (null != snaplexNameAttr && !snaplexNameAttr.isEmpty() && !snaplexNameAttr.equalsIgnoreCase("")) {
                if (antTestData.deleteSnaplex()) {
                    delRestServ.deleteSnaplex(orgNameAttr, projectSpaceAttr, projectNameAttr, snaplexNameAttr);
                }
            }

            if (null != accountNameAttr && !accountNameAttr.isEmpty() && !accountNameAttr.equalsIgnoreCase("")) {
                if (antTestData.deleteAccount()) {
                    delRestServ.deleteAccount(orgNameAttr, projectSpaceAttr, projectNameAttr, accountNameAttr);
                }

				/*if (antTestData.createTask()){
					//reset service to create account
				}*/
            }
            if (null != snappackNameAttr && !snappackNameAttr.isEmpty() && !snappackNameAttr.equalsIgnoreCase("")) {
                if (antTestData.deleteSnappack()) {
                    delRestServ.deleteSnapPack(orgNameAttr, projectSpaceAttr, projectNameAttr, snappackNameAttr);
                }
				/*if (antTestData.createTask()){
					//reset service to create account
				}*/
            }
            if (null != fileNameAttr && !fileNameAttr.isEmpty() && !fileNameAttr.equalsIgnoreCase("")) {
                if (antTestData.deleteFile()) {
                    delRestServ.deleteFile(orgNameAttr, projectSpaceAttr, projectNameAttr, fileNameAttr);
                }
            }

            if (null != tableNameAttr && !tableNameAttr.isEmpty() && !tableNameAttr.equalsIgnoreCase("")) {
                if (antTestData.deleteTable()) {
                    delRestServ.deleteTable(orgNameAttr, projectSpaceAttr, projectNameAttr, tableNameAttr);
                }
            }

            //restServ = SlInitSettingsFactory.getRestServicesMap().get(loginType);

            SnapLogger.LOGGER.info("$$$ - " + Thread.currentThread().getName() + " -- " + restServ.toString());

            if (projectNameAttr != null) {
                if (antTestData.createProject()) {
                    // rest service to create project
                    restServ.createProject(orgNameAttr, projectSpaceAttr, projectNameAttr);
                }
            }

            if (null != pipeLineNameAttr && !pipeLineNameAttr.isEmpty() && !pipeLineNameAttr.equalsIgnoreCase("")) {
                if (antTestData.createPipeline()) {
                    // reset service to create pipeline
                    restServ.createPipeline(loginName, orgNameAttr, projectSpaceAttr, projectNameAttr, pipeLineNameAttr);
                }
            }

            if (null != importPipelineFileAttr && !importPipelineFileAttr.isEmpty()) {
                restServ.importPipeline(orgNameAttr, projectSpaceAttr, projectNameAttr, importPipelineFileAttr, pipeLineNameAttr,
                        orgPhaseName, null);
                importPipelineFileAttr = null;
            }
        }

        if (usersMap.containsKey(LoginUserType.BIGDATA_USER)) {
            defaultPlex = snaplexNameAttr;
        } else {
            defaultPlex = getDefaultPlex(orgNameAttr, SlInitSettingsFactory.getRestServicesMap().get(loginType));
        }

        driver.manage().deleteAllCookies();
// 		driver.manage().window().maximize();
    }

    /**
     * This method is used to get the value from either testdata Annotation or
     * from dataMap or from default value
     *
     * @param testDataValue
     * @param dataMapKey
     * @return
     */
    private String setValue(String testDataValue, String dataMapKey, String defaultValue) {
        // TODO Auto-generated method stub
        String returnValue = null;
        if (null != testDataValue && !testDataValue.isEmpty()) {
            returnValue = testDataValue;
        } else if (null != dataMap && null != dataMap.get(dataMapKey) && !dataMap.get(dataMapKey).isEmpty()) {
            returnValue = dataMap.get(dataMapKey);
        } else {
            returnValue = defaultValue;
        }
        return returnValue;
    }

    private String getDefaultPlex(String org, SLRestServices restService) {
        String defaultPlex = null;
        //Map<String,List<String>> activeplexes = restService.getActiveSnaplexesinOrg(org);
        Map<String, List<String>> activeplexes = new HashMap<>();

        try {
            activeplexes = restService.getActiveSnaplexesinOrg(org);
        } catch (SLRestServiceException exc) {
            activeplexes = restService.getActiveSnaplexesinOrg(org);
        }

        for (String s : activeplexes.keySet()) {
            List a = activeplexes.get(s);
            if (a.contains(ConfigManager.getBundle().getString("plex.pipeLineQueing"))) {
                a.remove(ConfigManager.getBundle().getString("plex.pipeLineQueing"));
                activeplexes.put(s, a);
            }
        }
        if (activeplexes.get("cloudPlexes").size()!=0 && activeplexes.get("cloudPlexes").get(0) != "newsnaplex"){
        	defaultPlex = activeplexes.get("cloudPlexes").get(0);
        	}else if (activeplexes.get("cFeedMasterPlexes").size()!=0){
        	defaultPlex = activeplexes.get("cFeedMasterPlexes").get(0);
        	}else if (activeplexes.get("groundPlexes").size()!=0 && activeplexes.get("groundPlexes").get(0) != "newsnaplex"){
        	defaultPlex = activeplexes.get("groundPlexes").get(0);
        	}else if (activeplexes.get("gFeedMasterPlexes").size()!=0){
        	defaultPlex = activeplexes.get("gFeedMasterPlexes").get(0);
        	}
        	return defaultPlex;
    }


    @AfterClass
    public void baseTestAfterClass() {
        logger.info("In After base class");
        String envBaseUrl = ConfigManager.getBundle().getString("env.baseurl");
        String pod = envBaseUrl.replace("https://", "").split("\\.")[0];
       // this.releaseUsers(SlInitSettingsFactory.getUsersMap(), pod);
        SLRestServices slRestServices = new SLRestServices(ConfigManager.getBundle().getString("env.baseurl"), usersMap.get(LoginUserType.ADMIN).getUserName(),
                usersMap.get(LoginUserType.ADMIN).getUserPassword());
        if (orgNameAttr != null && classLevelProjectSpace != null) {
            try {
                if (!classLevelProjectSpace.equalsIgnoreCase(CommonConstants.SHARED) && !classLevelProjectSpace.equalsIgnoreCase("projects")) {
                    slRestServices.deleteProjectSpace(orgNameAttr, classLevelProjectSpace);
                    logger.info("Deleted project space ======> "+orgNameAttr+"/"+classLevelProjectSpace);
                } else if (classLevelProjectName != null && !classLevelProjectName.equalsIgnoreCase(CommonConstants.SHARED)) {
                    slRestServices.deleteProject(orgNameAttr, classLevelProjectSpace, classLevelProjectName);
                    logger.info("Deleted project ======> "+orgNameAttr+"/"+classLevelProjectSpace+"/"+classLevelProjectName);
                }
            } catch (Exception e) {
                logger.error("Unable to delete project space or project ======== " + orgNameAttr + "/" + classLevelProjectSpace + "/" + classLevelProjectName);
            }
        }

       /* Map<LoginUserType, SLRestServices> slRestServicesMap = SlInitSettingsFactory.getRestServicesMap();
        for (LoginUserType tmpLoginUserType : usersMap.keySet()) {
            slRestServices = slRestServicesMap.get(tmpLoginUserType);
            slRestServices.setNewUIFlag(false);
        }*/

        if(emailUserKey != null){
            try{
            List<String> userList = slRestServices.getUserListOfOrg(orgNameAttr);
            for(String user : userList){
                if(user.toLowerCase().contains(emailUserKey.toLowerCase())){

                        slRestServices.deleteUser(user);
                    }
                }
            }
            catch (Exception e) {
                logger.error("Unable to delete users");
            }
        }

        Map<LoginUserType, SLRestServices> userMap = SlInitSettingsFactory.getRestServicesMap();
        Set<LoginUserType> keySet = userMap.keySet();
        for (LoginUserType key : keySet) {
            //SLRestServices slRestServicesTemp = SlInitSettingsFactory.getRestServicesMap().get(key);
            try {
                slRestServices.logout();
            } catch (Exception e) {
                logger.error("Unable to delete session for user ========> " +usersMap.get(key).getUserName());
            }
        }


    }


}