package id.co.gpsc.common.client.lov;

import id.co.gpsc.common.data.lov.CommonLOVHeader;



/**
 * handler kalau LOV content berubah
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id 
 **/
public interface LOVChangeHandler {
	

	/**
	 * handler kalau katalog LOV berubah
	 **/
	public void onLOVChange(CommonLOVHeader lovData ); 

}
