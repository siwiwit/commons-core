/**
 * 
 */
package id.co.gpsc.common.client.security.lookup;

import id.co.gpsc.common.client.control.lookup.BaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.client.security.lookup.base.BaseSecurityLookupBrowseText;
import id.co.gpsc.common.security.dto.PageDefinitionDTO;

import java.math.BigInteger;

/**
 * 
 * @author <a href="mailto:agus.adiparth@gmail.com">Agus Gede Adipartha Wibawa</a>
 * @since Aug 15, 2013 2:24:11 PM
 */
public class BrowseLookupPageDefinition extends BaseSecurityLookupBrowseText<BigInteger , PageDefinitionDTO> {

	@Override
	protected BaseSimpleSingleResultLookupDialog<BigInteger, PageDefinitionDTO> instantiateLookup() {
		return new PageDefinitionLookup();
	}

	@Override
	protected String getDataForTextBox(PageDefinitionDTO data) {
		return data.getPageCode();
	}

}
