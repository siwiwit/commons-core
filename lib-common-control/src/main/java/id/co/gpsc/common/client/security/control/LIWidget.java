package id.co.gpsc.common.client.security.control;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * LI Widget
 * @author I Gede Mahendra
 * @since Jan 4, 2013, 4:02:36 PM
 * @version $Id
 */
public class LIWidget extends ComplexPanel{

	public LIWidget(){
		setElement(DOM.createElement("li"));		
	}
	
	public void add(Widget w) {
	    add(w, getElement());
	  }
}
