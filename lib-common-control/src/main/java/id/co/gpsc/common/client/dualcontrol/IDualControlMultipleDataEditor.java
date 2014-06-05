package id.co.gpsc.common.client.dualcontrol;

import id.co.gpsc.common.data.app.DualControlEnabledData;

import java.math.BigInteger;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * interface untuk approval multiple data
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IDualControlMultipleDataEditor<DATA extends DualControlEnabledData<?, ?>> {
	
	
	/**
	 * menampilkan data dalam mode readonly
	 **/
	public void viewMultipleData (BigInteger dualControlDataId) ;
	
	
	
	/**
	 * menampilkan data dalam mode readonly
	 **/
	public void viewApprovedOrRejectedMultipleData (BigInteger dualControlDataId) ;
	
	/**
	 * menampikan screen approval multiple data
	 **/
	public void approveMultipleData (BigInteger dualControlDataId) ;
	
	/**
	 * class yang di handle class ini
	 **/
	public abstract Class<DATA> getProccessedClass () ; 
	
	
	/**
	 * register change handler
	 **/
	public HandlerRegistration addDataChangeHandlers (final DataChangedEventHandler<DATA> handler);
	
	
	/**
	 * ganti remark menjadi mode readonly
	 */
	public void switchRemarkReadonly ( boolean readonly) ;
	
	
	/**
	 * ganti remark
	 */
	public void setRemarkLabel ( String label ); 
	
	

}
