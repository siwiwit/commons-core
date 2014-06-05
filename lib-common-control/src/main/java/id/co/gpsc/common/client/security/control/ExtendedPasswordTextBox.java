/**
 * 
 */
package id.co.gpsc.common.client.security.control;

import com.google.gwt.user.client.ui.PasswordTextBox;

/**
 * @author Dode
 * @version $Id
 * @since Dec 19, 2012, 5:27:58 PM
 */
public class ExtendedPasswordTextBox extends PasswordTextBox {
	
	public ExtendedPasswordTextBox() {
		super();
	}
	
	public void setDomId(String elementId) {
		getElement().setAttribute("id", elementId);
	}
	
	public String getDomId() {
		return getElement().getId();
	}
}
