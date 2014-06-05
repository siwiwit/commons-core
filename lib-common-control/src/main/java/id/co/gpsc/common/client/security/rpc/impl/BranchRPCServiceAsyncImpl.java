package id.co.gpsc.common.client.security.rpc.impl;

import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.client.security.rpc.BranchRPCServiceAsync;
import id.co.gpsc.common.security.rpc.BranchRPCService;

public class BranchRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<BranchRPCService> implements BranchRPCServiceAsync{

	@Override
	protected Class<BranchRPCService> getServiceInterface() {
		return BranchRPCService.class;
	}
	
		public void remove(java.math.BigInteger param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "remove", new Class<?>[]{
			java.math.BigInteger.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void getDataByParameter(id.co.gpsc.common.data.query.SigmaSimpleQueryFilter[] param0,int param1,int param2,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.security.dto.BranchDTO>> callback) {
		this.submitRPCRequestRaw( "getDataByParameter", new Class<?>[]{
			id.co.gpsc.common.data.query.SigmaSimpleQueryFilter[].class,int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}


	public void saveOrUpdateBranch(id.co.gpsc.common.security.domain.Branch param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "saveOrUpdateBranch", new Class<?>[]{
			id.co.gpsc.common.security.domain.Branch.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}




	

}

