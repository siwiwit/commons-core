package id.co.gpsc.common.client.security.rpc.impl;

import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.client.security.rpc.UserRPCServiceAsync;
import id.co.gpsc.common.security.rpc.UserRPCService;

public class UserRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<UserRPCService> implements UserRPCServiceAsync{

	@Override
	protected Class<UserRPCService> getServiceInterface() {
		return UserRPCService.class;
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


	public void insert(id.co.gpsc.common.security.domain.User param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "insert", new Class<?>[]{
			id.co.gpsc.common.security.domain.User.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void update(id.co.gpsc.common.security.domain.User param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "update", new Class<?>[]{
			id.co.gpsc.common.security.domain.User.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void getUserByParameter(id.co.gpsc.common.data.query.SimpleQueryFilter[] param0,int param1,int param2,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.security.dto.UserDTO>> callback) {
		this.submitRPCRequestRaw( "getUserByParameter", new Class<?>[]{
			id.co.gpsc.common.data.query.SimpleQueryFilter[].class,int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}


	public void getUserByParameter(java.math.BigInteger param0,id.co.gpsc.common.data.query.SimpleQueryFilter[] param1,int param2,int param3,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.security.dto.UserDTO>> callback) {
		this.submitRPCRequestRaw( "getUserByParameter", new Class<?>[]{
			java.math.BigInteger.class,id.co.gpsc.common.data.query.SimpleQueryFilter[].class,int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, param3, 
		}, 
		callback); 	
	}


	public void getUserByFilter(id.co.gpsc.common.data.query.SimpleQueryFilter[] param0,int param1,int param2,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.security.domain.User>> callback) {
		this.submitRPCRequestRaw( "getUserByFilter", new Class<?>[]{
			id.co.gpsc.common.data.query.SimpleQueryFilter[].class,int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}


	public void resetPassword(id.co.gpsc.common.security.domain.User param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "resetPassword", new Class<?>[]{
			id.co.gpsc.common.security.domain.User.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void getCurrentUserLogin(com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.security.dto.UserDetailDTO> callback) {
		this.submitRPCRequestRaw( "getCurrentUserLogin", new Class<?>[]{
			 
			
		}, 
		new Object[]{
			 
		}, 
		callback); 	
	}




	

}


