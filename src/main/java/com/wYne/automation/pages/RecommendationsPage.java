package com.wYne.automation.pages;

import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.support.FindBy;

public class RecommendationsPage extends  AbstractDriverBasePage{


    public SlWebElement getBackButton() {
        return backButton;
    }

    @FindBy(xpath = "//span[text()=' Back']")
    private SlWebElement backButton;

    public SlWebElement getFilter() {
        return filter;
    }

    @FindBy(xpath = "//span[text()='Filter']")
    private SlWebElement filter;

    public SlWebElement getSeeAllButton() {
        return seeAllButton;
    }

    @FindBy(xpath = "//button[text()='See All']")
    private SlWebElement seeAllButton;




}
