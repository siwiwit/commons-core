package id.co.gpsc.common.client.form;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.control.UIDataFilter;
import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.client.lov.LovPoolPanel;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.util.ClientSideLOVRelatedUtil;
import id.co.gpsc.common.data.CustomDataFormatter;
import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;

/**
 * 
 * Listbox dengan LOV support
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class LOVCapabledListBox extends ExtendedListBox  implements LOVCapabledControl{

	
	
	/**
	 * id dari parameter.cek underlying LOV
	 **/
	private String parameterId ; 
	
	/**
	 * source LOV default : directFromLookupTable
	 **/
	private LOVSource lovSource = LOVSource.directFromLookupTable ; 
	
	
	private CommonLOVHeader lovData ; 
	
	
	/**
	 * pada bagian awal apakah ada pilihan silakan pilih atau sebangsanya
	 **/
	private boolean appendNonSelectedChoice =false ; 
	
	

	/**
	 * worker 
	 **/
	private UIDataFilter<CommonLOV> dataVisualizerFilter ; 
	
	
	
	
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
	
	@Override
	public void onLOVChange(CommonLOVHeader lovData) {
		setLOVData(lovData);
	}

	@Override
	public String getParameterId() {
		return parameterId;
	}

	@Override
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId ;
		
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
	
	
	

	@Override
	public CommonLOVHeader getLOVData() {
		return this.lovData;
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
	
	
	/**
	 * worker untuk render data ke combo box
	 **/
	public void renderLovData () {
		this.clear();
		if ( lovData==null|| lovData.getDetails()==null||lovData.getDetails().isEmpty())
			return ;
		
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
	
	
	public void setCustomFormatter(
			CustomDataFormatter<CommonLOV> customFormatter) {
		this.customFormatter = customFormatter;
	}
	
	public CustomDataFormatter<CommonLOV> getCustomFormatter() {
		return customFormatter;
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
