package id.co.gpsc.common.client.rpc.impl;

import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.client.rpc.SessionTimerRPCServiceAsync;
import id.co.gpsc.common.rpc.common.SessionTimerRpcService;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SessionTimerRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<SessionTimerRpcService> implements SessionTimerRPCServiceAsync {

	@Override
	public void getSessionTimeoutLength(AsyncCallback<Long> callback) {
		this.submitRPCRequestRaw( "getSessionTimeoutLength",
			new Class[]{
		}, 
		
		new Object []{
		}, callback);
	}

	@Override
	protected Class<SessionTimerRpcService> getServiceInterface() {
		return SessionTimerRpcService.class;
	}



}
