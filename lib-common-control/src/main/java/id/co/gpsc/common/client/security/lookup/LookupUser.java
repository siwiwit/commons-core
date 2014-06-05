package id.co.gpsc.common.client.security.lookup;

import id.co.gpsc.common.client.control.BaseSimpleSearchComboContentLocator;
import id.co.gpsc.common.client.security.lookup.base.SecurityBaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.security.rpc.UserRPCServiceAsync;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;

import java.math.BigInteger;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Browse user popup
 * @author I Gede Mahendra
 * @since Dec 10, 2012, 2:47:19 PM
 * @version $Id
 */
public class LookupUser extends SecurityBaseSimpleSingleResultLookupDialog<BigInteger, UserDTO>{

	@Override
	protected String getLookupTitleI18Key() {		
		return "";
	}

	@Override
	protected String getDefaultPopupCaption() {		
		return "Select user";
	}

	@Override
	protected String getGridCaptionI18Key() {		
		return "";
	}

	@Override
	protected String getDefaultGridCaption() {		
		return "User";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected BaseColumnDefinition<UserDTO, ?>[] getLookupGridColumns() {	
		BaseColumnDefinition<UserDTO, ?>[] retval = (BaseColumnDefinition<UserDTO, ?>[]) new BaseColumnDefinition<?,?>[] {
			new StringColumnDefinition<UserDTO>("User Name" , 250 , "") {
				@Override
				public String getData(UserDTO data) {
					return data.getUsername();
				}
			},
			new StringColumnDefinition<UserDTO>("Full Name" , 250 , "") {
				@Override
				public String getData(UserDTO data) {
					return data.getFullName();
				}
			}
		};
		return retval;
	}

	@Override
	protected Class<? extends UserDTO> getLookupDataClass() {	
		return UserDTO.class;
	}

	@Override
	protected void retrieveData(SigmaSimpleQueryFilter[] filters, int page,int pageSize, AsyncCallback<PagedResultHolder<UserDTO>> callback) {		
		UserRPCServiceAsync.Util.getInstance().getUserByParameter(filters, page, pageSize, callback);		
	}

	@Override
	protected Integer getDefaultWidth() {		
		return 580;
	}

	@Override
	protected BaseSimpleSearchComboContentLocator[] generateSearchCriteriaLocators() {		
		return new BaseSimpleSearchComboContentLocator[]{
			new BaseSimpleSearchComboContentLocator("userCode", "Username", ""),
			new BaseSimpleSearchComboContentLocator("realName", "Fullname", "")
		};
	}
}
