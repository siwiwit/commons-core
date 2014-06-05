package id.co.gpsc.common.client.util;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

import id.co.gpsc.common.util.json.ParsedJSONArrayContainer;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

/**
 * array container untuk json di client
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public class ClientSideParsedJSONArrayContainer implements ParsedJSONArrayContainer{
	
	private JSONArray rawData ; 
	/**
	 * tipe data dalam array
	 **/
	private String dataTypeFQCN ; 
	
	public ClientSideParsedJSONArrayContainer(){
		this.rawData = new JSONArray(); 
	}
	
	public ClientSideParsedJSONArrayContainer( JSONArray rawData ){
		this.rawData = rawData; 
	}
	
	@Override
	public ParsedJSONContainer get(int index) {
		if ( index>= rawData.size())
			return null ; 
		JSONValue rawVal =  rawData.get(index);
		return new ClientParsedJSONContainer(rawVal);
	}

	@Override
	public int length() {
		return rawData.size();
	}
	
	@Override
	public String getAsString(int index) {
		JSONValue val =  rawData.get(index) ;
		if ( val!= null){
			if ( val.isString()!= null)
				return val.isString().stringValue(); 
		}
		return null;
	}

	/**
	 * tipe data dalam array
	 **/
	public void setDataTypeFQCN(String dataTypeFQCN) {
		this.dataTypeFQCN = dataTypeFQCN;
	}
	/**
	 * tipe data dalam array
	 **/
	public String getDataTypeFQCN() {
		return dataTypeFQCN;
	}

	@Override
	public Double getAsNumber(int index) {
		JSONValue swap =  this.rawData.get(index);
		if ( swap== null || JSONNull.getInstance().equals(swap))
			return null ; 
		JSONNumber n  = swap.isNumber(); 
		return n == null ? null : n.doubleValue();
	}

	@Override
	public Boolean getAsBoolean(int index) {
		JSONValue swap =  this.rawData.get(index);
		if ( swap== null || JSONNull.getInstance().equals(swap))
			return null ; 
		JSONBoolean n  = swap.isBoolean(); 
		return n == null ? null : n.booleanValue();
	}

	@Override
	public void appendToArray(ParsedJSONContainer jsonData) {
		ClientParsedJSONContainer c = (ClientParsedJSONContainer)jsonData ; 
		rawData.set(rawData.size()	, c.getJsonVal()); 
	}

	@Override
	public String getJSONString() {
		return this.rawData.toString();
	}
}
