/**
 * 
 */
package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.client.form.ExtendedLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * component text box untuk edit password
 * terdiri dari 2 component text box yaitu password dan retype password
 * @author Dode
 * @version $Id
 * @since Dec 19, 2012, 5:13:58 PM
 */
public class EditPasswordTextBox extends Composite {

	private static EditPasswordTextBoxUiBinder uiBinder = GWT
			.create(EditPasswordTextBoxUiBinder.class);
	@UiField ExtendedLabel lblPassword;
	@UiField ExtendedLabel lblRetypePassword;
	@UiField ExtendedPasswordTextBox txtPassword;
	@UiField ExtendedPasswordTextBox txtRetypePassword;
	
	//id untuk kolom pertama
	public final String ID_COLUMN_1 = "TD_1_COMPONENT_EDIT_PASSWORD";
	
	//element untuk kolom pertama
	private Element elementColumn1;

	interface EditPasswordTextBoxUiBinder extends
			UiBinder<Widget, EditPasswordTextBox> {
	}

	public EditPasswordTextBox() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private Element getElementColumn1() {
		if (elementColumn1 == null) {
			elementColumn1 = DOM.getElementById(ID_COLUMN_1);
		}
		return elementColumn1;
	}
	
	/**
	 * mendapatkan lebar dari kolom pertama tabel
	 * @return lebar kolom
	 */
	public int getColumnWidth() {
		int columnWidth = getElementColumn1().getOffsetWidth();
		return columnWidth;
	}
	
	/**
	 * validasi masukkan password dan retype password apakah sudah benar
	 * @return true kalo benar, false kalo salah
	 */
	public boolean validatePassword() {
		if (txtPassword.getValue() == null || txtPassword.getValue().length() == 0 || !txtPassword.getValue().equals(txtRetypePassword.getValue()))
			return false;
		return true;
	}
	
	/**
	 * mendapatkan value dari textbox password
	 * @return value text box password
	 */
	public String getPasswordValue() {
		return txtPassword.getValue();
	}
	
	/**
	 * mendapatkan value dari textbox retype password
	 * @return value dari textbox retype password
	 */
	public String getRetypePasswordValue() {
		return txtRetypePassword.getValue();
	}
	
	/**
	 * set value dari textbox password
	 * @param value
	 */
	public void setPasswordValue(String value) {
		txtPassword.setValue(value);
	}
	
	/**
	 * set value dari textbox retype password
	 * @param value
	 */
	public void setRetypePasswordValue(String value) {
		txtRetypePassword.setValue(value);
	}
	
	/**
	 * set value yang sama untuk kedua textbox
	 * @param value value untuk kedua textbox
	 */
	public void setBothValue(String value) {
		txtPassword.setValue(value);
		txtRetypePassword.setValue(value);
	}
}
