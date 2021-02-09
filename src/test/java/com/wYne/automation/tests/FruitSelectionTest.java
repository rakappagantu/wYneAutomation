package com.wYne.automation.tests;

import com.wYne.automation.pages.*;
import com.wYne.automation.tests.base.BaseTest;
import com.wYne.automation.pages.FruitFlavorsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.wYne.automation.dataTypes.FruitSelection;

public class FruitSelectionTest extends BaseTest {

    protected StartQuizPage startQuizPage ;


    @Test(alwaysRun = true,groups = { "AnonymousUserRecommendations" })
    public void verifySelectionOfFruitRecommendations() {
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
        Assert.assertTrue(browserUtils.isElementExists(fruitFlavorsPage.getBackButon()));

    }
}
