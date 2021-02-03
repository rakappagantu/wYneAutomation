package com.wYne.automation.ui.internal;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public final class SlDefaultElementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;

    public SlDefaultElementLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public ElementLocator createLocator(Field field) {
        return new SlDefaultElementLocator(searchContext, field);
    }
}
