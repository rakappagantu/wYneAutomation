package com.wYne.automation.ui;

import com.wYne.automation.ui.driver.SlWebDriver;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;


public class BrowserUtils  {

    protected SlWebDriver driver;

    public BrowserUtils(SlWebDriver driver){
        this.driver = driver;
    }


    public void hoverMouse(SlWebElement elemForMouseOver)
    {
        Actions builder = new Actions(driver);
        Actions hoverOver=builder.moveToElement(elemForMouseOver);
        hoverOver.perform();
    }

    // Verify button is enabled
    /*public boolean isButtonEnabled(SlWebElement e1) {
        return !(e1.getAttribute("class").contains("slc-x-disabled"));
    }*/

    /**
     * Method to focus on an element
     */
    public void focusOnElement(SlWebElement e1) {
        Actions act = new Actions(driver);
        act.moveToElement(e1).build().perform();
    }


    public void moveToElementAndClick(WebElement element){
        Actions act = new Actions(driver);
        act.moveToElement(element).build().perform();
        act.moveToElement(element).click(element).build().perform();
    }
    /**
     * This method is used to click with javascript if it is chrome driver
     * @param element
     */
    public void javascriptClick(SlWebElement element) {
        if((WebDriver)driver instanceof ChromeDriver || ((RemoteWebDriver) driver).getCapabilities().getBrowserName().equals("chrome")) {
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", element);
        } else {
            element.click();
        }
    }

    /**
     * This method is used to click with javascript if it is chrome driver
     * @param element
     */
    public void javascriptClick(WebElement element) {
        if((WebDriver)driver instanceof ChromeDriver || ((RemoteWebDriver) driver).getCapabilities().getBrowserName().equals("chrome")) {
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", element);
        } else {
            element.click();
        }
    }

    /**
     * This method is used to clear with javascript if it is chrome driver
     * @param element
     */
    public void javascriptClear(WebElement element) {
        if((WebDriver)driver instanceof ChromeDriver || ((RemoteWebDriver) driver).getCapabilities().getBrowserName().equals("chrome")) {
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].value = '';", element);
        } else {
            element.click();
        }
    }

    public boolean isElementExists(SlWebElement elem){

        boolean bExists = true;
        try {
            elem.getTagName();
            bExists = elem.isDisplayed();
        } catch (NoSuchElementException | NullPointerException e) {
            bExists = false;
            //System.out.println("******isElementExists method is called ---  elem is not found");
        }
        return bExists;
    }
    public boolean isElementExists(WebElement elem){

        boolean bExists = true;
        try {
            elem.getTagName();
            bExists = elem.isDisplayed();
            //System.out.println("isElementExists method is called ---  elem is found");
        } catch (NoSuchElementException | NullPointerException e) {
            bExists = false;
            //System.out.println("******isElementExists method is called ---  elem is not found");
        }
        return bExists;
    }

    public boolean isElementClickable(SlWebElement e1) {
        boolean flag = true;
        if(isElementExists(e1)) {
            flag = e1.getAttribute("class").contains("disabled") || !e1.isEnabled();// e1.getAttribute("class").contains("slc-x-disabled") || e1.getAttribute("class").contains("ui-state-disabled");
        }
        return !flag;
    }
    /*

    /**
    * This is used to enter text to a text box using robot class. for example browser authentication popup
     * @param robot
     * @param letter
     */
    /*
    public void typeCharacter(Robot robot, String letter)
    {
        try
        {
            Thread.sleep(500);
            if(letter.equals("@")){
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_2);
                robot.keyRelease(KeyEvent.VK_2);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }else if(letter.equals(".")){
                System.out.println("entering dot");
                robot.keyPress(KeyEvent.VK_PERIOD);
                robot.keyRelease(KeyEvent.VK_PERIOD);
            }
            else if(letter.equals("$")){
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_4);
                robot.keyRelease(KeyEvent.VK_4);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
            else if(letter.equals("+")){
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_EQUALS);
                robot.keyRelease(KeyEvent.VK_EQUALS);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
            else {
                boolean upperCase = Character.isUpperCase( letter.charAt(0) );
                String variableName = "VK_" + letter.toUpperCase();
                Class clazz = KeyEvent.class;
                Field field = clazz.getField( variableName );
                int keyCode = field.getInt(null);

                //robot.delay(1000);

                if (upperCase) robot.keyPress( KeyEvent.VK_SHIFT );
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                if (upperCase) robot.keyRelease( KeyEvent.VK_SHIFT );
            }


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void loginInAuthenticationPopUp(String userName, String password) throws Exception {
        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        Thread.sleep(2000);
        Robot rb = new Robot();
        for(int i=0;i<userName.length();i++)
        {
            this.typeCharacter(rb, ""+userName.charAt(i));
        }
        //tab to password entry field
        rb.keyPress(KeyEvent.VK_TAB);
        rb.keyRelease(KeyEvent.VK_TAB);
        for(int i=0;i<password.length();i++)
        {
            this.typeCharacter(rb, ""+password.charAt(i));
        }
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(5000);
    }*/



    public void maximize() {

        String os = System.getProperty("os.name").toLowerCase();
        if (os.toLowerCase().contains("mac")) {

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenResolution = new Dimension((int)
                    toolkit.getScreenSize().getWidth(), (int)
                    toolkit.getScreenSize().getHeight());
            driver.manage().window().setSize(screenResolution);
        } else {
                driver.manage().window().maximize();
        }
    }




}
