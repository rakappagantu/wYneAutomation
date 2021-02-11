package com.wYne.automation.pages;

import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.ui.core.Waiting;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.io.*;
import java.util.*;
import org.openqa.selenium.Cookie;

public class Loginpage extends AbstractDriverBasePage {
    public Loginpage() {
        waiting.waitForPageToLoad();
        this.readCookies();
        this.writeCookies();
    }

    @FindBy(xpath = "//input[@id='id_login']")
    public SlWebElement login_username_textbox;

    @FindBy(xpath = "//input[@id='id_password']")
    public SlWebElement login_password_textbox;

    public SlWebElement getLogin_username_textbox() {
        return login_username_textbox;
    }

    @FindBy(xpath = "")
    private SlWebElement error_message;

    @FindBy(xpath = "")
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

    @FindBy(xpath = "//button[text()='Log in']")
    public SlWebElement login_button;

    public SelectPartner moveToSelectPartnerPage() {

        this.getLogin_username_textbox().sendKeys("frontend");
        this.getLogin_password_textbox().sendKeys("wyneforyou");
        this.getLogin_button().click();
        waiting.waitTillSpinnerDisappears();
        waiting.waitForPageToLoad();
        return new SelectPartner();
    }

    public void readCookies() {
        driver.get(ConfigManager.getBundle().getString("env.baseurl"));
        waiting.waitForPageToLoad();
        waiting.waitForAjaxToComplete();
        File file = new File("Cookies.data");
        try {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);
            // loop for getting the cookie information

            // loop for getting the cookie information
            for (Cookie ck : driver.manage().getCookies()) {
                Bwrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";" + ck.getExpiry() + ";" + ck.isSecure()));
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeCookies() {
        try {
            File file = new File("Cookies.data");
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;
            while ((strline = Buffreader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(strline, ";");
                while (token.hasMoreTokens()) {
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    Date expiry = null;

                    String val;
                    if (!(val = token.nextToken()).equals("null")) {
                        expiry = new Date(val);
                    }
                    Boolean isSecure = new Boolean(token.nextToken()).
                            booleanValue();
                    Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
                    System.out.println(ck);
                    driver.manage().addCookie(ck); // This will add the stored cookie to your current session
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        driver.get(ConfigManager.getBundle().getString("env.baseurl"));

    }


}
