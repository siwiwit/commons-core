package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.UserDomainRPCServiceAsyncImpl;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.security.menu.UserDomain;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * User domain untuk windows authentifikasi RPC Service Asyncronous
 * @author I Gede Mahendra
 * @since Nov 29, 2012, 5:50:43 PM
 * @version $Id
 */
public interface UserDomainRPCServiceAsync {
	
	public static class Util {
		private static UserDomainRPCServiceAsync instance ; 
		
		public static UserDomainRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(UserDomainRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	/**
	 * Mendapatkan user domain dari server IIS
	 * @param parameter
	 * @param page
	 * @param pageSize
	 * @param callback
	 */
	void getUserDomainFromIIS(SimpleQueryFilter[] filter, int page, int pageSize, AsyncCallback<PagedResultHolder<UserDomain>> callback);
}