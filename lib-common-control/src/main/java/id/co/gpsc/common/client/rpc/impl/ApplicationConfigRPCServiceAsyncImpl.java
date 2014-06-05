package id.co.gpsc.common.client.rpc.impl;

import id.co.gpsc.common.client.rpc.ApplicationConfigRPCServiceAsync;
import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.data.app.ApplicationConfigRPCService;

public class ApplicationConfigRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<ApplicationConfigRPCService> implements ApplicationConfigRPCServiceAsync{

	@Override
	protected Class<ApplicationConfigRPCService> getServiceInterface() {
		return ApplicationConfigRPCService.class;
	}
	
		public void saveLabel(id.co.gpsc.common.data.entity.I18Text param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "saveLabel", new Class<?>[]{
			id.co.gpsc.common.data.entity.I18Text.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void saveLabel(java.lang.String param0,java.lang.String param1,java.lang.String param2,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "saveLabel", new Class<?>[]{
			java.lang.String.class,java.lang.String.class,java.lang.String.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}


	public void getLabels(java.lang.String param0,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.app.LabelDataWrapper[]> callback) {
		this.submitRPCRequestRaw( "getLabels", new Class<?>[]{
			java.lang.String.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void saveLabels(id.co.gpsc.common.data.entity.I18Text[] param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "saveLabels", new Class<?>[]{
			id.co.gpsc.common.data.entity.I18Text[].class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void saveLabels(java.lang.String param0,id.co.gpsc.common.data.app.LabelDataWrapper[] param1,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "saveLabels", new Class<?>[]{
			java.lang.String.class,id.co.gpsc.common.data.app.LabelDataWrapper[].class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}


	public void updateLabel(id.co.gpsc.common.data.entity.I18Text param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "updateLabel", new Class<?>[]{
			id.co.gpsc.common.data.entity.I18Text.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void getI18NTexts(int param0,int param1,id.co.gpsc.common.data.query.SigmaSimpleQueryFilter[] param2,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.data.entity.I18Text>> callback) {
		this.submitRPCRequestRaw( "getI18NTexts", new Class<?>[]{
			int.class,int.class,id.co.gpsc.common.data.query.SigmaSimpleQueryFilter[].class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}


	public void getFormConfiguration(java.lang.String param0,java.lang.String param1,java.lang.Integer param2,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.app.AppFormConfiguration> callback) {
		this.submitRPCRequestRaw( "getFormConfiguration", new Class<?>[]{
			java.lang.String.class,java.lang.String.class,java.lang.Integer.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}


	public void getI18NGroupId(id.co.gpsc.common.data.entity.I18Text param0,int param1,int param2,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.data.app.LabelDataWrapper>> callback) {
		this.submitRPCRequestRaw( "getI18NGroupId", new Class<?>[]{
			id.co.gpsc.common.data.entity.I18Text.class,int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, 
		}, 
		callback); 	
	}


	public void getTextGroups(com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<id.co.gpsc.common.data.entity.I18NTextGroup>> callback) {
		this.submitRPCRequestRaw( "getTextGroups", new Class<?>[]{
			 
			
		}, 
		new Object[]{
			 
		}, 
		callback); 	
	}


	public void getAvaliableLanguages(com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<id.co.gpsc.common.data.entity.I18Code>> callback) {
		this.submitRPCRequestRaw( "getAvaliableLanguages", new Class<?>[]{
			 
			
		}, 
		new Object[]{
			 
		}, 
		callback); 	
	}


	public void saveControlConfiguration(id.co.gpsc.common.data.entity.FormElementConfiguration param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "saveControlConfiguration", new Class<?>[]{
			id.co.gpsc.common.data.entity.FormElementConfiguration.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void readControlConfiguration(java.lang.String param0,java.lang.String param1,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.entity.FormElementConfiguration> callback) {
		this.submitRPCRequestRaw( "readControlConfiguration", new Class<?>[]{
			java.lang.String.class,java.lang.String.class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}


	public void getAllLanguagesTextById(java.lang.String param0,com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<id.co.gpsc.common.data.entity.I18Text>> callback) {
		this.submitRPCRequestRaw( "getAllLanguagesTextById", new Class<?>[]{
			java.lang.String.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}




	

}
