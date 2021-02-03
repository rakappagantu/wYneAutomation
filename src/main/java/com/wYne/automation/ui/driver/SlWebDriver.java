package com.wYne.automation.ui.driver;

import com.wYne.automation.ui.elements.SlWebElement;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.internal.*;

public interface SlWebDriver extends WebDriver, JavascriptExecutor,
        FindsById, FindsByClassName, FindsByLinkText, FindsByName,
        FindsByCssSelector, FindsByTagName, FindsByXPath,
        HasInputDevices, HasCapabilities, TakesScreenshot {

    //WebElement findElement(By by);
    SlWebElement findElement(By by);
    SlWebElement findElementByXPath(String locator);
    SlWebElement findElementByCssSelector(String locator);

    //SlWebElement findElement(String locator);
}
