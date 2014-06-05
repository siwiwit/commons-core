package id.co.gpsc.common.client.security.lookup.base;

import id.co.gpsc.common.client.control.lookup.BaseSimpleMultipleResultLookupDialog;

/**
 * Base Lookup Multiple Result
 * @author I Gede Mahendra
 * @since Dec 21, 2012, 1:55:13 PM
 * @version $Id
 */
public abstract class BaseSecurityMultipleResultLookupDialog<KEY, ENTITY> extends BaseSimpleMultipleResultLookupDialog<KEY,ENTITY>{
	
	/**
	 * get i18 OK label
	 **/
	protected  String getOkLabelI18Key(){
		return "global.lookup.chooseButton";
	}
	
	/**
	 * i18 key untuk cancel
	 **/
	protected  String getCancelLabelI18Key(){
		return "global.lookup.cancelButon";
	}
	
	
	@Override
	protected String getSearchButtonLabelI18Key() {
		return "global.common.buttonSearch";
	}

	@Override
	protected String getResetButtonLabelI18Key() {
		return "global.common.buttonReset";
	}
	
	
	@Override
	protected String getNoneSelectedI18Key() {
		return "global.lookup.noneSelectedMessage";
	}
	
	@Override
	protected String getNoColumnI18Key() {
		return "global.lookup.noColumnHeader";
	}
}