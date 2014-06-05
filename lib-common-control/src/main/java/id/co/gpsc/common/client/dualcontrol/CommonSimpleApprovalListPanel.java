/**
 * 
 */
package id.co.gpsc.common.client.dualcontrol;

import id.co.gpsc.common.client.control.MainPanelStackControl;
import id.co.gpsc.common.client.control.ViewScreenMode;
import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.advance.LOVCapabledComboBoxWithLabel;
import id.co.gpsc.common.client.form.advance.TextBoxWithLabel;
import id.co.gpsc.common.client.widget.BaseSigmaComposite;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.CoreLibLookup;
import id.co.gpsc.common.data.approval.CommonApprovalHeader;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author <a href="mailto:gede.wibawa@sigma.co.id">Agus Gede Adipartha Wibawa</a>
 * @since Sep 25, 2013 4:51:34 PM
 */
public class CommonSimpleApprovalListPanel extends BaseSigmaComposite {

	private static CommonSimpleApprovalListPanelUiBinder uiBinder = GWT
			.create(CommonSimpleApprovalListPanelUiBinder.class);
	
	
	private static final SigmaSimpleSortArgument[] APPROVAL_SORT_ARGS ={
		
		new SigmaSimpleSortArgument("requestTime" , false)
		
	} ; 
	@UiField LOVCapabledComboBoxWithLabel cbObjectType;
	@UiField TextBoxWithLabel txtReqUserName;
	@UiField ExtendedButton btnSearch;
	@UiField ExtendedButton btnReset;
	@UiField Label lblPanelTitle;
	@UiField SimpleApprovalGrid approvalGrid;

	interface CommonSimpleApprovalListPanelUiBinder extends
			UiBinder<Widget, CommonSimpleApprovalListPanel> {
	}

	public CommonSimpleApprovalListPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		cbObjectType.setCustomLookupParameter(CoreLibLookup.approvalDefinitionTypes);
		
		this.registerLOVCapableControl(cbObjectType);
		this.fillLookupValue();
		
		approvalGrid.setApproveProcessWorker(new DataProcessWorker<CommonApprovalHeader>() {
			
			@Override
			public void runProccess(final CommonApprovalHeader data) {
				
				//System.out.println("class handler di minta  : " + data.getTargteObjectFQCN() ) ; 
				final ISimpleApprovalPanel  handler = SimpleApprovalUtil.getInstance().getHandler(data.getTargteObjectFQCN());
				
				new Timer() {
					
					@Override
					public void run() {
						handler.setAfterApprovalCommand(new Command() {
							
							@Override
							public void execute() {
								getData();
							}
						});
						
						Widget panel = (Widget)handler ; 
						
						MainPanelStackControl.getInstance().putPanel(panel, ViewScreenMode.normal);
						new Timer() {
							
							@Override
							public void run() {
								handler.renderDataForApproval(data);
								
							}
						}.schedule(100);;
						
						
					}
				}.schedule(100);;
				
			}
		});
	}
	
	/**
	 * generate query filters
	 * @return query filters
	 */
	public SigmaSimpleQueryFilter[] generateFilters() {
		List<SigmaSimpleQueryFilter> filterList = new ArrayList<SigmaSimpleQueryFilter>();
		if (!(cbObjectType.getValue() == null || cbObjectType.getValue().trim().length() == 0)) {
			SigmaSimpleQueryFilter objectTypeFilter = new SigmaSimpleQueryFilter();
			objectTypeFilter.setField("targteObjectFQCN");
			objectTypeFilter.setOperator(SimpleQueryFilterOperator.equal);
			objectTypeFilter.setFilter(cbObjectType.getValue());
			filterList.add(objectTypeFilter);
		}
		
		if (!(txtReqUserName.getValue() == null || txtReqUserName.getValue().trim().length() == 0)) {
			SigmaSimpleQueryFilter reqUNameFilter = new SigmaSimpleQueryFilter();
			reqUNameFilter.setField("requestorUserName");
			reqUNameFilter.setOperator(SimpleQueryFilterOperator.likeBothSide);
			reqUNameFilter.setFilter(txtReqUserName.getValue());
			filterList.add(reqUNameFilter);
		}
		
		if (filterList.isEmpty())
			return null;
		
		SigmaSimpleQueryFilter[] filters = new SigmaSimpleQueryFilter[filterList.size()];
		filterList.toArray(filters);
		return filters;
	}
	
	@UiHandler("btnSearch")
	void onBtnSearchClickHandler(ClickEvent event) {
		getData();
	}
	
	
	@Override
	protected void onAttach() {
		super.onAttach();
		getData();
	}
	/**
	 * get list data yang waiting approval
	 */
	private void getData() {
		SigmaSimpleQueryFilter[] filters = generateFilters();
		approvalGrid.applyFilter(filters, APPROVAL_SORT_ARGS);
	}

	@UiHandler("btnReset")
	void onBtnResetClickHandler(ClickEvent event) {
		cbObjectType.setSelectedIndex(0);
		txtReqUserName.setValue(null);
		approvalGrid.clearData();
	}
}
