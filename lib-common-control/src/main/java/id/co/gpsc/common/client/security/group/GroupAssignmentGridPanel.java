package id.co.gpsc.common.client.security.group;

import id.co.gpsc.common.client.control.worklist.I18ColumnDefinition;
import id.co.gpsc.common.client.control.worklist.I18EnabledSimpleGrid;
import id.co.gpsc.common.client.security.lookup.LookupUser;
import id.co.gpsc.common.control.SingleValueLookupResultHandler;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;
import id.co.gpsc.jquery.client.grid.event.GridSelectRowHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

/**
 * Grid untuk panel group assignment
 * @author I Gede Mahendra
 * @since Dec 7, 2012, 2:10:05 PM
 * @version $Id
 */
public class GroupAssignmentGridPanel extends I18EnabledSimpleGrid<UserGroupAssignmentDTO>{

	private String REMOVE_CONFIRMATION = I18Utilities.getInstance().getInternalitionalizeText("security.common.notification.deleteuser", "Are you sure to delete this data?");
	
	private IAdd<UserDTO> iUser;
	private IRemoveUser iRemove;
	private StringColumnDefinition<UserGroupAssignmentDTO> colUsername;
	private StringColumnDefinition<UserGroupAssignmentDTO> colFullname;
	private BaseColumnDefinition<?, ?>[] gridColumnDefinision;
	
	private UserGroupAssignmentDTO currentSelectedData;
	
	
	
	
	/**
	 * dialog lookup user, ini untuk memilih user
	 **/
	private LookupUser lookupUser ;  
	/**
	 * Constructor
	 */
	public GroupAssignmentGridPanel() {
		colUsername = new StringColumnDefinition<UserGroupAssignmentDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.username", "USERNAME"), 220) {
			@Override
			public String getData(UserGroupAssignmentDTO data) {				
				return data.getUsername();
			}			
		};
		
		colFullname = new StringColumnDefinition<UserGroupAssignmentDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.fullname", "FULL NAME"), 220) {
			@Override
			public String getData(UserGroupAssignmentDTO data) {				
				return data.getFullname();
			}			
		};
		
		gridColumnDefinision = new BaseColumnDefinition<?, ?>[] {
			colFullname,colUsername
		};
		
		new Timer() {			
			@Override
			public void run() {
				getGridButtonWidget().appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.add", "Add"), "ui-icon ui-icon-plus", new Command() {					
					@Override
					public void execute() {
						getLookupUser().showLookup(); 					
					}
				});				
				getGridButtonWidget().appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.remove", "Remove"), "ui-icon ui-icon-minus", new Command() {					
					@Override
					public void execute() {
						if (currentSelectedData == null) {
							return ;
						}
						Boolean resultConfirm = Window.confirm(REMOVE_CONFIRMATION);
						if(resultConfirm){
							iRemove.remove(currentSelectedData);
						}
					}
				});
				
				GroupAssignmentGridPanel.this.addRowSelectedHandler(new GridSelectRowHandler<UserGroupAssignmentDTO>() {					
					@Override
					public void onSelectRow(String rowId, UserGroupAssignmentDTO selectedData) {						
						currentSelectedData = selectedData;						
					}
				});
			}
		}.schedule(1000);
	}
	
	@Override
	public void clearData() {	
		super.clearData();
		currentSelectedData = null;		
	}
	
	@Override
	public I18ColumnDefinition<UserGroupAssignmentDTO>[] getI18ColumnDefinitions() {		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<UserGroupAssignmentDTO, ?>[] getColumnDefinitions() {
		return (BaseColumnDefinition<UserGroupAssignmentDTO, ?>[]) gridColumnDefinision;
	}
	
	
	/**
	 * dialog lookup user, ini untuk memilih user
	 **/
	protected LookupUser getLookupUser() {
		if ( lookupUser==null){
			lookupUser = GWT.create(LookupUser.class);
			lookupUser.assignLookupResultHandler(new SingleValueLookupResultHandler<UserDTO>() {
				
				@Override
				public void onSelectionDone(UserDTO data) {
					iUser.add(data);
					
				}
			}); 
		}
		return lookupUser;
	}

	/*Set interface*/
	
	public void setiUser(IAdd<UserDTO> iUser) {
		this.iUser = iUser;
	}

	public void setiRemove(IRemoveUser iRemove) {
		this.iRemove = iRemove;
	}
}
