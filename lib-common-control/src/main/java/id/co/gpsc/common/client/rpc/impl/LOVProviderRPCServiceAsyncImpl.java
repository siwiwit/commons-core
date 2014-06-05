/**
 * 
 */
package id.co.gpsc.common.client.rpc.impl;

/**
 * @author Agus Gede Adipartha Wibawa
 * @since Sep 6, 2013, 5:39:14 PM
 *
 */
import id.co.gpsc.common.client.rpc.LOVProviderRPCServiceAsync;
import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.data.lov.LOVProviderRPCService;

public class LOVProviderRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<LOVProviderRPCService> implements LOVProviderRPCServiceAsync{

	@Override
	protected Class<LOVProviderRPCService> getServiceInterface() {
		return LOVProviderRPCService.class;
	}
	
		public void getModifiedLOVs(java.lang.String param0,java.util.List<id.co.gpsc.common.data.lov.LOVRequestArgument> param1,com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<id.co.gpsc.common.data.lov.CommonLOVHeader>> callback) {
		this.submitRPCRequestRaw( "getModifiedLOVs", new Class<?>[]{
			java.lang.String.class,java.util.List.class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}


	public void getModifiedLOV(java.lang.String param0,id.co.gpsc.common.data.lov.LOV2ndLevelRequestArgument param1,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.lov.Common2ndLevelLOVHeader> callback) {
		this.submitRPCRequestRaw( "getModifiedLOV", new Class<?>[]{
			java.lang.String.class,id.co.gpsc.common.data.lov.LOV2ndLevelRequestArgument.class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}


	public void getSameParent2ndLevelLOV(java.lang.String param0,id.co.gpsc.common.data.lov.LOV2ndLevelRequestArgument param1,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.lov.Common2ndLevelLOVHeader> callback) {
		this.submitRPCRequestRaw( "getSameParent2ndLevelLOV", new Class<?>[]{
			java.lang.String.class,id.co.gpsc.common.data.lov.LOV2ndLevelRequestArgument.class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}




	

}