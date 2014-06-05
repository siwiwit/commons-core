/**
 * 
 */
package id.co.gpsc.common.client.dualcontrol;

import id.co.gpsc.common.client.control.worklist.SimpleRPCDrivenPagedSimpleGridPanel;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.approval.CommonApprovalHeader;
import id.co.gpsc.common.data.approval.SimpleApprovalStatus;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;
import id.co.gpsc.common.util.json.SharedServerClientLogicManager;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import com.google.gwt.core.client.GWT;

/**
 * grid untuk dual control
 * @author <a href="mailto:gede.wibawa@sigma.co.id">Agus Gede Adipartha Wibawa</a>
 * @since Sep 24, 2013 1:42:22 PM
 */
public class SimpleApprovalGrid extends SimpleRPCDrivenPagedSimpleGridPanel<CommonApprovalHeader> {

	/**
	 * format date yang ditampilkan di grid
	 */
	private final String DISPLAYED_DATE_FORMAT = "d-MMM-yyyy";
	
	/**
	 * worker jika tombol approve di klik
	 */
	private DataProcessWorker<CommonApprovalHeader> approveProcessWorker;
	
	@Override
	protected String generateMessageOnRequestDataGridFailure(Throwable caught) {
		// FIXME ini msgnya belum i18n
		return "Gagal get data class " + CommonApprovalHeader.class.getName() +", error msg : " + caught.getMessage();
	}

	@Override
	protected Class<CommonApprovalHeader> getRenderedClass() {
		return CommonApprovalHeader.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<CommonApprovalHeader, ?>[] getColumnDefinitions() {
		CellButtonHandler<CommonApprovalHeader>[] cellButtons = generateCellButton();
		BaseColumnDefinition<CommonApprovalHeader, ?>[] retval = (BaseColumnDefinition<CommonApprovalHeader, ?>[]) new BaseColumnDefinition<?, ?>[] {
			generateRowNumberColumnDefinition("No", 40),
			
			generateButtonsCell(cellButtons, "Action", "", 80),
			
			new StringColumnDefinition<CommonApprovalHeader>("Item To Approve", 110) {

				@Override
				public String getData(CommonApprovalHeader data) {
					return data.getApprovalDefinition() == null ? "-" : data.getApprovalDefinition().getObjectName();
				}
			},
			
			new StringColumnDefinition<CommonApprovalHeader>("Reff No" , 100) {
				@Override
				public String getData(CommonApprovalHeader data) {
					return data.getReferenceNumber();
				}
			} , 
			
			new StringColumnDefinition<CommonApprovalHeader>("requestor", 80) {

				@Override
				public String getData(CommonApprovalHeader data) {
					return data.getRequestorUserName() == null ? "-" : data.getRequestorUserName();
				}
			},
			
			new StringColumnDefinition<CommonApprovalHeader>("request date", 80) {

				@Override
				public String getData(CommonApprovalHeader data) {
					return data.getRequestTime() == null ? "-" : SharedServerClientLogicManager.getInstance().getDateTimeParser().format(data.getRequestTime(), DISPLAYED_DATE_FORMAT);
				}
			},
			
			new StringColumnDefinition<CommonApprovalHeader>("request remark", 110) {

				@Override
				public String getData(CommonApprovalHeader data) {
					return data.getRequestRemark() == null ? "-" : data.getRequestRemark();
				}
			},
		};
		return retval;
	}

	/**
	 * generate cell button handler
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private CellButtonHandler<CommonApprovalHeader>[] generateCellButton() {
		CellButtonHandler<CommonApprovalHeader> approveButton = new CellButtonHandler<CommonApprovalHeader>("ui-icon-check", "Approve", new DataProcessWorker<CommonApprovalHeader>() {

			@Override
			public void runProccess(CommonApprovalHeader data) {
				
				
				if (approveProcessWorker == null){
					GWT.log("maaf, tidak ada handler untuk click di define, saya tidak melakukan apa-apa");
					return ;
				}
				approveProcessWorker.runProccess(data);
			}
		});
		return (CellButtonHandler<CommonApprovalHeader>[]) new CellButtonHandler<?>[] {approveButton};
	}
	
	public void setApproveProcessWorker(
			DataProcessWorker<CommonApprovalHeader> approveProcessWorker) {
		this.approveProcessWorker = approveProcessWorker;
		if ( this.approveProcessWorker == null){
			this.approveProcessWorker = new DataProcessWorker<CommonApprovalHeader>() {
				
				@Override
				public void runProccess(CommonApprovalHeader data) {
					
					
				}
			};
		}
	}
	
	public DataProcessWorker<CommonApprovalHeader> getApproveProcessWorker() {
		return approveProcessWorker;
	}
	
	/**
	 * menambahkan filter approval status code = waiting_approval
	 * @param currentFilters filter dari list panel
	 * @return filter yang baru
	 */
	private SigmaSimpleQueryFilter[] addWaitingApprovalToFilter(SigmaSimpleQueryFilter[] currentFilters) {
		SigmaSimpleQueryFilter waitingApprovalFilter = new SigmaSimpleQueryFilter();
		waitingApprovalFilter.setField("approvalStatusCode");
		waitingApprovalFilter.setOperator(SimpleQueryFilterOperator.equal);
		waitingApprovalFilter.setFilter(SimpleApprovalStatus.WAITING_APPROVAL.toString());
		if (currentFilters == null) {
			return new SigmaSimpleQueryFilter[] {waitingApprovalFilter};
		}
		SigmaSimpleQueryFilter[] newFilters = new SigmaSimpleQueryFilter[currentFilters.length + 1];
		//add new filter to array filter
		for (int i = 0; i < currentFilters.length; i++) {
			newFilters[i] = currentFilters[i];
		}
		newFilters[currentFilters.length] = waitingApprovalFilter;
		return newFilters;
	}
	
	@Override
	public void applyFilter(SigmaSimpleQueryFilter[] filters) {
		SigmaSimpleQueryFilter[] newFilters = addWaitingApprovalToFilter(filters);
		super.applyFilter(newFilters);
	}
	
	@Override
	public void applyFilter(SigmaSimpleQueryFilter[] filters,
			SigmaSimpleSortArgument[] sorts) {
		SigmaSimpleQueryFilter[] newFilters = addWaitingApprovalToFilter(filters);
		super.applyFilter(newFilters, sorts);
	}
}
