package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.client.security.menu.IContainerMenu;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;

import com.google.gwt.user.client.ui.Anchor;

/**
 * LI Widget beserta container childnya
 * @author I Gede Mahendra
 * @since Apr 12, 2013, 3:39:46 PM
 * @version $Id
 */
public class MenuLIWidgetWithChild extends MenuLIWidget implements IContainerMenu<MenuLIWidget>{
	
	private MenuULWidget ul;
	
	/**
	 * Constructor Default
	 * @param label
	 */
	public MenuLIWidgetWithChild(ApplicationMenuSecurity menu) {
		super(menu);
		this.ul = new MenuULWidget();	
		Anchor anchor= new Anchor(menu.getLabel());
		anchor.setHref("#");		
		add(anchor);
		add(ul);			
	}

	@Override
	public void add(MenuLIWidget subMenu) {
		ul.add(subMenu);	
	}
}