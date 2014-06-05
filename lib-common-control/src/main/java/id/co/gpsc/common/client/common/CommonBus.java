package id.co.gpsc.common.client.common;

import com.google.gwt.event.shared.SimpleEventBus;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public final class CommonBus extends SimpleEventBus{
	
	
	
	private static CommonBus instance ; 
	
	
	private CommonBus(){
		super();
	}
	
	
	
	/**
	 * singleton instance Bus
	 **/
	public static CommonBus getInstance() {
		if(instance== null)
			instance = new CommonBus(); 
		return instance;
	}
	

}
