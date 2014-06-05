package id.co.gpsc.common.client.util;



import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.client.lov.LovPoolPanel;



/**
 * utils untuk urusan LOV
 **/
public final class ClientSideLOVRelatedUtil {
	
	private static ClientSideLOVRelatedUtil instance ; 
	
	private ClientSideLOVRelatedUtil(){}
	
	
	/**
	 * singleton instance
	 **/
	public static ClientSideLOVRelatedUtil getInstance() {
		if(instance==null)
			instance = new ClientSideLOVRelatedUtil();
		return instance;
	}
	
	
	/**
	 * scan dom keatas untuk mencari Object dengan tipe {@link LovPoolPanel}
	 **/
	public LovPoolPanel getLOVPoolPanel (LOVCapabledControl control) {
		if( control==null|| !(control instanceof Widget))
			return null ; 
		int maxDepth = 1000;
		Element element = ((Widget)control).getElement().getParentElement();
		int i = 0 ; 
		while(element!=null){
			try {
				Object swap =  element.getPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF);
				if ( swap !=null && swap instanceof LovPoolPanel)
					return (LovPoolPanel)swap;
				
			} catch (Exception e) {
			}
			
			i++;
			if ( i>=maxDepth)
				return null ; 
			element = element.getParentElement(); 
		}
		return null ; 
		
	}
	
	
	

}
