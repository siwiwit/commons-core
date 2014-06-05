package id.co.gpsc.common.client.form;

import com.google.gwt.user.client.Command;

import id.co.gpsc.common.client.control.lookup.BaseSimpleSingleResultLookupDialog;
import id.co.gpsc.common.control.SingleValueLookupResultHandler;
import id.co.gpsc.common.data.SingleKeyEntityData;




/**
 * textbox dengan tombol browse + tombol reset
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 25-Dec-2012
 **/
public abstract class BrowseTextBoxWithSingleResultLookup<KEY , ENTITY /*extends SingleKeyEntityData<KEY>*/> extends BrowseTextBox<ENTITY> {

	
	
	
	/**
	 * dialog untuk popup
	 **/
	private BaseSimpleSingleResultLookupDialog<KEY,ENTITY> lookupDialog ; 
	
	
	
	/**
	 * handler lookup
	 **/
	private SingleValueLookupResultHandler<ENTITY> lookupHandler  ; 
	
	
	/**
	 * worker untuk instantiate lookup dialog
	 **/
	protected abstract BaseSimpleSingleResultLookupDialog<KEY,ENTITY> instantiateLookup() ; 
	
	public BrowseTextBoxWithSingleResultLookup() {
		super(); 
		setBrowseClickHandler(new Command() {
			@Override
			public void execute() {
				getLookupDialog().showLookup();
			}
		});
	}
	
	/**
	 * proxy untuk akses lookup dialog
	 **/
	protected BaseSimpleSingleResultLookupDialog<KEY, ENTITY> getLookupDialog() {
		if ( lookupDialog==null){
			lookupDialog  = instantiateLookup() ; 
			lookupDialog.assignLookupResultHandler(new SingleValueLookupResultHandler<ENTITY>() {
				@Override
				public void onSelectionDone(ENTITY data) {
					setValue(data); 
					if ( lookupHandler!=null)
						lookupHandler.onSelectionDone(data);
					
				}
			});
		}
		return lookupDialog;
	}
	
	
	
	/**
	 * handler lookup
	 **/
	public SingleValueLookupResultHandler<ENTITY> getLookupHandler() {
		return lookupHandler;
	}
	/**
	 * handler lookup
	 **/
	public void setLookupHandler(
			SingleValueLookupResultHandler<ENTITY> lookupHandler) {
		this.lookupHandler = lookupHandler;
	}
	
	
	
	
	
	
	
	
	
}
