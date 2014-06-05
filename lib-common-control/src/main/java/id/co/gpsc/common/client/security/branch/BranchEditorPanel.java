package id.co.gpsc.common.client.security.branch;

import id.co.gpsc.common.client.form.ExtendedTextArea;
import id.co.gpsc.common.client.form.advance.CheckBoxWithLabel;
import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.lookup.BrowseLookupBranch;
import id.co.gpsc.common.client.security.rpc.BranchRPCServiceAsync;
import id.co.gpsc.common.control.SingleValueLookupResultHandler;
import id.co.gpsc.common.security.domain.Branch;
import id.co.gpsc.common.security.dto.BranchDTO;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Widget;

/**
 * Branch Editor Panel
 * @author I Gede Mahendra
 * @since Jan 31, 2013, 10:18:47 AM
 * @version $Id
 */
public class BranchEditorPanel extends BaseAriumSecurityComposite {

	private static BranchEditorPanelUiBinder uiBinder = GWT.create(BranchEditorPanelUiBinder.class);
	
	@UiField TextBoxWithLabel txtBranchCode;
	@UiField TextBoxWithLabel txtBranchName;
	@UiField ExtendedTextArea txtBranchAddress;
	@UiField ExtendedTextArea txtBranchDescription;
	@UiField CheckBoxWithLabel txtStatus;
	@UiField Hidden txtBranchId;
	@UiField BrowseLookupBranch txtParentCode;
	@UiField Hidden txtBranchParentId;

	private IReloadGridCommand commandReload;
	
	interface BranchEditorPanelUiBinder extends UiBinder<Widget, BranchEditorPanel> {}

	public BranchEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		configLookupHandler();
	}
	
	/**
	 * Clear data
	 */
	public void clearData(){
		txtBranchId.setValue("");
		txtBranchParentId.setValue("");
		txtBranchCode.setValue("");
		txtParentCode.getResetClickHandler().execute();
		txtBranchName.setValue("");
		txtBranchAddress.setValue("");
		txtBranchDescription.setValue("");		
		txtStatus.setValue(true);
		txtBranchCode.setEnabled(true);
		txtParentCode.setValue(null);
	}
	
	/**
	 * render data to worker
	 * @param data
	 */
	public void renderDataToControl(BranchDTO data){
		txtBranchId.setValue(String.valueOf(data.getId()));		
		
		if(data.getIdParent() == null){			
			txtBranchParentId.setValue("");
			txtParentCode.setValue(null);
		}else{			
			txtBranchParentId.setValue(String.valueOf(data.getIdParent()));
			BranchDTO branchParent = new BranchDTO();
			branchParent.setId(data.getIdParent());
			branchParent.setBranchParentCode(data.getBranchParentCode());
			branchParent.setBranchCode(data.getBranchParentCode());
			txtParentCode.setValue(branchParent);
		}	
		txtBranchCode.setEnabled(false);
		txtBranchCode.setValue(data.getBranchCode());				
		txtBranchName.setValue(data.getBranchName());
		txtBranchAddress.setValue(data.getBranchAddress());
		txtBranchDescription.setValue(data.getBranchDescription());
		
		boolean status = false;
		if(data.getBranchStatus().equalsIgnoreCase("A")){
			status = true;
		}
		txtStatus.setValue(status);
	}
	
	/**
	 * Save atau update data
	 */
	public void saveOrUpdate(){	
		if(validationForm()){
			BranchRPCServiceAsync.Util.getInstance().saveOrUpdateBranch(getDataFromControl(), new AsyncCallback<Void>() {			
				@Override
				public void onSuccess(Void arg) {
					Window.alert("Save branch is success");
					commandReload.reload();				
				}
				
				@Override
				public void onFailure(Throwable ex) {
					Window.alert("Sorry, save branch is failed. Please try again");
					ex.printStackTrace();
				}
			});
		}		
	}
	
	/**
	 * Remove branch
	 * @param id
	 */
	public void remove(BigInteger id){
		BranchRPCServiceAsync.Util.getInstance().remove(id, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable ex) {
				Window.alert("Sorry, save branch is failed. Please try again");
				ex.printStackTrace();				
			}

			@Override
			public void onSuccess(Void arg) {
				Window.alert("Remove branch is success");
				commandReload.reload();
			}			
		});
	}
	
	/**
	 * Get data from control
	 * @return Branch
	 */
	private Branch getDataFromControl(){
		Branch branch = new Branch();
		String status = "N";
		
		branch.setBranchAddress(txtBranchAddress.getValue());
		branch.setBranchCode(txtBranchCode.getValue());
		branch.setBranchName(txtBranchName.getValue());
		if(txtBranchParentId.getValue().length() > 0){
			branch.setBranchParendId(new BigInteger(txtBranchParentId.getValue()));
		}		
		branch.setCreatedBy(getCurrentUserLogin());
		branch.setDescription(txtBranchDescription.getValue());
		if(txtBranchId.getValue().length() > 0){
			branch.setId(new BigInteger(txtBranchId.getValue()));
		}
		if(txtStatus.getValue()){
			status = "A";
		}
		branch.setStatus(status);		
		return branch;
	}

	/**
	 * Set lookup handler
	 */
	private void configLookupHandler(){
		txtParentCode.setLookupHandler(new SingleValueLookupResultHandler<BranchDTO>() {			
			@Override
			public void onSelectionDone(BranchDTO data) {
				txtBranchParentId.setValue(data.getId().toString());
			}
		});		
		txtParentCode.setResetClickHandler(new Command() {			
			@Override
			public void execute() {
				txtBranchParentId.setValue("");				
			}
		});
	}
	
	/**
	 * Validasi mandatory
	 * @return boolean
	 */
	private boolean validationForm(){
		if(txtBranchCode.getValue().trim().length() == 0){
			Window.alert("Branch code can't be null");
			txtBranchCode.setFocus(true);
			return false;
		}else if(txtBranchName.getValue().trim().length() == 0){
			Window.alert("Branch name can't be null");
			txtBranchName.setFocus(true);
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Set reload command
	 * @param commandReload
	 */
	public void setCommandReload(IReloadGridCommand commandReload) {
		this.commandReload = commandReload;
	}
}