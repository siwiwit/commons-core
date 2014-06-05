package id.co.gpsc.common.client.security.user;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.ExtendedComboBox;
import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.security.BaseRootSecurityPanel;
import id.co.gpsc.common.client.security.group.IOpenAndCloseable;
import id.co.gpsc.common.client.security.group.IRemove;
import id.co.gpsc.common.client.security.rpc.UserRPCServiceAsync;
import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Dode
 * @version $Id
 * @since Dec 17, 2012, 2:41:33 PM
 */
public class UserListPanel extends BaseRootSecurityPanel implements IReloadGridCommand, IOpenAndCloseable<User>, IRemove<BigInteger> {

	private static UserListPanelUiBinder uiBinder = GWT
			.create(UserListPanelUiBinder.class);
	@UiField ExtendedComboBox cbSearchCriteria;
	@UiField ExtendedTextBox txtSearchValue;
	@UiField ExtendedButton btnSearch;
	@UiField ExtendedButton btnReset;
	@UiField SimplePanel panelForGrid;
	
	//grid user
	private UserGridPanel gridUser;
	
	//editor panel
	private UserEditorPanel editorPanel;
	
	//dialog untuk editor
	private JQDialog dialog;
	
	interface UserListPanelUiBinder extends UiBinder<Widget, UserListPanel> {
	}

	public UserListPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		//set reffer object2 yang diperlukan grid
		gridUser = new UserGridPanel();
		gridUser.setOpenCloseable(this);
		gridUser.setRemoveUtil(this);
		gridUser.setReloadGrid(this);
		gridUser.setCurrentUserLogin(getCurrentUserLogin());
		gridUser.setApplicationDate(getApplicationDate());
		gridUser.setPageChangeHandler(new PageChangeHandler() {
			
			@Override
			public void onPageChange(int newPage) {
				getDataUser();
			}
		});
		
		panelForGrid.add(gridUser);
		renderItemToComboBox();
	}

	/**
	 * remove user
	 */
	@Override
	public void remove(BigInteger parameter) {
		try {
			UserRPCServiceAsync.Util.getInstance().remove(parameter, new AsyncCallback<Void>() {
				
				@Override
				public void onSuccess(Void arg0) {
					reload();
				}
				
				@Override
				public void onFailure(Throwable e) {
					Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorremovedata", "Remove user data failed !"));
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.user.alert.errorremovedata", "Remove user data failed !"));
			e.printStackTrace();
		}
	}

	/**
	 * close dialog user editor panel
	 */
	@Override
	public void closeDialog() {
		dialog.close();
	}

	/**
	 * open dialog user editor panel
	 */
	@Override
	public void openDialog(final User data) {
		String saveButtonTitle = "";
		if ( dialog==null){
			dialog = new JQDialog("", getEditorPanel());
			
			saveButtonTitle = I18Utilities.getInstance().getInternalitionalizeText(data.getId() == null ? "security.common.button.label.save" : "security.common.button.label.aply", data.getId() == null ? "Save" : "Apply");
			
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
		
		getEditorPanel().renderItemToCbApplication();
		getEditorPanel().setCurrentUser(data);
		
		//kalo id nya null berarti editornya dalam state add new
		if (data.getId() == null) {
			dialog.setTitle(I18Utilities.getInstance().getInternalitionalizeText("security.user.title.dialog.adduser", "Add User"));
			getEditorPanel().setEditorState(getEditorPanel().STATE_ADD_NEW);
		} else {
			dialog.setTitle(I18Utilities.getInstance().getInternalitionalizeText("security.user.title.dialog.edituser", "Edit User"));
			getEditorPanel().setEditorState(getEditorPanel().STATE_EDIT);
		}
		
		getEditorPanel().renderDataToControlWorker(data);
		
		dialog.setHeightToAuto();
		dialog.setWidth(620);
		dialog.setResizable(false);
		dialog.show(true);
		//adjust width colom component password
		getEditorPanel().adjustPasswordColumnWidth();
		//tampilkan password bila state editor add new
		getEditorPanel().showHidePasswordControl(true);
	}

	/**
	 * reload grid
	 */
	@Override
	public void reload() {
		getDataUser();
	}
	
	/**
	 * render item ke combobox searching criteria
	 */
	private void renderItemToComboBox() {
		cbSearchCriteria.addItem("User Name","userCode");
		cbSearchCriteria.addItem("Full Name", "realName");
	}

	/**
	 * handler untuk button reset
	 * @param event
	 */
	@UiHandler("btnReset")
	void onBtnResetClick(ClickEvent event) {
		gridUser.clearData();
		//hide pagging button
		gridUser.getGridButtonWidget().showHidePagingSide(false);
		txtSearchValue.setValue("");
		cbSearchCriteria.setSelectedIndex(0);
	}
	
	/**
	 * handler untuk button save
	 * @param event
	 */
	@UiHandler("btnSearch")
	void onBtnSearchClick(ClickEvent event) {
		gridUser.resetGrid();
		getDataUser();
	}
	
	/**
	 * generate query filter untuk searchingnya
	 * @return
	 */
	private SigmaSimpleQueryFilter[] generateQueryFilter() {
		if (txtSearchValue.getText() == null || txtSearchValue.getText().length() == 0)
			return null;
		SigmaSimpleQueryFilter filter = new SigmaSimpleQueryFilter();
		filter.setField(cbSearchCriteria.getValue());
		filter.setFilter(txtSearchValue.getValue());
		if (txtSearchValue.getValue().contains("%"))
			filter.setOperator(SimpleQueryFilterOperator.likePercentProvided);
		else
			filter.setOperator(SimpleQueryFilterOperator.equal);
		return new SigmaSimpleQueryFilter[] {filter};
	}
	
	/**
	 * get data user berdasarkan searching criteria
	 */
	private void getDataUser() {
		try {
			SigmaSimpleQueryFilter[] filters = generateQueryFilter();
			UserRPCServiceAsync.Util.getInstance().getUserByFilter(filters, gridUser.getCurrentPageToRequest(), gridUser.getPageSize(), new AsyncCallback<PagedResultHolder<User>>() {
				
				@Override
				public void onSuccess(PagedResultHolder<User> result) {
					//set data ke grid + pagging buttonnya nampil
					gridUser.setData(result);
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
	
	/**
	 * generate user editor panel
	 * @return
	 */
	public UserEditorPanel getEditorPanel() {
		if (editorPanel == null) {
			editorPanel = new UserEditorPanel();
			editorPanel.renderItemToCbApplication();
			editorPanel.setOpenClose(this);
			editorPanel.setReloadGrid(this);
		}
		return editorPanel;
	}

	@Override
	public String getTitlePanel() {
		return getApplicationNameForTitlePanel() + "User List".toUpperCase();
	}	
}