package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.security.applicationuser.ApplicationUserListPanel;
import id.co.gpsc.common.client.security.branch.BranchListPanel;
import id.co.gpsc.common.client.security.control.ApplicationMenuLink;
import id.co.gpsc.common.client.security.control.LIWidget;
import id.co.gpsc.common.client.security.control.RootPanelManager;
import id.co.gpsc.common.client.security.control.ULWidget;
import id.co.gpsc.common.client.security.function.FunctionListPanel;
import id.co.gpsc.common.client.security.group.GroupListPanel;

import com.google.gwt.user.client.ui.Composite;

/**
 * Menu admin tanpa back button
 * @author I Gede Mahendra
 * @since Jan 7, 2013, 11:30:28 AM
 * @version $Id
 */
public class MenuAdminWithoutBack extends Composite{
	
	private ULWidget allMenu;
	
	/**
	 * Default Constructor
	 */
	public MenuAdminWithoutBack() {
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
		
		allMenu.add(liUserGroup);				
		allMenu.add(liApplicationUser);
		allMenu.add(liBranch);
		allMenu.add(liFunction);
		
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
	}
}