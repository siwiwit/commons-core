package id.co.gpsc.common.client.rpc;

import id.co.gpsc.common.exception.LoginExpiredException;
import id.co.gpsc.common.rpc.ILoginExpiredDetector;
import id.co.gpsc.common.rpc.ManagedAsyncCallback;




/**
 * Base class untuk handler async callback. penempatan packaging ini agak salah taruh. jadinya di bikinkan base class {@link ManagedAsyncCallback} 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * 
 **/
public abstract class SigmaAsyncCallback<T> extends ManagedAsyncCallback<T>{

	static {
		ManagedAsyncCallback.LOGIN_EXPIRED_DETECTOR = new ILoginExpiredDetector() {
			
			@Override
			public boolean isLoginExpired(Throwable exception) {
				return exception instanceof LoginExpiredException;
			}
		};
		
		
	}
	


	
}
