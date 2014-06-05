package id.co.gpsc.common.client.dualcontrol;


import com.google.gwt.core.client.GWT;

import java.util.ArrayList;
import java.util.Date;

import id.co.gpsc.common.client.control.worklist.SimpleRPCDrivenPagedSimpleGridPanel;
import id.co.gpsc.common.client.widget.BaseSigmaComposite;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.app.DualControlApprovalStatusCode;
import id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.DateColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;
/**
 * grid untuk approval seragam terhadap data
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public   class UnifiedApprovalGrid  extends SimpleRPCDrivenPagedSimpleGridPanel<SimplifiedDualControlContainerTable> {

	
	
	
	public static String ACTION_GRID_I18_CODE ="core.dualcontrol.approvalgrid.actionHeaderLabel";
	
	
	
	private DataProcessWorker<SimplifiedDualControlContainerTable> viewDetailHandler ; 
	private DataProcessWorker<SimplifiedDualControlContainerTable> approveItemHandler ; 
	
	
	
	private SigmaSimpleSortArgument [] DEFAULT_SORTS ={
		new SigmaSimpleSortArgument("createdTime" ,false )	
	}; 
	
	private String currentUserName ; 
	
	private String fullName ; 
	
	//FIXME: internationalize me
	/**
	 * ini message kalau misalnya di larang submit data, data di buat oleh dirinya sendiri
	 *
	 **/
	protected String messageForNotAllowEditYourOwnData ="you are not allowed to approve this data, because this data was requested by you";
	
	
	private SigmaSimpleSortArgument [] sortArguments  =DEFAULT_SORTS ; 
	
	public UnifiedApprovalGrid(DataProcessWorker<SimplifiedDualControlContainerTable> viewDetailHandler , DataProcessWorker<SimplifiedDualControlContainerTable> approveItemHandler){
		super();
		this.viewDetailHandler = viewDetailHandler ; 
		this.approveItemHandler = approveItemHandler ; 
		setCaption("Data To approve");
		currentUserName = BaseSigmaComposite.userDetail.getUsername(); 
		fullName = BaseSigmaComposite.userDetail.getFullNameUser();
	}
	@Override
	protected String generateMessageOnRequestDataGridFailure(Throwable caught) {
		return "Gagal membaca data , error :" + caught.getMessage();
	}

	@Override
	protected Class<SimplifiedDualControlContainerTable> getRenderedClass() {
		return SimplifiedDualControlContainerTable.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<SimplifiedDualControlContainerTable, ?>[] getColumnDefinitions() {
		
		
		final CellButtonHandler<SimplifiedDualControlContainerTable> handlers[] = (CellButtonHandler<SimplifiedDualControlContainerTable> [])new   CellButtonHandler<?>[]{
			new CellButtonHandler<SimplifiedDualControlContainerTable>("ui-icon-folder-open" , "view detail of data" , viewDetailHandler ),
			// strick dual control ndak boleh di approve oleh orang yang sama
			new CellButtonHandler<SimplifiedDualControlContainerTable>("ui-icon-check" , "approve data" , approveItemHandler ){
				public boolean isDataAllowMeToVisible (SimplifiedDualControlContainerTable data) {
					return checkIsAllowApproveItem(data);
				}
			}
		};
		
		
		
		
		BaseColumnDefinition<SimplifiedDualControlContainerTable, ?>[] swap =   
				(BaseColumnDefinition<SimplifiedDualControlContainerTable, ?>[]) new BaseColumnDefinition<?, ?>[]{
			generateRowNumberColumnDefinition("No", 40) ,
			generateButtonsCell(handlers, "Action", ACTION_GRID_I18_CODE, 80),
			new StringColumnDefinition<SimplifiedDualControlContainerTable>("Item Type To Approve" , 150) {
				@Override
				public String getData(SimplifiedDualControlContainerTable data) {
					if ( data.getDualControlDefinition()==null)
						return "unknown";
					
					return data.getDualControlDefinition().getName();
				}
			}, 
			
			new StringColumnDefinition<SimplifiedDualControlContainerTable>("Action On Data" , 150) {
				@Override
				public String getData(SimplifiedDualControlContainerTable data) {
					return data.getOperationCode();
				}
			}, 
			
			//FIXME : cari konfigurasi. ini untuk label dari approval
			new StringColumnDefinition<SimplifiedDualControlContainerTable>("Requestor" , 150) {
				public String getData(SimplifiedDualControlContainerTable data) {
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
			new  DateColumnDefinition<SimplifiedDualControlContainerTable>("Request Date" , 100) {
				@Override
				public Date getData(SimplifiedDualControlContainerTable data) {
					return data.getCreatedTime();
				}
			},
			new StringColumnDefinition<SimplifiedDualControlContainerTable>("Remark" , 200) {
				public String getData(SimplifiedDualControlContainerTable data) {
					return data.getLatestRemark();
				};
			},
		};
		return swap;
	}
	
	
	protected boolean checkIsAllowApproveItem (SimplifiedDualControlContainerTable data) {
		if ( data.getDualControlDefinition() != null){
            GWT.log("current user is : " + BaseSigmaComposite.userDetail.getUsername() +", requestor : " + data.getCreatorUserId());
            if ( "Y".equals(data.getDualControlDefinition().getStrickDualControlFlag())){
            	
                return !data.getCreatorUserId().equals(  currentUserName) ; 
            }
            
        }
		GWT.log("wah, ke skip dia");
		return true ; 
	}
	
	
	
	private static final String[] RENDERED_TYPES = 
		{
			DualControlApprovalStatusCode.WAITING_APPROVE_CREATE.toString() , 
			DualControlApprovalStatusCode.WAITING_APPROVE_DELETE.toString() , 
			DualControlApprovalStatusCode.WAITING_APPROVE_UPDATE.toString() , 
			DualControlApprovalStatusCode.WAITING_APPROVE_BULK.toString()
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
	
	
	
      
}
