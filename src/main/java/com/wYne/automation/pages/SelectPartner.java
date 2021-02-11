package com.wYne.automation.pages;

import com.wYne.automation.dataTypes.SelectWynePartner;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class SelectPartner extends AbstractDriverBasePage {
    public List<SlWebElement> getListOfPartners() {
        return listOfPartners;
    }

    @FindBy(xpath = "//p[text()='Select the partner website ']/following-sibling::div/div")
    private List<SlWebElement> listOfPartners;


    public Inventory selectWynePartners(SelectWynePartner wynePartner) {
        Actions act = new Actions(driver);
        SlWebElement element = null;
        try {
            waiting.waitForPageToLoad();
            waiting.waitTillSpinnerDisappears();
            waiting.waitForElementVisible(driver.findElement(By.xpath("//p[text()='Select the partner website ']/following-sibling::div/div/a[text()='" + wynePartner.getValue() + "']")));
            element = driver.findElement(By.xpath("//p[text()='Select the partner website ']/following-sibling::div/div/a[text()='" + wynePartner.getValue() + "']"));
            act.moveToElement(element).click().build().perform();
        } catch (Exception e) {
            Assert.fail("Excetion occured in " + this.getClass(), e);
        }
        return new Inventory();
    }
}

