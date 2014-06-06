package id.co.gpsc.common.client.form.i18;

import id.co.gpsc.common.client.control.i18.I18DataListPanel;
import id.co.gpsc.common.client.control.i18.I18GroupId;
import id.co.gpsc.common.client.control.i18.IReload;
import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.form.LOVCapabledComboBox;
import id.co.gpsc.common.client.rpc.ApplicationConfigRPCServiceAsync;
import id.co.gpsc.common.client.widget.BaseSimpleComposite;
import id.co.gpsc.common.data.entity.I18NTextGroup;
import id.co.gpsc.common.data.entity.I18Text;
import id.co.gpsc.common.data.entity.I18TextPK;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 * form untuk edit localization dari label
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a> 
 * @version $Id
 **/
public class LabelConfigurationEditorPanel extends BaseSimpleComposite  {
	
	private static LabelConfigurationEditorPanelUiBinder uiBinder = GWT.create(LabelConfigurationEditorPanelUiBinder.class);
	private String localeId;
	private boolean isEditable;
	private Integer version;
	private IReload iReload;
	
	@UiField ExtendedTextBox txtKeyLabel;
	@UiField LOVCapabledComboBox cmbGroupId;
	@UiField ExtendedTextBox txtLabel;
	@UiField Label lblLocalization;

	interface LabelConfigurationEditorPanelUiBinder extends
			UiBinder<Widget, LabelConfigurationEditorPanel> {
	}

	public LabelConfigurationEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));		
	}

	/**
	 * 
	 * @param data
	 * @param isEditable - True:Edit, false:Add
	 */
	public LabelConfigurationEditorPanel(I18DataListPanel data, boolean isEditable){
		initWidget(uiBinder.createAndBindUi(this));
		this.isEditable = isEditable;
		
		if(isEditable){
			editMode(data);
		}else{
			addMode(data);
		}
	}		
	
	/**
	 * On save handler
	 */
	public void onSaveHandler(){
		if(isEditable){
			update();
		}else{
			save();
		}
	}
	
	/**
	 * Save
	 */
	private void save(){
		I18Text data = new I18Text();
		I18TextPK id = new I18TextPK();
		id.setLocaleCode(localeId);
		id.setTextKey(txtKeyLabel.getValue());		
		data.setId(id);
		data.setGroupId(new I18NTextGroup(cmbGroupId.getValue(cmbGroupId.getSelectedIndex())));
		data.setLabel(txtLabel.getValue());
		data.setVersion(1);
		
		ApplicationConfigRPCServiceAsync.Util.getInstance().saveLabel(data, new AsyncCallback<Void>() {			
			@Override
			public void onSuccess(Void result) {
				Window.alert("Save success");
				iReload.reloadAndClose();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Save failed." + caught.getMessage());				
			}
		});
	}
	
	/**
	 * Update
	 */
	private void update(){
		I18Text data = new I18Text();
		I18TextPK id = new I18TextPK();
		id.setLocaleCode(localeId);
		id.setTextKey(txtKeyLabel.getValue());		
		data.setId(id);
		data.setGroupId(new I18NTextGroup(cmbGroupId.getValue(cmbGroupId.getSelectedIndex())));
		data.setLabel(txtLabel.getValue());
		data.setVersion(version + 1);
		
		ApplicationConfigRPCServiceAsync.Util.getInstance().updateLabel(data, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Update failed." + caught.getMessage());				
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Update success");	
				iReload.reloadAndClose();
			}			
		});
	}
	
	/**
	 * Addnew Mode
	 * @param data
	 */
	private void addMode(I18DataListPanel data){
		localeId = data.getLocale();
		lblLocalization.setText(data.getLocale());
		cmbGroupId.addItem(I18GroupId.FORM_SAMPLE1.toString());
		cmbGroupId.addItem(I18GroupId.FORM_SAMPLE2.toString());
		cmbGroupId.addItem(I18GroupId.FORM_SAMPLE3.toString());
	}
	
	/**
	 * Editable Mode
	 * @param data
	 */
	private void editMode(I18DataListPanel data){
		lblLocalization.setText(data.getLocale());
		txtKeyLabel.setText(data.getKey());		
		cmbGroupId.addItem(data.getGroupId());
		txtLabel.setValue(data.getLabel());
				
		txtKeyLabel.setReadOnly(true);
		cmbGroupId.setSelectedIndex(0);
		
		this.version = data.getVersion();
		this.localeId = data.getLocale();
	}

	public void setiReload(IReload iReload) {
		this.iReload = iReload;
	}	
}
