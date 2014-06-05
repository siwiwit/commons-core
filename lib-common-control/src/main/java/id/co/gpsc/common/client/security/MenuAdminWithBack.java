package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.security.applicationuser.ApplicationUserListPanel;
import id.co.gpsc.common.client.security.branch.BranchListPanel;
import id.co.gpsc.common.client.security.control.ApplicationMenuLink;
import id.co.gpsc.common.client.security.control.LIWidget;
import id.co.gpsc.common.client.security.control.RootPanelManager;
import id.co.gpsc.common.client.security.control.ULWidget;
import id.co.gpsc.common.client.security.function.FunctionListPanel;
import id.co.gpsc.common.client.security.group.GroupListPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

/**
 * Menu utk admin byasa.Jadi admin ini admin dari aplikasi.
 * Misalnya admin dari aplikasi opencams
 * @author I Gede Mahendra
 * @since Jan 4, 2013, 4:26:13 PM
 * @version $Id
 */
public class MenuAdminWithBack extends BaseAriumSecurityComposite{

	private ULWidget allMenu;
	
	/**
	 * Default Constructor
	 */
	public MenuAdminWithBack() {
		allMenu = new ULWidget();			
		initWidget(allMenu);
		setAllMenu();
	}
	
	/**
	 * Set all menu into elemen ul
	 */
	private void setAllMenu(){
		ApplicationMenuLink menuUserGroupList = new ApplicationMenuLink("Group List");
		ApplicationMenuLink menuApplicationUserList = new ApplicationMenuLink("Application User");
		ApplicationMenuLink menuBranchList = new ApplicationMenuLink("Branch List");
		ApplicationMenuLink menuFunctionList = new ApplicationMenuLink("Function List");
		Anchor menuBackToAdmin = new Anchor("Back");
		
		RootPanelManager.getInstance().getListOfApplicationLink().add(menuUserGroupList);		
		RootPanelManager.getInstance().getListOfApplicationLink().add(menuApplicationUserList);
		RootPanelManager.getInstance().getListOfApplicationLink().add(menuBranchList);
		RootPanelManager.getInstance().getListOfApplicationLink().add(menuFunctionList);
		
		LIWidget liUserGroup = new LIWidget();
		liUserGroup.add(menuUserGroupList);
		
		LIWidget liApplicationUser = new LIWidget();
		liApplicationUser.add(menuApplicationUserList);
		
		LIWidget liBranch = new LIWidget();
		liBranch.add(menuBranchList);
		
		LIWidget liFunction = new LIWidget();
		liFunction.add(menuFunctionList);
		
		LIWidget liBack = new LIWidget();
		liBack.add(menuBackToAdmin);		
		
		allMenu.add(liUserGroup);			
		allMenu.add(liApplicationUser);
		allMenu.add(liBranch);
		allMenu.add(liFunction);
		allMenu.add(liBack);
		
		menuUserGroupList.setWidgetGenerator(new RootWigetGenerator() {			
			@Override
			public BaseRootSecurityPanel generatePanel() {				
				return new GroupListPanel();
			}
		});		
		menuApplicationUserList.setWidgetGenerator(new RootWigetGenerator() {			
			@Override
			public BaseRootSecurityPanel generatePanel() {			
				return new ApplicationUserListPanel();
			}
		});
		menuBranchList.setWidgetGenerator(new RootWigetGenerator() {			
			@Override
			public BaseRootSecurityPanel generatePanel() {				
				return new BranchListPanel();
			}
		});
		menuFunctionList.setWidgetGenerator(new RootWigetGenerator() {			
			@Override
			public BaseRootSecurityPanel generatePanel() {			
				return new FunctionListPanel();
			}
		});
		menuBackToAdmin.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent arg0) {				
				RootPanelManager.getInstance().switchMenuSuperAdmin();
				BaseAriumSecurityComposite.setApplicationDTO(null);
			}
		});
	}
}
