package com.wYne.automation.ui.driver;

import com.wYne.automation.ui.elements.SlWebElement;
import com.wYne.automation.ui.elements.impl.SlWebElementImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;

import java.net.URL;
import java.util.List;

public class SlExtendedWebDriver extends RemoteWebDriver implements SlWebDriver {
    Log logger;

    public SlExtendedWebDriver(URL url, MutableCapabilities capabilities) {
        super(url, capabilities);
        this.logger = LogFactory.getLog(getClass());
    }

    public SlExtendedWebDriver(WebDriver driver) {
        this(((RemoteWebDriver) driver).getCommandExecutor(), ((RemoteWebDriver) driver).getCapabilities());
        if (getCapabilities().getBrowserName().toLowerCase().contains("internet")) {
            driver.close();
        }
    }

    public SlExtendedWebDriver(MutableCapabilities capabilities){
        super(capabilities);
    }


    public SlExtendedWebDriver(CommandExecutor cmdExecutor, Capabilities capabilities) {
        super(cmdExecutor, capabilities);
        this.logger = LogFactory.getLog(getClass());
    }


    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        if (((Boolean) getCapabilities().getCapability("takesScreenshot")).booleanValue()) {
            return target.convertFromBase64Png(execute("screenshot").getValue().toString());
        }
        return null;
    }

    public <T> T extractScreenShot(WebDriverException e, OutputType<T> target) {
        if (e.getCause() instanceof ScreenshotException) {
            return target.convertFromBase64Png(((ScreenshotException) e.getCause()).getBase64EncodedScreenshot());
        }
        return null;
    }


    public SlWebElement findElementByCssSelector(String using) {
        WebElement elem = super.findElementByCssSelector(using);
        return (SlWebElement) new SlWebElementImpl(elem);

        /*List<SlWebElementImpl> elements = findElementsBySizzleCss(using);
        if (elements.size() > 0) {
            return (SlWebElementImpl) elements.get(0);
        }
        return null;*/
    }

    public List<SlWebElementImpl> findElementsBySizzleCss(String using) {
        injectSizzleIfNeeded();
        return (List) executeScript(createSizzleSelectorExpression(using), new Object[0]);
    }

    public SlWebElement findElement(By by){
        WebElement elem = super.findElement(by);
        return (SlWebElement) new SlWebElementImpl(elem);
    }

    public SlWebElement findElementByXPath(String locator){
        WebElement elem = super.findElementByXPath(locator);
        return  new SlWebElementImpl(elem);
    }

    private String createSizzleSelectorExpression(String using) {
        return "return Sizzle(\"" + using + "\")";
    }

    private void injectSizzleIfNeeded() {
        if (!sizzleLoaded().booleanValue()) {
            injectSizzle();
        }
    }

    private Boolean sizzleLoaded() {
        try {
            return (Boolean) executeScript("return Sizzle()!=null", new Object[0]);
        } catch (WebDriverException e) {
            return Boolean.valueOf(false);
        }
    }

    private void injectSizzle() {
        executeScript(" var headID = document.getElementsByTagName(\"head\")[0];var newScript = document.createElement('script');newScript.type = 'text/javascript';newScript.src = 'https://raw.github.com/jquery/sizzle/master/sizzle.js';headID.appendChild(newScript);", new Object[0]);
    }

    public String takeScreenShot() {
        return (String) getScreenshotAs(OutputType.BASE64);
    }

    //public SlWebElement findElement(String locator) {
    //    return null;
    //}

    //public List<SlWebElement> findElements(String locator) {
    //    return null;
    //}
}
