package id.co.gpsc.common.client.control;

import com.google.gwt.user.client.ui.Widget;

/**
 * mekanisme standard kala panel yang di generte perlu effort gede.
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface ExpensivePanelGenerator<W extends Widget> {
	
	
	
	/**
	 * handler pada saat widget di generated
	 * @param widget widget yang di generated
	 */
	public void onPanelGenerationComplete (W widget) ; 

}
