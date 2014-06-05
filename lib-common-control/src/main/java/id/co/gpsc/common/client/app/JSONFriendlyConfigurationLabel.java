package id.co.gpsc.common.client.app;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;





import id.co.gpsc.common.client.JSONFriendlyObject;
import id.co.gpsc.common.client.util.JSONUtilities;
import id.co.gpsc.common.data.app.ConfigurationLabel;

public class JSONFriendlyConfigurationLabel extends ConfigurationLabel implements JSONFriendlyObject<JSONFriendlyConfigurationLabel>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4955677113143804098L;
	
	
	public JSONFriendlyConfigurationLabel(){
		super();
	}
	
	
	public JSONFriendlyConfigurationLabel(String key, String label){
		super(key , label);
	}
	
	
	public JSONFriendlyConfigurationLabel(ConfigurationLabel sample){
		super(sample);
	}
	@Override
	public JSONValue translateToJSON() {
		JSONObject retval = new JSONObject(); 
		JSONUtilities.getInstance().put(retval, "key", key);
		JSONUtilities.getInstance().put(retval, "label", label);
		return retval;
	}
	@Override
	public JSONFriendlyConfigurationLabel instantiateFromJSON(JSONValue jsonValueRef) {
		JSONFriendlyConfigurationLabel retval = new JSONFriendlyConfigurationLabel(); 
		
		retval.setKey( JSONUtilities.getInstance().getString(jsonValueRef, "key"));
		retval.setLabel(JSONUtilities.getInstance().getString(jsonValueRef, "label"));
		return retval;
	}

}
