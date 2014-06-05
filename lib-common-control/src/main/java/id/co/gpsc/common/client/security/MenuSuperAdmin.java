package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.security.application.ApplicationListPanel;
import id.co.gpsc.common.client.security.control.ApplicationMenuLink;
import id.co.gpsc.common.client.security.control.LIWidget;
import id.co.gpsc.common.client.security.control.RootPanelManager;
import id.co.gpsc.common.client.security.control.ULWidget;
import id.co.gpsc.common.client.security.passwordpolicy.PasswordPolicyListPanel;
import id.co.gpsc.common.client.security.user.UserListPanel;

import com.google.gwt.user.client.ui.Composite;

/**
 * Menu untuk super admini arium security. Isi menu nya : Application List dan User List
 * @author I Gede Mahendra
 * @since Jan 4, 2013, 12:59:57 PM
 * @version $Id
 */
public class MenuSuperAdmin extends Composite{
		
	private ULWidget allMenu;
	
	/**
	 * Default Constructor
	 */
	public MenuSuperAdmin() {
		allMenu = new ULWidget();			
		initWidget(allMenu);
		setAllMenu();
	}
	
	/**
	 * Set all menu into elemen ul
	 */
	private void setAllMenu(){				
		ApplicationMenuLink menuApplicationList = new ApplicationMenuLink("Application List");
		ApplicationMenuLink menuUserList = new ApplicationMenuLink("User List");
		ApplicationMenuLink menuPasswordPolicy = new ApplicationMenuLink("Password Policy");
		
		RootPanelManager.getInstance().getListOfApplicationLink().add(menuApplicationList);
		RootPanelManager.getInstance().getListOfApplicationLink().add(menuUserList);
		RootPanelManager.getInstance().getListOfApplicationLink().add(menuPasswordPolicy);
		
		LIWidget liApplicationList = new LIWidget();
		liApplicationList.add(menuApplicationList);
		
		LIWidget liUserList = new LIWidget();
		liUserList.add(menuUserList);
		
		LIWidget liPasswordPolicy = new LIWidget();
		liPasswordPolicy.add(menuPasswordPolicy);
		
		allMenu.add(liApplicationList);
		allMenu.add(liUserList);
		allMenu.add(liPasswordPolicy);
		
		menuApplicationList.setWidgetGenerator(new RootWigetGenerator() {			
			@Override
			public BaseRootSecurityPanel generatePanel() {				
				return new ApplicationListPanel();
			}
		});
		
		menuUserList.setWidgetGenerator(new RootWigetGenerator() {			
			@Override
			public BaseRootSecurityPanel generatePanel() {				
				return new UserListPanel();
			}
		});	
		
		menuPasswordPolicy.setWidgetGenerator(new RootWigetGenerator() {			
			@Override
			public BaseRootSecurityPanel generatePanel() {
				return new PasswordPolicyListPanel();
			}
		});
		
		menuApplicationList.setCacheable(false);
		menuUserList.setCacheable(false);
		menuPasswordPolicy.setCacheable(false);
	}

	public ULWidget getAllMenu() {
		return allMenu;
	}	
}