package com.wYne.automation.tests;

import com.wYne.automation.pages.Loginpage;
import com.wYne.automation.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginFunctionalityTest extends BaseTest {
    @BeforeClass()
    public void beforeClass() throws Exception {

    }

    @Test
    public void verifyLogin() {
        Loginpage loginpage = new Loginpage();
        Assert.assertTrue(browserUtils.isElementExists(loginpage.getLogin_button()));

    }
}



