package id.co.gpsc.common.client.widget.dialog;

import id.co.gpsc.common.client.control.i18.I18NTextBrowseButton;
import id.co.gpsc.common.client.form.IntegerTextBox;
import id.co.gpsc.common.data.entity.FormElementConfiguration;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Dialog konfigurasi untuk string control
 * @author I Gede Mahendra
 * @since Jan 22, 2013, 11:28:40 AM
 * @version
 */
public class DialogConfigurationString extends BaseDialogEditorConfig{

	private IntegerTextBox txtMaxLength;
	private Label lblMaxLength;
	private Label lblI18NPlaceHolder;	
	
	private I18NTextBrowseButton i18PlaceHolderLookup ;  
	
	
	
	/**
	 * Constructor
	 * @param formId
	 * @param elementId
	 */
	public DialogConfigurationString(String formId, String elementId, String groupId) {
		super(formId, elementId, groupId);		
	}
	
	@Override
	protected void renderDataIntoGeneralControl(
			FormElementConfiguration configurationData) {
		
		super.renderDataIntoGeneralControl(configurationData);
		txtMaxLength.setValue(configurationData.getMaxLength());
		i18PlaceHolderLookup.setValue(configurationData.getPlaceHolderI18NKey());
	}

	@Override
	protected void generateAdditionalControl(FlexTable flexTable) {
		txtMaxLength = new IntegerTextBox();
		lblI18NPlaceHolder = new Label("Placeholder I18N Key");
		lblMaxLength = new Label("Max Length");
		i18PlaceHolderLookup = new I18NTextBrowseButton(); 
		
		txtMaxLength.setValue(null);
		
		flexTable.setWidget(2, 0, lblMaxLength);
		flexTable.setWidget(2, 1, txtMaxLength);
		flexTable.setWidget(3, 0, lblI18NPlaceHolder);
		flexTable.setWidget(3, 1, i18PlaceHolderLookup);	
		
		cleanControl();
	}

	@Override
	protected void readDataFromControl(
			FormElementConfiguration configurationData) {
		super.readDataFromControl(configurationData);
		configurationData.setMaxLength(txtMaxLength.getValue());
		configurationData.setPlaceHolderI18NKey(i18PlaceHolderLookup.getValueString());
	}
	

	@Override
	protected boolean validateForm() {	
		if(validateControlAtBaseClass()){
			/*if(txtMaxLength.getValue() == 0){				
				Window.alert("Max length can't be null. Please fill this field!!!");
				txtMaxLength.setValue(null);
				txtMaxLength.setFocus(true);
				return false;
			}
			*/
			/*else if(txtI18NPlaceHolder.getValue().trim().length() == 0) {
				Window.alert("I18N Key for place holder can't be null. Please fill this field!!!");
				txtI18NPlaceHolder.setFocus(true);
				return false;
			}*/
			return true;
		}else {
			return false;
		}		
	}
}