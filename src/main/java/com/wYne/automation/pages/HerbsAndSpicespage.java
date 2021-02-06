package com.wYne.automation.pages;

import com.wYne.automation.dataTypes.HerbsSelection;
import com.wYne.automation.ui.core.Waiting;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HerbsAndSpicespage extends AbstractDriverBasePage {

    public SlWebElement getNextButton() {
        return nextButton;
    }

    @FindBy(xpath = "//button[text()=' Next ']")
    private SlWebElement nextButton;
    public SlWebElement getBackButon() {
        return backButon;
    }

    @FindBy(xpath = "//span[text()='Back']")
    private SlWebElement backButon;

    public void selectHerbs(HerbsSelection herbsSelection)
    {
        Actions act = new Actions(driver);
        SlWebElement element = null;
        try{
            waiting.waitForElementVisible(driver.findElement(By.xpath("//label//img[@alt='"+herbsSelection.getValue().toLowerCase()+"']")));
            element =driver.findElement(By.xpath("//label//img[@alt='"+herbsSelection.getValue().toLowerCase()+"']"));
            // waiting.waitForElementVisible(element);
            act.moveToElement(element).click().build().perform();
        }
        catch (Exception e)
        {
            Assert.fail("Excetion occured in "+this.getClass(),e);
        }
    }
    public FlowersPage moveToFlowerSelectionPage() {
        Actions act = new Actions(driver);
        try {

            if (utils.isElementClickable(this.getNextButton())) {
                this.getNextButton().click();
                Waiting.staticWait(500);
            } else {
                System.out.println("Next button is not enabled in HerbsAndSpicespage");
            }


        } catch (Exception e) {
            Assert.fail("Excetion occured in "+this.getClass(),e);
        }

        return new FlowersPage();
    }
}
