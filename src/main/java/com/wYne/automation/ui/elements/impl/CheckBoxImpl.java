package com.wYne.automation.ui.elements.impl;


import com.slqa.ui.elements.CheckBox;
import com.slqa.ui.elements.SnapLogger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;


/**
 * Wrapper class like Select that wraps basic checkbox functionality.
 */
public class CheckBoxImpl extends SlWebElementImpl implements CheckBox {

    /**
     * Wraps a WebElement with checkbox functionality.
     *
     * @param element to wrap up
     */
    public CheckBoxImpl(WebElement element) {
        super(element);
        //SnapLogger.LOGGER.debug("Checkbox Class:" + this.toString());
    }

    public void toggle() {
    	SnapLogger.LOGGER.debug("toggle the checkbox element :[" + getWrappedElement().toString() + "]");
        getWrappedElement().click();
    }

    public void check() {
    	SnapLogger.LOGGER.debug("Check the checkbox element :[" + getWrappedElement().toString() + "]");
    	if (!isChecked()) {
            toggle();
        }
    }

    public void uncheck() {
    	SnapLogger.LOGGER.debug("UnCheck the checkbox element :[" + getWrappedElement().toString() + "]");
        if (isChecked()) {
            toggle();
        }
    }

    public boolean isChecked() {
    	SnapLogger.LOGGER.debug("isChecked the checkbox element :[" + getWrappedElement().toString() + "]");
    	//SnapLogger.LOGGER.debug(getWrappedElement().toString());
        return getWrappedElement().isSelected();
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }
}
