package com.wYne.automation.pages;


import com.wYne.automation.AbstractNewDriverBasePage;

public abstract class AbstractDriverBasePage extends AbstractNewDriverBasePage {

    /*public SlWebElement getPopupBottomButtonByText(String text) throws SLException {

        //waiting.shortWait();
        List<WebElement> listOfButtons = driver.findElements(By.cssSelector(".ui-dialog-buttonset button, .btns button"));
        int count = listOfButtons.size();

        log.info("count of popup buttons = " + count);


        for (WebElement elem : listOfButtons) {
            log.info("Button found with text : [ " + elem.getText() + "]");
            if (elem.getText().equalsIgnoreCase(text) && elem.isEnabled()) {
                return new SlWebElementImpl(elem);
            }
        }
        throw new ElementNotFoundException("Button", "Text", text);
        //return null;
    }

    public boolean isPopupBottomButtonByTextDisplayed(String text) throws SLException {

        List<WebElement> listOfButtons = driver.findElements(By.cssSelector(".ui-dialog-buttonset button"));
        int count = listOfButtons.size();

        log.info("count of popup buttons = " + count);


        for (WebElement elem : listOfButtons) {
            log.info("Button found with text : [ " + elem.getText() + "]");
            if (elem.getText().equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }



    public SlWebElement getPopupBottomButtonByTextInActivePopup(String text) throws SLException {

    	SlWebElement activeElement=driver.findElement(By.xpath("//div [contains(@class,'slc-notify-front')]"));
    	List<WebElement> listOfButtons = activeElement.findElements(By.cssSelector(".ui-dialog-buttonset button"));
            for (WebElement elem : listOfButtons) {

                if (elem.getText().equalsIgnoreCase(text)) {
                    return new SlWebElementImpl(elem);

                }
            }
        //return null;
        throw new ElementNotFoundException("Button", "Text", text);
    }*/

}
