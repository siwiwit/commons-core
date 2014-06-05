/**
 * 
 */
package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.FunctionAssignmentRPCServiceAsyncImpl;
import id.co.gpsc.common.data.app.security.MenuEditingData;
import id.co.gpsc.common.security.domain.FunctionAssignment;

import java.math.BigInteger;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Dode
 * @version $Id
 * @since Jan 8, 2013, 2:21:28 PM
 */
public interface FunctionAssignmentRPCServiceAsync {
	
	public static class Util {
		private static FunctionAssignmentRPCServiceAsync instance ; 
		
		public static FunctionAssignmentRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(FunctionAssignmentRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	/**
	 * get function id by group id
	 * @param groupId group id
	 * @return list of function id
	 * @throws Exception
	 */
	public void getFunctionIdByGroupId(BigInteger groupId, AsyncCallback<List<BigInteger>> callback) throws Exception;
	
	/**
	 * added by dode
	 * add and remove menu item from group menu item
	 * @param addedMenuItem list of added menu item
	 * @param removedMenuItem list of removed menu item
	 * @throws Exception
	 */
	public void addRemoveFunctionAssignment(List<FunctionAssignment> addedMenuItem, List<FunctionAssignment> removedMenuItem, AsyncCallback<Void> callback) throws Exception;
	
	/**
	 * membaca data aplication yang tersedia
	 * @param applicationId id aplikasi
	 * @param groupId id group 
	 */
	public void getAllAvailableMenu ( BigInteger applicationId , BigInteger groupId , AsyncCallback<MenuEditingData> callback) ; 
}
