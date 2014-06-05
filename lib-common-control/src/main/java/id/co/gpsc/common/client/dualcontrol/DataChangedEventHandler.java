package id.co.gpsc.common.client.dualcontrol;

import id.co.gpsc.common.client.control.EditorOperation;

import com.google.gwt.event.shared.EventHandler;

/**
 * handler data change
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface DataChangedEventHandler<DATA> extends EventHandler{
	
	
	/**
	 * ini wajib meresponse kalau ada perubahan 
	 **/
	public void handleDataChange( DATA data , EditorOperation operation ); 

}
