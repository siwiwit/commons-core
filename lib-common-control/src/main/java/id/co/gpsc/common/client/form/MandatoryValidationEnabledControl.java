package id.co.gpsc.common.client.form;

import id.co.gpsc.common.control.ResourceBundleConfigurableControl;



/**
 * kontrol dengan mandatory validation enabled. kontrol ini bisa di validasi mandatory 
 **/
public interface MandatoryValidationEnabledControl extends ResourceBundleConfigurableControl{
	
	/**
	 * set object mandatory. ini sebaiknya di set dengan konfigurasi dari database. jadinya
	 * @param mandatory mandatory atau tidak
	 **/
	public void setMandatory (boolean mandatory  ) ;
	
	
	
	/**
	 * getter. current state. control mandatory atau tidak
	 * 
	 **/
	public boolean isMandatory (  ) ;
	/**
	 * nama business dari control. di inject oleh {@link #setConfiguredText(String)} dari konfigurasi
	 **/
	public String getControlBusinessName(); 
	
	
	
	/**
	 * validate
	 **/
	public boolean validateMandatory();

}
