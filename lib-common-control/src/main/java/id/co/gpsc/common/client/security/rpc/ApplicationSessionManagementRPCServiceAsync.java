package id.co.gpsc.common.client.security.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.security.rpc.impl.ApplicationSessionManagementRPCServiceAsyncImpl;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.ApplicationSessionRegistry;

/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface ApplicationSessionManagementRPCServiceAsync {
	
	
	public static class Util {
		
		private static ApplicationSessionManagementRPCServiceAsync instance ; 
		
		public static ApplicationSessionManagementRPCServiceAsync getInstance() {
			if ( instance == null)
				instance =GWT.create(ApplicationSessionManagementRPCServiceAsyncImpl.class);  
			return instance;
		}
		
		
	}

	/**
	 * membaca data user yang sedang log in
	 * @param usernameWildCard username wild card
	 * @param pageSize ukuran page per pembacaan
	 * @param page page berapa yang hendak di baca
	 *  
	 */
	public void getCurrentlyLogedInUser (String usernameWildCard ,String realNameWildCard , String email ,  int pageSize , int page , AsyncCallback<PagedResultHolder<ApplicationSessionRegistry>> callback);
	
	
	/**
	 * paksa session di loggof
	 * @param sessionId id dari session yang di paksa logoff
	 */
	public void forceLogoff( String sessionId , AsyncCallback<Void> callback) ; 

}
