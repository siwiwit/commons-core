package id.co.gpsc.common.client.form;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.control.ResourceBundleConfigurableControl;
import id.co.gpsc.common.util.I18Utilities;
import id.co.sigma.common.util.NativeJsUtilities;
import id.co.gpsc.jquery.client.util.JQueryUtils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;



/**
 * tombol dengan i18 support
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class ExtendedButton extends Button implements ResourceBundleConfigurableControl{
	
	
	/**
	 * css name untuk button
	 **/
	public static  String DEFAULT_CSS_NAME ="button";
	
	public static String DEFAULT_CSS_DISABLED_NAME="";
	
	/**
	 * prefix ID tombol. ini untuk jqurey thing
	 **/
	protected static String BUTTON_ID_PREFIX="SIGMA_BUTTON";
	
	
	
	/**
	 * argument constructor js
	 **/
	protected JavaScriptObject constructorArgument = JavaScriptObject.createObject();
	
	public ExtendedButton(){
		super();
		setStyleName(DEFAULT_CSS_NAME);
	}
	
	public ExtendedButton(String label){
		super(label);
		setStyleName(DEFAULT_CSS_NAME);
	}
	
	public ExtendedButton(SafeHtml safeHtml){
		super(safeHtml);
		setStyleName(DEFAULT_CSS_NAME);
	}
	public ExtendedButton(SafeHtml html, ClickHandler handler){
		super(html, handler);
		//setStyleName(DEFAULT_CSS_NAME);
	}
	public ExtendedButton(String html, ClickHandler handler) {
		super(html, handler);
		//setStyleName(DEFAULT_CSS_NAME);
	}
	
	@Override
	public void setText(String text) {
		String i18Key =DOM.getElementProperty(getElement(), CommonClientControlConstant.I18_KEY_ON_DOM); 
		if (i18Key!=null&& i18Key.length()>0){
			if ( setLabelWithI18Label(i18Key))
				return ;
		}
		super.setText(text);
	}

	
	
	
	/**
	 * worker untuk set label dengan i18 enable label. jadinya ngambil dari label cache
	 * @param key key dari i18
	 * @return true = ada label yang di set ke label 
	 *  
	 **/
	private boolean setLabelWithI18Label(String key){
		if ( key!=null&&key.length()>0){
			String lbli18 =  I18Utilities.getInstance().getInternalitionalizeText(key);
			if ( lbli18!=null&&lbli18.length()>0){
				setConfiguredText(lbli18);
				return true ; 
			}
		}
		return false;
	}
	@Override
	public void setI18Key(String key) {
		DOM.setElementProperty(getElement(), CommonClientControlConstant.I18_KEY_ON_DOM, key);
		setLabelWithI18Label(key);
	}
	
	
	

	@Override
	public String getI18Key() {
		return getElement().getAttribute(CommonClientControlConstant.I18_KEY_ON_DOM);
		
	}

	@Override
	public void setConfiguredText(String text) {
		setHTML(text);
		
	}


	@Override
	protected void onAttach() {
		
		super.onAttach();
		initJQueryButton();
		getElement().removeClassName("gwt-Button"); 
	}
	
	
	/**
	 * init Jquery widget. ini 
	 **/
	protected void initJQueryButton(){
		if ( getElement().getId()==null||getElement().getId().length()==0){
			getElement().setId(BUTTON_ID_PREFIX + DOM.createUniqueId());
		}
		
		
		JQueryUtils.getInstance().renderJQueryWidget(getElement().getId(), "button", constructorArgument);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		
		//super.setEnabled(enabled);
		//initJQueryButton();
		triggerOption(getElement().getId(), enabled?"enable" : "disable");
	}
	
	
	
	/**
	 * trigger option ke dalam tombol
	 * @param  buttonId id dari tombol
	 * @param  optionValue value dari option
	 * 
	 *  
	 */
	private native void triggerOption ( String buttonId ,   String optionValue )/*-{
		$wnd.$("#" + buttonId).button(  optionValue) ; 
	
	}-*/;
	
	
	/**
	 * ini mengeset icon yang muncuk di bagian awal dari button
	 * @param cssName nama css button depan
	 **/
	public void setLeftSideIconCss(String cssName ){
		NativeJsUtilities.getInstance().putObject(constructorArgument, "icons.primary", cssName);
		initJQueryButton();
	}
	
	
	/**
	 * mengeset icon sisi belakang dari icon
	 **/
	public void setRightSideIconCss(String cssName ){
		NativeJsUtilities.getInstance().putObject(constructorArgument, "icons.secondary", cssName);
		initJQueryButton();
	}
	
	
	private boolean hideLabel =false; 
	
	
	private String leftSideIconCss;
	
	private String rightSideIconCss ; 
	/**
	 * hide label atau tidak(kalau iya berarti tanpa text)
	 **/
	public void setHideLabel(boolean hide){
		NativeJsUtilities.getInstance().putObject(constructorArgument, "text", !hide);
		initJQueryButton();
		this.hideLabel = hide;
	}
	
	
	/**
	 * hide label atau tidak(kalau iya berarti tanpa text)
	 **/
	public boolean isHideLabel() {
		return hideLabel;
	}
	
	public String getLeftSideIconCss() {
		return leftSideIconCss;
	}
 
	public String getRightSideIconCss() {
		return rightSideIconCss;
	}
}
