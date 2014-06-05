package id.co.gpsc.common.client.control;

import com.google.gwt.user.client.ui.Widget;



/**
 * worker render panel
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public interface IPanelRenderWorker {

	/**
	 * ini perintah menu remove panel. 
	 * @param panel panel yang akan di remove
	 * @param viewScreenMode mode , apakah dia view normal atau view full screen
	 **/
	public void removePanel(Widget panel , ViewScreenMode viewScreenMode);
	
	
	/**
	 * menaruh panel dalam container. kalau full screen dalam full screen container, normal screen dalam normal screen container
	 **/
	public void putPanel(Widget panel , ViewScreenMode viewScreenMode) ; 
	/**
	 * aktivasi mode normal screen
	 * 
	 **/
	public void activateNormalScreenMode(); 
	
	
	/**
	 * aktivasi mode mode fullscreen.
	 **/
	public void activateFullScreenMode() ; 
	
}
