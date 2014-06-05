package id.co.gpsc.jquery.client.grid.event;



/**
 * handler grid row selection(1 row saja di pilih)
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 20-aug-2012
 **/
public interface GridSelectRowHandler<POJO> {
	
	
	/**
	 * di trigger kalau ada row yang di pilih(click)
	 * @param rowId id row data yang di pilih
	 * @param selectedData data (POJO) yang sesuai dengan row yng di pilih 
	 **/
	public void onSelectRow(String rowId , POJO selectedData);

}
