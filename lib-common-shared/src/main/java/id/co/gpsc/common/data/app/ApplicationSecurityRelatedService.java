package id.co.gpsc.common.data.app;

import id.co.gpsc.common.data.app.security.ClientSecurityData;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;




/**
 * rpc untuk security related task
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public interface ApplicationSecurityRelatedService extends JSONSerializedRemoteService{


	
	/**
	 * membaca current user security data
	 **/
	public ClientSecurityData getCurrentUserSecurityData () ; 
	
	
	
}
