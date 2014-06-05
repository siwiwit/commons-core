package id.co.gpsc.common.client.security.control;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * UL Widget
 * @author I Gede Mahendra
 * @since Jan 4, 2013, 4:08:04 PM
 * @version $Id
 */
public class ULWidget extends ComplexPanel{

	public ULWidget(){
		setElement(DOM.createElement("ul"));		
	}
		 
	public void add(Widget w) {
	    add(w, getElement());
	}	
}