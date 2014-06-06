package id.co.gpsc.common.client.dualcontrol;

import id.co.gpsc.common.client.control.MainPanelStackControl;
import id.co.gpsc.common.client.control.ViewScreenMode;
import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.widget.BaseSimpleComposite;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.app.CommonDualControlContainerTable;
import id.co.gpsc.common.data.app.DualControlApprovalStatusCode;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;

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
 * 
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class MasterDataUnifiedRejectMainPanel extends BaseSimpleComposite {
	
	
	
	protected static final String MESSAGE_HANDLER_NOT_REGISTERED =
			"sebagai catatan, editor dual control perlu di register manual, karena keterbatasan dengan proses reflection di GWT\n"
			+ "Mohon di cek, kemungkinan anda belum meregister handler anda. untuk melakukan ini, anda bisa cek method ini : "
			+ "id.co.gpsc.common.client.dualcontrol.DualControlUtil.registerDualControlHandler(IDualControlEditor<?>) \n"
			+ "register bisa anda lakukan onload dari applikasi anda. semoga berguna "; 

	private static MasterDataUnifiedRejectMainPanelUiBinder uiBinder = GWT
			.create(MasterDataUnifiedRejectMainPanelUiBinder.class);

	interface MasterDataUnifiedRejectMainPanelUiBinder extends
			UiBinder<Widget, MasterDataUnifiedRejectMainPanel> {
	}


	/**
	 * size dalam pixel, di sisakan berapa di sisi kanan dari grid
	 */
	public static int MINIMUM_SPACE_LEFT_ON_RIGHT_SIDE_OF_GRID = 50 ; 
	
	public static int MINIMUM_SPACE_LEFT_ON_LEFT_SIDE_OF_GRID = 300 ;
	
	@UiField Label lblTitleHader;
	 MasterDataUnifiedRejectGrid   rejectGrid;
	@UiField SimplePanel  approvalGridWrapperPanel;  
	@UiField ExtendedButton btnCari ;
	
	
	static  SimpleQueryFilter REJECT_FILTER ;  
	static {
		REJECT_FILTER = new SimpleQueryFilter(); 
		REJECT_FILTER.setField("approvalStatus");
		
		REJECT_FILTER.setOperator(SimpleQueryFilterOperator.fieldIn);
		REJECT_FILTER.setFilter(new String[]{
				DualControlApprovalStatusCode.REJECTED_BULK_DATA.toString()  , 
				DualControlApprovalStatusCode.REJECTED_CREATE_DATA.toString()  ,
				DualControlApprovalStatusCode.REJECTED_DELETE_DATA.toString()  ,
				DualControlApprovalStatusCode.REJECTED_UPDATE_DATA.toString()  ,
		});
	}
	
	SimpleQueryFilter [] filters = new SimpleQueryFilter[]{
		new SimpleQueryFilter("approverUserId" , SimpleQueryFilterOperator.equal , getCurrentUserLogin())  , 	
		REJECT_FILTER	
			
	}; 
	
	SimpleSortArgument [] sorts = new SimpleSortArgument[]{
			
			new SimpleSortArgument("approvedTime" , false) 
	};
	
	
	
	
	public MasterDataUnifiedRejectMainPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		rejectGrid  = new MasterDataUnifiedRejectGrid(new DataProcessWorker<CommonDualControlContainerTable>() {
			
			@Override
			public void runProccess(CommonDualControlContainerTable data) {
				viewDataDetailHandler(data); 
				
			}
		} ); 
		approvalGridWrapperPanel.setWidget(rejectGrid);
		
		rejectGrid.setHeight(300);
		
		
		
		
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
			editor.viewSingleDataRejectedOrApprovedDataDetail(dualControlDataDef.getId());
			 
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
					editor.viewApprovedOrRejectedMultipleData(  dualControlDataDef.getId());
					editor.setRemarkLabel("Reject Remark");
					editor.switchRemarkReadonly(true); 
				}
			}.schedule(10);
		}
		
	}
	
	
	/**
	 * handle click 
	 **/
	@UiHandler("btnCari")
	protected void onCariClick (ClickEvent event) {
		rejectGrid.applyFilter(filters , sorts);
	}

	

	/**
	 * adjust screen sesuai dengan lebar dari screen
	 */
	public void adjustScreenWidth (){
		int lebar =  Window.getClientWidth();
		
		
		int lebarGrid = lebar -(MINIMUM_SPACE_LEFT_ON_RIGHT_SIDE_OF_GRID +MINIMUM_SPACE_LEFT_ON_LEFT_SIDE_OF_GRID)  ; 
		rejectGrid.setWidth(lebarGrid);
		//approvalGrid.adjustGridToFitCurrentScreenSize(MINIMUM_SPACE_LEFT_ON_RIGHT_SIDE_OF_GRID, GRID_MINIMUM_WIDTH);
		
	}
	
}
