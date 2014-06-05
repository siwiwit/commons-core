package id.co.gpsc.common.client.lov;

import id.co.gpsc.common.client.app.JSONFriendlyCommonLOVHeader;
import id.co.gpsc.common.client.cache.ClientObjectCacheWrapper;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;




/**
 * Worker untuk menaruh/baca data dari SessionStoraage
 **/
public class LOVCacheManager {

	private static final String CACHE_ID= "LOV_ID_CATALOG"; 
	
	private ArrayList<LOVCacheDefinition> lovCaches = new ArrayList<LOVCacheDefinition>();
	
	private Map<String, LOVCacheDefinition> indexedData = new HashMap<String, LOVCacheDefinition>(); 
	
	
	
	
	private boolean supportHTML5Storage = true; 
	
	
	
	public LOVCacheManager(){
		try {
			supportHTML5Storage  = Storage.isLocalStorageSupported();
		} catch (Exception e) {
			supportHTML5Storage = false; 
		}
		
		
		
	}
	
	
	
	
	/**
	 * tulis data ke dalam storage
	 **/
	private void writeDefinitionToStorage (){
		JSONArray arr = new JSONArray(); 
		if (! lovCaches.isEmpty()){
			int i = 0 ; 
			int indexIncrement = 0 ; 
			for ( LOVCacheDefinition scn : lovCaches){
				JSONValue val =null ; 
				try {
					val = scn.translateToJSON(); 
				} catch (Exception e) {
					continue ; 
				}
				finally{
					indexIncrement++; 
				}
						
				arr.set(i++, val  ); 
			}
		}
		String defOfLOV= arr.toString();
		GWT.log(defOfLOV);
		Storage.getLocalStorageIfSupported().setItem(CACHE_ID, defOfLOV); 
	}
	
	
	
	
	
	/**
	 * cached definition
	 * 
	 **/
	public LOVCacheDefinition getCachedLOVDefinition (LOVSource lovSource, String localizationCode , String id ){
		if ( !supportHTML5Storage)
			return null  ; 
		String key= generateCacheId(id, localizationCode, lovSource) ; // lovSource.toString() + id;
		return this.indexedData.get(key); 
	}
	
	
	/**
	 * load definisi dari dalam storage
	 **/
	public void loadCacheDefinitionFromStorage (){
		if ( !supportHTML5Storage)
			return    ; 
		lovCaches.clear(); 
		indexedData.clear(); 
		String jsonString =  Storage.getLocalStorageIfSupported().getItem(CACHE_ID);
		if ( jsonString==null|| jsonString.length()==0)
			return ; 
		JSONValue val =  JSONParser.parseLenient(jsonString);
		if ( val.isArray()==null){
			// broken data. remove saja
			Storage.getLocalStorageIfSupported().removeItem(CACHE_ID);
			return ; 
		}
		JSONArray arr = val.isArray(); 
		int size = arr.size();
		LOVCacheDefinition def = new LOVCacheDefinition(); 
		for ( int i=0; i<size; i++){
			  
			LOVCacheDefinition instance =  def.instantiateFromJSON(arr.get(i));
			if ( instance==null)
				continue ; 
			lovCaches.add(instance);
			indexedData.put(instance.getCacheId(), instance); 
		}
	}

	
	
	/**
	 * put data ke dalam session stroage cache
	 * @param lovData data yang akan di taruh ke dalam cache
	 **/
	public void submitToCache (  CommonLOVHeader lovData){
		if ( !supportHTML5Storage)
			return   ; 
		if (! lovData.isCacheable()){
			
			//
		}
		else{
			//FIXME: buat cache catalog agar bisa erase dengan cepat
			String key= generateCacheId(lovData.getLovId() , lovData.getI18Key(), lovData.getSource());
			
			 	
			LOVCacheDefinition def = null ; 
			if ( indexedData.containsKey(key)){
				def = indexedData.get(key);
				Date date = new Date();
				def.setCacheTime(date); 
				def.setVersion(lovData.getVersion());
			}
			else{
				def= new LOVCacheDefinition(); 
				def.setCacheId(key); 
				Date date1 = new Date();
				def.setCacheTime(date1);
				def.setVersion(lovData.getVersion());
				indexedData.put(key, def); 
				this.lovCaches.add(def);
			}
			ClientObjectCacheWrapper<JSONFriendlyCommonLOVHeader> dataForCache = new ClientObjectCacheWrapper<JSONFriendlyCommonLOVHeader>(new JSONFriendlyCommonLOVHeader(lovData)); 
			
			Storage storage =Storage.getLocalStorageIfSupported(); 
			storage.setItem(key, dataForCache.generateJSON().toString());
			this.writeDefinitionToStorage();
		}
	}
	
	
	/**
	 * membaca data dari cache
	 * @param id id dari List Of Value
	 * @param  localizationCode kode localization
	 * @param source source data
	 **/
	public ClientObjectCacheWrapper<JSONFriendlyCommonLOVHeader> getDataFromCache (String id,String localizationCode  ,  LOVSource source ){
		if ( !supportHTML5Storage)
			return null  ; 
		String key= generateCacheId(id, localizationCode, source);
		Storage storage =Storage.getLocalStorageIfSupported(); 
		String rawData =   ( storage.getItem(key));
		if ( rawData==null)
			return null; 
		ClientObjectCacheWrapper<JSONFriendlyCommonLOVHeader> sample = new ClientObjectCacheWrapper<JSONFriendlyCommonLOVHeader>();
		sample.readFromString(rawData, new JSONFriendlyCommonLOVHeader()); 
 		return sample ; 
	}
	
	
	
	/**
	 * generate key untuk menaruh data dalam cache lokal
	 **/
	public String generateCacheId (String id,String localizationCode  ,  LOVSource source) {
		return source +"::" +  id + "-" +localizationCode ;
	}
	
	
	
	/**
	 * flag apakah HTML 5 storage di dukung atau tidak
	 */
	public boolean isSupportHTML5Storage() {
		return supportHTML5Storage;
	}
}
