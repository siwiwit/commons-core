package id.co.gpsc.common.client.widget.dialog;

import id.co.gpsc.common.client.control.i18.I18NTextBrowseButton;
import id.co.gpsc.common.client.form.SimpleSpanImageButton;
import id.co.gpsc.common.client.rpc.ApplicationConfigRPCServiceAsync;
import id.co.gpsc.common.data.entity.FormElementConfiguration;
import id.co.gpsc.common.data.entity.FormElementConfigurationPK;
import id.co.gpsc.jquery.client.container.JQDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;


/**
 * Base dialog spesial untuk editor configurasi field control
 * @author I Gede Mahendra
 * @since Jan 22, 2013, 10:46:29 AM
 * @version
 */
public abstract class BaseDialogEditorConfig {
	
	protected FlexTable flexTable;
	protected Label lblMandatory;
	protected Label lblHintI18NKey;
	protected CheckBox chkMandatory;
	protected JQDialog dialog;
	
	protected String formId;
	protected String elementId;
	protected String groupId;
	protected FormElementConfiguration dataConfiguration;
	
	
	
	protected I18NTextBrowseButton hintBrowseTextBox ; 
		
	
	
	
	
	/**
	 * tombol configure i18 text dari control
	 **/
	private SimpleSpanImageButton editControlLabelButton ;
	/**
	 * Additional constructor
	 * @param formId
	 * @param elementId
	 */
	public BaseDialogEditorConfig(String formId, String elementId, String groupId) {
		super();
		this.formId = formId;
		this.elementId = elementId;
		this.groupId = groupId;
		
	}

	
	/**
	 * Generate controll configuration. Dipanggil saat open dialog
	 */
	private void generateControl(){		
		flexTable = new FlexTable();
		lblMandatory = new Label("Mandatory");
		lblHintI18NKey = new Label("Hint I18N Key");
		chkMandatory = new CheckBox();
		hintBrowseTextBox = new I18NTextBrowseButton(); 
		
		chkMandatory.setValue(false);
		
		int start=0;
		flexTable.setWidget(0+start, 0, lblMandatory);
		flexTable.setWidget(0+start, 1, chkMandatory);
		flexTable.setWidget(1+start, 0, lblHintI18NKey);
		flexTable.setWidget(1+start, 1, hintBrowseTextBox);
		
		
		
		flexTable.getFlexCellFormatter().setWidth(0, 1, "300px");
		flexTable.getFlexCellFormatter().setWidth(0, 0, "150px");
		flexTable.setWidth("400px");
		
		generateAdditionalControl(flexTable);
		
		getDataFromDatabase();		
	}
	
	/**
	 * Save atau update field control
	 */
	private void saveOrUpdate() {
		if(validateForm()) {
			//FormElementConfiguration formElement =
			readDataFromControl(dataConfiguration);					
			ApplicationConfigRPCServiceAsync.Util.getInstance().saveControlConfiguration(dataConfiguration, new AsyncCallback<Void>() {			
				@Override
				public void onSuccess(Void arg0) {
					Window.alert("Success to save configuration control");
					dialog.close();
					dataConfiguration = null;
				}
				
				@Override
				public void onFailure(Throwable arg0) {
					Window.alert("Fail to save configuration control. Error : " + arg0.getLocalizedMessage());
					dataConfiguration = null;
				}
			});
		}		
	}
	
	/**
	 * Show dialog Editor Control
	 */
	public void showDialogEditorControl(){
		generateControl();		
	}
	
	/**
	 * Created dialog panel
	 * @param data 
	 */
	private void createDialogPanel(FormElementConfiguration configurationData) {
		renderDataIntoGeneralControl(configurationData);
		dialog = new JQDialog("Configuration Form", flexTable);
		dialog.setHeightToAuto();
		dialog.show(true);
		dialog.setWidth(500);
		dialog.appendButton("Save", new Command() {			
			@Override
			public void execute() {				
				saveOrUpdate();
			}
		});
		dialog.appendButton("Close", new Command() {			
			@Override
			public void execute() {
				dialog.close();
			}
		});				
		
		dialog.show();
	}
	
	/**
	 * Read data dari JSON atau dari tabel. Hasilnya akan di set ke control yg bersesuaian<br />
	 * Sebelum memanggil method ini,pastikan anda mengeset variable formId dan elementId dg memanggil method<br />
	 * <b>setFormAndElementId(formId, elementId)</b>
	 * @return FormElement
	 */
	protected FormElementConfiguration readFieldControlConfigFromDatabase(){				
		return this.dataConfiguration;
	}	
	
	/**
	 * Get data form database
	 */
	private void getDataFromDatabase() {
		try {
			System.out.println("Form Id (BaseDialog)    : " + formId);
			System.out.println("Elememet Id (BaseDialog): " + elementId);
			System.out.println("Group Id (BaseDialog)   : " + groupId);
			ApplicationConfigRPCServiceAsync.Util.getInstance().readControlConfiguration(formId, elementId, new AsyncCallback<FormElementConfiguration>() {
				@Override
				public void onFailure(Throwable exp) {
							
					dataConfiguration = instantiateConfiguration();
					createDialogPanel(dataConfiguration);
					//Window.alert("gagal membaca data dengan id :" +formId + "," +elementId +">>" + exp.getMessage() );
				}
				
				
				private FormElementConfiguration instantiateConfiguration(){
					FormElementConfiguration cnf = new FormElementConfiguration(); 
					 cnf.setId(new FormElementConfigurationPK());
					 cnf.getId().setFormId(formId);
					 cnf.getId().setElementId(elementId);
					 cnf.setGroupId(groupId);
					 return cnf ; 
				}

				@Override
				public void onSuccess(final FormElementConfiguration data) {
					if(data == null) {
						dataConfiguration = instantiateConfiguration();					
					}else {
						dataConfiguration = data;
						formId = data.getId().getFormId();
						elementId = data.getId().getElementId();
						groupId = data.getGroupId();
					}					
					createDialogPanel(  dataConfiguration);
				}			
			});
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Validate control in base class
	 * @return
	 */
	protected boolean validateControlAtBaseClass() {
		
		//none mandatory
		/*
		if(txtHintI18NKey.getValue().trim().length() > 0) {
			return true;
		}else {
			Window.alert("Hint I18N key can't be null. Please fill this field!!!");
			txtHintI18NKey.setFocus(true);
			return false;
		}*/
		return true ;
	}
	
	/**
	 * Clean control in baseclass
	 */
	protected void cleanControl() {
		this.hintBrowseTextBox.setValue("");
		chkMandatory.setValue(false);
	}
	
	/**
	 * Mengeset data hasil pembacaan dari tabel. Spesial untuk checkBox mandatory dan textBox HintI18NKey
	 * @param configurationData 
	 */
	protected  void renderDataIntoGeneralControl(FormElementConfiguration configurationData){
		chkMandatory.setValue(configurationData.getMandatory());
		this.hintBrowseTextBox.setValue(configurationData.getHintI18NKey());
	}
	/**
	 * Read data dari control
	 * @return FormElementConfiguration
	 */
	protected  void readDataFromControl(FormElementConfiguration configurationData){
		configurationData.setMandatory(chkMandatory.getValue());
		configurationData.setHintI18NKey(hintBrowseTextBox.getValueString());
	}
	/**
	 * Generate control tambahan sesuai dg form configurasinya. Secara default control yg masih ada
	 * mandatory dan hintI18NKey. Mulai dari 2,0
	 * @param flexTable
	 * @param chkMandatory
	 * @param txtHintI18NKey
	 */
	protected abstract void generateAdditionalControl(FlexTable flexTable); 
	
	
	/**
	 * ini untuk render control internalization
	 **/
	protected void configureControlI18NLabelData (FlexTable flexTable) {
		
		editControlLabelButton = new SimpleSpanImageButton(); 
		int latestRow = flexTable.getRowCount();
		flexTable.getFlexCellFormatter().setColSpan(latestRow-1, 0, 2);
		flexTable.setWidget(latestRow-1, 0, new HTML("<hr width='80%'/>"));
		flexTable.setWidget(latestRow , 0, editControlLabelButton);
		Element spnLbl = DOM.createSpan(); 
		spnLbl.setInnerHTML("Input Label I18N Key");
		flexTable.getCellFormatter().getElement(latestRow, 0).insertBefore(spnLbl, editControlLabelButton.getElement());
		flexTable.getRowFormatter().getElement(latestRow+1).setAttribute("valign", "top");
		final FlowPanel cntPanel  = new FlowPanel(); 
		
		flexTable.setWidget(latestRow , 1, cntPanel);
		
	}
	/**
	 * Validasi form
	 * @return true:Valid, false:Invalid
	 */
	protected abstract boolean validateForm();
}