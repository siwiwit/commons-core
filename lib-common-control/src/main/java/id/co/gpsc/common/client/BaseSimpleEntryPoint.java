package id.co.gpsc.common.client;

import java.util.ArrayList;

import id.co.gpsc.common.client.control.i18.AppPanelResourceCacheManager;
import id.co.gpsc.common.client.control.i18.DefaultAppPanelResourceCacheManagerImpl;
import id.co.gpsc.common.client.control.i18.I18ReloaderControllerPanel;
import id.co.gpsc.common.client.dualcontrol.DualControlUtil;
import id.co.gpsc.common.client.dualcontrol.IDualControlEditorManager;
import id.co.gpsc.common.client.dualcontrol.ISimpleApprovalPanelManager;
import id.co.gpsc.common.client.dualcontrol.SimpleApprovalUtil;
import id.co.gpsc.common.client.security.MenuHandlerPanelGeneratorGroup;
import id.co.gpsc.common.client.security.rpc.UserRPCServiceAsync;
import id.co.gpsc.common.client.util.ClientSideDateTimeParser;
import id.co.gpsc.common.client.util.ClientSideSimpleDebugerWriterManager;
import id.co.gpsc.common.client.util.ClientSideWrappedJSONParser;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.client.widget.BaseSimpleComposite;
import id.co.gpsc.common.control.I18TextProvider;
import id.co.gpsc.common.rpc.ManagedAsyncCallback;
import id.co.gpsc.common.security.dto.UserDetailDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.common.util.ObjectGeneratorManager;
import id.co.gpsc.common.util.json.SharedServerClientLogicManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;



/**
 * Entry point dengan penambahan-penambahan tertentu version of entry point
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class BaseSimpleEntryPoint implements EntryPoint{
	
	
	
	/**
	 * command di native js load internalization. ini di taruh di level window, jadinya method untuk load internalization bisa di load juga
	 **/
	public static final String LOAD_INTERNALIZATION_METHOD ="loadInternalizationCatalog"; 
	

	
	
	/**
	 * method untuk menaruh base application url. yang tahu adalah servlet, dari client agak ada beberapa kombinasi yang bisa membuat proses ini sulit
	 **/
	public static final String WRITE_BASE_APP_URL_METHOD ="assignApplicationBaseUrl";
	
	
	/**
	 * data user detail data. di load pada saat awal load
	 **/
	protected UserDetailDTO userDetailData ; 
	
	
	
	protected ArrayList< MenuHandlerPanelGeneratorGroup> cachedMenuGenerator  = new ArrayList<MenuHandlerPanelGeneratorGroup>(); 
	
	
	
	
	/**
	 * panel untuk reload panel
	 **/
	protected I18ReloaderControllerPanel i18ReloaderPanel ; 
	
	/**
	 * di override+di buat menjadi final karena ada sejumlah task yang di perlukan pada saat startup
	 **/
	@Override
	public final void onModuleLoad() {
		new  Timer() {
			
			@Override
			public void run() {
				ManagedAsyncCallback.LOGIN_SCREEN_URL = getLoginUrl(); 
				
			}
		}.schedule(5000);;
		
		
		try{
			registerLoadI18AndHideReloaderPanel(LOAD_INTERNALIZATION_METHOD);
			registerAssignPageBaseUrl(WRITE_BASE_APP_URL_METHOD);
			
			CommonClientControlUtil.getInstance().setUseDotForThousandSeparator(isUseDotTousandSeparator());
			additionalOnLoadHandler();
			AppPanelResourceCacheManager.Util.setInstance((AppPanelResourceCacheManager) GWT.create(  DefaultAppPanelResourceCacheManagerImpl.class));
			
			
			SharedServerClientLogicManager.getInstance().setJSONParser(  ClientSideWrappedJSONParser.getInstance()); 
			SharedServerClientLogicManager.getInstance().setDateTimeParser(ClientSideDateTimeParser.getInstance());
			SharedServerClientLogicManager.getInstance().setDebugerWriterManager(new ClientSideSimpleDebugerWriterManager());
			//FIXME: mohon di buatkan object generator
			
			
			 
			registerObjectGenerators(ObjectGeneratorManager.getInstance());
			registerDualControlledEditorHandlers(DualControlUtil.getInstance());
			renderApprovalHandlerPanel(SimpleApprovalUtil.getInstance());
			//SharedServerClientLogicManager.getInstance().ge
			
			
			//FIXME : masukan kode untuk membaca konfigurasi dari server
			I18ReloaderControllerPanel pnl = new I18ReloaderControllerPanel();
			
			i18ReloaderPanel = pnl ; 
			
			I18Utilities.getInstance().setI18TextProvider(new I18TextProvider() {
				
				@Override
				public String getText(String key) {
					return CommonClientControlUtil.getInstance().getStringResource(key);
				}
			});
			
			
			RootPanel.get().add(pnl);
			
			if ( GWT.isProdMode()){
				
				//FIXME: masukan kode untuk load localization di sini
				customOnLoadHandler();
				pnl.setVisible(false);
				}
			else{
				pnl.setAfterPreparationDoneCommand(new Command() {
					
					@Override
					public void execute() {
						customOnLoadHandler();
					}
				});
			}
		}catch ( Exception exc){
			Window.alert("gagal trigger load method. error : " + exc.getMessage());
			exc.printStackTrace(); 
		}
		
	}
	
	
	
	
	
	/**
	 * ini anda perlu override kalau anda mempergunakan JSON RPC di bandingkan dengan built in RPC dari GWT. 
	 **/
	protected void registerObjectGenerators(final ObjectGeneratorManager objectManager) {
		GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onSuccess() {
				objectManager.registerGenerator(new id.co.gpsc.common.data.serializer.LibCommonObjectGenerator());
				objectManager.registerGenerator(new id.co.gpsc.common.data.serializer.LibSharedObjectGenerator());
				objectManager.registerGenerator(new id.co.gpsc.common.security.SecuritySharedObjectGenerator());
				objectManager.registerGenerator(new id.co.gpsc.common.security.domain.SecurityDataObjectGenerator());
				loadLoginInformationData();
			}
			
			@Override
			public void onFailure(Throwable reason) {
				if ( Window.confirm( "gagal load object manager shared object. Ini mungkin di sebabkan karena bandkwidth yang tidak memadai.\nDi sarankan anda untuk mereload browser anda. Apakah anda mau mereload sekarang?")){
					Window.Location.reload(); 
				}
			}
		});
		
		
	}
	
	
	
	/**
	 * load login data. ini di trigger setelah object generator complete di load. kalau tidak ini bisa memicu error 
	 **/
	protected void loadLoginInformationData () {
		UserRPCServiceAsync.Util.getInstance().getCurrentUserLogin(new AsyncCallback<UserDetailDTO>() {
			@Override
			public void onFailure(Throwable exception) {
				Window.alert("gagal membaca user login info. error : " + exception.getMessage()); 
				
			}
			
			public void onSuccess(UserDetailDTO userData) {
				userDetailData = userData ; 
                                BaseSimpleComposite.userDetail = userData ; 
                                        
				additionalTaskOnUserDetailDataAccepted(userDetailData); 
				requestMenuData(); 
			};
		});
	}
	
	
	
	
	
	/**
	 * di sini akan di register, panel apa saja yang bertugas me-respon terhadap penekanan tombol approval. apa yang perlu anda lakukan : <br/>
	 * pangggil <code><br/>
	 * approvalHandlerManer.register(new panelXX);<br/>
	 * approvalHandlerManer.register(new panelYYY);<br/>
	 * dst .......
	 * </code>
	 * jadinya di dalam app and ada apa saja panel handler approval, anda wajib masukan di sini
	 **/
	protected void renderApprovalHandlerPanel (ISimpleApprovalPanelManager approvalHandlerManer ) {
		
	}
	
	
	
	
	/**
	 * register dual control editor. di sini anda perlu mendaftarkan dual control editor, agar approval panel berjalan normal
	 **/
	protected void registerDualControlledEditorHandlers (IDualControlEditorManager editorManager) {
		
	}
	
	/**
	 * di panggil paling awal, pergunakan ini kalau memerlukan tambahan task on load
	 **/
	protected void additionalOnLoadHandler(){
		
	
	}
	
	/**
	 * pekerjaan di sini : 
	 * <ol>
	 * <li>request menu via RPC</li>
	 * <li>render dom untuk menu</li>
	 * <li>render menu</li>
	 * </ol>
	 **/
	protected void requestMenuData () {
		//TODO: kalau diperlukan di level sini handler menu request masukan di sini
	}
	/**
	 * pengganti {@link #onModuleLoad()}.  actual task , spesifik ke aplikasi di terapkan di sini
	 **/
	protected abstract void customOnLoadHandler() ; 
	
	
	
	
	/**
	 * pls fill this. ini pasti akan tergantung pada internalzition yang di pergunakan user. kalau misalnya indonesa ini = <i>true</i>, sebaliknya en : false(thousand separator meraka =,)
	 **/
	protected abstract boolean   isUseDotTousandSeparator  () ; 
	
	/**
	 * pekerjaan ini di trigger kalau request untuk data(data login) dari server di terima. pekerjaan nya misal nya :<br/>
	 * <ol>
	 * 
	 * <li>menampilkan user name</li>
	 * <li>menampilkan cabang user</li>
	 * <li>menampilkan tanggal applikasi</li>
	 * </ol> 
	 * 
	 **/
	protected   void additionalTaskOnUserDetailDataAccepted (UserDetailDTO userData) {
		
	}
	
	
	
	
	
	/**
	 * request i18n code utnuk reload
	 **/
	protected void loadI18AndHideReloaderPanel(String i18NCode){
		this.i18ReloaderPanel.loadLanguageAndHide(i18NCode);
	}
	
	
	
	/**
	 * set base applicaiton URL
	 **/
	protected   void assignPageBaseUrl (String baseAppUrl   ){
		CommonClientControlUtil.getInstance().setApplicationBaseUrl(baseAppUrl);
	}
	
	
	
	/**
	 * ini di isikan dari raw javascript. kalau path aplikasi lumayan dalam, maka url dari aplikasi sebaiknya di set dari luar
	 **/
	protected native void registerAssignPageBaseUrl (String methodName  )/*-{
		var thisSwap = this ;
		$wnd[methodName] = function (baseAppUrl){
			thisSwap.@id.co.gpsc.common.client.BaseSimpleEntryPoint::assignPageBaseUrl(Ljava/lang/String;)(baseAppUrl);
		}
	}-*/;
	/**
	 * pasang load language di raw js
	 **/
	protected native void registerLoadI18AndHideReloaderPanel(String methodName  )/*-{
		var thisSwap = this ; 
		$wnd[methodName] = function (langToLoad){
			thisSwap.@id.co.gpsc.common.client.BaseSimpleEntryPoint::loadI18AndHideReloaderPanel(Ljava/lang/String;)(langToLoad);
		}
	}-*/; 
	
	
	
	/**
	 * url untuk login. ini di pergunakan untuk meredirect halaman kalau login sudah habis. sebaiknya di sertakan full url . 
	 * misal nya : http://app-gwt/app/login.jsp etc. Sebab kalau tidak di sertakan full, bisa salah path
	 **/
	public abstract String getLoginUrl ()  ; 
	
	
	

}
