package id.co.gpsc.common.client.security.rpc.impl;

import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.client.security.rpc.UserDomainRPCServiceAsync;
import id.co.gpsc.common.security.rpc.UserDomainRPCService;

public class UserDomainRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<UserDomainRPCService> implements UserDomainRPCServiceAsync{

	@Override
	protected Class<UserDomainRPCService> getServiceInterface() {
		return UserDomainRPCService.class;
	}
	
		public void getUserDomainFromIIS(id.co.gpsc.common.data.query.SigmaSimpleQueryFilter[] param0,int param1,int param2,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.security.menu.UserDomain>> callback) {
		this.submitRPCRequestRaw( "getUserDomainFromIIS", new Class<?>[]{
			id.co.gpsc.common.data.query.SigmaSimpleQueryFilter[].class,int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}




	

}


