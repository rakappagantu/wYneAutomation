package com.wYne.automation.pages;

import com.wYne.automation.dataTypes.FruitSelection;
import com.wYne.automation.ui.core.Waiting;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class StartQuizPage extends AbstractDriverBasePage{

    public  StartQuizPage(){

    }


    public SlWebElement getStartButton() {
        return startButton;
    }

    //button[text()=' Next ']

    @FindBy(xpath = "//button[@type='button']")
    private SlWebElement startButton;



    public List<SlWebElement> getCountOfSelectedImagesInFruitsPage() {
        return countOfSelectedImagesInFruitsPage;
    }

    @FindBy(xpath = "//button[@type='button']")
    List<SlWebElement> countOfSelectedImagesInFruitsPage;

    //input[@class='checkbox-custom ng-valid ng-dirty ng-touched']

    public FruitFlavorsPage moveToQuiz() {
        Actions act = new Actions(driver);
        try{
            if (utils.isElementClickable(this.getStartButton())) {
                act.moveToElement(this.getStartButton()).click().build().perform();
                Waiting.staticWait(2000);
            }
        }
        catch (Exception e){
            Assert.fail("Excetion occured in "+this.getClass(),e);
        }

        return new FruitFlavorsPage();
    }

    public void selectFruit(FruitSelection flavors)
    {
        Actions act = new Actions(driver);
        SlWebElement element = null;
        try{
            waiting.waitForElementVisible(driver.findElement(By.xpath("//img[@alt='"+flavors.getValue().toLowerCase()+"']")));
        element =driver.findElement(By.xpath("//img[@alt='"+flavors.getValue().toLowerCase()+"']"));
            act.moveToElement(element).click().build().perform();
    }
    catch (Exception e)
    {
        Assert.fail("Excetion occured in "+this.getClass(),e);
    }
    }
    }

