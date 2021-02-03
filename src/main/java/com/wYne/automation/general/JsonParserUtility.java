package com.wYne.automation.general;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.*;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.wYne.automation.exceptions.WyneException;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <p/>
 * This Class is used for all JSON helper methods which can be reused
 */

public class JsonParserUtility {

	private static final Logger LOGGER = Logger.getLogger(JsonParserUtility.class);

	/**
	 * This method is used to read a file with given filePath
	 *
	 * @param filePath
	 * @return Simple JsonObject
	 * @throws FileNotFoundException
	 * @throws WyneException
	 * @throws FileNotFoundException
	 * @throws WyneException
	 */
	public JsonObject readFileAsJson(String filePath) throws FileNotFoundException, WyneException {

		LOGGER.debug("==> Entered into readFileAsJson method");

		JsonObject jsonFileObj = null;
		JsonParser parser = new JsonParser();

		if (null != filePath && !filePath.isEmpty()) {
			FileReader fileReader = new FileReader(filePath);
			jsonFileObj = (JsonObject) parser.parse(fileReader);
		} else {
			throw new WyneException("Unable to find the filePath or filePath as null :" + filePath);
		}

		LOGGER.debug("<== Entered into readFileAsJson method");
		return jsonFileObj;
	}

	/***
	 * This method is used to read a file with given inputstream
	 * @param input
	 * @return
	 * @throws FileNotFoundException
	 * @throws WyneException
	 */
	public JsonObject readFileAsJson( InputStream input) throws FileNotFoundException, WyneException {

		LOGGER.debug("==> Entered into readFileAsJson method");

		JsonObject jsonObject = null;
		JsonElement element = new JsonParser().parse(
				new InputStreamReader(input)
		);
		jsonObject = element.getAsJsonObject();

		return jsonObject;
	}

	/**
	 * This method is used to return the jsonElement of given JSON path
	 *
	 * @param jsonResponse
	 * @param jsonPath
	 * @return String Element
	 */
	public Object getJsonElement(Response jsonResponse, String jsonPath) throws Exception {
		LOGGER.debug("==> Entered into getJsonElement method");
		String strJson = jsonResponse.getBody().asString();
		LOGGER.debug("<== Entered into getJsonElement method");
		return JsonPath.read(strJson, jsonPath);
	}

	/**
	 * This method is used to return the jsonElement of given JSON path using JSONObject
	 *
	 * @param jsonObject
	 * @param jsonPath
	 * @return String Element
	 */
	public String getJsonElement(JsonObject jsonObject, String jsonPath) throws Exception {

		LOGGER.debug("==> Entered into getJsonElement method");

		//Converting the JSonObject to JsonString
		//To Eliminate the PathNotFoundException
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonString = gson.toJson(jsonObject);

		JsonPath compiledPath = JsonPath.compile(jsonPath);
		String jsonElement = compiledPath.read(jsonString);
		LOGGER.debug("<== Entered into getJsonElement method");
		return jsonElement;

	}

	/**
	 * This method is used to return the jsonElement of given JSON path using JSONObject
	 *
	 * @param jsonObject
	 * @param jsonPath
	 * @return String Element
	 */
	public List<String> getJsonElements(JsonObject jsonObject, String jsonPath) throws Exception {

		LOGGER.debug("==> Entered into getJsonElement method");

		//Converting the JSonObject to JsonString
		//To Eliminate the PathNotFoundException
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonString = gson.toJson(jsonObject);

		JsonPath compiledPath = JsonPath.compile(jsonPath);
		LOGGER.debug("<== Entered into getJsonElement method");
		return compiledPath.read(jsonString);
	}

	/**
	 * This method is used to return the jsonElement of given JSON path using JSONObject
	 *
	 * @param jsonResponse
	 * @param jsonPath
	 * @return String Element
	 */
	public List<String> getJsonElements(Response jsonResponse, String jsonPath) throws Exception {

		LOGGER.debug("==> Entered into getJsonElement method");

		String jsonString = jsonResponse.getBody().asString();

		JsonPath compiledPath = JsonPath.compile(jsonPath);
		LOGGER.debug("<== Entered into getJsonElement method");
		return compiledPath.read(jsonString);
	}

	/**
	 * This method is used to return the jsonObject of given JSON path
	 *
	 * @param jsonObject
	 * @param jsonPath
	 * @return String Element
	 */
	public JsonObject getJsonObject(JsonObject jsonObject, String jsonPath) throws Exception {

		LOGGER.debug("==> Entered into getJsonObject method");
		//Converting the JSonObject to JsonString
		//To Eliminate the PathNotFoundException
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonString = gson.toJson(jsonObject);

		JsonObject obtainedJsonObj = null;
		JsonPath compiledPath = JsonPath.compile(jsonPath);
		Object obj = compiledPath.read(jsonString);
		if (obj instanceof JsonObject) {
			obtainedJsonObj = (JsonObject) obj;
		} else {
			throw new WyneException("Expecting a JsonObject but found : " + obj);
		}

		LOGGER.debug("<== Entered into getJsonObject method");
		return obtainedJsonObj;
	}

	/**
	 * This method is used to return the jsonObject of given JSON path
	 *
	 * @param jsonObject
	 * @param jsonPath
	 * @return String Element
	 */
	public JSONArray getJsonArray(JsonObject jsonObject, String jsonPath) throws Exception {

		LOGGER.debug("==> Entered into getJsonObject method");
		//Converting the JSonObject to JsonString
		//To Eliminate the PathNotFoundException
		Gson gson = new GsonBuilder().serializeNulls().create();
		String jsonString = gson.toJson(jsonObject);

		JSONArray obtainedJsonObj = null;
		JsonPath compiledPath = JsonPath.compile(jsonPath);
		Object obj = compiledPath.read(jsonString);
		if (obj instanceof JSONArray) {
			obtainedJsonObj = (JSONArray) obj;
		} else {
			throw new WyneException("Expecting a JSON Array but found : " + obj);
		}

		LOGGER.debug("<== Entered into getJsonObject method");
		return obtainedJsonObj;
	}

	/**
	 * This method is used to return the jsonObject of given JSON path
	 *
	 * @param jsonResponse
	 * @param jsonPath
	 * @return String Element
	 */
	public JsonObject getJsonObject(Response jsonResponse, String jsonPath) throws Exception {

		LOGGER.debug("==> Entered into getJsonObject method");
		String strJson = jsonResponse.getBody().asString();
		Object mapObj = JsonPath.read(strJson, jsonPath);

		//Convert Map to String
		Gson gson = new GsonBuilder().serializeNulls().create();
		String strMapObj = gson.toJson(mapObj);
		JsonObject obtainedJsonObj = convertStringtoJson(strMapObj);

		LOGGER.debug("<== Entered into getJsonObject method");
		return obtainedJsonObj;
	}

	/**
	 * This method is used to convert given string to JSON value
	 *
	 * @param jsonValue
	 * @return
	 * @throws Exception
	 */
	public JsonObject convertStringtoJson(String jsonValue) {

		LOGGER.debug("==> Entered into convertStringtoJson method");

		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(jsonValue).getAsJsonObject();

		LOGGER.debug("<== Entered into convertStringtoJson method");
		return jsonObject;
	}


	/**
	 * This method is used to Update the jsonElement with given Value
	 *
	 * @param jsonObject
	 * @param jsonPath
	 * @param newValue
	 * @return JsonObject Element
	 */
	public JsonObject updateJsonElement(JsonObject jsonObject, String jsonPath, String newValue) throws Exception {

		LOGGER.debug("==> Entered into updateJsonElement method");
		String strJsonObject = jsonObject.toString();

		//Parsing the string to document context to update the value
		DocumentContext documentContext = JsonPath.parse(strJsonObject);
		String newJson = documentContext.set(jsonPath, newValue).jsonString();

		JsonObject updatedJsonObj = convertStringtoJson(newJson);
		LOGGER.debug("<== Entered into updateJsonElement method");
		return updatedJsonObj;

	}

	/**
	 * This method is used to Update the jsonElement with given Value
	 *
	 * @param jsonObject
	 * @param jsonElements
	 * @return JsonObject Element
	 */
	public JsonObject updateMultipleJsonElement(JsonObject jsonObject, Map<String, String> jsonElements) throws Exception {

		LOGGER.debug("==> Entered into updateJsonElement method");
		String strJsonObject = jsonObject.toString();

		//Parsing the string to document context to update the value
		DocumentContext documentContext = JsonPath.parse(strJsonObject);
		for (Map.Entry<String, String> entry : jsonElements.entrySet())
		{
			String jsonPath = entry.getKey();
			String newValue = entry.getValue();
			documentContext.set(jsonPath, newValue);
		}

		JsonObject updatedJsonObj = convertStringtoJson(documentContext.jsonString());
		LOGGER.debug("<== Entered into updateJsonElement method");
		return updatedJsonObj;

	}

	/**
	 * This method is used to Update the account reference for the given all snaps with accounts tab
	 *
	 * @param jsonObject
	 * @param newValue
	 * @return JsonObject Element
	 */
	public JsonObject updateAllAccountReferences(JsonObject jsonObject, String newValue) throws Exception {

		LOGGER.debug("==> Entered into updateJsonElement method");
		String strJsonObject = jsonObject.toString();

		//Read using jackson
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(strJsonObject);
        JsonNode newValueNode = mapper.readTree(newValue);
        List<JsonNode> property_maps = actualObj.path("snap_map").findValues("property_map");
        for(JsonNode node: property_maps){
            if(node.has("account")){
                JsonNode value = node.findPath("account").findPath("account_ref");
                ((ObjectNode) value).replace("value", newValueNode);
            }
        }
        JsonObject updatedJsonObj = convertStringtoJson(mapper.writeValueAsString(actualObj));
		LOGGER.debug("<== Entered into updateJsonElement method");
		return updatedJsonObj;

	}

    /**
     * This method is used to Update the account reference for the given specific snaps
     *
     * @param jsonObject
     * @param snapNames
     * @param newValue
     * @return JsonObject Element
     */
    public JsonObject updateSpecificAccountReferences(JsonObject jsonObject, List snapNames, String newValue) throws Exception {

        LOGGER.debug("==> Entered into updateJsonElement method");
        String strJsonObject = jsonObject.toString();

        //Read using jackson
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(strJsonObject);
        JsonNode newValueNode = mapper.readTree(newValue);
        List<JsonNode> property_maps = actualObj.path("snap_map").findValues("property_map");
        for(JsonNode node: property_maps){
            if(node.has("account") && (snapNames.size() == 0 ||
                    snapNames.contains(node.findPath("debug").findPath("label").findValue("value").asText()))){
                JsonNode value = node.findPath("account").findPath("account_ref");
                ((ObjectNode) value).replace("value", newValueNode);
            }
        }
        LOGGER.debug("<== Entered into updateJsonElement method");
        return jsonObject;

    }

	/**
	 * This method is used to delete the jsonElement with given Value
	 *
	 * @param jsonObject
	 * @param jsonPath
	 * @return JsonObject Element
	 */
	public JsonObject deleteJsonElement(JsonObject jsonObject, String jsonPath) throws Exception {

		LOGGER.debug("==> Entered into deleteJsonElement method");
		String strJsonObject = jsonObject.toString();

		//Parsing the string to document context to update the value
		DocumentContext documentContext = JsonPath.parse(strJsonObject);
		String newJson = documentContext.delete(jsonPath).jsonString();

		JsonObject updatedJsonObj = convertStringtoJson(newJson);
		LOGGER.debug("<== Entered into deleteJsonElement method");
		return updatedJsonObj;

	}

	/***
	 * Utility method to convert the json string to map
	 *
	 * @param jsonData
	 * @return
	 */
	public Map<String, String> getEntitiesAsMap(String jsonData) {
		Map<String, String> entitityMap = new HashMap<String, String>();
		ReadContext ctx = JsonPath.parse(jsonData);
		List<String> entities = ctx.read("$.response_map.entries[*].snode_id");
		for (int i = 0; i < entities.size(); i++) {
			entitityMap.put(ctx.read("$.response_map.entries[" + i + "].snode_id").toString(),
					ctx.read("$.response_map.entries[" + i + "].name").toString());
		}
		return entitityMap;
	}

	/***
	 * Utility method to convert the json string to map. Returns Project Asset map with name as key and asset_id as Value
	 *
	 * @param jsonData
	 * @return
	 */
	public Map<String, String> getAssetIdAssetNameMap(String jsonData) {
		Map<String, String> entitityMap = new HashMap<String, String>();
		ReadContext ctx = JsonPath.parse(jsonData);
		List<String> entities = ctx.read("$.response_map.entries[*].snode_id");
		for (int i = 0; i < entities.size(); i++) {
			entitityMap.put(ctx.read("$.response_map.entries[" + i + "].asset_id").toString(),
					ctx.read("$.response_map.entries[" + i + "].name").toString());
		}
		return entitityMap;
	}
	
	public Map<String, String> getAssetNameAssetTypeMap(String jsonData) {
		Map<String, String> entitityMap = new HashMap<String, String>();
		ReadContext ctx = JsonPath.parse(jsonData);
		List<String> entities = ctx.read("$.response_map.entries[*].snode_id");
		for (int i = 0; i < entities.size(); i++) {
			entitityMap.put(ctx.read("$.response_map.entries[" + i + "].name").toString(),
					ctx.read("$.response_map.entries[" + i + "].asset_type").toString());
		}
		return entitityMap; //assetName is unique. 
	}
	
	/***
	 * Utility method to convert the json string to map
	 *
	 * @param jsonData
	 * @return
	 */
	public Map<String, String> getOrgsMap(String jsonData) {
		Map<String, String> orgMap = new HashMap<String, String>();
		ReadContext ctx = JsonPath.parse(jsonData);
		List<String> entities = ctx.read("$.response_map.org_snodes.*.snode_id");
		for (int i = 0; i < entities.size(); i++) {
			if (!entities.get(i).equals(null)) {
				orgMap.put(ctx.read("$.response_map.org_snodes." + entities.get(i) + ".name").toString(),
						ctx.read("$.response_map.org_snodes." + entities.get(i) + ".snode_id").toString());
			}
		}
		return orgMap;
	}

    /**
     * This method is used to get the account label for the specific account type existing in that project,
     * shared project and shared project space
     * @param jsonData
     * @param fullPath
     * @param accountType
     * @return
     */
	public Map<String, String> getAccountsUsingType(String jsonData, String fullPath, String accountType) {

        Map<String, String> accountDetails = new LinkedHashMap<>();
        String projectSharedPath = null, projectSpaceSharedPath = null;

        String[] pathSplit = fullPath.split("/");

        if(!pathSplit[3].equalsIgnoreCase(CommonConstants.SHARED) && !pathSplit[2].equalsIgnoreCase(CommonConstants.SHARED)) {
            projectSharedPath = "/"+pathSplit[1]+"/"+pathSplit[2]+"/"+CommonConstants.SHARED;
        }

        if(!pathSplit[2].equalsIgnoreCase(CommonConstants.SHARED)) {
            projectSpaceSharedPath = "/"+pathSplit[1]+"/"+CommonConstants.SHARED;
        }

        ReadContext ctx = JsonPath.parse(jsonData);
        List<LinkedHashMap<String, String>> entities = ctx.read("$.response_map.*");

        for(LinkedHashMap<String, String> obj : entities) {

            String pathId = obj.get("path_id");
            String accountTypeEle = obj.get("account_type");
            if ((fullPath.equalsIgnoreCase(pathId) ||
                    (null != projectSharedPath && projectSharedPath.equalsIgnoreCase(pathId)) ||
                    (null != projectSpaceSharedPath && projectSpaceSharedPath.equalsIgnoreCase(pathId)))
                    && accountTypeEle.equalsIgnoreCase(accountType)) {
                accountDetails.put(obj.get("account_label"),pathId);
            }
        }
        return accountDetails;
	}
}
