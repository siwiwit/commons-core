package id.co.gpsc.common.client.widget.dialog;


import com.google.gwt.user.client.ui.FlexTable;

/**
 * Dialog konfigurasi untuk string control
 * @author I Gede Mahendra
 * @since Jan 23, 2013, 11:33:22 AM
 * @version
 */
public class DialogConfigurationComboBox extends BaseDialogEditorConfig{

	/**
	 * Constructor
	 * @param formId
	 * @param elementId
	 */
	public DialogConfigurationComboBox(String formId, String elementId, String groupId) {
		super(formId, elementId, groupId);
	}

	

	@Override
	protected void generateAdditionalControl(FlexTable flexTable) {
		cleanControl();		
	}

	

	@Override
	protected boolean validateForm() {
		if(validateControlAtBaseClass()){
			return true;
		}else {
			return false;
		}		
	}
}