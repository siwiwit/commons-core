package id.co.gpsc.common.client.dualcontrol;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.control.ICustomValidator;
import id.co.gpsc.common.client.control.SimpleSearchFilterHandler;
import id.co.gpsc.common.client.control.worklist.PagedSimpleGridPanel;
import id.co.gpsc.common.client.form.exception.CommonFormValidationException;
import id.co.gpsc.common.client.rpc.DualControlDataRPCServiceAsync;
import id.co.gpsc.common.client.rpc.SimpleAsyncCallback;
import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.CommonDualControlContainerTable;
import id.co.gpsc.common.data.app.DualControlApprovalStatusCode;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.common.data.app.DualControlEnabledOperation;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;
import id.co.gpsc.jquery.client.util.JQueryUtils;

/**
 * base grid untuk dual control enable data
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public abstract class BaseDualControlDataGridPanel<DATA extends DualControlEnabledData<?, ?>> extends PagedSimpleGridPanel<DATA> implements SimpleSearchFilterHandler{

	
	
	
	static Map<String, String> STATUS_DATA =new HashMap<String, String>() ; 
	static {
		STATUS_DATA.put(DualControlApprovalStatusCode.APPLIED.toString(), ""); 
		STATUS_DATA.put(DualControlApprovalStatusCode.WAITING_APPROVE_CREATE.toString(), "menunggu persetujuan:tambah data");
		STATUS_DATA.put(DualControlApprovalStatusCode.WAITING_APPROVE_UPDATE.toString(), "menunggu persetujuan:rubah data");
		STATUS_DATA.put(DualControlApprovalStatusCode.WAITING_APPROVE_DELETE.toString(), "menunggu persetujuan:hapus data");
		STATUS_DATA.put(DualControlApprovalStatusCode.WAITING_APPROVE_BULK.toString(), "menunggu persetujuan:bulk (bulk upload)");
	}
	
	/**
	 * filter saat ini
	 **/
	private SimpleQueryFilter[] currentFilters ; 
	
	
	
	/**
	 * sort saat ini
	 **/
	private SimpleSortArgument[] currentSortArguments ; 
	
	
	
	/**
	 * state dari dual control editor, add edit approve dsb
	 **/
	private DualControlEditorState dualControlEditorState; 
	
	static final String APPLIED_STATUS = DualControlApprovalStatusCode.APPLIED.toString() ; 
	
	
	
	
	/**
	 * handler untuk edit data. jadi data di submit untuk di approve
	 **/
	protected DataProcessWorker<DATA> editForApprovalRequestHandler ; 
	
	/**
	 * handler untuk proses approve. jadinya ini akan membuka screen approval data
	 **/
	protected DataProcessWorker<DATA> editForApprovingHandler ; 
	
	
	
	/**
	 * ini handler utnuk view detail dari data
	 **/
	protected DataProcessWorker<DATA> viewDetailHandler ;
	
	
	/**
	 * custom validator untuk proses hapus data
	 */
	protected ICustomValidator<DATA> eraseDataCustomValidator ; 
	
	
	
	
	private CellButtonHandler<DATA> eraseButton = new CellButtonHandler<DATA>("ui-icon-trash", I18Utilities.getInstance().getInternalitionalizeText(  getEraseIconTitleI18nKey() , "Hapus"), generateEraseClickHandler()){
		public boolean isDataAllowMeToVisible(DATA data) {
			return isAllowEraseButtonShow(data);
		};
	}; 
	private CellButtonHandler<DATA> editButton =  new CellButtonHandler<DATA>("ui-icon-pencil", I18Utilities.getInstance().getInternalitionalizeText(getEditIconTitleI18nKey(), "Edit"), generateEditClickHandler()) {
		public boolean isDataAllowMeToVisible(DATA data) {
			return isAllowEditShow(data);
		};
	};
	
	
	private CellButtonHandler<DATA> viewDetailButton  =  new CellButtonHandler<DATA>("ui-icon-folder-open", I18Utilities.getInstance().getInternalitionalizeText( getViewDetailIconTitleI18nKey(), "tampilkan detail"), generateViewDetailClickHandler()) ;
	
	
	public BaseDualControlDataGridPanel(DataProcessWorker<DATA> editForApprovalRequestHandler  , DataProcessWorker<DATA> editForApprovingHandler  , DataProcessWorker<DATA> viewDetailHandler ) {
		super() ;  
	
		this.editForApprovalRequestHandler = editForApprovalRequestHandler ; 
		this.editForApprovingHandler = editForApprovingHandler ;
		this.viewDetailHandler = viewDetailHandler;
		
		
	}
	
	
	
	protected BaseDualControlDataGridPanel(){
		super(); 
	}
	
	public void setEditForApprovalRequestHandler(
			DataProcessWorker<DATA> editForApprovalRequestHandler) {
		this.editForApprovalRequestHandler = editForApprovalRequestHandler;
	}
	
	public void setEditForApprovingHandler(
			DataProcessWorker<DATA> editForApprovingHandler) {
		this.editForApprovingHandler = editForApprovingHandler;
	}
	
	public void setViewDetailHandler(DataProcessWorker<DATA> viewDetailHandler) {
		this.viewDetailHandler = viewDetailHandler;
	}
	
	
	
	
	/**
	 * tombol edit di show atau tidak. override ini kalau anda berancana bikun custom show /hide
	 **/
	protected boolean isAllowEditShow(DATA data){
		return DualControlApprovalStatusCode.APPLIED .equals(data.getApprovalStatus()); 
		//data.getApprovalStatus()
		//return true ; 
	}
	
	
	
	/**
	 * tombol hapus boleh ndak
	 **/
	protected boolean isAllowEraseButtonShow(DATA data){
		return DualControlApprovalStatusCode.APPLIED.equals(data.getApprovalStatus()); 
	}
	
	
	
	
	
	/**
	 * handler delete
	 **/
	protected DataProcessWorker<DATA> generateEraseClickHandler () {
		return new DataProcessWorker<DATA>() {

			@Override
			public void runProccess(DATA data) {
				if ( eraseDataCustomValidator!= null){
					try {
						eraseDataCustomValidator.validate(data); 
					} catch (CommonFormValidationException e) {
						Window.alert(e.getMessage()); 

						
						return ; 
					} 
				}
				String msg =  generateConfirmDeleteDataMessage(data);
				if ( !Window.confirm(msg))
					return ;
				JQueryUtils.getInstance().blockEntirePage( generateDeleteDataBlockingPanel());
				CommonDualControlContainerTable delReqData = new CommonDualControlContainerTable(); 
				delReqData.setApprovalStatus(DualControlApprovalStatusCode.WAITING_APPROVE_DELETE.toString());
				delReqData.setJsonData(data.generateJSONString()); 
				delReqData.setTargetObjectFQCN(data.getClass().getName());
				
				DualControlDataRPCServiceAsync.Util.getInstance().submitDataForApproval(delReqData, DualControlEnabledOperation.DELETE, new SimpleAsyncCallback<BigInteger>() {
					@Override
					public void onSuccess(BigInteger result) {
						JQueryUtils.getInstance().unblockEntirePage();
						Window.alert(getDefaultDeleteRequestSubmitedDoneMessage());
						getPageChangeHandler().onPageChange(1); // paksa navigasi ke halaman 1, agar di reload kembali
					}
					@Override
					protected void customFailurehandler(Throwable caught) {
						JQueryUtils.getInstance().unblockEntirePage();
						String msg = generateFailSubmitRequestDeleteDataMessage(caught); 
						Window.alert(msg);
					}
				}); 
			}
			
		};
	}
	
	
	/**
	 * handler untuk tombol edit
	 **/
	protected DataProcessWorker<DATA> generateEditClickHandler() {
		return new DataProcessWorker<DATA>() {
			@Override
			public void runProccess(DATA data) {

				if (editForApprovalRequestHandler != null) {
					editForApprovalRequestHandler.runProccess(data);
					return;
				}
				if (!GWT.isProdMode()) {
					Window.alert("anda belum memasang edit handler utnuk grid:"
							+ BaseDualControlDataGridPanel.this.getClass()
									.getName() + ", mohon di recek kembali");
				}
			}

		};
	}
	
	protected DataProcessWorker<DATA> generateViewDetailClickHandler () {
		return new DataProcessWorker<DATA>(){
			@Override
			public void runProccess(DATA data) {
				if ( viewDetailHandler== null){
					Window.alert("view detail untuk class " + BaseDualControlDataGridPanel.this.getClass().getName() +",blm di definisikan");
					return ; 
				}
				viewDetailHandler.runProccess(data); 
			}
		}; 
	}
	
	protected DataProcessWorker<DATA> generateApproveClickHandler () {
		return new DataProcessWorker<DATA>(){
			@Override
			public void runProccess(DATA data) {

				if ( editForApprovingHandler!= null){
					editForApprovingHandler.runProccess(data);
					return ; 
				}
				if (! GWT.isProdMode()){
					Window.alert("anda belum memasang approve handler utnuk grid:" + BaseDualControlDataGridPanel.this.getClass().getName() +", mohon di recek kembali" );
				}
			}
		}; 
	}
	
	
	/**
	 * ini loader untuk edit data as list worker. jadinya akan otomatis me-requestkan data untuk di edit
	 **/
	private PageChangeHandler editDataListHandler = new PageChangeHandler() {
		
		@Override
		public void onPageChange(int newPage) {
			 
			loadDataWorker(currentFilters, currentSortArguments, newPage, getPageSize());
		}
	};
	
	
	
	/**
	 * method ini untuk menampilkan data-data yang bisa di edit. jadinya bisa di ubah, atau bisa di delete. di sini data yang di tampilkan adalah data yang 
	 * <ol>
	 * <li>active - blm di mark deleted</li>
	 * <li>tidak sedang dalam proses approval</li>
	 * </ol>
	 * ini akan mengeset page = 1
	 **/
	public void initialLoadEditableData (SimpleQueryFilter[] filters , SimpleSortArgument[] sortArguments ){
		clearData(); 
		editButton.setVisible(  DualControlEditorState.CREATE_FOR_APPROVAL.equals(dualControlEditorState)  );
		//approveButton.setVisible( DualControlEditorState.APPROVAL.equals(dualControlEditorState) ); 
		eraseButton.setVisible(DualControlEditorState.CREATE_FOR_APPROVAL.equals(dualControlEditorState) );
		viewDetailButton.setVisible(DualControlEditorState.CREATE_FOR_APPROVAL.equals(dualControlEditorState)  ||DualControlEditorState.APPROVAL.equals(dualControlEditorState)  ||DualControlEditorState.VIEW_DETAIL.equals(dualControlEditorState)  );
		
		
		
		setPageChangeHandler(editDataListHandler); 
		this.currentFilters = filters  ; 
		this.currentSortArguments = sortArguments ; 
		loadDataWorker(currentFilters, currentSortArguments, getCurrentPageToRequest(), getPageSize());
		
	}
	
	
	/**
	 * class dual control object
	 **/
	public abstract Class<DATA> getDualControlClass() ;
	
	public Class<DATA> getDualControlDataClass() {
		return getDualControlClass();
	}
	
	
	
	
	/**
	 * label default untuk icon hapus
	 **/
	protected String getDefaultEraseIconTitle () {
		return "hapus data"; 
	}
	
	
	/**
	 * i18n key untuk title erase
	 **/
	protected abstract
		String getEraseIconTitleI18nKey () ; 
	
	
	/**
	 * i18n key untuk title delete
	 **/
	protected abstract
		String getEditIconTitleI18nKey () ; 
	
	
	/**
	 * default label utnuk edit data
	 **/
	protected abstract
		String getDefaultEditIconTitle () ; 
	
	
	/**
	 * i18n Key untuk view detail
	 **/
	protected abstract
		String getViewDetailIconTitleI18nKey () ;
	
	/**
	 * label default utnuk view detail data
	 **/
	protected abstract
		String getDefaultViewDetailIconTitle () ;
	/**
	 * key internalization untuk approve data
	 **/
	protected abstract
		String getApproveDataIconTitleI18nKey () ;
	
	/**
	 * label default utnuk approve data
	 **/
	protected abstract
		String getDefaultApproveDataIconTitle () ;
	
	
	/**
	 * message kalau membaca da ta yang bisa di edit gagal di lakukan. menjadi tanggung jawab masing-masing. termasuk isu i18n 
	 **/
	protected abstract String generateFailGetEditableDataListMessage (Throwable caught) ; 
	
	/**
	 * message kalau gagal submit request delete data
	 **/
	protected abstract String generateFailSubmitRequestDeleteDataMessage (Throwable caught) ;
	
	
	/**
	 * message kalau delete di submit
	 **/
	protected abstract String getDefaultDeleteRequestSubmitedDoneMessage (); 
	

	/**
	 * i18n code utnuk row number
	 **/
	protected abstract  String  getRowNumberColumnHeaderLabelI18nKey () ; 
	
	/**
	 * i18n key utnuk action column label
	 **/
	protected abstract String  getActionColumnHeaderLabelI18nKey () ; 
	

	/**
	 * message untuk konfirmasi kalau data mau di delete. render data anda di sini. kalau di perlukan internalization, silakan di masukan
	 * @param dataToErase data yang hendak di hapus. kalau ada property yang di perlukan silakan di get dari data nya
	 **/
	protected abstract String generateConfirmDeleteDataMessage (DATA dataToErase) ; 
	
	
	
	 
	@SuppressWarnings("rawtypes")
	private AsyncCallback swapCallback  = new SimpleAsyncCallback<PagedResultHolder<DATA>>() {
		@Override
		protected void customFailurehandler(Throwable caught) {
			String msg = generateFailGetEditableDataListMessage(caught); 
			Window.alert(msg); 
			showHideLoadingBlockerScreen(false);
			return ; 
		}
		
		@Override
		public void onSuccess(PagedResultHolder<DATA> result) {
			try{
				
				setData(result); 
				
			}finally{
				showHideLoadingBlockerScreen(false);
			}
			
		}
	};
	@SuppressWarnings("unchecked")
	private AsyncCallback<PagedResultHolder<? extends DualControlEnabledData<?, ?>>> dataGridCallback  = swapCallback;
	/**
	 * worker yang bertugas load data dari server. data yang di load adalah data untuk di edit(modify / delete)
	 **/
	protected void loadDataWorker (SimpleQueryFilter[] filters , SimpleSortArgument[] sortArguments , int page , int pageSize){
		clearData(); 
		showHideLoadingBlockerScreen(true); 
		DualControlDataRPCServiceAsync.Util.getInstance().getDataForEditList(getDualControlClass().getName(), filters, sortArguments, getPageSize(), getCurrentPageToRequest(), dataGridCallback);
	}
	
	
	
	
	
	/**
	 * reload grid
	 **/
	public void reload() {
		this.getPageChangeHandler().onPageChange(1);
	}
	
	
	
	
	/**
	 * label header untuk column action. en : action , id : tindakan. Default : <i>tindakan</i>
	 **/
	protected  String  getActionColumnHeaderLabel () {
		return "Tindakan"; 
	}
	
	
	/**
	 * row nomor urut dari column
	 **/
	protected  String  getRowNumberColumnHeaderLabel () {
		return "No"; 
	}
	


	@Override
	protected BaseColumnDefinition<DATA, ?>[] getActualGridColumnDefinitions() {
		
		
		@SuppressWarnings("unchecked")
		CellButtonHandler<DATA> [] arrBtn =(CellButtonHandler<DATA>[]) new CellButtonHandler<?>[]{
				editButton , eraseButton /*, approveButton*/ , viewDetailButton  
			}; 
		 
		
		BaseColumnDefinition<DATA, ?>[] specGrid = getColumnDefinitions(); 
		
		@SuppressWarnings("unchecked")
		BaseColumnDefinition<DATA, ?>[] actualGrid = (BaseColumnDefinition<DATA, ?>[])new BaseColumnDefinition<?, ?>[specGrid.length + 3];
		actualGrid[0] = generateRowNumberColumnDefinition(getRowNumberColumnHeaderLabel(), 50, getRowNumberColumnHeaderLabelI18nKey()); 
		actualGrid[1] = this.generateButtonsCell( arrBtn, getActionColumnHeaderLabel()
				, getActionColumnHeaderLabelI18nKey(), 100);
		for (int i =0 ; i< specGrid.length; i++){
			actualGrid[i+2] = specGrid[i];
		}
		actualGrid[actualGrid.length-1] = new StringColumnDefinition<DATA>("Status Data" , 200 ) {
			@Override
			public String getData(DATA data) {
				try {
					if ( STATUS_DATA.containsKey(data.getApprovalStatus().toString())){
						if ( data.getApprovalStatus()!= null)
							return STATUS_DATA.get(data.getApprovalStatus().toString());
						return ""; 
					}
					return data.getApprovalStatus()!= null ? data.getApprovalStatus().toString() : "";
					
				} catch (Exception e) {
					return "";
				}
			
			}
		};
		return actualGrid;
	}
	
	
	/**
	 * key i8n untuk status data. column ini ada pada bagian terakhir data
	 */
	protected String getDataStatusColumnI18nKey () {
		return null ; 
	}
	
	/**
	 * state dari dual control editor, add edit approve dsb
	 **/
	public void setDualControlEditorState(
			DualControlEditorState dualControlEditorState) {
		this.dualControlEditorState = dualControlEditorState;
	}
	/**
	 * state dari dual control editor, add edit approve dsb
	 **/
	public DualControlEditorState getDualControlEditorState() {
		return dualControlEditorState;
	}
	
	@Override
	public void applyFilter(SimpleQueryFilter[] filters) {
		resetGrid();
		initialLoadEditableData(filters, this.currentSortArguments);
		
	}
	
	@Override
	public void applyFilter(SimpleQueryFilter[] filters,
			SimpleSortArgument[] sorts) {
		resetGrid();
		initialLoadEditableData(filters, sorts);
		this.currentSortArguments = sorts ; 
	}
	
	
	public SimpleQueryFilter[] getCurrentFilters() {
		return currentFilters;
	}
	

	/**
	 * message yang di keluarkan pada saat menghapus. grid akan di block
	 **/
	protected   String generateDeleteDataBlockingPanel() {
		return "mohon menunggu, menghapus data " ; 
	}
	
	public SimpleSortArgument[] getCurrentSortArguments() {
		return currentSortArguments;
	}
	
	
	@Override
	public void onResetRequested() {
		clearData();
		
	}
	
	
	/**
	 * custom validator untuk proses hapus data
	 */
	public ICustomValidator<DATA> getEraseDataCustomValidator() {
		return eraseDataCustomValidator;
	}
	/**
	 * custom validator untuk proses hapus data
	 */
	public void setEraseDataCustomValidator(
			ICustomValidator<DATA> eraseDataCustomValidator) {
		this.eraseDataCustomValidator = eraseDataCustomValidator;
	}
}
