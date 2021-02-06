package com.wYne.automation.pages;

import com.wYne.automation.dataTypes.FlowerSelection;
import com.wYne.automation.ui.core.Waiting;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class FlowersPage extends AbstractDriverBasePage{
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

    public void selectFlowers(FlowerSelection flowerSelection)
    {
        SlWebElement element = null;
        try{
            waiting.waitForElementVisible(driver.findElement(By.xpath("//img[@alt='"+flowerSelection.getValue().toLowerCase()+"']")));
            element =driver.findElement(By.xpath("//img[@alt='"+flowerSelection.getValue().toLowerCase()+"']"));
            element.click();
        }
        catch (Exception e)
        {
            Assert.fail("Excetion occured in "+this.getClass(),e);
        }
    }
    public NewFlowerspage moveToNewFlowerspage() {
        try {
            if (utils.isElementClickable(this.getNextButton())) {
                this.getNextButton().click();
                Waiting.staticWait(500);
            } else {
                System.out.println("Next button is not enabled");
            }


        } catch (Exception e) {
            Assert.fail("Excetion occured in "+this.getClass(),e);
        }

        return new NewFlowerspage();
    }
}
