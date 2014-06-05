package id.co.gpsc.common.client.dualcontrol;


import id.co.gpsc.common.client.control.MainPanelStackControl;
import id.co.gpsc.common.client.control.ViewScreenMode;
import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.widget.BaseSigmaComposite;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.app.CommonDualControlContainerTable;

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
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class MasterDataUnifiedApprovalMainPanel extends BaseSigmaComposite {
	

	
	protected static final String MESSAGE_HANDLER_NOT_REGISTERED =
			"sebagai catatan, editor dual control perlu di register manual, karena keterbatasan dengan proses reflection di GWT\n"
			+ "Mohon di cek, kemungkinan anda belum meregister handler anda. untuk melakukan ini, anda bisa cek method ini : "
			+ "id.co.sigma.common.client.dualcontrol.DualControlUtil.registerDualControlHandler(IDualControlEditor<?>) \n"
			+ "register bisa anda lakukan onload dari applikasi anda. semoga berguna "; 
	

	private static MasterDataUnifiedApprovalMainPanelUiBinder uiBinder = GWT
			.create(MasterDataUnifiedApprovalMainPanelUiBinder.class);

	interface MasterDataUnifiedApprovalMainPanelUiBinder extends
			UiBinder<Widget, MasterDataUnifiedApprovalMainPanel> {
	}
	
	
	@UiField Label lblTitleHader;
	 MasterDataUnifiedApprovalGrid approvalGrid;
	@UiField SimplePanel  approvalGridWrapperPanel;  
	@UiField ExtendedButton btnCari ;
	
	
	
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	private final  DataChangedEventHandler approveDoneHandler  = new  DataChangedEventHandler(){
		public void handleDataChange(Object data, id.co.gpsc.common.client.control.EditorOperation operation) {
			approvalGrid.reload();
		}
	};
	
	
	

	public MasterDataUnifiedApprovalMainPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		approvalGrid = new MasterDataUnifiedApprovalGrid( new DataProcessWorker<CommonDualControlContainerTable>() {
			@Override
			public void runProccess(CommonDualControlContainerTable data) {
				viewDataDetailHandler(data);
				
			}
		}, new DataProcessWorker<CommonDualControlContainerTable>() {
			
			@Override
			public void runProccess(CommonDualControlContainerTable data) {
				approveDataDetailHandler(data);
				
			}
			
		});
		approvalGridWrapperPanel.setWidget(approvalGrid);
		
		approvalGrid.setHeight(300);
		DualControlUtil.getInstance().setApproveDoneHandler(approveDoneHandler);
		
		
	}
	
	
	
	/**
	 * size dalam pixel, di sisakan berapa di sisi kanan dari grid
	 */
	public static int MINIMUM_SPACE_LEFT_ON_RIGHT_SIDE_OF_GRID = 50 ; 
	
	public static int MINIMUM_SPACE_LEFT_ON_LEFT_SIDE_OF_GRID = 300 ;
	
	/**
	 * adjust screen sesuai dengan lebar dari screen
	 */
	public void adjustScreenWidth (){
		int lebar =  Window.getClientWidth();
		
		
		int lebarGrid = lebar -(MINIMUM_SPACE_LEFT_ON_RIGHT_SIDE_OF_GRID +MINIMUM_SPACE_LEFT_ON_LEFT_SIDE_OF_GRID)  ; 
		approvalGrid.setWidth(lebarGrid);
		//approvalGrid.adjustGridToFitCurrentScreenSize(MINIMUM_SPACE_LEFT_ON_RIGHT_SIDE_OF_GRID, GRID_MINIMUM_WIDTH);
		
	}

	
	
	
	
	/**
	 * menampilkan data dual control. ini unutk menampilkan detail dari data
	 **/
	protected void viewDataDetailHandler (final CommonDualControlContainerTable dualControlDataDef  ){
		if ("Y".equalsIgnoreCase(   dualControlDataDef.getSingleLineDataTypeFlag() )){
			IDualControlEditor<?> editor =  DualControlUtil.getInstance().getDualControlHandler(dualControlDataDef.getTargetObjectFQCN());
			
			if ( editor== null){
				Window.alert("maaf, anda belum mendefisikan handler untuk proses  view detail class: " + dualControlDataDef.getTargetObjectFQCN() +"\n, aplikasi tidak dapat menampilkan data detail object ini. mohon di laporkan ke tim developer anda\n" + MESSAGE_HANDLER_NOT_REGISTERED);
				return ; 
			}
			MainPanelStackControl.getInstance().putPanel((Widget)editor, ViewScreenMode.normal);
			editor.viewSingleDataApprovalDetail(dualControlDataDef.getId());
		}
		else {
			final IDualControlMultipleDataEditor<?> editor =  DualControlUtil.getInstance().getMultipleItemHandler(dualControlDataDef.getTargetObjectFQCN()); 
			if ( editor== null){
				Window.alert("Multi item\nmaaf, anda belum mendefisikan handler untuk proses  approve detail class: " + dualControlDataDef.getTargetObjectFQCN() +"\n, aplikasi tidak dapat menampilkan data detail object ini. mohon di laporkan ke tim developer anda\n" + MESSAGE_HANDLER_NOT_REGISTERED);
				return ; 
			}
			new Timer() {
				
				@Override
				public void run() {
					MainPanelStackControl.getInstance().putPanel((Widget)editor, ViewScreenMode.normal);
					editor.viewMultipleData( dualControlDataDef.getId());
					
				}
			}.schedule(10);
		}
		
	}
	
	
	
	
	
	
	/**
	 * render panel dalam mode approve
	 **/
	protected void approveDataDetailHandler (final CommonDualControlContainerTable dualControlDataDef  ){
		
		
		
		
		if ("Y".equalsIgnoreCase(  dualControlDataDef.getSingleLineDataTypeFlag() )){
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
