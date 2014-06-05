package id.co.gpsc.common.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;

import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.client.lov.LovPoolPanel;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.util.ClientSideLOVRelatedUtil;
import id.co.gpsc.common.data.CustomDataFormatter;
import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;
import id.co.gpsc.common.data.lov.StrongTypedCustomLOVID;

/**
 * span yang bisa menampilkan deskripsi dari LOV item. 
 * ini di jadikan deskripsi dari Kode tertentu
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class SpanLabelLOVEnableControl extends SpanLabel implements LOVCapabledControl , HasValue<String>{
	
	
	
	
	private String parameterId ; 

	
	
	private LOVSource lovSource ; 
	
	
	/**
	 * current LOV data
	 */
	private CommonLOVHeader currentLovData; 
	 
	
	
	/**
	 * otomatis di scan pada saat item di attach ke dalam kontrol. mencari di lakukan pada saat control di attach ke dalam parent node.
	 **/
	private LovPoolPanel lovPoolPanel ; 
	
	private List<ValueChangeHandler<String> > handlers = new ArrayList<ValueChangeHandler<String>>(); 
	
	
	
	private boolean registerOnAttach = false ; 
	
	
	
	private Element loadingAnimation ; 
	
	
	/**
	 * formatter. kalau di perlukan dedicated thing. bisa di atur di sini
	 */
	private CustomDataFormatter<CommonLOV> customFormatter = new CustomDataFormatter<CommonLOV>() {
		
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
		GWT.log("## plug LOV header data ke dalam span LOV:" + lovData);
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

	@Override
	public void setLOVData(CommonLOVHeader lovData) {
		this.currentLovData = lovData ;
		plugDescFromLOV();
	}

	@Override
	public CommonLOVHeader getLOVData() {
		return currentLovData;
	}

	@Override
	public void setLovSource(LOVSource lovSource) {
		this.lovSource = lovSource ;
	}

	@Override
	public boolean isLOVRegisteredOnAttach() {
		return registerOnAttach;
	}

	@Override
	public void setLOVRegisteredOnAttach(boolean registerOnAttach) {
		this.registerOnAttach = registerOnAttach ; 
		
	}

	@Override
	public void registerLOVToContainer() {
		this.lovPoolPanel =  ClientSideLOVRelatedUtil.getInstance().getLOVPoolPanel(this);
		if ( this.lovPoolPanel!=null)
			this.lovPoolPanel.registerLOVCapableControl(this);
	}

	@Override
	public void startLoadingAnimation() {
		if ( loadingAnimation!= null){
			loadingAnimation.getStyle().setProperty("display", "");
		}
		
	}

	@Override
	public void stopLoadingAnimation() {
		if ( loadingAnimation!= null){
			loadingAnimation.getStyle().setProperty("display", "none");
		}
	}

	@Override
	protected void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();
		if ( loadingAnimation!= null){
			loadingAnimation.removeFromParent();
			loadingAnimation = null ; 
		}
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		Image img =  AbstractImagePrototype.create(CommonResourceBundle.getResources().iconLoadingWheel()).createImage(); 
		loadingAnimation = img.getElement(); 
		getElement().getParentElement().insertAfter(loadingAnimation, getElement()); 
		loadingAnimation.getStyle().setDisplay(Display.NONE);
	}
	
	@Override
	public void setValue(String value) {
		setValue(value , false);
	}
	
	
	@Override
	public String getValue() {
		return null;
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			final ValueChangeHandler<String> handler) {
		if ( handler == null)
			return null ; 
		handlers.add(handler); 
		return new HandlerRegistration() {
			
			@Override
			public void removeHandler() {
				handlers.remove(handler);
				
			}
		};
		
	}
	
	
	private String currentValue ; 

	@Override
	public void setValue(String value, boolean fireEvents) {
		this.currentValue = value ;
		plugDescFromLOV();
		if ( fireEvents){
			 ValueChangeEvent.fire(this, value); 
		 }
	}
	
	private void plugDescFromLOV() {
		if ( currentValue== null)
			return  ; 
		getElement().setInnerHTML("");
		if ( currentLovData!= null){
			 CommonLOV l =  currentLovData.findById(currentValue);
			 if ( l!= null){
				 getElement().setInnerHTML(  customFormatter.getStringForValue(l));
					 
			 }
		}else{
			GWT.log("##maaf, LOV data null");
		}

	}
	
	/**
	 * formatter. kalau di perlukan dedicated thing. bisa di atur di sini
	 */
	public CustomDataFormatter<CommonLOV> getCustomFormatter() {
		return customFormatter;
	}
	/**
	 * formatter. kalau di perlukan dedicated thing. bisa di atur di sini
	 */
	public void setCustomFormatter(
			CustomDataFormatter<CommonLOV> customFormatter) {
		this.customFormatter = customFormatter;
	}
	
	/**
	 * pasti pakai custom kalau dengan ini
	 **/
	public void setCustomLookupParameter(StrongTypedCustomLOVID<?> lookupParam ){
		setParameterId(lookupParam.getModulGroupId()+"." + lookupParam.getId()); 
		setLovSource(LOVSource.useCustomProvider); 
	}
}
