package com.wYne.automation.tests.base;

import com.ispl.automation.common.CommonConstants;
import com.slqa.annotations.ClassData;
import com.slqa.annotations.SlTest;
import com.slqa.annotations.TestData;
import com.slqa.config.ConfigManager;
import com.slqa.datatypes.LoginUserType;
import com.wYne.automation.exceptions.DataNotFoundException;
import com.wYne.automation.exceptions.WyneException;
import com.slqa.ui.core.TCMetaData;
import com.snaplogic.automation.framework.core.ResultLogger;
import com.snaplogic.automation.framework.core.SlInitSettingsFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by Sunil B on 13/5/16.
 */
//@Listeners({ Listener.class })
public class BaseLogger extends com.wYne.automation.tests.base.WebDriverBaseTest {





	@BeforeClass(alwaysRun = true)
	public void baseLoggerBeforeClass() throws Exception {
		testPassName = ConfigManager.getBundle().getString("testPassName");
		usersMap = SlInitSettingsFactory.getUsersMap();

		classDataAnnotation = this.getClass().getAnnotation(ClassData.class);
		if(!classDataAnnotation.clsLvlPrjName().isEmpty()){
			classLevelProjectName = classDataAnnotation.clsLvlPrjName();
		}
		if(!classDataAnnotation.clsLvlPrjSpace().isEmpty()){
			classLevelProjectSpace = classDataAnnotation.clsLvlPrjSpace();
		}

		// added a check to throw exception if users are not available
		if (usersMap == null || usersMap.size() < 1) {
			throw new DataNotFoundException("Users not available of types : [{}]" + classDataAnnotation.userTypeList().toString());
		}


		//tracker.setClassname(this.getClass().getCanonicalName());
		//tracker.setStage("BEFORE_CLASS");
		//tracker.setMethodname("BEFORE_CLASS");
		//SlDriverTrackerFactory.setTracker(tracker);

		logger.info("In the BeforeClass");
		lPassedTests = ResultLogger.getPassedTestIds(this.getClass().getName());
	}

		/*switch (loginType){
			case BASIC_USER:
				loginName = usersMap.get(LoginUserType.BASIC_USER).getUserName();
				loginPassword = usersMap.get(LoginUserType.BASIC_USER).getUserPassword();
				/*loginName = ConfigManager.getBundle().getString("BasicUserName");
				loginPassword = ConfigManager.getBundle().getString("BasicUserPasswd");
				break;
			case ADMIN:
				loginName = usersMap.get(LoginUserType.ADMIN).getUserName();
				loginPassword = usersMap.get(LoginUserType.ADMIN).getUserPassword();
/*				loginName = ConfigManager.getBundle().getString("adminname");
				loginPassword = ConfigManager.getBundle().getString("adminpwd");
				break;
			case BIGDATA_USER:
				loginName = usersMap.get(LoginUserType.BIGDATA_USER).getUserName();
				loginPassword = usersMap.get(LoginUserType.BIGDATA_USER).getUserPassword();
				ConfigManager.getBundle().setProperty("defaultplex",CommonConstants.HADOOPLEX_NAME);
				/*loginName = ConfigManager.getBundle().getString("srusername");
				loginPassword = ConfigManager.getBundle().getString("sruserpwd");
				break;

			case REDSHIFT_USER:
				loginName = usersMap.get(LoginUserType.REDSHIFT_USER).getUserName();
				loginPassword = usersMap.get(LoginUserType.REDSHIFT_USER).getUserPassword();
				/*loginName = ConfigManager.getBundle().getString("redshiftusername");
				loginPassword = ConfigManager.getBundle().getString("redshiftpwd");
				break;

			case SYSADMIN:
				loginName = usersMap.get(LoginUserType.SYSADMIN).getUserName();
				loginPassword = usersMap.get(LoginUserType.SYSADMIN).getUserPassword();
				/*loginName = ConfigManager.getBundle().getString("sysadminname");
				loginPassword = ConfigManager.getBundle().getString("sysadminpwd");

				break;

		}
	}*/



}
