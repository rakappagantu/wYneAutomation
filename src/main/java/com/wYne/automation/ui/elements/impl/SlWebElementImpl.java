package com.wYne.automation.ui.elements.impl;

import com.wYne.automation.ui.elements.ElementExpectedConditions;
import com.wYne.automation.ui.elements.ElementWait;
import com.wYne.automation.ui.elements.SlWebElement;
import com.wYne.automation.ui.elements.SnapLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Arrays;
import java.util.List;

/**
 * An implementation of the Element interface. Delegates its work to an underlying WebElement instance for
 * custom functionality.
 */

public class SlWebElementImpl extends RemoteWebElement implements SlWebElement {

    private final WebElement element;

    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public SlWebElementImpl(final WebElement element) {
        this.element = element;
    }

    @Override
    public void click() {
    	SnapLogger.LOGGER.debug("Clicking on the element:[" + element.toString() + "]");
        element.click();

    }

    /*public void domClick(){
        Actions actions = new Actions(getWrappedDriver());
        actions.click(element).build().perform();
    }*/

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        //SnapLogger.LOGGER.debug(keysToSend.length);
        //SnapLogger.LOGGER.debug(keysToSend[0].toString());
        //SnapLogger.LOGGER.debug(new CharSequence("ab").length());
        SnapLogger.LOGGER.debug("SendKeys:[" + Arrays.toString((CharSequence[]) keysToSend) + "] to the element:[" + element.toString() + "]");
    	element.sendKeys(keysToSend);
    }

    //@Override
    public Point getLocation() {
    	SnapLogger.LOGGER.debug("Get the location of the element:[" + element.toString() + "]");
        return element.getLocation();
    }

    //@Override
    public void submit() {
    	SnapLogger.LOGGER.debug("Submit the form/page for the element:[" + element.toString() + "]");
        element.submit();
    }

    @Override
    public String getAttribute(String name) {
    	SnapLogger.LOGGER.debug("Getting the attribute :[" + name + "] for the element:[" + element.toString() + "]");
        return element.getAttribute(name);
    }

    @Override
    public String getCssValue(String propertyName) {
    	SnapLogger.LOGGER.debug("Getting the CSS for property:[" + propertyName + "] for the element:[" + element.toString() + "]");
        return element.getCssValue(propertyName);
    }

    @Override
    public Dimension getSize() {
    	SnapLogger.LOGGER.debug("Get the dimension for the element:[" + element.toString() + "]");
        return element.getSize();
    }

    @Override
    public List<WebElement> findElements(By by) {
    	SnapLogger.LOGGER.debug("findElements by:[" + by + "] for the element:[" + element.toString() + "]");
    	return element.findElements(by);
    }

    @Override
    public String getText() {
    	SnapLogger.LOGGER.debug("Getting the text for the element:[" + element.toString() + "]");
    	return element.getText();
    }

    @Override
    public String getTagName() {
    	SnapLogger.LOGGER.debug("Getting the tag for the element:[" + element.toString() + "]");
        return element.getTagName();
    }

    @Override
    public boolean isSelected() {
    	SnapLogger.LOGGER.debug("isSelected for the element:[" + element.toString() + "]");
        return element.isSelected();
    }

    @Override
    public WebElement findElement(By by) {
    	SnapLogger.LOGGER.debug("find element by :[" + by + "] for the element:[" + element.toString() + "]");
    	return element.findElement(by);
    }

    @Override
    public boolean isEnabled() {
    //	EMMLog.verbose("isEnabled for the element:["+ element.toString() +"]");
        return element.isEnabled();
    }

    @Override
    public boolean isDisplayed() {
    	SnapLogger.LOGGER.debug("isDisplayed for the element:[" + element.toString() + "]");
        return element.isDisplayed();
    }


    @Override
    public void clear() {
        SnapLogger.LOGGER.debug("Clearing the contents of element :[" + element.toString() + "]");
        element.clear();
    }

    public void set(String text) {
        WebElement element = getWrappedElement();
        SnapLogger.LOGGER.debug("Setting the data :[" + text + "] in element :[" + getWrappedElement().toString() + "]");
        //SnapLogger.LOGGER.debug("Class:" + this + ", Method:" + "set , Value:" + text);
        //element.clear();
        element.sendKeys(text);
    }


    @Override
    public WebElement getWrappedElement() {
    	return element;
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) element).getCoordinates();
    }

    @Override
    public boolean elementWired() {
        return (element != null);
    }


    public void waitForVisible(long... timeout) {
        new ElementWait(this, timeout).
                ignore(WebDriverException.class, NoSuchElementException.class, StaleElementReferenceException.class).
                until(ElementExpectedConditions.elementVisible());
    }

    public void waitForNotVisible(long... timeout) {
        new ElementWait(this, timeout).
                ignore(WebDriverException.class, NoSuchElementException.class, StaleElementReferenceException.class).
                until(ElementExpectedConditions.elementNotVisible());
        //new ElementWait(this,timeout).ignoring(WebDriverException.class).until((Predicate<WebElement>) ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
    }

    public void waitForDisabled(long... timeout) {
        new ElementWait(this, timeout).
                ignore(WebDriverException.class, NoSuchElementException.class, StaleElementReferenceException.class).
                until(ElementExpectedConditions.elementDisabled());
        //new ElementWait(this,timeout).ignoring(WebDriverException.class).until((Predicate<WebElement>) ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element)));
    }

    public void waitForEnabled(long... timeout) {
        new ElementWait(this, timeout).
                ignore(WebDriverException.class, NoSuchElementException.class, StaleElementReferenceException.class).
                until(ElementExpectedConditions.elementEnabled());
        //new ElementWait(this,timeout).ignoring(WebDriverException.class).until((Predicate<WebElement>) ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForPresent(long... timeout) {
        waitForVisible(timeout);
    }

    public void waitForNotPresent(long... timeout) {
        waitForNotVisible(timeout);
    }

    public void waitForText(String text, long... timeout) {
        //new ElementWait(this,timeout).ignoring(WebDriverException.class).until((Predicate<WebElement>) ExpectedConditions.textToBePresentInElement(element,text));
        new ElementWait(this, timeout).
                ignore(WebDriverException.class, NoSuchElementException.class, StaleElementReferenceException.class).
                until(ElementExpectedConditions.elementTextEq(text));
    }

    public void waitForNotText(String text, long... timeout) {
        new ElementWait(this,timeout).
                ignore(WebDriverException.class, NoSuchElementException.class, StaleElementReferenceException.class).
                until(ElementExpectedConditions.elementTextNotEq(text));
    }

    public void waitForAttritube(String var1, String var2, long... timeout) {

    }


    //public boolean verifyPresent(String... var1) {
    //    return false;
    //}
    //public boolean verifyNotPresent(String... var1) {
    //    return false;
    //}

    //public boolean isPresent() {
     //   return false;//return BrowserUtils.isElementExists(this);
    //}

    @Override
    public String toString(){
    	return getWrappedElement().toString();
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }
}
