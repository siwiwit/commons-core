package id.co.gpsc.common.client.security.rpc.impl;

import java.math.BigInteger;

import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.client.security.rpc.FunctionAssignmentRPCServiceAsync;
import id.co.gpsc.common.data.app.security.MenuEditingData;
import id.co.gpsc.common.security.rpc.FunctionAssignmentRPCService;

public class FunctionAssignmentRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<FunctionAssignmentRPCService> implements FunctionAssignmentRPCServiceAsync{

	@Override
	protected Class<FunctionAssignmentRPCService> getServiceInterface() {
		return FunctionAssignmentRPCService.class;
	}
	
		public void getFunctionIdByGroupId(java.math.BigInteger param0,com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<java.math.BigInteger>> callback) {
		this.submitRPCRequestRaw( "getFunctionIdByGroupId", new Class<?>[]{
			java.math.BigInteger.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void addRemoveFunctionAssignment(java.util.List<id.co.gpsc.common.security.domain.FunctionAssignment> param0,java.util.List<id.co.gpsc.common.security.domain.FunctionAssignment> param1,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "addRemoveFunctionAssignment", new Class<?>[]{
			java.util.List.class,java.util.List.class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}

	@Override
	public void getAllAvailableMenu(BigInteger applicationId,
			BigInteger groupId, AsyncCallback<MenuEditingData> callback) {
		this.submitRPCRequestRaw( "addRemoveFunctionAssignment", new Class<?>[]{
				BigInteger.class
				
			}, 
			new Object[]{
				applicationId
			}, 
			callback);
		
	}




	

}


