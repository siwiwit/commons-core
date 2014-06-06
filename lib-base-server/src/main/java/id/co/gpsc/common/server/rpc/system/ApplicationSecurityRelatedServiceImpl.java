package id.co.gpsc.common.server.rpc.system;

import id.co.gpsc.common.data.app.ApplicationSecurityRelatedService;
import id.co.gpsc.common.data.app.security.ClientSecurityData;
import id.co.gpsc.common.server.rpc.BaseServerRPCService;



/**
 * RPC class untuk handle security related task
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
/*@WebServlet(name="id.co.gpsc.common.server.rpc.system.ApplicationSecurityRelatedServiceImpl" , 
urlPatterns={"/rpc/security/application-security.app-rpc"})*/
public class ApplicationSecurityRelatedServiceImpl extends /*BaseSelfRegisteredRPCService*/BaseServerRPCService<ApplicationSecurityRelatedService> implements ApplicationSecurityRelatedService{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7059080708211478057L;
	

	@Override
	public ClientSecurityData getCurrentUserSecurityData() {
		//FIXME: ini blm di implementasi
		System.out.println("method id.co.gpsc.common.server.rpc.system.ApplicationSecurityRelatedServiceImpl.getCurrentUserSecurityData() masih hard codeed" );
		ClientSecurityData retval = new ClientSecurityData() ; 
		/*if ( usr!=null){
			retval.setFullName(usr.getFullName());
			retval.setSecurityToken(usr.getUuid());
			retval.setUserName(usr.getUsername());
			
			//FIXME : pemilihan branch blm jalan
			//retval.setUnitCode(unitCode)
		}*/
		return retval;
	}

	@Override
	public Class<ApplicationSecurityRelatedService> implementedInterface() {
		return ApplicationSecurityRelatedService.class;
	} 
}
