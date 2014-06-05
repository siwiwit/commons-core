package id.co.gpsc.common.client.cache;

import id.co.gpsc.common.client.JSONFriendlyObject;

import java.util.Date;




import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;


/**
 * wrapper untuk object yag di cache di lokal browser
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public class ClientObjectCacheWrapper<T extends JSONFriendlyObject<T>> {


	private T objectToCache ;
	/**
	 * waktu object di cache
	 **/
	private Date cacheTime = new Date();
	
	
	
	public ClientObjectCacheWrapper(){}
	
	public ClientObjectCacheWrapper(T theObject){
		this.objectToCache= theObject; 
	}
	
	
	/**
	 * set waktu cache
	 **/
	public void setCacheTime(Date cacheTime) {
		this.cacheTime = cacheTime;
	}
	public Date getCacheTime() {
		return cacheTime;
	}
	
	
	public T getObjectToCache() {
		return objectToCache;
	}
	
	
	/**
	 * translate object ke dalam json
	 **/
	public JSONValue generateJSON(){
		JSONObject retval = new JSONObject();
		
		retval.put("time", new JSONNumber(cacheTime.getTime())); 
		retval.put("data", objectToCache.translateToJSON()); 
		return retval  ;
	}
	
	
	
	
	/**
	 * check apakah cache sudah di anggap expired atau tidak
	 * @param tolerantInSeconds toleransi cache. dalam seconds. lebih   
	 **/
	public boolean checkIsExpired (int tolerantInSeconds){
		//FIXME: masukan real implemntation dari expire ini belum ada perbandingan waktu
		Date skr = new Date(); 
		long selisihMilis = skr.getTime() - cacheTime.getTime(); 
		int selisihSecond = (int )((float) selisihMilis/(float)1000); 
		return selisihSecond>tolerantInSeconds; 
	}
	
	
	/**
	 *  baca dari string dan transform kembali ke dalam represnetasi object yang di cache
	 *  @param  jsonString string json di baca dari cache
	 *  @param sampleObjectForRegenerateData object yang di expected untuk di konstruksi. GWT tidak memungkinkan refleksi. jadinya yang paling mungkin instance object di kirimkan ke dalam method untuk merebuild object dari json value
	 **/
	
	public void readFromString (String jsonString , T sampleObjectForRegenerateData){
		JSONValue val =  JSONParser.parseLenient(jsonString);
		JSONObject obj  =  val.isObject();
		long tglAsLong = (long) obj.get("time").isNumber().doubleValue();
		Date tgl = new Date();
		tgl.setTime(tglAsLong); 
		objectToCache= sampleObjectForRegenerateData.instantiateFromJSON(obj.get("data")); 
	}
	
}
