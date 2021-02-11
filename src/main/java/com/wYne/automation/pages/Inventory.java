package com.wYne.automation.pages;

import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.support.FindBy;

public class Inventory extends AbstractDriverBasePage {

    public SlWebElement getLogOutButton() {
        return logOutButton;
    }

    @FindBy(xpath="//span[text()='Logout']")
    private SlWebElement logOutButton;

    public SlWebElement getInventory() {
        return Inventory;
    }

    public SlWebElement getShopify() {
        return shopify;
    }

    @FindBy(xpath="//span[text()=' Shopify ']")
    private SlWebElement shopify;

    @FindBy(xpath="//span[text()=' Inventory ']")
    private SlWebElement Inventory;

}
