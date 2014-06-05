package id.co.gpsc.common.client.security.group;

import id.co.gpsc.common.client.control.worklist.I18ColumnDefinition;
import id.co.gpsc.common.client.control.worklist.I18EnabledSimpleGrid;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.security.dto.UserGroupDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.BooleanColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import java.math.BigInteger;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

/**
 * Grid panel user group
 * @author I Gede Mahendra
 * @since Nov 27, 2012, 10:16:55 AM
 * @version $Id
 */
public class GroupGridPanel extends I18EnabledSimpleGrid<UserGroupDTO>{

	private String REMOVE_CONFIRMATION = I18Utilities.getInstance().getInternalitionalizeText("security.common.notification.deleteuser", "Are you sure to delete this data?");
	
	private IOpenAndCloseable openCloseablse;
	private IRemove removeUserGroup;
	private StringColumnDefinition<UserGroupDTO> colGroupCode;
	private StringColumnDefinition<UserGroupDTO> colGroupName;
	private BooleanColumnDefinition<UserGroupDTO> colActive;
	private BooleanColumnDefinition<UserGroupDTO> colSuperUser;
	private CellButtonHandler<UserGroupDTO>[] buttonAction;	
	
	BaseColumnDefinition<?, ?>[] gridColumnDefinision;
	
	@SuppressWarnings("unchecked")
	public GroupGridPanel() {
		colGroupCode = new StringColumnDefinition<UserGroupDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.group.table.header.groupcode", "GROUP CODE"), 200) {
			@Override
			public String getData(UserGroupDTO data) {			
				return data.getGroupCode();
			}		
		};
		
		colGroupName = new StringColumnDefinition<UserGroupDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.group.table.header.groupname", "GROUP NAME"), 200) {
			@Override
			public String getData(UserGroupDTO data) {			
				return data.getGroupName();
			}		
		};
		
		colActive = new BooleanColumnDefinition<UserGroupDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.active", "ACTIVE"), 100){
			@Override
			public Boolean getData(UserGroupDTO data) {
				if(data.getStatus().equals("A")){
					return true;
				}else{
					return false;
				}				
			}			
		};
		
		
		colSuperUser = new BooleanColumnDefinition<UserGroupDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.group.table.header.superuser", "SUPER USER"), 100) {
			@Override
			public Boolean getData(UserGroupDTO data) {
				if(data.getSuperGroup().equals("N")){
					return false;
				}else{
					return true;
				}				
			}			
		};
		
		buttonAction = (CellButtonHandler<UserGroupDTO>[]) new CellButtonHandler<?>[]{
			new CellButtonHandler<UserGroupDTO>("ui-icon ui-icon-pencil", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.edit", "Edit"), new DataProcessWorker<UserGroupDTO>() {
				public void runProccess(UserGroupDTO data) {					
					openCloseablse.openDialog(data);
				};
			}),
			new CellButtonHandler<UserGroupDTO>("ui-icon ui-icon-closethick", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.remove", "Remove"), new DataProcessWorker<UserGroupDTO>() {
				@Override
				public void runProccess(UserGroupDTO data) {
					Boolean result = Window.confirm(REMOVE_CONFIRMATION);
					if(result){						
						removeUserGroup.remove(new BigInteger(data.getId().toString()));
					}
				}				
			})
		};
		
			
		gridColumnDefinision = new BaseColumnDefinition<?, ?>[] {
			generateButtonsCell(buttonAction, I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.action", "ACTION"), "", 80),
			colGroupCode,colGroupName,colActive,colSuperUser
		};
		
		new Timer() {			
			@Override
			public void run() {
				getGridButtonWidget().appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.add", "Add"), "ui-icon ui-icon-plus", new Command() {			
					@Override
					public void execute() {
						openCloseablse.openDialog(null);			
					}
				});				
			}
		}.schedule(100);		
	}
	
	@Override
	public I18ColumnDefinition<UserGroupDTO>[] getI18ColumnDefinitions() {				
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<UserGroupDTO, ?>[] getColumnDefinitions() {
		return (BaseColumnDefinition<UserGroupDTO, ?>[]) gridColumnDefinision;
	}

	public void setOpenCloseablse(IOpenAndCloseable openCloseablse) {
		this.openCloseablse = openCloseablse;
	}

	public void setRemoveUserGroup(IRemove removeUserGroup) {
		this.removeUserGroup = removeUserGroup;
	}
}
