package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.security.common.ConstantAriumSecurity;

/**
 * Panel root dari security composite
 * @author I Gede Mahendra
 * @since Jan 2, 2013, 4:17:16 PM
 * @version $Id
 */
public abstract class BaseRootSecurityPanel extends BaseAriumSecurityComposite{	
	
	/**
	 * Title panel dari composite
	 * @return String
	 */
	public abstract String getTitlePanel();	
	
	/**
	 * Level admin dari security. Apakah super admin atau admin aplikasi byasa
	 */
	protected String getAdminLevel(){
		return ConstantAriumSecurity.ADMIN_SECURITY_APPLICATION;
	}
	
	/**
	 * host page harus mengeset variable : SECURITY_MENUBAR_CONTAINER_ID
	 **/
	protected native String getMenuBarContainerId () /*-{		
		return $wnd["SECURITY_MENUBAR_CONTAINER_ID"];
	}-*/;
	
	/**
	 * di mana naruh main app panel. editor dsb
	 * host page harus mengeset variable : SECURITY_MAINAPP_CONTAINER_ID
	 **/
	protected native String getMainApplicationLayoutContainerId () /*-{
		return $wnd["SECURITY_MAINAPP_CONTAINER_ID"];
	}-*/;
	
	/**
	 * id untuk menaruh title dari content
	 * host page harus mengeset variable : SECURITY_TITLE_PANEL_ID
	 */
	protected native String getTitleContainerId() /*-{
		return $wnd["SECURITY_TITLE_PANEL_ID"];
	}-*/;
	
	/**
	 * id untuk menaruh komponent menu super admin	 
	 */
	protected native String getMenuBarContainerIdSuperAdmin() /*-{
		return $wnd["SECURITY_MENUBAR_CONTAINER_ID_SUPER_ADMIN"];
	}-*/;
	
	/**
	 * id untuk menaruh komponent menu admin dg button back	 
	 */
	protected native String getMenuBarContainerIdAdminWithBack() /*-{
		return $wnd["SECURITY_MENUBAR_CONTAINER_ID_ADMIN_WITH_BACK"];
	}-*/;
	
	/**
	 * id untuk menaruh komponent menu admin dg button back	 
	 */
	protected native String getMenuBarContainerIdAdminWithoutBack() /*-{
		return $wnd["SECURITY_MENUBAR_CONTAINER_ID_ADMIN_WITHOUT_BACK"];
	}-*/;
	
	/**
	 * id untuk menaruh current user id	 
	 */
	protected native String getCurrentUserId() /*-{
		return $wnd["SECURITY_CURRENT_USER_LOGIN"];
	}-*/;
	
	/**
	 * id untuk menaruh current application date	 
	 */
	protected native String getCurrentApplicationDateId() /*-{
		return $wnd["SECRITY_CURRENT_APPLICATION_DATE"];
	}-*/;
}