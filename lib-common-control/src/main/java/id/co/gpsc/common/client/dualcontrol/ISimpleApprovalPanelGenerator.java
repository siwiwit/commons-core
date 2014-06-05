package id.co.gpsc.common.client.dualcontrol;

/**
 * panel ini untuk approval. ini di pergunakan 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface ISimpleApprovalPanelGenerator {
	
	
	/**
	 * class yang di handle
	 */
	public Class<?> getHandledClass () ; 
	
	/**
	 * panel untuk yang di pergunakan untuk approval
	 */
	public ISimpleApprovalPanel instantiatePanel () ; 

}
