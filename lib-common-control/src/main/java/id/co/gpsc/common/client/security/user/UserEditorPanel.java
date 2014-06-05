/**
 * 
 */
package id.co.gpsc.common.client.security.user;

import id.co.gpsc.common.client.form.ExtendedLabel;
import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.form.advance.CheckBoxWithLabel;
import id.co.gpsc.common.client.form.advance.ComboBoxWithLabel;
import id.co.gpsc.common.client.form.advance.DatePickerWithLabel;
import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.control.EditPasswordTextBox;
import id.co.gpsc.common.client.security.group.IOpenAndCloseable;
import id.co.gpsc.common.client.security.lookup.BrowseLookupUserDomain;
import id.co.gpsc.common.client.security.rpc.ApplicationRPCServiceAsync;
import id.co.gpsc.common.client.security.rpc.UserRPCServiceAsync;
import id.co.gpsc.common.control.SingleValueLookupResultHandler;
import id.co.gpsc.common.security.domain.Application;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.security.exception.PasswordPolicyException;
import id.co.gpsc.common.security.menu.UserDomain;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Dode
 * @version $Id
 * @since Dec 18, 2012, 2:54:21 PM
 */
public class UserEditorPanel extends BaseAriumSecurityComposite {
	
	/**
	 * variabel untuk melakukan validasi email
	 */
	private final String EMAIL_PATTERN = ".+@.+\\.[a-z]+";
	private RegExp regExp;
	
	//id kolom di tabel
	private final String ID_COLUMN_1 = "EDITOR_USER_SECURITY_COLUMN_1";
	private final String ID_ROW_4 = "EDITOR_USER_SECURITY_ROW_4";
	
	//state dari editornya
	public static final String STATE_ADD_NEW = "add";
	public static final String STATE_EDIT = "edit";
	
	private static UserEditorPanelUiBinder uiBinder = GWT
			.create(UserEditorPanelUiBinder.class);
	@UiField TextBoxWithLabel txtFullName;
	@UiField TextBoxWithLabel txtEmail;
	@UiField DatePickerWithLabel dtExpiredDate;
	@UiField CheckBoxWithLabel checkBoxNTLM;
	@UiField CheckBoxWithLabel checkBoxStatus;
	@UiField CheckBoxWithLabel checkBoxSuperAdmin;
	@UiField BrowseLookupUserDomain txtNtlmUserName;
	@UiField ComboBoxWithLabel cbDefaultApplication;
	@UiField ExtendedTextBox txtUserName;
	@UiField ExtendedLabel lblUserName;
	@UiField EditPasswordTextBox txtPassword;
	
	//variabel for relation with list panel
	private IReloadGridCommand reloadGrid;
	private IOpenAndCloseable openClose; 
	
	//save current user edited data
	private User currentUser;
	
	//editor state
	private String editorState;

	interface UserEditorPanelUiBinder extends UiBinder<Widget, UserEditorPanel> {
	}

	public UserEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		txtNtlmUserName.setLookupHandler(new SingleValueLookupResultHandler<UserDomain>() {
			
			@Override
			public void onSelectionDone(UserDomain data) {
				txtFullName.setValue(data.getFullName());
			}
		});
		txtNtlmUserName.setResetClickHandler(new Command() {
			
			@Override
			public void execute() {
				resetNtlmRelatedComponent();
			}
		});
	}
	
	/**
	 * adjust kolom width untuk element edit password
	 * supaya sama dengan kolom pada tabel editor
	 */
	public void adjustPasswordColumnWidth() {
		DOM.getElementById(txtPassword.ID_COLUMN_1).setId(txtPassword.ID_COLUMN_1 + "_EDITOR_USER_RESET_PASSWORD");
		Element element = DOM.getElementById(ID_COLUMN_1);
		Element elTargetColumn = DOM.getElementById(txtPassword.ID_COLUMN_1+ "_EDITOR_USER_RESET_PASSWORD");
		if (element == null || elTargetColumn == null) {
			new Timer() {
				
				@Override
				public void run() {
					adjustPasswordColumnWidth();
				}
			}.schedule(100);
			return ;
		} else {
			int columnWidth = element.getOffsetWidth();
			elTargetColumn.getStyle().setWidth(columnWidth - 5, Unit.PX);
		}
	}
	
	/**
	 * save new data to db
	 * @param data user data to save
	 */
	private void saveNewData(User data) {
		data.setCreatedBy(getCurrentUserLogin());
		data.setCreatedOn(getApplicationDate());
		try {
			UserRPCServiceAsync.Util.getInstance().insert(data, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable e) {
					if (e instanceof PasswordPolicyException)
						Window.alert(I18Utilities.getInstance().getInternalitionalizeText(((PasswordPolicyException) e).getI18nKeyFriendlyMessage(), ((PasswordPolicyException) e).getDefaultFriendlyMessage()) + "\n" + ((PasswordPolicyException) e).constructPrintedDetailMessage());
					else
						Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorinsertuser", "Insert user data failed !"));
					e.printStackTrace();
				}

				@Override
				public void onSuccess(Void result) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.allert.insertusersuccess", "Save new user success."));
					actionAfterModify();
				}
			});
		} catch (Exception e) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorinsertuser", "Insert user data failed !"));
			e.printStackTrace();
		}
	}
	
	/**
	 * save edit data to db
	 * @param data user data to save
	 */
	private void applyEditData(User data) {
		data.setModifiedBy(getCurrentUserLogin());
		data.setModifiedOn(getApplicationDate());
		
		try {
			UserRPCServiceAsync.Util.getInstance().update(data, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable e) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorupdateuser", "Update user data failed !"));
					e.printStackTrace();
				}

				@Override
				public void onSuccess(Void arg0) {
					Window.alert("Update user success");
					actionAfterModify();
				}
			});
		} catch (Exception e) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorupdateuser", "Update user data failed !"));
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
	
	/**
	 * set data ke control yang ada di form
	 * @param data user yang nilainya ditampilkan di form
	 */
	public void renderDataToControlWorker(User data) {
		//set control form to ntlm user rule
		ntlmUserControlRule(data);
		
		//render data to control form
		checkBoxNTLM.setValue("Y".equals(data.getNtlmUser())? true : false);
		txtFullName.setValue(data.getRealName());
		dtExpiredDate.setValue(data.getExpiredDate());
		txtEmail.setValue(data.getEmail());
		checkBoxStatus.setValue("A".equals(data.getStatus()) || data.getStatus()==null? true : false);
		//checkBoxSuperAdmin.setValue("Y".equals(data.getSuperAdmin()) || data.getSuperAdmin()==null? true : false );
		if(data.getSuperAdmin() == null){
			checkBoxSuperAdmin.setValue(false);
		}else{
			if(data.getSuperAdmin().equals("Y")){
				checkBoxSuperAdmin.setValue(true);
			}else{
				checkBoxSuperAdmin.setValue(false);
			}
		}		
		cbDefaultApplication.setValue(data.getDefaultApplicationId() == null ? cbDefaultApplication.getValue() : data.getDefaultApplicationId().toString());		
	}
	
	/**
	 * menyesuaikan form component berdsarkan field ntlmUser
	 * @param data
	 */
	private void ntlmUserControlRule(User data) {
		boolean isNtlmUser = false;
		if ("Y".equals(data.getNtlmUser())) {
			UserDomain userDomain = new UserDomain();
			userDomain.setUsername(data.getUserCode());
			userDomain.setFullName(data.getRealName());
			txtNtlmUserName.setValue(userDomain);
			isNtlmUser = true;
		} else {
			txtUserName.setValue(data.getUserCode());
		}
		if (STATE_ADD_NEW.equals(editorState))
			checkBoxNTLM.setEnabled(true);
		else
			checkBoxNTLM.setEnabled(false);
		enableDisableNtlmControl(isNtlmUser);
	}
	
	/**
	 * untuk enable atau disable kontrol yang berhubungan dengan ntlm user
	 * @param isNtlmUser apakah ntlm user
	 */
	private void enableDisableNtlmControl(boolean isNtlmUser) {
		showHidePasswordControl(!isNtlmUser);
		txtFullName.setEnabled(!isNtlmUser);
		dtExpiredDate.setEnabled(!isNtlmUser);
		txtNtlmUserName.setVisible(isNtlmUser);
		txtNtlmUserName.setVisible(isNtlmUser);
		txtUserName.setVisible(!isNtlmUser);
	}
	
	/**
	 * untuk show atau hide kontrol password
	 * @param isVisible
	 */
	public void showHidePasswordControl(final boolean isVisible) {
		Element element = DOM.getElementById(ID_ROW_4);
		if (element == null) {
			new Timer() {
				
				@Override
				public void run() {
					showHidePasswordControl(isVisible);
				}
			}.schedule(100);
			return ;
		} else {
			if (STATE_ADD_NEW.equals(editorState))
				element.getStyle().setProperty("display", isVisible? "" : "none");
			else
				element.getStyle().setProperty("display", "none");
		}
	}
	
	/**
	 * set data from control worker to pojo
	 * @param targetData
	 */
	public void renderEntryDataToPojo(User targetData) {
		targetData.setUserCode(checkBoxNTLM.getValue()? txtNtlmUserName.getValue().getUsername() : txtUserName.getValue());
		targetData.setNtlmUser(checkBoxNTLM.getValue()? "Y" : "N");
		targetData.setRealName(txtFullName.getValue());
		targetData.setExpiredDate(dtExpiredDate.getValue());
		targetData.setEmail(txtEmail.getValue());
		targetData.setStatus(checkBoxStatus.getValue()? "A" : "D");
		targetData.setSuperAdmin(checkBoxSuperAdmin.getValue()? "Y" : "N");
		targetData.setDefaultApplicationId(Integer.valueOf(cbDefaultApplication.getValue()));
		if (STATE_ADD_NEW.equals(editorState)) {
			targetData.setChipperText(checkBoxNTLM.getValue()? null : txtPassword.getPasswordValue());
		}
	}

	/**
	 * handler click untuk check box ntlm user
	 * @param event
	 */
	@UiHandler("checkBoxNTLM")
	void onCheckBoxNTLMClick(ClickEvent event) {
		boolean isNtlmUser = checkBoxNTLM.getValue();
		resetNtlmRelatedComponent();
		enableDisableNtlmControl(isNtlmUser);		
	}
	
	/**
	 * reset value component yang berkaitan dengan ntlmUser
	 */
	private void resetNtlmRelatedComponent() {
		txtNtlmUserName.setValue(null);
		txtFullName.setValue(null);
		txtPassword.setBothValue(null);
	}
	
	/**
	 * validasi untuk inputan yang mandatory
	 * @return true if valid, false if not
	 */
	private boolean validateForm() {
		//mandatory validation
		boolean isMandatoryValid = true;
		if (txtFullName.getText() == null || txtFullName.getValue().isEmpty())
			isMandatoryValid = false;
		if (txtEmail.getText() == null || txtEmail.getValue().isEmpty())
			isMandatoryValid = false;
		if (!checkBoxNTLM.getValue()) {
			if (dtExpiredDate.getValue() == null)
				isMandatoryValid = false;
			if (txtUserName.getText() == null || txtUserName.getValue().isEmpty())
				isMandatoryValid = false;
		} else {
			if (txtNtlmUserName.getValue().getUsername() == null || txtNtlmUserName.getValue().getUsername().isEmpty())
				isMandatoryValid = false;
		}
		if (!isMandatoryValid) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errormandatoryfield", "Mandatory field cannot be empty !"));
			return false;
		}
		
		//password validation
		if (!passwordValidation() && STATE_ADD_NEW.equals(editorState)) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorwrongpassword", "Wrong password or not same, please try again !"));
			return false;
		}
		
		//email validation
		if (!isEmailValid(txtEmail.getValue())) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.erroremailnotvalid", "Email not valid !"));
			return false;
		}
		return true;
	}
	
	/**
	 * validasi untuk input password
	 * @return true if valid, false if not
	 */
	private boolean passwordValidation() {
		if (checkBoxNTLM.getValue())
			return true;
		return txtPassword.validatePassword();
	}
	
	/**
	 * save input atau edit user
	 */
	public void saveUser() {
		//form validation 
		if (!validateForm())
			return ;
		
		renderEntryDataToPojo(currentUser);
		
		//if id null than, state is add new
		if (STATE_ADD_NEW.equals(editorState)) {
			saveNewData(currentUser);
		} else {
			applyEditData(currentUser);
		}
	}

	/**
	 * set object untuk reload grid
	 * @param reloadGrid
	 */
	public void setReloadGrid(IReloadGridCommand reloadGrid) {
		this.reloadGrid = reloadGrid;
	}

	/**
	 * set object untuk open close dialog
	 * @param openClose
	 */
	public void setOpenClose(IOpenAndCloseable openClose) {
		this.openClose = openClose;
	}
	
	/**
	 * set data current user 
	 * @param currentUser
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	/**
	 * reset value component form
	 */
	public void clearComponentData() {
		txtUserName.setValue(null);
		txtNtlmUserName.setValue(null);
		txtFullName.setValue(null);
		txtEmail.setValue(null);
		dtExpiredDate.setValue(new Date());
		checkBoxNTLM.setValue(false);
		checkBoxStatus.setValue(false);
		checkBoxSuperAdmin.setValue(false);
		txtPassword.setBothValue(null);
	}
	
	/**
	 * add item ke cb application
	 * @param applications list application
	 */
	public void addItemToCbApplication(List<Application> applications) {
		if (applications == null)
			return ;
		cbDefaultApplication.clear();
		for (Application app : applications) {
			cbDefaultApplication.addItem(app.getApplicationName(), app.getId().toString());
		}
	}
	
	/**
	 * request item untuk cb application
	 */
	public void renderItemToCbApplication() {
		try {
			ApplicationRPCServiceAsync.Util.getInstance().getApplicationList(new AsyncCallback<List<Application>>() {
				
				@Override
				public void onSuccess(List<Application> applications) {
					addItemToCbApplication(applications);
				}
				
				@Override
				public void onFailure(Throwable e) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorcbapplication", "Failed to get application data for combo box !"));
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorcbapplication", "Failed to get application data for combo box !"));
			e.printStackTrace();
		}
	}
	
	/**
	 * set editor state panel
	 * @param editorState
	 */
	public void setEditorState(String editorState) {
		this.editorState = editorState;
	}
	
	/**
	 * validate masukkan email
	 * @param email email yang divalidasi
	 * @return true = valid, false = not valid
	 */
	public boolean isEmailValid(String email) {
		if (regExp == null) {
			regExp = RegExp.compile(EMAIL_PATTERN);
		}
		return regExp.test(email);
	}
}