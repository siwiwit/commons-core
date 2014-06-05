package id.co.gpsc.common.client.security.rpc.impl;

import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.client.security.rpc.GroupAssignmentRPCServiceAsync;
import id.co.gpsc.common.security.rpc.GroupAssignmentRPCService;

public class GroupAssignmentRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<GroupAssignmentRPCService> implements GroupAssignmentRPCServiceAsync{

	@Override
	protected Class<GroupAssignmentRPCService> getServiceInterface() {
		return GroupAssignmentRPCService.class;
	}
	
		public void delete(java.math.BigInteger param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "delete", new Class<?>[]{
			java.math.BigInteger.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void insert(id.co.gpsc.common.security.domain.UserGroupAssignment param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "insert", new Class<?>[]{
			id.co.gpsc.common.security.domain.UserGroupAssignment.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void getUserGroupByUserId(java.math.BigInteger param0,com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<id.co.gpsc.common.security.dto.UserGroupAssignmentDTO>> callback) {
		this.submitRPCRequestRaw( "getUserGroupByUserId", new Class<?>[]{
			java.math.BigInteger.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void getAllGroup(id.co.gpsc.common.security.domain.UserGroupAssignment param0,int param1,int param2,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.security.dto.UserGroupAssignmentDTO>> callback) {
		this.submitRPCRequestRaw( "getAllGroup", new Class<?>[]{
			id.co.gpsc.common.security.domain.UserGroupAssignment.class,int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}




	

}

