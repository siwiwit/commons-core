package id.co.gpsc.common.client.control;

import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;

/**
 *
 *interface yang meresponse pada entry filter. tipikal nya ini dipergunakan dalam grid. jadinya control meresponse terhadap isin dari grid. untuk membuat item ini menjadi loose couple, maka panel filter cukup merefer class ini
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 */
public interface SimpleSearchFilterHandler {
	
	
	
	/**
	 * kirim request filter ke grid atau control lain nya
	 * @param filters filters untuk data
	 * @param sorts argument untuk proses sorting
	 **/
	public void applyFilter (SigmaSimpleQueryFilter[] filters , SigmaSimpleSortArgument[] sorts) ; 
	
	
	/**
	 * kirim request filter ke grid atau control lain nya
	 * @param filters filters untuk data
	 * @param sorts argument untuk proses sorting
	 **/
	public void applyFilter (SigmaSimpleQueryFilter[] filters  ) ; 
	
	/**
	 * ini kalau di request reset
	 */
	public void onResetRequested () ; 

}
