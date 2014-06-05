package id.co.gpsc.common.client.security.lookup;

import id.co.gpsc.common.client.control.lookup.BaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.security.lookup.base.BaseSecurityLookupBrowseText;
import id.co.gpsc.common.security.menu.UserDomain;

/**
 * Browse button user domain
 * @author I Gede Mahendra
 * @since Nov 30, 2012, 5:18:49 PM
 * @version $Id
 */
public class BrowseLookupUserDomain extends BaseSecurityLookupBrowseText<String  ,  UserDomain>{

	@Override
	protected BaseSimpleSingleResultLookupDialog<String, UserDomain> instantiateLookup() {
		return new LookupUserDomain();
	}

	@Override
	protected String getDataForTextBox(UserDomain data) {		
		return data.getUsername();		
	}
}