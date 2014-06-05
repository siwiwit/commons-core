package id.co.gpsc.common.client.form;


import java.text.ParseException;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.text.shared.testing.PassthroughParser;
import com.google.gwt.text.shared.testing.PassthroughRenderer;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.ControlCommonValueType;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.form.advance.AdvanceControlUtil;
import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.client.lov.LovPoolPanel;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.util.ClientSideLOVRelatedUtil;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;
import id.co.gpsc.common.data.CustomDataFormatter;
import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;
import id.co.gpsc.common.data.lov.StrongTypedCustomLOVID;
import id.co.gpsc.jquery.client.form.JQBaseAutoComplete;


/**
 * auto complete dengan 
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 5 Aug 2012
 **/
public class LOVCapabledAutoComplete extends JQBaseAutoComplete<String> implements LOVCapabledControl, MandatoryValidationEnabledControl , OnScreenConfigurableControl{


	private CommonLOVHeader lovData ; 
	
	/**
	 * id dari parameter.cek underlying LOV
	 **/
	private String parameterId ; 
	
	/**
	 * source LOV default : directFromLookupTable
	 **/
	private LOVSource lovSource = LOVSource.directFromLookupTable ; 
	
	
	/**
	 * otomatis di scan pada saat item di attach ke dalam kontrol. mencari di lakukan pada saat control di attach ke dalam parent node.
	 **/
	private LovPoolPanel lovPoolPanel ; 
	
	
	
	/**
	 * data LOV current control
	 **/
	private CommonLOVHeader currentLOVHeaderData ;
	/**
	 * register LOV pada saat on load
	 **/
	private boolean lOVRegisteredOnAttach =false;
	
	
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
	
	
	/**
	 * kalau label dari lookup tidakcuma label, timpa ini
	 **/
	private CustomDataFormatter<CommonLOV> customFormatter = new CustomDataFormatter<CommonLOV>() {
		
		@Override
		public String getFormattedData(CommonLOV data) {
			return data.getLabel();
		}
		@Override
		public String getStringForValue(CommonLOV data) {
			return data.getDataValue();
		}
	}; 
	
	public LOVCapabledAutoComplete() {
		super(PassthroughRenderer.instance(), PassthroughParser.instance());
		deployAutoCompleteHandler();
	}

	private CustomDataFormatter<CommonLOV> dataFormatter = new CustomDataFormatter<CommonLOV>() {
		@Override
		public String getFormattedData(CommonLOV data) {
			return data.getLabel();
		}
		public String getStringForValue(CommonLOV data) {
			return data.getDataValue();
		};
	};
	
	
	@Override
	public void onLOVChange(CommonLOVHeader lovData) {
		setLOVData(lovData);
	}
	
	/**
	 * pasti pakai custom kalau dengan ini
	 **/
	public void setCustomLookupParameter(StrongTypedCustomLOVID lookupParam ){
		setParameterId(lookupParam.getModulGroupId()+"." + lookupParam.getId()); 
		setLovSource(LOVSource.useCustomProvider); 
	}

	@Override
	public String getParameterId() {
		return this.parameterId;
	}


	/**
	 * id parameter 
	 **/
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}
	
	
	
	 
	@Override
	public LOVSource getLOVSource() {
		return lovSource;
	}

	@Override
	public void setLOVData(CommonLOVHeader lovData) {
		this.currentLOVHeaderData = lovData ; 
		resetArray();
		if ( lovData==null||lovData.getDetails()==null||lovData.getDetails().isEmpty())
			return ; 
		for (CommonLOV scn : lovData.getDetails()){
			appendLovDataWorker(scn.getDataValue(), this.customFormatter.getFormattedData(  scn ));
		}
		if ( attached){
			destroy(getElement().getId());
			renderAutoComplete();
		}
			
		
	}
	
	
	/**
	 * data LOV current control
	 **/
	@Override
	public CommonLOVHeader getLOVData() {
		return currentLOVHeaderData;
	}
	
	@Override
	public void setLovSource(LOVSource lovSource) {
		this.lovSource= lovSource;
	}
	
	/**
	 * worker untuk memasukan data native JQUERY auto complete data
	 **/
	private native void appendLovDataWorker (String dataValue , String dataLabel ) /*-{
		 
		
		
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg.source.push ({
			value: dataValue,
			label: dataLabel
		});
	}-*/;
	
	
	//FIXME : cek efek pada event normal
	/**
	 * plug handler focus + select 
	 **/
	private native void deployAutoCompleteHandler () /*-{
		
		var elementId =  this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::getElement()().id ;
		var keyData = @id.co.gpsc.jquery.client.form.JQBaseAutoComplete::ACTUAL_VALUE_KEY;
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg["select"]=function (event, ui) {
			$wnd.$("#" + elementId ).val( ui.item.label );
			$wnd.$("#" + elementId )[0][keyData]=ui.item.value; 
			return false ; 
		};
		
		this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::autocompleteArg["focus"]=function (event, ui) {
			$wnd.$("#" + elementId ).val( ui.item.label );
			return false ; 
		};
			
	
	}-*/;

	
	/**
	 * kalau label dari lookup tidakcuma label, timpa ini
	 **/
	public void setCustomFormatter(
			CustomDataFormatter<CommonLOV> customFormatter) {
		this.customFormatter = customFormatter;
	}
	

	/**
	 * membaca underlying value(string)
	 **/
	protected native String getStringValue()/*-{
		return $wnd.$( "#" +  this.@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::elementId )[0][@id.co.gpsc.jquery.client.form.JQBaseAutoComplete::ACTUAL_VALUE_KEY] ;
	}-*/;
	
	@Override
	public String getValueOrThrow() throws ParseException {
		return getStringValue();
	}
	
	
	@Override
	protected void onAttach() {
		super.onAttach();
		if ( this.lOVRegisteredOnAttach){
			registerLOVToContainer();
		}
		if ( getElement().getId()==null||getElement().getId().length()==0)
			getElement().setId(DOM.createUniqueId());
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);
		showHideEditConfigButton(showEditConfigButton);
		baseMandatoryMarkerRenderer();
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		if ( this.lovPoolPanel!=null){
			this.lovPoolPanel.unregister(this);
			this.lovPoolPanel = null ;
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
	public boolean isLOVRegisteredOnAttach() {
		return lOVRegisteredOnAttach;
	}
	
	@Override
	public void setLOVRegisteredOnAttach(boolean registerOnAttach) {
		this.lOVRegisteredOnAttach = registerOnAttach ; 
	}

	@Override
	public void registerLOVToContainer() {
		this.lovPoolPanel =  ClientSideLOVRelatedUtil.getInstance().getLOVPoolPanel(this);
		if ( this.lovPoolPanel!=null)
			this.lovPoolPanel.registerLOVCapableControl(this);
		
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
		if(!isMandatory())
			return true ;
		String val =  getValue();
		return val!=null&& val.length()>0;
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

	
	
	
	private Element loadingIconImage ; 
	
	
	@Override
	public void startLoadingAnimation() {
		setEnabled(false);
		if ( !isAttached())
			return ; 
		if ( loadingIconImage== null){
			String src= CommonResourceBundle.getResources().iconLoadingWheel().getSafeUri().asString();
			loadingIconImage =  DOM.createImg();
			loadingIconImage.setPropertyString("src", src);
			getElement().getParentElement().insertAfter(loadingIconImage, getElement());
		}
		
		
		loadingIconImage.getStyle().setProperty("display", "");
		
		
		
		
	}

	@Override
	public void stopLoadingAnimation() {
		if ( loadingIconImage!= null){
			loadingIconImage.getStyle().setDisplay(Display.NONE);
		}
		setEnabled(true);
	}
	
	
}



