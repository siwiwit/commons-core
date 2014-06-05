package id.co.gpsc.common.client.security.group;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.ExtendedComboBox;
import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.security.BaseRootSecurityPanel;
import id.co.gpsc.common.client.security.rpc.GroupRPCServiceAsync;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.UserGroup;
import id.co.gpsc.common.security.dto.UserGroupDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel list group
 * @author I Gede Mahendra
 * @since Nov 26, 2012, 3:40:17 PM
 * @version $Id
 */
public class GroupListPanel extends BaseRootSecurityPanel implements IReloadGridCommand,IOpenAndCloseable<UserGroupDTO>,IRemove<BigInteger>{

	private static GroupListPanelUiBinder uiBinder = GWT.create(GroupListPanelUiBinder.class);	
	
	@UiField ExtendedComboBox cmbCriteria;
	@UiField ExtendedTextBox txtCriteria;
	@UiField ExtendedButton btnSearch;
	@UiField SimplePanel spGridGroup;	
	@UiField ExtendedButton btnReset;
	
	private GroupGridPanel gridPanel;
	private JQDialog dialog;
	private GroupEditorPanel editorPanel;
	
	/**
	 * add by dode
	 * ini untuk flag ke group editor panelnya apakah bisa melakukan manipulasi terhadap tree menu
	 * true = tampilkan menu tree tab, false = hide menu tree tab
	 */
	private boolean disableMenuTreeManipulation = false;
	
	interface GroupListPanelUiBinder extends UiBinder<Widget, GroupListPanel> {}
	
	public GroupListPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		getBtnSearch().setText(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.search", "Search"));
		gridPanel = instantiateGridPanel() ; //new GroupGridPanel();
		gridPanel.setOpenCloseablse(this);	
		gridPanel.setRemoveUserGroup(this);
		
		spGridGroup.add(gridPanel);
		//FIXME Dein : Belum support Internalization
		cmbCriteria.addItem(I18Utilities.getInstance().getInternalitionalizeText("security.group.combobox.item.groupcode", "Group Code"), "groupCode");
		cmbCriteria.addItem(I18Utilities.getInstance().getInternalitionalizeText("security.group.combobox.item.groupname", "Group Name"), "groupName");
	}
	
	public GroupListPanel(boolean disableMenuTreeManipulation) {
		this();
		this.disableMenuTreeManipulation = disableMenuTreeManipulation;
	}
	
	@UiHandler("btnSearch")
	void onBtnSearchClick(ClickEvent event) {		
		getDataGroup();
	}
	
	@UiHandler("btnReset")
	void onBtnResetClick(ClickEvent event) {
		cmbCriteria.setSelectedIndex(0);
		txtCriteria.setText("");
		gridPanel.clearData();
		gridPanel.getGridButtonWidget().showHidePagingSide(false);
	}
	
	/**
	 * Query untuk dpt data group 
	 */
	public void getDataGroup(){
		UserGroup parameter = new UserGroup();
		parameter.setApplicationId(getApplicationDTO().getId());
		
		if(cmbCriteria.getSelectedIndex() == 0){
			parameter.setGroupCode(txtCriteria.getValue());
		}else{
			parameter.setGroupName(txtCriteria.getValue());
		}
		
		int posisi = gridPanel.getCurrentPageToRequest();
		int jumlahData = gridPanel.getPageSize();
		GroupRPCServiceAsync.Util.getInstance().getAllGroup(parameter, posisi, jumlahData, new AsyncCallback<PagedResultHolder<UserGroupDTO>>() {
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorgetallgroup", "Failed to get group data !"));			
			}

			@Override
			public void onSuccess(PagedResultHolder<UserGroupDTO> arg0) {				
				renderGrid(arg0);
			}			
		});
	}
	
	/**
	 * Render data to grid
	 * @param data
	 */
	private void renderGrid(PagedResultHolder<UserGroupDTO> data){
		if(data == null){
			gridPanel.clearData();
		}else{
			gridPanel.clearData();
			gridPanel.setData(data);
			/*List<UserGroupDTO> result = data.getHoldedData();
			if(result.size() > 0){
				gridPanel.clearData();
				for (UserGroupDTO userGroupDTO : result) {
					gridPanel.appendRow(new UserGroupDTO(userGroupDTO.getId(),userGroupDTO.getGroupCode(),userGroupDTO.getGroupName(),userGroupDTO.getStatus(),userGroupDTO.getSuperGroup()));
				}
			}*/
		}			
	}

	@Override
	public void reload() {
		getDataGroup();			
	}

	@Override
	public void closeDialog() {
		dialog.close();		
	}

	@Override
	public void openDialog(UserGroupDTO data) {		
		if(dialog == null){
			editorPanel = new GroupEditorPanel(disableMenuTreeManipulation);
			dialog = new JQDialog(I18Utilities.getInstance().getInternalitionalizeText("security.group.title.dialog.addgroupdetail", "Add Group Detail"), editorPanel);
			editorPanel.setClose(this);
			editorPanel.setReload(this);
		}
		
		if(data == null){
			dialog.setTitle(I18Utilities.getInstance().getInternalitionalizeText("security.group.title.dialog.addgroupdetail", "Add Group Detail"));
			editorPanel.clearDataComponent();
			editorPanel.getBtnAdd().setText(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.add", "Add Group"));
			editorPanel.setIsUpdate(false);
			editorPanel.getGridPanelGroupAssignment().clearData();
			editorPanel.getTxtGroupCode().setEnabled(true);
			editorPanel.getMenuTreeViewPanel().setVisible(false);
		}else{
			dialog.setTitle(I18Utilities.getInstance().getInternalitionalizeText("security.group.title.dialog.editgroupdetail", "Edit Group Detail"));
			UserGroup parameter = new UserGroup();
			parameter.setId(new BigInteger(data.getId().toString()));
			editorPanel.setDataIntoComponent(parameter);
			
			//add by dode
			//create menu tree
			editorPanel.createMenuTreeView(parameter.getId());
			editorPanel.getMenuTreeViewPanel().setVisible(true);
			
			editorPanel.getBtnAdd().setText(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.apply", "Apply Change"));
			editorPanel.setIsUpdate(true);
			editorPanel.getGridPanelGroupAssignment().clearData();
			editorPanel.getTxtGroupCode().setEnabled(false);
		}
		editorPanel.getBtnCancel().setText(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.cancel", "Cancel"));
		
		dialog.setHeightToAuto();
		dialog.setWidth(550);
		dialog.setResizable(false);
		dialog.show(true);
	}

	@Override
	public void remove(BigInteger parameter) {
		
		GroupRPCServiceAsync.Util.getInstance().delete(parameter, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorremoveusergroup", "Failed to remove user group !"));		
			}

			@Override
			public void onSuccess(Void arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.removeusergroupsuccess", "Remove user group success."));
				getDataGroup();			
			}			
		});
	}
	
	public ExtendedButton getBtnSearch() {
		return btnSearch;
	}

	@Override
	public String getTitlePanel() {		
		return getApplicationNameForTitlePanel() + I18Utilities.getInstance().getInternalitionalizeText("security.group.title.panel.usergrup", "User Group").toUpperCase();
	}	
	
	
	
	
	/**
	 * instantiate grid panel
	 **/
	protected GroupGridPanel instantiateGridPanel () {
		return GWT.create(GroupGridPanel.class);
	}
	
	
	/**
	 * membuat editor panel group
	 **/
	protected GroupEditorPanel instaintateEditorPanel () {
		return GWT.create(GroupEditorPanel.class); 
	}
}