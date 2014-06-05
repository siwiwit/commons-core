package id.co.gpsc.common.client.dualcontrol;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.advance.TextBoxAreaWithLabel;
import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.widget.BaseSigmaComposite;
import id.co.gpsc.common.data.app.DualControlEnabledData;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * panel simple approval. untuk multiple item
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @deprecated class ini blm siap untuk di pergunakan
 */
@Deprecated 
public class SimpleMultipleItemApprovalEditor<DATA extends DualControlEnabledData<?,?>> extends BaseSigmaComposite implements IDualControlMultipleDataEditor<DATA>{

	private static SimpleMultipleItemApprovalEditorUiBinder uiBinder = GWT
			.create(SimpleMultipleItemApprovalEditorUiBinder.class);
	@UiField TextBoxWithLabel txtRequestor;
	@UiField TextBoxAreaWithLabel txtLatestRemark;
	@UiField ExtendedButton btnApprove;
	@UiField ExtendedButton btnReject;
	@UiField ExtendedButton btnClose;
	@UiField TextBoxWithLabel txtRequestTime;
	@UiField SimplePanel gridContainerPanel;
	@UiField HTML lblEditorTitle;

	interface SimpleMultipleItemApprovalEditorUiBinder extends
			UiBinder<Widget, SimpleMultipleItemApprovalEditor> {
	}

	public SimpleMultipleItemApprovalEditor() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void viewMultipleData(BigInteger dualControlDataId) {
		
		
	}
	
	
	@Override
	public void viewApprovedOrRejectedMultipleData(BigInteger dualControlDataId) {
		
		
	}

	@Override
	public void approveMultipleData(BigInteger dualControlDataId) {
		
		
	}

	@Override
	public Class<DATA> getProccessedClass() {
		
		return null;
	}

	@Override
	public HandlerRegistration addDataChangeHandlers(
			DataChangedEventHandler<DATA> handler) {
		
		return null;
	}

	@Override
	public void switchRemarkReadonly(boolean readonly) {
		if ( readonly ){
			txtLatestRemark.switchToReadonlyText();
		}
		else{
			txtLatestRemark.restoreControl();
		}
		
	}

	@Override
	public void setRemarkLabel(String label) {
		txtLatestRemark.setControlLabel( label); 
		
	}

}
