package com.wYne.automation.general;

import com.wYne.automation.exceptions.WyneException;
import com.sun.mail.pop3.POP3Folder;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Sunil B  on 19/12/16.
 */

public class EmailUtils {
    private static final Logger LOGGER = Logger.getLogger(EmailUtils.class);
    String host = "pop.gmail.com";
    String mailStoreType = "pop3";
    Properties properties;


    public EmailUtils() {
        properties = new Properties();
        properties.put("mail.store.protocol", mailStoreType);
        properties.put("mail.pop3s.host", host);
        properties.put("mail.pop3s.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
    }

    public String getResetPasswordLink(String username, String password, String subject, String searchContent) throws Exception {
        Map<String, String> emailContent = this.getEmailHtmlContentAsString(username,password, subject, searchContent);
        String resetLink = null;
        Document doc = Jsoup.parse(emailContent.get(CommonConstants.EMAIL_BODY));
        Elements elements = doc.getElementsByTag("a");
        for (Element ele : elements) {
            if (ele.attr("href").contains(username.split("@")[0])) {
                resetLink = ele.attr("href");
                break;
            }
        }
        return resetLink;
    }

    public void delete(String username,String password) throws Exception {
        try {
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("pop3s");
            store.connect(host, username, password);
            Folder emailFolder = (POP3Folder) store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);
            Message[] messages = emailFolder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                message.setFlag(Flags.Flag.DELETED, true);
            }
            emailFolder.close(true);
            store.close();
            Thread.sleep(4000);

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    /***
     * This method is used to read the email and return the user and password depending on the given subject
     *
     * If no subject is given, the default subject will be snaplogic where we used for most of the cases
     * If its unable to find the email with in a given time, it will throw an exception
     * @param username
     * @param password
     * @param subject
     * @return
     * @throws Exception
     */
    public Map<String, String> getUsernameAndPasswordFromEmail(String username,String password, String subject) throws Exception {
        subject = (subject == null || subject.isEmpty()) ? "Snaplogic" : subject;
        Map<String, String> userMap = new HashMap<>();
        //Waiting for some time for email to appear, as other options did not work
        Thread.sleep(3000);
        try {
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("pop3s");
            store.connect(host, username, password);
            Folder emailFolder = null;
            boolean isExists = false;
            int maxCount = 0;
            while (!isExists && maxCount < 100) {
                emailFolder = store.getFolder("INBOX");
                emailFolder.open(Folder.READ_ONLY);
                Message[] messages = emailFolder.getMessages();
                for (int i = 0; i < messages.length; i++) {
                    Message message = messages[i];
                    if (message.getSubject().contains(subject)){
                        Document doc = Jsoup.parse(writePart(message));
                        List<Element> elementList = doc.getElementsByTag("span");
                        String temp = "";
                        for (Element ele : elementList) {
                            if (ele.text().contains("Your login is")) {
                                temp = ele.text().replace("Your login is: ", "");
                                temp = temp.replace(".com.", ".com");
                                userMap.put("userName", temp);
                            }
                            if (ele.text().contains("Your password is")) {
                                temp = ele.text().replace("Your password is: ", "");
                                userMap.put("password", temp);
                            }
                        }
                        isExists = true;
                        message.setFlag(Flags.Flag.DELETED, true);
                        break;
                    } else {
                        Thread.sleep(6000);
                        // maxCount += 5;
                    }

                }
                maxCount += 3;
                Thread.sleep(6000);
            }
            if(null != emailFolder) {
                emailFolder.close(true);
            }
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        if(userMap.isEmpty()) {
            throw new WyneException("User Map after reading the email for credentials failed with subject : " +
                    ""+subject+" : username - "+userMap.get("userName") + " password - "+userMap.get("password"));
        }
        return userMap;
    }

    /***
     * This method is used to read the email body as html and return the content in a map
     *
     * With the given subject and search content it will search for the email and read the content of HTML
     * If its unable to find the email with in a given time, it will throw an exception
     * @param username
     * @param password
     * @param subject
     * @param searchContent
     * @return
     * @throws Exception
     */
    public Map<String,String> getEmailHtmlContentAsString(String username,String password, String subject, String searchContent) throws Exception {
        Map<String, String> emailContent = new HashMap<>();
        try {
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("pop3s");
            store.connect(host, username, password);
            Folder emailFolder = null;
            boolean isExists = false;
            int maxCount = 0;
            while (!isExists && maxCount < 100) {
                emailFolder = store.getFolder("INBOX");
                //emailFolder.open(Folder.READ_ONLY);
                emailFolder.open(Folder.READ_WRITE);
                Message[] messages = emailFolder.getMessages();
                for (int i = 0; i < messages.length; i++) {
                    Message message = messages[i];
                    if (message.getSubject().toLowerCase().contains(subject.toLowerCase())){
                        if (message.isMimeType("text/*")) {
                            String s = (String)message.getContent();
                            if(s.contains(searchContent)) {
                                emailContent.put(CommonConstants.EMAIL_SUBJECT, message.getSubject());
                                emailContent.put(CommonConstants.EMAIL_BODY, s);
                                message.setFlag(Flags.Flag.DELETED, true);
                                isExists = true;
                                break;
                            }
                        }
                    } else {
                        Thread.sleep(6000);
                        //maxCount += 5;
                    }

                }
                maxCount += 3;
                Thread.sleep(6000);
            }
            if(null != emailFolder){
                emailFolder.close(true);
            }
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        if(emailContent.isEmpty()) {
            throw new WyneException("Email content read from email with subject - "
                    + emailContent.get(CommonConstants.EMAIL_SUBJECT) + "expected subject: "
                    +subject + " and search string : "+ searchContent);
        }
        LOGGER.info("Email content read from email with subject - "+ emailContent.get(CommonConstants.EMAIL_SUBJECT));
        return emailContent;
    }

    /***
     * This method is used to get the Email content from body as a Map depending on the given search string and subject
     * @param userName
     * @param password
     * @param subject
     * @param searchString
     * @return
     * @throws Exception
     */
    public Map<String, String> getEmailBody(String userName, String password, String subject, String searchString) throws Exception {
        Map<String, String> emailNotification = this.getEmailHtmlContentAsString(userName, password, subject, searchString);
        String body = "";
        Document doc = Jsoup.parse(emailNotification.get(CommonConstants.EMAIL_BODY));
        Elements elements = doc.getElementsByTag("p");
        for (Element ele : elements)
            body += ele.text()+"\n";
        emailNotification.put(CommonConstants.EMAIL_BODY, body.trim());
        emailNotification.put(CommonConstants.EMAIL_HEADER, doc.select("table").get(3).getElementsByTag("a").get(0).attributes().get("title"));
        if(!body.contains(searchString))
            emailNotification.put(CommonConstants.EMAIL_BODY, null);
        return emailNotification;
    }

    public static String writePart(Part p) throws Exception {
        String str = "";
        if (p.isMimeType("text/plain")) {
            return (String) p.getContent();
        }
        else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            //for (int i = 0; i < count; i++)
            str = (String) mp.getBodyPart(1).getContent();
            return str;
        }
        else if (p.isMimeType("message/rfc822")) {
            return writePart((Part) p.getContent());
        } else {
            Object o = p.getContent();
            if (o instanceof String) {
                return (String) o;
            }
        }
        return str;
    }

    /*public static void main(String args[]) throws Exception {

        SLRestServices slRestServices = new SLRestServices("sbuddi@snaplogic.com", "snapLogic@12345");
        List<String> projectSpaceList = slRestServices.getListOfProjectSpaces("automation");
        for (String projectSpace : projectSpaceList) {
            List<String> projectsList = slRestServices.getListOfProjects("automation", projectSpace);
            for (String str : projectsList) {
                if (str.contains("first last") || str.contains("DefaultProject Test")) {
                    slRestServices.deleteProject("automation", projectSpace, str);
                    System.out.println("Deleted project " + str);
                }
            }
        }
    }*/

        /*for(int i = 1;i < 31;i++) {
            try {
                String user = "autoreg+basic";
                SLRestServices slRestServices = new SLRestServices("autoreg+admin1@snaplogic.com", "Snaplogic@12345");
                //slRestServices.createUser(user + i + "@snaplogic.com", "Basic", "User" + i, "automation", false, true);
                //String userLink = new UserUtils().getResetPasswordLink("autoregplatform@gmail.com", "snaplogic@12345", user + i + "@gmail.com");
                // Map<String, String> userMap = new UserUtils().read("autoregplatform@gmail.com", "snaplogic@12345");
                //String resetCode = userLink.substring(userLink.indexOf("code=") + 5, userLink.indexOf("&email"));
                //slRestServices.resetPasswordForNewUser(user + i + "@gmail.com", "Snaplogic@12345", resetCode);

                // SLRestServices currentService = new SLRestServices(userMap.get("userName"), "Snaplogic@12345");
                //slRestServices.addUserToGroup("automation", "admins", "autoregplatform+admin" + i + "@gmail.com");

                System.out.println("Created user " + user + i + "@snaplogic.com");
                slRestServices.addExistingUsersToOrg(user + i + "@snaplogic.com", "SEC1");
                slRestServices.addUserToGroup("SEC1", "members", user + i + "@snaplogic.com");

                slRestServices.addExistingUsersToOrg(user + i + "@snaplogic.com", "SEC2");
                slRestServices.addUserToGroup("SEC2", "members", user + i + "@snaplogic.com");

                slRestServices.addExistingUsersToOrg(user + i + "@snaplogic.com", "SEC3");
                slRestServices.addUserToGroup("SEC3", "members", user + i + "@snaplogic.com");
            }catch (Exception e){

            }
        }*/

}