package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.security.application.ApplicationListPanel;
import id.co.gpsc.common.client.security.control.ApplicationMenuLink;
import id.co.gpsc.common.client.security.group.GroupListPanel;
import id.co.gpsc.common.client.security.user.UserListPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Menu panel.
 * Pindah ke Class {@link id.co.gpsc.common.client.security.MenuSuperAdmin} 
 * @author I Gede Mahendra
 * @since Jan 3, 2013, 10:08:19 AM
 * @version $Id
 */
@Deprecated
public class MenuPanel extends Composite {

	private static MenuPanelUiBinder uiBinder = GWT.create(MenuPanelUiBinder.class);	
	@UiField ApplicationMenuLink menuApplicationList;
	@UiField ApplicationMenuLink menuUserGroup;
	@UiField ApplicationMenuLink menuUserList;

	interface MenuPanelUiBinder extends UiBinder<Widget, MenuPanel> {}

	public MenuPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		menuApplicationList.setWidgetGenerator(new RootWigetGenerator() {			
			@Override
			public BaseRootSecurityPanel generatePanel() {				
				return new ApplicationListPanel();
			}
		});
		menuUserGroup.setWidgetGenerator(new RootWigetGenerator() {			
			@Override
			public BaseRootSecurityPanel generatePanel() {				
				return new GroupListPanel();
			}
		});
		menuUserList.setWidgetGenerator(new RootWigetGenerator() {
			
			@Override
			public BaseRootSecurityPanel generatePanel() {
				return new UserListPanel();
			}
		});
		menuUserList.setCacheable(false);
	}	
}