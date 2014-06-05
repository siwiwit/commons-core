package id.co.gpsc.common.client.lov;



import id.co.gpsc.common.client.JSONFriendlyObject;
import id.co.gpsc.common.client.util.JSONUtilities;

import java.util.Date;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;


/**
 * master definisi cache lov. cache di client(local storage)
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class LOVCacheDefinition  implements JSONFriendlyObject<LOVCacheDefinition>{

	private String cacheId; 
	
	
	private Date cacheTime ;
	
	
	
	/**
	 * versi data
	 **/
	private String version ; 
	/**
	 * hapus cache dari dalam database lokal
	 **/
	public void eraseCache (){
		Storage.getLocalStorageIfSupported().removeItem(cacheId); 
	}
	
	
	
	
	
	
	@Override
	public JSONValue translateToJSON() {
		JSONObject retval = new JSONObject();  
		retval.put("cacheId", new JSONString(  cacheId)); 
		retval.put("cacheTime", new JSONNumber(  cacheTime.getTime())); 
		JSONUtilities.getInstance().put(retval, "version", version) ;
		return retval;
	}
	@Override
	public LOVCacheDefinition instantiateFromJSON(JSONValue jsonValueRef) {
		JSONObject jsObject = jsonValueRef.isObject(); 
		if ( jsObject==null)
			return null ; 
		String strCacheId = JSONUtilities.getInstance().getString(jsObject , "cacheId") ; 
		Double tgl = JSONUtilities.getInstance().getDouble(jsObject , "cacheTime") ;
		
		LOVCacheDefinition retval = new LOVCacheDefinition(); 
		retval.setCacheId(strCacheId);
		retval.setVersion(JSONUtilities.getInstance().getString(jsObject, "version"));
		
		Date tglDateinstance =  null;
		if (tgl!=null){
			tglDateinstance = new Date();
			
			tglDateinstance.setTime(tgl.longValue());
			retval.setCacheTime(tglDateinstance);
		}
		return retval;
	}
	
	
	
	public String getCacheId() {
		return cacheId;
	}
	public void setCacheId(String cacheId) { 
		this.cacheId = cacheId; 
		
	}
	public Date getCacheTime() {
		return cacheTime;
	}
	public void setCacheTime(Date cacheTime) {
		this.cacheTime = cacheTime;
	}





	/**
	 * versi data
	 **/
	public String getVersion() {
		return version;
	}





	/**
	 * versi data
	 **/
	public void setVersion(String version) {
		this.version = version;
	} 
}
