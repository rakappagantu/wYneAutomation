package com.wYne.automation.tests;

import com.wYne.automation.dataTypes.*;
import com.wYne.automation.pages.*;
import com.wYne.automation.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SelectWyneTest extends BaseTest {
    protected StartQuizPage startQuizPage;
    protected FruitFlavorsPage fruitFlavorsPage;
    protected HerbsAndSpicespage herbsAndSpicespage;
    protected FlowersPage flowersPage;
    protected NewFlowerspage newflowersPage;
    protected ScentPage scentPage;
    @BeforeMethod()
    public void beforeMethod() throws Exception {
        startQuizPage = new StartQuizPage();
        waiting.waitTillSpinnerDisappears();
        browserUtils.isElementClickable(startQuizPage.getStartButton());
        fruitFlavorsPage = new FruitFlavorsPage();
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
        herbsAndSpicespage = new HerbsAndSpicespage();
        FlowersPage flowersPage = herbsAndSpicespage.moveToFlowerSelectionPage();
        waiting.waitForElementVisible(flowersPage.getBackButon());
        Assert.assertTrue(browserUtils.isElementExists(flowersPage.getBackButon()));
        waiting.waitForAjaxToComplete();
        flowersPage.selectFlowers(FlowerSelection.JASMINE);
        Assert.assertTrue(browserUtils.isElementExists(flowersPage.getNextButton()));
        flowersPage = new FlowersPage();
        NewFlowerspage newFlowerspage = flowersPage.moveToNewFlowerspage();
        waiting.waitForElementVisible(newFlowerspage.getBackButon());
        Assert.assertTrue(browserUtils.isElementExists(flowersPage.getBackButon()));
        waiting.waitForAjaxToComplete();
        newFlowerspage.selectFlowers(NewFlowerSelection.HIBISCUS);
        Assert.assertTrue(browserUtils.isElementExists(newFlowerspage.getNextButton()));
        newflowersPage = new NewFlowerspage();
        ScentPage scentPage = newflowersPage.moveToScentpage();
        waiting.waitForElementVisible(scentPage.getBackButon());
        Assert.assertTrue(browserUtils.isElementExists(newflowersPage.getBackButon()));
        waiting.waitForAjaxToComplete();
        scentPage.selectFlowers(ScentSelection.FRESHLYBAKED);
        Assert.assertTrue(browserUtils.isElementExists(scentPage.getNextButton()));
    }

    @Test(alwaysRun = true)
    public void verifySelectionOfNewFlowersRecommendations() {
        scentPage = new ScentPage();
        SelectWyne  selectWyne = scentPage.moveToSelectWynepage();
        waiting.waitForAjaxToComplete();
        selectWyne.selectWyne(SelectTypeOfWine.SPARKLING);
        Assert.assertTrue(browserUtils.isElementExists(selectWyne.getNextButton()));

    }
}
