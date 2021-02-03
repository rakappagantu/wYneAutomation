package com.wYne.automation.ui.elements;


import com.google.common.collect.ImmutableList;
import com.slqa.general.ResourceLoader;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;

import java.util.concurrent.TimeUnit;

public class ElementWait extends FluentWait<WebElement> {
    public ElementWait(WebElement element, long timeOutInMiliSeconds) {
        this(element, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInMiliSeconds, 50);
    }

    public ElementWait(WebElement element, long timeOutInMiliSeconds, long sleepInMillis) {
        this(element, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeOutInMiliSeconds, sleepInMillis);
    }

    public ElementWait(WebElement isExtendedWebElement, long... timeout) {
        this(isExtendedWebElement, 10, 10);
    }

    @SuppressWarnings("unchecked")
    protected ElementWait(WebElement element, Clock clock, Sleeper sleeper, long timeOutInMiliSeconds, long sleepTimeOut) {
        super(element, clock, sleeper);
        withTimeout(timeOutInMiliSeconds, TimeUnit.MILLISECONDS);
        pollingEvery(sleepTimeOut, TimeUnit.MILLISECONDS);
        ignore(NoSuchElementException.class, StaleElementReferenceException.class, WebDriverException.class);
    }



    public ElementWait ignore(Class<? extends RuntimeException>... exceptionType) {
        return (ElementWait) ignoreAll(ImmutableList.copyOf(exceptionType));
    }

    private static long getTimeout(long... timeout) {
        if (timeout == null || timeout.length < 1 || timeout[0] <= 0) {
            return getDefaultTimeout();
        }
        return timeout[0];
    }

    private static long getInterval(long... timeout) {
        if (timeout == null || timeout.length < 2 || timeout[1] <= 0) {
            return getDefaultInterval();
        }
        return timeout[1];
    }

    private static long getDefaultTimeout() {
        return Long.parseLong(ResourceLoader.getBundle().getString("selenium.wait.timeout"));//, WaitService.getDefaultPageWaitTimeNum());
    }

    private static long getDefaultInterval() {
        return 50;
        //return Long.parseLong(ConfigManager.getBundle().getString("selenium.explicit.wait.interval"));
        //return ConfigurationManager.getBundle().getLong("selenium.explicit.wait.interval", WaitService.getDefaultWaitIntervalTimeNum());
    }
}
