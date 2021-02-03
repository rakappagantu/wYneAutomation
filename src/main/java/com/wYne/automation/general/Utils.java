package com.wYne.automation.general;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.ReadContext;
import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.exceptions.WyneException;
import com.wYne.automation.ui.core.Waiting;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.http.Header;
import org.apache.http.*;
import com.wYne.automation.ui.core.AsyncHttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.*;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {
	private static final Logger LOGGER = Logger.getLogger(Utils.class);


	String downloadPath = ConfigManager.getBundle().getString("downloadfile.path");
	//this method accepts string, string pattern (broken into groups) and group you want to use in comparison
	public String matchStringPattern(String pattern, String stringToMatch, int groupNum) {

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(stringToMatch);
		m.find();
		return m.group(groupNum);

	}

	// Verify actual pipelines against expected
	public static <T> boolean isListEqual(List<T> actual, List<T> expected) {


		if (actual.size() != expected.size())
			return false;

		boolean equal = true;
		for (int i = 0; i < actual.size(); i++) {
			if (!actual.contains(expected.get(i))) {
				equal = false;
				break;
			}
		}

		return equal;
	}


	public String getCurrentDateInMMDDYYFormat()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
		String formattedDate=sdf.format(cal.getTime());
		return formattedDate;
	}


	public String getCurrentDateFormattedPlusHours(String format,int hours)
	{
		String currentDateFormattedPlusHours= LocalDateTime.now().plusHours(hours).format(DateTimeFormatter.ofPattern(format));
		return currentDateFormattedPlusHours;
	}



	public boolean compareDates(String expectedDate, String actualDate, String format){
		boolean flag = false;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date expDate = sdf.parse(expectedDate);
			Date actDate = sdf.parse(actualDate);
			if(expDate.compareTo(actDate) == 0){
				flag = true;
			}

		}catch(ParseException ex){
			//ex.printStackTrace();
		}
		return flag;
	}

	public  String currentDateTimePlus(String timeZone, String dateFormat, int minutes)
	{
		String newTime=null;
		Date currentTime = new Date();
		try
		{
			SimpleDateFormat gmtDateFormat = new SimpleDateFormat(dateFormat);
			gmtDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
			SimpleDateFormat gmtDateFormatlocal = new SimpleDateFormat(dateFormat);
			Date d1=gmtDateFormatlocal.parse(gmtDateFormat.format(currentTime));
			// String date= datetime.split(" ")[0];
			// String time= datetime.split(" ")[1];

			Calendar cal = Calendar.getInstance();
			cal.setTime(d1);

			cal.add(Calendar. MINUTE,  minutes);
			SimpleDateFormat gmtDateFormatlatest = new SimpleDateFormat(dateFormat);
			//cal.add(Calendar.MONTH, -11);
			newTime = gmtDateFormatlatest.format(cal.getTime());

		}
		catch(Exception e)
		{

		}
		return newTime;
	}

	/**
	 * This method is used to check the string null and return the list using the default delimiter ,
	 * It can escape the delimiter ,  between quotes for any text.
	 * @param mandatoryFields
	 * @return
	 */
	public List<String> changeToList(String mandatoryFields) {
		// TODO Auto-generated method stub
		List<String> fields = null;
		if(null != mandatoryFields && !mandatoryFields.isEmpty()) {
			/***
			 * "," 					+	//Match a comma
			 "	(?=" 				+	//Start positive look ahead eg: t(?=s) matches the second t in streets
			 "		(?:" 			+	//start non-capturing group 1
			 "			[^\"]*" 	+	//match 'otherThanQuote(")' zero or more times
			 "			\"[^\"]*\"" +	//match 'quotedString'
			 "			)*" 		+	//end group 1 and repeat it zero or more times
			 "		[^\"]*" 		+	//match 'otherThanQuotes (")'
			 "	$" 					+	//match the end of the string
			 ")"						//stop positive look ahead
			 */
			fields = new LinkedList<String>(Arrays.asList(mandatoryFields.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)));
			for (ListIterator<String> lit = fields.listIterator(); lit.hasNext();) {
				lit.set(lit.next().trim());
			}
		}
		return fields;
	}
	public void clearDownloadPath(String deleteFileName) throws WyneException{
		downloadPath = downloadPath + "\\exportPipeLine";
		try{
			File dir = new File(downloadPath);
			FileFilter fileFilter = new WildcardFileFilter(deleteFileName+".zip");
			File[] files = dir.listFiles(fileFilter);

			for (File zipFile : files){
				//System.out.println("deleting the files:" + zipFile.getAbsolutePath());
				FileUtils.forceDelete(zipFile);
			}
		}
		catch(Exception io){ //catch(IOException io)
			throw new WyneException("Unable to delete files in path : [" + downloadPath +"]");
		}

	}

	public  boolean isValidDate(String inDate, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);

		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	public void deleteAllEmailMessages(String host, String port,
									   String userName, String password) {
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
				String.valueOf(port));

		Session session = Session.getDefaultInstance(properties);

		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

			// fetches new messages from server
			Message[] arrayMessages = folderInbox.getMessages();

			for (int i = 0; i < arrayMessages.length; i++) {
				Message message = arrayMessages[i];
				message.setFlag(Flags.Flag.DELETED, true);

			}

			// expunges the folder to remove messages which are marked deleted
			boolean expunge = true;
			folderInbox.close(expunge);

			// another way:
			//folderInbox.expunge();
			//folderInbox.close(false);

			// disconnect
			store.close();
		} catch (NoSuchProviderException ex) {
			//System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			//System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}
	}

	/**
	 * This method gets the current time of specified time zone
	 * @param currentTimeZone
	 * @return current time
	 */
	public String getCurrentTime(String currentTimeZone) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone(currentTimeZone));
		return "" + ((now.get(Calendar.HOUR) == 0 && now.get(Calendar.AM_PM) == Calendar.PM) ? 12 : now.get(Calendar.HOUR)) + ":" + String.format("%02d", now.get(Calendar.MINUTE));
	}

	/**
	 * This method gets adds n minutes to the current specified time zone
	 * @param minutesToAdd
	 * @param currentTimeZone
	 * @return current time in HH:MM AM/PM format
	 */
	public String getCurrentTimePlusN(String currentTimeZone, int minutesToAdd) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone(currentTimeZone));
		System.out.println(now);
		now.add(Calendar.MINUTE, minutesToAdd);
		return "" + ((now.get(Calendar.HOUR) == 0 && now.get(Calendar.AM_PM) == Calendar.PM) ? 12 : (now.get(Calendar.HOUR))
				+ ":" + String.format("%02d", now.get(Calendar.MINUTE)) + ((now.get(Calendar.AM_PM) == 0) ? "AM" : "PM"));
	}

	public String readLatestEmail(String host, String port,
								  String userName, String password, String subjectEmail,
								  int emailPollTime, int emailPollStep) throws Exception {
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
				String.valueOf(port));

		Session session = Session.getDefaultInstance(properties);

		String body = "";

		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			//int emailsCount = folderInbox.getMessageCount();
			folderInbox.open(Folder.READ_ONLY);

			// fetches latest Snaplogic message from server by polling every (emailPollStep) seconds
			for (int i = 0; i < emailPollTime/emailPollStep; i++) {
				//IsTestBase.pause(emailPollStep); //waiting for email to be sent and received
				Waiting.staticWait(emailPollStep);
				Message[] arrayMessages = folderInbox.getMessages();
				Message message = arrayMessages[0]; //picking up the latest email
				String subject = message.getSubject();
				if (subject.contains(subjectEmail)) {
					body = message.getContent().toString();
					break;
				}
			}
			//If email with required subject is not found OR is blank after polling then exception is thrown
			if (body == "") {
				throw new WyneException("Could not find email OR email is blank");
			}

			// disconnect
			store.close();


		} catch (NoSuchProviderException ex) {
			//System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			//System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}
		return body;
	}

	/** Method that returns data (URL, username and password) from Welcome Email
	 * needs email body and needs to know whether it is an Admin or Basic User Welcome email
	 * this data is useful for assertion purposes.
	 @author aomarov */
	public JSONObject welcomeEmailData(String body, boolean isAdmin) throws JSONException {

		String url = "";
		String userName = "";
		String password = "";
		String urlPattern = "";
		String userNamePattern="";
		String passwordPattern="";

		if (isAdmin) {
			urlPattern = "SnapLogic Designer:.*href=\"(.*?)\"";
			userNamePattern = "Username: <b>(.*?)<";
			passwordPattern = "Password: <b>(.*?)<";
		}
		else {
			urlPattern = "visit:.*href=\"(.*?)\"";
			userNamePattern = "Your login is: (.*?).<";
			passwordPattern = "Your password is: (.*?)<";
		}


		Pattern patternUrl = Pattern.compile(urlPattern);
		Pattern patternUserName = Pattern.compile(userNamePattern);
		Pattern patternPassword = Pattern.compile(passwordPattern);

		Matcher matcherUrl = patternUrl.matcher(body);
		if (matcherUrl.find())
		{
			url = matcherUrl.group(1);
		}

		Matcher matcherUserName = patternUserName.matcher(body);
		if (matcherUserName.find())
		{
			userName = matcherUserName.group(1);
		}

		Matcher matcherPassword = patternPassword.matcher(body);
		if (matcherPassword.find())
		{
			password = matcherPassword.group(1);
		}

		JSONObject obj = new JSONObject();

		obj.put("URL", url);
		obj.put("Username", userName);
		obj.put("Password", password);

		return obj;
	}

	public String resetPasswordEmailData(String baseUrl, String body) throws Exception {

		String resetPasswordCode = "";
		String userEmail = "";
		String urlPattern = "";
		String emailPattern = "";

		urlPattern = "password&code=(.*?)&email";
		emailPattern = "&email=(.*?)\"";

		Pattern patternUrl = Pattern.compile(urlPattern);
		Matcher matcherUrl = patternUrl.matcher(body);
		if (matcherUrl.find())
		{
			resetPasswordCode = matcherUrl.group(1);
		}

		Pattern patternEmail = Pattern.compile(emailPattern);
		Matcher matcherEmail = patternEmail.matcher(body);
		if (matcherEmail.find())
		{
			userEmail = matcherEmail.group(1);

		}

		String resetUrl = baseUrl + "/sl/login.html?recover=password&code=" + resetPasswordCode + "&email=" + userEmail;

		return resetUrl;
	}


	public static String getHexColorValueFromrgba(String color)
	{

		String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");

		int hexValue1=Integer.parseInt(hexValue[0]);
		hexValue[1] = hexValue[1].trim();
		int hexValue2=Integer.parseInt(hexValue[1]);
		hexValue[2] = hexValue[2].trim();
		int hexValue3=Integer.parseInt(hexValue[2]);
		String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
		return actualColor;

	}

	public String getDateInFormat(Date date, String format){
		String dateInString = null;
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateInString = dateFormat.format(date);
		return dateInString;
	}

	public String removeSpecialCharacters(String str) {
		//str = str.split(System.getProperty("line.separator"))[0];
		str = str.replaceAll("[^a-zA-Z]+", "");
		return str;
	}

	public boolean comparePartialString(String actualString, List<String> expectedList){
		for(String str : expectedList){
			if(actualString.toLowerCase().contains(str.toLowerCase())){
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used to run a job in jenkins
	 *
	 * @param jobName //job name in jenkins
	 * @param userName //username to run the job with
	 * @param userToken //Token of the above user can be found in jenkins user profile
	 * @param parametersMap // parameters for the job need to be properly escaped
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String runJenkinsJob(String jenkinsUrl, String jobName, Map<String,String> parametersMap,String userName,String userToken) throws Exception {


		/*if (jenkinsUrl.contains("172.27.179.220")){
			userName = "skdarshanam";
			userToken = "115f4f84c1c547bc963294143b6df37235";
		}*/

		JsonObject jsonObject = getJenkinsConfig(jenkinsUrl);
		if (jsonObject.has("userName"))
			userName = jsonObject.get("userName").getAsString();
		if (jsonObject.has("userToken"))
			userToken = jsonObject.get("userToken").getAsString();

		String authString = userName + ":" + userToken;
		//LOGGER.info("auth string: " + authString);
		//byte[] authEncBytes = org.apache.commons.codec.binary.Base64.encodeBase64(authString.getBytes());
		//String authStringEnc = new String(authEncBytes);
		//LOGGER.info(Thread.currentThread().getName() + " --Base64 encoded auth string: " + authStringEnc);
		LOGGER.info("Jenkins job details:");
		LOGGER.info(parametersMap.toString());


		String url = jenkinsUrl+"job/"+jobName+"/buildWithParameters";
		String contentType = "application/x-www-form-urlencoded";

		StringEntity se = new StringEntity(parametersMap.toString());

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		HttpEntity httpEntity = new UrlEncodedFormEntity(params);
		CloseableHttpResponse response = (CloseableHttpResponse) postHttpClient(url, userName, userToken, httpEntity);

		if (response.getStatusLine().getStatusCode()!=200 && response.getStatusLine().getStatusCode()!=201){
			LOGGER.info("response body"+EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
			throw new WyneException("Jenkins job failed : " + response.getStatusLine().getStatusCode());
		}

		Header[] headers = response.getAllHeaders();
		String queueURL = "";
		for (Header header: headers) {
			//System.out.println("Key [ " + header.getName() + "], Value[ " + header.getValue() + " ]");
			if (header.getName().equals("Location")) {
				queueURL = header.getValue();
				break;
			}
		}
		JsonParserUtility jsonParserUtility = new JsonParserUtility();

		String buildNumber;
		while(true) {

			//Response res2 = given().header("Authorization", "Basic " + authStringEnc).get(queueURL + "api/json");
			CloseableHttpResponse getcallResponse = (CloseableHttpResponse) getHttpClient(queueURL + "api/json", userName, userToken);
			try {
				String respBody = EntityUtils.toString(getcallResponse.getEntity(), StandardCharsets.UTF_8);
				ReadContext ctx1 = JsonPath.parse(respBody);
				buildNumber = ctx1.read("$.executable.number").toString();
				break;
			}catch (PathNotFoundException pe){
				continue;
			}
		}

		//log the job build location link.
		if(!buildNumber.isEmpty()){
			LOGGER.info("Job build link : "+ jenkinsUrl + "job/"+jobName+"/"+buildNumber);
		}

		Object result;
		while(true){
			//Response res1 = given().header("Authorization", "Basic " + authStringEnc).get(jenkinsUrl + "job/"+jobName+"/"+buildNumber+"/api/json");
			//result = jsonParserUtility.getJsonElement(res1,"result");
			CloseableHttpResponse response2 = (CloseableHttpResponse) getHttpClient(jenkinsUrl + "job/"+jobName+"/"+buildNumber+"/api/json", userName, userToken);
			String respBody = EntityUtils.toString(response2.getEntity(), StandardCharsets.UTF_8);
			result = jsonParserUtility.getJsonElement(jsonParserUtility.convertStringtoJson(respBody), "result");
			if(result != null){
				break;
			}
		}

		return result.toString();

	}


	/**
	 * This method is used to run a job in jenkins
	 *
	 * @param jobName //job name in jenkins
	 * @param parametersMap // parameters for the job need to be properly escaped
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void runJenkinsJob(String jenkinsUrl, String jobName, Map<String,String> parametersMap) throws Exception {

		//System.out.println(Thread.currentThread().getName() + " jobName: "+jobName);

		/*if (jenkinsUrl.contains("172.27.179.220")){
			userName = "skdarshanam";
			userToken = "115f4f84c1c547bc963294143b6df37235";
		}*/
		String userName="",userToken="";

		JsonObject jsonObject = getJenkinsConfig(jenkinsUrl);
		if (jsonObject.has("userName"))
			userName = jsonObject.get("userName").getAsString();
		if (jsonObject.has("userToken"))
			userToken = jsonObject.get("userToken").getAsString();

		String authString = userName + ":" + userToken;
		//LOGGER.info("auth string: " + authString);
		//byte[] authEncBytes = org.apache.commons.codec.binary.Base64.encodeBase64(authString.getBytes());
		//String authStringEnc = new String(authEncBytes);
		//LOGGER.info(Thread.currentThread().getName() + " --Base64 encoded auth string: " + authStringEnc);


		// New client
		//CloseableHttpClient client = HttpClients.createDefault();
		String url = jenkinsUrl+"job/"+jobName+"/buildWithParameters";

		LOGGER.info("Jenkins job details:");
		LOGGER.info(parametersMap.toString());

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
			LOGGER.info(entry.getKey() + ":" + entry.getValue());
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		HttpEntity httpEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
		CloseableHttpResponse response = (CloseableHttpResponse) postHttpClient(url, userName, userToken, httpEntity);

		if (response.getStatusLine().getStatusCode()!=200 && response.getStatusLine().getStatusCode()!=201){
			LOGGER.info("response body"+EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
			throw new WyneException("Jenkins job failed : " + response.getStatusLine().getStatusCode());
		}

		Header[] headers = response.getAllHeaders();
		String queueURL = "";
		for (Header header: headers) {
			//System.out.println("Key [ " + header.getName() + "], Value[ " + header.getValue() + " ]");
			 if (header.getName().equals("Location")) {
				 queueURL = header.getValue();
				 break;
			 }
		}

		String buildNumber;
		while(true) {

			//Response res2 = given().header("Authorization", "Basic " + authStringEnc).get(queueURL + "api/json");
			CloseableHttpResponse getcallResponse = (CloseableHttpResponse) getHttpClient(queueURL + "api/json", userName, userToken);
			try {
				String respBody = EntityUtils.toString(getcallResponse.getEntity(), StandardCharsets.UTF_8);
				//buildNumber = jsonParserUtility.getJsonElement(jsonParserUtility.convertStringtoJson(respBody), "$.executable.number").toString();
				ReadContext ctx1 = JsonPath.parse(respBody);
				buildNumber = ctx1.read("$.executable.number").toString();
				break;
			}catch (PathNotFoundException pe){
				continue;
			}
		}

		//log the job build location link.
		if(!buildNumber.isEmpty()){
			LOGGER.info("Job build link : "+ jenkinsUrl + "job/"+jobName+"/"+buildNumber);
		}

	}

	/**
	 * This method is used to run a job in jenkins in Async mode
	 *
	 * @param jobName //job name in jenkins
	 * @param userName //username to run the job with
	 * @param userToken //Token of the above user can be found in jenkins user profile
	 * @param parametersMap // parameters for the job need to be properly escaped
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void runJenkinsJobAsync(String jenkinsUrl, String jobName, Map<String,String> parametersMap, String userName, String userToken) throws Exception {

		JsonObject jsonObject = getJenkinsConfig(jenkinsUrl);
		if (jsonObject.has("userName"))
			userName = jsonObject.get("userName").getAsString();
		if (jsonObject.has("userToken"))
			userToken = jsonObject.get("userToken").getAsString();

		String authString = userName + ":" + userToken;
		LOGGER.info("auth string: " + authString);
		byte[] authEncBytes = org.apache.commons.codec.binary.Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		LOGGER.info("Jenkins job details:");
		LOGGER.info(parametersMap.toString());



		String uri = jenkinsUrl+"job/"+jobName+"/buildWithParameters";
		Map<String,String> headersMap = new HashMap<>();
		headersMap.put("Authorization","Basic " + authStringEnc);
		headersMap.put("Content-Type","application/x-www-form-urlencoded");

		AsyncHttpRequest.postParams(uri, parametersMap, headersMap);


	}

	/***
	 *
	 * @param url
	 * @return
	 * @throws FileNotFoundException
	 */
	public JsonObject getJenkinsConfig(String url) throws FileNotFoundException {
		String resourcespath = "resources/";
		JsonParserUtility jsonParserUtility = new JsonParserUtility();

		InputStream in = getClass().getResourceAsStream("/JenkinsConfig.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String fileContents = reader.lines().collect(Collectors.joining(System.lineSeparator()));
		try {
			if (reader!=null)
				reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JsonObject jsonObject = jsonParserUtility.convertStringtoJson(fileContents);
		JsonObject jsonObject1 = new JsonObject();
		System.out.println(jsonObject);
		Set<Map.Entry<String, JsonElement>> keySet = jsonObject.entrySet();


		Iterator<Map.Entry<String, JsonElement>> it = keySet.iterator();

		while(it.hasNext()){
			Map.Entry<String, JsonElement> val = it.next();

			if  (url.contains(val.getKey())) {
				jsonObject1 = val.getValue().getAsJsonObject();
				break;
			}
		}
		return jsonObject1;
	}


	/***
	 * This method is used to create a file with given content
	 *
	 * @param fileContent
	 * @param fileName
	 * @param folderPath - Default folder path is /src/test/resources as we may consider to uploadFile method from SLRestServices
	 * @param fileExtension
	 * @return
	 * @throws Exception
	 */
	public String createFile(String fileContent, String folderPath, String fileName, String fileExtension, boolean overwriteFile) throws IOException {
		String fullFileName = fileName+"."+fileExtension;
		String fullPath = null != folderPath ? "src/test/resources"+"/"+folderPath : "src/test/resources";
		File file = new File(fullPath+ "/" +fullFileName);
		//Create the file
		if (file.createNewFile()){
			LOGGER.info("File created successfully : "+ fullFileName);

		}else{
			LOGGER.info("File already exists and not deleting the file : "+ fullFileName);
		}

		//Write Content
		FileWriter writer = new FileWriter(file, !overwriteFile);  //true to append false to overwrite
		writer.write(fileContent);
		writer.close();
		return folderPath+ "/" +fullFileName;
	}

	/***
	 * This method is used to delete the created temporary file
	 * @param filepath
	 */
	public void deleteFile(String filepath) {
		String fullPath = "src/test/resources/"+filepath;
		File file = new File(fullPath);
		if(file.exists()){
			file.delete();
		}
	}

	public static String getHostName(){
		InetAddress ip;
		String hostname = "";
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
			System.out.println(Inet4Address.getLocalHost().getHostAddress());
			//System.out.println("Your current IP address : " + ip);
			System.out.println("Your current Hostname : " + hostname);

		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		return hostname;
	}



	public static void main(String args[]) throws Exception {

		Map<String,String> map = new HashMap<>();
		map.put("testsuite","");
		map.put("enableTracker","false");
		map.put("generateTestPassName","false");

		//new  Utils().runJenkinsJob("selenium_test_Chaitanya",map, Accounts_Credentials.get(Accounts_Credentials.JENKINSUSER),Accounts_Credentials.get(Accounts_Credentials.JENKINSUSERTOKEN));
	}

	public static String decrypt(String strToDecrypt)
	{
		try
		{
			String secret = ConfigManager.getBundle().getProperty("application.key").toString();
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		}
		catch (Exception e)
		{
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	private static SecretKeySpec secretKey;
	private static byte[] key;
	public static void setKey(String myKey)
	{
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(String strToEncrypt)
	{
		try
		{
			String secret = ConfigManager.getBundle().getProperty("application.key").toString();
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		}
		catch (Exception e)
		{
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;

	}

	/***
	 * New method to use httpclient apache by ignoring SSL certs validation
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public HttpResponse postHttpClient(String url, String userName, String password, HttpEntity httpEntity) throws
			KeyStoreException, NoSuchAlgorithmException, KeyManagementException, AuthenticationException, IOException {

			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					builder.build(), NoopHostnameVerifier.INSTANCE);
			CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(
					sslsf).build();

			// New client
			//CloseableHttpClient client = HttpClients.createDefault();
			//url = jenkinsUrl+"job/"+jobName+"/buildWithParameters";
			HttpPost httpPost = new HttpPost(url);
			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName,password);
			httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
			//httpPost.addHeader("Content-type","application/x-www-form-urlencoded");

			//StringEntity se = new StringEntity(params);
			httpPost.setEntity(httpEntity);
			CloseableHttpResponse response = client.execute(httpPost);
			return response;
	}

	/***
	 * New method to use httpclient apache by ignoring SSL certs validation
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws AuthenticationException
	 * @throws IOException
	 */
	public HttpResponse getHttpClient(String url, String userName, String password) throws
			KeyStoreException, NoSuchAlgorithmException, KeyManagementException, AuthenticationException, IOException {

		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				builder.build(), NoopHostnameVerifier.INSTANCE);
		CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(
				sslsf).build();

		// New client
		//CloseableHttpClient client = HttpClients.createDefault();
		//url = jenkinsUrl+"job/"+jobName+"/buildWithParameters";
		HttpGet httpget = new HttpGet(url);
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName, password);
		httpget.addHeader(new BasicScheme().authenticate(creds, httpget, null));

		CloseableHttpResponse response = client.execute(httpget);
		return response;
	}
	/**
	 * @desc - get previous date by n days in format specified
	 * @param numberofpreviousdays
	 * @param dateFormat
	 * @return
	 */
	public static String getCalendarPreviousDate(int numberofpreviousdays, String dateFormat ) {
		DateTimeFormatter formmat1 = null;
		LocalDateTime ldt = null;
		String formatter = null;
		if(numberofpreviousdays == 0){
			ldt = LocalDateTime.now();
		}else {
			ldt = LocalDateTime.now().minusDays(numberofpreviousdays+1);
		}
		formmat1 = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);
		formatter = formmat1.format(ldt);
		System.out.println(formatter);
		return formatter;
	}

	/**
	 * @desc - returns utc time in given format
	 * @param format
	 * @return
	 */
	public static String getUTCTime(String format){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format); //"yyyy-MM-dd'T'HH:mm:ss"
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}

	/**
	 * Method will delete given file in download path.
	 *
	 * @param fileNameWithType file name with file type.
	 * @throws WyneException
	 */
	public void clearDownloadFile(String fileNameWithType) {
		try {
			String fileName = fileNameWithType.substring(0, fileNameWithType.lastIndexOf("."));
			String fileType = fileNameWithType.substring(fileNameWithType.lastIndexOf(".") + 1);
			File dir = new File(downloadPath);
			FileFilter fileFilter = new WildcardFileFilter(fileName + "*." + fileType);
			File[] files = dir.listFiles(fileFilter);
			for (File fileObj : files)
				FileUtils.forceDelete(fileObj);
		} catch (Exception io) {
			throw new WyneException("Unable to delete files in path : [" + downloadPath + "]");
		}
	}

	/**
	 * Method will return true if given file exists in download path. Else it returns false.
	 *
	 * @param fileNameWithType file name with file type.
	 * @return boolean value.
	 */
	public boolean isFileDownloaded(String fileNameWithType) {
		File dir = new File(downloadPath);
		FileFilter fileFilter = new WildcardFileFilter(fileNameWithType);
		File[] files = dir.listFiles(fileFilter);
		return files[0].exists();
	}

	public boolean isSpecFileFileExists(String fileNameWithType, String downloadPath) {
		File dir = new File(downloadPath);
		FileFilter fileFilter = new WildcardFileFilter(fileNameWithType);
		File[] files = dir.listFiles(fileFilter);
		return files[0].exists();
	}

	/**
	 * @Desc - Allows to Unzip spec file
	 * @param source
	 * @param destination
	 */
	public static void unzip(String source, String destination) throws Exception{
		try {
			net.lingala.zip4j.core.ZipFile zipFile = new ZipFile(source);
			zipFile.extractAll(destination);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}


	public String getPreviousDateFromCurrentDate(int numberOfDays){
		Calendar cal = Calendar.getInstance();
		//String currentDate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
		String previousDayDate= LocalDateTime.now().minusDays(numberOfDays).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
		return previousDayDate;
	}

	public String addHoursToCurrentDate(int hours){
		Calendar cal = Calendar.getInstance();
		String hoursToBeAdded = LocalDateTime.now().plusHours(hours).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
		return hoursToBeAdded;
	}

	public String minusHoursToCurrentDate(int hours){
		Calendar cal = Calendar.getInstance();
		String hoursToBeSubtracted = LocalDateTime.now().minusHours(hours).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
		return hoursToBeSubtracted;
	}
}

