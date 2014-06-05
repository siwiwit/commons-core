package id.co.gpsc.common.client.form;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.ControlCommonValueType;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.ITransformableToReadonlyLabel;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.form.advance.AdvanceControlUtil;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;
import id.co.gpsc.common.form.BaseFormElement;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.TextBox;




/**
 * extended Text Box. tambahan untuk keseragaman css, keseragaman validasi
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 **/
public class ExtendedTextBox extends TextBox implements BaseFormElement,
	MandatoryValidationEnabledControl,OnScreenConfigurableControl,ICommonTextboxBasedElement, IHaveMaxLengthTextEntry, ITransformableToReadonlyLabel{

	protected String i18Key;	
	
	
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	private String placeholder ; 
	/**
	 * id on screen configuration
	 **/
	private String onScreenConfigurationId  ; 
	
	
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	private String parentFormConfigurationId; 
	
	
	private boolean onAttachedCalled1 =false ; 
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	@Override
	public String getParentFormConfigurationId() {
		return parentFormConfigurationId;
	}
	
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	@Override
	public void setParentFormConfigurationId(String parentFormId) {
		this.parentFormConfigurationId = parentFormId ; 
		
	}

	/**
	 * id on screen configuration
	 **/
	@Override
	public String getOnScreenConfigurationId() {
		return onScreenConfigurationId;
	}
	
	/**
	 * id on screen configuration
	 **/
	@Override
	public void setOnScreenConfigurationId(String configurationId) {
		this.onScreenConfigurationId = configurationId ; 
		
	}
	
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	public String getPlaceHolder() {
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
	
	@Override
	public void setDomId(String elementId) {
		
		//ensureDebugId(elementId) ;
		getElement().setAttribute("id", elementId);
	}
	@Override
	public String getDomId() {
		return getElement().getId();
	}
	
	
	@Override
	public void setI18Key(String key) {
		this.i18Key=  key ; 
		getElement().setPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM, key);
	}


	@Override
	public String getI18Key() {
		//return getElement().getPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM);
		return i18Key ; 
	}


	@Override
	public void setConfiguredText(String text) {
		getElement().setPropertyString(CommonClientControlConstant.CONTROL_BUSINESS_NAME_DOM_KEY, text) ; 
	}


	/**
	 * flag show hide config button
	 **/
	private boolean showEditConfigButton = false ; 
	
	
	@Override
	public void showHideEditConfigButton(boolean show) {
		this.showEditConfigButton = show ; 
		if ( this.getElement().getParentElement()!=null){
			if ( show){
				OnScreenConfigurationUtils.getInstance().renderEditoControl(this, ControlCommonValueType.stringValue);
			}
			else
				OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		}
	}
	
	@Override
	public void setMandatory(boolean mandatory) {
		CommonClientControlUtil.getInstance().setMandatory(this, mandatory);
		if ( this.isAttached()){
			baseMandatoryMarkerRenderer();
		}
	}

	@Override
	public boolean isMandatory() {
		return getElement().getPropertyBoolean(CommonClientControlConstant.CONTROL_MANDATORY_DOM_KEY);
	}

	@Override
	public String getControlBusinessName() {
		return getElement().getPropertyString(CommonClientControlConstant.CONTROL_BUSINESS_NAME_DOM_KEY) ;
	}
	@Override
	public boolean validateMandatory() {
		if ( !isMandatory())
			return true ; 
		String val =  getText();
		return val!=null && val.length()>0;
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		if ( getElement().getId()==null||getElement().getId().length()==0)
			getElement().setId(DOM.createUniqueId());
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);
		showHideEditConfigButton(screenEditable);
		
		baseMandatoryMarkerRenderer();		
	}
	
	@Override
	protected void onDetach() {	
		super.onDetach();
		try {
			OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	
	
private String groupId;
	
	@Override
	public String getGroupFormConfiguration() {		
		return this.groupId;
	}

	@Override
	public void setOnScreenGroupFormConfiguration(String groupId) {
		this.groupId = groupId;		
	}
	
	/**
	 * id group, di inject oleh parent container/form
	 **/
	@Override
	public String getGroupId() {
		return groupId;
	}
	/**
	 * id group, di inject oleh parent container/form
	 **/
	@Override
	public void setGroupId(String groupId) {
		this.groupId = groupId;
		
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
	
	/**
	 * penanada apakah kontrol boleh di konfigurasi atau tidak
	 */
	private boolean screenEditable = true;

	@Override
	public void setOnScreenEditable(boolean state) {
		this.screenEditable = state;
	}

	@Override
	public boolean isOnScreenEditable() {
		return screenEditable;
	}
	@Override
	public void assignConfigurationData(
			IFormElementConfiguration formConfiguration) {
		OnScreenConfigurationUtils.getInstance().applyConfiguration(this, formConfiguration);
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
	@Override
	public void setValue(String value) {
		
		super.setValue(value);
		if ( readonlyMode)
			switchToReadonlyText();
	}
}