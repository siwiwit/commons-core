package id.co.gpsc.common.client.form;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;
import id.co.gpsc.common.form.BaseFormElement;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RadioButton;

/**
 * komponen radio button for cams
 * @author ashadi.pratama
 * @version $id
 * @since 2012-12-04
 */

public class ExtentedRadioButton extends RadioButton 
		implements BaseFormElement, MandatoryValidationEnabledControl,OnScreenConfigurableControl {
	/**
	 * id on screen configuration
	 **/
	private String onScreenConfigurationId  ; 
	
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	private String parentFormConfigurationId; 
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
	@UiConstructor
	public ExtentedRadioButton(String name) {
		super(name);
	}
	
	
	protected String i18Key;	
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


	@Override
	public void showHideEditConfigButton(boolean show) {
		// FIXME: masukan code untuk editor 
	}
	@Override
	public void setMandatory(boolean mandatory) {
		CommonClientControlUtil.getInstance().setMandatory(this, mandatory);
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
		//FIXME: control ini blm configurable
		OnScreenConfigurationUtils.getInstance().applyConfiguration(this, formConfiguration);
	}
	
	
	
	
	
	/**
	 * radio button ada item dengan tag :<i>label</i> di sebelah nya. method ini akan meremove tag label ini dan menambahkan span biasa. dengan cara ini, control di sebelah control tidak akan turun ke bawah
	 **/
	public void removeLabelAndReplaceWithSimpleText () {
		Element meElement = this.getElement() ; 
		  int itung = meElement.getChildCount() ;
		  for ( int i = 0 ; i< itung ; i++) {
		   
		   try {
		    Node n =  meElement.getChild(i);
		    Element chld = (Element)n ; 
		    if ( "label".equalsIgnoreCase(  chld.getTagName())){
		     
				 Element spn = DOM.createSpan(); 
				 Element spnPasi = DOM.createSpan(); 
				 spnPasi.setInnerHTML("&nbsp;");
				 spn.setInnerHTML(chld.getInnerHTML());
				 
				 meElement.insertAfter(spn, chld);
				 meElement.insertAfter(spnPasi, chld);
				 
				 chld.removeFromParent(); 
				 
				 return ; 
		    }
		   } catch (Exception e) {
		    
		   }
		  }
		
		
		
	}
}
