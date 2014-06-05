package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.GroupRPCServiceAsyncImpl;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.UserGroup;
import id.co.gpsc.common.security.dto.UserGroupDTO;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Group RPC Asyncronous
 * @author I Gede Mahendra
 * @since Nov 26, 2012, 3:57:47 PM
 * @version $Id
 */
public interface GroupRPCServiceAsync {
		
	public static class Util {
		private static GroupRPCServiceAsync instance ; 
		
		public static GroupRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(GroupRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	/**
	 * Sample RPC
	 * @param comment
	 * @param callback
	 */
	void sampleRpc(String comment, AsyncCallback<String> callback);
	
	/**
	 * Get all group
	 * @param paramater
	 * @param pagePosition
	 * @param pageSize
	 * @param callback
	 */
	void getAllGroup(UserGroup paramater,int pagePosition, int pageSize, AsyncCallback<PagedResultHolder<UserGroupDTO>> callback);
		
	/**
	 * Insert user group
	 * @param parameter
	 * @param callback
	 */
	void insert(UserGroup parameter, AsyncCallback<Void> callback);
	
	/**
	 * Delete user group
	 * @param parameter
	 * @param callback
	 */
	void delete(BigInteger parameter, AsyncCallback<Void> callback);
	
	/**
	 * Update use group
	 * @param parameter
	 * @param callback
	 */
	void update(UserGroup parameter, AsyncCallback<Void> callback);
	
	/**
	 * Get user group by parameter
	 * @param parameter
	 * @param callback
	 */
	void getUserGroupByParameter(UserGroup parameter, AsyncCallback<UserGroupDTO> callback);
}