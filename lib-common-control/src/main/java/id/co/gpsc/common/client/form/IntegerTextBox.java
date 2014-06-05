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

import java.io.IOException;
import java.text.ParseException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ValueBoxBase;

public class IntegerTextBox  extends ValueBoxBase<Integer> implements BaseFormElement,
	MandatoryValidationEnabledControl,OnScreenConfigurableControl , ICommonTextboxBasedElement, ITransformableToReadonlyLabel{

	
	
	
	
	
	
	/**
	 * angka integer yang di pegang oleh kontrol ini
	 **/
	private Integer holdedIntegerValue; 
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	private String placeHolder ; 
	
	
	
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
	
	public IntegerTextBox() {
		super(DOM.createInputText(), new Renderer<Integer>() {
			/**
			 * formatter
			 **/
			final NumberFormat formatter=NumberFormat.getFormat("#,###");
			@Override
			public String render(Integer object) {
				if ( object==null)
					return "";
				return formatter.format(object) ; 
				
			}
			@Override
			public void render(Integer object, Appendable appendable)
					throws IOException {
				if ( object==null)
					return ;
				appendable.append(formatter.format(object)); 
			}
			
		},  
		new Parser<Integer>() {
			@Override
			public Integer parse(CharSequence text) throws ParseException {
				try {
					if ( text==null|| text.length()==0)
						return null; 
					return Integer.parseInt(text.toString()); 
				} catch (Exception e) {
					GWT.log("gagal merubah text entry ke dalam integer. error di laporkan : " + e.getMessage() , e) ;
					return null;
				}
			}
		});
		
		addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				try{
					String txtEntry=getElement().getPropertyString("value");
					if ( txtEntry==null||txtEntry.length()==0)
						holdedIntegerValue=null ;
					else
						holdedIntegerValue=Integer.parseInt(txtEntry); 
				}
				catch(Exception exc){
					//TODO: masukan kontrol di sini untuk menandai ada salah entry
					holdedIntegerValue=null ; 
				}
			}
		}); 
		addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if ( holdedIntegerValue==null)
					getElement().setPropertyString("value", "") ;
				else
					getElement().setPropertyInt("value", holdedIntegerValue); 
			}
		}); 
		addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				readIntegerChar();
			}
		});
		setAlignment(TextAlignment.RIGHT); 
	
	}

	
	
	
	
	@Override
	public void setValue(Integer value, boolean fireEvents) {
		this.holdedIntegerValue=value; 
		
		super.setValue(value, fireEvents);
		if ( readonlyMode)
			switchToReadonlyText(); 
	}
	
	
	@Override
	public Integer getValueOrThrow() throws ParseException {
		return holdedIntegerValue; 
		
	}





	@Override
	public void setDomId(String id) {
		ensureDebugId(id);
		
	}


	@Override
	public String getDomId() {
		return getElement().getId();
	}
	
	@Override
	public void setI18Key(String key) {
		getElement().setPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM, key);
		getElement().setAttribute(CommonClientControlConstant.I18_KEY_ON_DOM, key);
		
	}


	@Override
	public String getI18Key() {
		return getElement().getAttribute(CommonClientControlConstant.I18_KEY_ON_DOM);
	}


	@Override
	public void setConfiguredText(String text) {
		getElement().setPropertyString(CommonClientControlConstant.CONTROL_BUSINESS_NAME_DOM_KEY, text) ;
		getElement().setAttribute(CommonClientControlConstant.CONTROL_BUSINESS_NAME_DOM_KEY, text);
	}


	/**
	 * flag show hide config button
	 **/
	private boolean showEditConfigButton = false ; 
	
	
	@Override
	public void showHideEditConfigButton(boolean show) {
		this.showEditConfigButton = show ; 
		if ( this.getElement().getParentElement()!=null){
			if ( show)
				OnScreenConfigurationUtils.getInstance().renderEditoControl(this, ControlCommonValueType.numberValue);
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
		if (!isMandatory())
			return true ; 
		Integer val = getValue() ; 
		boolean retval =val!=null;
		if ( retval){
			GWT.log("int value :" + val);
		}
		return retval;
	}
	
	
	
	/**
	 * marker mandatory
	 **/
	protected Element mandatoryMarker = null ;
	
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
	
	public void setMandatoryMarker(Element mandatoryMarker) {
		this.mandatoryMarker = mandatoryMarker;
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
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	public String getPlaceHolder() {
		return placeHolder;
	}
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	public void setPlaceholder(String placeHolder) {
		try {
			this.placeHolder = placeHolder ; 
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
	 * membatasi karakter yang masuk saat di ketik dengan asumsi karakter yang boleh dimasukkan
	 * adalah 0123456789 dan thousand separator berdasarkan internazionalitation
	 * @author ashadi.pratama
	 */
	protected void readIntegerChar(){
		String currentEntry = getElement().getPropertyString("value");
		if ( currentEntry==null||currentEntry.length()==0){
			return ;
		}	
		
		String validString="0123456789"+CommonClientControlUtil.getInstance().getThousandSeparator();
		String validChar="";
		for(int i=0;i<currentEntry.length();i++){
			if(validString.indexOf(currentEntry.charAt(i))>=0)
				validChar+=currentEntry.charAt(i);
		}
		
		getElement().setPropertyString("value",validChar);
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
	@Override
	public void setValue(Integer value) {
		if (value==null){
			setText("");
			return ; 
		}
		
		super.setValue(value);
	}
	@Override
	public Integer getValue() {
		
		return super.getValue();
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
