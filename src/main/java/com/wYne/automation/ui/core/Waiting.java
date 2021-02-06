package com.wYne.automation.ui.core;

import com.wYne.automation.general.ResourceLoader;
import com.wYne.automation.ui.driver.SlWebDriver;
import com.wYne.automation.ui.elements.ElementExpectedConditions;
import com.wYne.automation.ui.elements.ElementWait;
import com.wYne.automation.ui.elements.SlWebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class Waiting {
	
	static int smallWait = 2;
	static int mediumWait = 5;
	static int longWait = 10;
	private SlWebDriver driver;
	private WebDriverWait webDriverWait;
	long timeOutInSecs;// = 30;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public Waiting(SlWebDriver driver){
		this.driver = driver;
		timeOutInSecs = getDefaultPageWaitTimeNum()/1000;
		webDriverWait = new WebDriverWait(driver,timeOutInSecs * 5);
	}
	
	public void longWait() {
		staticWait(longWait);
	}
	
	public void shortWait() {
		staticWait(smallWait);
	}

	public void mediumWait() {
		staticWait(mediumWait);
	}

	public static void staticWait(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public enum JsToolkit {
		DOJO("dojo", "dojo.io.XMLHTTPTransport.inFlight.length==0"),
		EXTJS("Ext", "Ext.Ajax.isLoading()==false"),
		JQUERY("jQuery", "jQuery.active==0"),
		YUI("YAHOO", "YAHOO.util.Connect.isCallInProgress==false"),
		PHPJS("PHP_JS", "PHP_JS.resourceIdCounter==0"),
		PROTOTYPE("Ajax", "Ajax.activeRequestCount==0");

		String expr;
		String identifier;

		private JsToolkit(String identifier, String expr) {
			this.identifier = identifier;
			this.expr = expr;
		}

		private String IsNotPresent() {
			return "!selenium.browserbot.getCurrentWindow()." + this.identifier;
		}

		private String isAjaxCallComplete() {
			return "selenium.browserbot.getCurrentWindow()." + this.expr;
		}

		protected boolean isPageActive(Object... objects) {
			return false;
		}

		public String waitCondition() {
			return "(" + IsNotPresent() + " || " + isAjaxCallComplete() + ")";
		}
	}

	public void waitForAjaxToComplete() {
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
				}
				catch (Exception e) {
					// no jQuery present
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState")
						.toString().equals("complete");
			}
		};

		webDriverWait.withTimeout(timeOutInSecs*10,TimeUnit.SECONDS).until(jQueryLoad);
		webDriverWait.until(jsLoad);

	}



	public void waitForElement(WebElement element) {
		waitForElement(element,timeOutInSecs);
	}
	
	public void waitForElement(WebElement element, long maxWaitTimeOut) {
		WebDriverWait wait = new WebDriverWait(driver, maxWaitTimeOut);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public  void waitForElement(By locator) {
		waitForElement(locator, timeOutInSecs);
	}
	
	public void waitForElement(By locator, long maxWaitTimeOut) {
		WebDriverWait wait = new WebDriverWait(driver, maxWaitTimeOut);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitForElement(String name){
		waitForElement(name, timeOutInSecs);
	}

	public void waitForElement(String name, long maxWaitTimeOut){
		WebDriverWait wait = new WebDriverWait(driver, maxWaitTimeOut);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
	}
		
	public void waitForElementInvisibility(By locator){
		waitForElementInvisibility(locator, timeOutInSecs);
	}

	public void waitForElementInvisibility(By locator, long maxTimeout){
		WebDriverWait wait = new WebDriverWait(driver, maxTimeout);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	public void waitForElementText(WebElement element,String text){
		webDriverWait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

    public void waitForElementText(WebElement element,String text, long maxWaitTimeOut){
        webDriverWait.withTimeout(maxWaitTimeOut, TimeUnit.SECONDS).ignoring(NoSuchElementException.class).
				until(ExpectedConditions.textToBePresentInElement(element, text));
    }

	public void waitForElementInvisibility(String name){
		waitForElementInvisibility(name, timeOutInSecs);
	}
	
	public void waitForElementInvisibility(String name, long maxWaitTimeOut) {
		WebDriverWait wait = new WebDriverWait(driver, maxWaitTimeOut);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name(name)));
	}

	/***
	 * This method is used to wait until the expected no of tabs are present or not with in a given max timeout
	 * @param numberOfWindows
	 * @param maxWaitTimeOut
	 */
	public void waitForNumberOfWindowsToEqual(final int numberOfWindows, long maxWaitTimeOut) {
		WebDriverWait wait = new WebDriverWait(driver, maxWaitTimeOut);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (driver.getWindowHandles().size() == numberOfWindows);
			}
		});
	}

	public void waitForElementVisible(WebElement element) {
//		WebDriverWait wait = new WebDriverWait(driver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementVisible(WebElement element, long maxWaitTimeOut) {
		WebDriverWait newWebDriverWait = new WebDriverWait(driver,maxWaitTimeOut);
		newWebDriverWait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementEnabled(WebElement element) {
		webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForElementEnabled(WebElement element,long maxWaitTimeOut) {
		WebDriverWait newWebDriverWait = new WebDriverWait(driver,maxWaitTimeOut);
		newWebDriverWait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	
	public void waitForElementInVisible(WebElement element) {
		//webDriverWait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
		new ElementWait(element).withTimeout(timeOutInSecs, TimeUnit.SECONDS).until(ElementExpectedConditions.elementNotVisible());
	}

	public void waitForElementInVisible(WebElement element, long maxWaitTimeOut) {
		new ElementWait(element).withTimeout(maxWaitTimeOut, TimeUnit.SECONDS).
				until(ElementExpectedConditions.elementNotVisible());
	}

	public void waitForElementNotPresent(By locator) {
		webDriverWait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(locator)));
	}

	public void waitForElementPresent(By locator) {
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}


	public static long getDefaultPageWaitTimeNum() {
		String timeOut = ResourceLoader.getBundle().getString("selenium.wait.timeout", "30000");
		return Long.parseLong(timeOut);
	}

	public void waitTillSpinnerDisappears() {
		long timeout = timeOutInSecs * 10;
		waitTillSpinnerDisappears(timeout);
	}

	public void waitTillSpinnerDisappears(long timeOutInSeconds) {
		//SlWebElement processingSpinner = driver.findElement(By.cssSelector(".slc-x-busy"));
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".spinner-border")));
		}catch (TimeoutException e){
			e.printStackTrace();
			throw new TimeoutException("Spinner taking more time to disappear, waiting for more than : [" + timeOutInSecs * 3 + "] seconds");
		}
	}

	public void waitForVisible(SlWebElement elem) {
		long timeout = Long.parseLong(ResourceLoader.getBundle().getString("selenium.wait.timeout"));
		new ElementWait(elem, timeout).
				ignore(WebDriverException.class, NoSuchElementException.class, StaleElementReferenceException.class).
				until(ElementExpectedConditions.elementVisible());
	}


	
/*	//Just a try as existing waitForPageToLoad() is timing out after 10 sec
	public void waitForPageToLoad() {
		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");

			}
		});
	}*/


 	public void waitForPageToLoad() {
 		ExpectedCondition<Boolean> pageLoadCondition = new
 				ExpectedCondition<Boolean>() {
 					public Boolean apply(WebDriver driver) {
 						return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
 					}
 				};
 		webDriverWait.until(pageLoadCondition);
 	}

	public void waitTillValidationCompletesOnConfigWizard() {
		long timeout = timeOutInSecs * 5;
		waitTillValidationCompletesOnConfigWizard(timeout);
	}

	public void waitTillValidationCompletesOnConfigWizard(long timeOutInSeconds) {
		//SlWebElement processingSpinner = driver.findElement(By.cssSelector(".slc-x-busy"));
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.attributeContains(By.xpath("//div[@class='sl-wizard-mask']"),"style","none"));
		}catch (TimeoutException e){
			e.printStackTrace();
			throw new TimeoutException("validation taking more time to disappear, waiting for more than : [" + timeOutInSecs * 3 + "] seconds");
		}
	}


}
