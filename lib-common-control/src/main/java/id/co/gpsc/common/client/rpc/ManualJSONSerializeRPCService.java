package id.co.gpsc.common.client.rpc;


import id.co.gpsc.common.client.cache.SessionStorageActualClientCacheWorker;
import id.co.gpsc.common.client.util.ClientParsedJSONContainer;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.data.serializer.IJSONWrapperObject;
import id.co.gpsc.common.data.serializer.json.ListDataWrapperJSONFriendlyType;
import id.co.gpsc.common.data.serializer.json.ListDataWrapperPrimitiveType;
import id.co.gpsc.common.data.serializer.json.NonPrimitiveArrayDataWrapper;
import id.co.gpsc.common.data.serializer.json.ObjectSerializerManager;
import id.co.gpsc.common.data.serializer.json.PrimitiveArrayDataWrapper;
import id.co.gpsc.common.data.serializer.json.RPCResponseWrapper;
import id.co.gpsc.common.data.serializer.json.SimpleObjectWrapper;
import id.co.gpsc.common.exception.SimpleJSONSerializableException;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.JSONCharacterEscapeUtil;
import id.co.gpsc.jquery.client.util.JQueryUtils;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;



/**
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public abstract class ManualJSONSerializeRPCService<RPC extends JSONSerializedRemoteService> {
	
	
	
	protected static final String serviceUrl = "communication-link/json-servlet.json-rpc" ; 
	
	 
	/**
	 * timer session. static agar berbagi dengan RPC yang lain
	 */
	static Timer SESSION_TIMER  ; 
	
	
	
	private static boolean isLogoutTriggered = false ; 
	public ManualJSONSerializeRPCService() {
		 
	}
	
	
	public String getServiceURL() {
		return CommonClientControlUtil.getInstance().getApplicationBaseUrl() + serviceUrl;
	}
	
	/**
	 * method ini utnuk invoke request JSON ke server. metode nya : 
	 * <ol>
	 * <li>semua parameter di serialize kan</li>
	 * <li>buat post request</li>
	 * <li>hasil di transpose menjadi data kembali</li>
	 * </ol>
	 **/
	public <T>  void submitRPCRequest(final Class<?> serviceInterface, final String methodName , Class<?>[] methodArgumentFQCN , IJSONFriendlyObject<?>[] requestArguemnt , 
			final AsyncCallback<T> rawCallback) {
		
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final SimpleAsyncCallback<T> actualCallback =  rawCallback instanceof SimpleAsyncCallback? 
				(SimpleAsyncCallback) rawCallback : 
				new SimpleAsyncCallback<T>() {
					@Override
					protected void customFailurehandler(Throwable caught) {
						rawCallback.onFailure(caught);
						
					}
					@Override
					public void onSuccess(T result) {
						rawCallback.onSuccess(result);
						
					}
				}	; 
		
		
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, getServiceURL());
		builder.setHeader("Content-type",
				"application/x-www-form-urlencoded");

		
		// bangun argument
		String dataString = "METHOD_TO_INVOKE=" + serviceInterface.getName() + "." 
				+ methodName + 
				"&PARAM_FQCN="; 
		String paramFQCN = "" ; 
		if ( methodArgumentFQCN!= null && methodArgumentFQCN.length>0){
			for ( Class<?> scn : methodArgumentFQCN){
				paramFQCN+= scn.getName() + "-"; 
			}
			dataString += paramFQCN.substring(0 , paramFQCN.length()-1);
		}
		if ( requestArguemnt != null && requestArguemnt.length>0){
			int i = 0 ; 
			for ( IJSONFriendlyObject<?> scn : requestArguemnt){
				dataString +="&RPC_PARAM_" + i + "=";
				i++ ; 
				if ( scn!= null){
					ClientParsedJSONContainer cnt = new ClientParsedJSONContainer(); 
					scn.translateToJSON(cnt); 
					
					String jsString = cnt.getJSONString();
					jsString = JSONCharacterEscapeUtil.getInstance().convertToUrlEncodedString(jsString);
					
					String swap = URL.encode( jsString);
					
					/*swap = swap.replaceAll("\\ ","%20");
					swap = swap.replaceAll("\\!","%21");
					swap = swap.replaceAll("\\\"","%22");
					swap = swap.replaceAll("\\#","%23");
					swap = swap.replaceAll("\\$","%24");
					swap = swap.replaceAll("\\%","%25");*/
					
					/*swap = swap.replaceAll("\\'","%27");
					swap = swap.replaceAll("\\(","%28");
					swap = swap.replaceAll("\\)","%29");
					swap = swap.replaceAll("\\*","%2A");*/
					/*
					swap = swap.replaceAll("\\,","%2C");
					swap = swap.replaceAll("\\-","%2D");
					swap = swap.replaceAll("\\.","%2E");
					swap = swap.replaceAll("\\/","%2F");*/
					dataString += swap ; 
				} 						
						
			}
		}
		try {
			
			builder.sendRequest(dataString, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					if ( response.getStatusCode()==200){
						String rawRequestBody =  response.getText() ;
			 
						handleRequestResponse(rawRequestBody, actualCallback);
					}
					else{
						if ( response.getStatusCode()==0 ){
							JQueryUtils.getInstance().blockEntirePage("Request terakhir melaporkan status : 0<br/>Ini bisa berarti server mati, atau koneksi anda putus.<br/>Di sarankan anda mereload browser anda atau login ulang");
							JQueryUtils.getInstance().unblockEntirePage(10000); 
							return ; 
						}else if ( response.getStatusCode()==12029){
							JQueryUtils.getInstance().blockEntirePage("Request terakhir melaporkan status : 0<br/>Ini bisa berarti server mati, atau koneksi anda putus.<br/>Di sarankan anda mereload browser anda atau login ulang");
							JQueryUtils.getInstance().unblockEntirePage(10000); 
							return ;  
						}
						Window.alert("status code : " + response.getStatusCode());
						rawCallback.onFailure(new RequestFailureException("gagal request RPC, response code : " + response.getStatusCode()) );
						return ; 
					}
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					rawCallback.onFailure(exception); 
					
				}
			});
		} catch (RequestException e) {
			rawCallback.onFailure(e); 
			e.printStackTrace();
		} 
		
		
	} 
	
	/**
	 * default session timeout (30 menit). Value dari variable ini harus diset pada saat startup aplikasi.
	 */
	private static long DEFAULT_SESSION_TIMEOUT_LENGTH = 900000;
	
	public static String USERNAME_SESSION_KEY = "usernameSessKey";

	
	private static String currentUsername = null;
	
	public static void setCurrentUsername(String userName)  {
		//Window.alert("Set Current Username : "+userName);
		currentUsername = userName;
		Cookies.setCookie(USERNAME_SESSION_KEY, userName);
		//CommonClientControlUtil.getInstance().log("Set Current Username : "+currentUsername);
	}
	
	
	/**
	 * method ini untuk invoke RPC dengan JSON Serialized item
	 * @param serviceInterface service interface class. interface mana yang hendak di panggil
	 * @param methodName nama method yang di panggil
	 * @param methodArgumentFQCN FQCN array dari argument mentod
	 * @param arguments arguments untuk method
	 * @param rawCallback callback untuk respone RPC
	 **/
	public <T>  void submitRPCRequestRaw(Class<?> serviceInterface, String methodName , Class<?>[] methodArgumentFQCN ,
			Object [] arguments , 
			final AsyncCallback<T> rawCallback) {
		
		String uname = Cookies.getCookie(USERNAME_SESSION_KEY);
		
		//CommonClientControlUtil.getInstance().log("Current Username : "+currentUsername);
		//CommonClientControlUtil.getInstance().log("Username : "+uname);
		
		if(currentUsername != null) {
			
			if(!currentUsername.equals(uname)) {
				JQueryUtils.getInstance().blockEntirePage("Informasi login anda berubah, apakah anda melakukan login di tab browser yg lain.<br /> "
						+ currentUsername + " menjadi "+ uname+"<br /> "
								+ "Silahkan refresh kembali browser anda.");
				return;
			}
		}
		
		if ( isLogoutTriggered)
			return ; 
		if ( SESSION_TIMER != null)
			SESSION_TIMER.cancel();
		
		
		activateSessionTimoutTimer();
		
		
		if ( arguments!= null){
			
			IJSONFriendlyObject<?>[] argJson = new IJSONFriendlyObject[arguments.length];
			int i=0 ; 
			for ( Object scn : arguments){
				if ( scn== null ){
					i++ ; 
					continue ; 
				}
				// skip saja,karena null
				if ( scn instanceof IJSONFriendlyObject<?>){
					argJson[i] = (IJSONFriendlyObject<?>)scn; 
				}
				else{
					argJson[i] =convertToJSONFriendlyObject(i, scn);
				}
				i++ ; 
			}
			
			submitRPCRequest(serviceInterface, methodName, methodArgumentFQCN, argJson, rawCallback);
			
		}else{
			submitRPCRequest(serviceInterface, methodName, methodArgumentFQCN, null, rawCallback);
		}
	}
	
	private static void activateSessionTimoutTimer () {
		SESSION_TIMER = new Timer() 	{
			
			@Override
			public void run() {
				isLogoutTriggered = true ; 
				JQueryUtils.getInstance().blockEntirePage("Sesi anda sudah habis.<br/> anda harus login kembali");
				JQueryUtils.getInstance().unblockEntirePage(10000, new Command() {
					@Override
					public void execute() {
						Window.Location.assign(CommonClientControlUtil.getInstance().getApplicationBaseUrl()+"j_spring_security_logout");
					}
				});
				
				
			}
		};
		SESSION_TIMER.schedule(new Long(DEFAULT_SESSION_TIMEOUT_LENGTH).intValue());
		GWT.log("Session Timeout Length: "+DEFAULT_SESSION_TIMEOUT_LENGTH);
	}
	
	/**
	 * invoke service dengan nama method. service interface ikut dengan {@link #getServiceInterface()}
	 **/
	public <T>  void submitRPCRequestRaw(  String methodName , Class<?>[] methodArgumentFQCN ,
			Object [] arguments , 
			final AsyncCallback<T> rawCallback) {
		submitRPCRequestRaw(getServiceInterface(), methodName, methodArgumentFQCN, arguments, rawCallback);
	}
	
	
	
	
	
	/**
	 * konversi dari object mebjadi raw json
	 **/
	protected IJSONFriendlyObject<?> convertToJSONFriendlyObject (int index , Object object) {
		if ( object == null)
			return null ;
		
		//1. string, integer, atau objecy sederhana
		if ( ObjectSerializerManager.isSimpleObject(object.getClass().getName())) 
			return new SimpleObjectWrapper(object);
		//2. array of simple object
		if ( ObjectSerializerManager.getInstance().isArrayofSimpleObject(object.getClass().getName())){
			return new PrimitiveArrayDataWrapper((Object[])object);
		}
		//3. json convertable object
		if ( object instanceof IJSONFriendlyObject<?>) // json friendly data. 1 data
			return (IJSONFriendlyObject<?> ) object ; 
		//4. array of json friendlu object
		if ( object instanceof IJSONFriendlyObject<?>[]){
			IJSONFriendlyObject<?>[] arr = (IJSONFriendlyObject<?>[])object ; 
			return new NonPrimitiveArrayDataWrapper(arr);
		}
		//java.util.List
		if ( object instanceof List<?>){
			List<?> objects = (List<?>)object ;
			
			
			boolean simpleType  = false; 
			 boolean match = false ; 
			
			
			if ( objects== null|| objects.isEmpty())
				return null ;
			
			for ( Object scn : objects){
				if ( scn == null)
					continue ; 
				
				String fqcn = scn.getClass().getName(); 
				if ( ObjectSerializerManager.isSimpleObject(fqcn)){
					// simple array
					simpleType = true ; 
					match = true ; 
					break ; 
				}
				else if (  (scn instanceof IJSONFriendlyObject<?>)){
					match = true ;
					break ; 
				}
				else{
					throw new RuntimeException("object dengan index :" + index + " tidak bisa di serialize kan. argument RPC hanya di ijinkan : primitive, array of primitive, IJSONFriendlyObject atau List of IJSONFriendlyObject. di luar itu akan di reject" );
				}
			}
			if (! match){
				throw new RuntimeException("object dengan index :" + index + " tidak bisa di serialize kan. argument RPC hanya di ijinkan : primitive, array of primitive, IJSONFriendlyObject atau List of IJSONFriendlyObject. di luar itu akan di reject" );
			}
			if ( simpleType){
				return new ListDataWrapperPrimitiveType(objects);
			}else{
				return new ListDataWrapperJSONFriendlyType((List<IJSONFriendlyObject<?>>)objects); 
			}
			
			
		}
		throw new RuntimeException("object dengan index :" + index + " tidak bisa di serialize kan. argument RPC hanya di ijinkan : primitive, array of primitive, IJSONFriendlyObject atau List of IJSONFriendlyObject. di luar itu akan di reject" );
		
	} 
	
	
	
	
	
	
	/**
	 * handler kalau RPC request done
	 **/
	@SuppressWarnings("unchecked")
	protected <T> void handleRequestResponse (String rawString , SimpleAsyncCallback<T> callback){
		RPCResponseWrapper wrapper = new  RPCResponseWrapper(); 
		RPCResponseWrapper actualData =  wrapper.instantiateFromJSON(new ClientParsedJSONContainer(rawString));
		if ( actualData.isHaveError()){
			SimpleJSONSerializableException wrapperExc =actualData.getException()  ;
			Exception actualExc =  wrapperExc.getActualException();
			if ( actualExc!= null)
				callback.onFailure(actualExc);
			else
				callback.onFailure(wrapperExc);
			return ; 
		}
		
		
		
		
		
		
		Object w = actualData.getData(); 
		if ( w != null ){
			if ( w instanceof IJSONWrapperObject){
				IJSONWrapperObject<T> wraper = (IJSONWrapperObject<T>)w;
				callback.onSuccess(wraper.getActualData()); 
			}else{
				callback.onSuccess((T) actualData.getData());
			}
		}else
			callback.onSuccess(null);
	}
	
	
	
	
	
	
	/**
	 * interface dari service. ini untuk unifikasi message
	 **/
	protected abstract Class<RPC> getServiceInterface () ; 
	

	
	/**
	 * plug timeot ke dalam timer
	 */
	public static void setSessionTimeoutLength ( long timeoutLengthInMilis) {
		DEFAULT_SESSION_TIMEOUT_LENGTH = timeoutLengthInMilis   ;
		if ( SESSION_TIMER!= null){
			SESSION_TIMER.cancel();
			activateSessionTimoutTimer();
		}
		Storage s =  Storage.getSessionStorageIfSupported();
		if ( s!= null)
			s.setItem("DEFAULT_SESSION_TIMEOUT_LENGTH", timeoutLengthInMilis +"");
	}
	
	
	
}
