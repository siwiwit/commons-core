package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.ApplicationUserRPCServiceAsyncImpl;
import id.co.gpsc.common.security.domain.ApplicationUser;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;

import java.math.BigInteger;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Application User RPC Service Asyncronous
 * @author I Gede Mahendra
 * @since Dec 20, 2012, 10:37:44 AM
 * @version $Id
 */
public interface ApplicationUserRPCServiceAsync {
	
	public static class Util {
		private static ApplicationUserRPCServiceAsync instance ; 
		
		public static ApplicationUserRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(ApplicationUserRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	
	/**
	 * Count application user by parameter
	 * @param parameter
	 * @param callback
	 * @throws Exception
	 */
	void countApplicationUserByParameter(ApplicationUser parameter, AsyncCallback<Integer> callback);
	
	/**
	 * Insert or update user group assignment
	 * @param data
	 * @param applicationId
	 * @param userId
	 * @param currentUser
	 * @param callback
	 */
	void insertOrUpdate(List<UserGroupAssignmentDTO> data, BigInteger applicationId, BigInteger userId, String currentUser, AsyncCallback<Void> callback);
	
	/**
	 * Delete application user
	 * @param applicationId
	 * @param userId
	 * @param callback
	 */
	void deleteApplicationUser(BigInteger applicationId, BigInteger userId, AsyncCallback<Void> callback);
	
	/**
	 * Get application menu berdasarkan user yg login pada signon data
	 * @param data
	 * @param callback
	 */
	void getApplicationMenu(Signon data, AsyncCallback<List<ApplicationMenuSecurity>> callback);
}