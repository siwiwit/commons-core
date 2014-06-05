package id.co.gpsc.common.client.security;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

/**
 * Home panel untuk role super admin. Silahkan isikan sesuatu di home panel ini
 * @author I Gede Mahendra
 * @since Jan 3, 2013, 9:33:01 AM
 * @version $Id
 */
public class HomePanel extends BaseRootSecurityPanel {

	private static HomePanelUiBinder uiBinder = GWT.create(HomePanelUiBinder.class);
	interface HomePanelUiBinder extends UiBinder<Widget, HomePanel> {}
		
	public HomePanel() {
		initWidget(uiBinder.createAndBindUi(this));		
		System.out.println("HOME PANEL JALAN ");
	}

	@Override
	public String getTitlePanel() {		
		return "home panel super admin".toUpperCase();
	}
}