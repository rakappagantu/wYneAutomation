package com.wYne.automation.pages;

import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.support.FindBy;

public class Loginpage extends AbstractDriverBasePage{
    @FindBy(xpath = "//input[@id='id_login']")
    public SlWebElement login_username_textbox;

    @FindBy(xpath = "//input[@type='password']")
    public SlWebElement login_password_textbox;

    public SlWebElement getLogin_username_textbox() {
        return login_username_textbox;
    }

    @FindBy(xpath="")
    private SlWebElement error_message;

    @FindBy(xpath="")
    public SlWebElement invalidCredentails_error;

    @FindBy(xpath = "//a[text()='Forgot password?']")
    public SlWebElement forgot_password;

    @FindBy(xpath = "//button[text()='Submit']")
    public SlWebElement forgot_Submit;

    public SlWebElement getLogin_password_textbox() {
        return login_password_textbox;
    }

    public SlWebElement getLogin_button() {
        return login_button;
    }

    public SlWebElement getError_message() {
        return error_message;
    }

    public SlWebElement getInvalidCredentails_error() {
        return invalidCredentails_error;
    }

    public SlWebElement getForgot_password() {
        return forgot_password;
    }

    public SlWebElement getForgot_Submit() {
        return forgot_Submit;
    }

    @FindBy(xpath = "//button[@type='submit']")
    public SlWebElement login_button;


}
