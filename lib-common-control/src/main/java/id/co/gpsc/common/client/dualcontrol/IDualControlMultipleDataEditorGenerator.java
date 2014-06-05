package id.co.gpsc.common.client.dualcontrol;

import id.co.gpsc.common.data.app.DualControlEnabledData;

/**
 * 
 * panel generator untuk multiple approval dual control data
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 */
public interface IDualControlMultipleDataEditorGenerator<DATA extends DualControlEnabledData<?, ?>> {


	/**
	 * instantiate editor panel
	 */
	public IDualControlMultipleDataEditor<DATA> instantiatePanel () ;  
	
	
	/**
	 * class yang di handle generator ini
	 */
	public Class<DATA> getHandledClass () ; 
}
