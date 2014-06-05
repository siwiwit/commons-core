package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.client.security.menu.IBaseMenu;
import id.co.gpsc.common.client.security.menu.IRootMenuContainer;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;

import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel yg membentuk tag UL 
 * @author I Gede Mahendra
 * @since Apr 12, 2013, 3:42:50 PM
 * @version $Id
 */
public class MenuULWidget extends ComplexPanel implements IBaseMenu, IRootMenuContainer{
	
	/**
	 * Constructor
	 */
	public MenuULWidget(){
		setElement(DOM.createElement("ul"));		
	}
		 
	/**
	 * Add widget
	 */
	@Override
	public void add(Widget w) {
	    add(w, getElement());
	}	

	@Override
	public ApplicationMenuSecurity getMenuData() {		
		return null;
	}

	@Override
	public void addWidgets(List<?> childMenus) {
		for (Object widget : childMenus) {
			Widget obj = (Widget) widget;
			add(obj);
		}		
	}
}