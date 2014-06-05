package id.co.gpsc.common.client.form;


import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.UIDataFilter;
import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.client.lov.LovPoolPanel;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.util.ClientSideLOVRelatedUtil;
import id.co.gpsc.common.data.CustomDataFormatter;
import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;
import id.co.gpsc.common.data.lov.StrongTypedCustomLOVID;
import id.co.gpsc.common.util.I18Utilities;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;


/**
 * combo box yan gbisa menerima LOV
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version 1.0
 * @since 5 Aug 2012
 **/
public class LOVCapabledComboBox extends ExtendedComboBox implements LOVCapabledControl{

	
	
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
	 * pada bagian awal apakah ada pilihan silakan pilih atau sebangsanya
	 **/
	private boolean appendNonSelectedChoice =false ; 
	
	/**
	 * label untuk non selected.default <i>-silakan pilih-</i>
	 **/
	private String noneSelectedLabel ="-silakan pilih-";
	
	
	
	
	
	/**
	 * key i18 untuk none selected label. ini kalau none selected mempergunakan internalization
	 **/
	protected String noneSelectedLabelI18NKey ; 
	
	/**
	 * register LOV pada saat on load
	 **/
	private boolean lOVRegisteredOnAttach =false; 
	
	
	private CustomDataFormatter<CommonLOV> customFormatter = new CustomDataFormatter<CommonLOV>() {
		
		@Override
		public String getFormattedData(CommonLOV data) {
			return data.getLabel();
		}
		public String getStringForValue(CommonLOV data) {
			return data.getDataValue();
		};
	}; 
	
	/**
	 * otomatis di scan pada saat item di attach ke dalam kontrol. mencari di lakukan pada saat control di attach ke dalam parent node.
	 **/
	private LovPoolPanel lovPoolPanel ; 
	
	
	
	
	/**
	 * worker 
	 **/
	private UIDataFilter<CommonLOV> dataVisualizerFilter ; 
	
	
	public LOVCapabledComboBox(){
		super(); 
		
	}
	
	
	
	/**
	 * apply data filter. ini in case 
	 **/
	public void applyDataFilter() {
		if(dataVisualizerFilter==null)
			return ; 
		renderLovData();
	}
	
	
	@Override
	public void onLOVChange(CommonLOVHeader lovData) {
		setLOVData(lovData);
	}

	@Override
	public String getParameterId() {
		return parameterId;
	}

	
	/**
	 * id parameter 
	 **/
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}
	
	
	/**
	 * pasti pakai custom kalau dengan ini
	 **/
	public void setCustomLookupParameter(StrongTypedCustomLOVID lookupParam ){
		setParameterId(lookupParam.getModulGroupId()+"." + lookupParam.getId()); 
		setLovSource(LOVSource.useCustomProvider); 
	}
	
	
	@Override
	public LOVSource getLOVSource() {
		return lovSource;
	}
	
	/**
	 * assign LOV type source untuk kontrol ini
	 **/
	public void setLovSource(LOVSource lovSource) {
		this.lovSource = lovSource;
	}

	@Override
	public void setLOVData(CommonLOVHeader lovData) {
		
		
		this.lovData= lovData;
		renderLovData();
	}
	
	
	
	
	
	/**
	 * worker untuk render data ke combo box
	 **/
	public void renderLovData () {
		this.clear();
		if ( lovData==null|| lovData.getDetails()==null||lovData.getDetails().isEmpty())
			return ;
		if ( appendNonSelectedChoice){
			if ( noneSelectedLabelI18NKey!=null&&noneSelectedLabelI18NKey.length()>0){
				this.addItem(I18Utilities.getInstance().getInternalitionalizeText(noneSelectedLabelI18NKey, noneSelectedLabel) , "");
			}else{
				addItem( this.noneSelectedLabel , "" );
			}
			
		}
		if( dataVisualizerFilter==null){
			for ( CommonLOV scn : lovData.getDetails()){
				addItem( customFormatter.getFormattedData(scn) ,customFormatter.getStringForValue( scn)  );
			}
		}
		else{
			for ( CommonLOV scn : lovData.getDetails()){
				if ( !dataVisualizerFilter.isDataAllowedToVisible(scn))// skip kalau ndak boleh tampil
					continue ; 
				addItem( customFormatter.getFormattedData(scn) ,customFormatter.getStringForValue(scn)  );
			}
		}
		if ( currentSelectedValue!= null && currentSelectedValue.length()>0 ){
			setValue(currentSelectedValue);
		}
		
	}
	@Override
	public CommonLOVHeader getLOVData() {
		return lovData;
	}
	

	/**
	 * pada bagian awal apakah ada pilihan silakan pilih atau sebangsanya
	 **/
	public boolean isAppendNonSelectedChoice() {
		return appendNonSelectedChoice;
	}

	/**
	 * pada bagian awal apakah ada pilihan silakan pilih atau sebangsanya
	 **/
	public void setAppendNonSelectedChoice(boolean appendNonSelectedChoice) {
		this.appendNonSelectedChoice = appendNonSelectedChoice;
	}
	/**
	 * label untuk non selected.default <i>-silakan pilih-</i>
	 **/
	public String getNoneSelectedLabel() {
		return noneSelectedLabel;
	}
	/**
	 * label untuk non selected.default <i>-silakan pilih-</i>
	 **/
	public void setNoneSelectedLabel(String noneSelectedLabel) {
		this.noneSelectedLabel = noneSelectedLabel;
	}

	@Override
	public String getValue() {
		if(lovData==null||lovData.getDetails()==null||lovData.getDetails().isEmpty())
			return null ;
		return super.getValue();
	}
	
	
	
	/**
	 * underlying LOV Data
	 **/
	public CommonLOVHeader getLovData() {
		return lovData;
	}
	
	
	/**
	 * LOV data dari current selection. Kalau misal nya ada extended value dari LOV yang perlu di akses
	 **/
	public CommonLOV getCurrentSelectionLOVData () {
		if(lovData==null||lovData.getDetails()==null||lovData.getDetails().isEmpty())
			return null ;
		int idx = getSelectedIndex() ; 
		if ( idx<0)
			return null ;
		String val = getValue(idx);
		if (val==null||val.length()==0)
			return null ; 
		for( CommonLOV scn : lovData.getDetails()){
			if ( val.equals(  scn.getDataValue()))
				return scn ; 
		}
		return null ;
	}
	@Override
	protected void onAttach() {
		super.onAttach();
		if(lOVRegisteredOnAttach){
			registerLOVToContainer();
		}
		if ( getElement().getId()==null||getElement().getId().length()==0)
			getElement().setId(DOM.createUniqueId());
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);		
		baseMandatoryMarkerRenderer();
		
		String debugerFlag = Window.Location.getParameter(CommonClientControlConstant.SHOW_DEBUGER_THING_KEY) ;
		if ( "true".equalsIgnoreCase(debugerFlag)){
			setTitle("parameter : " + getParameterId() + ", lov source : " + getLOVSource().toString()); 
		}
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		if ( this.lovPoolPanel!=null){
			this.lovPoolPanel.unregister(this);
			this.lovPoolPanel = null ; 
		}
	}

	@Override
	public boolean isLOVRegisteredOnAttach() {
		return this.lOVRegisteredOnAttach;
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
	
	
	public void setCustomFormatter(
			CustomDataFormatter<CommonLOV> customFormatter) {
		this.customFormatter = customFormatter;
	}
	
	public CustomDataFormatter<CommonLOV> getCustomFormatter() {
		return customFormatter;
	}

	/**
	 * key i18 untuk none selected label. ini kalau none selected mempergunakan internalization
	 **/
	public String getNoneSelectedLabelI18NKey() {
		return noneSelectedLabelI18NKey;
	}

	/**
	 * key i18 untuk none selected label. ini kalau none selected mempergunakan internalization
	 **/
	public void setNoneSelectedLabelI18NKey(String noneSelectedLabelI18NKey) {
		this.noneSelectedLabelI18NKey = noneSelectedLabelI18NKey;
	}
	
	/**
	 * worker 
	 **/
	public UIDataFilter<CommonLOV> getDataVisualizerFilter() {
		return dataVisualizerFilter;
	}
	
	/**
	 * ini untuk menaruh class yang akan memfilter isi combo box, mana yang boleh tampil mana yang tidak. ini berguna untuk combo box bertingkat. contohnya bank account vs bank, provinsi vs kabupaten 
	 * cek juga : {@link UIDataFilter}
	 **/
	public void setDataVisualizerFilter(
			UIDataFilter<CommonLOV> dataVisualizerFilter) {
		this.dataVisualizerFilter = dataVisualizerFilter;
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
