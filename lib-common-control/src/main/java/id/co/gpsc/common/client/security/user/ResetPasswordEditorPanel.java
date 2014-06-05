/**
 * 
 */
package id.co.gpsc.common.client.security.user;

import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.control.EditPasswordTextBox;
import id.co.gpsc.common.client.security.group.IOpenAndCloseable;
import id.co.gpsc.common.client.security.rpc.UserRPCServiceAsync;
import id.co.gpsc.common.security.MD5Utils;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.security.exception.PasswordPolicyException;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Dode
 * @version $Id
 * @since Dec 20, 2012, 5:12:55 PM
 */
public class ResetPasswordEditorPanel extends BaseAriumSecurityComposite {

	public final String ID_COLUMN_1 = "EDITOR_RESET_PASSWORD_COLUMN_1";
	
	private static ResetPasswordEditorPanelUiBinder uiBinder = GWT
			.create(ResetPasswordEditorPanelUiBinder.class);
	@UiField TextBoxWithLabel txtUserName;
	@UiField EditPasswordTextBox txtPassword;
	
	//object open close dialog dan reload grid
	private IOpenAndCloseable<User> openClosable;
	private IReloadGridCommand reloadGrid;
	
	//current data user
	private User currentUser;
	
	//string md5 password
	private String md5Password;

	interface ResetPasswordEditorPanelUiBinder extends
			UiBinder<Widget, ResetPasswordEditorPanel> {
	}

	public ResetPasswordEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	/**
	 * render data yang akan di edit ke form
	 * @param data
	 */
	public void renderDataToControlWorker(User data) {
		txtUserName.setValue(data.getUserCode());
	}
	
	/**
	 * render data dari form ke object 
	 * @param targetData
	 */
	public void renderDataEntryToPojo(User targetData) {
		targetData.setChipperText(txtPassword.getPasswordValue());
	}
	
	/**
	 * set current data user 
	 * @param currentUser
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	/**
	 * clear value form component
	 */
	public void clearComponentData() {
		txtUserName.setValue(null);
		txtPassword.setBothValue(null);
	}
	
	/**
	 * save password
	 */
	public void saveResetPassword() {
		//cek apakah password sama dan tidak kosong
		if (!txtPassword.validatePassword()) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorwrongpassword", "Wrong password or not same, please try again !"));
			return ;
		}
		
		//cek apakah password yang baru sama dengan password yang lama
		md5Password = MD5Utils.getInstance().hashMD5(txtPassword.getPasswordValue());
		if (currentUser.getChipperText().equals(md5Password)) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorpasswordmustdifferent", "New password must different with old password !"));
			return ;
		}
		
		renderDataEntryToPojo(currentUser);
		currentUser.setModifiedBy(getCurrentUserLogin());
		currentUser.setModifiedOn(getApplicationDate());
		
		try {
			UserRPCServiceAsync.Util.getInstance().resetPassword(currentUser, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void arg0) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.resetpasswordsucceed", "Reset password succeed."));
					reloadGrid.reload();
					openClosable.closeDialog();
				}
				
				@Override
				public void onFailure(Throwable e) {
					if (e instanceof PasswordPolicyException) {
						Window.alert(I18Utilities.getInstance().getInternalitionalizeText(((PasswordPolicyException) e).getI18nKeyFriendlyMessage(), ((PasswordPolicyException) e).getDefaultFriendlyMessage()) + ((PasswordPolicyException) e).constructPrintedDetailMessage());
						e.printStackTrace();
					} else {
						Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.resetpasswordfailed", "Reset password failed !"));
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.resetpasswordfailed", "Reset password failed !"));
			e.printStackTrace();
		}
	}
	
	/**
	 * set object untuk open close dialog reset password
	 * @param openClosable
	 */
	public void setOpenClosable(IOpenAndCloseable<User> openClosable) {
		this.openClosable = openClosable;
	}
	
	/**
	 * object untuk reload grid
	 * @param reloadGrid
	 */
	public void setReloadGrid(IReloadGridCommand reloadGrid) {
		this.reloadGrid = reloadGrid;
	}
	
	/**
	 * menyesuaikan lebar kolom tabel agar serasi
	 */
	public void adjustColumnWidth() {
		DOM.getElementById(txtPassword.ID_COLUMN_1).setId(txtPassword.ID_COLUMN_1 + "_RESSET_PASSWORD");
		Element elSourceColumn = DOM.getElementById(txtPassword.ID_COLUMN_1 + "_RESSET_PASSWORD");
		Element elTargetColumn = DOM.getElementById(ID_COLUMN_1);
		if (elSourceColumn == null || elTargetColumn == null) {
			new Timer() {
				
				@Override
				public void run() {
					adjustColumnWidth();
				}
			};
			return ;
		} else {
			int columnWidth = elSourceColumn.getOffsetWidth();
			elTargetColumn.getStyle().setWidth(columnWidth, Unit.PX);
			int colW = elTargetColumn.getOffsetWidth();
		}
	}
}
