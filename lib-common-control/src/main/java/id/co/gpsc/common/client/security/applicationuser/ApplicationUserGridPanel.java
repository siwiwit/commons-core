package id.co.gpsc.common.client.security.applicationuser;

import id.co.gpsc.common.client.control.worklist.I18ColumnDefinition;
import id.co.gpsc.common.client.control.worklist.I18EnabledSimpleGrid;
import id.co.gpsc.common.client.security.common.IOpenCloseDialog;
import id.co.gpsc.common.client.security.group.IRemove;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.common.security.dto.UserGroupDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;

/**
 * Grid panel user
 * @author I Gede Mahendra
 * @since Dec 11, 2012, 10:27:42 AM
 * @version $Id
 */
public class ApplicationUserGridPanel extends I18EnabledSimpleGrid<UserDTO>{

	private String REMOVE_CONFIRMATION = I18Utilities.getInstance().getInternalitionalizeText("security.common.notification.deleteuser", "Are you sure to delete this data?");
	
	private IOpenCloseDialog<UserDTO> iOpenCloseDialog;
	private IRemove<UserDTO> iRemove;
	
	private StringColumnDefinition<UserDTO> colUsername;
	private StringColumnDefinition<UserDTO> colFullname;
	private StringColumnDefinition<UserDTO> colEmail;
	private StringColumnDefinition<UserDTO> colGroup;
	
	private CellButtonHandler<UserDTO>[] buttonAction;
	
	BaseColumnDefinition<?, ?>[] gridColumnDefinision;
	
	@SuppressWarnings("unchecked")
	public ApplicationUserGridPanel() {
		colUsername = new StringColumnDefinition<UserDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.username", "USERNAME"), 100){
			@Override
			public String getData(UserDTO data) {			
				return data.getUsername();
			}
		};
		colFullname = new StringColumnDefinition<UserDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.fullname", "FULLNAME"),200) {
			@Override
			public String getData(UserDTO data) {				
				return data.getFullName();
			}			
		};
		colEmail = new StringColumnDefinition<UserDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.email", "EMAIL"),220) {
			@Override
			public String getData(UserDTO data) {				
				return data.getEmail();
			}			
		};
		colGroup = new StringColumnDefinition<UserDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.group", "GROUP"),100) {
			@Override
			public String getData(UserDTO data) {
				if(!data.getUserGroups().isEmpty()){
					String group = "<ul>";
					for (UserGroupDTO userGroup : data.getUserGroups()) {
						group += "<li>" + userGroup.getGroupName() + "</li>";
					}
					group += "</ul>";
					return new HTML(group).toString();
				}else{
					return "";
				}
			}			
		};
		
		buttonAction = (CellButtonHandler<UserDTO>[]) new CellButtonHandler<?>[]{
			new CellButtonHandler<UserDTO>("ui-icon ui-icon-pencil", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.edit", "Edit"), new DataProcessWorker<UserDTO>() {
				@Override
				public void runProccess(UserDTO data) {					
					iOpenCloseDialog.openDialog(data);
				}				
			}),
			new CellButtonHandler<UserDTO>("ui-icon ui-icon-closethick", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.remove", "Remove"), new DataProcessWorker<UserDTO>() {
				@Override
				public void runProccess(UserDTO data) {
					Boolean result = Window.confirm(REMOVE_CONFIRMATION);
					if(result){
						iRemove.remove(data);
					}
				}				
			})
		};		
				
		gridColumnDefinision = new BaseColumnDefinition<?, ?>[] {				
				generateButtonsCell(buttonAction, I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.action", "ACTION"), "", 80),colUsername,colFullname,colEmail,colGroup
		};
		
		new Timer() {			
			@Override
			public void run() {
				getGridButtonWidget().appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.add", "Add"), "ui-icon ui-icon-plus", new Command() {			
					@Override
					public void execute() {					
						iOpenCloseDialog.openDialog(null);
					}
				});				
			}
		}.schedule(100);	
	}	
	
	@Override
	public I18ColumnDefinition<UserDTO>[] getI18ColumnDefinitions() {		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<UserDTO, ?>[] getColumnDefinitions() {		
		return (BaseColumnDefinition<UserDTO, ?>[]) gridColumnDefinision;
	}
	
	/**
	 * Set interface untuk open dan close dialog
	 * @param iOpenCloseDialog
	 */
	public void setiOpenCloseDialog(IOpenCloseDialog<UserDTO> iOpenCloseDialog) {
		this.iOpenCloseDialog = iOpenCloseDialog;
	}

	/**
	 * Set interface untuk remove data user assignment
	 * @param iRemove
	 */
	public void setiRemove(IRemove<UserDTO> iRemove) {
		this.iRemove = iRemove;
	}
}