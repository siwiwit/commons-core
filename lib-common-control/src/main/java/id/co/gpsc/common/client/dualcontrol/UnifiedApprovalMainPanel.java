package id.co.gpsc.common.client.dualcontrol;

import java.util.Collection;

import id.co.gpsc.common.client.control.MainPanelStackControl;
import id.co.gpsc.common.client.control.ViewScreenMode;
import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.widget.BaseSimpleComposite;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * panel approval unified, jadi 1 untuk semua nya
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class UnifiedApprovalMainPanel extends BaseSimpleComposite {

	
	
	protected static final String MESSAGE_HANDLER_NOT_REGISTERED =
			"sebagai catatan, editor dual control perlu di register manual, karena keterbatasan dengan proses reflection di GWT\n"
			+ "Mohon di cek, kemungkinan anda belum meregister handler anda. untuk melakukan ini, anda bisa cek method ini : "
			+ "id.co.gpsc.common.client.dualcontrol.DualControlUtil.registerDualControlHandler(IDualControlEditor<?>) \n"
			+ "register bisa anda lakukan onload dari applikasi anda. semoga berguna "; 
	
	
	
	private static UnifiedApprovalMainPanelUiBinder uiBinder = GWT
			.create(UnifiedApprovalMainPanelUiBinder.class);

	
	
	
	
	
	
	@UiField Label lblTitleHader;
	 UnifiedApprovalGrid approvalGrid;
	@UiField SimplePanel  approvalGridWrapperPanel;  
	@UiField ExtendedButton btnCari ;
	
	
	
	
	
	
	
	
	private final  DataChangedEventHandler approveDoneHandler  = new  DataChangedEventHandler(){
		public void handleDataChange(Object data, id.co.gpsc.common.client.control.EditorOperation operation) {
			approvalGrid.reload();
		}
	};
	
	interface UnifiedApprovalMainPanelUiBinder extends
			UiBinder<Widget, UnifiedApprovalMainPanel> {
	}

	public UnifiedApprovalMainPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		approvalGrid = new UnifiedApprovalGrid( new DataProcessWorker<SimplifiedDualControlContainerTable>() {
			@Override
			public void runProccess(SimplifiedDualControlContainerTable data) {
				viewDataDetailHandler(data);
				
			}
		}, new DataProcessWorker<SimplifiedDualControlContainerTable>() {
			
			@Override
			public void runProccess(SimplifiedDualControlContainerTable data) {
				approveDataDetailHandler(data);
				
			}
			
		});
		approvalGridWrapperPanel.setWidget(approvalGrid);
	 
		
		
	}
	
	
	
	
	/**
	 * menampilkan data dual control. ini unutk menampilkan detail dari data
	 **/
	protected void viewDataDetailHandler ( SimplifiedDualControlContainerTable dualControlDataDef  ){
		IDualControlEditor<?> editor =  DualControlUtil.getInstance().getDualControlHandler(dualControlDataDef.getTargetObjectFQCN());
		if ( editor== null){
			Window.alert("maaf, anda belum mendefisikan handler untuk proses  view detail class: " + dualControlDataDef.getTargetObjectFQCN() +"\n, aplikasi tidak dapat menampilkan data detail object ini. mohon di laporkan ke tim developer anda\n" + MESSAGE_HANDLER_NOT_REGISTERED);
			return ; 
		}
		MainPanelStackControl.getInstance().putPanel((Widget)editor, ViewScreenMode.normal);
		editor.viewSingleDataApprovalDetail(dualControlDataDef.getId());
		
	}
	
	
	
	
	
	
	/**
	 * render panel dalam mode approve
	 **/
	@SuppressWarnings("deprecation")
	protected void approveDataDetailHandler (final SimplifiedDualControlContainerTable dualControlDataDef  ){
		if ("Y".equals(  dualControlDataDef.getSingleDataFlag())){
			final IDualControlEditor<?> editor =  DualControlUtil.getInstance().getDualControlHandler(dualControlDataDef.getTargetObjectFQCN());
			if ( editor== null){
				Window.alert("maaf, anda belum mendefisikan handler untuk proses  approve detail class: " + dualControlDataDef.getTargetObjectFQCN() +"\n, aplikasi tidak dapat menampilkan data detail object ini. mohon di laporkan ke tim developer anda\n" + MESSAGE_HANDLER_NOT_REGISTERED);
				return ; 
			}
			MainPanelStackControl.getInstance().putPanel((Widget)editor, ViewScreenMode.normal);
			new Timer() {
				
				@Override
				public void run() {
					editor.approveSingleData(dualControlDataDef.getId());
					
				}
			}.schedule(10);
			
			
		}else{
			final IDualControlMultipleDataEditor<?> editor =  DualControlUtil.getInstance().getMultipleItemHandler(dualControlDataDef.getTargetObjectFQCN()); 
			if ( editor== null){
				Window.alert("Multi item\nmaaf, anda belum mendefisikan handler untuk proses  approve detail class: " + dualControlDataDef.getTargetObjectFQCN() +"\n, aplikasi tidak dapat menampilkan data detail object ini. mohon di laporkan ke tim developer anda\n" + MESSAGE_HANDLER_NOT_REGISTERED);
				return ; 
			}
			new Timer() {
				
				@Override
				public void run() {
					MainPanelStackControl.getInstance().putPanel((Widget)editor, ViewScreenMode.normal);
					editor.approveMultipleData(dualControlDataDef.getId());
					
				}
			}.schedule(10);
			
		}
		
		
	}
	
	
	/**
	 * handle click 
	 **/
	@UiHandler("btnCari")
	protected void onCariClick (ClickEvent event) {
		approvalGrid.applyFilter(null);
	}

	
	/**
	 * mencari editor panel untuk single panel
	 **/
	protected BaseDualControlMainPanel<?, ?> getSingleDataEditorPanel (String fqcn) {
		return null ; 
	}
	

}
