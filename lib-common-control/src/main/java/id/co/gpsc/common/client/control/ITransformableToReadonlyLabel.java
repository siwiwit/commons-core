package id.co.gpsc.common.client.control;

/**
 * ini interface control yang bisa di transpose menjadi span readonly. ini di pergunakan untuk mode readonly. jadinya textbox di hide  di ganti menjadi readonly span
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface ITransformableToReadonlyLabel {
	
	
	
	/**
	 * main control di bikin visible, readonly label di dispose
	 **/
	public void restoreControl () ; 
	
	
	
	/**
	 * transform control menjadi readonly span
	 **/
	public void switchToReadonlyText () ; 

}
