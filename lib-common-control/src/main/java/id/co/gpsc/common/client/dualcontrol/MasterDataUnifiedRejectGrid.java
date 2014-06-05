package id.co.gpsc.common.client.dualcontrol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.control.worklist.SimpleRPCDrivenPagedSimpleGridPanel;
import id.co.gpsc.common.client.rpc.DualControlDataRPCServiceAsync;
import id.co.gpsc.common.client.widget.BaseSigmaComposite;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.CommonDualControlContainerTable;
import id.co.gpsc.common.data.app.DualControlApprovalStatusCode;
import id.co.gpsc.common.data.app.DualControlDefinition;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.DateColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

/**
 * Grid Reject 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class MasterDataUnifiedRejectGrid extends SimpleRPCDrivenPagedSimpleGridPanel<CommonDualControlContainerTable>{


	public static String ACTION_GRID_I18_CODE ="core.dualcontrol.approvalgrid.actionHeaderLabel";
	
	
	
	private DataProcessWorker<CommonDualControlContainerTable> viewDetailHandler ; 
	private DataProcessWorker<CommonDualControlContainerTable> approveItemHandler ; 
	
	
	
	private SigmaSimpleSortArgument [] DEFAULT_SORTS ={
		new SigmaSimpleSortArgument("createdTime" ,false )	
	}; 
	
	private String currentUserName ; 
	
	private String fullName ; 
	

	
	
	/**
	 * flag LOV definition sudah di load atau tidak
	 */
	private boolean lovDefinitionLoaded = false ; 
	
	
	
	/**
	 * definisi dual control
	 */
	private HashMap<String, DualControlDefinition> indexedDualControlDefinition = new HashMap<String, DualControlDefinition>(); 
	
	
	//FIXME: internationalize me
	/**
	 * ini message kalau misalnya di larang submit data, data di buat oleh dirinya sendiri
	 *
	 **/
	private String messageForNotAllowEditYourOwnData ="you are not allowed to approve this data, because this data was requested by you";
	
	
	private SigmaSimpleSortArgument [] sortArguments  =DEFAULT_SORTS ; 
	
	public MasterDataUnifiedRejectGrid(DataProcessWorker<CommonDualControlContainerTable> viewDetailHandler ){
		super();
		this.viewDetailHandler = viewDetailHandler ;  
		setCaption("Data To approve");
		currentUserName = BaseSigmaComposite.userDetail.getUsername(); 
		fullName = BaseSigmaComposite.userDetail.getFullNameUser();
		
		
	}
	@Override
	protected String generateMessageOnRequestDataGridFailure(Throwable caught) {
		return "Gagal membaca data , error :" + caught.getMessage();
	}

	@Override
	protected Class<CommonDualControlContainerTable> getRenderedClass() {
		return CommonDualControlContainerTable.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<CommonDualControlContainerTable, ?>[] getColumnDefinitions() {
		
		
		final CellButtonHandler<CommonDualControlContainerTable> handlers[] = (CellButtonHandler<CommonDualControlContainerTable> [])new   CellButtonHandler<?>[]{
			new CellButtonHandler<CommonDualControlContainerTable>("ui-icon-folder-open" , "view detail of data" , viewDetailHandler ),
			
		};
		
		
		
		
		BaseColumnDefinition<CommonDualControlContainerTable, ?>[] swap =   
				(BaseColumnDefinition<CommonDualControlContainerTable, ?>[]) new BaseColumnDefinition<?, ?>[]{
			generateRowNumberColumnDefinition("No", 40) ,
			generateButtonsCell(handlers, "Action", ACTION_GRID_I18_CODE, 80),
			new StringColumnDefinition<CommonDualControlContainerTable>("Rejected Item Type" , 150) {
				@Override
				public String getData(CommonDualControlContainerTable data) {
					if (! indexedDualControlDefinition.containsKey(data.getTargetObjectFQCN()))
						return "unknown"; 
					return indexedDualControlDefinition.get(data.getTargetObjectFQCN()).getName();
				}
			}, 
			
			new StringColumnDefinition<CommonDualControlContainerTable>("Action On Data" , 150) {
				@Override
				public String getData(CommonDualControlContainerTable data) {
					return data.getOperationCode();
				}
			}, 
			
			new StringColumnDefinition<CommonDualControlContainerTable>("Reff Number" , 150) {
				@Override
				public String getData(CommonDualControlContainerTable data) {
					return data.getReffNo();
				}
			}, 
			
			
			new  DateColumnDefinition<CommonDualControlContainerTable>("Request Date" , 100) {
				@Override
				public Date getData(CommonDualControlContainerTable data) {
					return data.getCreatedTime();
				}
			},
			
			new StringColumnDefinition<CommonDualControlContainerTable>("Reject By" , 100) {
				@Override
				public String getData(CommonDualControlContainerTable data) {
					return data.getApproverUserId();
				}
			},
			
			new  DateColumnDefinition<CommonDualControlContainerTable>("Reject Date" , 100) {
				@Override
				public Date getData(CommonDualControlContainerTable data) {
					return data.getApprovedTime();
				}
			},
			
			
			
			new StringColumnDefinition<CommonDualControlContainerTable>("Approval Request Remark" , 200) {
				public String getData(CommonDualControlContainerTable data) {
					return data.getLatestRemark();
					
				};
			},
			
			new StringColumnDefinition<CommonDualControlContainerTable>("Reject Remark" , 200) {
				public String getData(CommonDualControlContainerTable data) {
					return data.getApprovalRemark();
					
				};
			},
		};
		return swap;
	}
	
	
	
	
	
	private static final String[] RENDERED_TYPES = 
		{
			DualControlApprovalStatusCode.REJECTED_BULK_DATA.toString() , 
			DualControlApprovalStatusCode.REJECTED_CREATE_DATA.toString() , 
			DualControlApprovalStatusCode.REJECTED_DELETE_DATA.toString() , 
			DualControlApprovalStatusCode.REJECTED_UPDATE_DATA.toString()
		};
	
	@Override
	public void applyFilter(SigmaSimpleQueryFilter[] filters) {
		// tambahkan filter
		
		ArrayList<SigmaSimpleQueryFilter> filtersArray = new ArrayList<SigmaSimpleQueryFilter>(); 
		if ( filters!= null ){
			for( SigmaSimpleQueryFilter scn : filters){
				filtersArray.add(scn); 
			}
			
		}
		SigmaSimpleQueryFilter fltTipe = new SigmaSimpleQueryFilter(); 
		fltTipe.setField("approvalStatus"); 
		fltTipe.setFilter(RENDERED_TYPES); 
		filtersArray.add(fltTipe);
		
		
		
		filters = new SigmaSimpleQueryFilter[filtersArray.size()]; 
		filtersArray.toArray(filters); 
		
		super.applyFilter(filters , sortArguments);
	}
	
	
	
	@Override
	public void setData(final PagedResultHolder<CommonDualControlContainerTable> data) {
		if ( !lovDefinitionLoaded){
			DualControlDataRPCServiceAsync.Util.getInstance().getMasterDataDualControlDefinitions(new AsyncCallback<List<DualControlDefinition>>() {
				
				@Override
				public void onSuccess(List<DualControlDefinition> result) {
					if (result!= null){
						for (DualControlDefinition scn : result ){
							indexedDualControlDefinition.put(scn.getId(), scn); 
						}
					}
					lovDefinitionLoaded = true ; 
					parentObjectSetDataWorker(data);
				}
				@Override
				public void onFailure(Throwable caught) {
					lovDefinitionLoaded = true ; 
					parentObjectSetDataWorker(data);
				}
			});
		}else{
			super.setData(data);
		}
		
	}
	
	
	
	/**
	 * set data pada parent object 
	 */
	protected void parentObjectSetDataWorker (final PagedResultHolder<CommonDualControlContainerTable> data) {
		super.setData(data);
	}
	
	
	
	
	public void getDualControlDefinition (final  CommonDualControlContainerTable data ,final AsyncCallback<DualControlDefinition> callback ){
		if ( indexedDualControlDefinition.containsKey(data.getTargetObjectFQCN())){
			callback.onSuccess(indexedDualControlDefinition.get(data.getTargetObjectFQCN()));
			return ; 
		}
			
		DualControlDataRPCServiceAsync.Util.getInstance().getMasterDataDualControlDefinitions(new AsyncCallback<List<DualControlDefinition>>() {
			
			@Override
			public void onSuccess(List<DualControlDefinition> result) {
				if (result!= null){
					for (DualControlDefinition scn : result ){
						indexedDualControlDefinition.put(scn.getId(), scn); 
					}
				}
				lovDefinitionLoaded = true ; 
				callback.onSuccess(indexedDualControlDefinition.get(data.getTargetObjectFQCN()));
			}
			@Override
			public void onFailure(Throwable caught) {
				lovDefinitionLoaded = true ; 
				callback.onFailure(caught);
			}
		});
	}
}
