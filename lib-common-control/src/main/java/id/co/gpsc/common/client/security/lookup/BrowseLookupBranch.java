package id.co.gpsc.common.client.security.lookup;

import id.co.gpsc.common.client.control.lookup.BaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.security.lookup.base.BaseSecurityLookupBrowseText;
import id.co.gpsc.common.security.dto.BranchDTO;

import java.math.BigInteger;

/**
 * Browse button branch
 * @author I Gede Mahendra
 * @since Jan 31, 2013, 11:18:03 AM
 * @version $Id
 */
public class BrowseLookupBranch extends BaseSecurityLookupBrowseText<BigInteger, BranchDTO>{

	@Override
	protected BaseSimpleSingleResultLookupDialog<BigInteger, BranchDTO> instantiateLookup() {		
		return new LookupBranch();
	}

	@Override
	protected String getDataForTextBox(BranchDTO data) {		
		return data.getBranchCode();
	}
	
	
}