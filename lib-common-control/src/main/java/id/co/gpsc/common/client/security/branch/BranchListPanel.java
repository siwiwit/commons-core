package id.co.gpsc.common.client.security.branch;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.ExtendedComboBox;
import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.security.BaseRootSecurityPanel;
import id.co.gpsc.common.client.security.rpc.BranchRPCServiceAsync;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;
import id.co.gpsc.common.security.dto.BranchDTO;
import id.co.gpsc.jquery.client.container.JQDialog;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Branch list panel
 * @author I Gede Mahendra
 * @since Jan 30, 2013, 2:33:00 PM
 * @version $Id
 */
public class BranchListPanel extends BaseRootSecurityPanel {

	private static BranchListPanelUiBinder uiBinder = GWT.create(BranchListPanelUiBinder.class);
	
	@UiField SimplePanel panelBranchList;
	@UiField ExtendedComboBox cmbCriteria;
	@UiField ExtendedTextBox txtCriteria;
	@UiField ExtendedButton btnSearch;
	@UiField ExtendedButton btnReset;

	private BranchGridPanel gridBranch;
	private BranchEditorPanel editorBranch;
	private JQDialog dialog;
	
	interface BranchListPanelUiBinder extends UiBinder<Widget, BranchListPanel> {}

	/**
	 * Constructor
	 */
	public BranchListPanel() {
		initWidget(uiBinder.createAndBindUi(this));		
		gridBranch = new BranchGridPanel();
		panelBranchList.add(gridBranch);		
		editorBranch = new BranchEditorPanel();
		
		populateComboBox();
		createdDialogContainer();
		setWorkerInterface();		
	}

	@UiHandler("btnSearch")
	void onBtnSearchClick(ClickEvent event) {
		getDataBranch();
	}
	
	@UiHandler("btnReset")
	void onBtnResetClick(ClickEvent event) {
		gridBranch.clearData();
		gridBranch.getGridButtonWidget().showHidePagingSide(false);
		cmbCriteria.setSelectedIndex(0);
		txtCriteria.setValue("");
	}
	
	@Override
	public String getTitlePanel() {		
		return getApplicationNameForTitlePanel() + "Branch".toUpperCase();
	}
	
	/**
	 * Get data branch
	 */
	private void getDataBranch(){
		SimpleQueryFilter[] filters = generateQueryFilter();
		int page = gridBranch.getCurrentPageToRequest();
		int pageSize = gridBranch.getPageSize();
		BranchRPCServiceAsync.Util.getInstance().getDataByParameter(filters, page, pageSize, new AsyncCallback<PagedResultHolder<BranchDTO>>() {			
			@Override
			public void onSuccess(PagedResultHolder<BranchDTO> data) {
				gridBranch.setData(data);			
			}
			
			@Override
			public void onFailure(Throwable ex) {
				Window.alert("Failed to get data branch");
				ex.printStackTrace();
			}
		});
	}	
	
	/**
	 * Generate query filter
	 */
	private SimpleQueryFilter[] generateQueryFilter(){
		if(txtCriteria.getValue() == "" || txtCriteria.getValue().trim().length() == 0){
			return null;
		}
		
		SimpleQueryFilter filter = new SimpleQueryFilter();
		filter.setField(cmbCriteria.getValue());		
		filter.setFilter(txtCriteria.getValue());
		filter.setOperator(SimpleQueryFilterOperator.likeBothSide);
		return new SimpleQueryFilter[] {filter};
	}
	
	/**
	 * Worker untuk editor panel
	 */
	private void workerForEditor(BranchDTO data){
		if(data == null){
			dialog.setTitle("Add New Branch");
			editorBranch.clearData();
		}else{
			dialog.setTitle("Modify Branch");
			editorBranch.renderDataToControl(data);
		}			
		dialog.show(true);
	}
	
	/**
	 * populate combo box criteria
	 */
	private void populateComboBox(){
		cmbCriteria.addItem("Branch Code", "branchCode");
		cmbCriteria.addItem("Branch Parent Code", "branchParendId");
		cmbCriteria.addItem("Branch Name", "branchName");
		cmbCriteria.addItem("Branch Address", "branchName");		
	}
	
	/**
	 * Membuat object dialog
	 */
	private void createdDialogContainer(){
		dialog = new JQDialog("");		
		dialog.setWidget(editorBranch);
		dialog.setWidth(500);
		dialog.setHeightToAuto();
		dialog.setResizable(false);		
		dialog.appendButton("Save", new Command() {			
			@Override
			public void execute() {
				editorBranch.saveOrUpdate();
			}
		});		
		dialog.appendButton("Cancel", new Command() {	
			@Override
			public void execute() {
				dialog.close();				
			}
		});
	}
	
	/**
	 * Set worker interface
	 */
	private void setWorkerInterface(){
		/*worker saat button add pada grid di-klik*/
		gridBranch.setWorkerAdd(new DataProcessWorker<BranchDTO>() {			
			@Override
			public void runProccess(BranchDTO data) {
				workerForEditor(null);
			}
		});
		
		/*worker saat button edit pada grid di-klik*/
		gridBranch.setWorkerEdit(new DataProcessWorker<BranchDTO>() {
			@Override
			public void runProccess(BranchDTO data) {
				workerForEditor(data);
			}			
		});
		
		/*worker saat button remove pada grid di-klik*/
		gridBranch.setWorkerRemove(new DataProcessWorker<BranchDTO>() {
			@Override
			public void runProccess(BranchDTO data) {
				editorBranch.remove(data.getId());
			}			
		});
		
		/*worker saat proses save or update berhasil. Kemudian reload grid dan tutup dialog*/
		editorBranch.setCommandReload(new IReloadGridCommand() {			
			@Override
			public void reload() {
				getDataBranch();
				dialog.close();
			}
		});
	}	
}