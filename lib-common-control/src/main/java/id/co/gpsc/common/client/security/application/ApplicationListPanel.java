package id.co.gpsc.common.client.security.application;

import id.co.gpsc.common.client.security.BaseAriumSecurityComposite;
import id.co.gpsc.common.client.security.BaseRootSecurityPanel;
import id.co.gpsc.common.client.security.common.IOpenCloseDialog;
import id.co.gpsc.common.client.security.control.RootPanelManager;
import id.co.gpsc.common.client.security.rpc.ApplicationRPCServiceAsync;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.dto.ApplicationDTO;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * List panel application
 * @author I Gede Mahendra
 * @since Dec 28, 2012, 10:46:35 AM
 * @version $Id
 */
public class ApplicationListPanel extends BaseRootSecurityPanel implements IOpenCloseDialog<ApplicationDTO>{

	private static ApplicationListPanelUiBinder uiBinder = GWT.create(ApplicationListPanelUiBinder.class);
	private ApplicationGridPanel gridPanel;
	private ApplicationEditorPanel editorPanel;
	private JQDialog dialog;
	
	@UiField SimplePanel panelGrid;	

	interface ApplicationListPanelUiBinder extends UiBinder<Widget, ApplicationListPanel> {}

	/**
	 * Default Constructor
	 */
	public ApplicationListPanel() {
		initWidget(uiBinder.createAndBindUi(this));		
		gridPanel = new ApplicationGridPanel();
		gridPanel.setActionDialog(this);
		gridPanel.setActionReload(new IReloadGridCommand() {			
			@Override
			public void reload() {
				getData();			
			}
		});
		
		gridPanel.setActionSwitchMenu(new DataProcessWorker<ApplicationDTO>() {			
			@Override
			public void runProccess(ApplicationDTO data) {						
				switchToMenuWithBack(data);
			}
		});
		
		panelGrid.add(gridPanel);
		new Timer() {			
			@Override
			public void run() {				
				getData();
			}
		}.schedule(100);			
	}
	
	private void switchToMenuWithBack(ApplicationDTO data){
		BaseAriumSecurityComposite.setApplicationDTO(data);
		RootPanelManager.getInstance().switchMenuWithBackButton();
	}
	
	@Override
	public void openDialog(ApplicationDTO data) {
		if(dialog == null){
			editorPanel = new ApplicationEditorPanel();	
			editorPanel.setReload(new IReloadGridCommand() {				
				@Override
				public void reload() {
					getData();
					dialog.close();
				}
			});
			
			dialog = new JQDialog(I18Utilities.getInstance().getInternalitionalizeText("security.applicationlist.title.dialog.addapplication", "Add Application"), editorPanel);
			dialog.appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.save", "Save"), new Command() {					
				@Override
				public void execute() {
					editorPanel.saveOrUpdate();						
				}
			});				
			
			dialog.appendButton(I18Utilities.getInstance().getInternalitionalizeText("security.common.button.label.cancel", "Cancel"), new Command() {					
				@Override
				public void execute() {
					dialog.close();						
				}
			});
		}
		
		if(data == null){
			dialog.setTitle(I18Utilities.getInstance().getInternalitionalizeText("security.applicationlist.title.dialog.addapplication", "Add Application"));
			editorPanel.setIsEditable(false);
			editorPanel.setDataIntoComponet(null);
		}else{
			dialog.setTitle(I18Utilities.getInstance().getInternalitionalizeText("security.applicationlist.title.dialog.modifyapplication", "Modify Application"));
			editorPanel.setIsEditable(true);
			editorPanel.setDataIntoComponet(data);
		}
		
		dialog.setHeightToAuto();
		dialog.setWidth(550);
		dialog.setResizable(false);
		dialog.show(true);		
	}

	@Override
	public void closeDialog() {
		dialog.close();		
	}
	
	/**
	 * Get data application list
	 */
	private void getData(){
		int posisi = gridPanel.getCurrentPageToRequest();
		int jumlahData = gridPanel.getPageSize();
		
		ApplicationRPCServiceAsync.Util.getInstance().getApplicationList(posisi, jumlahData, new AsyncCallback<PagedResultHolder<ApplicationDTO>>() {			
			@Override
			public void onSuccess(PagedResultHolder<ApplicationDTO> arg0) {
				renderGrid(arg0);						
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText("security.applicationlist.allert.errorgetapplicationlist", "Fail to get application list data !"));
				gridPanel.clearData();
			}
		});
	}
	
	/**
	 * Render data to grid
	 * @param data
	 */
	private void renderGrid(PagedResultHolder<ApplicationDTO> data){
		if(data == null){
			gridPanel.clearData();
		}else{
			gridPanel.clearData();
			gridPanel.setData(data);
			/*List<ApplicationDTO> result = data.getHoldedData();
			if(result.size() > 0){
				gridPanel.clearData();
				for (ApplicationDTO dto : result) {
					gridPanel.appendRow(new ApplicationDTO(dto.getId(), dto.getApplicationCode(), dto.getApplicationName(), dto.getApplicationUrl(), dto.getIsActive(), dto.getApplicationLoginUrl(), dto.getIsConcurentUser()));
				}
			}*/
		}		
	}

	@Override
	public String getTitlePanel() {		
		return I18Utilities.getInstance().getInternalitionalizeText("security.applicationlist.title.panel.applicationlist", "Application List").toUpperCase();
	}
}