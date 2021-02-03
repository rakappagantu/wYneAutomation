package com.wYne.automation.ui.internal;

import com.slqa.ui.elements.SlWebElement;
import com.slqa.ui.elements.SnapLogger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.*;
import java.util.Arrays;

import static com.slqa.ui.internal.ImplementedByProcessor.getWrapperClass;


/**
 * Replaces DefaultLocatingElementHandler. Simply opens it up to descendants of the WebElement interface, and other
 * mix-ins of WebElement and Locatable, etc. Saves the wrapping type for calling the constructor of the wrapped classes.
 */
public class ElementHandler implements InvocationHandler {
    private final ElementLocator locator;
    private final Class<?> wrappingType;
    private final Field myField;
    /**
     * Generates a handler to retrieve the WebElement from a locator for a given WebElement interface descendant.
     *
     * @param interfaceType Interface wrapping this class. It contains a reference the the implementation.
     * @param locator       Element locator that finds the element on a page.
     * @param <T>           type of the interface
     */
    public <T> ElementHandler(Class<T> interfaceType, Field field, ElementLocator locator) {
        this.locator = locator;
        this.myField = field;
        if (!SlWebElement.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }

        this.wrappingType = getWrapperClass(interfaceType);
    }

    @Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {

        WebElement element = locator.findElement();
        
        String simpleClassName = this.myField.getDeclaringClass().getSimpleName();
        String simpleObjName = this.myField.getName();
        String value = "";
        
        //SnapLogger.LOGGER.debug("****");
        //SnapLogger.LOGGER.debug();
        //SnapLogger.LOGGER.debug(this.myField.getName());
		if (objects != null){ 
			for (Object obj: objects){
				if (obj == null) break;
                String temp = null;
                if (obj instanceof CharSequence[])
                    temp = Arrays.toString((CharSequence[]) obj);
				//value = value +"+" + obj.toString();
				value = (value=="")? temp  : value +"+" + temp;
			}
		}
		
        //SnapLogger.LOGGER.debug("ACTIONS----Class:[" + simpleClassName + "], Object:[" +simpleObjName + "], method: [" + method.getName() + "], objects:[" + objects);

        SnapLogger.LOGGER.info("LOGGING THE ACTIONS:" + "CLASS:["+simpleClassName+"] , OBJECT:[" +simpleObjName + "],"
        		+" METHOD:["+ method.getName() +"], VALUE:[" +value+ "]" );
        
        //SnapLogger.LOGGER.debug("######END######");
        
        if ("getWrappedElement".equals(method.getName())) {
            return element;
        }
        Constructor<?> cons = wrappingType.getConstructor(WebElement.class);
        Object thing = cons.newInstance(element);
        
        try {
            return method.invoke(wrappingType.cast(thing), objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
