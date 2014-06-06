package id.co.gpsc.common.server.dao.base;


import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.util.IDateFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public abstract class SimpleDataGridPagedResultExecutorTemplate<CORETABLECLASS> {
	
	
	/**
	 * field yang perlu di fetch
	 **/
	private  String [] actualFieldToFetch;
	
	protected static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(SigmaSimpleQueryFilter.DATE_TO_STRING_SERIALIZATION_PATTERN);
	
	
	/**
	 * formatter yang di wrap dalam interface {@link #DATE_FORMATTER}
	 **/
	protected static final IDateFormatter DATE_FORMATTER_AS_WRAPPED = new IDateFormatter() {
		@Override
		public String format(Date date) {
			return DATE_FORMATTER.format(date);
		}
		@Override
		public Date parse(String dateAsString) throws Exception { 
			if ( dateAsString== null|| dateAsString.isEmpty())
				return null ; 
			return DATE_FORMATTER.parse(dateAsString);
		}
	};
	/**
	 * index dari nama field dalam field yang di fetch
	 **/
	private int idFieldIndex ; 
	
	
	
	/**
	 * internal constructor, ini untuk 
	 **/
	protected SimpleDataGridPagedResultExecutorTemplate(){
		
	}
	
	/**
	 * @param actualFieldToFetch field yang perlu di baca dari dalam database
	 * @param idField nama field untuk id(ini include juga dalam actualFieldToFetch)
	 **/
	public SimpleDataGridPagedResultExecutorTemplate(String [] actualFieldToFetch , String idField  ){
		handleAssignFieldTask(actualFieldToFetch, idField);
		
	}
	/**
	 * index dari nama field dalam field yang di fetch
	 **/
	public int getIdFieldIndex() {
		return idFieldIndex;
	}
	/**
	 * field yang perlu di fetch
	 **/
	public String[] getActualFieldToFetch() {
		return actualFieldToFetch;
	}
	
	
	
	/**
	 * assign field, ini sekaligus juga untuk mengatur index dari field ID dalam field yang di fetch
	 **/
	protected void handleAssignFieldTask(String [] actualFieldToFetch , String idField ) {
		this.actualFieldToFetch = actualFieldToFetch ;
		
		for ( int i = 0 ; i< actualFieldToFetch.length;i++){
			String scn = actualFieldToFetch[i]; 
			if( scn.equals(idField)){
				this.idFieldIndex = i ;
				break;
			}
		}
	}
	
	/**
	 * method ini untuk run count query
	 **/
	protected abstract Integer count ()  throws Exception;
	
	/**
	 * method ini untuk run list data
	 * @param actualSelectedFields fields yang actual akhirnya di select
	 * @param pageSize ukuran page yang akan di baca
	 * @param firstRowPosition posisi pertama data yang akan di baca
	 **/
	protected abstract List<Object[]> list ( String actualSelectedFields[] ,  int pageSize , int firstRowPosition
			)  throws Exception; 
	
	
	/**
	 * class core . ini untuk memeriksa graph of data
	 **/
	protected abstract Class<? extends CORETABLECLASS> getCoreDataClass() ; 
	
	
	

}
