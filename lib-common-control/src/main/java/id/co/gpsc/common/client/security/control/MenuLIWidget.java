package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.client.security.menu.IBaseMenu;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel yg membentuk tag LI
 * @author I Gede Mahendra
 * @since Apr 12, 2013, 3:43:34 PM
 * @version $Id
 */
public class MenuLIWidget extends ComplexPanel implements IBaseMenu{
	
	private ApplicationMenuSecurity menuData;
	
	/**
	 * Default Constructor
	 */
	public MenuLIWidget(){
		setElement(DOM.createElement("li"));		
	}
	
	/**
	 * Constructor
	 * @param menuData
	 */
	public MenuLIWidget(ApplicationMenuSecurity menuData ){
		this();
		this.menuData = menuData;				
	}	
	
	/**
	 * Add widget
	 */
	@Override
	public void add(Widget w){		
		add(w, getElement());
	}
			
	/**
	 * Get application menu data
	 */
	public ApplicationMenuSecurity getMenuData() {
		return menuData;
	}
}