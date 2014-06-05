package id.co.gpsc.common.client.control;

import com.google.gwt.event.dom.client.HasChangeHandlers;

/**
 * 
 * marker class parent Lookup.ada value + event change handler
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public interface IParentLOVEnableControl extends HasChangeHandlers {
	
	
	
	/**
	 * membaca nilai dari control
	 **/
	public String getValue(); 
	
	/**
	 * mengeset nilai ke dalam control
	 **/
	public void setValue(String valueToSet) ; 

}
