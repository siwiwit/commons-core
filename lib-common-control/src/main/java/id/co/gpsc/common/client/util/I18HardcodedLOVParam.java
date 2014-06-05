package id.co.gpsc.common.client.util;

import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;



/**
 * Wrapper object yg menghandle parameter untuk comboBox yg hardcode i18 Key
 * @author I Gede Mahendra
 * @since Oct 30, 2012, 3:23:31 PM
 * @version $Id
 */
public class I18HardcodedLOVParam implements IJSONFriendlyObject<I18HardcodedLOVParam>{

	private static final long serialVersionUID = 6457520420723159458L;
	
	private String i18KeyCode;
	private String defaultLabel;
	private String valueData;	
	
	/**
	 * Constructor dg parameter
	 * @param i18KeyCode - i18Key yg ada didatabase
	 * @param defaultLabel - Nilai default jika i18Key kosong
	 * @param valueData - value dari data yg masuk dicomboBox
	 */
	public I18HardcodedLOVParam(String i18KeyCode, String defaultLabel,String valueData) {
		super();
		this.i18KeyCode = i18KeyCode;
		this.defaultLabel = defaultLabel;
		this.valueData = valueData;
	}

	/**
	 * Getter untuk i18Key yg ada di-db
	 * @return String
	 */
	public String getI18KeyCode() {
		return i18KeyCode;
	}
	
	/**
	 * Setter untuk i18Key yg ada di-db
	 * @param i18KeyCode
	 */
	public void setI18KeyCode(String i18KeyCode) {
		this.i18KeyCode = i18KeyCode;
	}
	
	/**
	 * Getter untuk default label yg muncul jika tidak ada i18Key<br>
	 * <b>NOTE : </b> field ini wajib diisi
	 * @return String
	 */
	public String getDefaultLabel() {
		return defaultLabel;
	}
	
	/**
	 * Setter untuk default label yg muncul jika tidak ada i18Key<br>
	 * <b>NOTE : </b> field ini wajib diisi
	 * @param defaultLabel
	 */
	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}
	
	/**
	 * Getter untuk value dari data yg masuk di-combobox
	 * @return String
	 */
	public String getValueData() {
		return valueData;
	}
	
	/**
	 * Setter untuk value dari data yg masuk di-combobox
	 * @param valueData
	 */
	public void setValueData(String valueData) {
		this.valueData = valueData;
	}

	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainerData) {
		
		
	}

	@Override
	public I18HardcodedLOVParam instantiateFromJSON(
			ParsedJSONContainer jsonContainer) {
		
		return null;
	}
}