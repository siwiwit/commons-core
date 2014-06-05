package id.co.gpsc.common.client.lov;

import com.google.gwt.user.client.Command;


/**
 * panel yang yang menjadi tempat bagi LOV capable control
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 5-aug-2012
 **/
public interface LovPoolPanel {
	
	/**
	 * mendaftarkan object dalam LOV yang di kuasai oleh panel ini 
	 **/
	public void registerLOVCapableControl(LOVCapabledControl control);
	
	
	/**
	 * register bulk LOV
	 **/
	public void registerLOVs(LOVCapabledControl[] controls); 
	
	/**
	 * detach object dalam LOV yang di kuasai oleh panel ini 
	 **/
	public void unregister(LOVCapabledControl control); 
        
        
        
        
        /**
         * detach semua LOV controls dari manager. ini untuk menjamin kontrol aman dari memory leak
         */
        public void unregisterAllLOVControls() ; 
	
	
	/**
	 * perintahkan LOV manager untuk fill Lookup Value.<br/> 
	 * Semua Lookup yang ada dalam form di isi dengan Content dari server
	 **/
	public void fillLookupValue () ; 
	
	/**
	 * perintahkan LOV manager untuk fill Lookup Value.<br/> 
	 * Semua Lookup yang ada dalam form di isi dengan Content dari server
	 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
	 * @param afterLoadCompleteCommand command yang di trigger setelah load complete
	 **/
	public void fillLookupValue (Command afterLoadCompleteCommand) ; 
	
	
	
	
}
