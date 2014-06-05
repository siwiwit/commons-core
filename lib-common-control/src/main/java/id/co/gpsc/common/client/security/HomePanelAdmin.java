package id.co.gpsc.common.client.security;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

/**
 * Home panel admin application
 * @author I Gede Mahendra
 * @since Jan 7, 2013, 12:10:00 PM
 * @version $Id
 */
public class HomePanelAdmin extends BaseRootSecurityPanel {

	private static HomePanelAdminUiBinder uiBinder = GWT
			.create(HomePanelAdminUiBinder.class);

	interface HomePanelAdminUiBinder extends UiBinder<Widget, HomePanelAdmin> {
	}

	public HomePanelAdmin() {
		initWidget(uiBinder.createAndBindUi(this));		
	}

	@Override
	public String getTitlePanel() {			
		return getApplicationNameForTitlePanel() + "Home Panel Admin";
	}	
}
