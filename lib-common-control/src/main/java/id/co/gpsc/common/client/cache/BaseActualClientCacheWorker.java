package id.co.gpsc.common.client.cache;

import java.util.Collection;



/**
 * interface untuk client side cache
 **/
public interface BaseActualClientCacheWorker {
	
	
	
	/**
	 * submit data ke dalam cache
	 **/
	public void submitToCache (String key, String dataToCache);
	
	/**
	 * membaca data dari cache
	 **/
	public String get(String key) ;
	
	
	
	/**
	 * hapus data dari cache
	 * @param key key dari data cache yang mau di hapus
	 **/
	public void remove(String key);
	
	
	/**
	 * hapus dengan list of keys
	 **/
	public void removes (Collection<String> keys); 

}
