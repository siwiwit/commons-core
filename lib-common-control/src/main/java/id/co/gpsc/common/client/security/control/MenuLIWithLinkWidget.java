package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.client.security.menu.ILinkMenu;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;
import id.co.gpsc.jquery.client.menu.MenuClickHandler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

/**
 * Widget yg membentuk tag li dan a
 * @author Gede Sutarsa 
 * @author I Gede Mahendra 
 * @since Apr 12, 2013, 3:42:02 PM
 * @version $Id
 */
public class MenuLIWithLinkWidget extends MenuLIWidget implements ILinkMenu{
	
	
	private MenuClickHandler<ApplicationMenuSecurity> clickHandler;
		
	public MenuLIWithLinkWidget(final ApplicationMenuSecurity menuData){
		super(menuData);
		
		Anchor a = new Anchor(menuData.getLabel());
		a.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent evt) {
				//History.newItem(JamkrindoClientConstants.MENU_ID_LINK_TOKEN_PREFIX + menuData.getMenuId());
				clickHandler.execute(menuData);
			}
		});
		
		add(a);		
	}

	@Override
	public void setMenuClickHandler(
			MenuClickHandler<ApplicationMenuSecurity> clickHandler) {
		this.clickHandler = clickHandler ;
		
	}
}