package id.co.gpsc.common.client.form.advance;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

/**
 * Integer Textbox with Label and Unit<br>
 * [Default Label] [:] [Unit] [TextBox Widget]
 * @author I Gede Mahendra
 * @since Oct 19, 2012, 10:24:22 AM
 * @version $Id
 */
public class IntegerTextBoxWithUnit extends IntegerTextBoxWithLabel{
	
	private boolean isLeft = true;		
	private String defaultUnit = "Unit";
	private String i18KeyUnit;
	private Element labelUnit;
	
	public IntegerTextBoxWithUnit() {
		super();
	}
	
	@Override
	protected void onAttach() {			
		super.onAttach();					
		if(getElement().getId() == null || getElement().getId().length() == 0){
			getElement().setId(DOM.createUniqueId());
		}						
		
		this.labelUnit = AdvanceControlUtil.getInstance().createdUnit(getElement(), i18KeyUnit, defaultUnit, isLeft, labelUnit);
	}
	
	@Override
	protected void onDetach() {	
		super.onDetach();
		labelUnit.removeFromParent();
	}
	
	/**
	 * Flag untuk menampilkan posisi Unit.
	 * @return True->Posisi di kiri, False->Posisi di kanan
	 */
	public boolean isLeft() {
		return isLeft;
	}

	/**
	 * Flag untuk menampilkan posisi Unit. True->Posisi di kiri, False->Posisi di kanan
	 * @param isLeft - True->Posisi di kiri, False->Posisi di kanan
	 */
	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	/**
	 * Get I18Key for unit label
	 * @return
	 */
	public String getI18KeyUnit() {
		return i18KeyUnit;
	}

	/**
	 * Set I18Key for unit label
	 * @param i18KeyUnit
	 */
	public void setI18KeyUnit(String i18KeyUnit) {
		this.i18KeyUnit = i18KeyUnit;
	}

	/**
	 * Get default unit label
	 * @return
	 */
	public String getDefaultUnit() {
		return defaultUnit;
	}

	/**
	 * Set default unit label
	 * @param defaultUnit
	 */
	public void setDefaultUnit(String defaultUnit) {
		this.defaultUnit = defaultUnit;
	}
}
