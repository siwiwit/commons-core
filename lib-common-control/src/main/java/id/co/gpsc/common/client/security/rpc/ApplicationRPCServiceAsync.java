/**
 * 
 */
package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.ApplicationRPCServiceAsyncImpl;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.Application;
import id.co.gpsc.common.security.dto.ApplicationDTO;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Dode
 * @version $Id
 * @since Dec 19, 2012, 2:50:14 PM
 */
public interface ApplicationRPCServiceAsync {

	public static class Util {
		private static ApplicationRPCServiceAsync instance ; 
		
		public static ApplicationRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(ApplicationRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	/**
	 * get all application data
	 * @return list application
	 * @throws Exception
	 */
	public void getApplicationList(AsyncCallback<List<Application>> callback) throws Exception;
	
	
	/**
	 * current app, data detail nya spt bagaimana : 
	 * <ol>
	 * <li>Id applikasi</li>
	 * <li>tanggal aplikasi</li>
	 * 
	 * </ol>
	 **/
	public void getCurrentAppApplicationInfo  (AsyncCallback<ApplicationDTO> callback) ; 
	
	/**
	 * Get all application
	 * @author I Gede Mahendra
	 * @param pagePosition
	 * @param pageSize
	 * @param callback
	 */
	void getApplicationList(int pagePosition, int pageSize, AsyncCallback<PagedResultHolder<ApplicationDTO>> callback);
	
	/**
	 * Save or update
	 * @param data
	 * @param callback
	 */
	void saveOrUpdate(ApplicationDTO data, AsyncCallback<Void> callback);
}
