package id.co.gpsc.common.client.rpc;

import id.co.gpsc.common.client.rpc.impl.SessionTimerRPCServiceAsyncImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SessionTimerRPCServiceAsync {
	
	public static class Util {
		
		private static SessionTimerRPCServiceAsync instance ; 
		
		public static SessionTimerRPCServiceAsync getInstance() {
			if ( instance == null){
				instance = GWT.create(SessionTimerRPCServiceAsyncImpl.class);

			}
			return instance;
		}
		
	}
	
	public void getSessionTimeoutLength(AsyncCallback<Long> callback);

}
