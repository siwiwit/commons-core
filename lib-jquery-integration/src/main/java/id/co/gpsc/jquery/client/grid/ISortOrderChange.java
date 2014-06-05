package id.co.gpsc.jquery.client.grid;

import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;



/**
 * interface kalau user merubah sort order dari data. di trigger klik pada header dari grid
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 28 Sept 2012
 **/
public interface ISortOrderChange<DATA> {
	
	/**
	 * di trigger pada saat sort index berubah
	 * @param sortIndexField nama field index untuk proses sort
	 * @param columnDefinition column def, yang di mentrigger ini. kalau di perlukan item-item tertentu dari sini
	 * @param ascendingSorting true =  data di minta untuk ascending dan<i>sebaliknya</i>
	 **/
	void onSortChange( String sortIndexField ,  boolean ascendingSorting, BaseColumnDefinition<DATA, ?> columnDefinition); 
}
