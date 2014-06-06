package id.co.gpsc.common.client.control.worklist;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.SimpleSearchFilterHandler;
import id.co.gpsc.common.client.rpc.GeneralPurposeRPCAsync;
import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;

/**
 * grid yang ke bind dengan simple select.Grid request select dengan simple select. cukup dengan array of SimpleQueryFilter. yang perlu anda lakukan hanya : 
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class SimpleRPCDrivenPagedSimpleGridPanel<DATA extends IJSONFriendlyObject<DATA>> extends PagedSimpleGridPanel<DATA> implements SimpleSearchFilterHandler{

	
	/**
	 * filters yang di pergunakan saat ini
	 **/
	protected  SimpleQueryFilter[] currentFilters ;
	
	
	
	
	/**
	 * argument sort current
	 **/
	protected SimpleSortArgument[] currentSortArguments  ;  
	
	
	
	/**
	 * reciever data change. ini kalau ada data di terima oleh grid, kalau ada yang berkepentingan, bisa menyesuaikan
	 */
	private ArrayList<AsyncCallback<PagedResultHolder<DATA>>> additionalDataRecievedCalbacks = new ArrayList<AsyncCallback<PagedResultHolder<DATA>>>() ;
	
	
	public SimpleRPCDrivenPagedSimpleGridPanel(){
		super() ; 
		plugPageChangeHandler(); 
	}
	
	
	private void plugPageChangeHandler(){
		this.setPageChangeHandler(new PageChangeHandler() {
			
			@Override
			public void onPageChange(int newPage) {
				submitRPCRequest( currentFilters, currentSortArguments , newPage, pageSize);
			}
		});
	}
	
	public SimpleRPCDrivenPagedSimpleGridPanel(boolean multipleSelection){
		this(multipleSelection , true); 
	}
	
	/**
	 * @param multipleSelection multiple selection
	 * @param renderOnAttach render on attach atau tidak
	 **/
	public SimpleRPCDrivenPagedSimpleGridPanel(boolean multipleSelection , boolean renderOnAttach){
		super( multipleSelection  , renderOnAttach); 
		plugPageChangeHandler(); 
	}
	
	
	
	
	
	
	/**
	 * kirim pesan kalau request data untuk grid gagal .pesan nya spt apa menjadi tanggung jawab implementator
	 **/
	protected abstract String generateMessageOnRequestDataGridFailure( Throwable caught); 
	
	
	@Override
	public void applyFilter(SimpleQueryFilter[] filters) {
			resetGrid();
		 this.currentFilters  = filters ; 
		 reload();
		
	}
	
	@Override
	public void applyFilter(SimpleQueryFilter[] filters,
			SimpleSortArgument[] sorts) {
		resetGrid();
		this.currentFilters = filters ;
		this.currentSortArguments = sorts ;
		reload();
	}
	
	
	
	
	
	/**
	 * class yang di render 
	 **/
	protected abstract Class<DATA> getRenderedClass () ; 
	
	
	

	/**
	 * reload data dari grid
	 **/
	public void reload() {
		submitRPCRequest( currentFilters, currentSortArguments , 0, pageSize);
		
	}
	
	
	
	
	
	private AsyncCallback<PagedResultHolder<DATA>> dataCallback = new AsyncCallback<PagedResultHolder<DATA>>() {
		
		@Override
		public void onSuccess(PagedResultHolder<DATA> result) {
			setData((PagedResultHolder<DATA>) result);
			showHideLoadingBlockerScreen(false);
			if (! additionalDataRecievedCalbacks.isEmpty()){
				for (  AsyncCallback<PagedResultHolder<DATA>> scn : additionalDataRecievedCalbacks){
					scn.onSuccess(result);
				}
			}
		}
	
		@Override
		public void onFailure(Throwable caught) {
			Window.alert(generateMessageOnRequestDataGridFailure(caught));
			showHideLoadingBlockerScreen(false);
			if (! additionalDataRecievedCalbacks.isEmpty()){
				for (  AsyncCallback<PagedResultHolder<DATA>> scn : additionalDataRecievedCalbacks){
					scn.onFailure(caught);
				}
			}
		}
};  
	
	
	/**
	 * callback untuk handle data
	 **/
	protected AsyncCallback<PagedResultHolder<DATA>> getDataCallback () {
		return dataCallback;
	}
	
	
	/**
	 * ini request ke RPC.Request ke   {@link GeneralPurposeRPCAsync#getPagedData(String, SimpleQueryFilter[], SimpleSortArgument[], int, int, AsyncCallback)}. kalau perlu custom, bisa override method ini
	 **/
	protected void submitRPCRequest(SimpleQueryFilter[] filters , SimpleSortArgument[] sortsArgument , int page, int pageSize  ) {
		actualSubmitCallbackWorker(filters, sortsArgument, page, pageSize, getDataCallback());
	}
	
	
	
	
	/**
	 * actual callback yang di trigger oleh grid. versi lengkap dengan argument callback di banding dengan {@link #submitRPCRequest(SimpleQueryFilter[], SimpleSortArgument[], int, int)}
	 * @param filters filter data
	 * @param sortsArgument sorting argument
	 * @param page page di baca
	 * @param pageSize ukuran page di baca
	 * @param callback callback data
	 */
	protected void actualSubmitCallbackWorker (SimpleQueryFilter[] filters , SimpleSortArgument[] sortsArgument , int page, int pageSize  , AsyncCallback<PagedResultHolder<DATA>> callback) {
		this.showHideLoadingBlockerScreen(true);
		GeneralPurposeRPCAsync.Util.getInstance().getPagedData(getRenderedClass().getName(), filters, sortsArgument, page, pageSize,callback);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		String debugerFlag = Window.Location.getParameter(CommonClientControlConstant.SHOW_DEBUGER_THING_KEY) ;
		if ( "true".equalsIgnoreCase(debugerFlag)){
			setTitle("rendered class  : " +  getRenderedClass().getName()  ); 
		}
	}
	
	
	@Override
	public void onResetRequested() {
		clearData();
		
	}
	
	
	/**
	 * register data recieved callback ke dalam grid
	 */
	public void registerDataRecievedCallback ( AsyncCallback<PagedResultHolder<DATA>> callback ) {
		if ( callback== null)
			return  ; 
		if (!additionalDataRecievedCalbacks.contains(callback)){
			additionalDataRecievedCalbacks.add(callback); 
		}
	}
	
	/**
	 * un register data recieved callback
	 */
	public void unregisterDataRecievedCallback ( AsyncCallback<PagedResultHolder<DATA>> callback ) {
		if ( callback== null)
			return  ; 
		additionalDataRecievedCalbacks.remove(callback); 
		 
	}
	
	
	
}
