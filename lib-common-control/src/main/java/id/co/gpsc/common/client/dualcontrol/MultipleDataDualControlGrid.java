package id.co.gpsc.common.client.dualcontrol;

import java.math.BigInteger;

import com.google.gwt.user.client.Window;

import id.co.gpsc.common.client.control.worklist.PagedSimpleGridPanel;
import id.co.gpsc.common.data.app.SimpleDualControlData;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;

/**
 * grid multiple control untuk grid approval. ini untuk versi data yang di upload dengan spreadsheet
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */

public class MultipleDataDualControlGrid<DATA extends SimpleDualControlData<?>> extends PagedSimpleGridPanel<DATA>{
	
	
	
	private BaseColumnDefinition<DATA, ?>[] columnDefinitions ; 
	
	
	
	/**
	 * target object FQCN.class data yang di render apa
	 **/
	private String targetObjectFQCN ; 
	
	public MultipleDataDualControlGrid(){
		super(); 
		this.renderJqueryOnAttach = false;
		
	}
	
	
	
	

	/**
	 * menaruh column definition ke dalam grid
	 **/
	public void configureGrid( String targetObjectFQCN , 
			BaseColumnDefinition<DATA, ?>[] columnDefinitions) {
		this.targetObjectFQCN = targetObjectFQCN; 
		this.columnDefinitions = columnDefinitions;
		if ( isAttached()){
			renderJQWidgetOnAttachWorker(); 
			
		}else{
			renderJqueryOnAttach = true ; 
		}
		
	}
	@Override
	protected BaseColumnDefinition<DATA, ?>[] getColumnDefinitions() {
		return columnDefinitions;
	}
	
	
	
	
	/**
	 * set data untuk di approve. ini otomatis memerintahkan editor untuk request data grid
	 **/
	public void setIdOfDataToApprove ( BigInteger dataId ){
		
		if ( this.targetObjectFQCN == null|| targetObjectFQCN.length()==0){
			Window.alert("anda belum mengeset variable :   targetObjectFQCN  , untuk class : " + this.getClass().getName() +", mohon di perbaiki");
			return ; 
		}
		//FIXME: kirim request ke RPC di sini
	}
	
	

	 	


}
