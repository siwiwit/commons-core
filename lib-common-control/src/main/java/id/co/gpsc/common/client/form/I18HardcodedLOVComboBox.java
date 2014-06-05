package id.co.gpsc.common.client.form;

import id.co.gpsc.common.client.control.ITransformableToReadonlyLabel;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.client.util.I18HardcodedLOVParam;
import id.co.gpsc.common.util.I18Utilities;

/**
 * Komponent combobox yg nilai comboBox nya di hardcode dan i18 friendly 
 * @author I Gede Mahendra
 * @since Oct 30, 2012, 3:28:52 PM
 * @version $Id
 */
public class I18HardcodedLOVComboBox extends ExtendedComboBox implements ITransformableToReadonlyLabel{
	
	private I18HardcodedLOVParam[] i18HardcodeParam;
	private boolean isShowChoiceString = false;	
	private String i18KeyForChoiceString;
	private String defaultValueChoiceString = "- Please Choice -";
	private String[] testString;
	
	/**
	 * Default Constructor
	 */
	public I18HardcodedLOVComboBox() {
		super();
	}
	
	

	/**
	 * Populate data ke dalam comboBox
	 */
	private void populateDataIntoComboBox(){
		try {
			this.clear();
			/*Add item kosong/please choice*/
			if(isShowChoiceString){
				if(i18KeyForChoiceString == null){
					this.addItem(defaultValueChoiceString, "");
				}else{
					this.addItem(I18Utilities.getInstance().getInternalitionalizeText(i18KeyForChoiceString), "");
				}				
			}
			// tidak ada no label no value
			//else{
				//this.addItem("", "");
			//}			
			//this.setSelectedIndex(0);
			
			/*Add data combobox*/
			if(i18HardcodeParam != null){
				String dataItem = "";
				for (I18HardcodedLOVParam data : i18HardcodeParam) {
					String dataFromI18Key = I18Utilities.getInstance().getInternalitionalizeText(data.getI18KeyCode());
					if(dataFromI18Key == null){
						dataItem = data.getDefaultLabel();
					}else{
						dataItem = dataFromI18Key;
					}
					this.addItem(dataItem, data.getValueData());
				}
			}else{
				this.addItem("", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}//end try
	}
	
	/**
	 * Get array of I18HardCodeParam
	 * @return Array of Object
	 */
	public I18HardcodedLOVParam[] getI18HardcodeParam() {
		return i18HardcodeParam;
	}
	
	/**
	 * Set array of I18HardCodeParam
	 * @param i18HardcodeParam
	 */
	public void setI18HardcodeParam(I18HardcodedLOVParam[] i18HardcodeParam) {
		this.i18HardcodeParam = i18HardcodeParam;
		populateDataIntoComboBox();
	}

	/**
	 * Menampilkan string : - please choice -
	 * @return <b>true</b> : tampil, <b>false</b> : tidak tampil<br>
	 */
	public boolean isShowChoiceString() {
		return isShowChoiceString;
	}

	/**
	 * Menampilkan string : - please choice -
	 * @param isShowChoiceString - <b>true</b> : tampil, <b>false</b> : tidak tampil<br>
	 */
	public void setShowChoiceString(boolean isShowChoiceString) {
		this.isShowChoiceString = isShowChoiceString;
	}

	/**
	 * Get i18Key
	 * @return String
	 */
	public String getI18KeyForChoiceString() {
		return i18KeyForChoiceString;
	}

	/**
	 * Set i18Key untuk locale "- Please Choice -"
	 * @param i18KeyForChoiceString
	 */
	public void setI18KeyForChoiceString(String i18KeyForChoiceString) {
		this.i18KeyForChoiceString = i18KeyForChoiceString;
	}
	
	/**
	 * Get default value untuk choice string
	 * @return String
	 */
	public String getDefaultValueChoiceString() {
		return defaultValueChoiceString;
	}

	/**
	 * Default Value untuk choice string dlm comboBox
	 * @param defaultValueChoiceString
	 */
	public void setDefaultValueChoiceString(String defaultValueChoiceString) {
		this.defaultValueChoiceString = defaultValueChoiceString;
	}

	public String[] getTestString() {
		return testString;
	}

	public void setTestString(String[] testString) {
		this.testString = testString;
	}
	
	@Override
	public void restoreControl() {
		CommonClientControlUtil.getInstance().switchToNormalMode(this);
		
	}

	@Override
	public void switchToReadonlyText() {
		CommonClientControlUtil.getInstance().switchToReadOnlyLabel( this);
		
	}
	
	
	
	
}