/**
 * 
 */
package id.co.gpsc.common.client.security.rpc.impl;

import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.client.security.rpc.ApplicationRPCServiceAsync;
import id.co.gpsc.common.security.rpc.ApplicationRPCService;

public class ApplicationRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<ApplicationRPCService> implements ApplicationRPCServiceAsync{

	@Override
	protected Class<ApplicationRPCService> getServiceInterface() {
		return ApplicationRPCService.class;
	}
	
		public void getApplicationList(int param0,int param1,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.security.dto.ApplicationDTO>> callback) {
		this.submitRPCRequestRaw( "getApplicationList", new Class<?>[]{
			int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}


	public void getApplicationList(com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<id.co.gpsc.common.security.domain.Application>> callback) {
		this.submitRPCRequestRaw( "getApplicationList", new Class<?>[]{
			 
			
		}, 
		new Object[]{
			 
		}, 
		callback); 	
	}


	public void getCurrentAppApplicationInfo(com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.security.dto.ApplicationDTO> callback) {
		this.submitRPCRequestRaw( "getCurrentAppApplicationInfo", new Class<?>[]{
			 
			
		}, 
		new Object[]{
			 
		}, 
		callback); 	
	}


	public void saveOrUpdate(id.co.gpsc.common.security.dto.ApplicationDTO param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "saveOrUpdate", new Class<?>[]{
			id.co.gpsc.common.security.dto.ApplicationDTO.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}
}