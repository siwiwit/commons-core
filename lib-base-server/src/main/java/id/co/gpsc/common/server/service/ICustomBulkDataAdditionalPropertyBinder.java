package id.co.gpsc.common.server.service;

import java.util.List;

import id.co.gpsc.common.data.app.DualControlEnabledData;

/**
 * binder data tambahan untuk bulk
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface ICustomBulkDataAdditionalPropertyBinder <DATA extends DualControlEnabledData<DATA, ?>>{
	
	/**
	 * bind data tambahan ke dalam data
	 */
	public void bindAdditionalData ( List<DATA> dataToBinds ) ;
	
	
	
	/**
	 * class yang di tanganin data
	 */
	public Class<? extends DATA> getHandledClass () ; 

}
