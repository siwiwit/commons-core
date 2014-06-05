package id.co.gpsc.jquery.client.grid.event;

import java.util.ArrayList;


/**
 * handler select all. di trigger proses click checkbox pada grid
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 20-aug-2012
 **/
public interface GridSelectAllHandler<POJO> {
	
	public void onSelectAll(ArrayList<POJO> selectedItems);

}
