package com.wYne.automation.core;

import com.wYne.automation.exceptions.PageNotFoundException;
import com.wYne.automation.ui.BrowserUtils;
import com.wYne.automation.ui.core.Waiting;
import com.wYne.automation.ui.driver.SlWebDriver;
import com.wYne.automation.ui.elements.SlWebElement;
import com.wYne.automation.ui.internal.ElementFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;

public abstract class AbstractNewDriverBasePage {

    protected BrowserUtils utils;
    protected SlWebDriver driver;
    protected Waiting waiting;
    //protected PropertyUtil pageProps;
    protected String uiBranch;
    //protected SlWebElement webChildElement;

    protected static Logger log = Logger.getLogger(AbstractNewDriverBasePage.class);

    public AbstractNewDriverBasePage() {
        //pageProps = ConfigManager.getBundle();
        driver = SlInitSettingsFactory.getDriver();
        ElementFactory.initElements(driver, this);
        utils = new BrowserUtils(driver);
        waiting = new Waiting(driver);
    }

    protected boolean isPageDisplayed(SlWebElement webChildElement) throws PageNotFoundException {
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        try{
            waiting.waitForElement(webChildElement);
        }catch (NoSuchElementException e){
            throw new PageNotFoundException(webChildElement.toString() + " not found in the page " + this.getClass().getName());
        }
        return true;
    }

    public void waitForAjaxToComplete(){
        waiting.waitForAjaxToComplete();
    }

    public void waitForPageToLoad(){ waiting.waitForPageToLoad();}


}
