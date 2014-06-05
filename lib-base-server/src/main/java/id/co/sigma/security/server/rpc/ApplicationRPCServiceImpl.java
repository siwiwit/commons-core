/**
 * 
 */
package id.co.sigma.security.server.rpc;

import id.co.sigma.common.data.PagedResultHolder;
import id.co.sigma.common.security.domain.Application;
import id.co.sigma.common.security.dto.ApplicationDTO;
import id.co.sigma.common.security.rpc.ApplicationRPCService;
import id.co.sigma.common.server.rpc.impl.BaseRPCHandler;
import id.co.sigma.security.server.service.IApplicationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dode
 * @version $Id
 * @since Dec 19, 2012, 2:54:06 PM
 */
/*@WebServlet(
		name="id.co.sigma.arium.security.server.rpc.ApplicationRPCServiceImpl" , 
		description="Servlet RPC untuk handle application" , 
		urlPatterns={"/sigma-rpc/application.app-rpc"})*/
public class ApplicationRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/BaseSecurityRPCService<ApplicationRPCService>
		implements ApplicationRPCService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -616116232677492291L;
	
	@Autowired
	private IApplicationService applicationService;

	@Override
	public List<Application> getApplicationList() throws Exception {
		return applicationService.getApplicationList();
	}

	@Override
	public PagedResultHolder<ApplicationDTO> getApplicationList(int pagePosition, int pageSize) throws Exception {		
		return applicationService.getApplicationList(pagePosition, pageSize);
	}

	@Override
	public void saveOrUpdate(ApplicationDTO data) throws Exception {
		applicationService.saveOrUpdate(data);
	}

	@Override
	public ApplicationDTO getCurrentAppApplicationInfo() {
		Application app = applicationService.getCurrentAppDetailData(); 
		if ( app== null)
			return null ;
		ApplicationDTO retval = new ApplicationDTO(); 
		retval.setApplicationCode(app.getApplicationCode()); 
		retval.setApplicationLoginUrl(app.getAutentificationLoginUrl()); 
		retval.setApplicationNotifyUrl(app.getNotifyAuthenticateResultUrl()); 
		retval.setApplicationUrl(app.getNotifyAuthenticateResultUrl()); 
		retval.setId(app.getId()); 
		retval.setIsActive("A".equalsIgnoreCase(  app.getStatus())); 
		
		return retval;
	}

	@Override
	public Class<ApplicationRPCService> implementedInterface() {
		return ApplicationRPCService.class;
	}
}
