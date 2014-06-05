/**
 * 
 */
package id.co.gpsc.jquery.client.grid;

/**
 * class untuk set label hypelink dari DATA
 * @author <a href="mailto:gede.wibawa@sigma.co.id">Agus Gede Adipartha Wibawa</a>
 * @since Oct 4, 2013 11:37:18 AM
 */
public abstract class CustomHyperLinkLabel<DATA> {
	
	/**
	 * set custom hyperlink label
	 * @param data
	 * @return
	 */
	public abstract String getHyperlinkLabel(DATA data       );
	
	 
}
