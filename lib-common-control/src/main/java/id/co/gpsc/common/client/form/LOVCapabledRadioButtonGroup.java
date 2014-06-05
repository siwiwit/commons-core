package id.co.gpsc.common.client.form;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.client.lov.LovPoolPanel;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.util.ClientSideLOVRelatedUtil;
import id.co.gpsc.common.data.CustomDataFormatter;
import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;




/**
 * radio button group yang bisa support LOV
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 5 aug 2012
 **/
public class LOVCapabledRadioButtonGroup extends FlowDrivenRadioButtonGroup implements LOVCapabledControl{

	
	

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
	 * register LOV pada saat on load
	 **/
	private boolean lOVRegisteredOnAttach =false; 
	
	
	
	
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
		this.parameterId=parameterId ;
		
	}

	@Override
	public LOVSource getLOVSource() {
		return lovSource;
	}

	@Override
	public void setLOVData(CommonLOVHeader lovData) {
		this.lovData = lovData ; 
		this.clear(); 
		if ( lovData==null||lovData.getDetails()==null||lovData.getDetails().isEmpty())
			return ;
		for (CommonLOV dt : lovData.getDetails()){
			appendItem(dt.getDataValue(), customFormatter.getFormattedData(dt));
		}
		
	}
	
	
	@Override
	public CommonLOVHeader getLOVData() {
		return lovData;
	}

	@Override
	public void setLovSource(LOVSource lovSource) {
		this.lovSource=lovSource;
	}

	
	public void setCustomFormatter(
			CustomDataFormatter<CommonLOV> customFormatter) {
		this.customFormatter = customFormatter;
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
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		if ( this.lovPoolPanel!=null)
			this.lovPoolPanel.unregister(this);
	}
	
	
	public CommonLOVHeader getLovData() {
		return lovData;
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
