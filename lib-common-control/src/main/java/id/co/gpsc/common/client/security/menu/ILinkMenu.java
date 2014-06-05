package id.co.gpsc.common.client.security.menu;

import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;
import id.co.gpsc.jquery.client.menu.MenuClickHandler;

/**
 * Link Menu
 * @author Gede Sutarsa
 * @author I Gede Mahendra
 * @since Apr 12, 2013, 3:46:04 PM
 * @version $Id
 */
public interface ILinkMenu extends IBaseMenu{
	
	
	
	/**
	 * menaruh menu click handler ke dalam menu lowest level
	 **/
	public void setMenuClickHandler(MenuClickHandler<ApplicationMenuSecurity> clickHandler);

}
