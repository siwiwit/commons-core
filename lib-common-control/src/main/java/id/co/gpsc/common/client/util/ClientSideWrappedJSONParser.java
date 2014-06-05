package id.co.gpsc.common.client.util;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;

import id.co.gpsc.common.util.json.ParsedJSONArrayContainer;
import id.co.gpsc.common.util.json.ParsedJSONContainer;
import id.co.gpsc.common.util.json.WrappedJSONParser;




/**
 * 
 * wrapper json di sisi client
 **/
public class ClientSideWrappedJSONParser implements WrappedJSONParser{
	
	
	
	private static ClientSideWrappedJSONParser instance ; 
	
	
	
	private ClientSideWrappedJSONParser (){}
	
	
	public static ClientSideWrappedJSONParser getInstance() {
		if (instance ==null)
			instance = new ClientSideWrappedJSONParser(); 
		return instance;
	}

	@Override
	public ParsedJSONContainer parseJSON(String jsonString) throws Exception {
		
		return new ClientParsedJSONContainer(jsonString);
	}


	@Override
	public ParsedJSONContainer createBlankObject() {
		return new ClientParsedJSONContainer();
	}


	@Override
	public ParsedJSONArrayContainer parseJSONArray(String jsonStringArray)
			throws Exception {
		
		if ( jsonStringArray== null|| jsonStringArray.isEmpty())
			return null; 
		JSONArray jsArray =  JSONParser.parseLenient(jsonStringArray).isArray();  
		
		return new ClientSideParsedJSONArrayContainer(jsArray);
	}
	

}
