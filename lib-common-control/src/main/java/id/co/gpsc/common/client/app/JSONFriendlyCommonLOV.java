package id.co.gpsc.common.client.app;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import id.co.gpsc.common.client.JSONFriendlyObject;
import id.co.gpsc.common.client.util.JSONUtilities;
import id.co.gpsc.common.data.lov.CommonLOV;

public class JSONFriendlyCommonLOV extends CommonLOV implements JSONFriendlyObject<JSONFriendlyCommonLOV>{
	
	public JSONFriendlyCommonLOV(){
		super();
	}
	
	
	public JSONFriendlyCommonLOV(CommonLOV sampleData){
		super(sampleData);
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -269137163800533795L;
	@Override
	public JSONValue translateToJSON() {
		JSONObject a = new JSONObject();
		if ( this.dataValue !=null)
			a.put("value", new JSONString(dataValue)); 
		if ( this.label != null)
			a.put("label", new JSONString(label));
		if ( this.additionalData1!=null)
			a.put("additionalData1", new JSONString(additionalData1)); 
		else
			a.put("additionalData1", JSONNull.getInstance());
		
		if ( this.additionalData2!=null)
			a.put("additionalData2", new JSONString(additionalData2)); 
		else
			a.put("additionalData2", JSONNull.getInstance());
		if ( this.parentId!=null)
			a.put("parentId",  new JSONString(parentId));
		else
			a.put("parentId",  JSONNull.getInstance());
		return a;
	}
	@Override
	public JSONFriendlyCommonLOV instantiateFromJSON(JSONValue jsonValueRef) {
		if ( jsonValueRef==null)
			return null ; 
		JSONObject swap =  jsonValueRef.isObject(); 
		if ( swap==null)
			return null ; 
		
		JSONFriendlyCommonLOV param = new JSONFriendlyCommonLOV();
		param.setDataValue(JSONUtilities.getInstance().getString(jsonValueRef, "value")) ;
		param.setLabel(JSONUtilities.getInstance().getString(jsonValueRef, "label")) ;
		param.setAdditionalData1(JSONUtilities.getInstance().getString(jsonValueRef, "additionalData1")) ;
		param.setAdditionalData2(JSONUtilities.getInstance().getString(jsonValueRef, "additionalData2")) ;
		param.setParentId(JSONUtilities.getInstance().getString(jsonValueRef, "parentId"));
		return param;
	}
	

}
