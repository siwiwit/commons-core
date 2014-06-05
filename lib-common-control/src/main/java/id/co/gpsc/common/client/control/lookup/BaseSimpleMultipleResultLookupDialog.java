package id.co.gpsc.common.client.control.lookup;

import java.util.List;

import id.co.gpsc.common.control.MultipleValueLookupResultHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.jquery.client.container.JQDialog;




/**
 * base class untuk dialog dengan tipe multiple result. jadinya pemilihan adalah dengan checkbox. beberapa item di pilih,
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id 
 **/
public abstract class BaseSimpleMultipleResultLookupDialog<KEY,ENTITY> extends BaseSimpleLookupDialog<KEY, ENTITY> {

	
	
	/**
	 * hamdler kalau selection done. jadinya harus kembali kepada caller
	 **/
	private MultipleValueLookupResultHandler<ENTITY> lookupResultHandler ; 
	
	
	
	@Override
	protected boolean isMultipleSelction() {
		return true;
	}
	
	
	@Override
	protected void selectionDoneClickHandler(JQDialog dialogReference) {
		if ( lookupResultHandler==null){
			dialogReference.close(); 
			return ; 
		} 
		List<ENTITY> datas =  this.selectorGrid.getCurrentSelectedData();
		lookupResultHandler.onSelectionDone(datas); 
		dialogReference.close();
		
		
	}
	/*@Override
	protected void selectionDoneClickHandler() {
		
		if ( lookupResultHandler==null){
			
			
			return ; 
		} 
		//lookupResultHandler.onSelectionDone(data)
	}*/
	
	/**
	 * hamdler kalau selection done. jadinya harus kembali kepada caller
	 **/
	public void assignLookupResultHandler(
			MultipleValueLookupResultHandler<ENTITY> lookupResultHandler) {
		this.lookupResultHandler = lookupResultHandler;
	}
	
	@Override
	protected void renderDataToGrid(PagedResultHolder<ENTITY> dataResult) {
		//this.selectorGrid.clearData();
		this.selectorGrid.setData(dataResult); 
		
	}
}
