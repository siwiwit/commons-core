package id.co.gpsc.common.client.control.i18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.rpc.ApplicationConfigRPCServiceAsync;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.data.CommonLibraryConstant;
import id.co.gpsc.common.data.entity.I18Code;
import id.co.gpsc.common.util.I18Utilities;

import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;



/**
 * helper i18 configurations
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class I18ReloaderControllerPanel extends Composite {

	private static I18ReloaderControllerPanelUiBinder uiBinder = GWT
			.create(I18ReloaderControllerPanelUiBinder.class);
	@UiField ListBox lblLanguages;
	@UiField ExtendedButton btnReloadLanguage;
	
	@UiField Button btnLoadApp; 
	
	
	
	/**
	 * command setelah preparation selesai di lakukan
	 **/
	private Command afterPreparationDoneCommand ; 
	
	
	private Map<String, String> indexedDecimalSeparator = new HashMap<String, String>(); 

	interface I18ReloaderControllerPanelUiBinder extends
			UiBinder<Widget, I18ReloaderControllerPanel> {
	}

	public I18ReloaderControllerPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		new Timer() {
			
			@Override
			public void run() {
				GWT.log("start loading languages");
				ApplicationConfigRPCServiceAsync.Util.getInstance().getAvaliableLanguages(new AsyncCallback<List<I18Code>>() {
					
					@Override
					public void onSuccess(List<I18Code> result) {
						lblLanguages.clear();
						indexedDecimalSeparator.clear();
						if ( result==null||result.isEmpty()){
							GWT
							.log("languages list empty");
							return ;
						}
						GWT.log("to fill " + result.size());
						for ( I18Code scn : result){
							GWT.log("append " +scn.getCode()+" - " + scn.getDescription()  );
							lblLanguages.addItem(scn.getCode()+" - " + scn.getDescription(), scn.getCode());
							indexedDecimalSeparator.put(scn.getCode(), scn.getDecimalSeparator());
						}
									
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						GWT.log(caught.getMessage() , caught);
						
					}
				});
				
				putReloader("reloadResourceBundle");
				
			}
		}.schedule(500);
		
	}
	
	
	@UiHandler("btnLoadApp")
	void onBtnLoadApplicationClick(ClickEvent event) {
		if ( this.afterPreparationDoneCommand!=null)
			afterPreparationDoneCommand.execute();
		
	}
	
	
	private native void putReloader (String methodName)/*-{
		var swapThis = this ; 
		$wnd[methodName]=function (localeCode){
			swapThis.@id.co.gpsc.common.client.control.i18.I18ReloaderControllerPanel::reloadWorker(Ljava/lang/String;)(localeCode);
		
		}
	
	}-*/;

	@UiHandler("btnReloadLanguage")
	void onBtnReloadClick(ClickEvent event) {
		if ( lblLanguages.getSelectedIndex()<0)
			return ;
		String localeCode = lblLanguages.getValue(lblLanguages.getSelectedIndex());
		GWT.log(")))" + GWT.getModuleName());
		reloadWorker(localeCode);
		
	}
	
	
	protected void reloadWorker (String localeCode ){
		String url = CommonClientControlUtil.getInstance().getApplicationBaseUrl()+   CommonLibraryConstant.APPLICATION_CONFIG_SERVICE_BASE_URL  +    "/" +localeCode+ "/i18-groups.json";
		
		
		I18Utilities.getInstance().setCurrentLocalizationCode(localeCode);
		CommonClientControlUtil.getInstance().setUseDotForThousandSeparator(!".".equals(indexedDecimalSeparator.get(localeCode)));
		fetchScript(url);
		Window.alert("reload language selesai");
	}
	
	
	/**
	 * load language dan hide panel 
	 **/
	public void loadLanguageAndHide (String localeCode) {
		String url = CommonClientControlUtil.getInstance().getApplicationBaseUrl()+  CommonLibraryConstant.APPLICATION_CONFIG_SERVICE_BASE_URL  +  "/" +localeCode+ "/i18-groups.json";
		
		I18Utilities.getInstance().setCurrentLocalizationCode(localeCode);
		CommonClientControlUtil.getInstance().setUseDotForThousandSeparator(!".".equals(indexedDecimalSeparator.get(localeCode)));
		fetchScript(url);
		setVisible(false);
		
		if ( afterPreparationDoneCommand!= null)
			afterPreparationDoneCommand.execute(); 
	}
	

	@Override
	protected void onAttach() {
		super.onAttach();
		makeDragable();
	}
	/**
	 * make it dragable
	 **/
	private native void makeDragable()/*-{
		$wnd.$( "#gpsc_i18_reloader_panel" ).draggable();
	
	}-*/;
	
	
	private native void fetchScript(String scriptUrl)/*-{
		$wnd.$.ajax({ 	
				type: "GET", 	
				global: true, 	
				url: scriptUrl, 	
				async: false, 	
				dataType: "script" });
		
	}-*/;
	/**
	 * load scripts 
	 * @param scriptUrl url js
	 **/
	private native void fetchScript1(String scriptUrl)/*-{
		(function() {
		    var s = document.createElement('script');
		    s.type = 'text/javascript';
		    s.async = true;
		    s.src = scriptUrl;
		    var x = document.getElementsByTagName('script')[0];
		    x.parentNode.insertBefore(s, x);
		})();
		
	
	}-*/;

	/**
	 * command setelah preparation selesai di lakukan
	 **/
	public void setAfterPreparationDoneCommand(Command afterPreparationDoneCommand) {
		this.afterPreparationDoneCommand = afterPreparationDoneCommand;
	}
}
