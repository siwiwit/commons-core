package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.client.security.BaseRootSecurityPanel;
import id.co.gpsc.common.client.security.HomePanel;
import id.co.gpsc.common.client.security.HomePanelAdmin;
import id.co.gpsc.common.client.security.MenuAdminWithBack;
import id.co.gpsc.common.client.security.MenuSuperAdmin;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * Root Panel Manager
 * @author Gede Sutarsa
 * @author I Gede Mahendra
 * @since Jan 3, 2013, 10:32:27 AM
 * @version $Id
 */
public final class RootPanelManager {
			
	public static RootPanelManager instance ; 
	private String titleBarId; 
	private String contentPlaceHolderId;	
	private String contentPlaceMenuBarId;
	private String contentPlaceMenuBarIdSuperAdmin;
	private String contentPlaceMenuBarIdAdminWithBack;
	private String contentPlaceMenuBarIdAdminWithoutBack;
	private HomePanel homePanelSuperAdmin;
	private HomePanelAdmin homePanelAdmin;
	private BaseRootSecurityPanel currentWidget ;
	
	private MenuSuperAdmin menuSuperAdmin;
	private MenuAdminWithBack menuAdmin;
	
	private List<ApplicationMenuLink> listOfApplicationLink = new ArrayList<ApplicationMenuLink>();
	
	/**
	 * Get Singleton Object
	 * @return RootPanelManager
	 */
	public static RootPanelManager getInstance() {
		if ( instance==null)
			instance = new RootPanelManager();
		return instance;
	}
		
	/**
	 * Set Root Widget
	 * @param panel
	 */
	public void setRootWidget (BaseRootSecurityPanel panel) {
		// kalau currentWidget = panel do nothing
		if(currentWidget != panel){			
			if (currentWidget!=null ){
				currentWidget.removeFromParent();
			}
			
			RootPanel.get(titleBarId).getElement().setInnerHTML(panel.getTitlePanel());
			RootPanel.get(contentPlaceHolderId).add(panel);
			currentWidget = panel;
		}
	}
	
	/**
	 * Menu admin in application. Isi tombol back paling bawah
	 */
	public void switchMenuWithBackButton(){					
		currentWidget.removeFromParent();				
		RootPanel.get(titleBarId).getElement().setInnerHTML(getHomePanelAdmin().getTitlePanel());
		
		RootPanel.get(contentPlaceMenuBarIdSuperAdmin).setVisible(false);
		RootPanel.get(contentPlaceMenuBarIdAdminWithBack).setVisible(true);
		RootPanel.get(contentPlaceMenuBarIdAdminWithoutBack).setVisible(false);
	}
	
	/**
	 * Menu super admin yg isinya application list dan user list
	 */
	public void switchMenuSuperAdmin(){				
		if(currentWidget != null){
			currentWidget.removeFromParent();			
		}		
		RootPanel.get(titleBarId).getElement().setInnerHTML(getHomePanelSuperAdmin().getTitlePanel());
		RootPanel.get(contentPlaceMenuBarIdSuperAdmin).setVisible(true);
		RootPanel.get(contentPlaceMenuBarIdAdminWithBack).setVisible(false);
		RootPanel.get(contentPlaceMenuBarIdAdminWithoutBack).setVisible(false);					
	}
	
	/**
	 * Menu admin in application. tidak isi tombol back paling bawah
	 */
	public void switchMenuWithoutBackButton(){
		currentWidget.removeFromParent();
		RootPanel.get(titleBarId).getElement().setInnerHTML(getHomePanelAdmin().getTitlePanel());
		RootPanel.get(contentPlaceMenuBarIdSuperAdmin).setVisible(false);
		RootPanel.get(contentPlaceMenuBarIdAdminWithBack).setVisible(false);
		RootPanel.get(contentPlaceMenuBarIdAdminWithoutBack).setVisible(true);
	}
	
	/**
	 * Get panel menu super admin
	 * @return
	 */
	public MenuSuperAdmin getMenuSuperAdminPanel(){
		if(menuSuperAdmin == null){
			this.menuSuperAdmin = new MenuSuperAdmin();
		}
		return menuSuperAdmin;
	}
	
	/**
	 * Get panel menu admin byasa
	 * @return
	 */
	public MenuAdminWithBack getMenuAdminPanel(){
		if(menuAdmin == null){
			this.menuAdmin = new MenuAdminWithBack();
		}
		return menuAdmin;
	}
	
	/**
	 * Get div id dari title bar
	 */
	public String getTitleBarId() {
		return titleBarId;
	}

	/**
	 * Set div id dari title bar 
	 */
	public void setTitleBarId(String titleBarId) {
		this.titleBarId = titleBarId;
	}

	/**
	 * Get div id untuk main content panel
	 */
	public String getContentPlaceHolderId() {
		return contentPlaceHolderId;
	}

	/**
	 * Set div id untuk main content panel
	 */
	public void setContentPlaceHolderId(String contentPlaceHolderId) {
		this.contentPlaceHolderId = contentPlaceHolderId;
	}

	/**
	 * Get div id untuk menu baru
	 */
	public String getContentPlaceMenuBarId() {
		return contentPlaceMenuBarId;
	}
	
	/**
	 * Set div id untuk menu baru
	 */
	public void setContentPlaceMenuBarId(String contentPlaceMenuBarId) {
		this.contentPlaceMenuBarId = contentPlaceMenuBarId;
	}

	public String getContentPlaceMenuBarIdSuperAdmin() {
		return contentPlaceMenuBarIdSuperAdmin;
	}

	public void setContentPlaceMenuBarIdSuperAdmin(
			String contentPlaceMenuBarIdSuperAdmin) {
		this.contentPlaceMenuBarIdSuperAdmin = contentPlaceMenuBarIdSuperAdmin;
	}

	public String getContentPlaceMenuBarIdAdminWithBack() {
		return contentPlaceMenuBarIdAdminWithBack;
	}

	public void setContentPlaceMenuBarIdAdminWithBack(
			String contentPlaceMenuBarIdAdminWithBack) {
		this.contentPlaceMenuBarIdAdminWithBack = contentPlaceMenuBarIdAdminWithBack;
	}

	public String getContentPlaceMenuBarIdAdminWithoutBack() {
		return contentPlaceMenuBarIdAdminWithoutBack;
	}

	public void setContentPlaceMenuBarIdAdminWithoutBack(
			String contentPlaceMenuBarIdAdminWithoutBack) {
		this.contentPlaceMenuBarIdAdminWithoutBack = contentPlaceMenuBarIdAdminWithoutBack;
	}

	public HomePanel getHomePanelSuperAdmin() {
		return homePanelSuperAdmin;
	}
	public void setHomePanelSuperAdmin(HomePanel homePanelSuperAdmin) {
		this.homePanelSuperAdmin = homePanelSuperAdmin;
	}
	public HomePanelAdmin getHomePanelAdmin() {
		return homePanelAdmin;
	}
	public void setHomePanelAdmin(HomePanelAdmin homePanelAdmin) {
		this.homePanelAdmin = homePanelAdmin;
	}

	public List<ApplicationMenuLink> getListOfApplicationLink() {
		return listOfApplicationLink;
	}
}