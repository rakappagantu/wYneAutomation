package com.wYne.automation.ui.elements;


import com.wYne.automation.ui.elements.impl.TextInputImpl;
import com.wYne.automation.ui.internal.ImplementedBy;
import com.wYne.automation.ui.internal.ImplementedBy;

/**
 * Text field functionality.
 */
@ImplementedBy(TextInputImpl.class)
public interface TextInput extends SlWebElement {
    /**
     * @param text The text to type into the field.
     */
    void set(String text);
}
