
package id.co.gpsc.common.client.security.applicationuser;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.ExtendedComboBox;
import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.security.BaseRootSecurityPanel;
import id.co.gpsc.common.client.security.SecurityConstant;
import id.co.gpsc.common.client.security.common.IOpenCloseDialog;
import id.co.gpsc.common.client.security.group.IRemove;
import id.co.gpsc.common.client.security.rpc.ApplicationUserRPCServiceAsync;
import id.co.gpsc.common.client.security.rpc.UserRPCServiceAsync;
import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;

import java.math.BigInteger;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel Worklist User
 * @author I Gede Mahendra
 * @since Dec 14, 2012, 10:19:52 AM
 * @version $Id
 */
public class ApplicationUserListPanel extends BaseRootSecurityPanel implements IOpenCloseDialog<UserDTO>, IRemove<UserDTO>{

	private static UserListPanelUiBinder uiBinder = GWT.create(UserListPanelUiBinder.class);
		
	@UiField ExtendedComboBox cmbCriteria;
	@UiField ExtendedTextBox txtCriteria;
	@UiField ExtendedButton btnSearch;
	@UiField ExtendedButton btnReset;
	@UiField SimplePanel panelGrid;
	
	private ApplicationUserGridPanel userGridPanel;
	private ApplicationUserEditorPanel editorPanel;
	private JQDialog dialog;
	

	interface UserListPanelUiBinder extends UiBinder<Widget, ApplicationUserListPanel> {}

	public ApplicationUserListPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		userGridPanel = new ApplicationUserGridPanel();
		userGridPanel.setiOpenCloseDialog(this);
		userGridPanel.setiRemove(this);
		
		panelGrid.add(userGridPanel);
		
		//txtTitleApplication.setText(getApplicationName().toUpperCase());
		
		//FIXME Dein : Masih hardcode belum dimasukkan ke tabel LOV & belum support I18N
		cmbCriteria.addItem(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.combobox.item.username", "User Name"), "userCode");
		cmbCriteria.addItem(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.combobox.item.fullname", "Full Name"), "realName");
		cmbCriteria.addItem(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.combobox.item.email", "Email"), "email");
		
		userGridPanel.setPageChangeHandler(new PageChangeHandler() {			
			@Override
			public void onPageChange(int newPage) {
				getDataUsername();			
			}
		});
	}
	
	@Override
	public String getTitlePanel() {		
		return getApplicationNameForTitlePanel() + I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.title.panel.applicationuser", "Application User").toUpperCase();
	}
	
	@Override
	public void remove(UserDTO parameter) {		
		ApplicationUserRPCServiceAsync.Util.getInstance().deleteApplicationUser(getApplicationDTO().getId(), parameter.getIdUser(), new AsyncCallback<Void>() {			
			@Override
			public void onSuccess(Void arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.alert.removesuccess", "Remove data success"));
				getDataUsername();
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.alert.errorremovefailed", "Fail to remove data !"));				
			}
		});
	}
	
	@Override
	public void openDialog(UserDTO data) {
		if(dialog == null){			
			editorPanel = new ApplicationUserEditorPanel();
			dialog = new JQDialog(I18Utilities.getInstance().getInternalitionalizeText("security.user.title.dialog.adduser", "Add User"), editorPanel);
			dialog.appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.save", "Save"), new Command() {			
				@Override
				public void execute() {					
					List<UserGroupAssignmentDTO> temp = editorPanel.getTemporaryListUserGroup();
					insertOrUpdateGroup(temp, new BigInteger(editorPanel.getTxtUserId().getValue()));
				}
			});
			
			dialog.appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.cancel", "Cancel"), new Command() {			
				@Override
				public void execute() {
					dialog.close();
				}
			});
		}				
		editorPanel.setDataIntoForEdit(data);		
		dialog.setHeightToAuto();
		dialog.setWidth(530);					
		dialog.show(true);
	}

	@Override
	public void closeDialog() {
		dialog.close();		
	}
	
	@UiHandler("btnSearch")
	void onBtnSearchClick(ClickEvent event) {		
		getDataUsername();		
	}
	
	@UiHandler("btnReset")
	void onBtnResetClick(ClickEvent event) {
		cmbCriteria.setSelectedIndex(0);
		txtCriteria.setText("");
		userGridPanel.clearData();
		userGridPanel.getGridButtonWidget().showHidePagingSide(false);
	}
	
	/**
	 * Insert atau update group user
	 * @param data
	 */
	private void insertOrUpdateGroup(List<UserGroupAssignmentDTO> data, BigInteger userId){
		ApplicationUserRPCServiceAsync.Util.getInstance().insertOrUpdate(data, getCurrentApplicationId(), userId, getCurrentUserLogin(), new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.alert.errorsaveusergroup", "Fail to save user group. Please try again !"));
			}

			@Override
			public void onSuccess(Void arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.alert.saveusergroupsuccess", "Success to save user group."));
				dialog.close();
				getDataUsername();
			}
			
		});
	}
	
	/**
	 * Get data user from database
	 */
	private void getDataUsername(){
		/*Set criteria pencarian*/
		
		String filterChoise = cmbCriteria.getValue(cmbCriteria.getSelectedIndex());
		SimpleQueryFilter[] filter = new SimpleQueryFilter[1];
		SimpleQueryFilter filterAssign = new SimpleQueryFilter();		
				
		if(filterChoise.equals(SecurityConstant.FILTER_USERNAME)){			
			filterAssign.setField(SecurityConstant.FILTER_USERNAME);			
		}else if(filterChoise.equals(SecurityConstant.FILTER_FULLNAME)){			
			filterAssign.setField(SecurityConstant.FILTER_FULLNAME);			
		}else if(filterChoise.equals(SecurityConstant.FILTER_EMAIL)){
			filterAssign.setField(SecurityConstant.FILTER_EMAIL);
		}
		filterAssign.setFilter(txtCriteria.getText());
		filter[0] = filterAssign;
		
		int posisi = userGridPanel.getCurrentPageToRequest();
		int jumlahData = userGridPanel.getPageSize();
		
		/*Request data ke server*/
		UserRPCServiceAsync.Util.getInstance().getUserByParameter(getApplicationDTO().getId(), filter, posisi, jumlahData, new AsyncCallback<PagedResultHolder<UserDTO>>() {			
			@Override
			public void onSuccess(PagedResultHolder<UserDTO> arg0) {					
				renderGrid(arg0);
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationuser.alert.errorgetdatafromserver", "Error to get data from server. Please check log detail !"));
			}
		});
	}
	
	/**
	 * Render data to grid
	 * @param data
	 */
	private void renderGrid(PagedResultHolder<UserDTO> data){
		if(data == null){
			userGridPanel.clearData();
		}else{
			userGridPanel.clearData();
			userGridPanel.setData(data);			
		}			
	}	
}