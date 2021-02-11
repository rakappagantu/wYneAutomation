package com.wYne.automation.tests;

import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.tests.base.BaseTest;
import org.openqa.selenium.Cookie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;

public class Cookies extends BaseTest {
    @BeforeClass
    public void beforeClass()
    {


    }

    @Test
    public String getallCookies() {
        driver.get(ConfigManager.getBundle().getString("env.baseurl"));
        waiting.waitForPageToLoad();
        waiting.waitForAjaxToComplete();
        Set<Cookie> cookieSet = driver.manage().getCookies();
        Iterator it = cookieSet.iterator();
        for(Cookie aCookie :cookieSet )
        {
            if(aCookie.getName().equalsIgnoreCase("csrftoken"))
            {
                System.out.println("cookie value is "+aCookie.getValue());
                return aCookie.getValue();
            }
        }

//        while (it.hasNext()) {
//            if(it.hasNext().eq)
//        }

        return null;
    }
}
