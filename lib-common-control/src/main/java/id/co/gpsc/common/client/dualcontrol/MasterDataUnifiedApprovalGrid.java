package id.co.gpsc.common.client.dualcontrol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.control.worklist.SimpleRPCDrivenPagedSimpleGridPanel;
import id.co.gpsc.common.client.rpc.DualControlDataRPCServiceAsync;
import id.co.gpsc.common.client.widget.BaseSimpleComposite;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.CommonDualControlContainerTable;
import id.co.gpsc.common.data.app.DualControlApprovalStatusCode;
import id.co.gpsc.common.data.app.DualControlDefinition;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.DateColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

/**
 * pengganti approval 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class MasterDataUnifiedApprovalGrid extends SimpleRPCDrivenPagedSimpleGridPanel<CommonDualControlContainerTable>{


	public static String ACTION_GRID_I18_CODE ="core.dualcontrol.approvalgrid.actionHeaderLabel";
	
	
	
	private DataProcessWorker<CommonDualControlContainerTable> viewDetailHandler ; 
	private DataProcessWorker<CommonDualControlContainerTable> approveItemHandler ; 
	
	
	
	private SimpleSortArgument [] DEFAULT_SORTS ={
		new SimpleSortArgument("createdTime" ,false )	
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
	
	
	
	/**
	 * ini message kalau misalnya di larang submit data, data di buat oleh dirinya sendiri
	 *
	 **/
	private String messageForNotAllowEditYourOwnData ="you are not allowed to approve this data, because this data was requested by you";
	
	
	private SimpleSortArgument [] sortArguments  =DEFAULT_SORTS ; 
	
	public MasterDataUnifiedApprovalGrid(DataProcessWorker<CommonDualControlContainerTable> viewDetailHandler , DataProcessWorker<CommonDualControlContainerTable> approveItemHandler){
		super();
		this.viewDetailHandler = viewDetailHandler ; 
		this.approveItemHandler = approveItemHandler ; 
		setCaption("Data To approve");
		currentUserName = BaseSimpleComposite.userDetail.getUsername(); 
		fullName = BaseSimpleComposite.userDetail.getFullNameUser();
		
		
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
			// strick dual control ndak boleh di approve oleh orang yang sama
			new CellButtonHandler<CommonDualControlContainerTable>("ui-icon-check" , "approve data" , approveItemHandler ){
				public boolean isDataAllowMeToVisible (CommonDualControlContainerTable data) {
					return checkIsAllowApproveItem(data);
				}
			}
		};
		
		
		
		
		BaseColumnDefinition<CommonDualControlContainerTable, ?>[] swap =   
				(BaseColumnDefinition<CommonDualControlContainerTable, ?>[]) new BaseColumnDefinition<?, ?>[]{
			generateRowNumberColumnDefinition("No", 40) ,
			generateButtonsCell(handlers, "Action", ACTION_GRID_I18_CODE, 80),
			new StringColumnDefinition<CommonDualControlContainerTable>("Item Type To Approve" , 150) {
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
			
			//FIXME : cari konfigurasi. ini untuk label dari approval
			new StringColumnDefinition<CommonDualControlContainerTable>("Requestor" , 150) {
				public String getData(CommonDualControlContainerTable data) {
					if ( currentUserName.equals(data.getCreatorUserId())){
						String retval =  "<i title='"+ fullName+"'>You</i>";
						if (!checkIsAllowApproveItem(data)){
							retval+="<span class='ui-icon ui-icon-alert' title='"+messageForNotAllowEditYourOwnData+"'></span>";
						}
						return retval ; 
					}
					return data.getCreatorUserId();
				};
			},
			new  DateColumnDefinition<CommonDualControlContainerTable>("Request Date" , 100) {
				@Override
				public Date getData(CommonDualControlContainerTable data) {
					return data.getCreatedTime();
				}
			},
			new StringColumnDefinition<CommonDualControlContainerTable>("Remark" , 200) {
				public String getData(CommonDualControlContainerTable data) {
					return data.getLatestRemark();
					
				};
			},
		};
		return swap;
	}
	
	
	protected boolean checkIsAllowApproveItem (CommonDualControlContainerTable data) {
		if ( indexedDualControlDefinition.containsKey(data.getTargetObjectFQCN())){
            GWT.log("current user is : " + BaseSimpleComposite.userDetail.getUsername() +", requestor : " + data.getCreatorUserId());
            if ( "Y".equals(indexedDualControlDefinition.get(data.getTargetObjectFQCN()).getStrickDualControlFlag())){
            	boolean enableVisible =!data.getCreatorUserId().equalsIgnoreCase(  currentUserName) ;
            	GWT.log("data id : " + data.getId() + "- enable : " + enableVisible);
                return  enableVisible  ; 
            }
            else
            	return true ; 
        }
		GWT.log("none match unutk id " + data.getId()  + " disable approve");
		return false ; 
	}
	
	
	
	private static final String[] RENDERED_TYPES = 
		{
			DualControlApprovalStatusCode.WAITING_APPROVE_CREATE.toString() , 
			DualControlApprovalStatusCode.WAITING_APPROVE_DELETE.toString() , 
			DualControlApprovalStatusCode.WAITING_APPROVE_UPDATE.toString() , 
			DualControlApprovalStatusCode.WAITING_APPROVE_BULK.toString()
		};
	
	@Override
	public void applyFilter(SimpleQueryFilter[] filters) {
		// tambahkan filter
		
		ArrayList<SimpleQueryFilter> filtersArray = new ArrayList<SimpleQueryFilter>(); 
		if ( filters!= null ){
			for( SimpleQueryFilter scn : filters){
				filtersArray.add(scn); 
			}
			
		}
		SimpleQueryFilter fltTipe = new SimpleQueryFilter(); 
		fltTipe.setField("approvalStatus"); 
		fltTipe.setFilter(RENDERED_TYPES); 
		filtersArray.add(fltTipe);
		
		
		
		filters = new SimpleQueryFilter[filtersArray.size()]; 
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
