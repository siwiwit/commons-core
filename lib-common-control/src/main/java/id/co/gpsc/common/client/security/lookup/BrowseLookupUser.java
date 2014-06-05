package id.co.gpsc.common.client.security.lookup;

import id.co.gpsc.common.client.control.lookup.BaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.security.lookup.base.BaseSecurityLookupBrowseText;
import id.co.gpsc.common.security.dto.UserDTO;

import java.math.BigInteger;

/**
 * Browse button user
 * @author I Gede Mahendra
 * @since Dec 10, 2012, 2:45:57 PM
 * @version $Id
 */
public class BrowseLookupUser extends BaseSecurityLookupBrowseText<BigInteger ,  UserDTO>{

	
	@Override
	protected String getDataForTextBox(UserDTO data) {		
		return data.getUsername();
	}

	@Override
	protected BaseSimpleSingleResultLookupDialog<BigInteger, UserDTO> instantiateLookup() {
		return new LookupUser();
	}
}