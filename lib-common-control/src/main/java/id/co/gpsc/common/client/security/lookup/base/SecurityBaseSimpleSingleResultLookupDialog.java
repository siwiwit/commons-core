package id.co.gpsc.common.client.security.lookup.base;

import id.co.gpsc.common.client.control.lookup.BaseSimpleSingleResultLookupDialog;



/**
 * base lookup cams
 * @author <a href="gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public abstract class SecurityBaseSimpleSingleResultLookupDialog<KEY, ENTITY> extends BaseSimpleSingleResultLookupDialog<KEY, ENTITY>{

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
