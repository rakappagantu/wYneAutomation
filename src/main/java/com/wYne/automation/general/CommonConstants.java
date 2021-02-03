package com.wYne.automation.general;

public class CommonConstants {

    private static String plexName = null;
    private static String hdfsPath = null;
	private static String build = null;


    static {
        plexName = System.getProperty(CommonConstants.PLEX_NAME);
        hdfsPath = System.getProperty(CommonConstants.HDFS_PATH);
		build = System.getProperty(CommonConstants.HADOOPLEX_BUILD);
    }

	public static final String SHARED = "shared" ;
	//protected static PropertyUtil props = ConfigManager.getBundle();
	
	//TestNg groups
	public static final String MANAGER_REGRESSION_TESTS = "ManagerRegressionTests";
	public static final String TESTNG_GROUP_ACCOUNT_WIZARD_TESTS = "AccountWizardTests";
    public static final String TESTNG_GROUP_SNAP_COMMON_TESTS = "SnapCommonTests";
	public static final String BIGDATA_REGRESSION_TESTS = "BigDataRegressionTests";
	
	//Data provider names for Tests
	public static final String DP_ULTRA_TASK_TESTS = "ultra_task_tests";
	public static final String DP_BIGDATA_PIPELINE_TESTS = "pipelineTests";
	public static final String DP_SNAP_COMMON_TESTS = "snap_common_tests";
	public static final String DP_FOREACH_TESTS = "foreach_tests";
	public static final String DP_PIPELINE_TESTS = "PipelineTests";
	public static final String DP_PIPELINE_DIFF_TESTS = "PipelineDiffTests";
	public static final String ACCOUNTS_TESTS = "AccountsTests";

	//Run test values
	public static final String RUNTEST = "Execute";
	public static final String RUNTESTVAL = "Y";
	public static final String RUNCATEGORY = "Category";

	public static final String VALIDATION = "condition";

	//Ultra Task Details
	public static final String NO_OF_INSTANCES = "noOfInstances";
	public static final String BEARER_TOKEN = "bearerToken";
	public static final String MAX_FAILURES = "maxFailures";
	public static final String MAX_IN_FLIGHT = "maxInFlight";
	public static final String NO_OF_DOCUMENTS = "noOfDocuments";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String VALUE = "value";
	public static final String FEED_DOCUMENT = "feedDocument";

	//Excel input file names
	public static final String ULTRA_TASK_TESTS_FILE_NAME = "ultraTaskTests.xlsx";
	public static final String BIGDATA_PIPELINE_TESTS_FILE_NAME = "bigDataPipelineTests.xlsx";
	public static final String PIPELINE_TESTS_FILE_NAME = "pipelines.xlsx";
	public static final String PIPELINE_TESTS_RESULTS_FILE_NAME = "pipelines_results.xlsx";
	public static final String PIPELINE_DIFF_TESTS_FILE_NAME = "PipelineDiffTests.xlsx";
    public static final String SNAP_COMMON_TESTS_FILE_NAME = "snapCommonTests.xlsx";
	//public static final String SNAP_COMMON_TESTS_FILE_NAME = "snapCommonTests.xls";
	public static final String FOREACH_TESTS_FILE_NAME = "foreachTests.xlsx";
	public static final String ACCOUNTS_TESTS_FILE_NAME = "accountsvalidation.xlsx";

	//Common Constant Names
	public static final String TEST_CASE_ID = "testCaseId";
	public static final String LOGIN_TYPE = "Login Type";
	public static final String TEST_CASE_DESCRIPTION = "testCaseDescription";
	public static final String ORG_NAME = "orgName";
	public static final String PROJECT_NAME = "projectName";

	public static final String PROJECT_SPACE = "projectSpace";
	public static final String FILE_NAME = "fileName";
	public static final String SLP_FILE_NAME = "slpFileName";
    public static final String PIPELINE_TYPE = "pipelineType";
	public static final String PIPELINE_NAME = "pipelineName";
	public static final String ORG_PHASE_NAME = "phaseName";
	public static final String TASK_NAME = "taskName";
	public static final String SNAPLEX_NAME = "snaplexName";
	public static final String PLEX_NAME = "plexName";
	public static final String HADOOPLEX_BUILD = "hadooplexbuild";
	public static final String FILE_OWNER = "fileOwner";
	public static final String FILE_SIZE = "fileSize";
	public static final String HDFS_PATH = "hdfsPath";
	public static final String USER_TYPE = "userType";
	public static final String ACCOUNT_NAME = "accountName";
	public static final String SNAPPACK_NAME = "snappackName";
	public static final String TABLE_NAME = "tableName";

	//Constants Header Names of REST Headers
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String AUTHORIZATION = "Authorization";
	public static final String ACCEPT = "Accept";
	
	//Constant Header Values
	public static final String ACCEPT_LANGUAGE_VALUE = "en-US,en;q=0.8";
	public static final String ACCEPT_ENCODING_VALUE = "gzip, deflate, sdch";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_XML = "text/xml";
	public static final String ACCEPT_VAL = "*/*";

	//Test SLP files location
	//public static final String TEST_SLP_FILES_PATH = "src/test/resources/";
	public static final String BIG_DATA_TEST_SLP_FILES_PATH = "src/test/resources/bigdata/";
	public static final String TEST_DATA_LOCATION = "src/test/resources/";
	public static final String TEST_DATA_FOLDER_LOCATION = "pipeline-folder/";
	public static final String TEST_DATA_ULTRA_SLP_FILE_LOCATION="ultraslpfiles/";


	public static final String REDSHIFT_ORG_NAME="redshift_trial";


	//Default spark application execution
	public static final String YARN_USER = "yarn";
	public static final String DEFAULT_KERBEROS_USER = "snaplogic/admin@CLOUDDEV.SNAPLOGIC.COM";

	//Task Types
	public static final String TASK_TYPE_ULTRA = "Ultra";
	public static final String TASK_TYPE_TRIGGERED = "Triggered";
	public static final String TASK_TYPE_SCHEDULED = "Scheduled";
	


	
	//Default Button Names
	public static final String CREATE_LABEL = "Create";
	
	//Ultra Task Headers
	public static final String ULTRA_HEADER = "header";
	public static final String ULTRA_URL_STRING = "urlString";
	
	//Delimeters
	public static String COMMA_DELIMITER = ",";
	public static String SEMI_COLN_DELIMITER = ":";
	
	//Webservices testing
	public static final String API_CONFIG_RESOURCES = "src/main/java/com/ispl/webservicesautomation/resources/APIConfig.xml";

	public static String DATE_MODIFIED = "dateModified";
	public static String FILE_PERMISSIONS = "filePermissions";

	//Big Data Common Constants
	//public static final String DEFAULT_HDFS_PATH = "wasb:///slrd-hdi4";
	//public static final String DEFAULT_BIGDATA_HADOOPLEX = "hdi4_plex";
	public static final String DEFAULT_HDFS_PATH = "hdfs://sl-cdh5-5-qabd-01.clouddev.snaplogic.com:8020";
	public static final String DEFAULT_BIGDATA_HADOOPLEX = "DevCluster";

	public static final String HADOOPLEX_NAME = (null != plexName && "" != plexName && !"{$plexName}".equalsIgnoreCase(plexName)?
			System.getProperty(CommonConstants.PLEX_NAME) : DEFAULT_BIGDATA_HADOOPLEX);
	public static final String HADOOPLEX_HDFS_PATH = (null != hdfsPath && "" != hdfsPath && !"{$hdfsPath}".equalsIgnoreCase(hdfsPath)?
			System.getProperty(CommonConstants.HDFS_PATH) : DEFAULT_HDFS_PATH);
	public static final String HADOOPLEX_BUILD_VALUE = (null != build && "" != build && !"{hadooplexbuild}".equalsIgnoreCase(build)?
			System.getProperty(CommonConstants.HADOOPLEX_BUILD):"");

	public static final String EMAIL_SUBJECT = "Subject";
	public static final String EMAIL_BODY = "Body";
	public static final String EMAIL_HEADER = "Header";

	public static final String EMAILUSERNAME = "autoregplatform@gmail.com";
	public static final String EMAILPASSWORD = "snaplogic@12345";
	public static final String EMAIL_SUBJECT_SNAPLOGIC = "SnapLogic";
	public static final String EMAIL_SUBJECT_SERVICE_ACCOUNT_CREATED = "Service Account Created";

	//Colours of Snap
	public static final String EXPECTED_LIGHT_GREEN = "rgb(160, 194, 144)";
	public static final String EXPECTED_GREEN_COLOR = "rgb(53, 136, 46)";
	public static final String EXPECTED_GRAY_COLOR = "rgb(220, 219, 219)";

	public static final String NODE_ACTIVITIES_SNAPLEX = "NodeActivities";

}
