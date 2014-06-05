package id.co.gpsc.common.client.security.group;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.ExtendedCheckBox;
import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.rpc.FunctionAssignmentRPCServiceAsync;
import id.co.gpsc.common.client.security.rpc.FunctionRPCServiceAsync;
import id.co.gpsc.common.client.security.rpc.GroupAssignmentRPCServiceAsync;
import id.co.gpsc.common.client.security.rpc.GroupRPCServiceAsync;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.domain.UserGroup;
import id.co.gpsc.common.security.domain.UserGroupAssignment;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;
import id.co.gpsc.common.security.dto.UserGroupDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQTabContainerPanel;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel Editor Group
 * @author I Gede Mahendra
 * @since Nov 26, 2012, 3:40:06 PM
 * @version $Id
 */
public class GroupEditorPanel extends BaseAriumSecurityComposite implements IAdd<UserDTO>,IRemoveUser{

	private static GroupEditorPanelUiBinder uiBinder = GWT
			.create(GroupEditorPanelUiBinder.class);
	@UiField TextBoxWithLabel txtGroupCode;
	@UiField TextBoxWithLabel txtGroupName;
	@UiField ExtendedCheckBox chkSuperUser;
	@UiField ExtendedCheckBox chkActive;
	@UiField ExtendedButton btnAdd;
	@UiField ExtendedButton btnCancel;
	@UiField Hidden txtUserGroupId;
	@UiField SimplePanel panelTabMenus;

	private List<UserGroupAssignmentDTO> tempUserAssignment;
	private GroupAssignmentGridPanel gridPanelGroupAssignment;	
	private JQTabContainerPanel tabContainer;
	
	private IReloadGridCommand reload;
	private IOpenAndCloseable openClose;
	private Boolean isUpdate = false;
	
	/**
	 * add by dode
	 * flag untuk hide yang berhubungan dengan menu tree
	 */
	private boolean hideMenuTab;
	
	/**
	 * variabel untuk menampilkan tree menu
	 * @author Dode
	 * @version $Id
	 * @since Jan 7, 2013, 3:40:04 PM
	 */
	private GroupMenuTreeViewPanel menuTreeViewPanel;
	private GroupMenuTreeEditorPanel menuTreeEditorPanel;
	private UserGroupDTO currentData;
	private FlowPanel menuPanel;
	
	interface GroupEditorPanelUiBinder extends
			UiBinder<Widget, GroupEditorPanel> {
	}

	/**
	 * Default Contructor
	 */
	public GroupEditorPanel() {
		//default adalah menu tab ditampilkan, hideMenuTab = false
		this(false);
	}
	
	public GroupEditorPanel(boolean hideMenuTabParam) {
		this.hideMenuTab = hideMenuTabParam;
		initWidget(uiBinder.createAndBindUi(this));
		tabContainer = new JQTabContainerPanel();
		
		txtGroupCode.setEnabled(false);
		gridPanelGroupAssignment = new GroupAssignmentGridPanel();
		gridPanelGroupAssignment.setiUser(this);
		gridPanelGroupAssignment.setiRemove(this);
		
		//instantiate menu tree view panel
		menuTreeViewPanel = new GroupMenuTreeViewPanel();
		menuTreeViewPanel.menuTree.setSize(480, 200);
		menuTreeViewPanel.setVisible(true);
		
		//instantiate menu tree editor panel
		menuTreeEditorPanel = new GroupMenuTreeEditorPanel();
		menuTreeEditorPanel.menuTree.setSize(480, 200);
		menuTreeEditorPanel.setVisible(false);
		
		//assign handler to button
		assignMenuTreeButtonHandler();
		
		//create menu panel
		menuPanel = new FlowPanel();
		menuPanel.add(menuTreeViewPanel);
		menuPanel.add(menuTreeEditorPanel);
		
		panelTabMenus.add(tabContainer);
		new Timer() {			
			@Override
			public void run() {				
				configureGroupEditorTab(); 
			}
		}.schedule(1);
	}
	
	
	
	
	
	
	/**
	 * ini di trigger untuk merender tab group. pergunakan ini kalau berencana override proses pembuatan tab
	 **/
	protected void configureGroupEditorTab () {
		tabContainer.appendTab(I18Utilities.getInstance().getInternalitionalizeText("security.group.title.tab.members", "Members"), gridPanelGroupAssignment);
		if (!hideMenuTab) {
			tabContainer.appendTab(I18Utilities.getInstance().getInternalitionalizeText("security.common.title.tab.menus", "Menus"), menuPanel);
		}
	}
	
	
	
	
	
	
	private void assignMenuTreeButtonHandler() {
		menuTreeViewPanel.btnEdit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				showHideMenuTreeViewAndEditorPanel(false);
			}
		});
		
		menuTreeEditorPanel.btnFinish.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				showHideMenuTreeViewAndEditorPanel(true);
			}
		});
		
		menuTreeEditorPanel.btnApply.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				applyEditedMenuItemData();
			}
		});
	}
	
	/**
	 * apply change menu item data to database
	 */
	private void applyEditedMenuItemData() {
		try {
			FunctionAssignmentRPCServiceAsync.Util.getInstance().addRemoveFunctionAssignment(menuTreeEditorPanel.getAddedMenuItems(), menuTreeEditorPanel.getRemoedMenuItems(), new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable e) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.alert.erroreditedmenuitem", "Failed to apply edited menu item !"));
					e.printStackTrace();
				}

				@Override
				public void onSuccess(Void arg0) {
					showHideMenuTreeViewAndEditorPanel(true);
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.alert.changesimpl", "Changes implemented."));
				}
			});
		} catch (Exception e) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.alert.erroreditedmenuitem", "Failed to apply edited menu item !"));
			e.printStackTrace();
		}
	}
	
	/**
	 * Request data ke server berdasarkan id dan menempatkan di komponent
	 * @param idUserGroup
	 */
	public void setDataIntoComponent(UserGroup parameter){
		GroupRPCServiceAsync.Util.getInstance().getUserGroupByParameter(parameter, new AsyncCallback<UserGroupDTO>() {
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorgetusergroup", "Failed to get user group data !"));
			}
			
			@Override
			public void onSuccess(UserGroupDTO data) {
				clearDataComponent();
				txtGroupCode.setValue(data.getGroupCode());
				txtGroupName.setValue(data.getGroupName());
				txtUserGroupId.setValue(data.getId().toString());				
				if(data.getSuperGroup().equals("Y")){
					chkSuperUser.setValue(true);
				}else{
					chkSuperUser.setValue(false);
				}								
				if(data.getStatus().equals("A")){
					chkActive.setValue(true);
				}else{
					chkActive.setValue(false);
				}
				
				getDataGroupAssignment(new BigInteger(data.getId().toString()));
				currentData = data;
				menuTreeEditorPanel.setCurrentData(currentData);
			}
		});
	}
	
	/**
	 * add by dode
	 * create tree menu dengan men set field group id nya
	 * @param groupId
	 */
	public void createMenuTreeView(BigInteger groupId) {
		List<BigInteger> groupIds = new ArrayList<BigInteger>();
		groupIds.add(groupId);
		
		//request menu item untuk create menu tree view
		try {
			  FunctionRPCServiceAsync.Util.getInstance().getFunctionByGroupIdOrderByTreeLevelAndSiblingOrder(groupIds, new AsyncCallback<List<Function>>() {

				  @Override
				  public void onFailure(Throwable e) {
					  Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.control.menutree.errorgetmenudata", "Failed to get menu data !"));
					  e.printStackTrace();
				  }
				  
				  @Override
				  public void onSuccess(List<Function> result) {
					  menuTreeViewPanel.menuTree.generateTree(result);
					  extractMenuItemIdFromMenuItemList(result);
					  
					  if (!hideMenuTab) {
						//jika result null atau empty tampilkan pesan ini
						  if (result == null || result.isEmpty())
							  Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.control.menutree.errornomenulisted", "There is no menu listed."));
					  }
				  }
			});
		  } catch (Exception e) {
			  Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.control.menutree.errorgetmenudata", "Failed to get menu data !"));
			  e.printStackTrace();
		  }
	}
	
	/**
	 * show hide menu tree view and editor panel
	 * @param visibleViewPanel
	 */
	private void showHideMenuTreeViewAndEditorPanel(boolean visibleViewPanel) {
		if (visibleViewPanel) {
			menuTreeEditorPanel.menuTree.clearTree();
			createMenuTreeView(new BigInteger(currentData.getId().toString()));
		} else {
			menuTreeViewPanel.menuTree.clearTree();
			createMenuTreeEditor();
		}
		menuTreeViewPanel.setVisible(visibleViewPanel);
		menuTreeEditorPanel.setVisible(!visibleViewPanel);
	}
	
	/**
	 * create menu tree editor 
	 */
	private void createMenuTreeEditor() {
		try {
			FunctionRPCServiceAsync.Util.getInstance().getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(currentData.getApplicationId(), new AsyncCallback<List<Function>>() {
				
				@Override
				public void onSuccess(List<Function> result) {
					menuTreeEditorPanel.menuTree.setMenus(result);
					menuTreeEditorPanel.menuTree.generateTree();
				}
				
				@Override
				public void onFailure(Throwable e) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.alert.errorfailedgetallmenu", "Failed to get all menu item !"));
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.alert.errorfailedgetallmenu", "Failed to get all menu item !"));
			e.printStackTrace();
		}
	}
	
	/**
	 * mendapatkan list menu item id dari list menu
	 * mendapatkan id dari menu selected untuk grup ini
	 * untuk keperluan edit tree menu
	 * @param menuItems menu items group
	 */
	private void extractMenuItemIdFromMenuItemList(List<Function> menuItems) {
		List<BigInteger> menuItemsId = new ArrayList<BigInteger>();
		menuTreeEditorPanel.menuTree.setSelectedMenus(menuItemsId);
		if (menuItems == null || menuItems.isEmpty())
			return ;
		for (Function menuItem : menuItems) {
			menuItemsId.add(menuItem.getId());
		}
	}
	
	/**
	 * Get data group assignment
	 */
	private void getDataGroupAssignment(BigInteger parameter){
		UserGroupAssignment data = new UserGroupAssignment();
		data.setGroupId(parameter);
		
		GroupAssignmentRPCServiceAsync.Util.getInstance().getAllGroup(data, 0, 10, new AsyncCallback<PagedResultHolder<UserGroupAssignmentDTO>>() {

			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorgetallgroup", "Failed to get group data !"));					
			}

			@Override
			public void onSuccess(PagedResultHolder<UserGroupAssignmentDTO> result) {				
				renderGridUserAssignment(result);
			}			
		});
	}
	
	/**
	 * Render grid group assignment
	 * @param result
	 */
	private void renderGridUserAssignment(PagedResultHolder<UserGroupAssignmentDTO> data){		
		if(data == null){
			gridPanelGroupAssignment.clearData();
			if(tempUserAssignment != null){
				tempUserAssignment.clear();
			}					
		}else{
			gridPanelGroupAssignment.clearData();
			List<UserGroupAssignmentDTO> result = data.getHoldedData();
			this.tempUserAssignment = result; 
			if(result.size() > 0){
				renderDataToGrid();
			}
		}	
	}
	
	@Override
	public void add(UserDTO data) {
		final UserGroupAssignmentDTO userGroup = new UserGroupAssignmentDTO();
		userGroup.setUsername(data.getUsername());
		userGroup.setFullname(data.getFullName());
		userGroup.setId(data.getIdUser());
		
		//cek username apakah sudah ada ato belum
		Boolean isExist = false;
		if(tempUserAssignment != null){
			for (UserGroupAssignmentDTO dto : tempUserAssignment) {
				if(dto.getUsername().equals(data.getUsername())){
					isExist = true;
					break;
				}
			}
		}else{
			tempUserAssignment = new ArrayList<UserGroupAssignmentDTO>();
		}
		
		if(isExist){
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.alert.errorusernamealreadyexist", "Sorry, username already exist !"));
		}else{
			UserGroupAssignment dataUserGroup = new UserGroupAssignment();
			dataUserGroup.setGroupId(new BigInteger(txtUserGroupId.getValue()));
			dataUserGroup.setUserId(data.getIdUser());
			dataUserGroup.setCreatedBy(getCurrentUserLogin());			
			
			GroupAssignmentRPCServiceAsync.Util.getInstance().insert(dataUserGroup, new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable arg0) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorinsertusergroupassignment", "Fail to insert user to user group assignment !"));			
				}

				@Override
				public void onSuccess(Void arg0) {
					tempUserAssignment.add(userGroup);
					renderDataToGrid();
				}				
			});			
		}				
	}
	
	/**
	 * Render data to grid
	 */
	private void renderDataToGrid(){		
		gridPanelGroupAssignment.clearData();
		for (UserGroupAssignmentDTO userGroupAssignmentDTO : tempUserAssignment) {
			gridPanelGroupAssignment.appendRow(new UserGroupAssignmentDTO(userGroupAssignmentDTO.getId(), userGroupAssignmentDTO.getUsername(), userGroupAssignmentDTO.getFullname()));
		}
	}
	
	/**
	 * Clear isi data dalam komponent
	 */
 	public void clearDataComponent(){
		txtGroupCode.setValue("");
		txtGroupName.setValue("");
		txtUserGroupId.setValue("");
		chkActive.setValue(false);
		chkSuperUser.setValue(false);
		menuTreeViewPanel.menuTree.clearTree();
	}
	
	@UiHandler("btnAdd")
	void onBtnAddClick(ClickEvent event) {
		if(validationForm()){
			UserGroup data = new UserGroup();
			data.setGroupCode(txtGroupCode.getValue());
			data.setGroupName(txtGroupName.getValue());	
			/*Application User diambil dari application yg diset saat super user admin mengklik di grid application user*/
			data.setApplicationId(getApplicationDTO().getId());
			data.setCreatedBy(getCurrentUserLogin());
			
			if(chkSuperUser.getValue()){
				data.setSuperGroup("Y");
			}else{
				data.setSuperGroup("N");
			}		
			
			if(chkActive.getValue()){
				data.setStatus("A");
			}else{
				data.setStatus("N");
			}
			
			if(isUpdate){
				data.setId(new BigInteger(txtUserGroupId.getValue()));
				data.setModifiedBy(getCurrentUserLogin());
				GroupRPCServiceAsync.Util.getInstance().update(data, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable arg0) {
						Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorupdateusergroup", "Failed to update user group data !"));			
					}

					@Override
					public void onSuccess(Void arg0) {
						Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.updateusergroupsuccess", "Update user group success."));
						reload.reload();
						openClose.closeDialog();					
					}				
				});
			}else{
				data.setCreatedBy(getCurrentUserLogin());
				GroupRPCServiceAsync.Util.getInstance().insert(data, new AsyncCallback<Void>() {			
					@Override
					public void onSuccess(Void arg0) {				
						Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.saveusergroupsuccess", "Save user group success."));
						reload.reload();
						openClose.closeDialog();
					}
					
					@Override
					public void onFailure(Throwable arg0) {
						Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorsaveusergroup", "Failed to save user group data !"));
					}
				});
			}
		}					
	}
	
	@UiHandler("btnCancel")
	void onBtnCancelClick(ClickEvent event) {
		menuTreeViewPanel.menuTree.clearTree();
		menuTreeEditorPanel.menuTree.clearTree();
		openClose.closeDialog();
	}	
	
	@Override
	public void remove(final UserGroupAssignmentDTO data) {		
		GroupAssignmentRPCServiceAsync.Util.getInstance().delete(data.getId(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorremoveusergroupassignment", "Fail to remove user from user group assignment !"));		
				arg0.printStackTrace();
			}

			@Override
			public void onSuccess(Void arg0) {
				int i = 0;
				for (UserGroupAssignmentDTO element : tempUserAssignment) {
					if(element.getUsername().equals(data.getUsername())){
						break;
					}
					i++;
				}		
				tempUserAssignment.remove(i);
				renderDataToGrid();
			}			
		});
	}
	
	/**
	 * Clear local variable group assignment
	 */
	public void clearTempListGroupAssignment(){
		if(tempUserAssignment == null){
			tempUserAssignment = new ArrayList<UserGroupAssignmentDTO>();
		}else{
			tempUserAssignment.clear();
		}
	}
	
	/**
	 * Validasi form
	 * @return True:Validasi sukses, False:Validasi gagal
	 */
	private boolean validationForm(){
		if(txtGroupCode.getValue() == ""){
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorgroupcodeempty", "Group Code is empty. Please fill it !"));
			txtGroupCode.setFocus(true);
			return false;
		}else if(txtGroupName.getValue() == ""){
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.group.allert.errorgroupnameempty", "Group name is empty. Please fill it !"));
			txtGroupName.setFocus(true);
			return false;
		}else{
			return true;
		}		
	}
	
	/*GETTER AND SETTER*/
	
	/**
	 * Set interface reload
	 * @param reload
	 */
	public void setReload(IReloadGridCommand reload) {
		this.reload = reload;
	}

	/**
	 * Set interface close
	 * @param close
	 */
	public void setClose(IOpenAndCloseable close) {
		this.openClose = close;
	}

	/**
	 * Get object button add
	 * @return btnAdd
	 */
	public ExtendedButton getBtnAdd() {
		return btnAdd;
	}
	
	/**
	 * Get object button cancel
	 * @return btnCancel
	 */
	public ExtendedButton getBtnCancel() {
		return btnCancel;
	}	
	
	/**
	 * Get object grid panel group assignment
	 * @return gridPanelGroupAssignment
	 */
	public GroupAssignmentGridPanel getGridPanelGroupAssignment() {
		return gridPanelGroupAssignment;
	}
	
	/**
	 * Get object txt group code
	 * @return txtGroupCode
	 */
	public TextBoxWithLabel getTxtGroupCode() {
		return txtGroupCode;
	}

	/**
	 * Get isUpdate
	 * @return True:State Edit, False:state Add
	 */
	public Boolean isUpdate() {
		return isUpdate;
	}

	/**
	 * Set isUpdate
	 * @param True:State Edit, False:state Add
	 */
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
		if (hideMenuTab) {
			//jika add new data hide panel tab
			panelTabMenus.setVisible(isUpdate);
		}
	}

	/**
	 * set current data
	 * @param currentData
	 */
	public void setCurrentData(UserGroupDTO currentData) {
		this.currentData = currentData;
	}

	/**
	 * get menu tree view panel
	 * @return menu tree view panel
	 */
	public GroupMenuTreeViewPanel getMenuTreeViewPanel() {
		return menuTreeViewPanel;
	}
	
	
	/**
	 * add by dode
	 * flag untuk hide yang berhubungan dengan menu tree
	 */
	public boolean isHideMenuTab() {
		return hideMenuTab;
	}
	/**
	 * add by dode
	 * flag untuk hide yang berhubungan dengan menu tree
	 */
	public void setHideMenuTab(boolean hideMenuTab) {
		this.hideMenuTab = hideMenuTab;
	}
}