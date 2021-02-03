package com.wYne.automation.ui.elements.impl;

import com.wYne.automation.ui.elements.WyneLogger;
import com.wYne.automation.ui.elements.TextInput;
import com.wYne.automation.ui.elements.WyneLogger;
import org.openqa.selenium.WebElement;

/**
 * TextInput  wrapper.
 */
public class TextInputImpl extends SlWebElementImpl implements TextInput {
    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public TextInputImpl(WebElement element) {
        super(element);
    }

    @Override
    public void clear() {
    	WyneLogger.LOGGER.debug("Clearing the contents of element :[" + getWrappedElement().toString() + "]");
        getWrappedElement().clear();
    }

    //@Override
    public void set(String text) {
    	WebElement element = getWrappedElement();
    	WyneLogger.LOGGER.debug("Setting the data :[" + text + "] in element :[" + getWrappedElement().toString() + "]");
    	//SnapLogger.LOGGER.debug("Class:" + this + ", Method:" + "set , Value:" + text);
        //element.clear();
        element.sendKeys(text);
    }

    /**
     * Gets the value of an input field.
     * @return String with the value of the field.
     */
    @Override
    public String getText() {
        WyneLogger.LOGGER.debug("Get the text present in element :[" + getWrappedElement().toString() + "]");
        return getWrappedElement().getAttribute("value");
    }
}
