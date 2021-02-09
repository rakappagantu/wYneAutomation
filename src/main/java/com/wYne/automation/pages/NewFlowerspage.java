package com.wYne.automation.pages;

import com.wYne.automation.dataTypes.FlowerSelection;
import com.wYne.automation.dataTypes.NewFlowerSelection;
import com.wYne.automation.ui.core.Waiting;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class NewFlowerspage extends AbstractDriverBasePage {
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

    public void selectFlowers(NewFlowerSelection newflowerSelection) {
        Actions act = new Actions(driver);
        SlWebElement element = null;
        try {
            waiting.waitForPageToLoad();
            waiting.waitTillSpinnerDisappears();
            waiting.waitForElementVisible(driver.findElement(By.xpath("//img[@alt='" + newflowerSelection.getValue().toLowerCase() + "']")));
            element = driver.findElement(By.xpath("//img[@alt='" + newflowerSelection.getValue().toLowerCase() + "']"));
            act.moveToElement(element).click().build().perform();
        } catch (Exception e) {
            Assert.fail("Excetion occured in " + this.getClass(), e);

        }
    }

    public ScentPage moveToScentpage() {
        try {
            if (utils.isElementClickable(this.getNextButton())) {
                this.getNextButton().click();
                Waiting.staticWait(2000);
            } else {
                System.out.println("Next button is not enabled");
            }


        } catch (Exception e) {
            Assert.fail("Excetion occured in "+this.getClass(),e);
        }

        return new ScentPage();
    }
}
