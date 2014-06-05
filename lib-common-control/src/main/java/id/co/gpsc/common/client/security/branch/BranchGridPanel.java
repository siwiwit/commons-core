package id.co.gpsc.common.client.security.branch;

import id.co.gpsc.common.client.control.worklist.I18ColumnDefinition;
import id.co.gpsc.common.client.control.worklist.I18EnabledSimpleGrid;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.security.dto.BranchDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

/**
 * Grid branch
 * @author I Gede Mahendra
 * @since Jan 30, 2013, 2:45:42 PM
 * @version $Id
 */
public class BranchGridPanel extends I18EnabledSimpleGrid<BranchDTO>{

	private StringColumnDefinition<BranchDTO> colBranchCode;
	private StringColumnDefinition<BranchDTO> colParentBranchCode;
	private StringColumnDefinition<BranchDTO> colBranchName;
	private StringColumnDefinition<BranchDTO> colBranchAddress;
	private CellButtonHandler<BranchDTO>[] buttonAction;
	private BaseColumnDefinition<?, ?>[] gridColumnDefinition;
	
	private DataProcessWorker<BranchDTO> workerAdd;
	private DataProcessWorker<BranchDTO> workerEdit;
	private DataProcessWorker<BranchDTO> workerRemove;
	
	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public BranchGridPanel() {
		colBranchCode = new StringColumnDefinition<BranchDTO>("Branch Code", 100, "") {
			@Override
			public String getData(BranchDTO data) {				
				return data.getBranchCode();
			}			
		};		
		
		colParentBranchCode = new StringColumnDefinition<BranchDTO>("Parent Code", 100, "") {
			@Override
			public String getData(BranchDTO data) {
				return data.getBranchParentCode();
			}			
		};
		
		colBranchName = new StringColumnDefinition<BranchDTO>("Branch Name",220,"") {
			@Override
			public String getData(BranchDTO data) {				
				return data.getBranchName();
			}			
		};
		
		colBranchAddress = new StringColumnDefinition<BranchDTO>("Branch Address", 220, "") {
			@Override
			public String getData(BranchDTO data) {			
				return data.getBranchAddress();
			}			
		};
		
		buttonAction = (CellButtonHandler<BranchDTO>[]) new CellButtonHandler<?>[]{
			new CellButtonHandler<BranchDTO>("ui-icon ui-icon-pencil", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.edit", "Edit"), new DataProcessWorker<BranchDTO>() {
				@Override
				public void runProccess(BranchDTO data) {
					workerEdit.runProccess(data);
				}				
			}),
			new CellButtonHandler<BranchDTO>("ui-icon ui-icon-closethick", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.showdetail", "Show Detail"), new DataProcessWorker<BranchDTO>() {
				@Override
				public void runProccess(BranchDTO data) {
					boolean answer = Window.confirm("Are you sure to delete this branch?");
					if(answer){
						workerRemove.runProccess(data);
					}
				}				
			})
		};		
		
		gridColumnDefinition = new BaseColumnDefinition<?, ?>[] {
			generateButtonsCell(buttonAction, "Action", "", 70),
			colBranchCode,colParentBranchCode,colBranchName,colBranchAddress
		};
		
		new Timer() {			
			@Override
			public void run() {
				getGridButtonWidget().appendButton("Add", "ui-icon ui-icon-plus", new Command() {					
					@Override
					public void execute() {
						workerAdd.runProccess(null);						
					}
				});				
			}
		}.schedule(1000);
	}
	
	@Override
	public I18ColumnDefinition<BranchDTO>[] getI18ColumnDefinitions() {		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<BranchDTO, ?>[] getColumnDefinitions() {		
		return (BaseColumnDefinition<BranchDTO, ?>[]) gridColumnDefinition;
	}

	
	/**
	 * Setter worker to add
	 * @param workerAddEdit
	 */
	public void setWorkerAdd(DataProcessWorker<BranchDTO> workerAddEdit) {
		this.workerAdd = workerAddEdit;
	}

	/**
	 * Setter worker to edit
	 * @param workerEdit
	 */
	public void setWorkerEdit(DataProcessWorker<BranchDTO> workerEdit) {
		this.workerEdit = workerEdit;
	}

	/**
	 * Setter worket to remove
	 * @param workerRemove
	 */
	public void setWorkerRemove(DataProcessWorker<BranchDTO> workerRemove) {
		this.workerRemove = workerRemove;
	}
}