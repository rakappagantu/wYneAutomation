package com.wYne.automation.tests.base;

import com.ispl.automation.common.CommonConstants;
import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.exceptions.DataNotFoundException;
import com.wYne.automation.exceptions.WyneException;
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

		logger.info("In the BeforeClass");
	}




}
