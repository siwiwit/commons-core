package id.co.gpsc.common.client.form;

import id.co.gpsc.common.client.control.ChildSelfRegisterAggregator;
import id.co.gpsc.common.client.control.ChildSelfRegisterWorker;
import id.co.gpsc.common.client.control.ControlCommonValueType;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.ITransformableToReadonlyLabel;
import id.co.gpsc.common.client.control.MandatoryControlSelfRegisterWorker;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.control.ResourceAwareControlSelfRegisterWorker;
import id.co.gpsc.common.client.form.advance.AdvanceControlUtil;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;
import id.co.gpsc.common.util.I18Utilities;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.TextArea;

public class ExtendedTextArea extends TextArea implements MandatoryValidationEnabledControl, 
	OnScreenConfigurableControl , ICommonTextboxBasedElement, ITransformableToReadonlyLabel{

	
	
	/**
	 * REGISTER_ON_ATTACH = true , komponen akan di register ke parent pada saat widget di attach ke parent
	 **/
	public static boolean REGISTER_ON_ATTACH =true ;
	protected String i18Key ; 
	protected boolean mandatory ; 
	
	
	@SuppressWarnings("unchecked")
	private ChildSelfRegisterAggregator<ExtendedTextArea> childSelfRegisterAggregator= 
			new ChildSelfRegisterAggregator<ExtendedTextArea>(new ChildSelfRegisterWorker[]{
				new MandatoryControlSelfRegisterWorker(), 
				new ResourceAwareControlSelfRegisterWorker()
			}, this);  
	
	
	/**
	 * key press, keydown etc yang di register. perlu di detach semua kalau max length null
	 **/
	private HandlerRegistration[] registrations ; 
	
	
	
	/**
	 * visualizer max length
	 **/
	private Element remainingCharVisualizer ; 
	/**
	 * panjang max text. <1 atau null = no limit
	 **/
	protected Integer maxLength ; 
	
	private boolean attached =false ; 
	
	
	
	/**
	 * kalau karakter ke sisia beraapa visualizer akan di tampilkan
	 **/
	private int toleranceOnRemainingCharToDisplay =20;
	
	/**
	 * css untuk remaing char label. set ini untuk mengatur tipe text dari remaing char(ada di sebelah textarea)
	 **/
	private String cssForRemainingCharLabel ; 
	
	
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	private String placeholder ; 
	
	
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	public String getPlaceholder() {
		return placeholder;
	}
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	public void setPlaceholder(String placeHolder) {
		try {
			this.placeholder = placeHolder ; 
			if ( placeHolder!=null&&placeHolder.length()>0){
				getElement().setAttribute("placeHolder", placeHolder); 
			}
			else{
				getElement().removeAttribute("placeHolder");
			}
		} catch (Exception e) {
			
		}
		
	}
	
	
	/**
	 * detach remaining char visualizer
	 **/
	protected void detachRemainingCharVisualizer(){
		if ( remainingCharVisualizer!=null){
			try {
				remainingCharVisualizer.removeFromParent();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		remainingCharVisualizer=null ;
	}
	
	/**
	 * pasang remaing char visualizer
	 **/
	protected void attachRemainingCharVisualizer(){
		if ( maxLength==null||maxLength<1||remainingCharVisualizer!=null||!attached)
			return ; 
		remainingCharVisualizer = DOM.createElement("span");
		if ( cssForRemainingCharLabel!=null&&cssForRemainingCharLabel.length()>0)
			remainingCharVisualizer.setClassName(cssForRemainingCharLabel);
		getElement().getParentElement().insertAfter(remainingCharVisualizer, getElement());
	}
	
	/**
	 * marker mandatory
	 **/
	protected Element mandatoryMarker = null ; 
	
	protected void baseMandatoryMarkerRenderer() {
		if ( isMandatory()){
			if(mandatoryMarker==null)
				mandatoryMarker=AdvanceControlUtil.getInstance().renderMandatoryMarkerAfterNode(getElement());
			else
				mandatoryMarker.getStyle().setProperty("display", "");
		}
		else{
			if ( mandatoryMarker!=null)
				mandatoryMarker.getStyle().setDisplay(Display.NONE);
		}
		if (!isVisible())
			mandatoryMarker.getStyle().setDisplay(Display.NONE);
	}
	
	public void setMandatoryMarker(Element mandatoryMarker) {
		this.mandatoryMarker = mandatoryMarker;
	}
	
	/**
	 * remove semua handler dari object kontrol
	 **/
	protected void detachAllLengthRelatedHandler(){
		// remove visualizer
		
		if ( registrations==null||registrations.length==0)
			return ; 
		for ( HandlerRegistration scn : registrations){
			scn.removeHandler();
		}
	}
	
	
	
	/**
	 * ini untuk menaruh semua yang di perlukan terkait dengan pembatasan length ( {@link #setMaxLength(Integer)})
	 **/
	protected void attachLengthRelatedHandler(){
		this.registrations = new HandlerRegistration[]{
			addKeyUpHandler(new KeyUpHandler() {
				
				@Override
				public void onKeyUp(KeyUpEvent event) {
					checkForMaxLengthConstraint();
					
				}
			})	,
			/* 
			addKeyDownHandler(new KeyDownHandler() {
				
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if ( event.isAnyModifierKeyDown()){
						checkForMaxLengthConstraint();
					}
				}
			}),*/
			addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					checkForMaxLengthConstraint();
				}
			}),
			addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					visualizeRemaining();
				}
			})
		};
	}
	
	
	
	
	/**
	 * <ol>
	 * <li>memotong string kalau kepanjangan</li>
	 * </ol>
	 **/
	protected void checkForMaxLengthConstraint (){
		String val = getValue(); 
		if (val==null||val.length()==0||maxLength==null||maxLength<1){
			remainingCharVisualizer.getStyle().setDisplay(Display.NONE);
			return ;
		}
		if ( val.length()>maxLength)
			setValue(val.substring(0, maxLength));
		visualizeRemaining();
	}
	@Override
	public void setValue(String value) {
		
		super.setValue(value);
		if(readonlyMode)
			switchToReadonlyText();
	}
	
	
	/**
	 * section ini hanya visualize
	 **/
	private void visualizeRemaining(){
		String val = getValue(); 
		if (val==null||val.length()==0||maxLength==null||maxLength<1){
			remainingCharVisualizer.getStyle().setDisplay(Display.NONE);
			return ;
		}
		visualizeRemainingWorker(val);
		
	}
	
	
	/**
	 * worker , bagian yang merender visualisasi remaining char only
	 **/
	private void visualizeRemainingWorker(String textAreaValue){
		if ( textAreaValue.length()> (maxLength-toleranceOnRemainingCharToDisplay)){
			remainingCharVisualizer.getStyle().setDisplay(Display.INLINE);
			remainingCharVisualizer.setInnerHTML(textAreaValue.length() +"/" + maxLength);
		}
		else{
			remainingCharVisualizer.getStyle().setDisplay(Display.NONE);
		}
	}
	
	@Override
	public void setI18Key(String key) {
		
		this.i18Key = key;
	}

	@Override
	public String getI18Key() {
		return i18Key;
	}

	@Override
	public void setConfiguredText(String text) {
		
		
	}

	@Override
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory ; 
		if ( this.isAttached()){
			baseMandatoryMarkerRenderer();
		}
	}

	@Override
	public boolean isMandatory() {
		return mandatory;
	}

	@Override
	public String getControlBusinessName() {
		if(i18Key==null||i18Key.length()==0)
			return null ;
		return I18Utilities.getInstance().getInternalitionalizeText(i18Key);
	}

	@Override
	public boolean validateMandatory() {
		if ( !mandatory)
			return true ;
		String swap = getValue(); 
		return swap!=null&& swap.length()>0;
	}

	/**
	 * panjang max text. <1 atau null = no limit
	 **/
	public Integer getMaxLength() {
		return maxLength;
	}
	/**
	 * panjang max text. <1 atau null = no limit
	 **/
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
		if ( this.maxLength==null|| this.maxLength< 1){
			detachAllLengthRelatedHandler();
			detachRemainingCharVisualizer();
		}
		else{
			attachLengthRelatedHandler();
			attachRemainingCharVisualizer();
		}
	}
	
	
	@Override
	protected void onAttach() {
		attached = true ;
		super.onAttach();
		attachRemainingCharVisualizer();
		if ( REGISTER_ON_ATTACH)
			childSelfRegisterAggregator.registerToParent();
		showHideEditConfigButton(isEditable);
		
		baseMandatoryMarkerRenderer();
	}
	@Override
	protected void onDetach() {
		attached=false ;
		super.onDetach();
		detachRemainingCharVisualizer();
		if ( REGISTER_ON_ATTACH)
			childSelfRegisterAggregator.unregisterFromParent();
		
		try {
			OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * css untuk remaing char label. set ini untuk mengatur tipe text dari remaing char(ada di sebelah textarea)
	 **/
	public String getCssForRemainingCharLabel() {
		return cssForRemainingCharLabel;
	}

	/**
	 * css untuk remaing char label. set ini untuk mengatur tipe text dari remaing char(ada di sebelah textarea)
	 **/
	public void setCssForRemainingCharLabel(String cssForRemainingCharLabel) {
		this.cssForRemainingCharLabel = cssForRemainingCharLabel;
		if ( remainingCharVisualizer!=null&&cssForRemainingCharLabel!=null&&cssForRemainingCharLabel.length()>0)
			remainingCharVisualizer.setClassName(cssForRemainingCharLabel);
	}
	
	/**
	 * kalau karakter ke sisia beraapa visualizer akan di tampilkan
	 **/
	public void setRemainingCharVisualizer(Element remainingCharVisualizer) {
		this.remainingCharVisualizer = remainingCharVisualizer;
	}
	/**
	 * kalau karakter ke sisia beraapa visualizer akan di tampilkan
	 **/
	public Element getRemainingCharVisualizer() {
		return remainingCharVisualizer;
	}
	
	
	private String configurationId;
	private String parentId;
	private String groupFormConfiguration;
	
	/**
	 * flag show hide config button
	 **/
	private boolean showEditConfigButton = false ; 
	
	
	@Override
	public void showHideEditConfigButton(boolean show) {
		this.showEditConfigButton = show ; 
		if ( isAttached()){
			if ( show)
				OnScreenConfigurationUtils.getInstance().renderEditoControl(this, ControlCommonValueType.stringValue);
			else
				OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		}
	}
	
	
	@Override
	public void setOnScreenConfigurationId(String configurationId) {
		this.configurationId = configurationId;
	}
	@Override
	public String getOnScreenConfigurationId() {		
		return this.configurationId;
	}
	@Override
	public void setParentFormConfigurationId(String parentFormId) {
		this.parentId = parentFormId;		
	}
	@Override
	public String getParentFormConfigurationId() {		
		return this.parentId;
	}
	@Override
	public String getGroupFormConfiguration() {		
		return this.groupFormConfiguration;
	}
	@Override
	public void setOnScreenGroupFormConfiguration(String groupId) {
		this.groupFormConfiguration = groupId;		
	}
	
	private String groupId;
	private boolean isEditable = true;
	
	@Override
	public String getGroupId() {		
		return this.groupId;
	}
	@Override
	public void setGroupId(String groupId) {
		this.groupId = groupId;	
	}
	@Override
	public void setOnScreenEditable(boolean state) {
		this.isEditable = state;
	}
	@Override
	public boolean isOnScreenEditable() {		
		return isEditable;
	}
	
	
	@Override
	public void assignConfigurationData(
			IFormElementConfiguration formConfiguration) {
		OnScreenConfigurationUtils.getInstance().applyConfiguration(this, formConfiguration);
	}
	
	@Override
	public void setVisible(boolean visible) {
		
		super.setVisible(visible);
		if ( !visible){
			if ( mandatoryMarker!=null){
				mandatoryMarker.getStyle().setProperty("display", "none");
			}
		}
		else{
			baseMandatoryMarkerRenderer();
		}
	}
	@Override
	public void restoreControl() {
		readonlyMode = false ; 
		CommonClientControlUtil.getInstance().switchToNormalMode(this);
		
	}
	
	
	
	private boolean readonlyMode = false ; 

	@Override
	public void switchToReadonlyText() {
		readonlyMode  = true ; 
		CommonClientControlUtil.getInstance().switchToReadOnlyLabel( this);
		
	}
}