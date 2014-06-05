package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.GroupAssignmentRPCServiceAsyncImpl;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.UserGroupAssignment;
import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;

import java.math.BigInteger;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Group Assignment RPC Async
 * @author I Gede Mahendra
 * @since Dec 7, 2012, 1:01:52 PM
 * @version $Id
 */
public interface GroupAssignmentRPCServiceAsync {
	
	public static class Util {
		private static GroupAssignmentRPCServiceAsync instance ; 
		
		public static GroupAssignmentRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(GroupAssignmentRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	/**
	 * Get all user group assignment
	 * @param paramater
	 * @param pagePosition
	 * @param pageSize
	 * @param callback
	 */
	void getAllGroup(UserGroupAssignment paramater,int pagePosition, int pageSize, AsyncCallback<PagedResultHolder<UserGroupAssignmentDTO>> callback);
	
	/**
	 * Insert data
	 * @param data
	 * @param callback
	 */
	void insert(UserGroupAssignment data, AsyncCallback<Void> callback);
	
	/**
	 * Delete data
	 * @param data
	 * @param callback
	 */
	void delete(BigInteger data, AsyncCallback<Void> callback);
	
	/**
	 * Get user group by user id
	 * @param userId
	 * @param callback
	 */
	void getUserGroupByUserId(BigInteger userId, AsyncCallback<List<UserGroupAssignmentDTO>> callback);
}