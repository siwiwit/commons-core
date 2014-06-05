package id.co.gpsc.common.client.form;




import java.math.BigInteger;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

import id.co.gpsc.common.client.control.IParentLOVEnableControl;



/**
 * combo box. dedicated di buat dengan simple set value
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa(gede.sutarsa@gmail.com)</a>
 * @version $Id
 **/
public class ExtendedComboBox extends BaseExtendedListbox implements IParentLOVEnableControl, HasValue<String>{

	
	protected class BundlerEvent extends ValueChangeEvent<String>{

		protected BundlerEvent(String value) {
			super(value);
		}
		
	}
	
	public ExtendedComboBox(){
		super(false);
		addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				fireChangeEvent(); 
			}
		}); 
	}
	
	
	
	protected void fireChangeEvent () {
		String val = getValue() ; 
		BundlerEvent e = new BundlerEvent(val); 
		for ( ValueChangeHandler<String > scn : handlers){
			scn.onValueChange(e); 
		}
	}
	
	

	
	/**
	 * otomatis memeilih index sesuai dengan value di kirim. jadinya tidak iterate manual untuk kontrol
	 **/
	public void setValue (String value){
		GWT.log("##set value on combo box di panggil.value : " + value );
		setValue(value , false); 
		if ( readonlyMode)
			renderReadonlyData();
	}
	 
	
	/**
	 * current slected item
	 **/
	public String getValue() {
		if(getSelectedIndex()==-1)
			return null ;
		return getValue(getSelectedIndex());
	}
	
	
	
	/**
	 * ambil nilai dari combo box dengan asumsi adata adalah integer
	 **/
	public Integer getIntegerValue() {
		String val = getValue() ;
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return null ; 
		}
		
		
	}
	
	
	
	/**
	 * eversi ini dengan return BIg integer
	 **/
	public BigInteger getBigIntegerValue() {
		String val = getValue() ;
		try {
			return new BigInteger(val);
		} catch (Exception e) {
			return null ; 
		}
		
		
	}
	
	/**
	 * internal worker untuk set value
	 **/
	protected int setValueInternalWorker (String value){
		int total = this.getItemCount();
		
		for ( int i= 0 ; i<total ; i++){
			String val = getValue(i); 
			if ( val==null ){
				if ( value==null){
					setSelectedIndex(i);
					return i;
				}
			}
			else{
				if ( val.equals(value)){
					setSelectedIndex(i);
					return i;
				}
			}
			
		}
		
		setSelectedIndex(-1);
		return -1 ; 
		
	}

	@Override
	public boolean validateMandatory() {
		String val = getValue(); 
		return val!=null&& val.length()>0;
	}
	
	
	
	
	
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if ( !visible){
			if ( mandatoryMarker!=null){
				mandatoryMarker.getStyle().setProperty("display", "none");
			}
		}
		else{
			baseMandatoryMarkerRenderer();
		}
	}




	
	
	private ArrayList<ValueChangeHandler<String>> handlers = new ArrayList<ValueChangeHandler<String>>() ; 

	@Override
	public HandlerRegistration addValueChangeHandler(
			final ValueChangeHandler<String> handler) {
		HandlerRegistration retval = new HandlerRegistration() {
			
			@Override
			public void removeHandler() {
				handlers.remove(handler); 
				
			}
		};
		return retval ; 
		 
	}





	@Override
	public void setValue(String value, boolean fireEvents) {
		this.currentSelectedValue= value; 
		this.setValueInternalWorker(currentSelectedValue); 
		 if ( readonlyMode)
			 switchToReadonlyText();
		if ( fireEvents){
			fireChangeEvent(); 
		}
	}
	
	

	

	
}
