package id.co.gpsc.common.client.dualcontrol;


/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IDualControlEditorManager {
	
	
	
	/**
	 * instantiate dual control editor panel
	 * @param panelGenerator instantiate generator panel
	 */
	public void registerDualControlHandler ( IDualControlEditorGenerator<?> panelGenerator) ;
	
	
	

	
	
	/**
	 * register multiple item approval
	 */
	public void registerDualControlMultipleHandler( IDualControlMultipleDataEditorGenerator<?> generator ); 
	
	
	/**
	 * membaca dual control handler. ini di pergunakan untuk handle proses approve
	 **/
	public IDualControlEditor<?> getDualControlHandler ( String fqcn );
	
	
	
	/**
	 * multiple line approval handler
	 */
	public IDualControlMultipleDataEditor<?> getMultipleItemHandler( String fqcn ); 
	
	
	

}
