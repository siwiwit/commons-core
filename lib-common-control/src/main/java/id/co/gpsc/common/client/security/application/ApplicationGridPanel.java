package id.co.gpsc.common.client.security.application;

import id.co.gpsc.common.client.control.worklist.I18ColumnDefinition;
import id.co.gpsc.common.client.control.worklist.I18EnabledSimpleGrid;
import id.co.gpsc.common.client.security.common.IOpenCloseDialog;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.security.dto.ApplicationDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.BooleanColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

/**
 * Grid application
 * @author I Gede Mahendra
 * @since Dec 28, 2012, 11:11:36 AM
 * @version $Id
 */
public class ApplicationGridPanel extends I18EnabledSimpleGrid<ApplicationDTO>{
	
	private StringColumnDefinition<ApplicationDTO> colApplicationCode;
	private StringColumnDefinition<ApplicationDTO> colApplicationName;
	private StringColumnDefinition<ApplicationDTO> colApplicationUrl;
	private BooleanColumnDefinition<ApplicationDTO> colActive;
	private CellButtonHandler<ApplicationDTO>[] buttonAction;	
	private BaseColumnDefinition<?, ?>[] gridColumnDefinision;
	
	private IOpenCloseDialog<ApplicationDTO> actionDialog;
	private IReloadGridCommand actionReload;
	//private Command actionSwitchMenu;
	
	private DataProcessWorker<ApplicationDTO> actionSwitchMenu;
	
	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public ApplicationGridPanel() {
		colApplicationCode = new StringColumnDefinition<ApplicationDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.code", "CODE"), 100) {
			@Override
			public String getData(ApplicationDTO data) {				
				return data.getApplicationCode();
			}			
		};
		
		colApplicationName = new StringColumnDefinition<ApplicationDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.applicationlist.table.header.appname", "APP NAME"), 200) {
			@Override
			public String getData(ApplicationDTO data) {
				return data.getApplicationName();
			}			
		};
		
		colApplicationUrl = new StringColumnDefinition<ApplicationDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.applicationlist.table.header.url", "URL"), 250) {
			@Override
			public String getData(ApplicationDTO data) {				
				return data.getApplicationUrl();
			}			
		};
		
		colActive = new BooleanColumnDefinition<ApplicationDTO>(I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.active", "ACTIVE"), 90){
			@Override
			public Boolean getData(ApplicationDTO data) {
				return data.getIsActive();				
			}
		};
		
		buttonAction = (CellButtonHandler<ApplicationDTO>[]) new CellButtonHandler<?>[]{
			new CellButtonHandler<ApplicationDTO>("ui-icon ui-icon-pencil", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.edit", "Edit"), new DataProcessWorker<ApplicationDTO>() {
				@Override
				public void runProccess(ApplicationDTO data) {
					actionDialog.openDialog(data);						
				}				
			}),
			new CellButtonHandler<ApplicationDTO>("ui-icon ui-icon-tag", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.showdetail", "Show Detail"), new DataProcessWorker<ApplicationDTO>() {
				@Override
				public void runProccess(ApplicationDTO data) {
					actionSwitchMenu.runProccess(data);
				}				
			})
		};		
		
		gridColumnDefinision = new BaseColumnDefinition<?, ?>[] {
			generateButtonsCell(buttonAction, I18Utilities.getInstance().getInternalitionalizeText("security.common.table.header.action", "ACTION"), "", 70),
			colApplicationCode,colApplicationName,colApplicationUrl,colActive
		};
		
		new Timer() {			
			@Override
			public void run() {
				getGridButtonWidget().appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.add", "Add"), "ui-icon ui-icon-plus", new Command() {			
					@Override
					public void execute() {
						actionDialog.openDialog(null);			
					}
				});	
				getGridButtonWidget().appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.refresh", "Refresh"), "ui-icon ui-icon-refresh", new Command() {					
					@Override
					public void execute() {
						actionReload.reload();
					}
				});
			}
		}.schedule(500);
	}
	
	@Override
	public I18ColumnDefinition<ApplicationDTO>[] getI18ColumnDefinitions() {	
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<ApplicationDTO, ?>[] getColumnDefinitions() {		
		return (BaseColumnDefinition<ApplicationDTO, ?>[]) gridColumnDefinision;
	}

	/**
	 * Setter interface
	 * @param actionDialog
	 */
	public void setActionDialog(IOpenCloseDialog<ApplicationDTO> actionDialog) {
		this.actionDialog = actionDialog;
	}

	public void setActionReload(IReloadGridCommand actionReload) {
		this.actionReload = actionReload;
	}

	/**
	 * Set interface to switch menu
	 * @param actionSwitchMenu
	 */
	public void setActionSwitchMenu(DataProcessWorker<ApplicationDTO> actionSwitchMenu) {
		this.actionSwitchMenu = actionSwitchMenu;
	}	
}