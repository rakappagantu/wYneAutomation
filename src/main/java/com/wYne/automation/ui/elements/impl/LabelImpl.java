package com.wYne.automation.ui.elements.impl;

import com.wYne.automation.ui.elements.Label;
import org.openqa.selenium.WebElement;

/**
 * Wraps a label on a html form with some behavior.
 */
public class LabelImpl extends SlWebElementImpl implements Label {

    /**
     * Creates a Element for a given WebElement.
     *
     * @param element element to wrap up
     */
    public LabelImpl(WebElement element) {
        super(element);
    }

    public String getFor() {
        return null;
    }
}
