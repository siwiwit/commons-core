package id.co.gpsc.common.client.form;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.ControlCommonValueType;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.IParentLOVEnableControl;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.form.advance.AdvanceControlUtil;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RadioButton;




/**
 * simple. Flow panel + radio button. jadinya ini akan otomatis menambah radio button kalau misalnya di tambahkan string value + label<br/>
 <span>
	<input type="radio" name="alpha1">lbl1
	<input type="radio" name="alpha1">lbl2
	<input type="radio" name="alpha1">lbl2
	
	</span> 
	
 * 
 * 
 * <br/>
 * <strong>Jangan lakukan : </strong><span style="color:#ff0000">memasukan radio button manual</span> 
 **/
public class FlowDrivenRadioButtonGroup extends FlowPanel implements
		HasValue<String> , MandatoryValidationEnabledControl,OnScreenConfigurableControl, IParentLOVEnableControl{

	private static int LATES_AUTO_NAME_COUNTER = 0;
	// private ArrayList<E>

	private String nameForRadio;

	protected HashMap<String, RadioButton> indexedButtonByValue = new HashMap<String, RadioButton>();

	private String currentValue;

	private boolean resyncCheckOnSetValue = true;
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
	public FlowDrivenRadioButtonGroup() {
		nameForRadio = "BC_CHAMP_AUTOMATED_NAME" + (LATES_AUTO_NAME_COUNTER++);
	}

	private String defaultSelectionValue;

	/**
	 * register label. flag default = false
	 **/
	public void appendItem(final String value, String label) {
		appendItem(value, label, false);
	}

	/**
	 * register label. tidak di ijinkan memasukan value duplikasi ke dalam
	 * kontrol
	 **/
	public void appendItem(final String value, String label,
			boolean defaultValue) {

		if (indexedButtonByValue.containsKey(value)) {
			indexedButtonByValue.get(value).setHTML(label);
			return;
		}
		RadioButton btn = new RadioButton(this.nameForRadio, label);
		btn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (value.equals(currentValue)) {
					// do nothing, ndak ada perubahan
				} else {
					resyncCheckOnSetValue = false;
					setValue(value, true);
					resyncCheckOnSetValue = true;
				}
			}
		});
		add(btn);
		defaultSelectionValue = defaultValue ? value : defaultSelectionValue;
		indexedButtonByValue.put(value, btn);
		if (defaultSelectionValue != null) {
			for (String key : indexedButtonByValue.keySet()) {
				indexedButtonByValue.get(key).setValue(
						defaultSelectionValue.equals(key));

			}

		}

	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	private ArrayList<ValueChangeHandler<String>> listeners = new ArrayList<ValueChangeHandler<String>>();

	@Override
	public HandlerRegistration addValueChangeHandler(
			final ValueChangeHandler<String> handler) {
		if (handler == null)
			return null;
		if (listeners.contains(handler))
			return null;
		addChangeHandler((ChangeHandler) handler);
		listeners.add(handler);
		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				listeners.remove(handler);

			}
		};

	}

	@Override
	public String getValue() {
		return currentValue;
	}

	@Override
	public void setValue(String value) {
		setValue(value, false);

	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		boolean changed = false;
		if (value != null) {
			changed = value.equals(currentValue);
		} else if (currentValue != null)
			changed = true;
		if (fireEvents && changed) {
			// ValueChangeEvent<String> a= new ValueChangeEvent<String>(value);
			ValueChangeEvent.fire(this, value);

		}
		this.currentValue = value;

		if (resyncCheckOnSetValue) {
			for (String key : indexedButtonByValue.keySet()) {
				indexedButtonByValue.get(key).setValue(key.equals(value));
			}
		}

	}

	public void setEnabled(boolean enabled) {
		for (RadioButton scn : indexedButtonByValue.values()) {
			scn.setEnabled(enabled);
		}
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
		if ( isAttached()){
			if ( show)
				OnScreenConfigurationUtils.getInstance().renderEditoControl(this, ControlCommonValueType.comboBoxValue);
			else
				OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		}
	}


	@Override
	public void setMandatory(boolean mandatory) {
		getElement().setPropertyBoolean(CommonClientControlConstant.CONTROL_MANDATORY_DOM_KEY, mandatory);
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
		return getValue()!=null;
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
	protected void onAttach() {
		
		super.onAttach();
		if ( getElement().getId()==null||getElement().getId().length()==0)
			getElement().setId(DOM.createUniqueId());
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);
		
		showHideEditConfigButton(showEditConfigButton);
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
	
	
}
