package id.co.gpsc.common.client.form;

import java.util.ArrayList;


/**
 * exception client. ini untuk marker validasi gagal. apa saja yang gagal di kirim dari sini 
 **/
public class MandatoryValidationFailureException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4366728334196531451L;
	private ArrayList<MandatoryValidationEnabledControl> invalidControls =new ArrayList<MandatoryValidationEnabledControl>(); 
	
	
	public MandatoryValidationFailureException(String message){
		super(message);
	}
	
	
	
	
	
	
	/**
	 * versi ini, di constructor dengan List p
	 **/
	public MandatoryValidationFailureException(String message , ArrayList<MandatoryValidationEnabledControl> invalidControls){
		this(message); 
		this.invalidControls = invalidControls ; 
	}
	
	
	/**
	 * push control ke dalam exception kalau validasi mandatory failure
	 **/
	public void pushIfNotValid(MandatoryValidationEnabledControl control) {
		if (! control.isMandatory())
			return ; 
		if (!control.validateMandatory())
			invalidControls.add(control);
	}
	
	/**
	 * list of invalid controls
	 **/
	public ArrayList<MandatoryValidationEnabledControl> getInvalidControls() {
		return invalidControls;
	}

}
