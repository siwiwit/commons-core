package id.co.gpsc.common.client.control;

import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;
import id.co.gpsc.jquery.client.util.JQueryUtils;

/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class LovEnabledDoubleListSelectorControl extends DoubleListSelectorControl implements LOVCapabledControl{

	
	
	private String parameterId; 
	
	
	private boolean registeredOnAttach = false;  
	
	
	
	private CommonLOVHeader currentData ; 
	
	
	private LOVSource lovSource = LOVSource.directFromLookupTable; 
	public LovEnabledDoubleListSelectorControl(){
		super(); 
		String id =getElement().getId();
		if ( id== null|| id.length()==0)
			getElement().setId(DOM.createUniqueId()); 
		
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
		currentData = lovData; 
		clear(); 
		if ( currentData!= null && currentData.getDetails()!= null && !currentData.getDetails().isEmpty()){
			for ( CommonLOV scn :  currentData.getDetails()){
				addItem(scn.getDataValue(), scn.getLabel()); 
			}
		}
		
		if ( latestPlugedValue!= null)
			setValue(latestPlugedValue);
		
	}
	
	
	
	private String [] latestPlugedValue = null ; 
	
	
	@Override
	public void setValue(String[] value) {
		latestPlugedValue = value ; 
		super.setValue(value);
	}

	@Override
	public CommonLOVHeader getLOVData() {
		return currentData;
	}

	@Override
	public void setLovSource(LOVSource lovSource) {
		this.lovSource = lovSource;
		
	}

	@Override
	public boolean isLOVRegisteredOnAttach() { 
		return registeredOnAttach;
	}

	
	@Override
	public void onLOVChange(CommonLOVHeader lovData) {
		setLOVData(lovData);
		
	}
	
	
	
	@Override
	public void setLOVRegisteredOnAttach(boolean registerOnAttach) {
		this.registeredOnAttach = registerOnAttach;   
		
	}

	@Override
	public void registerLOVToContainer() {
		// FIXME : ini belum siap
	}

	@Override
	public void startLoadingAnimation() {
		JQueryUtils.getInstance().blockUI(getElement().getId(), "loading...");
		
	}

	@Override
	public void stopLoadingAnimation() {
		JQueryUtils.getInstance().unblockUI( getElement().getId());
		
	}

}
