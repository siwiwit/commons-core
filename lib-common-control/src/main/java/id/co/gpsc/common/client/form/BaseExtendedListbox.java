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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ListBox;

/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class BaseExtendedListbox extends ListBox implements BaseFormElement , 
MandatoryValidationEnabledControl, OnScreenConfigurableControl,  ITransformableToReadonlyLabel{
	

	/**
	 * value yangsaat ini di pilih oleh user
	 **/
	protected String currentSelectedValue ; 
	
	/**
	 * id on screen configuration
	 **/
	protected String onScreenConfigurationId  ; 
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	protected String parentFormConfigurationId; 
	
	/**
	 * marker mandatory
	 **/
	protected Element mandatoryMarker = null ;
	
	/**
	 * penanada apakah kontrol boleh di konfigurasi atau tidak
	 */
	protected boolean screenEditable = true;
	/**
	 * flag show hide config button
	 **/
	protected boolean showEditConfigButton = false ;
	
	public BaseExtendedListbox(){
		super(false);
	}
	
	public BaseExtendedListbox( boolean listType){
		super( listType); 
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		if ( getElement().getId()==null||getElement().getId().length()==0)
			getElement().setId(DOM.createUniqueId());
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);
		showHideEditConfigButton(screenEditable);
		baseMandatoryMarkerRenderer();		
		if ( readonlyMode ){
			renderReadonlyData();
		}
	}
	
	private Element spanReadonlyLabel  ; 
	
	
	
	/**
	 * worker untuk render readonly label. label ini visible kalau combo box di switch ke readonly
	 */
	protected void renderReadonlyData () {
		String label = null ; 
		try {
			label =  getReadonlyLabel();
		} catch (Exception e) {
			GWT.log("gagal membaca readonly label. error : "  + e.getMessage() , e);
		}  
		if ( label== null)
			label ="-" ; 
		if ( spanReadonlyLabel== null){
			spanReadonlyLabel = DOM.createSpan(); 
			
			getElement().getParentElement().appendChild(spanReadonlyLabel); 
		}
		spanReadonlyLabel.setInnerHTML(label);
		new Timer() {
			
			@Override
			public void run() {
				spanReadonlyLabel.getStyle().setProperty("display", "");
				getElement().getStyle().setDisplay(Display.NONE);
			}
		}.schedule(100);
		
		
	}
	
	
	
	/**
	 * label data kalau dalam posisi readonly. override ini kalau mau membuat dedicated 
	 */
	protected   String getReadonlyLabel () {
		if ( getSelectedIndex()<0 || getSelectedIndex()>= getItemCount())
			return null ;
		int selIdx = getSelectedIndex() ; 
		GWT.log("###selected index = " + selIdx);
		return getItemText(selIdx);
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
	 * render mandatory marker. asterik merah penanda item mandatory
	 **/
	protected void baseMandatoryMarkerRenderer() {
		if ( isMandatory()){
			if(mandatoryMarker==null)
				mandatoryMarker=(Element) AdvanceControlUtil.getInstance().renderMandatoryMarkerAfterNode(getElement());
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
	
	


	@Override
	public void setDomId(String elementId) {
		ensureDebugId(elementId) ;
	}
	@Override
	public String getDomId() {
		return getElement().getId();
	}


	@Override
	public void setI18Key(String key) {
		getElement().setPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM, key);
	}


	@Override
	public String getI18Key() {
		
		return getElement().getPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM);
	}


	@Override
	public void setConfiguredText(String text) {
		getElement().setPropertyString(CommonClientControlConstant.CONTROL_BUSINESS_NAME_DOM_KEY, text) ; 
	}


	 
	
	
	@Override
	public void showHideEditConfigButton(boolean show) {
		this.showEditConfigButton = show ; 
		if ( isAttached()){
			if ( show)
				OnScreenConfigurationUtils.getInstance().renderEditoControl(this, ControlCommonValueType.comboBoxValue);
			else
				OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		}
	}
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
	protected boolean readonlyMode = false ; 
	

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if ( spanReadonlyLabel!= null)
			spanReadonlyLabel.getStyle().setProperty("display", visible?"":"none");
	}
	
	@Override
	public void switchToReadonlyText() {
		readonlyMode = true ;
		super.setVisible(false);
		if ( !isAttached()){
			return ; 
		}
		GWT.log("## switchToReadonlyText di panggil");
		renderReadonlyData();
	}
	@Override
	public void restoreControl() {
		if ( spanReadonlyLabel!= null)
			spanReadonlyLabel.getStyle().setDisplay(Display.NONE);
		super.setVisible(true);
		
		getElement().getStyle().setProperty("display", "");
		readonlyMode = false ; 
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
	
	@Override
	public String getControlBusinessName() {
		return getElement().getPropertyString(CommonClientControlConstant.CONTROL_BUSINESS_NAME_DOM_KEY) ;
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
protected String groupId;
	
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
	
}
