package com.wYne.automation.tests;

import com.wYne.automation.dataTypes.*;
import com.wYne.automation.pages.*;
import com.wYne.automation.tests.base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RecommendationsAndRatingPageTest extends BaseTest {

    protected StartQuizPage startQuizPage;
    protected FruitFlavorsPage fruitFlavorsPage;
    protected HerbsAndSpicespage herbsAndSpicespage;
    protected FlowersPage flowersPage;
    protected NewFlowerspage newflowersPage;
    protected ScentPage scentPage;
    protected SelectWyne selectWyne;
    @BeforeMethod(alwaysRun = true)
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
        scentPage = new ScentPage();
        SelectWyne  selectWyne = scentPage.moveToSelectWynepage();
        waiting.waitForAjaxToComplete();
        selectWyne.selectWyne(SelectTypeOfWine.SPARKLING);
        Assert.assertTrue(browserUtils.isElementExists(selectWyne.getNextButton()));
    }

    @Test(alwaysRun = true,groups = { "ReturningUserRecommendations" })
    public void verifyRatingAndRecommendations() {
        selectWyne = new SelectWyne();
        RecommendationsPage recommendationsPage  = selectWyne.moveToRecommendationsPage();
        RateRecommendationsPage rateRecommendationsPage = new RateRecommendationsPage();
        waiting.waitForAjaxToComplete();
        Assert.assertTrue(browserUtils.isElementExists(recommendationsPage.getBackButton()));
        Assert.assertTrue(browserUtils.isElementExists(recommendationsPage.getFilter()));
        Assert.assertTrue(browserUtils.isElementExists(recommendationsPage.getSeeAllButton()));
        driver.get("https://dev.wyne.ai/b/italico-palo-alto/");
        waiting.waitTillSpinnerDisappears();
        waiting.waitForPageToLoad();
        waiting.waitForAjaxToComplete();
        Assert.assertTrue(browserUtils.isElementExists(rateRecommendationsPage.getHeart()));
        Assert.assertTrue(browserUtils.isElementExists(rateRecommendationsPage.getThumbsDown()));
        Assert.assertTrue(browserUtils.isElementExists(rateRecommendationsPage.getThumbsUp()));
        Assert.assertTrue(browserUtils.isElementExists(rateRecommendationsPage.getLoginButton()));
        Loginpage loginpage = rateRecommendationsPage.moveToLoginPageFromRecommendationsPage();
        waiting.waitForElementVisible(loginpage.getLogin_button());
        loginpage.getLogin_username_textbox().sendKeys("frontend");
        loginpage.getLogin_password_textbox().sendKeys("wyneforyou");
        loginpage.getLogin_button().click();
        waiting.waitTillSpinnerDisappears();
        waiting.waitForPageToLoad();
        waiting.waitForAjaxToComplete();
        Assert.assertTrue(browserUtils.isElementExists(driver.findElement(By.xpath("//h1[text()=' Welcome back frontend! How was our last recommendation at iTalico? ']"))));
    }
}
