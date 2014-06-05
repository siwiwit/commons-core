package id.co.gpsc.common.client.control.i18;


import id.co.gpsc.common.client.app.JSONFriendlyAppFormConfiguration;
import id.co.gpsc.common.client.cache.BaseActualClientCacheWorker;
import id.co.gpsc.common.client.cache.ClientCacheDataExpiredException;
import id.co.gpsc.common.client.cache.ClientObjectCacheWrapper;
import id.co.gpsc.common.client.rpc.ApplicationConfigRPCServiceAsync;
import id.co.gpsc.common.data.app.AppFormConfiguration;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;




/**
 * abstract , <br/>
 * Form Configuration cache worker
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class BaseAppPanelResourceCacheManagerImpl implements
		AppPanelResourceCacheManager {
	
	
	protected class FormRequestParameter {
		private String formId ; 
		private String localizationCode ;
		
		private AsyncCallback<AppFormConfiguration> originalCallerCallback;
		
		public FormRequestParameter(String formId , String localizationCode, AsyncCallback<AppFormConfiguration> originalCallerCallback ){
			this.formId = formId ; 
			this.localizationCode=localizationCode; 
			this.originalCallerCallback=originalCallerCallback;
			
			
		}
		
		public String getFormId() {
			return formId;
		}
		public void setFormId(String formId) {
			this.formId = formId;
		}
		public String getLocalizationCode() {
			return localizationCode;
		}
		public void setLocalizationCode(String localizationCode) {
			this.localizationCode = localizationCode;
		}
		public AsyncCallback<AppFormConfiguration> getOriginalCallerCallback() {
			return originalCallerCallback;
		}
		
		
		
		
		
	}
	
	
	
	
	/**
	 * maks batch RPC request. mengindari masalah performence
	 **/
	private int maxBatchRequest = 3; 
	
	private int currentBatchCount =0;
	
	/**
	 * request qeue
	 **/
	private ArrayList<FormRequestParameter> requestQeue = new ArrayList<FormRequestParameter>();
	
	
	
	private ApplicationConfigRPCServiceAsync applicationConfigRPCServiceAsync;

	@Override
	public void submitToCache(AppFormConfiguration formConfiguration) {
		ClientObjectCacheWrapper<JSONFriendlyAppFormConfiguration> wrapper = new ClientObjectCacheWrapper<JSONFriendlyAppFormConfiguration>(new JSONFriendlyAppFormConfiguration(formConfiguration));
		wrapper.setCacheTime(new Date());
		JSONValue jsonVal =  wrapper.generateJSON();
		String cacheAsJson =  jsonVal.toString(); 
		getActualClientCacheWorker().submitToCache(generateKey(formConfiguration.getParentId(), formConfiguration.getLocaleId()), cacheAsJson);

	}

	@Override
	public AppFormConfiguration getCachedConfiguration(String panelId,
			String localizationCode) throws ClientCacheDataExpiredException {
		String key = generateKey(panelId , localizationCode) ;
		String data = getActualClientCacheWorker().get(key);
		if ( data==null)
			return null ; 
		ClientObjectCacheWrapper<JSONFriendlyAppFormConfiguration> sample = new ClientObjectCacheWrapper<JSONFriendlyAppFormConfiguration>();
		JSONFriendlyAppFormConfiguration  sampleDtl = new JSONFriendlyAppFormConfiguration();
		
		try {
			sample.readFromString(data, sampleDtl);
			sample.getObjectToCache();
		} catch (Exception e) {
			GWT.log("gagal fetch data cache dengan ID:" + panelId + ",localeid:" + localizationCode + ",error message:" + e.getMessage() , e);
			return null ;
		}
		if ( sample.checkIsExpired(CACHE_EXPIRY_DURATION_IN_SECONDS)){
			getActualClientCacheWorker().remove(key);
			throw new ClientCacheDataExpiredException("Form Object dengan key : " + panelId + ",localization :" + localizationCode + " expired", key);
		}
		return sample.getObjectToCache();
		
	}
	
	
	@Override
	public void requestForPanelConfiguration(String panelId,
			String localizationCode,
			final AsyncCallback<AppFormConfiguration> callback) {
		try {
			AppFormConfiguration cnf =  getCachedConfiguration(panelId, localizationCode);
			if(cnf!=null){
				GWT.log("form config: " + panelId + ", locale:" + localizationCode + ", mempergunakan client cache");
				callback.onSuccess(cnf);
				return ;
			}
		} catch (ClientCacheDataExpiredException cacheExpired) {
			GWT.log(cacheExpired.getMessage() ,cacheExpired);
		}
		
		
		requestQeue.add(new FormRequestParameter(panelId, localizationCode, callback));
		runRPCWorker();
		// request ke server
		
	}

	@Override
	public String generateKey(String panelId, String localeCode) {
		return "SIGMA-FORM-CONFIGURATION::" + panelId + "::" + localeCode;
	}
	
	
	
	/**
	 * actual client caching
	 **/
	public abstract BaseActualClientCacheWorker getActualClientCacheWorker() ;
	
	
	
	/**
	 * worker untuk invoke batch. di eksekusi yang paling awal dalam antruan
	 **/
	protected void runRPCWorker () {
		if ( currentBatchCount>= maxBatchRequest||requestQeue.isEmpty())
			return ;// jangan ijinkan RPC lagi kalau maks pool lg penuh
		GWT.log("form config batch requester size : " + currentBatchCount);
		currentBatchCount++;
		final FormRequestParameter param = requestQeue.get(0);
		requestQeue.remove(0);
		ApplicationConfigRPCServiceAsync.Util.getInstance();
		this.applicationConfigRPCServiceAsync = ApplicationConfigRPCServiceAsync.Util.getInstance();
				
		this.applicationConfigRPCServiceAsync.getFormConfiguration(param.getFormId(), param.getLocalizationCode() , null, new AsyncCallback<AppFormConfiguration>() {
			@Override
			public void onFailure(Throwable caught) {
				currentBatchCount--;
				runRPCWorker();// karena sudah kelar, maka request lg ke rpc. siapa tahu ada yang ngantri
				param.getOriginalCallerCallback().onFailure(caught);
			}
			@Override
			public void onSuccess(AppFormConfiguration result) {
				currentBatchCount--;
				runRPCWorker();
				param.getOriginalCallerCallback().onSuccess(result);
				submitToCache(result);
			}
		});
	}

}
