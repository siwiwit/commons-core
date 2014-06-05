package id.co.gpsc.common.client.dualcontrol;

import java.io.Serializable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.common.ITitleAndSearchPanelFilter;
import id.co.gpsc.common.client.control.EditorOperation;
import id.co.gpsc.common.client.control.ExpensivePanelGenerator;
import id.co.gpsc.common.client.control.IPanelGenerator;
import id.co.gpsc.common.client.control.MainPanelStackControl;
import id.co.gpsc.common.client.control.SimpleSearchFilterHandler;
import id.co.gpsc.common.client.control.ViewScreenMode;
import id.co.gpsc.common.client.widget.BaseSigmaComposite;
import id.co.gpsc.common.client.widget.EditorState;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.common.util.I18Utilities;

/**
 *
 *
 * layout dari dual control edito kurang lebih tipikal nya spt ini
 * 
 * 
 * <table border="1" style="border: solid 1px green">
 * 
 * <tr>
 * <td>
 * ---- > section 1<br/>
 * 
 * <h3>Judul Data di sini</h3>
 * <table>
 * <tr>
 * 	<td>Kode</td><td><input type="text"/></td>
 * </tr>
 * <tr>
 * 	<td>Kerangan</td><td><input type="text"/></td>
 * </tr>
 * </table> 
 * 
 * </td>
 * </tr>
 * <tr>
 * <td>
 *  ---- > section 1<br/>
 * Disini tempat grid <br/>
 * <div style="padding :0px 20px 0px 20px">
 * <table border="1">
 * <tr bgcolor="green" color="white" align="center">
 * <td>Task</td>
 * <td>Nama</td>
 * <td>Alamat</td>
 * <td>Keterangan</td>
 * </tr>
 *<tr>
 * <td>x e d</td>
 * <td>Gede Sutarsa</td>
 * <td>Jadi Tampih</td>
 * <td>SA</td>
 * </tr>
 * <tr>
 * <td>x e d</td>
 * <td>Agus Gede Adipartha Wibawa</td>
 * <td>Penebel</td>
 * <td>Developer</td>
 * </tr> 
 * <tr>
 * <td>x e d</td>
 * <td>Wayan Ari Agustina</td>
 * <td>Luwus</td>
 * <td>Developer</td>
 * </tr>
 * </table>
 * </div>
 * 
 * 
 * </td>
 * </tr> 
 * 
 * </table>
 * 
 * <h1>Section 1 </h1> <br/>
 * di sini anda perlu menaruh : 
 * <ol>
 * <li>search panel</li>
 *  <li>title dari panel</li>
 *   
 *</ol> 
 *
 *<br/>
 *<h1>Section 2 </h1> <br/> 
 *Grid untuk menampilkan data + search criteria
 * @author <a href='mailto:gede.sutarsa@gmail.com'><i>Gede Sutarsa</i> - gede[dot]sutarsa[at]gmail[dot]com </a>
 */
public abstract class BaseDualControlMainPanel<PK extends Serializable, DATA extends DualControlEnabledData<DATA, PK>> extends BaseSigmaComposite {
	
	
	
	/**
	 * panel paling luar dari container
	 **/
	private FlowPanel outmostPanel ; 
	
	
	
	private VerticalPanel gridContainerPanel ; 
	
	
	/**
	 * grid panel 
	 **/
	protected final BaseDualControlDataGridPanel<DATA> dataGrid ; 
	
	
	private ITitleAndSearchPanelFilter headerPanel ; 
	
	

	
	
	
	
	/**
	 * panel ini di taruh di bawah grid. kalau di perlukan anda taruh di sini
	 **/
	protected final Widget  gridFooterPanel  = instantiateFooterPanel(); 
	
	
	
	
	
	
	/**
	 * ini generator panel . di inject dari system lain nya. ini di pakai untuk membuat panel editor. masing-masing di buat sendiri untuk menghindari masalah
	 */
	protected IPanelGenerator<BaseDualControlDataEditor<PK, DATA>> editorGenerator ; 
	
	
	
	
	
	/**
	 * konstruktor. ini di desain 1 object per menu. bukan nya di share per menu. jadinya anda wajib mem-pass editor state untuk data ini apa. 
	 **/
	public BaseDualControlMainPanel(DualControlEditorState dualControlEditorState) {
		outmostPanel = new FlowPanel(); 
		gridContainerPanel = new VerticalPanel(); 
		initWidget(outmostPanel);
		
		outmostPanel.add(gridContainerPanel);
		gridContainerPanel.setSpacing(5);
		
		
		// header + grid + footer
		headerPanel = this.instantiateHeaderAndSearchFilter(); 
		dataGrid   = instantiateGrid(
				new DataProcessWorker<DATA>() {
					public void runProccess(DATA data) {
						handleEditDataForApproval(data);
						
					};
				} , 
				new DataProcessWorker<DATA>() {
					public void runProccess(DATA data) {
						handleApproveData(data);
					};
				} ,
				new DataProcessWorker<DATA>() {
					public void runProccess(DATA data) {
						GWT.log("Triggering view data");
						handleViewData(data);
					};
				} 
				
			);
		
		String swap = getGridCaption() ; 
		if ( swap!= null && !swap.isEmpty()){
			dataGrid.setCaption(swap);
		}
		
		
		
		if ( getGridHeight()!= null ){
			dataGrid.setHeight(getGridHeight());
			
		}
		
		if ( headerPanel!= null){
			gridContainerPanel.add((Widget)headerPanel);
			headerPanel.setSearchFilterHandler(new SimpleSearchFilterHandler() {
				
				@Override
				public void onResetRequested() {
					dataGrid.onResetRequested();
				}
				
				@Override
				public void applyFilter(SigmaSimpleQueryFilter[] filters) {
					dataGrid.applyFilter(filters);
					
				}
				
				@Override
				public void applyFilter(SigmaSimpleQueryFilter[] filters,
						SigmaSimpleSortArgument[] sorts) {
					dataGrid.applyFilter(filters , sorts);
				}
			});
		}
		
		gridContainerPanel.add(dataGrid);
		if ( gridFooterPanel!= null)
			gridContainerPanel.add(gridFooterPanel);
		// bind handler grid
		 dataGrid.setDualControlEditorState(dualControlEditorState);
		 
		if  ( DualControlEditorState.CREATE_FOR_APPROVAL.equals(dualControlEditorState)){
			renderAddNewDataButton(); 
			
		}
		
	}
	
	
	
	/**
	 * caption yang akan di set untuk grid.<br/>
	 * Ini di set null, dalam artian tidak akan ada caption yang di set untuk grid
	 */
	protected String getGridCaption () {
		return null ; 
	}
	
	
	/**
	 * tinggi dari grid. di set null, dalam artian grid tidak akan di set sama sekali.
	 */
	protected Integer getGridHeight () {
		return null ; 
	}
	
	/**
	 * ini untuk add button, yang ada pada sisi bawah. override ini kalau tombol perlu perubahan. misal ndak ada tombol
	 **/
	protected void renderAddNewDataButton () {
		new Timer() {
			
			@Override
			public void run() {
				dataGrid.appendButton(I18Utilities.getInstance().getInternalitionalizeText(getAddDataIconCaptionI18nCode(), getAddDataIconDefaultCaption()), "ui-icon-document", new Command() {
					@Override
					public void execute() {
						shoAddNewDataEditor();
					}
				});
				
			}
		}.schedule(100);
	}
	
	
	
	/**
	 * ini untuk menampilkan add new data. kalau di perlukan step tambahan dalam proses create data, pergunakan method ini
	 **/
	protected void shoAddNewDataEditor () {
		
		
		instantiateDataForAddNewTemplate(new AsyncCallback< DATA>() {
			@Override
			public void onFailure(Throwable caught) {
				// ini tidak mungkin gagal
				
			}
			@Override
			public void onSuccess(final DATA result) {
				editorGenerator.instantiatePanel(new ExpensivePanelGenerator<BaseDualControlDataEditor<PK,DATA>>() {
					@Override
					public void onPanelGenerationComplete(
							BaseDualControlDataEditor<PK, DATA> widget) {
						configureAndPlaceEditorPanel(widget);
						widget.addAndEditNewData(result);
					}
				});
			}
		}); 
		
		
	}
	
	
	
	/**
	 * ini membuat data untuk add baru. Proses nya spt ini : 
	 * <ol>
	 * <li>data di new</li>
	 * <li>data di kirimkan ke editor dalam proses add new</li>
	 * </ol>
	 * anda perlu mengisikan beberapa field yang mandatory, jadinya editor sudah dalam prosisi siap. ini di desain async, agar anda bisa melakukan usecase berikut ini : 
	 * <ol>
	 * <li>membuat data, user perlu memilih dulu parent dari data<li>
	 * <li>user mendefin data lain yang menjadi reference dari object baru</li>
	 * 
	 * </ol>
	 **/
	protected abstract void instantiateDataForAddNewTemplate (AsyncCallback<DATA> callbackOnSampleCreated) ; 
	
	
	/**
	 * ini di trigger kalau data akan di edit. jadinya dari requester edit data untuk di ajukan agar di approve oleh tim lain nya
	 **/
	protected void handleEditDataForApproval (final DATA data){
		editorGenerator.instantiatePanel(new ExpensivePanelGenerator<BaseDualControlDataEditor<PK,DATA>>() {
			@Override
			public void onPanelGenerationComplete(
					BaseDualControlDataEditor<PK, DATA> widget) {
				configureAndPlaceEditorPanel(widget);
				widget.editExistingData(data);
				
				
			}
		});
		
		
		
	}
	
	/**
	 * handler kalau anda mengedit data. 
	 **/
	protected void handleApproveData (final DATA data){
		editorGenerator.instantiatePanel(new ExpensivePanelGenerator<BaseDualControlDataEditor<PK,DATA>>() {
			@Override
			public void onPanelGenerationComplete(
					BaseDualControlDataEditor<PK, DATA> widget) {
				configureAndPlaceEditorPanel(widget);
				widget.renderDataToEditorControl(data, EditorState.edit);
				
			}
		});
		
	}
	
	
	
	
	
	
	
	protected void handleViewData (final DATA data){
		if ( editorGenerator== null){
			GWT.log("editor generator null untuk object:" + this.getClass());
		}
		editorGenerator.instantiatePanel(new ExpensivePanelGenerator<BaseDualControlDataEditor<PK,DATA>>() {
			@Override
			public void onPanelGenerationComplete(
					BaseDualControlDataEditor<PK, DATA> widget) {
				configureAndPlaceEditorPanel(widget);
				widget.viewDataAsReadOnly(data);
			}
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * set editor panel. ini kalau ada editor , editor dari main panel di replace dengan existing
	 **/
	private void configureAndPlaceEditorPanel(BaseDualControlDataEditor<PK, DATA> editorPanel) {
		MainPanelStackControl.getInstance().putPanel(editorPanel, ViewScreenMode.normal);
		editorPanel.addDataChangeHandlers(new DataChangedEventHandler<DATA>() {
			@Override
			public void handleDataChange(DATA data, EditorOperation operation) {
				dataGrid.reload();
			}
		});
	}
	
	/**
	 * di sini anda perlu membuat grid yang anda pergunakan untuk edit data. di sarankan untuk mempergunakan <i>GWT.create</i> di bandingkan mempergunakan <i>new</i>
	 **/
	protected abstract BaseDualControlDataGridPanel<DATA> instantiateGrid(DataProcessWorker<DATA> editForApprovalRequestHandler  , DataProcessWorker<DATA> editForApprovingHandler  , DataProcessWorker<DATA> viewDetailHandler) ; 
	
	
	/**
	 * panel editor untuk dual control data. di sini juga berlaku sama.  di sarankan untuk mempergunakan <i>GWT.create</i> di bandingkan mempergunakan <i>new</i>
	 **/
	protected abstract BaseDualControlDataEditor<PK, DATA> instantiateEditorPanel () ; 
	
	
	
	/**
	 * ini membuat panel title , dan kalau di perlukan kita perlu membuat 
	 **/
	protected abstract ITitleAndSearchPanelFilter instantiateHeaderAndSearchFilter ( ) ; 
	
	
	
	
	/**
	 * kalau misalnya di perlukan, anda perlu membuat footer panel. ini akan di taruh tepat di bawah grid. kalau di perukan anda perlu mendefine grid anda di sini
	 **/
	protected abstract Widget instantiateFooterPanel () ; 
	
	
	
	
	
	
	/**
	 * default label untuk icon add, ini ada di sisi bawah dari grid
	 **/
	protected abstract String getAddDataIconDefaultCaption () ; 
	
	
	
	/**
	 * key internalization untuk 
	 **/
	protected abstract String getAddDataIconCaptionI18nCode () ;
	
	
	
	
	
	/**
	 * reference ke data grid
	 */
	public BaseDualControlDataGridPanel<DATA> getDataGrid() {
		return dataGrid;
	}
	
	
	/**
	 * ini generator panel . di inject dari system lain nya. ini di pakai untuk membuat panel editor. masing-masing di buat sendiri untuk menghindari masalah
	 */
	public void setEditorGenerator(
			IPanelGenerator<BaseDualControlDataEditor<PK, DATA>> editorGenerator) {
		this.editorGenerator = editorGenerator;
	}
	
}
