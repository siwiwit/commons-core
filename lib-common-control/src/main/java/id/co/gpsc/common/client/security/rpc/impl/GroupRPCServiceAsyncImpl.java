package id.co.gpsc.common.client.security.rpc.impl;

import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.client.security.rpc.GroupRPCServiceAsync;
import id.co.gpsc.common.security.rpc.GroupRPCService;

public class GroupRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<GroupRPCService> implements GroupRPCServiceAsync{

	@Override
	protected Class<GroupRPCService> getServiceInterface() {
		return GroupRPCService.class;
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


	public void insert(id.co.gpsc.common.security.domain.UserGroup param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "insert", new Class<?>[]{
			id.co.gpsc.common.security.domain.UserGroup.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void update(id.co.gpsc.common.security.domain.UserGroup param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "update", new Class<?>[]{
			id.co.gpsc.common.security.domain.UserGroup.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void getUserGroupByParameter(id.co.gpsc.common.security.domain.UserGroup param0,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.security.dto.UserGroupDTO> callback) {
		this.submitRPCRequestRaw( "getUserGroupByParameter", new Class<?>[]{
			id.co.gpsc.common.security.domain.UserGroup.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void sampleRpc(java.lang.String param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.String> callback) {
		this.submitRPCRequestRaw( "sampleRpc", new Class<?>[]{
			java.lang.String.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void getAllGroup(id.co.gpsc.common.security.domain.UserGroup param0,int param1,int param2,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.security.dto.UserGroupDTO>> callback) {
		this.submitRPCRequestRaw( "getAllGroup", new Class<?>[]{
			id.co.gpsc.common.security.domain.UserGroup.class,int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}




	

}


