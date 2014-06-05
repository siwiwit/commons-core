package id.co.gpsc.common.client.control.i18;

import id.co.gpsc.common.client.cache.BaseActualClientCacheWorker;
import id.co.gpsc.common.client.cache.LocalStorageActualClientCacheWorker;





/**
 * default local storage
 **/
public class DefaultAppPanelResourceCacheManagerImpl extends BaseAppPanelResourceCacheManagerImpl{
	
	private BaseActualClientCacheWorker  baseActualClientCacheWorker = new LocalStorageActualClientCacheWorker(); 
	
	
	@Override
	public BaseActualClientCacheWorker getActualClientCacheWorker() {
		
		return baseActualClientCacheWorker;
	}

}
