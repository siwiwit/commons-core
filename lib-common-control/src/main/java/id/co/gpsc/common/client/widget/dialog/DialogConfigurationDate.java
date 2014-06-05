package id.co.gpsc.common.client.widget.dialog;

import id.co.gpsc.common.client.util.FormatingUtils;
import id.co.gpsc.common.client.util.JSONUtilities;
import id.co.gpsc.common.data.entity.FormElementConfiguration;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

/**
 * Dialog untuk konfigurasi datepicker
 * @author I Gede Mahendra
 * @since Jan 23, 2013, 5:43:00 PM
 * @version $Id
 */
public class DialogConfigurationDate extends BaseDialogEditorConfig{

	private Label lblMinValue;
	private Label lblMaxValue;
	private AbsoluteDateValueComposite absMaxComposite;
	private AbsoluteDateValueComposite absMinComposite;
	
	private static String ABSOLUTE = "absolute";
	private static String RELATIVE = "relative";
	
	
	/**
	 * Constructor
	 * @param formId
	 * @param elementId
	 * @param groupId
	 */
	public DialogConfigurationDate(String formId, String elementId,String groupId) {
		super(formId, elementId, groupId);		
	}
	
	@Override
	protected void renderDataIntoGeneralControl(FormElementConfiguration elementConfig) {
		super.renderDataIntoGeneralControl(elementConfig);
				
		if(elementConfig.getMaxValue() != null){
			String[] temp = readValueJson(elementConfig.getMaxValue());
			
			if(temp.length == 1){ //berarti absolute
				absMaxComposite.getRdiAbsolute().setValue(true);
				absMaxComposite.getDatePicker().setValue(FormatingUtils.getInstance().formatStringToDate(temp[0]));
				absMaxComposite.getDatePicker().setVisible(true);
				absMaxComposite.getRelativeDate().setVisible(false);
			}else {
				//jika relative
				String[] relativeValue = readValueJson(elementConfig.getMaxValue());
				absMaxComposite.getRdiRelative().setValue(true);		
				absMaxComposite.getDatePicker().setVisible(false);
				absMaxComposite.getRelativeDate().setVisible(true);
				
				int countOperator = absMaxComposite.getRelativeDate().getCmbOperator().getItemCount();
				for (int i = 0; i < countOperator; i++) {
					String valueCmbBox = absMaxComposite.getRelativeDate().getCmbOperator().getValue(i);
					if(valueCmbBox.equals(relativeValue[0])) {
						absMaxComposite.getRelativeDate().getCmbOperator().setSelectedIndex(i);
						break;
					}
				}
				
				int countParameter = absMaxComposite.getRelativeDate().getCmbParameter().getItemCount();
				for (int i = 0; i < countParameter; i++) {
					String valueCmbBox = absMaxComposite.getRelativeDate().getCmbParameter().getValue(i);
					if(valueCmbBox.equals(relativeValue[1])){
						absMaxComposite.getRelativeDate().getCmbParameter().setSelectedIndex(i);
						break;
					}
				}
				
				absMaxComposite.getRelativeDate().getTxtValue().setValue(relativeValue[2]);
			}
		}
		
		if(elementConfig.getMinValue() != null){
			String[] temp = readValueJson(elementConfig.getMinValue());
			if(temp.length == 1){ //berarti absolute
				absMinComposite.getRdiAbsolute().setValue(true);
				absMinComposite.getDatePicker().setValue(FormatingUtils.getInstance().formatStringToDate(temp[0]));
				absMinComposite.getDatePicker().setVisible(true);
				absMinComposite.getRelativeDate().setVisible(false);
			}else {
				//jika relative
				String[] relativeValue = readValueJson(elementConfig.getMinValue());
				absMinComposite.getRdiRelative().setValue(true);		
				absMinComposite.getDatePicker().setVisible(false);
				absMinComposite.getRelativeDate().setVisible(true);
				
				int countOperator = absMinComposite.getRelativeDate().getCmbOperator().getItemCount();
				for (int i = 0; i < countOperator; i++) {
					String valueCmbBox = absMinComposite.getRelativeDate().getCmbOperator().getValue(i);
					if(valueCmbBox.equals(relativeValue[0])) {
						absMinComposite.getRelativeDate().getCmbOperator().setSelectedIndex(i);
						break;
					}
				}
				
				int countParameter = absMinComposite.getRelativeDate().getCmbParameter().getItemCount();
				for (int i = 0; i < countParameter; i++) {
					String valueCmbBox = absMinComposite.getRelativeDate().getCmbParameter().getValue(i);
					if(valueCmbBox.equals(relativeValue[1])){
						absMinComposite.getRelativeDate().getCmbParameter().setSelectedIndex(i);
						break;
					}
				}
				
				absMinComposite.getRelativeDate().getTxtValue().setValue(relativeValue[2]);
			}
		}
	}

	

	@Override
	protected void generateAdditionalControl(FlexTable flexTable) {
		lblMaxValue = new Label("Max Value");
		lblMinValue = new Label("Min Value");
		absMaxComposite = new AbsoluteDateValueComposite();
		absMinComposite = new AbsoluteDateValueComposite();
					
		flexTable.setWidget(2, 0, lblMinValue);
		flexTable.setWidget(2, 1, absMaxComposite);
		flexTable.setWidget(3, 0, lblMaxValue);
		flexTable.setWidget(3, 1, absMinComposite);	
		
		cleanControl();
	}

	
	@Override
	protected void readDataFromControl(
			FormElementConfiguration formElement) {
		super.readDataFromControl(formElement);
		if(absMaxComposite.getRdiAbsolute().getValue()) { //absoloute date
			String dateMax = "";
			if(absMaxComposite.getDatePicker().getValue() != null) {
				dateMax = FormatingUtils.getInstance().formatDateCustom(absMaxComposite.getDatePicker().getValue(), "yyyy-MM-dd");
			}			
			formElement.setMaxValue(generateValueJson("", "", dateMax, ABSOLUTE));			
		}else { //relative date
			String operator1 = absMaxComposite.getRelativeDate().getCmbOperator().getValue(absMaxComposite.getRelativeDate().getCmbOperator().getSelectedIndex());
			String operator2 = absMaxComposite.getRelativeDate().getCmbParameter().getValue(absMaxComposite.getRelativeDate().getCmbParameter().getSelectedIndex());
			String value = absMaxComposite.getRelativeDate().getTxtValue().getValue();
			formElement.setMaxValue(generateValueJson(operator1, operator2, value, RELATIVE));
		}
		
		if(absMinComposite.getRdiAbsolute().getValue()){//absolute date
			String dateMin = "";
			if(absMinComposite.getDatePicker().getValue() != null) {
				dateMin = FormatingUtils.getInstance().formatDateCustom(absMinComposite.getDatePicker().getValue(), "yyyy-MM-dd");
			}
			formElement.setMinValue(generateValueJson("", "", dateMin, ABSOLUTE));
		}else { //relative date
			String operator1 = absMinComposite.getRelativeDate().getCmbOperator().getValue(absMinComposite.getRelativeDate().getCmbOperator().getSelectedIndex());
			String operator2 = absMinComposite.getRelativeDate().getCmbParameter().getValue(absMinComposite.getRelativeDate().getCmbParameter().getSelectedIndex());
			String value = absMinComposite.getRelativeDate().getTxtValue().getValue();
			formElement.setMinValue(generateValueJson(operator1, operator2, value, RELATIVE));
		}
		
	}
	

	@Override
	protected boolean validateForm() {
		if(validateControlAtBaseClass()) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Generate value for max or min value
	 * @param operator1
	 * @param operator2
	 * @param value
	 * @return StringJSON
	 */
	private String generateValueJson(String operator1, String operator2, String value, String dataType) {	
		JSONObject wrapValue = new JSONObject();
		JSONUtilities.getInstance().put(wrapValue, "dataType", dataType);
		if(dataType.equalsIgnoreCase(RELATIVE)){
			JSONUtilities.getInstance().put(wrapValue, "operator1", operator1);
			JSONUtilities.getInstance().put(wrapValue, "operator2", operator2);
		}		
		JSONUtilities.getInstance().put(wrapValue, "value", value);
		
		JSONObject retval = new JSONObject();
		JSONUtilities.getInstance().put(retval, "type", "date");
		JSONUtilities.getInstance().put(retval, "actual", wrapValue.toString());								
		return retval.toString();
	}
	
	/**
	 * Read value JSON
	 * @param json
	 * @return String[]
	 */
	private String[] readValueJson(String jsonString) {
		JSONObject json = (JSONObject) JSONParser.parseLenient(jsonString);
		String stringJsonActual = JSONUtilities.getInstance().getString(json, "actual");
						
		JSONObject retval = (JSONObject) JSONParser.parseLenient(stringJsonActual);
		
		String dataType = JSONUtilities.getInstance().getString(retval, "dataType");
		String value = JSONUtilities.getInstance().getString(retval, "value");
		if(dataType.equalsIgnoreCase(RELATIVE)){
			String operator1 = JSONUtilities.getInstance().getString(retval, "operator1");
			String operator2 = JSONUtilities.getInstance().getString(retval, "operator2");
			return new String[] {operator1,operator2,value};
		}else{
			return new String[]{value};
		}
	}
}