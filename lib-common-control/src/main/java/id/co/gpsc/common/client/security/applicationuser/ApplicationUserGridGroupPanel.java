package id.co.gpsc.common.client.security.applicationuser;

import id.co.gpsc.common.client.control.worklist.I18ColumnDefinition;
import id.co.gpsc.common.client.control.worklist.I18EnabledSimpleGrid;
import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

/**
 * Panel group untuk form editor aplication user.<br>
 * @author I Gede Mahendra
 * @since Dec 18, 2012, 11:31:19 AM
 * @version $Id
 */
public class ApplicationUserGridGroupPanel extends I18EnabledSimpleGrid<UserGroupAssignmentDTO>{
	
	private StringColumnDefinition<UserGroupAssignmentDTO> colGroupCode;
	private StringColumnDefinition<UserGroupAssignmentDTO> colGroupName;	
	
	BaseColumnDefinition<?, ?>[] gridColumnDefinision;
		
	public ApplicationUserGridGroupPanel() {
		colGroupCode = new StringColumnDefinition<UserGroupAssignmentDTO>("GROUP CODE", 225) {
			@Override
			public String getData(UserGroupAssignmentDTO data) {			
				return data.getGroupCode();
			}		
		};
		
		colGroupName = new StringColumnDefinition<UserGroupAssignmentDTO>("GROUP NAME", 225) {
			@Override
			public String getData(UserGroupAssignmentDTO data) {			
				return data.getGroupName();
			}		
		};
		
		gridColumnDefinision = new BaseColumnDefinition<?, ?>[] {		
				colGroupCode,colGroupName
		};
		
		new Timer() {			
			@Override
			public void run() {
				getGridButtonWidget().appendButton("Add", "ui-icon ui-icon-plus", new Command() {			
					@Override
					public void execute() {
						Window.alert("Add");			
					}
				});				
				
				getGridButtonWidget().appendButton("Cancel", "ui-icon ui-icon-cancel", new Command() {			
					@Override
					public void execute() {
						Window.alert("Cancel");			
					}
				});
			}
		}.schedule(500);
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
}