package com.wYne.automation.ui.elements;


import com.wYne.automation.ui.elements.impl.LabelImpl;
import com.wYne.automation.ui.internal.ImplementedBy;


/**
 * Html form label.
 */
@ImplementedBy(LabelImpl.class)
public interface Label extends SlWebElement {
    /**
     * Gets the for attribute on the label.
     *
     * @return string containing value of the for attribute, null if empty.
     */
    String getFor();
}
