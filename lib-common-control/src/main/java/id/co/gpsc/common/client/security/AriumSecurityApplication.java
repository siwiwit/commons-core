package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.security.application.ApplicationListPanel;
import id.co.gpsc.common.client.security.applicationuser.ApplicationUserListPanel;
import id.co.gpsc.common.client.security.group.GroupListPanel;
import id.co.gpsc.common.client.security.user.UserListPanel;
import id.co.gpsc.common.security.AriumSecurityGlobalParameterHolder;

import java.math.BigInteger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry Point arium security
 * @author I Gede Mahendra
 * @since Nov 29, 2012, 2:06:29 PM
 * @version $Id
 */
public class AriumSecurityApplication implements EntryPoint{

	
	
	@Override
	public void onModuleLoad() {
		
		/*Data dummy untuk application id dan current user login*/
		AriumSecurityGlobalParameterHolder.CURRENT_APPLICATION_ID =BigInteger.ONE ;
		AriumSecurityGlobalParameterHolder.CURRENT_USER_LOGIN = "DEIN";
		AriumSecurityGlobalParameterHolder.CURRENT_APPLICATION_NAME = "Open Cams";
		
		
		
		RootPanel.get("containerSecurity").add(new ApplicationListPanel());
		RootPanel.get("containerSecurity").add(new ApplicationUserListPanel());	
		RootPanel.get("containerSecurity").add(new GroupListPanel());
		RootPanel.get("containerSecurity").add(new UserListPanel());		
	}
}