package com.wYne.automation.pages;

import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class RateRecommendationsPage extends AbstractDriverBasePage{

    public SlWebElement getThumbsDown() {
        return thumbsDown;
    }

    public SlWebElement getThumbsUp() {
        return thumbsUp;
    }

    public SlWebElement getHeart() {
        return heart;
    }

    @FindBy(xpath="//i[@class='fas fa-thumbs-down']")
    private SlWebElement  thumbsDown;

    @FindBy(xpath="//i[@class='fas fa-thumbs-up']")
    private SlWebElement  thumbsUp;

    @FindBy(xpath="//i[@class='fas fa-heart']")
    private SlWebElement  heart;

    public SlWebElement getTextMessage() {
        return textMessage;
    }

    @FindBy(xpath="//div[text()=' I didn’t try ']")
    private SlWebElement textMessage;

    public SlWebElement getLoginButton() {
        return loginButton;
    }

    @FindBy(xpath="//button[text()='Please Login ']")
    private SlWebElement loginButton;

    public Loginpage moveToLoginPageFromRecommendationsPage()
    {
        this.getLoginButton().click();
        waiting.waitForPageToLoad();
        waiting.waitTillSpinnerDisappears();
        return new Loginpage();
    }

}
