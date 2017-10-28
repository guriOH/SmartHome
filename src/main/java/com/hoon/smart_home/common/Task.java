package com.hoon.smart_home.common;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Task {

	private final String PRJNAME="SMARTHOME";
	private final String REQEUST="REQEUST";
	private final String RESPONCE="RESPONCE";
	
	private JSONObject jsonobject = null;
	private JSONArray responseArray = null;
	private JSONArray requestArray = null;
	private JSONObject responseObject = null;
	private JSONObject requestObject =null;
	public Task(){
		jsonobject = new JSONObject();
		responseObject = new JSONObject();
		requestObject = new JSONObject();
		responseArray = new JSONArray();
		requestArray = new JSONArray();
	}
	
	public void makeResponce(){
		
	}
	
	
	public void makeRequest(){
		requestObject.put(REQEUST, "request");
		JSONObject subrequest = new JSONObject();
		subrequest.put("Test", "Test");
		subrequest.put("Test1", "Test");
		subrequest.put("Test2", "Test");
		subrequest.put("Test3", "Test");
		requestObject.put("SubRequest", subrequest);
		requestArray.add(requestObject);
		
		jsonobject.put(REQEUST, requestObject);
		String jsonInfo = jsonobject.toJSONString();
		 
        System.out.print(jsonInfo);
	}
	
	public static void main(String[] args){
		Task task = new Task();
		
		task.makeRequest();
	}
	
}
