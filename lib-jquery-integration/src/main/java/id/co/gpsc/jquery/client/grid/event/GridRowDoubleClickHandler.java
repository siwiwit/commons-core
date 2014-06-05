package id.co.gpsc.jquery.client.grid.event;



/**
 * handler grid double click
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 20-aug-2012
 * @param <POJO> data yang di render oleh grid
 **/
public interface GridRowDoubleClickHandler<POJO> {
	
	
	
	/**
	 * notifikasi grid row double click
	 **/
	public void onCellSelect(String rowId , POJO data);

}
