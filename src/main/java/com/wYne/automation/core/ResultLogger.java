package com.wYne.automation.core;

import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.general.SlResultEntity;
import com.wYne.automation.ui.driver.SlWebDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by gaian on 29/7/16.
 */
public class ResultLogger {

    protected static Logger logger = LoggerFactory.getLogger(ResultLogger.class);
    /**
     * This method generate a fileName cotaining , GroupId , method Name and
     * DateTime *Caution* : If there will be mutiple group Id for one method ,
     * we'll have to modify this function , for now single group Id is given
     *
     * @param
     * @return
     */


    protected synchronized static String generateFilename(String filename) {
        Calendar c = Calendar.getInstance();

        //filename = result.getMethod().getMethodName();
        filename = filename + "_" + c.get(Calendar.YEAR) + "-"
                + c.get(Calendar.MONTH) + "-"
                + c.get(Calendar.DAY_OF_MONTH) + "-"
                + c.get(Calendar.HOUR_OF_DAY) + "-"
                + c.get(Calendar.MINUTE) + "-" + c.get(Calendar.SECOND);
        //}
        // filename will contain the groupId_methodName_DateTime
        return filename + ".png";
    }


    protected synchronized static String takeScreenshot(String fileName)  {
        SlWebDriver driver;
        driver = SlInitSettingsFactory.getDriver();
        String filePath = "";
        try {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            String dirPath = ConfigManager.getBundle().getString("selenium.screenshots.dir");
            filePath = dirPath + File.separator + fileName;
            FileUtils.copyFile(scrFile, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }


    /***
     *
     * @param slResultEntity
     */


}
