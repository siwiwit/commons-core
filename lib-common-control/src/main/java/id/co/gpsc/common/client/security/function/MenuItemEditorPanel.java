/**
 * 
 */
package id.co.gpsc.common.client.security.function;

import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.lookup.BrowseLookupPageDefinition;
import id.co.gpsc.common.client.security.rpc.FunctionRPCServiceAsync;
import id.co.gpsc.common.security.dto.ApplicationMenuDTO;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * panel editro untuk function
 * @author <a href="mailto:gede.wibawa@sigma.co.id">Agus Gede Adipartha Wibawa</a>
 * @since Aug 15, 2013 12:59:27 PM
 */
public class MenuItemEditorPanel extends BaseAriumSecurityComposite {

	@UiField TextBoxWithLabel txtMenuCode;
	@UiField TextBoxWithLabel txtMenuLabel;
	@UiField BrowseLookupPageDefinition browsePageDef;
	
	/**
	 * parent application data
	 */
	private ApplicationMenuDTO parentData;
	
	/**
	 * current function data
	 */
	private ApplicationMenuDTO currentData;
	
	/**
	 * apakah editor panel dalam mode add new
	 */
	private boolean stateAddNew;
	
	/**
	 * command jika ada statement yang harus di eksekusi
	 */
	private Command saveSucceedAction;
	private Command updateSucceedAction;
	
	private static MenuItemEditorPanelUiBinder uiBinder = GWT
			.create(MenuItemEditorPanelUiBinder.class);

	interface MenuItemEditorPanelUiBinder extends
			UiBinder<Widget, MenuItemEditorPanel> {
	}

	public MenuItemEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		browsePageDef.setResetClickHandler(new Command() {
			
			@Override
			public void execute() {
				browsePageDef.setValue(null);
			}
		});
	}

	/**
	 * render data ke control
	 * @param data data yang di render
	 */
	public void renderDataToControl(ApplicationMenuDTO data) {
		this.currentData = data;
		txtMenuCode.setValue(data.getCode());
		txtMenuLabel.setValue(data.getLabel());
		browsePageDef.setValue(data.getPageDetail());
	}
	
	/**
	 * get data yang diedit
	 * @return data yang diedit
	 */
	public ApplicationMenuDTO getEditedData() {
		return currentData;
	}
	
	/**
	 * fetch data dari control ke pojo
	 */
	public void fetchDataFromControlToPOJO() {
		currentData.setCode(txtMenuCode.getValue());
		currentData.setLabel(txtMenuLabel.getValue());
		currentData.setPageDetail(browsePageDef.getValue());
		currentData.setPageId(browsePageDef.getValue() == null? null : browsePageDef.getValue().getId());
	}
	
	/**
	 * set state editor panel
	 * true = add new, false = edit
	 * @param isStateAddNew
	 */
	public void setEditorState(boolean isStateAddNew) {
		this.stateAddNew = isStateAddNew;
	}
	
	/**
	 * apakah state editor add new
	 * @return
	 */
	public boolean isStateAddNew() {
		return stateAddNew;
	}
	
	/**
	 * validasi masukan user
	 * @return true =  valid, false = tidak valid
	 */
	public boolean validateUserEntry() {
		String errMsg = "";
		if (txtMenuLabel.getValue() == null || txtMenuLabel.getValue().trim().length() == 0) {
			errMsg += "Menu label tidak boleh kosong !\n" + errMsg;
			txtMenuLabel.setFocus(true);
		}
		if (txtMenuCode.getValue() == null || txtMenuCode.getValue().trim().length() == 0) {
			errMsg += "Menu code tidak boleh kosong !\n" + errMsg;
			txtMenuCode.setFocus(true);
		}
		if (errMsg.length() > 0) {
			Window.alert("Maaf data belum lengkap :\n" + errMsg + "Silahkan dilengkapi terlebih dahulu.");
			return false;
		}
		return true;
	}
	
	/**
	 * set parent data
	 * @param parentData parent data
	 */
	public void setParentData(ApplicationMenuDTO parentData) {
		this.parentData = parentData;
	}
	
	/**
	 * simpan / update menu item 
	 */
	public void saveMenuItem() {
		if (!validateUserEntry()) {
			return ;
		}
		fetchDataFromControlToPOJO();
		if (isStateAddNew()) {
			try {
				currentData.setParentId(parentData == null ? null : parentData.getId());
				currentData.setTreeLevel(parentData == null ? 1 : parentData.getTreeLevel() + 1);
				currentData.setMenuTreeCode(parentData == null ? null : (parentData.getMenuTreeCode() == null ? parentData.getId().toString() : parentData.getMenuTreeCode()));
				
				currentData.setCreatedBy(getCurrentUserLogin());
				currentData.setCreatedDate(new Date());
				FunctionRPCServiceAsync.Util.getInstance().appendNewMenuNode(currentData, new AsyncCallback<ApplicationMenuDTO>() {
					
					@Override
					public void onSuccess(ApplicationMenuDTO result) {
						currentData = result;
						if (saveSucceedAction != null) {
							saveSucceedAction.execute();
						}
					}
					
					@Override
					public void onFailure(Throwable e) {
						Window.alert("Gagal menyimpan data menu karena terjadi kesalahan pada server !");
						e.printStackTrace();
					}
				});
			} catch (Exception e) {
				Window.alert("Gagal menyimpan data menu karena terjadi kesalahan pada server !");
				e.printStackTrace();
			}
		} else {
			try {
				ApplicationMenuDTO editedData = getEditedData();
				editedData.setModifiedBy(getCurrentUserLogin());
				editedData.setModifiedDate(new Date());
				FunctionRPCServiceAsync.Util.getInstance().updateApplicationMenu(editedData, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable e) {
						Window.alert("Gagal menyimpan data menu karena terjadi kesalahan pada server !");
						e.printStackTrace();
					}

					@Override
					public void onSuccess(Void result) {
						if (updateSucceedAction != null) {
							updateSucceedAction.execute();
						}
					}
				});
			} catch (Exception e) {
				Window.alert("Gagal menyimpan data menu karena terjadi kesalahan pada server !");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * set command yang dieksekusi jika save success
	 * @param saveSucceedAction
	 */
	public void setSaveSucceedAction(Command saveSucceedAction) {
		this.saveSucceedAction = saveSucceedAction;
	}
	
	/**
	 * set command yang dieksekusi jika update success
	 * @param saveSucceedAction
	 */
	public void setUpdateSucceedAction(Command updateSucceedAction) {
		this.updateSucceedAction = updateSucceedAction;
	}
}
