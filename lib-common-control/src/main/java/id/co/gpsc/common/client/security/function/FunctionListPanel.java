package id.co.gpsc.common.client.security.function;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.ExtendedComboBox;
import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.security.BaseRootSecurityPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Function list panel
 * @author I Gede Mahendra
 * @since Jan 31, 2013, 2:45:53 PM
 * @version $Id
 */
public class FunctionListPanel extends BaseRootSecurityPanel {

	private static FunctionListPanelUiBinder uiBinder = GWT.create(FunctionListPanelUiBinder.class);
	
	@UiField ExtendedComboBox cmbCriteria;
	@UiField ExtendedTextBox txtCriteria;
	@UiField ExtendedButton btnSearch;
	@UiField ExtendedButton btnReset;
	@UiField SimplePanel panelGrid;

	interface FunctionListPanelUiBinder extends UiBinder<Widget, FunctionListPanel> {}

	public FunctionListPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		populateComboBox();
	}

	@Override
	public String getTitlePanel() {		
		return getApplicationNameForTitlePanel() + "Function";
	}

	/**
	 * Populate comboBox
	 */
	private void populateComboBox(){
		cmbCriteria.addItem("Function Code", "functionCode");
		cmbCriteria.addItem("Function Name", "functionLabel");
	}
}