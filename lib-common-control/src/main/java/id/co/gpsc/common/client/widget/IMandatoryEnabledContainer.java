package id.co.gpsc.common.client.widget;

import id.co.gpsc.common.client.form.MandatoryValidationEnabledControl;
import id.co.gpsc.common.client.form.MandatoryValidationFailureException;

public interface IMandatoryEnabledContainer {

	/**
	 * validate mandatory 
	 **/
	public abstract void validateMandatory()
			throws MandatoryValidationFailureException;
	
	
	/**
	 * worker untuk register component yang mandatory enabled
	 * @param control kontrol yang mandatory enabled
	 **/
	public abstract void registerMandatoryComponent (MandatoryValidationEnabledControl control);
	
	
	/**
	 * detach komponen dari container
	 **/
	public abstract void unregisterMandatoryComponent (MandatoryValidationEnabledControl control);
	

}