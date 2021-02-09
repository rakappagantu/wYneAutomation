package com.wYne.automation.tests;

import com.wYne.automation.dataTypes.FlowerSelection;
import com.wYne.automation.dataTypes.FruitSelection;
import com.wYne.automation.dataTypes.HerbsSelection;
import com.wYne.automation.pages.FlowersPage;
import com.wYne.automation.pages.FruitFlavorsPage;
import com.wYne.automation.pages.HerbsAndSpicespage;
import com.wYne.automation.pages.StartQuizPage;
import com.wYne.automation.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FlowerSelectionTest extends BaseTest {

    protected StartQuizPage startQuizPage ;
    protected FruitFlavorsPage fruitFlavorsPage;
    protected HerbsAndSpicespage herbsAndSpicespage;
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() throws Exception{
        startQuizPage  = new StartQuizPage();
        waiting.waitTillSpinnerDisappears();
        browserUtils.isElementClickable(startQuizPage.getStartButton());
        fruitFlavorsPage  = new FruitFlavorsPage();
        FruitFlavorsPage fruitFlavorsPage = startQuizPage.moveToQuiz();
        waiting.waitForElementVisible(fruitFlavorsPage.getBackButon());
        Assert.assertTrue(browserUtils.isElementExists(fruitFlavorsPage.getBackButon()));
        startQuizPage.selectFruit(FruitSelection.APPLE);
        startQuizPage.selectFruit(FruitSelection.APRICOT);
        startQuizPage.selectFruit(FruitSelection.BLACKPLUM);
        startQuizPage.selectFruit(FruitSelection.CRANBERRY);
        startQuizPage.selectFruit(FruitSelection.BLACKBERRY);
        startQuizPage.selectFruit(FruitSelection.CANTALOUPE);
        waiting.waitForElementEnabled(fruitFlavorsPage.getNextButton());
        Assert.assertTrue(browserUtils.isElementClickable(fruitFlavorsPage.getNextButton()));
        HerbsAndSpicespage herbsAndSpicespage = fruitFlavorsPage.moveToHerbsAndSpicePage();
        Assert.assertTrue(browserUtils.isElementClickable(herbsAndSpicespage.getBackButon()));
        waiting.waitForAjaxToComplete();
        herbsAndSpicespage.selectHerbs(HerbsSelection.DIL);
        herbsAndSpicespage.selectHerbs(HerbsSelection.HONEY);
        Assert.assertTrue(browserUtils.isElementClickable(herbsAndSpicespage.getNextButton()));
    }

    @Test(alwaysRun = true,groups = { "AnonymousUserRecommendations" })
    public void verifySelectionOfFlowersRecommendations() {
        herbsAndSpicespage  = new HerbsAndSpicespage();
        FlowersPage flowersPage = herbsAndSpicespage.moveToFlowerSelectionPage();
        waiting.waitForElementVisible(flowersPage.getBackButon());
        Assert.assertTrue(browserUtils.isElementExists(flowersPage.getBackButon()));
        waiting.waitTillSpinnerDisappears();
        waiting.waitForAjaxToComplete();
        waiting.waitForPageToLoad();
        flowersPage.selectFlowers(FlowerSelection.Honeysuckle);
        Assert.assertTrue(browserUtils.isElementExists(flowersPage.getNextButton()));

    }
}
