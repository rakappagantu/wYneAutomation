package com.wYne.automation.core;



import com.wYne.automation.ui.driver.SlWebDriver;


import java.util.HashMap;
import java.util.Map;

public class SlInitSettingsFactory {
    protected enum ThreadKeys{
        DRIVER;
    }



    public static final ThreadLocal<Map<ThreadKeys, Object>> threadLocal = new ThreadLocal();

    public static SlWebDriver getDriver() {
        return (SlWebDriver) threadLocal.get().get(ThreadKeys.DRIVER);
    }

    private static Map<ThreadKeys,Object> setMap(SlWebDriver driver){
        Map<ThreadKeys,Object> map = new HashMap<ThreadKeys, Object>();
        map.put(ThreadKeys.DRIVER,driver);
        return map;

}
    public static void setDriver(SlWebDriver driver) {
        threadLocal.set(setMap(driver));
    }





}