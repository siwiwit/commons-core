package id.co.gpsc.common.client.security.applicationuser;

import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.control.MenuTree;
import id.co.gpsc.common.client.security.group.IAdd;
import id.co.gpsc.common.client.security.group.IRemove;
import id.co.gpsc.common.client.security.lookup.BrowseLookupUser;
import id.co.gpsc.common.client.security.rpc.ApplicationUserRPCServiceAsync;
import id.co.gpsc.common.client.security.rpc.FunctionRPCServiceAsync;
import id.co.gpsc.common.client.security.rpc.GroupAssignmentRPCServiceAsync;
import id.co.gpsc.common.control.SingleValueLookupResultHandler;
import id.co.gpsc.common.security.domain.ApplicationUser;
import id.co.gpsc.common.security.domain.ApplicationUserKey;
import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQTabContainerPanel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel editor application user
 * @author I Gede Mahendra
 * @since Dec 18, 2012, 11:54:28 AM
 * @version $Id
 */
public class ApplicationUserEditorPanel extends BaseAriumSecurityComposite implements IAdd<UserGroupAssignmentDTO>,IRemove<UserGroupAssignmentDTO>{

	private static ApplicationUserEditorPanelUiBinder uiBinder = GWT
			.create(ApplicationUserEditorPanelUiBinder.class);
	
	@UiField TextBoxWithLabel txtFullname;
	@UiField SimplePanel panelGrid;
	@UiField BrowseLookupUser browseUsername;	
	@UiField Hidden txtUserId;
		
	private ApplicationUserGridGroup gridGroup; 
	private List<UserGroupAssignmentDTO> temporaryListUserGroup;
	
	/**
	 * add by dode
	 * tab container dan menu tree component
	 */
	private JQTabContainerPanel tabContainer;
	private MenuTree menuTree;
	private List<BigInteger> groupIds;
	
	interface ApplicationUserEditorPanelUiBinder extends
			UiBinder<Widget, ApplicationUserEditorPanel> {
	}

	public ApplicationUserEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));			
		gridGroup = new ApplicationUserGridGroup();
		gridGroup.setiAddUserGroup(this);
		gridGroup.setiRemoveUserGroup(this);
		
		//initialize tab container and menu tree
		tabContainer = new JQTabContainerPanel();
		menuTree = new MenuTree();
		menuTree.setSize(480, 220);
		groupIds = new ArrayList<BigInteger>();
		
		panelGrid.add(tabContainer);
		
		//add by dode
		//set width tab container
		tabContainer.setWidth("500px");
		
		//add by dode
		//adding grid and menu tree to tab container
		new Timer() {			
			@Override
			public void run() {				
				tabContainer.appendTab(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.tab.title.group", "Groups"), gridGroup);
				tabContainer.appendTab(I18Utilities.getInstance().getInternalitionalizeText("security.common.tab.title.menus", "Menus"), menuTree);
			}
		}.schedule(1);
		
		browseUsername.setLookupHandler(new SingleValueLookupResultHandler<UserDTO>() {			
			@Override
			public void onSelectionDone(UserDTO data) {
				checkUsernameExist(data);				
			}
		});
	}
	
	/**
	 * Mengeset data pada form editor saat mode edit
	 * @param data
	 */
	public void setDataIntoForEdit(UserDTO data){
		if(data != null){
			txtFullname.setValue(data.getFullName());
			browseUsername.setValue(data);
			txtUserId.setValue(data.getIdUser().toString());
			getDataGroup(data.getIdUser());	
		}else{
			txtFullname.setValue("");
			browseUsername.setValue(null);
			txtUserId.setValue("");
			temporaryListUserGroup = null;
			renderDataGroup();
		}
	}
	
	/**
	 * Add data user group assignment ke dalam grid 
	 */
	@Override
	public void add(UserGroupAssignmentDTO data) {
		Boolean isExist = false;
		
		if(temporaryListUserGroup == null){
			temporaryListUserGroup = new ArrayList<UserGroupAssignmentDTO>();
		}
		for (UserGroupAssignmentDTO temp : temporaryListUserGroup) {
			if(temp.getGroupCode().equals(data.getGroupCode())){
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.alert.errorgroupalreadyexist", "Sorry, group already exist. Please choice another one !"));
				isExist = true;
				break;
			}
		}		
		
		if(!isExist){
			temporaryListUserGroup.add(data);
			renderDataGroup();
		}
	}
	
	@Override
	public void remove(UserGroupAssignmentDTO parameter) {
		int index = 0;
		for (UserGroupAssignmentDTO temp : temporaryListUserGroup) {
			if(temp.getGroupCode().equals(parameter.getGroupCode())){
				break;
			}
			index++;
		}
		
		temporaryListUserGroup.remove(index);
		renderDataGroup();
	}
	
	/**
	 * mengecek apakah user_id dalam aplikasi sudah ada apa belum.Klo belum, baru diijinkan insert baru
	 * @param data
	 */
	private void checkUsernameExist(final UserDTO data){				
		ApplicationUser parameter = new ApplicationUser();
		ApplicationUserKey key = new ApplicationUserKey();			
		parameter.setApplicationUser(key);
		
		key.setUserId(data.getIdUser());
		key.setApplicationId(getCurrentApplicationId());
		
		ApplicationUserRPCServiceAsync.Util.getInstance().countApplicationUserByParameter(parameter, new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable exc) {
				
			}

			@Override
			public void onSuccess(Integer result) {
				if(result > 0){
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.alert.errorusernamealreadyexist", "Sorry, username already exist !"));
					txtFullname.setValue("");
					txtUserId.setValue("");
				}else{
					txtFullname.setValue(data.getFullName());
					txtUserId.setValue(data.getIdUser().toString());
				}
			}			
		});		
	}
	
	/**
	 * Request data ke RPC
	 */
	private void getDataGroup(BigInteger userId){		
		GroupAssignmentRPCServiceAsync.Util.getInstance().getUserGroupByUserId(userId, new AsyncCallback<List<UserGroupAssignmentDTO>>() {
			@Override
			public void onFailure(Throwable data) {
							
			}

			@Override
			public void onSuccess(List<UserGroupAssignmentDTO> data) {
				temporaryListUserGroup = data;
				renderDataGroup();
			}			
		});
	}
	
	/**
	 * Render data ke dalam grid 
	 */
	private void renderDataGroup(){
		gridGroup.clearData();
		menuTree.clearTree();
		if(temporaryListUserGroup != null){									
			if(temporaryListUserGroup.size() > 0){
				gridGroup.clearData();
				groupIds.clear();
				for (UserGroupAssignmentDTO dto : temporaryListUserGroup) {					
					gridGroup.appendRow(new UserGroupAssignmentDTO(dto.getGroupCode(), dto.getGroupName()));
					groupIds.add(dto.getGroupId());
				}//end for	
				
				//create menu tree
				try {
					  FunctionRPCServiceAsync.Util.getInstance().getFunctionByGroupIdOrderByTreeLevelAndSiblingOrder(groupIds, new AsyncCallback<List<Function>>() {

						  @Override
						  public void onFailure(Throwable e) {
							  Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.control.menutree.errorgetmenudata", "Failed to get menu data !"));
							  e.printStackTrace();
						  }
						  
						  @Override
						  public void onSuccess(List<Function> result) {
							  menuTree.generateTree(result);
							  
							  //jika result null atau empty tampilkan pesan ini
							  if (result == null || result.isEmpty())
								  Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.control.menutree.errornomenulisted", "There is no menu listed."));
						  }
					});
				  } catch (Exception e) {
					  Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.control.menutree.errorgetmenudata", "Failed to get menu data !"));
					  e.printStackTrace();
				  }
			}//end if
		}//end if
	}

	/*Getter & Setter*/	
	public List<UserGroupAssignmentDTO> getTemporaryListUserGroup() {
		return temporaryListUserGroup;
	}
	public Hidden getTxtUserId() {
		return txtUserId;
	}	
}