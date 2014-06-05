/**
 * 
 */
package id.co.gpsc.common.client.security.group;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.control.CheckBoxMenuTree;
import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.domain.FunctionAssignment;
import id.co.gpsc.common.security.dto.UserGroupDTO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Dode
 * @version $Id
 * @since Jan 9, 2013, 3:15:45 PM
 */
public class GroupMenuTreeEditorPanel extends BaseAriumSecurityComposite {

	private static GroupMenuTreeEditorPanelUiBinder uiBinder = GWT
			.create(GroupMenuTreeEditorPanelUiBinder.class);
	@UiField ExtendedButton btnApply;
	@UiField ExtendedButton btnFinish;
	@UiField CheckBoxMenuTree menuTree;
	
	private UserGroupDTO currentData;

	interface GroupMenuTreeEditorPanelUiBinder extends
			UiBinder<Widget, GroupMenuTreeEditorPanel> {
	}

	public GroupMenuTreeEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * get added menu items in function assignment object
	 * @return list of added function assignment
	 */
	public List<FunctionAssignment> getAddedMenuItems() {
		//ubah dari added list bertipe function ke tipe function assignment agar bisa langsung diinsert
		List<FunctionAssignment> addedFunctionAssignment = new ArrayList<FunctionAssignment>();
		if (!menuTree.getAddedMenu().isEmpty()) {
			for (Function addedItem : menuTree.getAddedMenu()) {
				FunctionAssignment newGroupMenuItem = new FunctionAssignment();
				newGroupMenuItem.setFunctionId(addedItem.getId());
				newGroupMenuItem.setGroupId(new BigInteger(currentData.getId().toString()));
				newGroupMenuItem.setCreatedOn(getApplicationDate());
				newGroupMenuItem.setCreatedBy(getCurrentUserLogin());
				addedFunctionAssignment.add(newGroupMenuItem);
			}
		}
		
		return addedFunctionAssignment;
	}
	
	/**
	 * get added menu items in function assignment object
	 * @return list of removed function assignment
	 */
	public List<FunctionAssignment> getRemoedMenuItems() {
		//ubah dari removed list bertipe function ke tipe function assignment agar lebih mudah untuk deletenya
		List<FunctionAssignment> removedFunctionAssignment = new ArrayList<FunctionAssignment>();
		if (!menuTree.getRemovedMenu().isEmpty()) {
			for (Function removedItem : menuTree.getRemovedMenu()) {
				FunctionAssignment deleteGroupMenuItem = new FunctionAssignment();
				deleteGroupMenuItem.setFunctionId(removedItem.getId());
				deleteGroupMenuItem.setGroupId(new BigInteger(currentData.getId().toString()));
				removedFunctionAssignment.add(deleteGroupMenuItem);
			}
		}
		return removedFunctionAssignment;
	}
	
	/**
	 * set user group current data
	 * @param currentData
	 */
	public void setCurrentData(UserGroupDTO currentData) {
		this.currentData = currentData;
	}
}
