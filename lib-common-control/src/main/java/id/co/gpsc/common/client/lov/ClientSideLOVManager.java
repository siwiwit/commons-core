package id.co.gpsc.common.client.lov;


import id.co.gpsc.common.client.app.JSONFriendlyCommonLOVHeader;
import id.co.gpsc.common.client.cache.ClientObjectCacheWrapper;
import id.co.gpsc.common.client.control.ILOVCapableControl2ndLevel;
import id.co.gpsc.common.client.rpc.LOVProviderRPCServiceAsync;
import id.co.gpsc.common.client.rpc.SimpleAsyncCallback;
import id.co.gpsc.common.data.lov.Common2ndLevelLOVHeader;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOV2ndLevelRequestArgument;
import id.co.gpsc.common.data.lov.LOVRequestArgument;
import id.co.gpsc.common.data.lov.LOVSource;
import id.co.gpsc.common.data.lov.StrongTyped2ndLevelLOVID;
import id.co.gpsc.common.data.lov.StrongTypedCustomLOVID;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;











import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;


/**
 * LOV manager
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public  class ClientSideLOVManager implements IClientSideLOVManager {
	
	
	private static ClientSideLOVManager instance ; 
	
	
	
	
	private static int CACHE_LIVE_TIME = 60*120; 
	
	
	public static ClientSideLOVManager getInstance() {
		if ( instance==null)
			instance=new ClientSideLOVManager();
		return instance;
	}
	
	class LOVListenerWrapper {
		private LOVChangeHandler handler ; 
		private Widget parent; 
		private LOVSource lovSource;
		
		
		
		public LOVChangeHandler getHandler() {
			return handler;
		}
		public void setHandler(LOVChangeHandler handler) {
			this.handler = handler;
		}
		public Widget getParent() {
			return parent;
		}
		public void setParent(Widget parent) {
			this.parent = parent;
		}
		public LOVSource getLovSource() {
			return lovSource;
		}
		public void setLovSource(LOVSource lovSource) {
			this.lovSource = lovSource;
		} 
		
		
		
		
		
	}
	
	
	
	
	private LOVCacheManager cacheManager  ; 
	
	
	
	HashMap<LOVSource, HashMap<String , ArrayList<LOVListenerWrapper>>> listenerMap = new HashMap<LOVSource, HashMap<String,ArrayList<LOVListenerWrapper>>>(); 
	
	
	
	
	public ClientSideLOVManager(){
		cacheManager = new LOVCacheManager(); 
		cacheManager.loadCacheDefinitionFromStorage(); 
		
	}
	
	
	
	

	
	
	
	/**
	 * generate actual LOV ID
	 **/
	public String generateActualLOVID(StrongTypedCustomLOVID lovId){
		return lovId.getModulGroupId()+"." + lovId.getId();
	}
	
	
	/* (non-Javadoc)
	 * @see id.co.gpsc.common.client.lov.IClientSideLOVManager#registerLOVChangeListener(com.google.gwt.user.client.ui.Widget, id.co.gpsc.common.client.lov.LOVCapabledControl)
	 */
	@Override
	public HandlerRegistration registerLOVChangeListener(Widget parent , LOVCapabledControl control){
		return registerLOVChangeListener(parent, control.getParameterId(), control.getLOVSource()	, control);    
	}
	
	/* (non-Javadoc)
	 * @see id.co.gpsc.common.client.lov.IClientSideLOVManager#registerLOVChangeListener(com.google.gwt.user.client.ui.Widget, java.lang.String, id.co.gpsc.common.data.lov.LOVSource, id.co.gpsc.common.client.lov.LOVChangeHandler)
	 */
	@Override
	public HandlerRegistration registerLOVChangeListener(Widget parent ,final  String id ,final LOVSource lovSource  ,final  LOVChangeHandler handler) {
		if ( !listenerMap.containsKey(lovSource)){
			HashMap<String , ArrayList<LOVListenerWrapper>> baru=new HashMap<String, ArrayList<LOVListenerWrapper>>(); 
			listenerMap.put(lovSource, baru); 
		}
		if ( !listenerMap.get(lovSource).containsKey(id)){
			 ArrayList<LOVListenerWrapper> list = new ArrayList<ClientSideLOVManager.LOVListenerWrapper>(); 
			 listenerMap.get(lovSource).put(id, list); 
		}
		final LOVListenerWrapper w = new LOVListenerWrapper();
		w.setParent(parent); 
		w.setHandler(handler); 
		w.setLovSource(lovSource); 
		 listenerMap.get(lovSource).get(id).add(w); 
		 
		return new HandlerRegistration(){
			@Override
			public void removeHandler() {
				if ( !listenerMap.containsKey(lovSource))
					return ; 
				if ( !listenerMap.get(lovSource).containsKey(id))
					return ; 
				listenerMap.get(lovSource).get(id).remove(w); 
				
			}
		} ; 
	} 

	
	
	/* (non-Javadoc)
	 * @see id.co.gpsc.common.client.lov.IClientSideLOVManager#requestLOVUpdate(java.lang.String, com.google.gwt.user.client.ui.Widget, java.util.Collection)
	 */
	@Override
	public void requestLOVUpdate (String localeCode ,  Widget parent , Collection<LOVRequestArgument> lovIdList ){
		requestLOVUpdate(localeCode, parent, lovIdList, null);
	}
	
	
	/**
	 * request LOV. data Lookup akan di inject ke dalam control melalui method : {@link ILOVCapableControl2ndLevel#renderLookupData(Common2ndLevelLOVHeader)}. kalau anda memerlukan pekerjaan tambahan pada saat load Lookup selesai, anda cukup menyediakan command untuk afterLoadDoneHandler
	 * @param control control yang memerlukan Lookup. value parent akan di masukan di sini
	 * @param afterLoadDoneHandler task setelah load Lookup selesai 
	 **/
	public void  requestLOV(final ILOVCapableControl2ndLevel control ,final Command afterLoadDoneHandler){
		
		final String rawId = control.getLovParameterId().getId();
		LOV2ndLevelRequestArgument reqArg = new LOV2ndLevelRequestArgument(); 
		reqArg.setLookupId(control.getLovParameterId());
		reqArg.setParentLovValueId(control.getParentLookupValue());
		
		LOVProviderRPCServiceAsync.Util.getInstance().getModifiedLOV(I18Utilities.getInstance().getCurrentLocalizationCode(), reqArg, new SimpleAsyncCallback<Common2ndLevelLOVHeader>() {
			@Override
			public void onSuccess(Common2ndLevelLOVHeader result) {
				control.renderLookupData(result);
				if (afterLoadDoneHandler!=null)
					afterLoadDoneHandler.execute();
			}

			@Override
			protected void customFailurehandler(Throwable caught) {
				NativeJsUtilities.getInstance().writeToBrowserConsole("gagal membaca lookup dengan id :" + rawId + ",error message :" + caught.getMessage());
				caught.printStackTrace();
				if (afterLoadDoneHandler!=null)
					afterLoadDoneHandler.execute();
				
			}
		
		});
	}
	
	
	
	
	/**
	 * versi ini mentahan nya. baca dari cache atau lewat RPC. pintu nya dari sini
	 * @param parentValue value parent Lookup
	 * @param lovId id dari lookup
	 * @param  callback ini handle utnuk menerima data
	 * 
	 **/
	public void  requestLOV(final String parentValue ,@SuppressWarnings("rawtypes") StrongTyped2ndLevelLOVID lovId , SimpleAsyncCallback<Common2ndLevelLOVHeader> callback){
		
		
		LOV2ndLevelRequestArgument reqArg = new LOV2ndLevelRequestArgument(); 
		reqArg.setLookupId(lovId);
		reqArg.setParentLovValueId(parentValue);
		
		LOVProviderRPCServiceAsync.Util.getInstance().getModifiedLOV(I18Utilities.getInstance().getCurrentLocalizationCode(), reqArg,callback);
	}
	
	
	
	/**
	 * ini membaca 2nd level LOV. yang 1 level di ambil semua, di cari dengan id dari lookup
	 **/
	public void requestLOV2ndLevelByCurrentLookupValue (String parentLOVId ,StrongTyped2ndLevelLOVID lookupTypeId ,SimpleAsyncCallback<Common2ndLevelLOVHeader> callback){
		//FIXME : baca dari cache di sini. seharusnya ini ada pengecekan ke cache dan ada version nya
		LOV2ndLevelRequestArgument lovArg = new LOV2ndLevelRequestArgument(); 
		lovArg.setParentLovValueId(parentLOVId);
		lovArg.setLookupId(lookupTypeId);
		LOVProviderRPCServiceAsync.Util.getInstance().getSameParent2ndLevelLOV(I18Utilities.getInstance().getCurrentLocalizationCode(), lovArg ,callback);
	}
	
	
	@Override
	public void requestLOVUpdate(String localeCode, Widget parent,
			Collection<LOVRequestArgument> lovIdList,
			final Command commandAfterComplete) {
		if ( lovIdList==null||lovIdList.isEmpty()){
			if(commandAfterComplete!=null)
				commandAfterComplete.execute();
			return  ; 
		}	
		
		if ( this.cacheManager.isSupportHTML5Storage()){
			html5SupportedLOVLoadRequest(localeCode, parent, lovIdList, commandAfterComplete);
		}else{
			ArrayList<LOVRequestArgument> swaps = new ArrayList<LOVRequestArgument>();
			swaps.addAll(lovIdList); 
			queRequestToServer( localeCode ,  swaps, null, commandAfterComplete); 
		}
		
		
		
	}
	
	
	
	
	
	/**
	 * handler untuk request LOV dalam kasus HTML 5 ada dukungan
	 * @param localeCode kode i18n
	 */
	protected void html5SupportedLOVLoadRequest(String localeCode, Widget parent,
			Collection<LOVRequestArgument> lovIdList,
			final Command commandAfterComplete) {
		ArrayList<LOVRequestArgument> notFoundOrExpired = new ArrayList<LOVRequestArgument>();
		ArrayList<LOVRequestArgument> outOfSyncList = new ArrayList<LOVRequestArgument>();
		
		ArrayList<String> alreadyInRequest = new ArrayList<String>() ; 
		for ( LOVRequestArgument scn : lovIdList){
			// hindari request yang sama
			String smallKey = scn.getId()+ scn.getSource().toString(); 
			if ( alreadyInRequest.contains(smallKey))
				continue ; 
			alreadyInRequest.add(smallKey); 
			
			
			
			
			LOVCacheDefinition def =  cacheManager.getCachedLOVDefinition(scn.getSource(), localeCode, scn.getId()); 
			
			ClientObjectCacheWrapper<JSONFriendlyCommonLOVHeader> cachedData =
						def!=null?cacheManager.getDataFromCache(scn.getId(), localeCode, scn.getSource())  :null ;
			if ( cachedData==null||cachedData.checkIsExpired(CACHE_LIVE_TIME)){
				 
				if (cachedData!=null ){
					outOfSyncList.add(scn); 
					scn.setCacheVersion(def.getVersion());
				} 
				notFoundOrExpired.add(scn); 
				continue ; 
			}
			
			// ok nemu di cache, trigger saja listener(pada widget pemanggil only) untuk update data mereka
			
			fireLOVChangeEvent(parent ,cachedData.getObjectToCache()); 
		}
		if (outOfSyncList.isEmpty()&&notFoundOrExpired.isEmpty()){
			if(commandAfterComplete!=null)
				commandAfterComplete.execute();
			return ;
		}
		queRequestToServer( localeCode ,  notFoundOrExpired, outOfSyncList, commandAfterComplete); 
	}
	
	
	/**
	 * submit request lov load ke server
	 * @param localeCode kode internalization dari language
	 * @param lovIdList id lov yang perlu di load ( ini yang benar-benar belum ada)
	 * 
	 **/
	protected void queRequestToServer (final  String localeCode ,  final List<LOVRequestArgument> lovIdList  , final List<LOVRequestArgument> outOfSyncList , final Command commandAfterUpdaeComplete){
		
		if ( lovIdList==null|| lovIdList.isEmpty()){// kosong, skip saja
			if ( commandAfterUpdaeComplete!=null)
				commandAfterUpdaeComplete.execute();
			return ; 
		}
		final HashMap<String, LOVRequestArgument> indexedOutOfSync = new HashMap<String, LOVRequestArgument>(); 
		if ( outOfSyncList!=null && !outOfSyncList.isEmpty()){
			for  (LOVRequestArgument scn : outOfSyncList){
				indexedOutOfSync.put(scn.getId(), scn); 
			}
		}
		//FIXME: enhancement : idealnya ini antrian. maksimal 2 - 3 request paralel
		
		
		
		
		
		
		final AsyncCallback<List<CommonLOVHeader>> callback = new AsyncCallback<List<CommonLOVHeader>>() {
			
			@Override
			public void onSuccess(List<CommonLOVHeader> result) {
						
				if ( result!=null && !result.isEmpty()){
					for (CommonLOVHeader scn : result ){
						
						scn.setI18Key(localeCode);	
						cacheManager.submitToCache( scn);// cache data 
						fireLOVChangeEvent(null, scn); 
						// yang ada update di marker agar tidak ada update lease asal
						if ( indexedOutOfSync.containsKey(scn.getLovId())){
							outOfSyncList.remove(indexedOutOfSync.get(scn.getLovId()));
						}
					}
				}
				// yang out of sync menurut client, cuma menurtu server masih up to date di update 
				if ( outOfSyncList!=null && !outOfSyncList.isEmpty()){
					updateCacheCreateTime(localeCode ,  outOfSyncList); 
				}
				//fire complete notification
				if ( commandAfterUpdaeComplete!=null)
					commandAfterUpdaeComplete.execute();
			}
			
			@Override
			public void onFailure(Throwable fault) {
				fault.printStackTrace();
				if ( commandAfterUpdaeComplete!=null)
					commandAfterUpdaeComplete.execute();
				Window.alert("gagal membaca data list of value dari server.error di laporkan : " + fault.getMessage());
			}
		};

		
		LOVProviderRPCServiceAsync.Util.getInstance().getModifiedLOVs(localeCode ,  lovIdList, callback);
		 
	}
	
	
	
	
	
	/**
	 * update cache time agar bisa di pakai 120 menit berikutnya
	 * @param cacheToUpdateId daftar id cache yang perlu di update
	 **/
	protected void updateCacheCreateTime(String internalizationCode ,  final List<LOVRequestArgument> cacheToUpdateId ){
		//FIXME: masukan implementasi !!! 
		
	}
	
	
	
	/**
	 * notifikasi object ada LOV berubah, agar control merender ulang 
	 **/
	private void fireLOVChangeEvent (Widget parent , CommonLOVHeader lovData){
		if ( lovData.getSource()==null ){
			GWT.log("LOVFRAMEWORK: LOV source is null:");
			return ; 
		}
		if (  !listenerMap.containsKey(lovData.getSource())){
			GWT.log("LOVFRAMEWORK: no listener for :" + lovData.getSource() +",LOV id :" + lovData.getLovId() +"," + lovData.getSource() + " tidak di teruskan");
			return ; 
		}	
		
		 ArrayList<LOVListenerWrapper> listeners =  listenerMap.get(lovData.getSource()).get(lovData.getLovId());
		 if ( listeners==null|| listeners.isEmpty()){
			 GWT.log("LOVFRAMEWORK: kosong untuk : " + lovData.getLovId());
			 return ; 
		 }	 
		 
		 if ( parent==null){
			  
			 for(LOVListenerWrapper scn : listeners){
				 scn.getHandler().onLOVChange(lovData); 
			 } 
		 }
		 else{
			  
 			 int counter = 0 ; 
			 for(LOVListenerWrapper scn : listeners){
				 if ( parent.equals(scn.getParent())){
					 scn.getHandler().onLOVChange(lovData);
					 counter++; 
				 }
			 }
			 if ( counter==0){
				  
			 }
		 }
		 
		 
	}
	
	/**
	 * singleton instance
	 **/
	/*public static ClientSideLOVManager getInstance() {
		if (instance==null)
			instance=new ClientSideLOVManager();
		return instance;
	}
*/
}
