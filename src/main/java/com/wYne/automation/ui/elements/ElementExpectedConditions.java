package com.wYne.automation.ui.elements;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class ElementExpectedConditions
{
    public static ExpectedCondition<WebElement, Boolean> elementVisible() {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return element.isDisplayed();
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementNotVisible() {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                try {
                    return !element.isDisplayed();
                } catch (NoSuchElementException e) {
                    // Returns true because the element is not present in DOM. The
                    // try block checks if the element is present but is invisible.
                    return true;
                } catch (StaleElementReferenceException e) {
                    // Returns true because stale element reference implies that element
                    // is no longer visible.
                    return true;
                }
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementEnabled() {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
               // element.setId("-1");
                return element.isEnabled();
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementDisabled() {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
               // element.setId("-1");
                return !element.isEnabled();
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementAttributeValueEq(final String attributeName, final String val) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return element.getAttribute(attributeName).equals(val);
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementAttributeValueNotEq(final String attributeName, final String val) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return !element.getAttribute(attributeName).equals(val);
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementCssPropertyValueEq(final String propertyName, final String val) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return element.getCssValue(propertyName).equals(val);
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementCssPropertyValueNotEq(final String propertyName, final String val) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return !element.getCssValue(propertyName).equals(val);
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementTextEq(final String val) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return element.getText().equals(val);
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementTextNotEq(final Object val) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return !element.getText().equals(val);
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementValueEq(final String val) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return element.getAttribute("value").equals(val);
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementValueNotEq(final String val) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return !element.getAttribute("value").equals(val);
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementSelected() {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return element.isSelected();
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementNotSelected() {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return !element.isSelected();
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementHasCssClass(final String className) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return element.getAttribute("class").contains(className);
            }
        };
    }
    
    public static ExpectedCondition<WebElement, Boolean> elementHasNotCssClass(final String className) {
        return new ExpectedCondition<WebElement, Boolean>() {
            public Boolean apply(final WebElement element) {
                return !element.getAttribute("class").contains(className);
            }
        };
    }
}
