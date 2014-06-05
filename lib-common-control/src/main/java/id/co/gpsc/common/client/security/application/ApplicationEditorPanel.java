package id.co.gpsc.common.client.security.application;

import id.co.gpsc.common.client.form.ExtendedCheckBox;
import id.co.gpsc.common.client.form.ExtendedLabel;
import id.co.gpsc.common.client.form.advance.CheckBoxWithLabel;
import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.rpc.ApplicationRPCServiceAsync;
import id.co.gpsc.common.security.dto.ApplicationDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Widget;

/**
 * Application Editor Panel
 * @author I Gede Mahendra
 * @since Jan 2, 2013, 11:07:45 AM
 * @version $Id
 */
public class ApplicationEditorPanel extends BaseAriumSecurityComposite{

	private static ApplicationEditorPanelUiBinder uiBinder = GWT.create(ApplicationEditorPanelUiBinder.class);	
	private Boolean isEditable = false;	
	private IReloadGridCommand reloadGrid;
	
	@UiField TextBoxWithLabel txtApplicationCode;
	@UiField TextBoxWithLabel txtAplicationName;
	@UiField TextBoxWithLabel txtApplicationUrl;
	@UiField TextBoxWithLabel txtApplicationLoginUrl;
	@UiField ExtendedCheckBox chkActive;
	@UiField CheckBoxWithLabel chkKickLogin;
	@UiField Hidden txtApplicationId;	
	@UiField ExtendedLabel lblActive;
	@UiField ExtendedLabel lblPoint;
	@UiField TextBoxWithLabel txtNotifyUrl;
	
	interface ApplicationEditorPanelUiBinder extends UiBinder<Widget, ApplicationEditorPanel> {}

	/**
	 * Default Constructor
	 */
	public ApplicationEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		showHideCheckBoxActive();				
	}		
	
	/**
	 * Set data into component
	 * @param data
	 */
	public void setDataIntoComponet(ApplicationDTO data){
		if(data == null){
			clearAllValueComponent();
		}else{
			txtAplicationName.setValue(data.getApplicationName());
			txtApplicationCode.setValue(data.getApplicationCode());
			txtApplicationId.setValue(data.getId().toString());
			txtApplicationLoginUrl.setValue(data.getApplicationLoginUrl());
			txtApplicationUrl.setValue(data.getApplicationUrl());
			txtNotifyUrl.setValue(data.getApplicationNotifyUrl());
			
			chkActive.setValue(data.getIsActive());
			chkKickLogin.setValue(data.getIsConcurentUser());
			showHideCheckBoxActive();
		}
	}
	
	/**
	 * Membersihkan isi data pada component
	 */
	private void clearAllValueComponent(){
		txtAplicationName.setValue("");
		txtApplicationCode.setValue("");
		txtApplicationId.setValue("");
		txtApplicationLoginUrl.setValue("");
		txtApplicationUrl.setValue("");
		txtNotifyUrl.setValue("");
		chkActive.setValue(false);
		chkKickLogin.setValue(false);
	}
	
	/**
	 * Set state editable atau tidak
	 * @param isEditable - True:Edit, False:Add
	 */
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	/**
	 * Save or update data
	 */
	public void saveOrUpdate() {
		Boolean isActive = true;
		ApplicationDTO data = new ApplicationDTO();		
		String appId = txtApplicationId.getValue();
		if(!appId.equals("")){
			data.setId(new BigInteger(txtApplicationId.getValue()));
			isActive = chkActive.getValue();
		}		
		data.setApplicationCode(txtApplicationCode.getValue());
		data.setApplicationLoginUrl(txtApplicationLoginUrl.getValue());
		data.setApplicationName(txtAplicationName.getValue());
		data.setApplicationUrl(txtApplicationUrl.getValue());
		data.setApplicationNotifyUrl(txtNotifyUrl.getValue());
		data.setIsActive(isActive);
		data.setIsConcurentUser(chkKickLogin.getValue());
		data.setUserId(getCurrentUserLogin());
		
		ApplicationRPCServiceAsync.Util.getInstance().saveOrUpdate(data, new AsyncCallback<Void>() {			
			@Override
			public void onSuccess(Void arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationlist.allert.applicationlistsuccess", "Save application data success."));
				reloadGrid.reload();
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationlist.allert.errorapplicationlist", "Save application data fail. Please check your data again !"));
			}
		});
	}

	/**
	 * Set Interface reload
	 * @param reload
	 */
	public void setReload(IReloadGridCommand reload) {
		this.reloadGrid = reload;
	}
	
	/**
	 * Show atau hide check box active.
	 */
	private void showHideCheckBoxActive(){
		boolean state = false;
		if(isEditable){			
			state = true;
		}		
		lblActive.setVisible(state);
		lblPoint.setVisible(state);
		chkActive.setVisible(state);
	}
}