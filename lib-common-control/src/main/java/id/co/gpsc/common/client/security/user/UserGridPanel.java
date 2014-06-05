/**
 * 
 */
package id.co.gpsc.common.client.security.user;

import id.co.gpsc.common.client.control.worklist.I18ColumnDefinition;
import id.co.gpsc.common.client.control.worklist.I18EnabledSimpleGrid;
import id.co.gpsc.common.client.security.group.IOpenAndCloseable;
import id.co.gpsc.common.client.security.group.IRemove;
import id.co.gpsc.common.client.security.rpc.UserRPCServiceAsync;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.BooleanColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.DateColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author Dode
 * @version $Id
 * @since Dec 17, 2012, 3:09:29 PM
 */
public class UserGridPanel extends I18EnabledSimpleGrid<User>{
	
	private IOpenAndCloseable<User> openCloseable;
	private IRemove<BigInteger> removeUtil;
	
	//cell button component
	private CellButtonHandler<User> btnEdit;
	private CellButtonHandler<User> btnDelete;
	private CellButtonHandler<User> btnResetPassword;
	private CellButtonHandler<User> btnStatusActive;
	private CellButtonHandler<User> btnStatusNotActive;
	private CellButtonHandler<User>[] actionButtons;
	
	private String currentUserLogin;
	private Date applicationDate;
	
	//editor reset password
	private ResetPasswordEditorPanel resetPassEditorPanel;
	
	//dialog untuk editor
	private JQDialog dialog;
	
	//hepler panel
	private FlowPanel helperPanel;
	
	public UserGridPanel() {
		actionButtons = generateActionButton();
		
		new Timer() {			
			@Override
			public void run() {
				getGridButtonWidget().appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.add", "Add"), "ui-icon ui-icon-plus", new Command() {			
					@Override
					public void execute() {
						User newData = new User();
						openCloseable.openDialog(newData);			
					}
				});				
			}
		}.schedule(50);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<User, ?>[] getColumnDefinitions() {
		BaseColumnDefinition<User, ?>[] retval = (BaseColumnDefinition<User, ?>[]) new BaseColumnDefinition<?, ?>[] {
			generateRowNumberColumnDefinition(I18Utilities.getInstance().getInternalitionalizeText("security.common.header.grid.no", "No"), 20, ""),
			generateButtonsCell(actionButtons, I18Utilities.getInstance().getInternalitionalizeText("security.common.header.grid.action", "Action"), "", 130),
			new StringColumnDefinition<User>(I18Utilities.getInstance().getInternalitionalizeText("security.user.header.grid.username", "User Name"), 80) {

				@Override
				public String getData(User data) {
					return data.getUserCode();
				}
			},
			new StringColumnDefinition<User>(I18Utilities.getInstance().getInternalitionalizeText("security.user.header.grid.fullname", "Full Name"), 110) {

				@Override
				public String getData(User data) {
					return data.getRealName();
				}
			},
			new DateColumnDefinition<User>(I18Utilities.getInstance().getInternalitionalizeText("security.user.header.grid.expdate", "Expired Date"), 80) {
				
				@Override
				public Date getData(User data) {
					return data.getExpiredDate();
				}
			},
			new StringColumnDefinition<User>(I18Utilities.getInstance().getInternalitionalizeText("security.user.header.grid.email", "Email"), 130) {

				@Override
				public String getData(User data) {
					return data.getEmail();
				}
			},
			new StringColumnDefinition<User>(I18Utilities.getInstance().getInternalitionalizeText("security.user.header.grid.application", "Application"), 80) {

				@Override
				public String getData(User data) {
					return data.getDefaultApplication().getApplicationName();
				}
			},
			new BooleanColumnDefinition<User>(I18Utilities.getInstance().getInternalitionalizeText("security.user.header.grid.ntlmser", "NTLM User"), 50) {

				@Override
				public Boolean getData(User data) {
					if ("Y".equals(data.getNtlmUser()))
						return true;
					return false;
				}
			}
		};
		return retval;
	}
	
	/**
	 * generate button di action column
	 * @return array cell button
	 */
	private CellButtonHandler<User>[] generateActionButton() {
		btnEdit = new CellButtonHandler<User>("ui-icon-pencil", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.edit", "Edit"), new DataProcessWorker<User>() {
			
			@Override
			public void runProccess(User data) {
				openCloseable.openDialog(data);
			};
		});
		
		btnDelete = new CellButtonHandler<User>("ui-icon-closethick", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.action.hint.erase", "Erase"), new DataProcessWorker<User>() {
			@Override
			public void runProccess(User data) {
				Boolean result = Window.confirm(I18Utilities.getInstance().getInternalitionalizeText("security.common.notification.deleteuser", "Are you sure to delete this data?"));
				if(result){
					removeUtil.remove(data.getId());
				}
			}				
		});
		
		btnResetPassword = new CellButtonHandler<User>("ui-icon-key", I18Utilities.getInstance().getInternalitionalizeText("security.user.button.action.hint.resetpassword", "Reset Password"), new DataProcessWorker<User>() {

			@Override
			public void runProccess(User data) {
				openDialog(data);
			}
		}){
			@Override
			public boolean isDataAllowMeToVisible(User data) {
				if ("Y".equals(data.getNtlmUser()))
					return false;
				return true;
			}
		};
		
		btnStatusActive = new CellButtonHandler<User>("ui-icon-unlocked", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.title.activate", "Deactivate"), new DataProcessWorker<User>() {

			@Override
			public void runProccess(User data) {
				activeDeactiveUser(false, data);
			}
		}) {
			@Override
			public boolean isDataAllowMeToVisible(User data) {
				return "A".equals(data.getActiveStatus());
			}
		};
		
		btnStatusNotActive = new CellButtonHandler<User>("ui-icon-locked", I18Utilities.getInstance().getInternalitionalizeText("security.common.button.title.deactivate", "Activate"), new DataProcessWorker<User>() {

			@Override
			public void runProccess(User data) {
				activeDeactiveUser(true, data);
			}
		}) {
			@Override
			public boolean isDataAllowMeToVisible(User data) {
				return !"A".equals(data.getActiveStatus());
			}
		};
		
		return (CellButtonHandler<User>[]) new CellButtonHandler<?>[] {btnEdit, btnResetPassword, btnDelete, btnStatusActive, btnStatusNotActive};
	}
	
	/**
	 * set status user active dan deactive
	 * @param isActive
	 * @param data
	 */
	private void activeDeactiveUser(boolean isActive, User data) {
		data.setActiveStatus(isActive ? "A" : "D");
		data.setModifiedBy(currentUserLogin);
		data.setModifiedOn(applicationDate);
		
		try {
			UserRPCServiceAsync.Util.getInstance().update(data, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void arg0) {
					reloadCommand.reload();
				}
				
				@Override
				public void onFailure(Throwable e) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorupdateuser", "Update user data failed !"));
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorupdateuser", "Update user data failed !"));
			e.printStackTrace();
		}
	}
	
	/**
	 * object untuk open close dialog
	 * @param openCloseable
	 */
	public void setOpenCloseable(IOpenAndCloseable<User> openCloseable) {
		this.openCloseable = openCloseable;
	}
	
	/**
	 * object untuk remove data
	 * @param removeUtil
	 */
	public void setRemoveUtil(IRemove<BigInteger> removeUtil) {
		this.removeUtil = removeUtil;
	}
	
	/**
	 * object untuk mereload grid
	 * @param reloadGrid
	 */
	public void setReloadGrid(IReloadGridCommand reloadGrid) {
		this.reloadCommand = reloadGrid;
	}
	
	/**
	 * render data ke dalam grid
	 * @param data
	 */
	public void renderDataToGrid(PagedResultHolder<User> data) {
		clearData();
		if (data == null)
			return ;
		List<User> users = data.getHoldedData(); 
		if (!(users == null || users.isEmpty())) {
			for (User user : users) {
				appendRow(user);
			}
		}
	}
	
	/**
	 * generate panel reset password
	 * @return
	 */
	public ResetPasswordEditorPanel getResetPassEditorPanel() {
		if (resetPassEditorPanel == null) {
			resetPassEditorPanel = new ResetPasswordEditorPanel();
			resetPassEditorPanel.setOpenClosable(new IOpenAndCloseable<User>() {
				
				@Override
				public void openDialog(User data) {
					openDialog(data);
				}
				
				@Override
				public void closeDialog() {
					closeDialog();
				}
			});
			resetPassEditorPanel.setReloadGrid(reloadCommand);
			getHelperPanel().add(resetPassEditorPanel);
		}
		return resetPassEditorPanel;
	}

	/**
	 * cloase dialog reset password panel
	 */
	public void closeDialog() {
		dialog.close();
	}

	/**
	 * open dialog reset password panel
	 */
	public void openDialog(final User data) {
		if (dialog == null) {
			dialog = new JQDialog("", getResetPassEditorPanel());
			//append apply button ke dialog
			dialog.appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.aply", "Apply"), new Command() {
				
				@Override
				public void execute() {
					getResetPassEditorPanel().saveResetPassword();
				}
			});
			//append cancel button ke dialog
			dialog.appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.cancel", "Cancel"), new Command() {
				
				@Override
				public void execute() {
					closeDialog();
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
		getResetPassEditorPanel().clearComponentData();
		getResetPassEditorPanel().setCurrentUser(data);
		getResetPassEditorPanel().renderDataToControlWorker(data);
		
		dialog.setTitle(I18Utilities.getInstance().getInternalitionalizeText("security.user.title.dialog.resetpassword", "Reset Password"));
		dialog.setHeightToAuto();
		dialog.setWidth(550);
		dialog.setResizable(false);
		dialog.show(true);
		
		getResetPassEditorPanel().adjustColumnWidth();
	}
	
	/**
	 * tempat singgah grid
	 **/
	protected FlowPanel getHelperPanel(){
		if ( helperPanel ==null){
			helperPanel = new FlowPanel();
			RootPanel.get().add(helperPanel);
			helperPanel.setVisible(false);
		}
		return helperPanel;
	}

	@Override
	public I18ColumnDefinition<User>[] getI18ColumnDefinitions() {
		
		return null;
	}
	
	public void setCurrentUserLogin(String currentUserLogin) {
		this.currentUserLogin = currentUserLogin;
	}
	
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
}
