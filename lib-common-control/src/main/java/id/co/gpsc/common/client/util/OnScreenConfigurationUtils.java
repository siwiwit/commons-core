package id.co.gpsc.common.client.util;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.ControlCommonValueType;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.control.RawJSFormElementConfiguration;
import id.co.gpsc.common.client.form.ICommonTextboxBasedElement;
import id.co.gpsc.common.client.form.IHaveMaxLengthTextEntry;
import id.co.gpsc.common.client.form.MandatoryValidationEnabledControl;
import id.co.gpsc.common.client.form.SimpleSpanImageButton;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.widget.dialog.DialogConfigurationComboBox;
import id.co.gpsc.common.client.widget.dialog.DialogConfigurationDate;
import id.co.gpsc.common.client.widget.dialog.DialogConfigurationNumber;
import id.co.gpsc.common.client.widget.dialog.DialogConfigurationString;
import id.co.gpsc.common.data.entity.FormElementConfiguration;
import id.co.gpsc.common.util.I18Utilities;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Utils untuk mode admin on screen cofiguration
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public final class OnScreenConfigurationUtils {
	
	
	/**
	 * key untuk menaruh 
	 **/
	public static final String ON_SCREEN_BUTTON_MARKER_KEY ="ON_SCREEN_MARKER_KEY"; 
	
	private static FlowPanel SWAP_PANEL ; 
	static {
		
		SWAP_PANEL = new FlowPanel(); 
		SWAP_PANEL.setVisible(false);
		RootPanel.get().add(SWAP_PANEL);
		
	}
	
	
	/**
	 * prefix tombol on screen editor
	 **/
	private static final String ON_SCREEN_BUTTON_ID_PREFIX ="ON_SCREEN_EDITOR_" ; 
	
	private static OnScreenConfigurationUtils instance ; 
	
	
	public static OnScreenConfigurationUtils getInstance() {
		if(instance==null){
			instance = new OnScreenConfigurationUtils(); 		
		}
		return instance;
	}
	
	
	
	
	
	/**
	 * method ini untuk menghide control show hide editor icon
	 **/
	public void hideOnScreenEditorControl (OnScreenConfigurableControl control) {
		String btnId = ((Widget)control).getElement().getPropertyString(ON_SCREEN_BUTTON_MARKER_KEY);
		if ( btnId==null||btnId.length()==0)
			return ; 
		DOM.getElementById(btnId).getStyle().setDisplay(Display.NONE);
		
	}
	
	
	/**
	 * method ini untuk menghide control show hide editor icon
	 **/
	public void showOnScreenEditorControl (OnScreenConfigurableControl control) {
		String btnId = ((Widget)control).getElement().getPropertyString(ON_SCREEN_BUTTON_MARKER_KEY);
		if ( btnId==null||btnId.length()==0)
			return ; 
		DOM.getElementById(btnId).getStyle().setProperty("display", "");
		//FIXME : masukan command untuk hide button di sini
	}
	
	/**
	 * method ini di pilih untuk . reference ke button ini, id nya  ada pada control (dom) di simpan dengan key :ON_SCREEN_BUTTON_MARKER_KEY 
	 **/
	public Element renderEditoControl(final OnScreenConfigurableControl control , ControlCommonValueType controlValue){
		if (!CommonClientControlConstant.ENABLE_ON_SCREEN_ADMIN){// no admin mode allowed, nothing to render
			System.out.println("render admin mode tidak di ijinkan");
			return null ; 
		}
		String idParent = control.getParentFormConfigurationId();
		String onScreenCngId = control.getOnScreenConfigurationId() ; 
		String grpId =  control.getGroupId(); 
		
		if ( idParent==null||idParent.length()==0){
			GWT.log("control ini tidak bisa di konfigurasi , karena parent control null");
			return null; 
		}
		if ( onScreenCngId==null||onScreenCngId.length()==0){
			GWT.log("control.getOnScreenConfigurationId() null, control ini tidak bisa di configure");
			return null; 
		}
		if ( grpId==null||grpId.length()==0){
			GWT.log("group id dari control blm di set. kontrol ini tidak akan di set configureable");
			return null ; 
		}
		final Widget w = (Widget ) control; 
		String chkId =  w.getElement().getPropertyString(ON_SCREEN_BUTTON_MARKER_KEY);
		if ( chkId!=null&&chkId.length()>0){
			System.out.println("control sudah ada dalam control, keluar skr");
			return DOM.getElementById(chkId);
		}

		
		SimpleSpanImageButton btnConfigure = new SimpleSpanImageButton(CommonResourceBundle.getResources().iconConfigure() , CommonResourceBundle.getResources().iconConfigureDisabled());
		String id = btnConfigure.getElement().getId();
		SWAP_PANEL.add(btnConfigure);
		w.getElement().setPropertyString(ON_SCREEN_BUTTON_MARKER_KEY, id); 
		w.getElement().getParentElement().insertAfter(btnConfigure.getElement(), w.getElement());
						
		if ( ControlCommonValueType.stringValue.equals(controlValue)){			
			btnConfigure.addClickHandler( new Command() {				
				@Override
				public void execute() {					
					DialogConfigurationString dialog = new DialogConfigurationString(control.getParentFormConfigurationId(), control.getOnScreenConfigurationId(), control.getGroupId());
					dialog.showDialogEditorControl();									
				}
			});
		}else if(ControlCommonValueType.numberValue.equals(controlValue)) {
			btnConfigure.addClickHandler( new Command() {				
				@Override
				public void execute() {
					DialogConfigurationNumber dialog = new DialogConfigurationNumber(control.getParentFormConfigurationId(), control.getOnScreenConfigurationId(), control.getGroupId());
					dialog.showDialogEditorControl();
				}
			});
		}else if(ControlCommonValueType.comboBoxValue.equals(controlValue)) {
			btnConfigure.addClickHandler( new Command() {				
				@Override
				public void execute() {
					DialogConfigurationComboBox dialog = new DialogConfigurationComboBox(control.getParentFormConfigurationId(), control.getOnScreenConfigurationId(), control.getGroupId());
					dialog.showDialogEditorControl();
				}
			});
		}else if(ControlCommonValueType.dateValue.equals(controlValue)) {
			System.out.println("Form ID : " + control.getParentFormConfigurationId());
			System.out.println("Element ID : " + control.getOnScreenConfigurationId());
			
			btnConfigure.addClickHandler( new Command() {				
				@Override
				public void execute() {
					
					DialogConfigurationDate dialog = new DialogConfigurationDate(control.getParentFormConfigurationId(), 
								control.getOnScreenConfigurationId(), 
								control.getGroupId());
					dialog.showDialogEditorControl();
				}
			});
		}
		System.out.println("Class : " + control.getClass());
		return btnConfigure.getElement()  ; 
		
	} 
	
	
	
	/**
	 * worker untuk apply configuration. ini facade untuk semua
	 **/
	public void applyConfiguration(OnScreenConfigurableControl control , IFormElementConfiguration formConfiguration) {
		if ( control instanceof MandatoryValidationEnabledControl){
			MandatoryValidationEnabledControl mdCntrl = (MandatoryValidationEnabledControl)control; 
			mdCntrl.setMandatory(formConfiguration.getMandatory()!=null? formConfiguration.getMandatory().booleanValue(): false);
		}
		if ( control instanceof ICommonTextboxBasedElement && formConfiguration.getPlaceHolderI18NKey()!=null&&formConfiguration.getPlaceHolderI18NKey().length()>0){
			ICommonTextboxBasedElement plc = (ICommonTextboxBasedElement)control;
			plc.setPlaceholder(I18Utilities.getInstance().getInternalitionalizeText(formConfiguration.getPlaceHolderI18NKey(), ""));
		}
		if ( control instanceof  IHaveMaxLengthTextEntry && formConfiguration.getMaxLength()!=null&& formConfiguration.getMaxLength()>0){
			IHaveMaxLengthTextEntry mx = (IHaveMaxLengthTextEntry)control;
			mx.setMaxLength(formConfiguration.getMaxLength());
		}
		String hint = I18Utilities.getInstance().getInternalitionalizeText(formConfiguration.getHintI18NKey());
		if ( hint!=null&&hint.length()>=0){
			((Widget)control).setTitle(hint);
		}
		else{
			((Widget)control).setTitle("");
		}
		//FIXME: masukan limiter untuk date picker dan number
		
	}
	
	
	
	
	
	
	/**
	 * hub method.akses ke form config, dengan id form + id element
	 **/
	public IFormElementConfiguration getFormConfiguration(String containerPanelId , String elementId ) {
		return RawJSFormElementConfiguration.getConfigurationById(FormElementConfiguration.generateDataKey(containerPanelId, elementId));
	}
	
	
	/**
	 * Generate button with icon
	 * @param buttonId
	 * @param cssClass
	 */
	protected native void generateButtonWithIcon (String buttonId , String cssClass)/*-{		
		 $wnd.$("#"+ buttonId).button({
            icons: {
                primary: cssClass
         	},
         	text: false
         })         
	}-*/;
	
	/**
	 * Click handler dari button editor
	 * @param buttonId
	 * @param clickHandler
	 */
	protected native void bindClickHandler (String buttonId , Command clickHandler)/*-{
		$wnd.$("#" + buttonId).click(function () {			
			clickHandler.@com.google.gwt.user.client.Command::execute()();
		});
	}-*/;
}