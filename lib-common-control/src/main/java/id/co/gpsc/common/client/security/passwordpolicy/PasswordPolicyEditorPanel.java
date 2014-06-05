/**
 * 
 */
package id.co.gpsc.common.client.security.passwordpolicy;

import id.co.gpsc.common.client.form.advance.I18HardCodeLOVComboBoxWithLabel;
import id.co.gpsc.common.client.form.advance.IntegerTextBoxWithLabel;
import id.co.gpsc.common.client.form.advance.TextBoxAreaWithLabel;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.group.IOpenAndCloseable;
import id.co.gpsc.common.client.security.rpc.PasswordPolicyRPCServiceAsync;
import id.co.gpsc.common.client.util.I18HardcodedLOVParam;
import id.co.gpsc.common.security.domain.PasswordPolicy;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Dode
 * @version $Id
 * @since Jan 30, 2013, 6:26:18 PM
 */
public class PasswordPolicyEditorPanel extends BaseAriumSecurityComposite {

	private static PasswordPolicyEditorPanelUiBinder uiBinder = GWT
			.create(PasswordPolicyEditorPanelUiBinder.class);
	@UiField IntegerTextBoxWithLabel txtMinLength;
	@UiField IntegerTextBoxWithLabel txtMinAlphabet;
	@UiField IntegerTextBoxWithLabel txtMinNumeric;
	@UiField IntegerTextBoxWithLabel txtMaxLoginAttempt;
	@UiField IntegerTextBoxWithLabel txtPasswordAge;
	@UiField IntegerTextBoxWithLabel txtOldPasswordAge;
	@UiField IntegerTextBoxWithLabel txtInactiveLimit;
	@UiField IntegerTextBoxWithLabel txtDisabledLimit;
	@UiField TextBoxAreaWithLabel txtRegularExpression;
	@UiField TextBoxAreaWithLabel txtRegularExpressionDesc;
	@UiField I18HardCodeLOVComboBoxWithLabel cbOldPasswordBase;
	
	//variabel for relation with list panel
	private IReloadGridCommand reloadGrid;
	private IOpenAndCloseable openClose;
	
	//save current user edited data
	private PasswordPolicy currentPasswordPolicy;

	interface PasswordPolicyEditorPanelUiBinder extends
			UiBinder<Widget, PasswordPolicyEditorPanel> {
	}

	public PasswordPolicyEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//render cb old password item
		I18HardcodedLOVParam[] paramOldPasswordbase ={
			new I18HardcodedLOVParam("security.passwordpolicy.combo.label.countbasis", "Count Basis", "C"),
			new I18HardcodedLOVParam("security.passwordpolicy.combo.label.daybasis", "Day Basis", "D")
		};
		cbOldPasswordBase.setI18HardcodeParam(paramOldPasswordbase);
	}

	/**
	 * set data from control worker to pojo
	 * @param targetData
	 */
	public void renderEntryDataToPojo(PasswordPolicy targetData) {
		targetData.setMinimumLength(txtMinLength.getValue().shortValue());
		targetData.setMinimumNumeric(txtMinNumeric.getValue().shortValue());
		targetData.setMinimumAlphabet(txtMinAlphabet.getValue().shortValue());
		targetData.setRegularExpression(txtRegularExpression.getValue());
		targetData.setRegexDesc(txtRegularExpressionDesc.getValue());
		targetData.setMaximumLoginAttempt(txtMaxLoginAttempt.getValue().shortValue());
		targetData.setPasswordAge(txtPasswordAge.getValue().shortValue());
		targetData.setOldPasswordBase(cbOldPasswordBase.getValue());
		targetData.setOldPasswordAge(txtOldPasswordAge.getValue().shortValue());
		targetData.setInactiveLimit(txtInactiveLimit.getValue().shortValue());
		targetData.setDisabledLimit(txtDisabledLimit.getValue().shortValue());
	}
	
	/**
	 * validasi untuk inputan yang mandatory
	 * @return true if valid, false if not
	 */
	private boolean validateForm() {
		if (txtMinLength == null)
			return false;
		if (txtMinAlphabet == null)
			return false;
		if (txtMinNumeric == null)
			return false;
		if (txtMaxLoginAttempt == null)
			return false;
		if (txtPasswordAge == null)
			return false;
		if (txtOldPasswordAge == null)
			return false;
		if (txtInactiveLimit == null)
			return false;
		if (txtDisabledLimit == null)
			return false;
		return true;
	}
	
	/**
	 * save input atau edit user
	 */
	public void saveUser() {
		//form validation 
		if (!validateForm()) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errormandatoryfield", "Mandatory field cannot be empty !"));
			return ;
		}
		
		renderEntryDataToPojo(currentPasswordPolicy);
		
		//save new data to db
		saveNewData(currentPasswordPolicy);
	}
	
	/**
	 * save new data to db
	 * @param data user data to save
	 */
	private void saveNewData(PasswordPolicy data) {
		data.setCreatedBy(getCurrentUserLogin());
		data.setCreatedOn(getApplicationDate());
		try {
			PasswordPolicyRPCServiceAsync.Util.getInstance().insert(data, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable e) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.allert.errorinsertpasswordpolicy", "Insert password policy data failed !"));
					e.printStackTrace();
				}

				@Override
				public void onSuccess(Void result) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.allert.insertpasswordpolicysuccess", "Save password policy data success."));
					actionAfterModify();
				}
			});
		} catch (Exception e) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.allert.errorinsertpasswordpolicy", "Insert password policy data failed !"));
			e.printStackTrace();
		}
	}
	
	/**
	 * action setelah melakukan save atau update
	 */
	private void actionAfterModify() {
		reloadGrid.reload();
		openClose.closeDialog();
	}
	
	public void setCurrentPasswordPolicy(PasswordPolicy currentPasswordPolicy) {
		this.currentPasswordPolicy = currentPasswordPolicy;
	}
	
	public void setReloadGrid(IReloadGridCommand reloadGrid) {
		this.reloadGrid = reloadGrid;
	}
	
	public void setOpenClose(IOpenAndCloseable openClose) {
		this.openClose = openClose;
	}
	
	/**
	 * reset value component form
	 */
	public void clearComponentData() {
		txtMinAlphabet.setValue(null);
		txtMinLength.setValue(null);
		txtMinNumeric.setValue(null);
		txtRegularExpression.setValue(null);
		txtRegularExpressionDesc.setValue(null);
		txtMaxLoginAttempt.setValue(null);
		txtPasswordAge.setValue(null);
		txtOldPasswordAge.setValue(null);
		txtInactiveLimit.setValue(null);
		txtDisabledLimit.setValue(null);
		cbOldPasswordBase.setSelectedIndex(0);
	}
}
