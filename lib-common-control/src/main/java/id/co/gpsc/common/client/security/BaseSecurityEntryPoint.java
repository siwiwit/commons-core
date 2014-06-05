package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.security.control.RootPanelManager;
import id.co.gpsc.common.client.security.rpc.UserRPCServiceAsync;
import id.co.gpsc.common.security.dto.UserDetailDTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Base class entry poiny security
 * @author I Gede Mahendra
 * @since Jan 3, 2013, 9:31:02 AM
 * @version $Id
 */
public abstract class BaseSecurityEntryPoint extends BaseRootSecurityPanel implements EntryPoint{
	
	/**
	 * Halaman home aplikasi title nya kosong aja dlu
	 */
	@Override
	public String getTitlePanel() {	
		return "Home Panel Super Admin".toUpperCase();
	}
	
	/**
	 * Set current user yg login
	 */
	protected void setCurrentUserLogin(){
		UserRPCServiceAsync.Util.getInstance().getCurrentUserLogin(new AsyncCallback<UserDetailDTO>() {			
			@Override
			public void onSuccess(UserDetailDTO userDetail) {				
				setUserDetail(userDetail);
				
				/*Men-set div id dari content,title bar*/
				RootPanelManager.getInstance().setContentPlaceHolderId(getMainApplicationLayoutContainerId());
				RootPanelManager.getInstance().setTitleBarId(getTitleContainerId());
				RootPanelManager.getInstance().setContentPlaceMenuBarId(getMenuBarContainerId());
				RootPanelManager.getInstance().setContentPlaceMenuBarIdSuperAdmin(getMenuBarContainerIdSuperAdmin());
				RootPanelManager.getInstance().setContentPlaceMenuBarIdAdminWithBack(getMenuBarContainerIdAdminWithBack());
				RootPanelManager.getInstance().setContentPlaceMenuBarIdAdminWithoutBack(getMenuBarContainerIdAdminWithoutBack());
				RootPanelManager.getInstance().setHomePanelAdmin(new HomePanelAdmin());
				RootPanelManager.getInstance().setHomePanelSuperAdmin(new HomePanel());
								
				
				RootPanel.get(getMenuBarContainerIdSuperAdmin()).add(new MenuSuperAdmin());
				RootPanel.get(getMenuBarContainerIdAdminWithBack()).add(new MenuAdminWithBack());
				RootPanel.get(getMenuBarContainerIdAdminWithoutBack()).add(new MenuAdminWithoutBack());
				
				RootPanel.get(getTitleContainerId()).getElement().setInnerHTML(getTitlePanel());		
				RootPanel.get(getMainApplicationLayoutContainerId()).add(RootPanelManager.getInstance().getHomePanelSuperAdmin());
								
				RootPanel.get(getCurrentUserId()).getElement().setInnerHTML(getCurrentFullnameUserLogin());
				RootPanel.get(getCurrentApplicationDateId()).getElement().setInnerHTML(getCurrentApplicationDate());
				RootPanelManager.getInstance().switchMenuSuperAdmin();
			}
			
			@Override
			public void onFailure(Throwable ex) {
				ex.printStackTrace();
			}
		});
	}
	
	//BELUM TAU MAU DIISIIN APA AJA DSINI :-)
}