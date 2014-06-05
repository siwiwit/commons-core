package id.co.gpsc.common.client.cache;

import java.util.Collection;

import com.google.gwt.storage.client.Storage;



/**
 * setipe dengan {@link LocalStorageActualClientCacheWorker}, ini storage cache dengan memanfaatkan Session storage. data berlaku untuk 1 session browsing
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class SessionStorageActualClientCacheWorker implements BaseActualClientCacheWorker{

	@Override
	public void submitToCache(String key, String dataToCache) {
		Storage.getSessionStorageIfSupported().setItem(key, dataToCache);
	}

	@Override
	public String get(String key) {
		return Storage.getSessionStorageIfSupported().getItem(key);
	}

	@Override
	public void remove(String key) {
		Storage.getSessionStorageIfSupported().removeItem(key);
	}

	@Override
	public void removes(Collection<String> keys) {
		if ( keys==null||keys.isEmpty())
			return ; 
		for ( String scn : keys){
			Storage.getSessionStorageIfSupported().removeItem(scn);
		}
		
	}

}
