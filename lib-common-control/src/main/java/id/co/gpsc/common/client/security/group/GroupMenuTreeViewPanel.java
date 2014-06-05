/**
 * 
 */
package id.co.gpsc.common.client.security.group;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.control.MenuTree;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

/**
 * panel untuk menampilkan group menu tree
 * @author Dode
 * @version $Id
 * @since Jan 9, 2013, 2:13:35 PM
 */
public class GroupMenuTreeViewPanel extends BaseAriumSecurityComposite {

	private static GroupMenuTreeViewPanelUiBinder uiBinder = GWT
			.create(GroupMenuTreeViewPanelUiBinder.class);
	@UiField ExtendedButton btnEdit;
	@UiField MenuTree menuTree;

	interface GroupMenuTreeViewPanelUiBinder extends
			UiBinder<Widget, GroupMenuTreeViewPanel> {
	}

	public GroupMenuTreeViewPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
