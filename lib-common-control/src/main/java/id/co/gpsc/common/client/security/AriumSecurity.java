package id.co.gpsc.common.client.security;

/**
 * Entry point module arium security admin
 * @author I Gede Mahendra
 * @since Jan 2, 2013, 2:13:29 PM
 * @version $Id
 */
public class AriumSecurity extends BaseSecurityEntryPoint {

	@Override
	public void onModuleLoad() {						
		setCurrentUserLogin();
	}	
}