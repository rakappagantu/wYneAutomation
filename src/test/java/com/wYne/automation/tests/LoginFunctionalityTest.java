package com.wYne.automation.tests;

import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.dataTypes.SelectWynePartner;
import com.wYne.automation.pages.Inventory;
import com.wYne.automation.pages.Loginpage;
import com.wYne.automation.pages.SelectPartner;
import com.wYne.automation.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginFunctionalityTest extends BaseTest {

    @BeforeClass()
    public void beforeClass() throws Exception {

    }

    @Test
    public void verifyLogin() throws IOException {
        Loginpage loginpage = new Loginpage();
        Inventory inventory = new Inventory();
        waiting.waitForAjaxToComplete();
        waiting.waitForElementVisible(loginpage.getLogin_password_textbox());
        SelectPartner selectPartner = loginpage.moveToSelectPartnerPage();
        inventory = selectPartner.selectWynePartners(SelectWynePartner.ITALICAPALOALTO);
        waiting.waitForPageToLoad();
        waiting.waitForAjaxToComplete();
        waiting.waitTillSpinnerDisappears();
        Assert.assertTrue(browserUtils.isElementClickable(inventory.getLogOutButton()));

    }
}



