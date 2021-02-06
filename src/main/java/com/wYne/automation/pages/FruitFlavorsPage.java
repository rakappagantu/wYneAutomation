package com.wYne.automation.pages;

import com.wYne.automation.ui.core.Waiting;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.support.FindBy;
import com.wYne.automation.dataTypes.*;
import org.testng.Assert;

import java.util.List;

public class FruitFlavorsPage extends AbstractDriverBasePage {


    public List<SlWebElement> getListOfFruits() {
        return listOfFruits;
    }
    @FindBy(xpath = "//div[@class='fruitcontainer row']/div")
    private List<SlWebElement> listOfFruits;

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

    public HerbsAndSpicespage moveToHerbsAndSpicePage() {
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

        return new HerbsAndSpicespage();
    }


}
