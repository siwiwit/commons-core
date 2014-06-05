package id.co.gpsc.common.client;

import com.google.gwt.json.client.JSONValue;

/**
 * object yang bisa <i>self</i> transform ke dalam json. mohon hati- hati dalam memilih object yang di desain json friendly. 
 * lebih baik pada object-object yang simple saja(tidak nested reference dengan object lainnya)
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a> 
 **/
public interface JSONFriendlyObject<T> {
	/**
	 * konvert object ke dalam json
	 **/
	public JSONValue translateToJSON(); 
	
	
	
	/**
	 * instantiate data dengan json data
	 **/
	public T instantiateFromJSON(JSONValue jsonValueRef); 

}
