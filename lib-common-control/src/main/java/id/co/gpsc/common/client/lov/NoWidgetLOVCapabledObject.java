package id.co.gpsc.common.client.lov;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVSource;




/**
 * lov capabled object. versi ini tanpa widget, jadinya bisa di instantiate floating
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class NoWidgetLOVCapabledObject implements LOVCapabledControl{
	
	
	private CommonLOVHeader lovData ; 

	
	private String paremeterId ; 
	
	
	private LOVSource source =LOVSource.directFromLookupTable; 
	@Override
	public void onLOVChange(CommonLOVHeader lovData) {
		GWT.log("recieving lov data untuk LOVID :" + paremeterId +", di class :" +this.getClass().getName());
		this.lovData = lovData ; 
		
	}

	@Override
	public String getParameterId() {
		return paremeterId;
	}

	@Override
	public void setParameterId(String parameterId) {
		this.paremeterId = parameterId ; 
		
	}

	@Override
	public LOVSource getLOVSource() {
		return source;
	}

	@Override
	public void setLOVData(CommonLOVHeader lovData) {
		this.lovData = lovData ; 
	}

	@Override
	public CommonLOVHeader getLOVData() {
		return lovData;
	}

	@Override
	public void setLovSource(LOVSource lovSource) {
		this.source = lovSource;
		
	}

	@Override
	public boolean isLOVRegisteredOnAttach() {
		return false;
	}

	@Override
	public void setLOVRegisteredOnAttach(boolean registerOnAttach) {
		
	}

	@Override
	public void registerLOVToContainer() {
		
	}
	
	
	
	@Override
	public void startLoadingAnimation() {
		
		
		
		
		
		
	}

	@Override
	public void stopLoadingAnimation() {
		
	}

}
