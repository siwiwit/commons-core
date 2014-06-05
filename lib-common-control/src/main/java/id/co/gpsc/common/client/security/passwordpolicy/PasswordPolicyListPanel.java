/**
 * 
 */
package id.co.gpsc.common.client.security.passwordpolicy;

import id.co.gpsc.common.client.security.BaseRootSecurityPanel;
import id.co.gpsc.common.client.security.group.IOpenAndCloseable;
import id.co.gpsc.common.client.security.group.IRemove;
import id.co.gpsc.common.client.security.rpc.PasswordPolicyRPCServiceAsync;
import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.PasswordPolicy;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Dode
 * @version $Id
 * @since Jan 30, 2013, 3:18:03 PM
 */
public class PasswordPolicyListPanel extends BaseRootSecurityPanel implements IReloadGridCommand, IOpenAndCloseable<PasswordPolicy>, IRemove<BigInteger> {

	private static PasswordPolicyListPanelUiBinder uiBinder = GWT
			.create(PasswordPolicyListPanelUiBinder.class);
	@UiField SimplePanel panelForGrid;
	
	//grid user
	private PasswordPolicyGridPanel gridPasswordPolicy;
	
	//dialog untuk editor
	private JQDialog dialog;
	
	//editor panel
	private PasswordPolicyEditorPanel editorPanel;

	interface PasswordPolicyListPanelUiBinder extends
			UiBinder<Widget, PasswordPolicyListPanel> {
	}

	public PasswordPolicyListPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//set reffer object2 yang diperlukan grid
		gridPasswordPolicy = new PasswordPolicyGridPanel();
		gridPasswordPolicy.setOpenCloseable(this);
		gridPasswordPolicy.setRemoveUtil(this);
		gridPasswordPolicy.setReloadGrid(this);
		gridPasswordPolicy.setPageChangeHandler(new PageChangeHandler() {
			
			@Override
			public void onPageChange(int newPage) {
				getDataPasswordPolicy();
			}
		});
		gridPasswordPolicy.setWidth(750);
		panelForGrid.add(gridPasswordPolicy);
		fillGridWithData();
	}
	
	private void fillGridWithData() {
		new Timer() {
			
			@Override
			public void run() {
				getDataPasswordPolicy();
			}
		}.schedule(200);
	}

	@Override
	public void remove(BigInteger parameter) {
		
			PasswordPolicyRPCServiceAsync.Util.getInstance().remove(parameter, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable e) {
					Window.alert(e.getMessage());
					e.printStackTrace();
				}

				@Override
				public void onSuccess(Void arg0) {
					reload();
				}
			});
		
		
	}
	
	/**
	 * generate user editor panel
	 * @return
	 */
	public PasswordPolicyEditorPanel getEditorPanel() {
		if (editorPanel == null) {
			editorPanel = new PasswordPolicyEditorPanel();
			editorPanel.setOpenClose(this);
			editorPanel.setReloadGrid(this);
		}
		return editorPanel;
	}

	@Override
	public void closeDialog() {
		dialog.close();
	}

	@Override
	public void openDialog(final PasswordPolicy data) {
		String saveButtonTitle = "";
		if ( dialog==null){
			dialog = new JQDialog("", getEditorPanel());
			
			saveButtonTitle = I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.save", "Save");
			
			//append save button ke dialog
			dialog.appendButton(saveButtonTitle, new Command() {
				
				@Override
				public void execute() {
					getEditorPanel().saveUser();
				}
			});
			//append cancel button ke dialog
			dialog.appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.cancel", "Cancel"), new Command() {
				
				@Override
				public void execute() {
					dialog.close();
				}
			});
			
			new Timer() {
				
				@Override
				public void run() {
					openDialog(data);
				}
			}.schedule(100);
			return ;	
		}
		
		//clear component in control form
		getEditorPanel().clearComponentData();
		
		getEditorPanel().setCurrentPasswordPolicy(data);
		
		//set title dialog
		dialog.setTitle(I18Utilities.getInstance().getInternalitionalizeText("security.passwordpolicy.title.dialog.addpasswordpolicy", "Add Password Policy"));
		
		dialog.setHeightToAuto();
		dialog.setWidth(550);
		dialog.setResizable(false);
		dialog.show(true);
	}

	@Override
	public void reload() {
		getDataPasswordPolicy();
	}

	@Override
	public String getTitlePanel() {
		return getApplicationNameForTitlePanel() + "Password Policy List".toUpperCase();
	}
	
	/**
	 * get data user berdasarkan searching criteria
	 */
	private void getDataPasswordPolicy() {
		try {
			PasswordPolicyRPCServiceAsync.Util.getInstance().getPasswordPolicyData(gridPasswordPolicy.getCurrentPageToRequest(), gridPasswordPolicy.getPageSize(), new AsyncCallback<PagedResultHolder<PasswordPolicy>>() {
				
				@Override
				public void onSuccess(PagedResultHolder<PasswordPolicy> result) {
					//set data ke grid + pagging buttonnya nampil
					gridPasswordPolicy.setData(result);
				}
				
				@Override
				public void onFailure(Throwable arg0) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorgetuser", "Failed to get user data !"));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
