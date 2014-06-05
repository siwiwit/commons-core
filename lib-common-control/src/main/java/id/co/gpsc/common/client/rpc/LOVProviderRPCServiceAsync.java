package id.co.gpsc.common.client.rpc;

import id.co.gpsc.common.client.rpc.impl.LOVProviderRPCServiceAsyncImpl;
import id.co.gpsc.common.data.lov.Common2ndLevelLOVHeader;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOV2ndLevelRequestArgument;
import id.co.gpsc.common.data.lov.LOVProviderRPCService;
import id.co.gpsc.common.data.lov.LOVRequestArgument;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;



/**
 * async for {@link LOVProviderRPCService}
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 **/
public interface LOVProviderRPCServiceAsync {

	
	public static class Util{
		private static LOVProviderRPCServiceAsync instance ; 
		public static LOVProviderRPCServiceAsync getInstance() {
			if ( instance==null){
				instance = GWT.create(LOVProviderRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}
			return instance;
		}
	}
	/**
	 * send request LOV data.
	 * @param dataRequest lov id yang hendak di request
	 * @param callback data LOV yang berubah/belum ada di client
	 **/
	void getModifiedLOVs(String localizationCode ,   List<LOVRequestArgument> dataRequest,
			AsyncCallback<List<CommonLOVHeader>> callback);
	
	
	/**
	 * request 2nd Level . mohon di perhatikan, method ini bisa throw UnknownLookupValueProviderException ini dalam kasus LOV tidak di temukan dalam provider. dalam artian salah kode LOV, atau memang blm di register
	 **/
	public void getModifiedLOV(String localizationCode , LOV2ndLevelRequestArgument dataRequest, AsyncCallback<Common2ndLevelLOVHeader> callback);

	/**
	 * method ini membaca lookup yang 1 level. ini di pergunakan dalam kasus di set cuma dengan value dari current data, jadinya kontrol memerlukan data lain nya dalam 1 (yang parent nya sama). jadinya RPC ini akan return data dengan parent yang sama
	 * @param localizationCode kode localization
	 * @param lovId id dari lookup
	 * @param lookupValue value dari lookup
	 *  
	 **/
	public void getSameParent2ndLevelLOV(String localizationCode,LOV2ndLevelRequestArgument lovRequestArgument  /* StrongTyped2ndLevelLOVID lovId , String lookupValue, String currentClientLovVersion */, AsyncCallback<Common2ndLevelLOVHeader> callback) ;
}
