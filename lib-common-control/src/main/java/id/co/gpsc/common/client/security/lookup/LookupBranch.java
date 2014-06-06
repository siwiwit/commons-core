package id.co.gpsc.common.client.security.lookup;

import id.co.gpsc.common.client.control.BaseSimpleSearchComboContentLocator;
import id.co.gpsc.common.client.security.lookup.base.SecurityBaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.security.rpc.BranchRPCServiceAsync;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.security.dto.BranchDTO;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import java.math.BigInteger;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Browse branch popup
 * @author I Gede Mahendra
 * @since Jan 31, 2013, 11:06:01 AM
 * @version $Id
 */
public class LookupBranch extends SecurityBaseSimpleSingleResultLookupDialog<BigInteger, BranchDTO>{

	@Override
	protected String getLookupTitleI18Key() {		
		return "";
	}

	@Override
	protected Class<? extends BranchDTO> getLookupDataClass() {		
		return BranchDTO.class;
	}

	@Override
	protected void retrieveData(SimpleQueryFilter[] filters, int page, int pageSize, AsyncCallback<PagedResultHolder<BranchDTO>> callback) {
		BranchRPCServiceAsync.Util.getInstance().getDataByParameter(filters, page, pageSize, callback);	
	}

	@Override
	protected Integer getDefaultWidth() {		
		return 580;
	}

	@Override
	protected String getDefaultPopupCaption() {		
		return "Select Branch Parent";
	}

	@Override
	protected String getGridCaptionI18Key() {		
		return "";
	}

	@Override
	protected String getDefaultGridCaption() {		
		return "Branch";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<BranchDTO, ?>[] getLookupGridColumns() {
		BaseColumnDefinition<BranchDTO, ?>[] retval = (BaseColumnDefinition<BranchDTO, ?>[]) new BaseColumnDefinition<?, ?>[]{
			new StringColumnDefinition<BranchDTO>("Branch Code", 100, "") {
				@Override
				public String getData(BranchDTO data) {				
					return data.getBranchCode();
				}				
			},
			new StringColumnDefinition<BranchDTO>("Branch Name", 150, "") {
				@Override
				public String getData(BranchDTO data) {				
					return data.getBranchName();
				}				
			},
			new StringColumnDefinition<BranchDTO>("Branch Adress", 250, "") {
				@Override
				public String getData(BranchDTO data) {				
					return data.getBranchAddress();
				}				
			}
		};
		return retval;
	}

	@Override
	protected BaseSimpleSearchComboContentLocator[] generateSearchCriteriaLocators() {		
		return new BaseSimpleSearchComboContentLocator[]{
			new BaseSimpleSearchComboContentLocator("branchCode", "Branch Code", ""),
			new BaseSimpleSearchComboContentLocator("branchName", "Branch Name", "")
		};
	}
}