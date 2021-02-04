package com.wYne.automation.core;


import com.wYne.automation.ui.driver.SlWebDriver;


import java.util.HashMap;
import java.util.Map;

public class SlInitSettingsFactory {
    protected enum ThreadKeys{
        DRIVER
    }



    public static final ThreadLocal<Map<ThreadKeys, Object>> threadLocal = new ThreadLocal<Map<ThreadKeys, Object>>();

    public static SlWebDriver getDriver() {
        return (SlWebDriver) threadLocal.get().get(ThreadKeys.DRIVER);
    }

}
