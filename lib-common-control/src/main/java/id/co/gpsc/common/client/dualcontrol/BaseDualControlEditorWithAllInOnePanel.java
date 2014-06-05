package id.co.gpsc.common.client.dualcontrol;

import id.co.gpsc.common.client.common.ITitleAndSearchPanelFilter;
import id.co.gpsc.common.client.control.ExpensivePanelGenerator;
import id.co.gpsc.common.client.control.IPanelGenerator;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.GridColumnGroup;

import java.io.Serializable;
import java.math.BigInteger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * dual control panel , di jadikan 1 semua 
 * 
 * <ol>
 * <li>Editor</li>
 * <li>Grid</li>
 * <li>main panel</li>
 * <li>title panel</li>
 * </ol>
 * 
 *
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
  *@param <KEY> ini primary key dari data. di harapkan ini di antara ini : <ol>
 *<li>{@link BigInteger}</li>
 *<li>{@link Long}</li>
 *<li>{@link Integer}</li>
 *<li>{@link String}</li>
 *</ol>
 *key data di harapkan tunggal
 *@param <DATA> data yang di edit oleh editor. ini desain nya bukan DTO
 */
public abstract class BaseDualControlEditorWithAllInOnePanel<KEY extends Serializable ,  DATA extends DualControlEnabledData<DATA, KEY>> 
	extends 
	BaseDualControlDataEditor<KEY, DATA> implements IDualControlMultipleDataEditor<DATA>{
	
	
	
	
	
	
	
	/**
	 * generate main editor panel. Grid di buat dengan property pada 
	 **/
	public  BaseDualControlMainPanel<KEY, DATA> instantiateMainPanel (DualControlEditorState editorState) {
		final BaseDualControlMainPanel<KEY, DATA> retval = new BaseDualControlMainPanel< KEY, DATA>(editorState) {

			@Override
			protected BaseDualControlDataGridPanel<DATA> instantiateGrid(
					DataProcessWorker<DATA> editForApprovalRequestHandler,
					DataProcessWorker<DATA> editForApprovingHandler,
					DataProcessWorker<DATA> viewDetailHandler) {
				// end of grid
				return instantiateGridListOfEditableData(editForApprovalRequestHandler, editForApprovingHandler, viewDetailHandler);
			}
			
			

			@Override
			protected BaseDualControlDataEditor<KEY, DATA> instantiateEditorPanel() {
				BaseDualControlEditorWithAllInOnePanel.this.setMainPanelReference(this);
				return BaseDualControlEditorWithAllInOnePanel.this;
			}

			@Override
			protected ITitleAndSearchPanelFilter instantiateHeaderAndSearchFilter() {
				return generateHeaderAndSearchFilterPanel();
			}

			@Override
			protected Widget instantiateFooterPanel() {
				return generateFooterPanel();
			}

			@Override
			protected void instantiateDataForAddNewTemplate(AsyncCallback<DATA> callback) {
				
				  BaseDualControlEditorWithAllInOnePanel.this.instantiateDataForAddNewTemplate(callback);
			}

			@Override
			protected String getAddDataIconDefaultCaption() {
				return BaseDualControlEditorWithAllInOnePanel.this.getAddDataIconDefaultCaption();
			}

			@Override
			protected String getAddDataIconCaptionI18nCode() {
				return BaseDualControlEditorWithAllInOnePanel.this.getAddDataIconCaptionI18nCode();
			}
			
			 
		};
		setMainPanelReference(retval);
		
		final BaseDualControlEditorWithAllInOnePanel<KEY, DATA> swapTHis = this ; 
		retval.setEditorGenerator(new IPanelGenerator<BaseDualControlDataEditor<KEY,DATA>>() {
			@Override
			public void instantiatePanel(
					ExpensivePanelGenerator<BaseDualControlDataEditor<KEY, DATA>> callback) {
				swapTHis.makeClone(callback); //callback.onPanelGenerationComplete(widget);
				
			}
		}  );
		return retval ; 
	}

	@Override
	protected void initUnderlyingWidget(Widget uiBinderGeneratedWidget) {
		
		
		
		super.initUnderlyingWidget(uiBinderGeneratedWidget);
	}
	
	
	
	/**
	 * worker untuk instantiate grid list
	 **/
	protected BaseDualControlDataGridPanel<DATA> instantiateGridListOfEditableData (DataProcessWorker<DATA> editForApprovalRequestHandler,
			DataProcessWorker<DATA> editForApprovingHandler,
			DataProcessWorker<DATA> viewDetailHandler) {
		BaseDualControlDataGridPanel<DATA> retvalGrid = new BaseDualControlDataGridPanel< DATA>(editForApprovalRequestHandler, editForApprovingHandler , viewDetailHandler) {

			@Override
			public Class<DATA> getDualControlClass() {
				return BaseDualControlEditorWithAllInOnePanel.this.getProccessedClass();
			}

			@Override
			protected String getEraseIconTitleI18nKey() {
				return BaseDualControlEditorWithAllInOnePanel.this.getEraseIconTitleI18nKey();
			}

			@Override
			protected String getEditIconTitleI18nKey() {
				return BaseDualControlEditorWithAllInOnePanel.this.getEditIconTitleI18nKey();
			}

			@Override
			protected String getDefaultEditIconTitle() {
				return BaseDualControlEditorWithAllInOnePanel.this.getDefaultEditIconTitle();
			}
			@Override
			protected String getViewDetailIconTitleI18nKey() {
				return BaseDualControlEditorWithAllInOnePanel.this.getViewDetailIconTitleI18nKey();
			}
			@Override
			protected String getDefaultViewDetailIconTitle() {
				return BaseDualControlEditorWithAllInOnePanel.this.getDefaultViewDetailIconTitle();
			}
			@Override
			protected String getApproveDataIconTitleI18nKey() {
				return BaseDualControlEditorWithAllInOnePanel.this.getApproveDataIconTitleI18nKey();
			}
			@Override
			protected String getDefaultApproveDataIconTitle() {
				return BaseDualControlEditorWithAllInOnePanel.this.getDefaultApproveDataIconTitle();
			}
			@Override
			protected String generateFailGetEditableDataListMessage(
					Throwable caught) {
				return BaseDualControlEditorWithAllInOnePanel.this.generateFailGetEditableDataListMessage(caught);
			}

			@Override
			protected String generateFailSubmitRequestDeleteDataMessage(
					Throwable caught) {
				return BaseDualControlEditorWithAllInOnePanel.this.generateFailSubmitRequestDeleteDataMessage(caught);
			}

			@Override
			protected String getDefaultDeleteRequestSubmitedDoneMessage() {
				return BaseDualControlEditorWithAllInOnePanel.this.getDefaultDeleteRequestSubmitedDoneMessage();
			}

			@Override
			protected String getRowNumberColumnHeaderLabelI18nKey() {
				return BaseDualControlEditorWithAllInOnePanel.this.getRowNumberColumnHeaderLabelI18nKey();
			}

			@Override
			protected String getActionColumnHeaderLabelI18nKey() {
				return BaseDualControlEditorWithAllInOnePanel.this.getActionColumnHeaderLabelI18nKey();
			}

			@Override
			protected String generateConfirmDeleteDataMessage(
					DATA dataToErase) {
				return BaseDualControlEditorWithAllInOnePanel.this.generateConfirmDeleteDataMessage(dataToErase);
			}

			@Override
			protected BaseColumnDefinition<DATA, ?>[] getColumnDefinitions() {
				return BaseDualControlEditorWithAllInOnePanel.this.getColumnDefinitions();
			}
			
			
			@Override
			public void setData(PagedResultHolder<DATA> data) {
				super.setData(data);
			}
			
			
			
			
			@Override
			protected GridColumnGroup[] getGroupedColumnHeader() {
				return getGroupedGridColumnHeader();
			}
			/*
			@Override
			protected void renderJQWidgetOnAttachWorker() {
				super.renderJQWidgetOnAttachWorker();
				GridColumnGroup[] gheaders =  getGroupedColumnHeader();
				if ( gheaders!= null && gheaders.length>0){
					new Timer() {
						@Override
						public void run() {
							renderMergedColumnHeader(true);
						}
					}.schedule(500);
				}
			}
			*/
		};
		Integer tinggi = getGridHeight() ; 
		if ( tinggi != null ){
			retvalGrid.setHeight(tinggi);
		}
		Integer pgSize = getGridPagingSize() ; 
		if ( pgSize!= null){
			retvalGrid.setPageSize(pgSize);
		}
		return retvalGrid; 
		
	}
	
	
	
	
	
	/**
	 * ini kalau memerlukan grouped column header
	 */
	protected GridColumnGroup[] getGroupedGridColumnHeader() {
		
		return null;
	}
	
	
	
	/**
	 * ini untuk override grid paging size. kalau null tidak akan di set
	 */
	public Integer getGridPagingSize () {
		return null;
	}
	
	
	/**
	 * tinggi darigrid. kalau null berarti tidak akand i set
	 */
	protected Integer getGridHeight () {
		return null ; 
	}
	
	
	
	
	/**
	 * membuat search + title panel. ini untuk di taruh ke main panel. kalau anda mempergunakan UIBinder, new ui binder dan return di sini
	 **/
	protected abstract ITitleAndSearchPanelFilter generateHeaderAndSearchFilterPanel() ; 
	
	
	

	
	
	/**
	 * label default untuk icon hapus<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected String getDefaultEraseIconTitle () {
		return "hapus data"; 
	}
	
	
	
	/**
	 * widget untuk footer. kalau di perlukan footer, masukan di sini.<br/>
	 * Sebaliknya kalau misal nya tidak ada footer, return null saja
	 * 
	 * <br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract Widget generateFooterPanel();
	
	
	
	/**
	 * i18n key untuk title erase<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract
		String getEraseIconTitleI18nKey () ; 
	
	
	/**
	 * i18n key untuk title delete<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract
		String getEditIconTitleI18nKey () ; 
	
	
	/**
	 * default label utnuk edit data<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract
		String getDefaultEditIconTitle () ; 
	
	
	/**
	 * i18n Key untuk view detail<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract
		String getViewDetailIconTitleI18nKey () ;
	
	/**
	 * label default utnuk view detail data<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract
		String getDefaultViewDetailIconTitle () ;
	/**
	 * key internalization untuk approve data<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract
		String getApproveDataIconTitleI18nKey () ;
	
	/**
	 * label default utnuk approve data<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract
		String getDefaultApproveDataIconTitle () ;
	
	
	/**
	 * message kalau membaca da ta yang bisa di edit gagal di lakukan. menjadi tanggung jawab masing-masing. termasuk isu i18n<br/>
	 * Ini dipergunakan dalam grid 
	 **/
	protected abstract String generateFailGetEditableDataListMessage (Throwable caught) ; 
	
	/**
	 * message kalau gagal submit request delete data<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract String generateFailSubmitRequestDeleteDataMessage (Throwable caught) ;
	
	
	/**
	 * message kalau delete di submit<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract String getDefaultDeleteRequestSubmitedDoneMessage (); 
	

	/**
	 * i18n code utnuk row number<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract  String  getRowNumberColumnHeaderLabelI18nKey () ; 
	
	/**
	 * i18n key utnuk action column label<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract String  getActionColumnHeaderLabelI18nKey () ; 
	

	/**
	 * message untuk konfirmasi kalau data mau di delete. render data anda di sini. kalau di perlukan internalization, silakan di masukan<br/>
	 * Ini dipergunakan dalam grid
	 * @param dataToErase data yang hendak di hapus. kalau ada property yang di perlukan silakan di get dari data nya
	 **/
	protected abstract String generateConfirmDeleteDataMessage (DATA dataToErase) ; 
	
	
	
	/**
	 * column defs<br/>
	 * Ini dipergunakan dalam grid
	 **/
	protected abstract BaseColumnDefinition<DATA, ?>[] getColumnDefinitions(); 
	
	
	
	/**
	 * ini membuat data untuk add baru. Proses nya spt ini : 
	 * <ol>
	 * <li>data di new</li>
	 * <li>data di kirimkan ke editor dalam proses add new</li>
	 * </ol>
	 * anda perlu mengisikan beberapa field yang mandatory, jadinya editor sudah dalam prosisi siap
	 **/
	protected abstract void instantiateDataForAddNewTemplate (AsyncCallback<DATA> callback) ; 
	
	
	/**
	 * default label untuk icon add, ini ada di sisi bawah dari grid
	 **/
	protected abstract String getAddDataIconDefaultCaption () ; 
	
	
	
	/**
	 * key internalization untuk 
	 **/
	protected abstract String getAddDataIconCaptionI18nCode () ;
	
	
	

}
