package id.co.gpsc.common.client.util;

import id.co.gpsc.common.data.app.security.ClientSecurityData;



/**
 * client data security instance
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public final class ClientSecurityUtils {
	
	private static ClientSecurityUtils instance ; 
	
	
	private ClientSecurityData clientSecurityData ;  
	
	
	public static ClientSecurityUtils getInstance() {
		if ( instance==null)
			instance = new ClientSecurityUtils();
		return instance;
	}
	
	
	/**
	 * current client security data
	 **/
	public ClientSecurityData getClientSecurityData() {
		return clientSecurityData;
	}
	/**
	 * current client security data
	 **/
	public void setClientSecurityData(ClientSecurityData clientSecurityData) {
		this.clientSecurityData = clientSecurityData;
	}

}
