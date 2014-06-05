package id.co.gpsc.common.client.security.menu;

import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;


/**
 * Base menu
 * @author Gede Sutarsa
 * @author I Gede Mahendra
 * @since Apr 12, 2013, 3:45:29 PM
 * @version $Id
 */
public interface IBaseMenu {
	
	/**
	 * prefix untuk menu token
	 **/
	public static final String MENU_ID_LINK_TOKEN_PREFIX="MENUID:";
	
	
	
	
	public ApplicationMenuSecurity getMenuData();
}
