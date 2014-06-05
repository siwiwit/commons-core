package id.co.gpsc.common.client.widget.dialog;

import id.co.gpsc.common.client.util.JSONUtilities;
import id.co.gpsc.common.data.entity.FormElementConfiguration;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Dialog konfigurasi untuk number control
 * @author I Gede Mahendra
 * @since Jan 23, 2013, 10:11:19 AM
 * @version $Id
 */
public class DialogConfigurationNumber extends BaseDialogEditorConfig{

	private TextBox txtMinValue;
	private TextBox txtMaxValue;
	private Label lblMinValue;
	private Label lblMaxValue;
	
	/**
	 * Constructor
	 * @param formId
	 * @param elementId
	 */
	public DialogConfigurationNumber(String formId, String elementId, String groupId) {
		super(formId, elementId, groupId);		
	}

	@Override
	protected void renderDataIntoGeneralControl(
			FormElementConfiguration configurationData) {
		super.renderDataIntoGeneralControl(configurationData);
		String textMaxValue = "";
		String textMinValue = "";
		if(configurationData.getMaxValue() != null){
			textMaxValue = readValueFromJson(configurationData.getMaxValue());
		}
		if(configurationData.getMinValue() != null){
			textMinValue = readValueFromJson(configurationData.getMinValue());
		}
		txtMaxValue.setValue(textMaxValue);
		txtMinValue.setValue(textMinValue);
	}
	

	@Override
	protected void generateAdditionalControl(FlexTable flexTable) {
		txtMaxValue = new TextBox();
		txtMinValue = new TextBox();
		lblMaxValue = new Label("Max Value");
		lblMinValue = new Label("Min Value");
		
		txtMaxValue.setValue("0");
		txtMinValue.setValue("0");
		flexTable.setWidget(2, 0, lblMinValue);
		flexTable.setWidget(2, 1, txtMinValue);
		flexTable.setWidget(3, 0, lblMaxValue);
		flexTable.setWidget(3, 1, txtMaxValue);	
		
		cleanControl();
	}

	@Override
	protected void readDataFromControl(
			FormElementConfiguration configurationData) {
		super.readDataFromControl(configurationData);
		configurationData.setMaxValue(generateJsonValue(txtMaxValue.getValue()));	
		configurationData.setMinValue(generateJsonValue(txtMinValue.getValue()));
	}
	
	@Override
	protected boolean validateForm() {
		if(validateControlAtBaseClass()) {
			/*if(txtMaxValue.getValue().trim().length() == 0){
				Window.alert("Max value can't be null. Please fill this field!!!");
				txtMaxValue.setValue("0");
				txtMaxValue.setFocus(true);
				return false;
			}else if(txtMinValue.getValue().trim().length() == 0) {
				Window.alert("Min value can't be null. Please fill this field!!!");
				txtMinValue.setValue("0");
				txtMinValue.setFocus(true);
				return false;
			}*/
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Generate json value	 
	 */
	private String generateJsonValue(String value){
		JSONObject wrapValue = new JSONObject();
		JSONUtilities.getInstance().put(wrapValue, "value", value);				
		
		JSONObject retval = new JSONObject();
		JSONUtilities.getInstance().put(retval, "type", "number");
		JSONUtilities.getInstance().put(retval, "actual", wrapValue.toString());		
		return retval.toString();
	}
	
	/**
	 * Read value from json
	 * @param value
	 * @return
	 */
	private String readValueFromJson(String value){
		JSONObject json = (JSONObject) JSONParser.parseLenient(value);
		String stringJsonActual = JSONUtilities.getInstance().getString(json, "actual");
		
		JSONObject jsonValue = (JSONObject) JSONParser.parseLenient(stringJsonActual);		
		return JSONUtilities.getInstance().getString(jsonValue, "value");
	}
}