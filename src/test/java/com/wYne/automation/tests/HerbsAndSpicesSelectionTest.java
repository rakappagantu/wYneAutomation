package com.wYne.automation.tests;

import com.wYne.automation.dataTypes.FruitSelection;
import com.wYne.automation.dataTypes.HerbsSelection;
import com.wYne.automation.pages.FruitFlavorsPage;
import com.wYne.automation.pages.*;
import com.wYne.automation.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HerbsAndSpicesSelectionTest extends BaseTest {
    protected StartQuizPage startQuizPage ;
    protected FruitFlavorsPage fruitFlavorsPage;
    @BeforeMethod()
    public void beforeMethod(){
        startQuizPage  = new StartQuizPage();
        waiting.waitTillSpinnerDisappears();
        browserUtils.isElementClickable(startQuizPage.getStartButton());
        FruitFlavorsPage fruitFlavorsPage = startQuizPage.moveToQuiz();
        waiting.waitForElementVisible(fruitFlavorsPage.getBackButon());
        Assert.assertTrue(browserUtils.isElementExists(fruitFlavorsPage.getBackButon()));
        startQuizPage.selectFruit(FruitSelection.APPLE);
        startQuizPage.selectFruit(FruitSelection.APRICOT);
        startQuizPage.selectFruit(FruitSelection.BLACKPLUM);
        startQuizPage.selectFruit(FruitSelection.CRANBERRY);
        startQuizPage.selectFruit(FruitSelection.BLACKBERRY);
        startQuizPage.selectFruit(FruitSelection.CANTALOUPE);
        Assert.assertTrue(browserUtils.isElementExists(fruitFlavorsPage.getNextButton()));
    }


    @Test
    public void verifySelectionOfHerbsRecommendations() {
        fruitFlavorsPage  = new FruitFlavorsPage();
        HerbsAndSpicespage herbsAndSpicespage = fruitFlavorsPage.moveToHerbsAndSpicePage();
        waiting.waitForElementVisible(herbsAndSpicespage.getBackButon());
        Assert.assertTrue(browserUtils.isElementExists(herbsAndSpicespage.getBackButon()));
        herbsAndSpicespage.selectHerbs(HerbsSelection.ANISE);
        herbsAndSpicespage.selectHerbs(HerbsSelection.HONEY);
        Assert.assertTrue(browserUtils.isElementExists(herbsAndSpicespage.getNextButton()));

    }
}
