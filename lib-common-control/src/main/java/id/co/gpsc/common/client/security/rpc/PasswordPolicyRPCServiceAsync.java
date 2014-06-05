/**
 * 
 */
package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.PasswordPolicyRPCServiceAsyncImpl;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.PasswordPolicy;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * password policy RPC Service
 * @author Dode
 * @version $Id
 * @since Jan 30, 2013, 3:36:59 PM
 */
public interface PasswordPolicyRPCServiceAsync {

	public static class Util {
		private static PasswordPolicyRPCServiceAsync instance ; 
		
		public static PasswordPolicyRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(PasswordPolicyRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	/**
	 * get password policy data
	 * @param pagePosition page want to display
	 * @param pageSize size data per page
	 * @return list of password policy data
	 * @throws Exception
	 */
	public void getPasswordPolicyData(int pagePosition, int pageSize, AsyncCallback<PagedResultHolder<PasswordPolicy>> callback);
	
	/**
	 * insert data password policy
	 * @param data data password policy yang di save
	 * @throws Exception
	 */
	public void insert(PasswordPolicy data, AsyncCallback<Void> callback) ;
	
	/**
	 * update data password policy
	 * @param data data password policy yang di update
	 * @throws Exception
	 */
	public void update(PasswordPolicy data, AsyncCallback<Void> callback) ;
	
	/**
	 * remove password policy
	 * @param id id password policy yang di remove
	 * @throws Exception
	 */
	public void remove(BigInteger id, AsyncCallback<Void> callback) ;
}
