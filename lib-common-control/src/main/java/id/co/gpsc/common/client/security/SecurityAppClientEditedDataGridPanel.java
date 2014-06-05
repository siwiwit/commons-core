package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.control.BaseClientEditedDataGridPanel;
import id.co.gpsc.common.util.I18Utilities;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class SecurityAppClientEditedDataGridPanel<DATA> extends BaseClientEditedDataGridPanel<DATA> {

	@Override
	protected String getRowNumberI18NKey() {		
		return "security.common.header.grid.no";
	}
	
	@Override
	protected String getActionColumnHeaderLabelI18NKey() {		
		return "security.common.header.grid.action";
	}
	
	@Override
	protected String getEditButtonTitleI18NKey() {		
		return "security.common.button.action.hint.edit";
	}
	@Override
	protected String getEraseButtonTitleI18NKey() {		
		return "security.common.button.action.hint.erase";
	}
	@Override
	protected String getViewDetailButtonTitleI18NKey() {		
		return "security.common.button.action.hint.view";
	}
	
	/**
	 * key internalization untuk notifikasi erase data
	 **/
	protected abstract String  getEraseDataNotificationMessageI18NKey();
	
	/**
	 * notifikasi default hapus data
	 **/
	protected String getEraseDataNotificationDefaultMessage(){
		return "Are you sure to erase this data?";
	}
	
	
	/**
	 * override ini kalau anda memerlukan validasi tersendiri. sementara hanya di konfirmasi saja ke user
	 **/
	@Override
	protected void validateEraseAndConfirmEraseData(DATA data,
			AsyncCallback<Boolean> allowEraseCallback) {
		if ( Window.confirm(I18Utilities.getInstance().getInternalitionalizeText(getEraseDataNotificationMessageI18NKey(), getEraseDataNotificationDefaultMessage()))){
			allowEraseCallback.onSuccess(true);
		}
		else
			allowEraseCallback.onSuccess(false);
		I18Utilities.getInstance().getInternalitionalizeText(getEraseDataNotificationMessageI18NKey(),getEraseDataNotificationDefaultMessage());
	}	
}