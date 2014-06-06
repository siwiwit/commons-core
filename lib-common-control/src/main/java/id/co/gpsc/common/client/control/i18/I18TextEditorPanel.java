package id.co.gpsc.common.client.control.i18;

import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.form.LOVCapabledComboBox;
import id.co.gpsc.common.client.form.i18.LabelConfigurationEditorPanel;
import id.co.gpsc.common.client.rpc.ApplicationConfigRPCServiceAsync;
import id.co.gpsc.common.client.widget.BaseSimpleComposite;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.LabelDataWrapper;
import id.co.gpsc.common.data.entity.I18NTextGroup;
import id.co.gpsc.common.data.entity.I18Text;
import id.co.gpsc.common.data.entity.I18TextPK;
import id.co.gpsc.jquery.client.container.JQDialog;
import id.co.gpsc.jquery.client.grid.event.GridSelectRowHandler;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Main Panel untuk Editor Text i18
 * @author I Gede Mahendra
 * @since Sep 18, 2012, 11:37:54 AM
 * @version $Id
 */
public class I18TextEditorPanel extends BaseSimpleComposite implements IReload{

	private static I18TextEditorPanelUiBinder uiBinder = GWT.create(I18TextEditorPanelUiBinder.class);
	private LabelConfigurationEditorPanel editorPanel;
	private I18TextListPanel listPanel;
	private I18DataListPanel dataList;
	private JQDialog dialog;
	
	@UiField ExtendedTextBox txtKey;
	@UiField LOVCapabledComboBox cmbGroup;
	@UiField LOVCapabledComboBox cmbLocale;
	@UiField Button btnSearch;
	@UiField SimplePanel spGridPanel;
	@UiField Button btnEdit;
	@UiField Button btnAdd;
	
	
	interface I18TextEditorPanelUiBinder extends UiBinder<Widget, I18TextEditorPanel> {}

	public I18TextEditorPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		initAdditionalProcces();
		initActionHandler();
	}
	
	@Override
	public void reloadAndClose() {
		dialog.close();
		onBtnSearchClick();
	}
		
	public void onBtnEditClick() {
		editorPanel = new LabelConfigurationEditorPanel(dataList,true);
		editorPanel.setiReload(this);
		dialog = new JQDialog("Edit Label", editorPanel);
		dialog.appendButton("Save", new Command() {			
			@Override
			public void execute() {
				editorPanel.onSaveHandler();				
			}
		});
		
		dialog.setHeight(200);
		dialog.setWidth(300);
		dialog.moveToTop();
		dialog.show(true);
	}
		
	public void onBtnAddClick() {
		I18DataListPanel dataList = new I18DataListPanel();
		dataList.setLocale(cmbLocale.getValue(cmbLocale.getSelectedIndex()));
		
		editorPanel = new LabelConfigurationEditorPanel(dataList, false);
		editorPanel.setiReload(this);
		dialog = new JQDialog("Add Label", editorPanel);
		dialog.appendButton("Save", new Command() {			
			@Override
			public void execute() {
				editorPanel.onSaveHandler();			
			}
		});
		
		dialog.setHeight(200);
		dialog.setWidth(300);
		dialog.moveToTop();
		dialog.show(true);
	}
		
	public void onBtnSearchClick() {
		I18Text param = new I18Text();
		I18TextPK locale = new I18TextPK();
		locale.setLocaleCode(cmbLocale.getValue());
						
		param.setGroupId(new I18NTextGroup(cmbGroup.getValue(cmbGroup.getSelectedIndex())));
		param.setId(locale);
														
		ApplicationConfigRPCServiceAsync.Util.getInstance().getI18NGroupId(param, 10, 0, new AsyncCallback<PagedResultHolder<LabelDataWrapper>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getLocalizedMessage());						
			}

			@Override
			public void onSuccess(PagedResultHolder<LabelDataWrapper> result) {
				renderDataToGrid(result);				
			}
		});
	}
	
	/**
	 * Inisialisasi proses tambahan setelah init widget
	 */
	private void initAdditionalProcces(){
		this.listPanel = new I18TextListPanel();
		listPanel.setCaption("I18N Text Label");
		
		listPanel.getGridButtonWidget().appendButton("Add", "ui-icon-plusthick", new Command() {			
			@Override
			public void execute() {
				onBtnAddClick();
			}
		});
		
		listPanel.getGridButtonWidget().appendButton("Edit", "ui-icon-pencil", new Command() {			
			@Override
			public void execute() {
				onBtnEditClick();			
			}
		});
		
		spGridPanel.add(listPanel); //-> Add Grid		
		registerLOVCapableControl(cmbLocale); //-> Register LOV
		this.fillLookupValue(); //-> Fill nilai LOV pd combobox
		
		//add data dr enum (cuman utk sementara)		
		cmbGroup.addItem(I18GroupId.FORM_SAMPLE1.toString());
		cmbGroup.addItem(I18GroupId.FORM_SAMPLE2.toString());
		cmbGroup.addItem(I18GroupId.FORM_SAMPLE3.toString());
	}	
	
	/**
	 * Render data to grid
	 * @param data
	 */
	private void renderDataToGrid(final PagedResultHolder<LabelDataWrapper> data){
		new Timer() {	
			@Override
			public void run() {		
				List<LabelDataWrapper> dataWrapper = data.getHoldedData(); 
				int i=dataWrapper.size();
				if(i>0){
					listPanel.clearData();
					for (LabelDataWrapper list : dataWrapper) {
						listPanel.appendRow(new I18DataListPanel(i, list.getKey(), list.getLabel(), list.getLocaleCode(), list.getGroupId(), list.getVersion()));					
						i--;
					}
				}else{
					listPanel.clearData();
					Window.alert("Data Not Found");
					btnEdit.setEnabled(false);
				}
			}
		}.schedule(500);
		
		listPanel.addRowSelectedHandler(new GridSelectRowHandler<I18DataListPanel>() {			
			@Override
			public void onSelectRow(String rowId, I18DataListPanel selectedData) {
				dataList = selectedData;		
				btnEdit.setEnabled(true);
			}
		});								
	}	
	
	/**
	 * Inisialisasi action handler pd form
	 */
	private void initActionHandler(){
		btnEdit.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				onBtnEditClick();				
			}
		});
		
		btnAdd.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				onBtnAddClick();				
			}
		});
		
		btnSearch.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				onBtnSearchClick();				
			}
		});
	}
}