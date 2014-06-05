package id.co.gpsc.common.client.control.lookup;


import com.google.gwt.user.client.Window;




import id.co.gpsc.common.client.control.worklist.PagedSimpleGridPanel;
import id.co.gpsc.common.control.SingleValueLookupResultHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;
import id.co.gpsc.jquery.client.grid.event.GridRowDoubleClickHandler;
import id.co.gpsc.jquery.client.grid.event.GridSelectRowHandler;



/**
 * base class untuk simple lookup. Model :<br/>
 * Lookup dengan dialog, menampilkan data dalam bentuk grid. Popup menampilkan data dalam bentuk grid, pilihan direturn dalam bentuk array of data
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public abstract class BaseSimpleSingleResultLookupDialog<KEY,ENTITY> extends BaseSimpleLookupDialog<KEY, ENTITY>{
	
	
	
	
	
	
	
	
	/**
	 * data yang lg di pilih
	 **/
	protected ENTITY currentSelectedData ; 
	/**
	 * worker untuk menerima 
	 **/
	protected SingleValueLookupResultHandler<ENTITY> lookupResultHandler ; 
	
	
	
	
	
	public BaseSimpleSingleResultLookupDialog(){
		
		
		super();
		
		
		
		
		
		selectorGrid.addRowSelectedHandler(new GridSelectRowHandler<ENTITY>() {
			@Override
			public void onSelectRow(String rowId, ENTITY selectedData) {
				currentSelectedData = selectedData ; 
				dialog.enableButton(0);
			}
		}); 
		selectorGrid.addDoubleclickHandler(new GridRowDoubleClickHandler<ENTITY>() {
			@Override
			public void onCellSelect(String rowId, ENTITY data) {
				currentSelectedData = data ; 
				makeSelectionAndReturnToCaller();
			}
		});
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Grid untuk memilih item. mohon di perhatikan pada {@link #instantiateAndConfigureGrid()} untuk proses konstruksi gri
	 **/
	public PagedSimpleGridPanel<ENTITY> getSelectorGrid() {
		return selectorGrid;
	}
	
	
	
	
	
	
	
	
	/**
	 * kirim kembali ke caller dan tutup dialog
	 **/
	protected void makeSelectionAndReturnToCaller (){
		if ( lookupResultHandler!=null){
			lookupResultHandler.onSelectionDone(currentSelectedData);
		}
		dialog.close();
	}
	
	
	
	
	
	
	/*
	protected void renderDataWorker (PagedResultHolder<ENTITY>  data){
		this.currentSelectedData = null ; 
		this.dialog.disableButton(0);
		
	}*/
	
	
	
	@Override
	protected boolean isMultipleSelction() {
		return false;
	}
	
	
	
	/**
	 * hadnler pada saat ada item yang di pilih
	 **/
	public void assignLookupResultHandler(
			SingleValueLookupResultHandler<ENTITY> lookupResultHandler) {
		this.lookupResultHandler = lookupResultHandler;
	}
	
	@Override
	protected void selectionDoneClickHandler(JQDialog dialogReference) {
		if ( currentSelectedData==null){
			String msg = I18Utilities.getInstance().getInternalitionalizeText(getNoneSelectedI18Key());
			Window.alert(msg);
			return ; 
		}
		makeSelectionAndReturnToCaller();
		
	}
	
	
	@Override
	protected void renderDataToGrid(PagedResultHolder<ENTITY> dataResult) {
		selectorGrid.setData(dataResult);
		
	}
	
}
