package id.co.gpsc.common.client.security.rpc.impl;

import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.client.security.rpc.ApplicationSessionManagementRPCServiceAsync;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.ApplicationSessionRegistry;
import id.co.gpsc.common.security.rpc.ApplicationSessionManagementRPCService;

/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class ApplicationSessionManagementRPCServiceAsyncImpl extends 
	ManualJSONSerializeRPCService<ApplicationSessionManagementRPCService> implements ApplicationSessionManagementRPCServiceAsync {

	@Override
	protected Class<ApplicationSessionManagementRPCService> getServiceInterface() {
		return ApplicationSessionManagementRPCService.class;
	}

	@Override
	public void getCurrentlyLogedInUser(
			String usernameWildCard,
			String realNameWildCard,
			String email,
			int pageSize,
			int page,
			AsyncCallback<PagedResultHolder<ApplicationSessionRegistry>> callback) {
		this.submitRPCRequestRaw("getCurrentlyLogedInUser", 
				new Class<?>[]{
					String.class,
					String.class, 
					String.class, 
					int.class , 
					int.class
				}
				, new Object[]{
				usernameWildCard , 
				realNameWildCard, 
				email , 
				pageSize, 
				page
		}, callback); 
		
		
	}

	@Override
	public void forceLogoff(String sessionId, AsyncCallback<Void> callback) {
		this.submitRPCRequestRaw("forceLogoff", 
				new Class<?>[]{
					String.class,
				
				}
				, new Object[]{
				sessionId
		}, callback); 
		
	}

}
