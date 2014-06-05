package id.co.gpsc.common.client.app;

import java.util.ArrayList;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import id.co.gpsc.common.client.JSONFriendlyObject;
import id.co.gpsc.common.client.util.JSONUtilities;
import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;

public class JSONFriendlyCommonLOVHeader extends CommonLOVHeader implements JSONFriendlyObject<JSONFriendlyCommonLOVHeader>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6229780892802442378L;


	public JSONFriendlyCommonLOVHeader(){
		super();
	}
	
	public JSONFriendlyCommonLOVHeader(CommonLOVHeader sample){
		super(sample);
	}
	
	
	
	@Override
	public JSONValue translateToJSON() {
		JSONObject a = new JSONObject(); 
		JSONUtilities.getInstance().put(a, "lovId", lovId);
		JSONUtilities.getInstance().put(a, "i18Key", i18Key);
		JSONUtilities.getInstance().put(a, "lovRemark", lovRemark);
		JSONUtilities.getInstance().put(a, "version", version);
		
		JSONUtilities.getInstance().put(a, "cacheable", cacheable);
		JSONUtilities.getInstance().put(a, "source", source.toString());
		
		JSONArray arr = new JSONArray(); 
		if ( details!=null && !details.isEmpty()){
			int i=0;
			for (CommonLOV scn : details ){
				arr.set(i++, (new JSONFriendlyCommonLOV(scn)).translateToJSON() );
			}
		}
		a.put("details", arr);
		return a;
	}
	
	
	@Override
	public JSONFriendlyCommonLOVHeader instantiateFromJSON(JSONValue jsonValueRef) {
		JSONFriendlyCommonLOVHeader retval = new JSONFriendlyCommonLOVHeader(); 
		retval.setLovId(JSONUtilities.getInstance().getString(jsonValueRef, "lovId"));
		retval.setLovRemark(JSONUtilities.getInstance().getString(jsonValueRef, "lovRemark"));
		retval.setVersion(JSONUtilities.getInstance().getString(jsonValueRef, "version"));
		retval.setCacheable(JSONUtilities.getInstance().getBoolean(jsonValueRef, "cacheable"));
		retval.setI18Key(JSONUtilities.getInstance().getString(jsonValueRef, "i18Key"));
		String sourceRaw = JSONUtilities.getInstance().getString(jsonValueRef, "source");
		
		if ( sourceRaw!=null&& sourceRaw.length()>0)
			retval.setSource(LOVSource.directFromLookupTable.instantiateFromString(sourceRaw));
		ArrayList<CommonLOV> dtls = new ArrayList<CommonLOV>();
		retval.setDetails(dtls);
		JSONArray arr =  JSONUtilities.getInstance().getArray(jsonValueRef, "details");
		int ukuran =  arr.size();
		
		JSONFriendlyCommonLOV dtl = new JSONFriendlyCommonLOV();
		for ( int i=0;i< ukuran ; i++){
			dtls.add(dtl.instantiateFromJSON(arr.get(i)));
		}
		return retval;
	}	
	

	
}
