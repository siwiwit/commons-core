package id.co.gpsc.common.client.security.lookup;

import id.co.gpsc.common.client.control.BaseSimpleSearchComboContentLocator;
import id.co.gpsc.common.client.security.lookup.base.SecurityBaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.security.rpc.UserDomainRPCServiceAsync;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;
import id.co.gpsc.common.security.menu.UserDomain;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Browse User Popup
 * @author I Gede Mahendra
 * @since Nov 29, 2012, 3:33:10 PM
 * @version $Id
 */
public class LookupUserDomain extends SecurityBaseSimpleSingleResultLookupDialog<String, UserDomain>{

	@Override
	protected String getLookupTitleI18Key() {
		return "";
	}

	@Override
	protected String getDefaultPopupCaption() {
		return "Select User Domain";
	}

	@Override
	protected String getGridCaptionI18Key() {
		return "";
	}

	@Override
	protected String getDefaultGridCaption() {
		return "User Domain";
	}

	@SuppressWarnings("unchecked")	
	@Override
	protected BaseColumnDefinition<UserDomain, ?>[] getLookupGridColumns() {
		BaseColumnDefinition<UserDomain, ?>[] retval = (BaseColumnDefinition<UserDomain, ?>[]) new BaseColumnDefinition<?,?>[]{
			new StringColumnDefinition<UserDomain>("User Name" , 250 , "") {
				@Override
				public String getData(UserDomain data) {
					return data.getUsername();
				}
			},
			new StringColumnDefinition<UserDomain>("Full Name" , 250 , "") {
				@Override
				public String getData(UserDomain data) {
					return data.getFullName();
				}
			}
		};
		return retval;
	}

	@Override
	protected Class<? extends UserDomain> getLookupDataClass() {		
		return UserDomain.class;
	}

	@Override
	protected void retrieveData(SigmaSimpleQueryFilter[] filters, int page,int pageSize,AsyncCallback<PagedResultHolder<UserDomain>> callback) {
		UserDomainRPCServiceAsync.Util.getInstance().getUserDomainFromIIS(filters, page, pageSize, callback);
	}

	@Override
	protected Integer getDefaultWidth() {		
		return 580;
	}

	@Override
	protected BaseSimpleSearchComboContentLocator[] generateSearchCriteriaLocators() {		
		return new BaseSimpleSearchComboContentLocator[]{			
				new BaseSimpleSearchComboContentLocator("username", "Username", "", SimpleQueryFilterOperator.likeFrontOnly),	
				new BaseSimpleSearchComboContentLocator("fullName" ,"Full Name", "" , SimpleQueryFilterOperator.likeFrontOnly),								
		};
	}
}