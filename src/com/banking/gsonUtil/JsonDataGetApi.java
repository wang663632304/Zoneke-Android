package com.banking.gsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDataGetApi extends WebDataGetApi {
	//private String myUrl="http://192.168.20.158:9090/message/get/?id=1&lat=30.5270&lng=114.3550&scope=1";
    

    public JSONObject getObject(String sbj) throws JSONException, Exception {
        //return new JSONObject(getRequest(BASE_URL + EXTENSION + sbj));
    	return new JSONObject(getRequest(sbj));
    }

    public JSONArray getArray(String sbj) throws JSONException, Exception {
        //return new JSONArray(getRequest(BASE_URL + EXTENSION + sbj));
    	return new JSONArray(getRequest(sbj));
    }
}