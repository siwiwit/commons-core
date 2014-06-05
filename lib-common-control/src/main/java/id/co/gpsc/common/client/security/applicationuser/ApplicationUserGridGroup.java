package id.co.gpsc.common.client.security.applicationuser;

import id.co.gpsc.common.client.security.SecurityAppClientEditedDataGridPanel;
import id.co.gpsc.common.client.security.group.IAdd;
import id.co.gpsc.common.client.security.group.IRemove;
import id.co.gpsc.common.client.security.lookup.LookupGroupMultiple;
import id.co.gpsc.common.client.widget.BaseSimplePopupEditorPanel;
import id.co.gpsc.common.control.MultipleValueLookupResultHandler;
import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;
import id.co.gpsc.common.security.dto.UserGroupDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import java.math.BigInteger;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

/**
 * Grid User Group yg berisikan nama group yg dipunyai oleh masing2 user
 * @author I Gede Mahendra
 * @since Dec 18, 2012, 4:39:04 PM
 * @version $Id
 */
public class ApplicationUserGridGroup extends SecurityAppClientEditedDataGridPanel<UserGroupAssignmentDTO>{

	private IAdd<UserGroupAssignmentDTO> iAddUserGroup;
	private IRemove<UserGroupAssignmentDTO> iRemoveUserGroup;
	
	/**
	 * tombol hapus
	 **/	
	protected CellButtonHandler<UserGroupAssignmentDTO> eraseButton ;	
	
	
	
	
	/**
	 * lookup grid
	 **/
	private LookupGroupMultiple lookupGroup ;  
	
	@Override
	protected String getEraseDataNotificationMessageI18NKey() {		
		return "security.notification.deletegroupuser";
	}

	@Override
	protected UserGroupAssignmentDTO generateNewInstanceData() {		
		return null;
	}

	@Override
	protected BaseSimplePopupEditorPanel<UserGroupAssignmentDTO> generateEditorWidget() {		
		return null;
	}

	@Override
	protected String getGridCaptionDefaultLabel() {		
		return "Group User";
	}

	@Override
	protected String getGridCaptionI18NKey() {
		return "security.title.grid.groupuser";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<UserGroupAssignmentDTO, ?>[] getColumnDefinitions() {
		StringColumnDefinition<UserGroupAssignmentDTO> colGroupCode = new StringColumnDefinition<UserGroupAssignmentDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.group.table.header.groupcode", "GROUP CODE"), 150) {
			@Override
			public String getData(UserGroupAssignmentDTO data) {			
				return data.getGroupCode();
			}		
		};
		
		StringColumnDefinition<UserGroupAssignmentDTO> colGroupName = new StringColumnDefinition<UserGroupAssignmentDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.group.table.header.groupname", "GROUP NAME"), 150) {
			@Override
			public String getData(UserGroupAssignmentDTO data) {			
				return data.getGroupName();
			}		
		};
		
		BaseColumnDefinition<?, ?>[] gridColumnDefinision = new BaseColumnDefinition<?, ?>[] {colGroupCode,colGroupName};
		
		new Timer() {			
			@Override
			public void run() {
				getGridButtonWidget().appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.add", "Add"), "ui-icon ui-icon-plus", new Command() {			
					@Override
					public void execute() {
						/*CommonClientControlUtil.getInstance().getApplicationEventBus().dispatch("showUserGroupLookup", new SingleValueLookupResultHandler<UserGroupDTO>(){
							@Override
							public void onSelectionDone(UserGroupDTO data) {
								//Window.alert("DONE : " + data.getId() + " " + data.getGroupName());
								UserGroupAssignmentDTO groupAssignment = new UserGroupAssignmentDTO();
								groupAssignment.setGroupCode(data.getGroupCode());
								groupAssignment.setGroupName(data.getGroupName());
								groupAssignment.setGroupId(new BigInteger(data.getId().toString()));								
								iAddUserGroup.add(groupAssignment);
							}
						});*/	
						getLookupGroup().showLookup();
					}
				});								
			}
		}.schedule(500);
		
		return (BaseColumnDefinition<UserGroupAssignmentDTO, ?>[]) gridColumnDefinision;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected CellButtonHandler<UserGroupAssignmentDTO>[] generateButtonHandler() {		
		eraseButton = generateEraseButton();
		return (CellButtonHandler<UserGroupAssignmentDTO>[])new CellButtonHandler<?>[]{eraseButton};
	}
	
	@Override
	protected void eraseData(UserGroupAssignmentDTO dataToErase) {	
		iRemoveUserGroup.remove(dataToErase);
	}
	
	/*Getter & Setter*/
	public void setiAddUserGroup(IAdd<UserGroupAssignmentDTO> iAddUserGroup) {
		this.iAddUserGroup = iAddUserGroup;
	}
	public void setiRemoveUserGroup(IRemove<UserGroupAssignmentDTO> iRemoveUserGroup) {
		this.iRemoveUserGroup = iRemoveUserGroup;
	}		
	
	/**
	 * lookup grid
	 **/
	private LookupGroupMultiple getLookupGroup() {
		if ( lookupGroup==null){
			lookupGroup = GWT.create(LookupGroupMultiple.class);
			lookupGroup.assignLookupResultHandler(new MultipleValueLookupResultHandler<UserGroupDTO>() {
				
				@Override
				public void onSelectionDone(List<UserGroupDTO> data) {
					for (UserGroupDTO dto : data) {
						UserGroupAssignmentDTO groupAssignment = new UserGroupAssignmentDTO();
						groupAssignment.setGroupCode(dto.getGroupCode());
						groupAssignment.setGroupName(dto.getGroupName());
						groupAssignment.setGroupId(new BigInteger(dto.getId().toString()));								
						iAddUserGroup.add(groupAssignment);
					}
					
				}
			});
		}
		return lookupGroup;
	}
}