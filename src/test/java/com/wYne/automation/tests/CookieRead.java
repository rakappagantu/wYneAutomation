package com.wYne.automation.tests;

import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.tests.base.BaseTest;
import com.wYne.automation.ui.core.Waiting;
import org.openqa.selenium.Cookie;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;


public class CookieRead extends BaseTest {

    @Test
    public void readCookies()
    {
        driver.get(ConfigManager.getBundle().getString("env.baseurl"));
        waiting.waitForPageToLoad();
        waiting.waitForAjaxToComplete();
        File file = new File("Cookies.data");
        try
        {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);
            // loop for getting the cookie information

            // loop for getting the cookie information
            for(Cookie ck : driver.manage().getCookies())
            {
                Bwrite.write((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Test
    public void writeCookies()
    {
        try{

            File file = new File("Cookies.data");
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;
            while((strline=Buffreader.readLine())!=null){
                StringTokenizer token = new StringTokenizer(strline,";");
                while(token.hasMoreTokens()){
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    Date expiry = null;

                    String val;
                    if(!(val=token.nextToken()).equals("null"))
                    {
                        expiry = new Date(val);
                    }
                    Boolean isSecure = new Boolean(token.nextToken()).
                            booleanValue();
                    Cookie ck = new Cookie(name,value,domain,path,expiry,isSecure);
                    System.out.println(ck);
                    driver.manage().addCookie(ck); // This will add the stored cookie to your current session
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        driver.get(ConfigManager.getBundle().getString("env.baseurl"));
        driver.findElement(By.xpath("//input[@id='id_login']")).sendKeys("frontend");
        driver.findElement(By.xpath("//input[@id='id_password']")).sendKeys("wyneforyou");
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Waiting.staticWait(4000);

    }
}

