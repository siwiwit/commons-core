package id.co.gpsc.common.client.cache;

import java.util.Collection;

import com.google.gwt.storage.client.Storage;



/**
 * implementasi local cache dengan local storage
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $ID
 **/
public class LocalStorageActualClientCacheWorker implements BaseActualClientCacheWorker{

	@Override
	public void submitToCache(String key, String dataToCache) {
		Storage.getLocalStorageIfSupported().setItem(key, dataToCache);
	}

	@Override
	public String get(String key) {
		return Storage.getLocalStorageIfSupported().getItem(key);
	}

	@Override
	public void remove(String key) {
		Storage.getLocalStorageIfSupported().removeItem(key);
	}

	@Override
	public void removes(Collection<String> keys) {
		if ( keys==null||keys.isEmpty())
			return ; 
		for ( String scn : keys){
			Storage.getLocalStorageIfSupported().removeItem(scn);
		}
		
	}

	

}
