package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.UserRPCServiceAsyncImpl;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.common.security.dto.UserDetailDTO;
import id.co.gpsc.common.security.exception.PasswordPolicyException;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * User RPC Service Asyncronous
 * @author I Gede Mahendra
 * @since Dec 10, 2012, 11:06:23 AM
 * @version $Id
 */
public interface UserRPCServiceAsync {
	
	public static class Util {
		private static UserRPCServiceAsync instance ; 
		
		public static UserRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(UserRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	/**
	 * Get username by parameter
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param callback
	 */
	void getUserByParameter(SigmaSimpleQueryFilter[] filter, int page, int pageSize, AsyncCallback<PagedResultHolder<UserDTO>> callback);
	
	/**
	 * Get user by parameter
	 * @param applicationId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param callback
	 */
	void getUserByParameter(BigInteger applicationId, SigmaSimpleQueryFilter[] filter, int page, int pageSize, AsyncCallback<PagedResultHolder<UserDTO>> callback);
	
	/**
	 * get user by filter
	 * @param filter filter searching
	 * @param page page data yang ingin di tampilkan
	 * @param pageSize size data per page
	 * @return data user
	 * @throws Exception
	 */
	void getUserByFilter(SigmaSimpleQueryFilter[] filters, int pagePosition, int pageSize, AsyncCallback<PagedResultHolder<User>> callback) throws Exception;
	
	/**
	 * insert data user
	 * @param data data user yang disave
	 * @throws Exception
	 */
	public void insert(User data, AsyncCallback<Void> callback) throws Exception, PasswordPolicyException;
	
	/**
	 * update data user
	 * @param data data user yang di update
	 * @throws Exception
	 */
	public void update(User data, AsyncCallback<Void> callback) throws Exception;
	
	/**
	 * remove user
	 * @param id id user yang di remove
	 * @throws Exception
	 */
	public void remove(BigInteger id, AsyncCallback<Void> callback) throws Exception;
	
	/**
	 * reset password
	 * @param user data user dengan password baru
	 * @throws Exception
	 */
	public void resetPassword(User user, AsyncCallback<Void> callback) throws Exception, PasswordPolicyException;
	
	/**
	 * Get current user login
	 * @param callback
	 */
	void getCurrentUserLogin(AsyncCallback<UserDetailDTO> callback);
}