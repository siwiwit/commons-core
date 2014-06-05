package id.co.gpsc.common.client.style;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;



/**
 * Shared resource dalam app. misal image 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public class CommonResourceBundle {

	public interface Resources extends ClientBundle {
		
		/**
		 * icon untuk tombol search
		 **/
		public ImageResource searchIcon(); 
		
		/**
		 * icon change password. pada sisi atas
		 **/
		public ImageResource iconMenuChangePassword(); 
		
		/**
		 * icon help
		 **/
		public ImageResource iconMenuHelp();
		
		/**
		 * inco menu home
		 **/
		public ImageResource iconMenuHome(); 
		
		
		/**
		 * icon logout
		 **/
		public ImageResource iconMenuLogout();
		
		/**
		 * gambar loading icon
		 **/
		public ImageResource iconLoadingWheel() ; 
		
		
		/**
		 * icon minus
		 **/
		public ImageResource iconMinus();

		/**
		 * icon plus(expand)
		 **/
		public ImageResource iconPlus(); 
		
		/**
		 * icon folder
		 */
		public ImageResource iconFolder();
		
		
		public ImageResource iconMenuNode();
		
		
		
		/**
		 * icon checked dengan warna hijau.
		 */
		public ImageResource iconCheckedGreen () ; 
		
		
		
		
		
		
		/**
		 * icon configure
		 **/
		public ImageResource iconConfigure() ; 
		
		
		public ImageResource iconCancelDisabled(); 
		
		public ImageResource iconCancel (); 
		
		
		public ImageResource searchIconDisabled() ; 
		
		
		public ImageResource iconConfigureDisabled(); 

	}
	 
	private static Resources resources;
	static {
		   resources = GWT.create(Resources.class);
	}
	
	/**
	 * resource image yang di shared
	 **/ 
	public static Resources getResources() {
		return resources;
	} 
	
	
	

}
