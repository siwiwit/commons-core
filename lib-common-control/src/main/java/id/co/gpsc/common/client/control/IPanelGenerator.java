package id.co.gpsc.common.client.control;

import com.google.gwt.user.client.ui.Widget;

/**
 * interface generator panel
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IPanelGenerator<W extends Widget> {
	
	
	
	/**
	 * trigger untuk mengenerate panel
	 */
	public void instantiatePanel ( ExpensivePanelGenerator<W> callback); 

}
