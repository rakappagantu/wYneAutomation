package com.wYne.automation.ui.elements;


import com.wYne.automation.ui.elements.impl.SlWebElementImpl;
import com.wYne.automation.ui.internal.ImplementedBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

/**
 * wraps a web element interface with extra functionality. Anything added here will be added to all descendants.
 */
@ImplementedBy(SlWebElementImpl.class)
public interface SlWebElement extends WebElement, WrapsElement, Locatable {
    /**
     * Returns true when the inner element is ready to be used.
     *
     * @return boolean true for an initialized WebElement, or false if we were somehow passed a null WebElement.
     */
    boolean elementWired();

    //void domClick();

    void waitForVisible(long... var1);

    void waitForNotVisible(long... var1);

    void waitForDisabled(long... var1);

    void waitForEnabled(long... var1);

   void waitForPresent(long... var1);

   void waitForNotPresent(long... var1);

    void waitForText(String var1, long... var2);

    void waitForNotText(String var1, long... var2);

   /* void waitForValue(String var1, long... var2);

    void waitForNotValue(String var1, long... var2);

    void waitForSelected(long... var1);

    void waitForNotSelected(long... var1);*/

    void waitForAttritube(String var1, String var2, long... var3);

    /*void waitForNotAttritube(String var1, String var2, long... var3);

    void waitForCssClass(String var1, long... var2);

    void waitForNotCssClass(String var1, long... var2);

    void waitForCssStyle(String var1, String var2, long... var3);

    void waitForNotCssStyle(String var1, String var2, long... var3);*/



    //boolean verifyVisible(String... var1);

    //boolean verifyNotVisible(String... var1);

    //boolean verifyEnabled(String... var1);


    //boolean verifyText(String var1, String... var2);

    //boolean verifyNotText(String var1, String... var2);

   // boolean verifyPresent(String... var1);
   // boolean verifyNotPresent(String... var1);

    //boolean isPresent();

}
