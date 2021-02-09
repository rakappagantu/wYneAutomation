package com.wYne.automation.pages;

import com.wYne.automation.dataTypes.SelectTypeOfWine;
import com.wYne.automation.ui.core.Waiting;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class SelectWyne extends AbstractDriverBasePage {
    public SlWebElement getNextButton() {
        return nextButton;
    }

    @FindBy(xpath = "//button[text()=' Next ']")
    private SlWebElement nextButton;
    public void selectWyne(SelectTypeOfWine selectWyne) {
        Actions act = new Actions(driver);
        SlWebElement element = null;
        try {
            waiting.waitForElementVisible(driver.findElement(By.xpath("//span[text()='sparkling']")));
            element = driver.findElement(By.xpath("//span[text()='" + selectWyne.getValue().toLowerCase() + "']"));
            act.moveToElement(element).click().build().perform();
        } catch (Exception e) {
            Assert.fail("Excetion occured in " + this.getClass(), e);

        }
    }

    public RecommendationsPage moveToRecommendationsPage() {
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

        return new RecommendationsPage();
    }
}
