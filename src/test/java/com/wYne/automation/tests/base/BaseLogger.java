package com.wYne.automation.tests.base;

import com.wYne.automation.general.CommonConstants;
import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.exceptions.DataNotFoundException;
import com.wYne.automation.exceptions.WyneException;
import com.wYne.automation.core.ResultLogger;
import com.wYne.automation.core.SlInitSettingsFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

//@Listeners({ Listener.class })
public class BaseLogger extends com.wYne.automation.tests.base.WebDriverBaseTest {





	@BeforeClass(alwaysRun = true)
	public void baseLoggerBeforeClass() throws Exception {

		logger.info("In the BeforeClass");
	}




}
